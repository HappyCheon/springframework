package com.spring.javagreenS;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.javagreenS.service.ScheduleService;
import com.spring.javagreenS.vo.ScheduleVO;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	String msgFlag = "";
	
	@Autowired
	ScheduleService scheduleService;
	
	@RequestMapping(value="/schedule", method=RequestMethod.GET)
	public String scheduleGet() {
		scheduleService.getSchedule();
		return "schedule/schedule";
	}
	
	@RequestMapping(value="/scMenu", method=RequestMethod.GET)
	public String scMenuGet(HttpSession session, String ymd, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<ScheduleVO> vos = scheduleService.getScMenu(mid, ymd);
		
		model.addAttribute("vos", vos);
		model.addAttribute("ymd", ymd);
		model.addAttribute("scheduleCnt", vos.size());
		return "schedule/scMenu";
	}
	
	@ResponseBody
	@RequestMapping(value="/scheduleInputOk", method=RequestMethod.POST)
	public String scheduleInputOkPost(ScheduleVO vo) {
		scheduleService.scheduleInputOk(vo);
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/scheduleDeleteOk", method=RequestMethod.GET)
	public String scheduleDeleteOkGet(int idx) {
		scheduleService.scheduleDeleteOk(idx);
		return "";
	}

	// 일정내용 팝업창으로 보여주기
	@RequestMapping(value="/scContent", method=RequestMethod.GET)
	public String scheduleContentGet(int idx, Model model) {
		ScheduleVO vo = scheduleService.getScheduleSearch(idx);
		model.addAttribute("vo", vo);
		return "schedule/scContent";
	}
}
