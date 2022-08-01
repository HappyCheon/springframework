package com.spring.javagreenS.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javagreenS.dao.AdminDAO;
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

	
	
}
