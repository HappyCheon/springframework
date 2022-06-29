package com.spring.javagreenS.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.javagreenS.dao.PdsDAO;
import com.spring.javagreenS.vo.PdsVO;

@Service
public class PdsServiceImpl implements PdsService {
	
	@Autowired
	PdsDAO pdsDAO;

	@Override
	public ArrayList<PdsVO> getPdsList(int startIndexNo, int pageSize, String part) {
		return pdsDAO.getPdsList(startIndexNo, pageSize, part);
	}

	@Override
	public void setPdsInput(MultipartHttpServletRequest mfile, PdsVO vo) {
		try {
			List<MultipartFile> fileList = mfile.getFiles("file");
			String oFileNames = "";
			String sFileNames = "";
			int fileSizes = 0;
			
			for(MultipartFile file : fileList) {
				String oFileName = file.getOriginalFilename();
				String sFileName = saveFileName(oFileName);	// 서버에 저장될 파일명을 결정해준다.
				
				writeFile(file, sFileName);		// 서버에 파일 저장처리하기
				
				oFileNames += oFileName + "/";
				sFileNames += sFileName + "/";
				fileSizes += file.getSize();
			}
			vo.setFName(oFileNames);
			vo.setFSName(sFileNames);
			vo.setFSize(fileSizes);
			
			pdsDAO.setPdsInput(vo);		// 서버에 파일 저장완료후 DB에 내역을 저장시켜준다.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeFile(MultipartFile file, String sFileName) throws IOException {
		byte[] data = file.getBytes();
		
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		
		FileOutputStream fos = new FileOutputStream(uploadPath + sFileName);
		fos.write(data);
		fos.close();
	}

	// 저장되는 파일명의 중복을 방지하기위해 새로 파일명을 만들어준다.
	private String saveFileName(String oFileName) {
		String fileName = "";
		
		Calendar cal = Calendar.getInstance();
		fileName += cal.get(Calendar.YEAR);
		fileName += cal.get(Calendar.MONTH);
		fileName += cal.get(Calendar.DATE);
		fileName += cal.get(Calendar.HOUR);
		fileName += cal.get(Calendar.MINUTE);
		fileName += cal.get(Calendar.SECOND);
		fileName += cal.get(Calendar.MILLISECOND);
		fileName += "_" + oFileName;
		
		return fileName;
	}

	@Override
	public void setPdsDownNum(int idx) {
		pdsDAO.setPdsDownNum(idx);
	}

	@Override
	public PdsVO getPdsContent(int idx) {
		return pdsDAO.getPdsContent(idx);
	}

	@Override
	public void setPdsDelete(PdsVO vo) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String realPath = request.getSession().getServletContext().getRealPath("/resources/data/pds/");
		String[] fSNames = vo.getFSName().split("/");
		
		for(int i=0; i<fSNames.length; i++) {
			String realPathFile = realPath + fSNames[i];
			new File(realPathFile).delete();
		}
		
		pdsDAO.setPdsDelete(vo.getIdx());
	}
}
