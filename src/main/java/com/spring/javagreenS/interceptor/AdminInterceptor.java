package com.spring.javagreenS.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminInterceptor extends HandlerInterceptorAdapter {
	@Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
  	HttpSession session = request.getSession();
  	int level = session.getAttribute("sLevel")==null ? 99 : (int) session.getAttribute("sLevel");
  	if(level != 0) {		// 관리자가 아닌경우 이곳에 모두 적용을 받는다.
  		RequestDispatcher dispatcher = request.getRequestDispatcher("/msg/adminRecognizeNo");
  		dispatcher.forward(request, response);
  		return false;
  	}
  	
  	return true;
  }
}
