package com.spring.javagreenS.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.InquiryVO;

public interface InquiryDAO {

	public int totRecCnt(@Param("part") String part, @Param("mid") String mid);

	public List<InquiryVO> getInquiryList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part, @Param("mid") String mid);

	public void setInquiryInputOk(@Param("vo") InquiryVO vo);

}
