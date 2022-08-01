package com.spring.javagreenS.service;

import java.util.ArrayList;

import com.spring.javagreenS.vo.QrCodeVO;

public interface AdminService {

//	public String[] getQrCode();

	public ArrayList<QrCodeVO> getQrCodeCondition(int startIndexNo, int pageSize, String startJumun, String endJumun);

	public void setQrCodeDelete(String uploadPath, QrCodeVO vo);

	public void setQrCodeSelectDelete(String uploadPath, int idx);

}
