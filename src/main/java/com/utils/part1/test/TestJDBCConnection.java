package com.utils.part1.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.utils.part1.jdbc.ConnectionPool;
import com.utils.part1.jdbc.DBbean;
import com.utils.part1.jdbc.PropertiesUtil;

public class TestJDBCConnection {
	public static void main(String[] args) throws SQLException, InterruptedException {
		DBbean dbBean=getDBbean();
		ConnectionPool pool=new ConnectionPool(dbBean);
		Connection conn=pool.getConnection();
		String sql="select cus_nm,usr_no from t_urm_pinf where reg_mbl='17348522785'";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs=stmt.executeQuery();
		while(rs.next()){
			System.out.println(rs.getString("cus_nm"));
			System.out.println(rs.getString("usr_no"));
		}
		Thread.sleep(10000);
		pool.releaseConn(conn);
		System.out.println("releaseConn");
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
