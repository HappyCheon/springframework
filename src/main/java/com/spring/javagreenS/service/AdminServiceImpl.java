package com.spring.javagreenS.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javagreenS.dao.AdminDAO;
import com.spring.javagreenS.vo.InquiryReplyVO;
import com.spring.javagreenS.vo.InquiryVO;
import com.spring.javagreenS.vo.QrCodeVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDAO;
	/*
	@Override
	public String[] getQrCode() {
		return adminDAO.getQrCode();
	}
	*/
	@Override
	public ArrayList<QrCodeVO> getQrCodeCondition(int startIndexNo, int pageSize, String startJumun, String endJumun) {
		return adminDAO.getQrCodeCondition(startIndexNo, pageSize, startJumun, endJumun);
	}
	
	@Override
	public void setQrCodeDelete(String uploadPath, QrCodeVO vo) {
		String realPathFile = uploadPath + vo.getQrCode() + ".png";
		new File(realPathFile).delete();
		
		adminDAO.setQrCodeDelete(vo);
	}

	@Override
	public void setQrCodeSelectDelete(String uploadPath, int idx) {
		String qrCodeName = adminDAO.getQrCodeName(idx);
		String realPathFile = uploadPath + qrCodeName + ".png";
		new File(realPathFile).delete();
		
		adminDAO.setQrCodeSelectDelete(idx);
	}

	@Override
	public ArrayList<QrCodeVO> getInquiryListAdmin(int startIndexNo, int pageSize, String part) {
		return adminDAO.getInquiryListAdmin(startIndexNo, pageSize, part);
	}

	@Override
	public InquiryVO getInquiryContent(int idx) {
		return adminDAO.getInquiryContent(idx);
	}

	@Override
	public void setInquiryInputAdmin(InquiryReplyVO vo) {
		adminDAO.setInquiryInputAdmin(vo);
	}

	@Override
	public InquiryReplyVO getInquiryReplyContent(int idx) {
		return adminDAO.getInquiryReplyContent(idx);
	}

	@Override
	public void setInquiryReplyUpdate(InquiryReplyVO reVo) {
		adminDAO.setInquiryReplyUpdate(reVo);
	}

	@Override
	public void setInquiryReplyDelete(int reIdx) {
		adminDAO.setInquiryReplyDelete(reIdx);
	}

	@Override
	public void setInquiryUpdateAdmin(int inquiryIdx) {
		adminDAO.setInquiryUpdateAdmin(inquiryIdx);
	}

	
	
}
