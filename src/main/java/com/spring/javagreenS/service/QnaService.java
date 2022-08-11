package com.spring.javagreenS.service;

import java.util.List;

import com.spring.javagreenS.vo.QnaVO;

public interface QnaService {

	public String getEmail(String mid);

	public int getMaxIdx();

	public void qnaInputOk(QnaVO vo);

	public List<QnaVO> getQnaList(int startIndexNo, int pageSize);

	public QnaVO getQnaContent(int idx);

}
