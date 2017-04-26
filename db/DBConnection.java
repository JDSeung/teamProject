package com.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBConnection {
	//커넥션 풀로부터 Connection객체를 얻어냄 : DB 연동의 쿼리문을 수행
	public static Connection getConnection() throws Exception {
		Context initCtx = new InitialContext();
		Context evnCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource) evnCtx.lookup("jdbc/Oracle11g");
		Connection comm = ds.getConnection();
		return comm;
	}

	public static void disConnect(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.print("disConnect 에러 :");
			e.printStackTrace();
		}
	}
}
