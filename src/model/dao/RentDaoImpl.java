package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import model.RentDao;


public class RentDaoImpl implements RentDao{
	
	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER = "Park";
	final static String PASS = "1234";
	
	Connection con;
	
	public RentDaoImpl() throws Exception{
		// 1. 드라이버로딩
		
				
	}

	// 비디오 대여 클릭시 대여 정보 입력
	public void rentVideo(String tel, int vNum) throws Exception {
		con = null;
		PreparedStatement ps = null; 
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장 만들기
		String sql = "INSERT INTO RENTAL (r_number, tel, v_number, v_day, v_ban)  "
				+ "  VALUES(seq_ren_num.nextval,?,?,sysdate ,'N') ";
		
		// 4. sql 전송객체 (PreparedStatement)	
		ps = con.prepareStatement(sql);

		ps.setString(1, tel);
		ps.setInt(2, vNum);



		// 5. sql 전송
		ps.executeUpdate();
		} finally {
			// 6. 닫기 
			ps.close();
			con.close();
		}

		
	}

	// 비디오 반납
	public void returnVideo(int vNum) throws Exception {
		con = null;
		PreparedStatement ps = null; 
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
		
		// 3. sql 문장 만들기
		String sql = "UPDATE RENTAL SET v_ban = 'Y' "
				+ "  WHERE v_number = ? AND v_ban = 'N' ";
				
		
		// 4. sql 전송객체 (PreparedStatement)	
		ps = con.prepareStatement(sql);

		
		ps.setInt(1, vNum);



		// 5. sql 전송
		ps.executeUpdate();
		} finally {
			// 6. 닫기 
			ps.close();
			con.close();
		}
		
	}

	// 비디오 미납목록 검색 
	public ArrayList selectList() throws Exception {
		ArrayList data = new ArrayList();
		con = null;
		PreparedStatement ps = null; // 블럭안에서 선언시 해당블럭에서만 사용 가능하므로 블럭밖에서 선언
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장
			String sql = "select v.v_number v_number,v.v_name v_name, m.name name, m.tel tel, r.v_day+3 v_day, 'N' v_ban  "
					+ "  from member m inner join  rental r  "
					+ "  on M.tel = r.tel  "
					+ "  inner join video v   "
					+ "  on v.v_number = r.v_number   "
					+ "  where r.v_ban = 'N'  ";
			System.out.println(sql);
			// 4. 전송객체
			ps = con.prepareStatement(sql);
			System.out.println(ps);
			// 5. 전송
			rs = ps.executeQuery();
			System.out.println(rs);
			// 한사람에 대한 정보를 VO에 담기

			while (rs.next()) { // rs.next 다음행으로 넘기기위해 사용 엔터키
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("v_number"));
				temp.add(rs.getString("v_name"));
				temp.add(rs.getString("name"));
				temp.add(rs.getString("tel"));
				temp.add(rs.getString("v_day"));
				temp.add(rs.getString("v_ban"));
				data.add(temp);

			}

			return data;

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기
			rs.close();
			ps.close();
			con.close();
		}
	}

	
	
	// 고객번호 검색시 이름 나오게 
	public String videoName(String tel) throws Exception {
		// 2. 연결객체 얻어오기
				con = null;
				PreparedStatement ps = null; 
				ResultSet rs = null;
				String name = null;
				try {
					con = DriverManager.getConnection(URL, USER, PASS);
				// 3. sql 문장 만들기
				String sql = "SELECT name FROM MEMBER WHERE tel = ?";
				// 4. 전송 객체
				ps = con.prepareStatement(sql);
				ps.setString(1, tel);
				// 5. 전송 - executeQuery();
				rs = ps.executeQuery();
				
				//		전송결과를 CustomerVO 에담기
				if (rs.next()) {	// rs.next 다음행으로 넘기기위해 사용 엔터키
					name = rs.getString("name");
				} 

				return name;


		} finally {
			// 6. 닫기 
			rs.close();
			ps.close();
			con.close();
		}

		
	}
	
	
}
