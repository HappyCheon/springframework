package com.spring.javagreenS.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.common.ProjectSupport;
import com.spring.javagreenS.dao.InquiryDAO;
import com.spring.javagreenS.vo.InquiryVO;

@Service
public class InquiryServiceImpl implements InquiryService {
	@Autowired
	InquiryDAO inquiryDAO;

	@Override
	public List<InquiryVO> getInquiryList(int startIndexNo, int pageSize, String part, String mid) {
		return inquiryDAO.getInquiryList(startIndexNo, pageSize, part, mid);
	}

	@Override
	public void setInquiryInput(MultipartFile file, InquiryVO vo) {
		// 사진작업 처리후 DB에 저장
		try {
			String oFileName = file.getOriginalFilename();
			if(!oFileName.equals("") || oFileName != null) {
				UUID uid = UUID.randomUUID();
				String saveFileName = uid + "_" + oFileName;
				ProjectSupport ps = new ProjectSupport();
				ps.writeFile(file, saveFileName,"inquiry");
				vo.setFName(oFileName);
				vo.setFSName(saveFileName);
				System.out.println("vo : " + vo);
			}
			inquiryDAO.setInquiryInputOk(vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
