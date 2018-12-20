

package com.github.wxiaoqi.demo.depart.config;

import com.github.wxiaoqi.security.common.data.MybatisDataInterceptor;
import com.github.wxiaoqi.security.common.data.IUserDepartDataService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author ace
 * @create 2018/2/11.
 */
@Configuration
public class DepartDataConfig {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private IUserDepartDataService userDepartDataService;

    @PostConstruct
    public void init(){
        sqlSessionFactory.getConfiguration().addInterceptor(new MybatisDataInterceptor(userDepartDataService));
//        sqlSessionFactory.getConfiguration().addInterceptor(new TenantMybatisInterceptor());
    }
}
