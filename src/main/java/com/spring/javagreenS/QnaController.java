package com.spring.javagreenS;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.javagreenS.pagination.PageProcess;
import com.spring.javagreenS.pagination.PageVO;
import com.spring.javagreenS.service.QnaService;
import com.spring.javagreenS.vo.QnaVO;

@Controller
@RequestMapping("/qna")
public class QnaController {
  String msgFlag = "";
  
  @Autowired
  QnaService qnaService;
  
  @Autowired
  PageProcess pageProcess;
	
  @RequestMapping(value = "/qnaList", method = RequestMethod.GET)
  public String qnaListGet(
  		@RequestParam(name="pag", defaultValue = "1", required = false) int pag,
			@RequestParam(name="pageSize", defaultValue = "5", required = false) int pageSize,
			Model model) {
  	PageVO pageVO = pageProcess.totRecCnt(pag, pageSize, "qna", "", "");
		List<QnaVO> vos = qnaService.getQnaList(pageVO.getStartIndexNo(), pageSize);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVO", pageVO);
		
  	return "qna/qnaList";
  }
  
  // 질문글로 호출될때는 qnaSw가 'q'로, 답변글로 호출될때는 'a'로 qnaSW값에 담겨 넘어온다.
  @RequestMapping(value = "/qnaInput", method = RequestMethod.GET)
  public String qnaListGet(String qnaSw, HttpSession session, Model model) {
  	String mid = (String) session.getAttribute("sMid");
  	String email = qnaService.getEmail(mid);
  	
  	model.addAttribute("qnaSw", qnaSw);
  	model.addAttribute("email", email);
  	
  	return "qna/qnaInput";
  }
  
  // qna '글올리기'와 '답변글 올리기'에서 이곳을 모두 사용하고 있다. 
  @RequestMapping(value = "/qnaInput", method = RequestMethod.POST)
  public String qnaListPost(QnaVO vo, HttpSession session) {
  	int level = (int) session.getAttribute("sLevel");
  	
  	// 먼저 idx 설정하기
  	int newIdx = qnaService.getMaxIdx() + 1;
  	vo.setIdx(newIdx);
  	
  	// qnaIdx 설정하기
  	String qnaIdx = "";
  	if(newIdx < 10) qnaIdx = "0"+ newIdx + "_2";
  	else qnaIdx = newIdx + "_2";
  	
  	if(vo.getQnaSw().equals("a")) {  // qnaSw값과 qnaIdx값은 vo에 담겨서 넘어온다. 답변글(a)일 경우만 qnaIdx값을 편집처리한다.
  		qnaIdx = vo.getQnaIdx().split("_")[0]+"_1";
  		if(level == 0) vo.setTitle(vo.getTitle().replace("(Re)", "<font color='red'>(Re)</font>"));
  	}
  	vo.setQnaIdx(qnaIdx);
  	
  	qnaService.qnaInputOk(vo);
  	
  	return "redirect:/msg/qnaInputOk";
  }
  
  @RequestMapping(value = "/qnaContent", method = RequestMethod.GET)
  public String qnaListGet(int idx, String title, int pag, HttpSession session, Model model) {
  	String mid = (String) session.getAttribute("sMid");
  	String email = qnaService.getEmail(mid);
  	
  	QnaVO vo = qnaService.getQnaContent(idx);
  	model.addAttribute("email", email);
  	model.addAttribute("title", title);
  	model.addAttribute("pag", pag);
  	model.addAttribute("vo", vo);
  	
  	return "qna/qnaContent";
  }
  
}
