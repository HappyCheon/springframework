package com.spring.javagreenS;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javagreenS.pagination.PageProcess;
import com.spring.javagreenS.pagination.PageVO;
import com.spring.javagreenS.service.AdminService;
import com.spring.javagreenS.service.MemberService;
import com.spring.javagreenS.vo.MemberVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	String msgFlag = "";
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	MemberService memberService;
	
	
	@RequestMapping(value="/adMenu", method = RequestMethod.GET)
	public String adMenuGet() {
		return "admin/adMenu";
	}
	
	@RequestMapping(value="/adLeft", method = RequestMethod.GET)
	public String adLeftGet() {
		return "admin/adLeft";
	}
	
	@RequestMapping(value="/adContent", method = RequestMethod.GET)
	public String adContentGet(Model model) {
		return "admin/adContent";
	}
	
	@RequestMapping(value="/dbShop/dbCategory", method = RequestMethod.GET)
	public String dbCategoryGet(Model model) {
		return "admin/dbShop/dbCategory";
	}
	
	@RequestMapping(value="/adMemberList", method = RequestMethod.GET)
	public String adMemberListGet(
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
			@RequestParam(name="level", defaultValue="99", required=false) int level,
			@RequestParam(name="mid", defaultValue="", required=false) String mid,
			Model model) {
	  PageVO pageVo = null;
	  if(mid.equals("")) {
	  	pageVo = pageProcess.totRecCnt(pag, pageSize, "adminMemberList", "", level+"");	// 전체자료 갯수 검색
	  }
	  else {
	  	pageVo = pageProcess.totRecCnt(pag, pageSize, "adminMemberList", mid, "");	// 개별자료 검색
	  }
	  
	  ArrayList<MemberVO> vos = new ArrayList<MemberVO>();
	  if(mid.equals("")) {	// level로 검색처리
	  	vos = memberService.getAdminMemberLevelList(pageVo.getStartIndexNo(), pageSize, level);
	  }
	  else {								// 개별 id로 검색처리
	  	vos = memberService.getAdminMemberMidList(pageVo.getStartIndexNo(), pageSize, mid);
	  }
	  //System.out.println("pageVo : " +pageVo);
		model.addAttribute("vos", vos);
		model.addAttribute("level", level);
		model.addAttribute("mid", mid);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("totRecCnt", pageVo.getTotRecCnt());
		
		return "admin/member/adMemberList";
	}
	
	@ResponseBody
	@RequestMapping(value="/adMemberLevel", method = RequestMethod.POST)
	public String adMemberLevelPost(int idx, int level) {
		memberService.setAdminLevelUpdate(idx, level);
		return "";
	}
	
	
}

