package model;

import java.util.ArrayList;

import model.vo.CustomerVO;

public interface RentDao {
	
	// 대여
	public void rentVideo(String tel, int vNum) throws Exception;

	// 반납
	public void returnVideo(int vNum) throws Exception;

	// 미납목록검색
	public ArrayList selectList() throws Exception;
	
	// 이름 넣기
	public String videoName(String tel) throws Exception;


}
