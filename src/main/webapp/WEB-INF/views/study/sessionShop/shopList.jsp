<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>shopList.jsp(상품진열화면)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
    function fCheck() {
    	let product = myForm.product.value;
    	if(product == "") {
    		alert("상품을 선택하세요!");
    		return false;
    	}
    	let productArr = product.split("/");
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/sessionShop/shopList",
    		data : {product : product},
    		success:function() {
    			alert("구매품목("+productArr[0]+")이 장바구니에 담겼습니다.");
    		},
    		error : function() {
    			alert("전송오류!!(장바구니에 물건이 담기지 않았습니다.)");
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <h2>세션을 이용한 상품 구매하기</h2>
  <hr/>
  <h4>상품리스트</h4>
  <p>(이곳의 상품들은 DB에 저장되어 있는 상품리스트를 가져와서 구성해야 한다.)</p>
  <div>
    - 사과 : 500원<br/>
    - 배 : 1000원<br/>
    - 바나나 : 2500원<br/>
    - 포도 : 3000원<br/>
    - 딸기 : 5500원<br/>
    - 키위 : 3500원<br/>
    - 복숭아 : 1500원<br/>
  </div>
  <hr/>
  <form name="myForm" method="post">
    <select name="product">
      <option value="">상품선택</option>
      <option value="사과/500">사과</option>
      <option value="배/1000">배</option>
      <option value="바나나/2500">바나나</option>
      <option value="포도/3000">포도</option>
      <option value="딸기/5500">딸기</option>
      <option value="키위/3500">키위</option>
      <option value="복숭아/1500">복숭아</option>
    </select> &nbsp;
    <input type="button" value="장바구니담기" onclick="fCheck()" class="btn btn-secondary"/><br/>
    <hr/>
    <p>
      <input type="button" value="장바구니보기" onclick="location.href='cart';" class="btn btn-primary"/>
    </p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>