package com.spring.javagreenS.service;

import java.util.List;

import com.spring.javagreenS.vo.BoardVO;

public interface BoardService {

	public List<BoardVO> getBoardList(int startIndexNo, int pageSize);

	public void imgCheck(String content);

	public void setBoardInput(BoardVO vo);

	public void setReadNum(int idx);

	public BoardVO getBoardContent(int idx);

}
