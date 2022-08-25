package com.spring.javagreenS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.InquiryReplyVO;
import com.spring.javagreenS.vo.InquiryVO;
import com.spring.javagreenS.vo.QrCodeVO;

public interface AdminDAO {

//	public String[] getQrCode();
	
	public int totRecQrCodeTicket(@Param("startJumun") String startJumun, @Param("endJumun")  String endJumun);

	public ArrayList<QrCodeVO> getQrCodeCondition(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("startJumun") String startJumun, @Param("endJumun")  String endJumun);

	public void setQrCodeDelete(@Param("vo") QrCodeVO vo);

	public void setQrCodeSelectDelete(@Param("idx") int idx);

	public String getQrCodeName(@Param("idx") int idx);

	public int totRecCntAdmin(@Param("part") String part);

	public ArrayList<QrCodeVO> getInquiryListAdmin(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("part") String part);

	public InquiryVO getInquiryContent(@Param("idx") int idx);

	public void setInquiryInputAdmin(@Param("vo") InquiryReplyVO vo);

	public InquiryReplyVO getInquiryReplyContent(@Param("idx") int idx);

	public void setInquiryReplyUpdate(@Param("reVo") InquiryReplyVO reVo);

	public void setInquiryReplyDelete(@Param("reIdx") int reIdx);

	public void setInquiryUpdateAdmin(@Param("inquiryIdx") int inquiryIdx);

}
