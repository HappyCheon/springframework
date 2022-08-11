package com.spring.javagreenS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javagreenS.service.NotifyService;
import com.spring.javagreenS.vo.NotifyVO;

@Controller
@RequestMapping("/notify")
public class NotifyController {
	String msgFlag = "";
	
	@Autowired
	NotifyService notifyService;
	
	@RequestMapping(value="/nList", method=RequestMethod.GET)
	public String nListGet(Model model) {
		List<NotifyVO> vos = notifyService.getNotifyList();
		model.addAttribute("vos", vos);
		
		return "admin/notify/nList";
	}
	
	@RequestMapping(value="/nInput", method=RequestMethod.GET)
	public String nInputGet() {
		return "admin/notify/nInput";
	}

	@RequestMapping(value="/nInput", method=RequestMethod.POST)
	public String nInputPost(NotifyVO vo) {
		notifyService.nInput(vo);
		msgFlag = "notifyInputOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	@ResponseBody
	@RequestMapping(value="/nDelete", method=RequestMethod.GET)
	public String nDeleteGet(int idx) {
		notifyService.setDelete(idx);
		return "1";
	}
	
	@RequestMapping(value="/nUpdate", method=RequestMethod.GET)
	public String nUpdateGet(int idx, Model model) {
		NotifyVO vo = notifyService.getNUpdate(idx);
		model.addAttribute("vo", vo);
		
		return "admin/notify/nUpdate";
	}

	@RequestMapping(value="/nUpdate", method=RequestMethod.POST)
	public String nUpdatePost(NotifyVO vo) {
		notifyService.setNUpdateOk(vo);
		msgFlag = "notifyUpdateOk";
		
		return "redirect:/msg/" + msgFlag;
	}
	
	// 공지사항 팝업을 호출하는 메소드
	@RequestMapping(value="/popup", method=RequestMethod.GET)
	public String popupGet(int idx, Model model) {
		NotifyVO vo = notifyService.getNUpdate(idx);  // idx로 검색된 공지사항의 정보를 가져온다.(가져온 정보는 무조건 popupSw가 'Y'로 되어 있다)
		model.addAttribute("vo", vo);
		return "admin/notify/popup";
	}
	
	// 여러개의 리턴값을 반환하는 경우....
	@ResponseBody
	@RequestMapping(value="/popupCheck", method=RequestMethod.GET)
	public Map<Object, Object> popupCheck(int idx, String popupSw) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		notifyService.setpopupCheckUpdate(idx, popupSw);
		
		map.put("idx", idx);
		map.put("sw", popupSw);
		
		return map;
	}
	
	// 공지사항 리스트 보기(사이트에 방문한 모든 사용자)
	@RequestMapping(value="/mnList", method=RequestMethod.GET)
	public String mnListGet(Model model) {
		List<NotifyVO> vos = notifyService.getNotifyList();
		model.addAttribute("vos", vos);
		return "notify/mnList";
	}
	
	// 공지사항 팝업으로 보기
	@RequestMapping(value="/notifyView", method=RequestMethod.GET)
	public String mnNotifyViewGet(int idx, Model model) {
		NotifyVO vo = notifyService.getNofifyView(idx);
		model.addAttribute("vo", vo);
		
		return "notify/notifyView";
	}
}
