package com.spring.javagreenS.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.spring.javagreenS.vo.MemberVO;

public interface MemberDAO {

	public MemberVO getMemIdCheck(@Param("mid") String mid);

	public MemberVO getNickNameCheck(@Param("nickName")  String nickName);

	public void setMemInputOk(@Param("vo") MemberVO vo);

	public void setMemberVisitProcess(@Param("mid") String mid, @Param("todayCnt") int todayCnt, @Param("newPoint") int newPoint);

	public ArrayList<MemberVO> getMemList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize);

	public void setMemUpdateOk(@Param("vo") MemberVO vo);

	public void setMemDeleteOk(@Param("mid") String mid);

	public void setPwdChange(@Param("mid") String mid, @Param("pwd") String pwd);

	public MemberVO getMemIdEmailCheck(@Param("mid") String mid, @Param("toMail") String toMail);

	public int totRecCnt();

	public MemberVO getMemEmailCheck(@Param("email") String email);

	public void setKakaoMemberInputOk(@Param("mid") String mid, @Param("pwd") String pwd, @Param("nickName") String nickName, @Param("email") String email);

	public String getTodayVisitDate();

	public void setTodayVisitCountInsert();

	public void setTodayVisitCountUpdate(@Param("strToday") String strToday);

	public int totRecCntAdminMemberList(@Param("level") int level);
	
	public int totRecCntAdminMemberMidList(@Param("mid") String mid);

	public ArrayList<MemberVO> getAdminMemberLevelList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("level") int level);

	public ArrayList<MemberVO> getAdminMemberMidList(@Param("startIndexNo") int startIndexNo, @Param("pageSize") int pageSize, @Param("mid") String mid);

	public void setAdminLevelUpdate(@Param("idx") int idx, @Param("level") int level);

}
