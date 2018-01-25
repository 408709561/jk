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

package com.codingapi.tx.datasource.relational;

import com.codingapi.tx.aop.bean.TxTransactionLocal;
import com.codingapi.tx.datasource.bean.LCNDataSourceLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 关系型数据库动态代理连接池对象
 * create by lorne on 2017/7/29
 */

public class LCNDynamicTransactionDataSource extends LCNTransactionDataSource {


    private Logger logger = LoggerFactory.getLogger(LCNDynamicTransactionDataSource.class);


    private Map<String,DataSource> dataSourceMap = new ConcurrentHashMap<>();


    private String getNowDataSourceKey(){
        if(LCNDataSourceLocal.current()==null){
            return "default";
        }
        return LCNDataSourceLocal.current().getKey();
    }

    @Override
    public boolean hasGroup(String group) {
        return super.hasGroup(getNowDataSourceKey()+group);
    }

    @Override
    protected Connection createLcnConnection(Connection connection, TxTransactionLocal txTransactionLocal) {
        nowCount++;
        if(txTransactionLocal.isHasStart()){
            LCNStartConnection lcnStartConnection = new LCNStartConnection(connection,subNowCount);
            logger.info("get new start connection - > "+txTransactionLocal.getGroupId());
            pools.put(getNowDataSourceKey()+txTransactionLocal.getGroupId(), lcnStartConnection);
            txTransactionLocal.setHasConnection(true);
            return lcnStartConnection;
        }else {
            LCNDBConnection lcn = new LCNDBConnection(connection, dataSourceService, subNowCount);
            logger.info("get new connection ->" + txTransactionLocal.getGroupId());
            pools.put(getNowDataSourceKey()+txTransactionLocal.getGroupId(), lcn);
            txTransactionLocal.setHasConnection(true);
            return lcn;
        }
    }

    public void addDataSource(String key, DataSource dataSource){
        dataSourceMap.put(key,dataSource);
        logger.info("add datasource of "+key);
    }

    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
        addDataSource("default",dataSource);
        logger.info("load default datasource.");
    }

    @Override
    protected DataSource getDataSource() {
        logger.info("getDataSource--->");
        if(LCNDataSourceLocal.current()==null){
            return super.getDataSource();
        }else{
            String key = LCNDataSourceLocal.current().getKey();
            logger.info("get datasource of "+key);
            return dataSourceMap.get(key);
        }
    }
}
