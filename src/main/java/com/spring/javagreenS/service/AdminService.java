package com.spring.javagreenS.service;

import java.util.ArrayList;

import com.spring.javagreenS.vo.InquiryReplyVO;
import com.spring.javagreenS.vo.InquiryVO;
import com.spring.javagreenS.vo.QrCodeVO;

public interface AdminService {

//	public String[] getQrCode();

	public ArrayList<QrCodeVO> getQrCodeCondition(int startIndexNo, int pageSize, String startJumun, String endJumun);

	public void setQrCodeDelete(String uploadPath, QrCodeVO vo);

	public void setQrCodeSelectDelete(String uploadPath, int idx);

	public ArrayList<QrCodeVO> getInquiryListAdmin(int startIndexNo, int pageSize, String part);

	public InquiryVO getInquiryContent(int idx);

	public void setInquiryInputAdmin(InquiryReplyVO vo);

	public InquiryReplyVO getInquiryReplyContent(int idx);

	public void setInquiryReplyUpdate(InquiryReplyVO reVo);

	public void setInquiryReplyDelete(int reIdx);

	public void setInquiryUpdateAdmin(int inquiryIdx);

}
