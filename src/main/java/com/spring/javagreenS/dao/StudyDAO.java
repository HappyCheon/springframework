package com.spring.javagreenS.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.KakaoAddressVO;
import com.spring.javagreenS.vo.OperatorVO;
import com.spring.javagreenS.vo.PersonVO;

public interface StudyDAO {

	public OperatorVO getOperator(@Param("oid") String oid);

	public String getOperatorHashKey(@Param("keyIdx") int keyIdx);

	public void setOperatorInputOk(@Param("vo") OperatorVO vo);

	public ArrayList<OperatorVO> getOperatorList();

	public void setOperatorDelete(@Param("oid") String oid);

	public ArrayList<OperatorVO> getOperatorVos(@Param("oid") String oid);

	public void setPersonInput(@Param("vo") PersonVO vo);

	public ArrayList<PersonVO> getPersonList();

	public KakaoAddressVO getAddressName(@Param("address") String address);

	public void setAddressName(@Param("vo") KakaoAddressVO vo);

	public List<KakaoAddressVO> getAddressNameList();

	public void kakaoEx2Delete(@Param("address") String address);

}
