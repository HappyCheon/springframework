package com.spring.javagreenS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.javagreenS.pagination.PageProcess;
import com.spring.javagreenS.pagination.PageVO;
import com.spring.javagreenS.service.AdminService;
import com.spring.javagreenS.service.DbShopService;
import com.spring.javagreenS.service.MemberService;
import com.spring.javagreenS.vo.DbBaesongVO;
import com.spring.javagreenS.vo.DbCartListVO;
import com.spring.javagreenS.vo.DbOptionVO;
import com.spring.javagreenS.vo.DbOrderVO;
import com.spring.javagreenS.vo.DbProductVO;
import com.spring.javagreenS.vo.MemberVO;
import com.spring.javagreenS.vo.PayMentVO;

@Controller
@RequestMapping("/dbShop")
public class DbShopController {
	String msgFlag = "";
	
	@Autowired
	DbShopService dbShopService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PageProcess pageProcess;
	
	@Autowired
	AdminService adminService;
	
	// 모든 분류목록 출력하기(처음화면의 '대/중/소' 분류 등록및 조회 창 보여주기)
	@RequestMapping(value="/dbCategory", method = RequestMethod.GET)
	public String dbCategoryGet(Model model) {
		List<DbProductVO> mainVos = dbShopService.getCategoryMain();
		List<DbProductVO> middleVos = dbShopService.getCategoryMiddle();
		List<DbProductVO> subVos = dbShopService.getCategorySub();
		
		model.addAttribute("mainVos", mainVos);
		model.addAttribute("middleVos", middleVos);
		model.addAttribute("subVos", subVos);
		
		return "admin/dbShop/dbCategory";
	}
	
	// 대분류 선택시 중분류명 가져오기
	@ResponseBody
	@RequestMapping(value="/categoryMiddleName", method = RequestMethod.POST)
	public List<DbProductVO> categoryMiddleNamePost(String categoryMainCode) {
		return dbShopService.getCategoryMiddleName(categoryMainCode);
	}
	
	
	// 중분류 선택시 소분류명 가져오기
	@ResponseBody
	@RequestMapping(value="/categorySubName", method = RequestMethod.POST)
	public List<DbProductVO> categorySubNamePost(String categoryMainCode, String categoryMiddleCode) {
		return dbShopService.getCategorySubName(categoryMainCode, categoryMiddleCode);
	}
	
	// 대분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categoryMainInput", method = RequestMethod.POST)
	public String categoryMainInputPost(DbProductVO vo) {
		// 기존에 같은이름의 대분류가 있는지를 체크한다.
		DbProductVO imsiVo  = dbShopService.getCategoryMainOne(vo.getCategoryMainCode(), vo.getCategoryMainName());
		
		if(imsiVo != null) return "0";
		dbShopService.categoryMainInput(vo);	// 대분류항목 저장
		return "1";
	}
	
	// 중분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categoryMiddleInput", method = RequestMethod.POST)
	public String categoryMiddleInputPost(DbProductVO vo) {	
	  // 기존에 같은이름의 중분류가 있는지를 체크한다.
		List<DbProductVO> vos = dbShopService.getCategoryMiddleOne(vo);
		if(vos.size() != 0) return "0";
		
		dbShopService.setCategoryMiddleInput(vo);	// 중분류항목 저장
		
		return "1";
	}
	
	// 소분류 등록하기
	@ResponseBody
	@RequestMapping(value="/categorySubInput", method = RequestMethod.POST)
	public String categorySubInputPost(DbProductVO vo) {
		List<DbProductVO> vos = dbShopService.getCategorySubOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.setCategorySubInput(vo);
		return "1";
	}
	
	// 대분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategoryMain", method = RequestMethod.POST)
	public String delCategoryMainPost(DbProductVO vo) {
		List<DbProductVO> vos = dbShopService.getCategoryMiddleOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.delCategoryMain(vo.getCategoryMainCode());
		return "1";
	}
	
	// 중분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategoryMiddle", method = RequestMethod.POST)
	public String delCategoryMiddlePost(DbProductVO vo) {
		List<DbProductVO> vos = dbShopService.getCategorySubOne(vo);
		if(vos.size() != 0) return "0";
		dbShopService.delCategoryMiddle(vo.getCategoryMiddleCode());
		return "1";
	}
	
