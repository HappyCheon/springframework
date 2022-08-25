<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>adLeft.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script>
    function logoutCheck() {
    	parent.location.href = "${ctp}/member/memLogout";
    }
  </script>
</head>
<body>
<br/>
<div class="container text-center">
  <h4><a href="${ctp}/admin/adContent" target="adContent" class="btn btn-secondary btn-sm">관리자메뉴</a></h4>
  <div class="panel-group text-center" id="accordion">
    <div class="panel panel-default">
      <div class="panel-heading">
        <div class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse4"><span style="font-size:11pt">케뮤니케이션</span></a>
        </div>
      </div>
      <div id="collapse4" class="panel-collapse collapse">
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/" target="adContent">방명록</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/" target="adContent">게시판</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/" target="adContent">자료실</a></div>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <div class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">상품관리</a>
        </div>
      </div>
      <div id="collapse2" class="panel-collapse collapse">
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/dbShop/dbCategory" target="adContent">상품분류등록</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/dbShop/dbProduct" target="adContent">상품등록관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/dbShop/dbShopList" target="adContent">상품등록조회</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/dbShop/dbOption" target="adContent">옵션등록관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/dbShop/adminOrderStatus" target="adContent">주문관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/admin/adInquiryList" target="adContent">1:1문의</a></div>
      </div>
    </div>
    <div class="panel panel-default">
      <div class="panel-heading">
        <div class="panel-title">
          <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">기타관리</a>
        </div>
      </div>
      <div id="collapse3" class="panel-collapse collapse">
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/admin/adMemberList" target="adContent">회원관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/admin/qrCodeTicket" target="adContent">QR코드관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/notify/nList" target="adContent">공지사항관리</a></div>
        <div class="panel-body pt-2 pb-2"><a href="${ctp}/admin/imsiFileDelete" target="adContent">임시파일관리</a></div>
      </div>
    </div>
  </div>
  <hr/>
  <p><a href="${ctp}/" target="_top" class="btn btn-secondary btn-sm"> 홈 으 로 </a></p>
  <p><a href="javascript:logoutCheck()" class="btn btn-secondary btn-sm">로그아웃</a></p>
  <hr/>
  <h5><a href="${ctp}/admin/adContent" target="adContent" class="btn btn-secondary btn-sm">관리자메뉴</a></h5>
  <hr/>
</div>
</body>
</html>