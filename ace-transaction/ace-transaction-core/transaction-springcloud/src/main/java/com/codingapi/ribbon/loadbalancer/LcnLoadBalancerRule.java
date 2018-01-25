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

package com.codingapi.ribbon.loadbalancer;


import com.codingapi.tx.aop.bean.TxTransactionLocal;
import com.lorne.core.framework.utils.encode.MD5Util;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * created by foxdd 2017-12-05
 */
public class LcnLoadBalancerRule {
	
	private Logger logger = LoggerFactory.getLogger(LcnLoadBalancerRule.class);
	
	public Server proxy(List<Server> servers,Server server){

		TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
		if(txTransactionLocal==null){
			return server;
		}

		try{
			logger.info("LCNBalanceProxy - > start");

			String groupId = txTransactionLocal.getGroupId();

			//取出组件的appName
			String appName = server.getMetaInfo().getAppName();


			String key = MD5Util.md5((groupId + "_" + appName).getBytes());

			//如果只有一个可调用模块，则用当前的，且需要将数据记录到redis中
			if(servers.size() == 1){
				putServer(key, txTransactionLocal, server);
				logger.info("LCNBalanceProxy -> only one server available");
				return server;
			}

			Server oldServer =getServer(txTransactionLocal,servers,key);
			if(oldServer != null){
				logger.info("LCNBalanceProxy - > load old server ");
				return oldServer;
			}

			putServer(key, txTransactionLocal, server);
			logger.info("LCNBalanceProxy - > load new server ");

			return server;
		}finally {
			logger.info("LCNBalanceProxy - > end");
		}
	}



	private void putServer(String key,TxTransactionLocal txTransactionLocal,Server server){
		String serviceName =  server.getMetaInfo().getAppName();
		String address = server.getHostPort();

		String md5 = MD5Util.md5((address+serviceName).getBytes());

		logger.info("putServer->address->"+address+",md5-->"+md5);

		txTransactionLocal.putLoadBalance(key,md5);
	}


	private Server getServer(TxTransactionLocal txTransactionLocal, List<Server> servers, String key){
		String val = txTransactionLocal.getLoadBalance(key);
		if(StringUtils.isEmpty(val)){
			return null;
		}
		for(Server server:servers){
			String serviceName =  server.getMetaInfo().getAppName();
			String address = server.getHostPort();

			String md5 = MD5Util.md5((address+serviceName).getBytes());

			logger.info("getServer->address->"+address+",md5-->"+md5);

			if(val.equals(md5)){
				return server;
			}
		}
		return null;
	}
	
}