	// 소분류 삭제하기
	@ResponseBody
	@RequestMapping(value="/delCategorySub", method = RequestMethod.POST)
	public String delCategorySubPost(DbProductVO vo) {
		List<DbProductVO> vos = dbShopService.getDbProductOne(vo.getCategorySubCode());
		if(vos.size() != 0) return "0";
		dbShopService.delCategorySub(vo.getCategorySubCode());
		return "1";
	}
	
  //관리자 상품등록에서 상품 작성시, ckeditor에서 글올릴때 이미지와 함께 올린다면 이곳에서 서버 파일시스템에 저장시켜준다.
	@ResponseBody
	@RequestMapping("/imageUpload")
	public void imageUploadGet(HttpServletRequest request, HttpServletResponse response, @RequestParam MultipartFile upload) throws Exception {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		String originalFilename = upload.getOriginalFilename();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		originalFilename = sdf.format(date) + "_" + originalFilename;
		
		byte[] bytes = upload.getBytes();
		
		// ckeditor에서 올린 파일을 서버 파일시스템에 저장시켜준다.
		String uploadPath = request.getSession().getServletContext().getRealPath("/resources/data/dbShop/");
		OutputStream outStr = new FileOutputStream(new File(uploadPath + originalFilename));
		outStr.write(bytes);		// 서버에 업로드시킨 그림파일이 저장된다.
		
		// 서버 파일시스템에 저장된 파일을 화면(textarea)에 출력하기
		PrintWriter out = response.getWriter();
		String fileUrl = request.getContextPath() + "/data/dbShop/" + originalFilename;
		out.println("{\"originalFilename\":\""+originalFilename+"\",\"uploaded\":1,\"url\":\""+fileUrl+"\"}");       /* "atom":"12.jpg","uploaded":1,"": */
		
		out.flush();
		outStr.close();
	}
	
	// 상품 등록을 위한 목록창 보여주기
	@RequestMapping(value="/dbProduct", method = RequestMethod.GET)
	public String dbProductGet(Model model) {
		List<DbProductVO> mainVos = dbShopService.getCategoryMain();
		model.addAttribute("mainVos", mainVos);
		return "admin/dbShop/dbProduct";
	}
	
