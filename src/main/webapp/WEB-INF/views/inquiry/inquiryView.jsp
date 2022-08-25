<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>inquiryView.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
	<script>
	  /*  화면에 로딩된 그림 크기 제어하기... 여기선 그림이 1개 이기에 처리하지 않았다.
		$(document).ready(function() {
			var productImage = document.getElementById("productImage");
			
			productImage.onload = function() {
				var imgWidth = productImage.width;
				var imgHeight = productImage.height;
				
				if(imgWidth > imgHeight) $("#productImage").attr('width',300);
				else $("#productImage").attr('height',300);
			}
		});
	  */
	
		function updateCheck() {
			var ans = confirm("수정하시겠습니까?");
			if(!ans) return false;
			else location.href="${ctp}/inquiry/inquiryUpdate?idx=${vo.idx}&pag=${pag}";
		}
		
		function deleteCheck() {
			var ans = confirm("삭제하시겠습니까?");
			if(!ans) return false;
			location.href="${ctp}/inquiry/inquiryDelete?idx=${vo.idx}&fSName=${vo.FSName}&pag=${pag}";
		}
	</script>
	<style>
	  th {
	    background-color: #ccc;
	    text-align:center;
	    width: 15%;
	  }
	</style>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
	<h3>1:1 문의</h3>
	<table class="table table-bordered">
		<tr>
			<th>제목</th>
			<td colspan="3">[${vo.part}] ${vo.title}</td>
		</tr>
		<tr>
			<th>상태</th>
			<td colspan="3">
				<c:if test="${vo.reply=='답변대기중'}">
					<span class="badge badge-pill badge-secondary">${vo.reply}</span>						
				</c:if>
				<c:if test="${vo.reply=='답변완료'}">
					<span class="badge badge-pill badge-danger">${vo.reply}</span>						
				</c:if>
			</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${fn:substring(vo.WDate,0,10)}</td>
			<th>주문번호</th>
			<td>
				<c:if test="${!empty vo.jumunNo}">${vo.jumunNo}</c:if>
				<c:if test="${empty vo.jumunNo}">없음</c:if>	
			</td>
		</tr>
		<tr>
			<td colspan="4">
	      <!-- 이미지 파일  -->
	      <!-- 여러장 처리시는 아래처럼 처리한다.(여기선 1장만 처리하기게 생략한다.)
	    	<c:set var="fName" value="${vo.FName}"/>
	      <c:set var="fSName" value="${vo.FSName}"/>
	      <c:set var="fSNameLen" value="${fn:length(fSName)}"/>
	      <c:set var="fSName" value="${fn:substring(fSName, fSNameLen-4, fSNameLen-1)}"/>
	      <c:set var="fSName" value="${fn:toUpperCase(fSName)}"/>
	      <c:if test="${fSName=='JPG' || fSName=='GIF' || fSName=='PNG'}">
	        <c:set var="imgs" value="${fn:split(fName,'/')}"/>
	        <c:forEach var="img" items="${imgs}" varStatus="st">
	          <img src="${ctp}/data/inquiry/${img}" width="400px" id="productImage"/><br/><br/>
	          <%-- <img src="${ctp}/data/inquiry/${img}" id="productImage"/><br/><br/> --%>
	        </c:forEach>
	      </c:if>
	      -->
	      <c:if test="${!empty vo.FSName}"><img src="${ctp}/inquiry/${vo.FSName}" width="400px"/><br/></c:if>
	      <br/>
	      <p>${fn:replace(vo.content,newLine,"<br/>")}<br/></p>
	    	<hr/>
			</td>
		</tr>
	</table>
	
	<!-- 관리자가 답변을 달았을때는 현재글을 수정/삭제 처리 못하도록 하고 있다. -->
 	<div style="text-align: right" class="row">
		<span class="col"></span>
		<c:if test="${empty reVO.reContent}">
			  <input type="button" value="수 정" onclick="updateCheck()" class="btn btn-secondary col"/>
				<span class="col"></span>
				<input type="button" value="삭 제" onclick="deleteCheck()" class="btn btn-secondary col"/>
				<span class="col"></span>
		</c:if>
		<input type="button" value="목록으로" onclick="location.href='${ctp}/inquiry/inquiryList?pag=${pag}'" class="btn btn-secondary col"/>
		<span class="col"></span>
	</div>
	
	<hr/>
	<!-- 관리자가 답변을 달았을때 보여주는 구역 -->
	<c:if test="${!empty reVO.reContent}">
		<form name="replyForm">
			<label for="reContent">관리자 답변</label>
			<textarea name="reContent" rows="5"  id="reContent" readonly="readonly" class="form-control">${reVO.reContent}</textarea>
		</form>
	</c:if>
</div>
<br/>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>