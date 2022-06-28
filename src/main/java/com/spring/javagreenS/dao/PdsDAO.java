package com.spring.javagreenS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.PdsVO;

public interface PdsDAO {
	
	public int totRecCnt(@Param("part") String part);

	public ArrayList<PdsVO> getPdsList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public void setPdsInput(@Param("vo") PdsVO vo);

	public void setPdsDownNum(@Param("idx") int idx);

	public PdsVO getPdsContent(@Param("idx") int idx);

}
