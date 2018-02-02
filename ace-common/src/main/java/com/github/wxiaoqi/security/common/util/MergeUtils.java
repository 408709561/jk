package com.github.wxiaoqi.security.common.util;

import com.github.wxiaoqi.security.common.merge.MergeField;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author ace
 * @create 2018/2/2.
 */
@Slf4j
public class MergeUtils {
    static final Map<String, MergeField> mergeFieldMap = new HashMap<String, MergeField>();
    static final ListeningExecutorService backgroundRefreshPools =
            MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(20));
    static LoadingCache<String, Map<String, String>> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Map<String, String>>() {
                @Override
                public Map<String, String> load(String key) throws Exception {
                    log.debug("首次读取缓存: " + key);
                    MergeField mergeField = mergeFieldMap.get(key);
                    Object bean = BeanUtils.getBean(mergeField.feign());
                    Method method = mergeField.feign().getMethod(mergeField.method(), String.class);
                    Map<String, String> invoke = (Map<String, String>) method.invoke(bean, mergeField.key());
                    return invoke;
                }

                @Override
                public ListenableFuture<Map<String, String>> reload(final String key,
                                                                    Map<String, String> oldValue) throws Exception {
                    return backgroundRefreshPools.submit(() -> {
                        log.debug("异步刷新缓存: " + key);
                        MergeField mergeField = mergeFieldMap.get(key);
                        Object bean = BeanUtils.getBean(mergeField.feign());
                        Method method = mergeField.feign().getMethod(mergeField.method(), String.class);
                        Map<String, String> invoke = (Map<String, String>) method.invoke(bean, mergeField.key());
                        return invoke;
                    });
                }
            });

    public static Object mergeData(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        try {
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method m = signature.getMethod();
            ParameterizedType parameterizedType = (ParameterizedType) m.getGenericReturnType();
            Type rawType = parameterizedType.getRawType();
            List<?> result = null;
            // 非list直接返回
            if (((Class) rawType).isAssignableFrom(List.class)) {
                result = (List<?>) proceed;
                mergeResult(parameterizedType, result);
                return result;
            }else if(((Class) rawType).isAssignableFrom(TableResultResponse.class)){
                TableResultResponse response = (TableResultResponse) proceed;
                TableResultResponse.TableData data = (TableResultResponse.TableData) response.getData();
                result = data.getRows();
                mergeResult(parameterizedType, result);
                return response;
            }else{
                return proceed;
            }
        } catch (Exception e) {
            log.error("某属性数据聚合失败", e);
            return proceed;
        }

    }

    private static void mergeResult(ParameterizedType parameterizedType, List<?> result) throws java.util.concurrent.ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // 获取当前方法的返回值
        Type[] types = parameterizedType.getActualTypeArguments();
        Field[] fields = ((Class) types[0]).getDeclaredFields();
        List<Field> mergeFields = new ArrayList<Field>();
        Map<String, Map<String, String>> invokes = new HashMap<>();
        String className = ((Class) types[0]).getName();
        // 获取属性
        for (Field field : fields) {
            MergeField annotation = field.getAnnotation(MergeField.class);
            if (annotation != null) {
                mergeFields.add(field);
                String args = annotation.key();
                // 表示该属性需要将值聚合成条件远程查询
                if (annotation.isValueNeedMerge()) {
                    StringBuffer sb = new StringBuffer("");
                    Set<String> ids = new HashSet<>();
                    result.stream().forEach(obj -> {
                        field.setAccessible(true);
                        Object o = null;
                        try {
                            o = field.get(obj);
                            if (o != null) {
                                if (!ids.contains(o)) {
                                    ids.add(o.toString());
                                    sb.append(o.toString()).append(",");
                                }
                            }
                        } catch (IllegalAccessException e) {
                            log.error("数据属性加工失败:" + field, e);
                            throw new RuntimeException("数据属性加工失败:" + field, e);
                        }

                    });
                    sb.substring(0, sb.length() - 1);
                    args = sb.toString();
                } else {
                    String key = className + field.getName();
                    mergeFieldMap.put(key, annotation);
                    // 从缓存获取
                    Map<String, String> value =
                            (Map<String, String>) caches.get(key);
                    if (value != null) {
                        invokes.put(field.getName(), value);
                        continue;
                    }
                }
                Object bean = BeanUtils.getBean(annotation.feign());
                Method method = annotation.feign().getMethod(annotation.method(), String.class);
                Map<String, String> value = (Map<String, String>) method.invoke(bean, args);
                invokes.put(field.getName(), value);
            }
        }
        result.parallelStream().forEach(obj -> {
            for (Field field : mergeFields) {
                field.setAccessible(true);
                Object o = null;
                try {
                    o = field.get(obj);
                    if (o != null && invokes.get(field.getName()).containsKey(String.valueOf(o))) {
                        field.set(obj, invokes.get(field.getName()).get(o.toString()));
                    }
                } catch (IllegalAccessException e) {
                    log.error("数据属性加工失败:" + field, e);
                    throw new RuntimeException("数据属性加工失败:" + field, e);
                }

            }
        });
    }
}
