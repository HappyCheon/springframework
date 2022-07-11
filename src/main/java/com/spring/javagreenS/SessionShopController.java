package com.spring.javagreenS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//@RestController
@Controller
@RequestMapping("/sessionShop")
public class SessionShopController {

	// 세션을 활용한 쇼핑몰 연습(서비스객체와 DB를 사용하지 않고 처리해 본다.)
	
	// 상품 리스트 보여주기
	@RequestMapping(value = "/shopList", method = RequestMethod.GET)
	public String shopListGet() {
		return "study/sessionShop/shopList";
	}
	
	// 장바구니(productList)에 물품 담기(물품을 담은후 최종적으로 세션장바구니(sProductList)에 저장시켜준다.)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/shopList", method = RequestMethod.POST)
	public String shopListPost(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
				
		if(session.getAttribute("sProductList") == null) {
			productList = new ArrayList<String>();
		}
		productList.add(product);
		session.setAttribute("sProductList", productList);
		
		return "";
	}
	
	// 세션장바구니에 들어있는 상품들을 화면에 보여주기(장바구니보기) 위한 준비작업처리
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String cartGet(HttpSession session, Model model) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		if(productList == null || productList.size() == 0) {
			return "redirect:/msg/sessionCartNo";
		}
		
		// 구매한 상품의 총가격 구하기
		int totPrice = 0;
		for(String price : productList) {
			String[] strPrice = price.split("/");
			totPrice += Integer.parseInt(strPrice[1]);
		}
		
		Collections.sort(productList);
		model.addAttribute("productList", productList);
		session.setAttribute("sTotPrice", totPrice);
		
		return "study/sessionShop/cart";
	}
	
	// 장바구니 모두 비우기(구매취소)
	@RequestMapping(value = "/cartReset", method = RequestMethod.GET)
	public String cartResetGet(HttpSession session) {
		session.removeAttribute("sProductList");
		session.removeAttribute("sTotPrice");
		
		return "study/sessionShop/shopList";
	}
	
	// 장바구니안의 개별품목 수량 1개씩 감소하기
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cartSub", method = RequestMethod.GET)
	public String cartSubGet(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.remove(product);
		session.setAttribute("sProductList", productList);
		
		return "redirect:/sessionShop/cart";
	}
	
	// 장바구니안의 개별품목 수량 1개씩 증가하기
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cartAdd", method = RequestMethod.GET)
	public String cartAddGet(HttpSession session, String product) {
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.add(product);
		session.setAttribute("sProductList", productList);
		
		return "redirect:/sessionShop/cart";
	}
	
	// 장바구니안의 개별품목 취소하기(개별상품 주문취소)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cartDel", method = RequestMethod.GET)
	public String cartDelGet(HttpSession session, String product) {
		// 상품명이 문자로 넘어오기에 객체형식으로는 제거할수 없다. 따라서 객체형식으로 변경후 세션에 담긴객체를 제거해야한다.
		List<String> tempProduct = new ArrayList<String>();
		tempProduct.add(product);
		
		List<String> productList = (ArrayList<String>) session.getAttribute("sProductList");
		productList.removeAll(tempProduct);
		
		return "redirect:/sessionShop/cart";
	}
	
}
