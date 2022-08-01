<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>qrcode.jsp</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
  <script>
    'use strict';
    
    function qrCreate(no) {
    	let moveUrl = '';
    	if(no == 1)	{
    		moveUrl = myForm.moveUrl.value;
    	}
    	else {
    		moveUrl = myForm.email.value;
    	}
    	
    	$.ajax({
  			url  : "${ctp}/study/qrCreate",
  			type : "post",
  			data : {moveUrl : moveUrl},
  			success : function(data) {
 					alert("qr코드 생성완료 : "+data);
 					$("#qrCodeView").show();
 					$("#qrView").html(data);
 					var qrImage = '<img src="${ctp}/data/qrCode/'+data+'.png"/>';
 					$("#qrImage").html(qrImage);
				}
			});
		}
  </script>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide2.jsp" %>
<p><br/></p>
<div class="container">
  <form name="myForm">
	  <h2>QR코드 생성하기</h2>
	  <hr/>
	  <div>
	    <h4>QR코드 체크시 이동할 주소를 입력후 QR코드를 생성해 주세요.</h4>
	    <p>(소개하고 싶은 지역의 홈페이지 주소를 입력하세요.)</p>
	  </div>
	  <p>
	    이동할 주소1 : <input type="text" name="moveUrl" value="http://blog.daum.net/cjsk1126" size="30"/>
	    <input type="button" value="qr코드 생성1" onclick="qrCreate(1)" class="btn btn-primary btn-sm"/>
	  </p>
	  <hr/>
	  <div>
	    <h4>자신의 정보(email)를 입력후 QR코드를 생성해 주세요.</h4>
	    <p>티켓예매 : 생성된 이메일주소를 사진촬영후 매표소에 제시해주세요.</p>
	    
	  </div>
	  <p>
	    <b>개인정보입력</b> :<br/>
	    이메일 : <input type="text" name="email" value="${email}"/> &nbsp;
	    <input type="button" value="qr코드 생성2" onclick="qrCreate(2)" class="btn btn-success btn-sm"/>
	  </p>
	  <hr/>
	  <div id="qrCodeView" style="display:none">
	    <h3>생성된 QR코드 확인하기</h3>
	    <div>
		  - 생성된 qr코드명 : <span id="qrView"></span><br/>
		  <span id="qrImage"></span>
		  </div>
	  </div>
  </form>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>