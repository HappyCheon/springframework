package com.spring.javagreenS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javagreenS.dao.QnaDAO;
import com.spring.javagreenS.vo.QnaVO;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	QnaDAO qnaDAO;

	@Override
	public String getEmail(String mid) {
		return qnaDAO.getEmail(mid);
	}

	@Override
	public int getMaxIdx() {
		return qnaDAO.getCountIdx() == 0 ? 0 : qnaDAO.getMaxIdx();
	}

	@Override
	public void qnaInputOk(QnaVO vo) {
		qnaDAO.qnaInputOk(vo);
	}

	@Override
	public List<QnaVO> getQnaList(int startIndexNo, int pageSize) {
		return qnaDAO.getQnaList(startIndexNo, pageSize);
	}

	@Override
	public QnaVO getQnaContent(int idx) {
		return qnaDAO.getQnaContent(idx);
	}
	
}
