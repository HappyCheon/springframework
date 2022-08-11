package com.spring.javagreenS.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.javagreenS.dao.AdminDAO;
import com.spring.javagreenS.dao.BoardDAO;
import com.spring.javagreenS.dao.DbShopDAO;
import com.spring.javagreenS.dao.GuestDAO;
import com.spring.javagreenS.dao.InquiryDAO;
import com.spring.javagreenS.dao.MemberDAO;
import com.spring.javagreenS.dao.PdsDAO;
import com.spring.javagreenS.dao.QnaDAO;

@Service
public class PageProcess {
	@Autowired
	GuestDAO guestDAO;
	
	@Autowired
	MemberDAO memberDAO;
	
	@Autowired
	BoardDAO boardDAO;
	
	@Autowired
	PdsDAO pdsDAO;
	
	@Autowired
	DbShopDAO dbShopDAO;
	
	@Autowired
	AdminDAO adminDAO;
	
	@Autowired
  QnaDAO qnaDAO;
	
	@Autowired
	InquiryDAO inquiryDAO;
	
	// 인자: 1.page번호, 2.page크기, 3.소속(예:게시판(board),회원(member),방명록(guest)..), 4.분류(part), 5.검색어(searchString)
	public PageVO totRecCnt(int pag, int pageSize, String section, String part, String searchString) {
		PageVO pageVO = new PageVO();
		
		int totRecCnt = 0;
		int blockSize = 3;
		
		// section에 따른 레코드 갯수를 구해오기
		if(section.equals("member")) {
			totRecCnt = memberDAO.totRecCnt();
		}
		else if(section.equals("guest")) {
			totRecCnt = guestDAO.totRecCnt();
		}
		else if(section.equals("board")) {
			if(searchString.equals("")) {
  			totRecCnt = boardDAO.totRecCnt();
			}
			else {
				String search = part;
				totRecCnt = boardDAO.totSearchRecCnt(search, searchString);
			}
		}
		else if(section.equals("pds")) {
			totRecCnt = pdsDAO.totRecCnt(part);
		}
		else if(section.equals("dbMyOrder")) {
			totRecCnt = dbShopDAO.totRecCnt(part);
		}
		else if(section.equals("myOrderStatus")) {
			// searchString = startJumun + "@" + endJumun + "@" + conditionOrderStatus;
			String[] searchStringArr = searchString.split("@");
			totRecCnt = dbShopDAO.totRecCntMyOrderStatus(part,searchStringArr[0],searchStringArr[1],searchStringArr[2]);
		}
		else if(section.equals("dbShopMyOrderStatus")) {
			totRecCnt = dbShopDAO.totRecCntStatus(part,searchString);
		}
		else if(section.equals("dbShopMyOrderCondition")) {
			totRecCnt = dbShopDAO.totRecCntCondition(part, Integer.parseInt(searchString));
		}
//		else if(section.equals("dbShopMyOrderCondition")) {
//			// orderStatus = startJumun + "@" + endJumun + "@" + orderStatus;
//			String[] searchStringArr = searchString.split("@");
//			totRecCnt = dbShopDAO.totRecCntAdminStatus(searchStringArr[0],searchStringArr[1],searchStringArr[2]);
//		}
		else if(section.equals("adminDbOrderProcess")) {
			// strOrderStatus = startJumun + "@" + endJumun + "@" + orderStatus
			String[] searchStringArr = searchString.split("@");
			totRecCnt = dbShopDAO.totRecCntAdminStatus(searchStringArr[0],searchStringArr[1],searchStringArr[2]);
		}
		else if(section.equals("adminMemberList")) {
			if(part.equals("")) {
				totRecCnt = memberDAO.totRecCntAdminMemberList(Integer.parseInt(searchString));
			}
			else {
				totRecCnt = memberDAO.totRecCntAdminMemberMidList(part);
			}
		}
		else if(section.equals("qrCodeTicket")) {
			totRecCnt = adminDAO.totRecQrCodeTicket(part, searchString);
		}
		else if(section.equals("qna")) {
			totRecCnt = qnaDAO.totRecCnt();
		}
		else if(section.equals("inquiry")) {
			totRecCnt = inquiryDAO.totRecCnt(part, searchString);
		}
		
		int totPage = (totRecCnt%pageSize)==0 ? totRecCnt/pageSize : (totRecCnt/pageSize)+1;
		int startIndexNo = (pag - 1) * pageSize;
		int curScrStartNo = totRecCnt - startIndexNo;
		int curBlock = (pag - 1) / blockSize;
		int lastBlock = (totPage % blockSize)==0 ? (totPage / blockSize) - 1 : (totPage / blockSize);
		
		pageVO.setPag(pag);
		pageVO.setPageSize(pageSize);
		pageVO.setTotRecCnt(totRecCnt);
		pageVO.setTotPage(totPage);
		pageVO.setStartIndexNo(startIndexNo);
		pageVO.setCurScrStartNo(curScrStartNo);
		pageVO.setBlockSize(blockSize);
		pageVO.setCurBlock(curBlock);
		pageVO.setLastBlock(lastBlock);
		
		return pageVO;
	}
	
	
}
