<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>cart.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    'use strict'
    function cartReset() {
    	let ans = confirm("주문을 취소하시겠습니까?");
    	if(ans) location.href = "cartReset";
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <h2>세션 장바구니 보기</h2>
  <hr/>
  <p>${sNickName}님께서 구매하신 품목입니다.</p>
  <hr/>
  <div>
    <c:set var="tempProduct" value=""/> <!-- 상품명을 비교하기위한 변수 -->
    <c:set var="productCnt" value="${fn:length(productList)}"/>	<!-- 총상품개수 -->
    <c:set var="cnt" value="0"/>	<!-- 개별상품수량 -->
    <c:set var="sw" value="0"/>	<!-- 첫번째 상품을 저장(초기화)하기 위한 변수(1회만 사용할것이다.) -->
    <p>
      구입한 품목의 총 수량 : <b>${productCnt}개</b><br/>
      구입한 품목의 총 가격 : <b><fmt:formatNumber value="${sTotPrice}"/>원</b>
    </p>
    <hr/>
    <c:forEach var="product" items="${productList}">
      <c:if test="${tempProduct == product}">	<!-- 다음상품을 읽었을때, 같은상품이면 수량을 누적처리 -->
      	<c:set var="cnt" value="${cnt + 1}"/>
      </c:if>
      <c:if test="${tempProduct != product}">	<!-- 다음상품을 읽었을때, 다른상품이면 기존상품을 출력하고, cnt는 초기화, 새로운상품을 tempProduct에 저장한다. -->
        <c:if test="${sw != 0}">
          <p>
		      	<c:set var="productArr" value="${fn:split(tempProduct, '/')}"/>
		      	- 상품명 : <b>${productArr[0]}(<fmt:formatNumber value="${productArr[1]}"/>원)</b> / 수량 : <b>${cnt}</b>개 : &nbsp;
		      	<a href="cartSub?product=${tempProduct}">(-)</a>
		      	<a href="cartAdd?product=${tempProduct}">(+)</a>
		      	<a href="cartDel?product=${tempProduct}" class="badge badge-danger">주문취소</a>
		      	<c:set var="tempProduct" value="${product}"/>
		      	<c:set var="cnt" value="1"/>
	      	</p>
      	</c:if>
      	<c:if test="${sw == 0}">	<!-- 첫번째 상품은 초기화 작업을 한다.(상품을 tempProduct에 담고, cnt=1, sw=1) -->
      	  <c:set var="tempProduct" value="${product}"/>
      	  <c:set var="cnt" value="1"/>
      	  <c:set var="sw" value="1"/>
      	</c:if>
      </c:if>
    </c:forEach>
    <p>
    	<c:set var="productArr" value="${fn:split(tempProduct, '/')}"/>
    	- 상품명 : <b>${productArr[0]}(<fmt:formatNumber value="${productArr[1]}"/>원)</b> / 수량 : <b>${cnt}</b>개 : &nbsp;
    	<a href="cartSub?product=${tempProduct}">(-)</a>
    	<a href="cartAdd?product=${tempProduct}">(+)</a>
    	<a href="cartDel?product=${tempProduct}" class="badge badge-danger">주문취소</a>
    </p>
    <hr/>
    <p>
      <a href="${ctp}/sessionShop/shopList" class="btn btn-secondary">계속쇼핑하기</a> &nbsp;
      <a href="javascript:cartReset()" class="btn btn-secondary">전체주문취소</a> &nbsp;
      <a href="" class="btn btn-secondary">주문하기</a> &nbsp;
    </p>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>