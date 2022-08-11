package com.spring.javagreenS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.QnaVO;

public interface QnaDAO {

	public String getEmail(@Param("mid") String mid);

	public int getCountIdx();

	public int getMaxIdx();

	public void qnaInputOk(@Param("vo") QnaVO vo);
	
	public int totRecCnt();

	public List<QnaVO> getQnaList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public QnaVO getQnaContent(@Param("idx") int idx);

}
