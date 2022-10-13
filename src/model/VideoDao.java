package model;

import java.util.ArrayList;

import model.vo.VideoVO;


public interface VideoDao {
	public void 		insertVideo(VideoVO vo, int count) throws Exception;	// 비디오 입고
	public ArrayList 	searchVideo(int idx , String word) throws  Exception;	// 비디오 검색현황 출력
	public VideoVO 		sleleteByVnum(int vNum) throws Exception;	// 비디오 현황 클릭시 비디오번호로 내용 출력
	public void			modifyVideo(VideoVO vo) throws Exception;
	public int	 		deleteVideo(int vNum) throws  Exception;  			// 비디오 삭제
}
