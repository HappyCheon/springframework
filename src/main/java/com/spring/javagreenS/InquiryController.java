package com.spring.javagreenS;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.pagination.PageProcess;
import com.spring.javagreenS.pagination.PageVO;
import com.spring.javagreenS.service.InquiryService;
import com.spring.javagreenS.vo.InquiryVO;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	@Autowired
	InquiryService inquiryService;
	
	@Autowired
	PageProcess pageProcess;
	
	@RequestMapping(value = "/inquiryList", method = RequestMethod.GET)
	public String inquiryListGet(HttpSession session,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			Model model) {
		String mid = (String) session.getAttribute("sMid");
		PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "inquiry", part, mid);
		System.out.println("pageVo : " + pageVo);
		List<InquiryVO> vos = inquiryService.getInquiryList(pageVo.getStartIndexNo(), pageSize, part, mid);
		
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
		model.addAttribute("part", part);
		
		return "inquiry/inquiryList";
	}
	
	@RequestMapping(value = "/inquiryInput", method = RequestMethod.GET)
	public String inquiryInputGet() {
		return "inquiry/inquiryInput";
	}
	
	@RequestMapping(value = "/inquiryInput", method = RequestMethod.POST)
	public String inquiryInputPost(MultipartFile file, InquiryVO vo) {
		inquiryService.setInquiryInput(file, vo);
		
		return "redirect:/msg/inquiryInputOk";
	}
}
