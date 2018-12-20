

package com.github.wxiaoqi.search.service;


import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;

/**
 * lucense 接口
 * @author ace
 */
public interface LuceneService {

    void save(IndexObject indexObject);

    void update(IndexObject indexObject);

    void delete(IndexObject indexObject);

    void deleteAll();

    TableResultResponse page(Integer pageNumber, Integer pageSize, String keyword);
}