	// 상품 등록 시키기
	@RequestMapping(value="/dbProduct", method = RequestMethod.POST)
	public String dbProductPost(MultipartFile file, DbProductVO vo) {
	  // 이미지파일 업로드시에는 ckeditor폴더에서 board폴더로 복사작업처리
		dbShopService.imgCheckProductInput(file, vo);
		
		msgFlag = "dbProductInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 등록된 상품 보여주기(관리자화면에서 보여주기)
	@RequestMapping(value="/dbShopList", method = RequestMethod.GET)
	public String dbShopListGet(
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			Model model) {
		List<DbProductVO> subTitleVos = dbShopService.getSubTitle();
		model.addAttribute("subTitleVos", subTitleVos);
		model.addAttribute("part", part);
		
		List<DbProductVO> productVos = dbShopService.getDbShopList(part);
		model.addAttribute("productVos", productVos);
		return "admin/dbShop/dbShopList";
	}
	
	// 옵션 등록창 보기
	@RequestMapping(value="/dbOption", method=RequestMethod.GET)
	public String dbOptionGet(Model model) {
		String[] productNames = dbShopService.getProductName();
		model.addAttribute("productNames", productNames);
		
		return "admin/dbShop/dbOption";
	}
	
	// 옵션등록시 상품을 선택하면 상품의 상세설명 가져와서 뿌리기(해당 상품의 
	@ResponseBody
	@RequestMapping(value="/getProductInfor", method = RequestMethod.POST)
	public List<DbProductVO> getProductInforPost(String productName) {
		return dbShopService.getProductInfor(productName);
	}
	
	// 옵션등록시 선택한 상품에 대한 옵션들을 보여주기위한 처리 
	@ResponseBody
	@RequestMapping(value="/getOptionList", method = RequestMethod.POST)
	public List<DbOptionVO> getOptionListPost(int productIdx) {
		return dbShopService.getOptionList(productIdx);
	}
	
	// 옵션 기록사항 등록하기
	@RequestMapping(value="/dbOption", method=RequestMethod.POST)
	public String dbOptionPost(DbOptionVO vo, String[] optionName, int[] optionPrice) {
		for(int i=0; i<optionName.length; i++) {
			// 같은 제품에 같은 옵션이 등록되었으면 skip시킨다.
			int optionCnt = dbShopService.getOptionSame(vo.getProductIdx(), optionName[i]);
			if(optionCnt != 0) continue;
			
			vo.setProductIdx(vo.getProductIdx());
			vo.setOptionName(optionName[i]);
			
			vo.setOptionPrice(optionPrice[i]);
			dbShopService.setDbOptionInput(vo);		// 옵션에 기록된 모든 내용을 가지고 DB에 저장한다.
		}
		
		msgFlag = "dbOptionInputOk";
		return "redirect:/msg/" + msgFlag;
	}
	
	// 옵션등록시 선택한 상품에 대한 옵션들의 내용을 보여주고 삭제처리... 
	@ResponseBody
	@RequestMapping(value="/optionDelete", method = RequestMethod.POST)
	public String optionDeletePost(int idx) {
		dbShopService.setOptionDelete(idx);
		return "";
	}
	
  // 진열된 상품 클릭시 상품내역 상세보기
	@RequestMapping(value="/dbShopContent", method=RequestMethod.GET)
	public String dbShopContentGet(int idx, Model model) {
		DbProductVO productVo = dbShopService.getDbShopProduct(idx); 			// 상품 상세 정보 불러오기
		List<DbOptionVO> optionVos = dbShopService.getDbShopOption(idx); 	// 옵션 정보 모두 가져오기
		
		model.addAttribute("productVo", productVo);
		model.addAttribute("optionVos", optionVos);
		
		return "admin/dbShop/dbShopContent";
	}
	
	
	/* 아래로 사용자(고객)에서의 처리부분들~~ */
	
	// 등록된 상품 보여주기(사용자(고객)화면에서 보여주기)
	@RequestMapping(value="/dbProductList", method = RequestMethod.GET)
	public String dbProductListGet(
			@RequestParam(name="part", defaultValue="전체", required=false) String part,
			Model model) {
		List<DbProductVO> subTitleVos = dbShopService.getSubTitle();
		model.addAttribute("subTitleVos", subTitleVos);
		model.addAttribute("part", part);
		
		List<DbProductVO> productVos = dbShopService.getDbShopList(part);
		model.addAttribute("productVos", productVos);
		return "dbShop/dbProductList";
	}
	
	// 진열된 상품 클릭시 상품내역 상세보기
	@RequestMapping(value="/dbProductContent", method=RequestMethod.GET)
	public String dbProductContentGet(int idx, Model model) {
		DbProductVO productVo = dbShopService.getDbShopProduct(idx); 			// 상품 상세 정보 불러오기
		List<DbOptionVO> optionVos = dbShopService.getDbShopOption(idx); 	// 옵션 정보 모두 가져오기
		
		model.addAttribute("productVo", productVo);
		model.addAttribute("optionVos", optionVos);
		
		return "dbShop/dbProductContent";
	}
	
  //진열상품중에서 선택한상품을 장바구니로 보내기(DB에 저장 : 장바구니테이블(cart)에, 앞에서 넘어온 자료들을 담아주고 있다. : 이때 이미 존재하는 장바구니에 지금 같은 '상품/옵션'이 넘어온다면 수량을 누적시켜줘야하는 작업을 아래서 처리하고 있다.)
	@RequestMapping(value="/dbProductContent", method=RequestMethod.POST)
	public String dbProductContentPost(DbCartListVO vo, HttpSession session, String flag) {
		// 아래(300~309)는 기존에 이미 장바구니에 있던 제품의 수량과 지금 다시 구입하는 제품이 같을경우에 '수량'을 누적처리하고 있다.
		// 구매한 상품과 상품의 옵션 정보를 읽어온다. 이때, 기존에 구매했었던 제품이 장바구니에 담겨있었다면 지금 상품을 기존 장바구니에 'update'시키고, 처음 구매한 장바구니이면 새로담긴 품목을 장바구니 테이블에 'insert'시켜준다.
		// 장바구니테이블에서 지금 구매한 상품이 예전 장바구니에도 담겨있는지 확인하기위해 상품명과 옵션명과 현재 로그인중인 아이디를 넘겨서 기존 장바구니 내용을 검색해 온다.
		String mid = (String) session.getAttribute("sMid");
		DbCartListVO resVo = dbShopService.getDbCartListProductOptionSearch(vo.getProductName(), vo.getOptionName(), mid);
		if(resVo != null) {		// 기존에 구매한적이 있다면....update 시킨다.
			String[] voOptionNums = vo.getOptionNum().split(",");     // 앞에서 넘어온 vo안의 수량옵션리스트(배열로 넘어온다. 따라서 자료(옵션)가 여러개라면 수량옵션이 각각 ','로 분리되어 들어있기에 ','로 분리시켜주었다.)
			String[] resOptionNums = resVo.getOptionNum().split(","); // 기존 DB에 저장되어 있던 장바구니 : resVo(앞줄과 같은방식의 배열이기에 ','로 분리시켜서 앞에서 넘어온 수량가 함께 더해주려 한다.
			int[] nums = new int[99];		// 기존옵션수량과 지금 옵션수량을 함께 다시 더해서 저장시키기 위한 작업(같은 항목이지만 수량이 변경되었기에 수량을 더하기 위해 정수형 배열을 만들었다.)
			String strNums = "";				// 수량 계산후 다시 수량을 문자형식으로 누적저장시켜주기위한 변수 선언.				
			for(int i=0; i<voOptionNums.length; i++) {		// 옵션항목의 길이만큼 반복하면서 각각의 위치에 맞는 수량들을 누적해주고 있다.
				nums[i] += (Integer.parseInt(voOptionNums[i]) + Integer.parseInt(resOptionNums[i]));
				strNums += nums[i];		// 누적시킨 수량을 다시 문자수량에 담아주고 있다.(누적)
				if(i < nums.length - 1) strNums += ",";		// 마지막 옵션자료 전까지 ','를 붙여주고 있다.
			}
			vo.setOptionNum(strNums);
			dbShopService.dbShopCartUpdate(vo);
		}
		else {			// 기존에 구매한적이 없다면....insert시킨다.
			dbShopService.dbShopCartInput(vo);
		}
		
		if(flag.equals("order")) {
			return "redirect:/msg/cartOrderOk";
		}
		else {
			return "redirect:/msg/cartInputOk";
		}
		
	}
	
  // 장바구니에 담겨있는 모든 품목들 보여주기(장바구니는 DB에 들어있는 자료를 바로 불러와서 처리하면된다.)
	@RequestMapping(value="/dbCartList", method=RequestMethod.GET)
	public String dbCartListGet(HttpSession session, DbCartListVO vo, Model model) {
		String mid = (String) session.getAttribute("sMid");
		List<DbCartListVO> vos = dbShopService.getDbCartList(mid);
		
		if(vos.size() == 0) {
			msgFlag = "cartEmpty";
			return "redirect:/msg/" + msgFlag;
		}
		
		model.addAttribute("cartListVos", vos);
		return "dbShop/dbCartList";
	}
	
  // 카트에서 담겨있는 품목들중에서, 주문한 품목들을 읽어와서 세션에 담고, 고객의 정보도 가져와서 담고, 주문번호도 만들어서 model에 담아서 다음단계(결제)로 넘겨준다.
	@RequestMapping(value="/dbCartList", method=RequestMethod.POST)
	public String dbCartListPost(HttpServletRequest request, Model model, HttpSession session) {
		String mid = session.getAttribute("sMid").toString();
		int baesong = Integer.parseInt(request.getParameter("baesong"));
		
	  // 아래는 주문작업이 들어오면 그때 '주문고유번호'를 만들어주면된다.
    // 주문고유번호(idx) 만들기(기존 DB의 고유번호(idx) 최대값 보다 +1 시켜서 만든다) 
		DbOrderVO maxIdx = dbShopService.getOrderMaxIdx();
		int idx = 1;
		if(maxIdx != null) idx = maxIdx.getMaxIdx() + 1;
		
		//주문번호(orderIdx) 만들기(->날짜_idx)
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String orderIdx = sdf.format(today) + idx;
				
		
		// dbCartList.jsp에서 선택한 상품들이 여러개일수 있기에 배열로 넘어온다. 이때 선택한 상품의 name은 'idxChecked'이다. 체크된것은 값이 1인것, 즉 true인 것만 넘어온다.
		String[] idxChecked = request.getParameterValues("idxChecked");
		
		DbCartListVO cartVo = new DbCartListVO();
		List<DbOrderVO> orderVos = new ArrayList<DbOrderVO>();
		
		for(String strIdx : idxChecked) {	// idxChecked배열변수에 들어있는 값은 idxChecked의 값이 1인것(true)의 idx값만 담겨있다. 즉, cart의 고유번호를 이용해서 카트에 담긴 삼품의 정보를 가져오려한다.
			cartVo = dbShopService.getCartIdx(Integer.parseInt(strIdx));		// 장바구니에서 선택된 카트고유번호(idx)를 이용해서 선택된 상품의 정보를 가져온다.
			DbOrderVO orderVo = new DbOrderVO();
			orderVo.setProductIdx(cartVo.getProductIdx());
			orderVo.setProductName(cartVo.getProductName());
			orderVo.setMainPrice(cartVo.getMainPrice());
			orderVo.setThumbImg(cartVo.getThumbImg());
			orderVo.setOptionName(cartVo.getOptionName());
			orderVo.setOptionPrice(cartVo.getOptionPrice());
			orderVo.setOptionNum(cartVo.getOptionNum());
			orderVo.setTotalPrice(cartVo.getTotalPrice());
			orderVo.setCartIdx(cartVo.getIdx());
			orderVo.setBaesong(baesong);
			
			orderVo.setOrderIdx(orderIdx);	// 앞에서 '만들어준 주문고유번호'를 저장시켜준다.
			orderVo.setMid(mid);						// 로그인한 아이디를 저장시켜준다.
			
			orderVos.add(orderVo);
		}
		session.setAttribute("sOrderVos", orderVos); // 주문에서 보여준후 다시 그대로를 담아서 결제창으로 보내기에 model이 아닌, session처리했다.
		
		// 현재 로그인된 고객의 정보를 member2테이블에서 가져온다.
		MemberVO memberVo = memberService.getMemIdCheck(mid);
		model.addAttribute("memberVo", memberVo);
		
		return "dbShop/dbOrder";  // 결재 및 주문서 작성 jsp호출
	}
	
	// 장바구니안의 선택된 품목 삭제하기
	@ResponseBody
	@RequestMapping(value="/dbCartDelete", method=RequestMethod.POST)
	public String dbCartDeleteGet(int idx) {
		dbShopService.dbCartDelete(idx);
		return "";
	}
	
	
  // 결제시스템 연습하기(결제창 호출하기) - API이용
	@RequestMapping(value="/payment", method=RequestMethod.POST)
	public String paymentPost(DbOrderVO orderVo, PayMentVO payMentVo, DbBaesongVO baesongVo, HttpSession session, Model model) {
		
		model.addAttribute("payMentVo", payMentVo);
		
		session.setAttribute("sPayMentVo", payMentVo);
		session.setAttribute("sBaesongVo", baesongVo);
		
		return "dbShop/paymentOk";
	}
	
  // 결제시스템 연습하기(결제창 호출하기) - API이용
	// 주문 완료후 주문내역을 '주문테이블(dbOrder)에 저장
	// 주문이 완료되었기에 주문된 물품은 장바구니(dbCartList)에서 내역을 삭제처리한다.
	// 사용한 세션은 제거시킨다.
	// 작업처리후 오늘 구매한 상품들의 정보(구매품목,결제내역,배송지)들을 model에 담아서 확인창으로 넘겨준다.
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/paymentResult", method=RequestMethod.GET)
	public String paymentResultGet(HttpSession session, PayMentVO receivePayMentVo, Model model) {
		// 주문내역 dbOrder/dbBaesong 테이블에 저장하기(앞에서 저장했던 세션에서 가져왔다.)
		List<DbOrderVO> orderVos = (List<DbOrderVO>) session.getAttribute("sOrderVos");
		PayMentVO payMentVo = (PayMentVO) session.getAttribute("sPayMentVo");
		DbBaesongVO baesongVo = (DbBaesongVO) session.getAttribute("sBaesongVo");
		
//		사용된 세션은 반환한다.
//		session.removeAttribute("sOrderVos");
//		session.removeAttribute("sPayMentVo");
		session.removeAttribute("sBaesongVo");
		
		for(DbOrderVO vo : orderVos) {
			vo.setIdx(Integer.parseInt(vo.getOrderIdx().substring(8))); // 주문테이블에 고유번호를 셋팅한다.	
			vo.setOrderIdx(vo.getOrderIdx());        				// 주문번호를 주문테이블의 주문번호필드에 지정처리한다.
			vo.setMid(vo.getMid());							
			
			dbShopService.setDbOrder(vo);                 	// 주문내용을 주문테이블(dbOrder)에 저장.
			dbShopService.dbCartDeleteAll(vo.getCartIdx()); // 주문이 완료되었기에 장바구니(dbCartList)에서 주문한 내역을 삭체처리한다.
		}
		// 주문된 정보를 배송테이블에 담기위한 처리(기존 baesongVo에 담기지 않은 내역들을 담아주고 있다.)
		baesongVo.setOIdx(orderVos.get(0).getIdx());
		baesongVo.setOrderIdx(orderVos.get(0).getOrderIdx());
		baesongVo.setAddress(payMentVo.getBuyer_addr());
		baesongVo.setTel(payMentVo.getBuyer_tel());
		
		dbShopService.setDbBaesong(baesongVo);  // 배송내용을 배송테이블(dbBaesong)에 저장
		dbShopService.setMemberPointPlus((int)(baesongVo.getOrderTotalPrice() * 0.01), orderVos.get(0).getMid());	// 회원테이블에 포인트 적립하기(1%)
		
		payMentVo.setImp_uid(receivePayMentVo.getImp_uid());
		payMentVo.setMerchant_uid(receivePayMentVo.getMerchant_uid());
		payMentVo.setPaid_amount(receivePayMentVo.getPaid_amount());
		payMentVo.setApply_num(receivePayMentVo.getApply_num());
		
		// 오늘 주문에 들어간 정보들을 확인해주기위해 다시 session에 담아서 넘겨주고 있다.
//		model.addAttribute("orderVos", orderVos);
//		model.addAttribute("payMentVo", payMentVo);
//		model.addAttribute("orderTotalPrice", baesongVo.getOrderTotalPrice());
//		session.setAttribute("sOrderVos", orderVos);
		session.setAttribute("sPayMentVo", payMentVo);
		session.setAttribute("orderTotalPrice", baesongVo.getOrderTotalPrice());
		
//		return "dbShop/paymentResult";
		return "redirect:/msg/paymentResultOk";
	}
	
	// 배송지 정보 보여주기
	@RequestMapping(value="/dbOrderBaesong", method=RequestMethod.GET)
	public String dbOrderBaesongGet(String orderIdx, Model model) {
		List<DbBaesongVO> vos = dbShopService.getOrderBaesong(orderIdx);  // 같은 주문번호가 2개 이상 있을수 있기에 List객체로 받아온다.
		model.addAttribute("vo", vos.get(0));  // 같은 배송지면 0번째것 하나만 vo에 담아서 넘겨주면 된다.
		
		return "dbShop/dbOrderBaesong";
	}
	
	// 현재 로그인 사용자가 주문내역 조회하기 폼 보여주기
	@RequestMapping(value="/dbMyOrder", method=RequestMethod.GET)
	public String dbMyOrderGet(HttpServletRequest request, HttpSession session, Model model,
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		String mid = (String) session.getAttribute("sMid");
		int level = (int) session.getAttribute("sLevel");
		if(level == 0) mid = "전체";
		
		/* 이곳부터 페이징 처리(블록페이지) 변수 지정 시작 */
		PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "dbMyOrder", mid, "");
		
		// 오늘 구매한 내역을 초기화면에 보여준다.
		List<DbProductVO> vos = dbShopService.getMyOrderList(pageVo.getStartIndexNo(), pageSize, mid);
		System.out.println("vos : " + vos);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo",pageVo);
		
		return "dbShop/dbMyOrder";
	}
	
