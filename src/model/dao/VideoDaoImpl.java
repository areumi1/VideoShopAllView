package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.VideoDao;
import model.vo.VideoVO;

public class VideoDaoImpl implements VideoDao {

	final static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	final static String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	final static String USER = "Park";
	final static String PASS = "1234";

	public VideoDaoImpl() throws Exception {

		// 1. 드라이버로딩
		Class.forName(DRIVER);
		System.out.println("드라이버로딩 성공");

	}

	public void insertVideo(VideoVO vo, int count) throws Exception {
		// 2. Connection 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3. sql 문장 만들기

			String sql = "INSERT INTO VIDEO (v_number, v_type, v_name, v_director, v_actor, v_exp )  "
					+ "  VALUES(seq_v_num.nextval, ?,?,?,?,?)";
			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);

			ps.setString(1, vo.getV_type());
			ps.setString(2, vo.getV_name());
			ps.setString(3, vo.getV_director());
			ps.setString(4, vo.getV_actor());
			ps.setString(5, vo.getV_exp());

			// 5. sql 전송
			for (int i = 0; i < count; i++) {
				ps.executeUpdate();
			}

		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}

	} // end of insertVideo


	public ArrayList searchVideo(int idx, String word) throws Exception {
		ArrayList data = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null; // 블럭안에서 선언시 해당블럭에서만 사용 가능하므로 블럭밖에서 선언
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			// 3. sql 문장
			String[] colNames = { "v_name", "v_director" };
			String sql = "SELECT v_number, v_name, v_director, v_actor FROM VIDEO  " + "  WHERE " + colNames[idx]
					+ " like '%" + word + "%' ";
			System.out.println(sql);
			// 4. 전송객체
			ps = con.prepareStatement(sql);

			// 5. 전송
			rs = ps.executeQuery();
			// 한사람에 대한 정보를 VO에 담기

			while (rs.next()) { // rs.next 다음행으로 넘기기위해 사용 엔터키
				ArrayList temp = new ArrayList();
				temp.add(rs.getInt("v_number"));
				temp.add(rs.getString("v_name"));
				temp.add(rs.getString("v_director"));
				temp.add(rs.getString("v_actor"));
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

	/*
	 * 메소드명 : selectByNum 
	 * 인자 : 비디오번호 
	 * 리턴값 : 비디오정보 
	 * 역할 : 비디오번호를 넘겨받아 해당 비디오번호의 비디오정보를 출력
	 */
	public VideoVO sleleteByVnum(int vNum) throws Exception {

		VideoVO vo = new VideoVO();
		ArrayList data = new ArrayList();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			String sql = "SELECT * FROM VIDEO WHERE v_number = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setInt(1, vNum);

			// 5. sql 전송
			rs = ps.executeQuery();
			if (rs.next()) {	// rs.next 다음행으로 넘기기위해 사용 엔터키
				vo.setV_number(rs.getInt("v_number"));
				vo.setV_name(rs.getString("v_name"));
				vo.setV_type(rs.getString("v_type"));
				vo.setV_director(rs.getString("v_director"));
				vo.setV_actor(rs.getString("v_actor"));
				vo.setV_exp(rs.getString("v_exp"));
			}

		return vo;
		
		} finally {
			// 6. 닫기
			rs.close();
			ps.close();
			con.close();
		}
		
	}

	// 수정
	public void modifyVideo(VideoVO vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection(URL, USER, PASS);

			String sql = "UPDATE VIDEO SET v_name = ?, v_type = ?, v_director = ?, v_actor = ?, v_exp = ? "
					+ "  WHERE v_number = ?";

			// 4. sql 전송객체 (PreparedStatement)
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getV_name());
			ps.setString(2, vo.getV_type());
			ps.setString(3, vo.getV_director());
			ps.setString(4, vo.getV_actor());
			ps.setString(5, vo.getV_exp());
			ps.setInt(6, vo.getV_number());

			// 5. sql 전송
			ps.executeUpdate();
			
		
		} finally {
			// 6. 닫기
			ps.close();
			con.close();
		}


	}

	// 삭제
	public int deleteVideo(int vNum) throws Exception {
		// 2. 연결객체 얻어오기
		Connection con = null;
		PreparedStatement ps = null; 
		int de = 0;
		
		try {
			con = DriverManager.getConnection(URL, USER, PASS);
			// 3.sql 문장
			String sql = "DELETE FROM VIDEO  WHERE v_number = ?";
			// 4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, vNum);
			// 5. 삭제
			de = ps.executeUpdate();

		} finally {
			// 6. 닫기 무조건 finally 안에 넣어주기

			ps.close();
			con.close();
		}
		return de; 

	}
	
	
	

}
