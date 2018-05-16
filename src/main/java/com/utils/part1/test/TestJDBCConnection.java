package com.utils.part1.test;

import com.utils.part1.jdbc.ConnectionPool;
import com.utils.part1.jdbc.DBbean;
import com.utils.part1.jdbc.PropertiesUtil;

public class TestJDBCConnection {
	public static void main(String[] args) {
		DBbean dbBean=getDBbean();
		ConnectionPool pool=new ConnectionPool(dbBean);
	}
	
	private static DBbean getDBbean() {
		DBbean dbBean=new DBbean();
		dbBean.setUrl(PropertiesUtil.getValue("url"));
		dbBean.setUserName(PropertiesUtil.getValue("userName"));
		dbBean.setPassword(PropertiesUtil.getValue("password"));
		dbBean.setDriverName(PropertiesUtil.getValue("driverName"));
		dbBean.setInitConnections(Integer.parseInt(PropertiesUtil.getValue("initConnections")));
		dbBean.setMaxConnections(Integer.parseInt(PropertiesUtil.getValue("maxConnections")));
		dbBean.setMaxActiveConnections(Integer.parseInt(PropertiesUtil.getValue("maxActiveConnections")));
		dbBean.setConnTimeOut(Long.parseLong(PropertiesUtil.getValue("connTimeOut")));
		dbBean.setLazyCheck(Long.parseLong(PropertiesUtil.getValue("lazyCheck")));
		dbBean.setPeriodCheck(Long.parseLong(PropertiesUtil.getValue("periodCheck")));
		return dbBean;
	}
}
