/*
 *  Copyright (C) 2018  Wanghaobin<463540703@qq.com>

 *  AG-Enterprise 企业版源码
 *  郑重声明:
 *  如果你从其他途径获取到，请告知老A传播人，奖励1000。
 *  老A将追究授予人和传播人的法律责任!

 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.

 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.

 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.github.wxiaoqi.security.gate.config;

import com.codingapi.tx.datasource.relational.LCNTransactionDataSource;
import com.github.trang.druid.autoconfigure.DruidDataSourceConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 代理数据源
 *
 * @author ace
 * @version 2018/1/18.
 */
@AutoConfigureAfter(DruidDataSourceConfiguration.class)
@Configuration
public class TxConfiguration {

    @Bean("lcnTransactionDataSource")
    @ConfigurationProperties("spring.datasource.druid")
    @Primary
    public LCNTransactionDataSource dataSource(Environment env) {
        LCNTransactionDataSource dataSourceProxy = new LCNTransactionDataSource();
        dataSourceProxy.setDataSource(createDataSource(env));
        dataSourceProxy.setMaxCount(10);
        return dataSourceProxy;
    }

    public DataSource createDataSource(Environment env) {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return dataSourceBuilder.build();
    }
}
