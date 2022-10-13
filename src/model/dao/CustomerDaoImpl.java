package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import model.CustomerDao;
import model.vo.CustomerVO;

public class CustomerDaoImpl implements CustomerDao{
	
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER = "Park";
	final static String PASS = "1234";
	
	
	public CustomerDaoImpl() throws Exception{
	 	// 1. 드라이버로딩
		Class.forName(DRIVER); 
		System.out.println("드라이버로딩 성공");
		
	}
	
	public void insertCustomer(CustomerVO vo) throws Exception{
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO MEMBER (tel, name, addr, email, addtel)  "
				+ "  VALUES(?,?,?,?,?) ";
		
		// 4. sql 전송객체 (PreparedStatement)	
		ps = con.prepareStatement(sql);
		// ? 세팅(#)
		ps.setString(1, vo.getTel());
		ps.setString(2, vo.getName());
		ps.setString(3, vo.getAddr());
		ps.setString(4, vo.getEmail());
		ps.setString(5, vo.getAddtel());
		

		// 5. sql 전송
		ps.executeUpdate();
		} finally {
			// 6. 닫기 
			ps.close();
			con.close();
		}

	}
	
	/*
	 * 메소드명 : selectByTel
	 * 인자	: 검색할 전화번호
	 * 리턴값 : 전화번호 검색에 따른 고객정보
	 * 역할	: 사용자가 입력한 전화번호를 받아서 해당하는 고객정보를 리턴
	 */
	public CustomerVO selectByTel(String tel) throws Exception{
		CustomerVO dao = new CustomerVO();
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		// 3. sql 문장 만들기
		String sql = "SELECT * FROM MEMBER WHERE tel = ?";
		// 4. 전송 객체
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		// 5. 전송 - executeQuery();
		rs = ps.executeQuery();
		//		전송결과를 CustomerVO 에담기
		if (rs.next()) {	// rs.next 다음행으로 넘기기위해 사용 엔터키
			dao.setTel(rs.getString("tel"));
			dao.setName(rs.getString("name"));
			dao.setAddr(rs.getString("addr"));
			dao.setEmail(rs.getString("email"));
			dao.setAddtel(rs.getString("addtel"));
		// 6. 닫기
		} 
		
		return dao;
		
		} finally {
		rs.close();
		ps.close();
		con.close();
		}

		
	}
	
	
	/*
	 * 메소드명 : updateCustomer
	 * 인자	: 테이블 컬럼들
	 * 리턴값 : 수정한 정보 리턴
	 * 역할	: 사용자가 입력한 전화번호를 받아서 
	 * 			해당하는 고객정보찾아 수정후 리턴
	 */
	public int updateCustomer(CustomerVO vo) throws Exception{
		Connection con = null;
		PreparedStatement ps = null; 
		int result = 0;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3.sql 문장
			String sql = " UPDATE MEMBER SET name=?, addr=?, email=?, addtel=? WHERE tel = ? ";
			// 4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getName());
			ps.setString(2, vo.getAddr());
			ps.setString(3, vo.getEmail());
			ps.setString(4, vo.getAddtel());
			ps.setString(5, vo.getTel());

			// 5. 전송
			result = ps.executeUpdate();

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기
			ps.close();
			con.close();
		}

		return result;
	}
}