	// 날짜별 상태별 기존제품 구매한 주문내역 확인하기
	@RequestMapping(value="/myOrderStatus", method=RequestMethod.GET)
	public String myOrderStatusGet(
			HttpServletRequest request, 
			HttpSession session, 
			String startJumun, 
			String endJumun, 
			@RequestParam(name="pag", defaultValue="1", required=false) int pag,
			@RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
  	  @RequestParam(name="conditionOrderStatus", defaultValue="전체", required=false) String conditionOrderStatus,
			Model model) {
		String mid = (String) session.getAttribute("sMid");
		int level = (int) session.getAttribute("sLevel");
		
		if(level == 0) mid = "전체";
		String searchString = startJumun + "@" + endJumun + "@" + conditionOrderStatus;
		PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "myOrderStatus", mid, searchString);  // 4번째인자에 '아이디/조건'(을)를 넘겨서 part를 아이디로 검색처리하게 한다.
		
		List<DbBaesongVO> vos = dbShopService.getMyOrderStatus(pageVo.getStartIndexNo(), pageSize, mid, startJumun, endJumun, conditionOrderStatus);
		model.addAttribute("vos", vos);				
		model.addAttribute("startJumun", startJumun);
		model.addAttribute("endJumun", endJumun);
		model.addAttribute("conditionOrderStatus", conditionOrderStatus);
		model.addAttribute("pageVo", pageVo);
		
