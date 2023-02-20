package com.yedam.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DAO {
	//DAO -> Data Access Object
	//Java<-> DB 연결할 때 쓰는 객체
	//JDBC를 통해서 JAVA<-> DB가 연결 한다.
	//OJDBC<- 오라클사에서 제공해주는 JDBC;를 사용
	
	//사용할 객체 4가지
	//1. Java-> DB 연결할 때 쓰는 객체(Connection)
	protected Connection conn = null;
	
	//2. Query문(SQL)을 가지고 해당 질의문 실행하는 객체
	protected PreparedStatement pstmt = null;
	
	//3. Query문(SQL)을 가지고 해당 질의문 실행하는 객체
	protected Statement stmt=null;
	
	//4. Select(조회) 결과 값을 반환 받는 객체 <- 즉 데이터 넘겨 받을 객체
	protected ResultSet rs = null;
	
	//JAVA->연결(conn)->Select(조회)->결과값(ResultSet)
	//해제할땐 역순
	
	//hr 계정 접속[DB에 연결할 구문]
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String id = "hr";
	String pw = "hr";
	//chatGPT>>? 성인 인증 무엇
	
	//DB연결메소드
	public void conn() {
		try {
			//1.드라이버 로딩
			Class.forName(driver);
			//2.DB연결
			conn = DriverManager.getConnection(url,id,pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//DB 연결 해제 [conn 끊었을 경우 쿼리문 휴지상태->충돌위험]
	public void disconn() {
		try {
		if(rs !=null) {
			rs.close();
		}
		if(stmt !=null) {
			stmt.close();
		}
		if(pstmt !=null) {
			pstmt.close();
		}
		if(conn !=null) {
			conn.close();
		}
	}catch(Exception e) {
			e.printStackTrace();
	}
}
	
	
}
