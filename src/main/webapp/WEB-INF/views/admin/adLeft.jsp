<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>adLeft.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function logoutCheck() {
    	parent.location.href = "${ctp}/member/memLogout";
    }
  </script>
  <style>
    body {background-color: #ddd}
  </style>
</head>
<body>
<br/>
<div class="container text-center" style="font-size:12px;">
  <h6><a href="${ctp}/admin/adContent" target="adContent">관리자메뉴</a></h6>
  <hr/>
  <p><a href="${ctp}/" target="adContent">공지사항</a></p>
  <hr/>
  <p><a href="${ctp}/" target="adContent">방명록</a></p>
  <p><a href="${ctp}/" target="adContent">회원관리</a></p>
  <p><a href="${ctp}/" target="adContent">게시판</a></p>
  <p><a href="${ctp}/" target="adContent">자료실</a></p>
  <hr/>
  <p><a href="${ctp}/dbShop/dbCategory" target="adContent">상품분류등록</a></p>
  <p><a href="${ctp}/dbShop/dbProduct" target="adContent">상품등록관리</a></p>
  <p><a href="${ctp}/dbShop/dbShopList" target="adContent">상품등록조회</a></p>
  <p><a href="${ctp}/dbShop/dbOption" target="adContent">옵션등록관리</a></p>
  <p><a href="${ctp}/dbShop/dbOrderProcess" target="adContent">주문관리</a></p>
  <hr/>
  <p><a href="${ctp}/" target="adContent">임시파일관리</a></p>
  <hr/>
  <p><a href="${ctp}/" target="_top">홈으로</a></p>
  <p><a href="javascript:logoutCheck()">로그아웃</a></p>
</div>
</body>
</html>