		return "dbShop/dbMyOrder";
	}
	
  // 주문 상태별(결제완료/배송중~~) 조회하기
  @RequestMapping(value="/orderStatus", method=RequestMethod.GET)
  public String orderStatusGet(HttpSession session,
      @RequestParam(name="pag", defaultValue="1", required=false) int pag,
      @RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize,
      @RequestParam(name="orderStatus", defaultValue="전체", required=false) String orderStatus,
      Model model) {
    String mid = (String) session.getAttribute("sMid");

    PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "dbShopMyOrderStatus", mid, orderStatus);

    List<DbBaesongVO> vos = dbShopService.getOrderStatus(mid, orderStatus, pageVo.getStartIndexNo(), pageSize);
    model.addAttribute("orderStatus", orderStatus);
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);

    return "dbShop/dbMyOrder";
  }
  
  // 주문 조건 조회하기(날짜별(오늘/일주일/보름/한달/3개월/전체)
  @RequestMapping(value="/orderCondition", method=RequestMethod.GET)
  public String orderConditionGet(HttpSession session, int conditionDate, Model model,
      @RequestParam(name="pag", defaultValue="1", required=false) int pag,
      @RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
    String mid = (String) session.getAttribute("sMid");
    String strConditionDate = conditionDate + "";
    PageVO pageVo = pageProcess.totRecCnt(pag, pageSize, "dbShopMyOrderCondition", mid, strConditionDate);

    List<DbBaesongVO> vos = dbShopService.getOrderCondition(mid, conditionDate, pageVo.getStartIndexNo(), pageSize);
    
		model.addAttribute("vos", vos);
		model.addAttribute("pageVo", pageVo);
    model.addAttribute("conditionDate", conditionDate);

    // 아래는 1일/일주일/보름/한달/3달/전체 조회시에 startJumun과 endJumun을 넘겨주는 부분(view에서 시작날짜와 끝날짜를 지정해서 출력시켜주기위해 startJumun과 endJumun값을 구해서 넘겨준다.)
    Calendar startDateJumun = Calendar.getInstance();
    Calendar endDateJumun = Calendar.getInstance();
    startDateJumun.setTime(new Date());  // 오늘날짜로 셋팅
    endDateJumun.setTime(new Date());    // 오늘날짜로 셋팅
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String startJumun = "";
    String endJumun = "";
    switch (conditionDate) {
      case 1:
        startJumun = sdf.format(startDateJumun.getTime());
        endJumun = sdf.format(endDateJumun.getTime());
        break;
      case 7:
        startDateJumun.add(Calendar.DATE, -7);
        break;
      case 15:
        startDateJumun.add(Calendar.DATE, -15);
        break;
      case 30:
        startDateJumun.add(Calendar.MONTH, -1);
        break;
      case 90:
        startDateJumun.add(Calendar.MONTH, -3);
        break;
      case 99999:
        startDateJumun.set(2022, 00, 01);
        break;
      default:
        startJumun = null;
        endJumun = null;
    }
    if(conditionDate != 1 && endJumun != null) {
      startJumun = sdf.format(startDateJumun.getTime());
      endJumun = sdf.format(endDateJumun.getTime());
    }

    model.addAttribute("startJumun", startJumun);
    model.addAttribute("endJumun", endJumun);

    return "dbShop/dbMyOrder";
  }
	
  // 관리자에서 주문 확인하기
	@RequestMapping(value="/adminOrderStatus")
	public String dbOrderProcessGet(Model model,
    @RequestParam(name="startJumun", defaultValue="", required=false) String startJumun,
    @RequestParam(name="endJumun", defaultValue="", required=false) String endJumun,
    @RequestParam(name="orderStatus", defaultValue="전체", required=false) String orderStatus,
    @RequestParam(name="pag", defaultValue="1", required=false) int pag,
    @RequestParam(name="pageSize", defaultValue="5", required=false) int pageSize) {
		
		//System.out.println("startJumun: " + startJumun + " , endJumun : " + endJumun);

		List<DbBaesongVO> vos = null;
		PageVO pageVo = null;
		String strNow = "";
		if(startJumun.equals("")) {
			Date now = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    strNow = sdf.format(now);
	    
	    startJumun = strNow;
	    endJumun = strNow;
		}
    
    String strOrderStatus = startJumun + "@" + endJumun + "@" + orderStatus;
    pageVo = pageProcess.totRecCnt(pag, pageSize, "adminDbOrderProcess", "", strOrderStatus);
		
//		if(startJumun.equals("")) {
//	    Date now = new Date();
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	    strNow = sdf.format(now);
//	    
//	    startJumun = strNow;
//	    endJumun = strNow;
//	    
//	    String strOrderStatus = startJumun + "@" + endJumun + "@" + orderStatus;
//	    pageVo = pageProcess.totRecCnt(pag, pageSize, "adminDbOrderProcess", "", strOrderStatus);
	    
//	    vos = dbShopService.getAdminOrderStatus(strNow, strNow, orderStatus);
//	  }
//	  else {
//	  	String strOrderStatus = startJumun + "@" + endJumun + "@" + orderStatus;
//	  	pageVo = pageProcess.totRecCnt(pag, pageSize, "adminDbOrderProcess", "", strOrderStatus);
	  	
//		  vos = dbShopService.getAdminOrderStatus(startJumun, endJumun, orderStatus);
//	  }
		vos = dbShopService.getAdminOrderStatus(startJumun, endJumun, orderStatus);
		//System.out.println("vos : " + vos);
	
	  model.addAttribute("startJumun", startJumun);
	  model.addAttribute("endJumun", endJumun);
	  model.addAttribute("orderStatus", orderStatus);
	  model.addAttribute("vos", vos);
	  model.addAttribute("pageVo", pageVo);
	
	  return "admin/dbShop/dbOrderProcess";
	}
  
	// 관리자가 주문상태를 변경처리하는것
	@ResponseBody
	@RequestMapping(value="/goodsStatus", method=RequestMethod.POST)
	public String goodsStatusGet(String orderIdx, String orderStatus) {
		dbShopService.setOrderStatusUpdate(orderIdx, orderStatus);
		return "";
	}  
  
}
