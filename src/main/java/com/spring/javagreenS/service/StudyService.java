package com.spring.javagreenS.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.vo.KakaoAddressVO;
import com.spring.javagreenS.vo.OperatorVO;
import com.spring.javagreenS.vo.PersonVO;

public interface StudyService {

	public OperatorVO getOperator(String oid);

	public void setOperatorInputOk(OperatorVO vo);

	public ArrayList<OperatorVO> getOperatorList();

	public void setOperatorDelete(String oid);

	public String setOperatorSearch(OperatorVO vo);

	public String[] getCityStringArr(String dodo);

	public ArrayList<String> getCityArrayListStr(String dodo);

	public ArrayList<OperatorVO> getOperatorVos(String oid);

	public int fileUpload(MultipartFile fName);

	public void setPersonInput(PersonVO vo);

	public ArrayList<PersonVO> getPersonList();

	public void getCalendar();

	public KakaoAddressVO getAddressName(String address);

	public void setAddressName(KakaoAddressVO vo);

	public List<KakaoAddressVO> getAddressNameList();

	public void kakaoEx2Delete(String address);

}
