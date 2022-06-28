package com.spring.javagreenS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.BoardReplyVO;
import com.spring.javagreenS.vo.BoardVO;

public interface BoardDAO {

	public int totRecCnt();

	public List<BoardVO> getBoardList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public void setBoardInput(@Param("vo") BoardVO vo);

	public void setReadNum(@Param("idx") int idx);

	public BoardVO getBoardContent(@Param("idx") int idx);

	public int totSearchRecCnt(@Param("search") String search, @Param("searchString") String searchString);

	public List<BoardVO> getBoardSearch(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("search") String search, @Param("searchString") String searchString);

	public ArrayList<BoardVO> getPreNext(@Param("idx") int idx);

	public int getMinIdx();

	public void setBoardDelete(@Param("idx") int idx);

	public void setBoardUpdate(@Param("vo") BoardVO vo);

	public String maxLevelOrder(@Param("boardIdx") int boardIdx);

	public void setBoardReplyInput(@Param("replyVo") BoardReplyVO replyVo);

	public ArrayList<BoardReplyVO> getBoardReply(@Param("idx") int idx);

	public void levelOrderPlusUpdate(@Param("replyVo") BoardReplyVO replyVo);

	public void setBoardReplyInput2(@Param("replyVo") BoardReplyVO replyVo);

	public void setBoardReplyDeleteOk(@Param("idx") int idx);

}
