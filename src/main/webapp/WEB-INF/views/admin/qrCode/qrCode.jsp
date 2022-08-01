<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>qrCode.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  
	  $(document).ready(function() {
	  	$(".qrCodeBoxCloseBtn").hide();
    	// 티켓 주문 일자별 조회
    	$("#orderDateSearch").click(function() {
	    	let startJumun = document.getElementById("startJumun").value;
	    	let endJumun = document.getElementById("endJumun").value;
	    	let orderStatus = $(this).val();
	    	location.href="${ctp}/admin/qrCodeTicket?startJumun="+startJumun+"&endJumun="+endJumun;
    	});
    });
	  
	  // QR Code보기
	  function qrCodeView(idx, qrCode) {
    	let str = '';
    	str += '<div class="container text-center">';
    	str += '<img src="${ctp}/data/qrCode/'+qrCode+'.png"/> &nbsp;';
    	str += '</div>';
    	
    	$("#qrCodeBoxShowBtn"+idx).hide();
    	$("#qrCodeBoxCloseBtn"+idx).show();
    	$("#qrCodeBox"+idx).slideDown(500);
    	$("#qrCodeBox"+idx).html(str);
	    }
	  
	  // qrCode화면 닫기
	  function qrCodeClose(idx) {
    	$("#qrCodeBoxShowBtn"+idx).show();
    	$("#qrCodeBoxCloseBtn"+idx).hide();
		  $("#qrCodeBox"+idx).slideUp(500);
	  }
	  
	  // qrCode 삭제하기
	  function qrDelCheck(idx, qrCode) {
		  let ans = confirm("선택한 qrCode를 삭제하시겠습니까?");
		  if(!ans) return false;
		  
		  $.ajax({
			  type : "post",
			  url  : "${ctp}/admin/qrCodeDelete",
			  data : {
				  idx : idx,
				  qrCode : qrCode},
			  success:function() {
				  alert("삭제처리하였습니다.");
				  location.reload();
			  },
			  error : function() {
				  alert("전송오류!");
			  }
		  });
	  }
	  
	  // 전체선택
    $(function(){
    	$("#checkAll").click(function(){
    		if($("#checkAll").prop("checked")) {
	    		$(".chk").prop("checked", true);
    		}
    		else {
	    		$(".chk").prop("checked", false);
    		}
    	});
    });
    
    // 선택항목 반전
    $(function(){
    	$("#reverseAll").click(function(){
    		$(".chk").prop("checked", function(){
    			return !$(this).prop("checked");
    		});
    	});
    });
    
    // 선택항목 삭제하기(ajax처리하기)
    function selectDelCheck() {
    	var ans = confirm("선택된 모든 게시물을 삭제 하시겠습니까?");
    	if(!ans) return false;
    	var delItems = "";
    	for(var i=0; i<myForm.chk.length; i++) {
    		if(myForm.chk[i].checked == true) delItems += myForm.chk[i].value + "/";
    	}
  		
    	$.ajax({
    		type : "post",
    		data : {delItems : delItems},
    		success:function() {
    			alert("선택항목을 모두 삭제처리하였습니다.");
    			location.reload();
    		},
    		error  :function() {
    			alert("전송오류!!");
    		}
    	});
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
  <form name="myForm">
	  <h2>티켓 구매 현황(QR Code사용예)</h2>
	  <br/>
	  <table class="table table-borderless">
	    <tr>
	      <td>주문일자 조회 :
	        <!-- <input type="date" name="startJumun" id="startJumun"/> ~<input type="date" name="endJumun" id="endJumun"/> -->
	        <c:if test="${pageVo.startJumun == null or pageVo.startJumun == ''}">
	          <c:set var="startJumun" value="<%=new java.util.Date() %>"/>
		        <c:set var="startJumun"><fmt:formatDate value="${startJumun}" pattern="yyyy-MM-dd"/></c:set>
	        </c:if>
	        <c:if test="${pageVo.endJumun == null or pageVo.endJumun == ''}">
	          <c:set var="endJumun" value="<%=new java.util.Date() %>"/>
		        <c:set var="endJumun"><fmt:formatDate value="${endJumun}" pattern="yyyy-MM-dd"/></c:set>
	        </c:if>
	        <input type="date" name="startJumun" id="startJumun" value="${pageVo.startJumun}"/>~<input type="date" name="endJumun" id="endJumun" value="${pageVo.endJumun}"/>
	        <button type="button" id="orderDateSearch" class="btn btn-outline-secondary btn-sm">조회</button>
	      </td>
	      <td align="right">
					<input type="button" value="전체조회" onclick="location.href='qrCodeTicket';" class="btn btn-success btn-sm"/>
	      </td>
	    </tr>
	  </table>
	  <table class="table table-hover text-center">
	    <tr>
	      <td colspan="6" class="text-left">
	        <input type="checkbox" id="checkAll"/>전체선택/해제 &nbsp;
	        <input type="checkbox" id="reverseAll"/>선택반전 &nbsp;
	        <input type="button" value="선택항목삭제" onclick="selectDelCheck()" class="btn btn-danger btn-sm"/>
	      </td>
	    </tr>
	    <tr style="background-color:#ccc;">
	      <th>선택</th>
	      <th>번호</th>
	      <th>주문날짜시간</th>
	      <th>구매자 아이디</th>
	      <th>구매자 메일주소</th>
	      <th>비고</th>
	    </tr>
	    <c:set var="curScrStartNo" value="${pageVo.curScrStartNo}"/>
	    <c:forEach var="vo" items="${vos}" varStatus="st">
	      <c:set var="codes" value="${fn:split(vo.qrCode,'_')}"/>
	      <tr>
	        <td><input type="checkbox" name="chk" class="chk" value="${vo.idx}"/></td>
	        <td>${curScrStartNo}</td>
	        <td>
	          ${fn:substring(codes[0],0,4)}-${fn:substring(codes[0],4,6)}-${fn:substring(codes[0],6,8)} ${fn:substring(codes[0],8,10)}:${fn:substring(codes[0],10,12)}
	        </td>
	        <td>${codes[1]}</td>
	        <td>${codes[2]}</td>
	        <td>
	          <a href="javascript:qrCodeView('${vo.idx}','${vo.qrCode}')" class="badge badge-primary" id="qrCodeBoxShowBtn${vo.idx}">QrCode</a>
	          <a href="javascript:qrCodeClose(${vo.idx})" class="badge badge-success qrCodeBoxCloseBtn" id="qrCodeBoxCloseBtn${vo.idx}">닫기</a>
	          <a href="javascript:qrDelCheck('${vo.idx}','${vo.qrCode}')" class="badge badge-danger">삭제</a>
	        </td>
	      </tr>
	      <tr>
	        <td colspan="6" class="m-0 p-0" style="border-top:none;"><div id="qrCodeBox${vo.idx}"></div></td>
	      </tr>
	      <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
	    </c:forEach>
	    <tr><td colspan="6" class="p-0"></td></tr>
	  </table>
  </form>
  <p><br/></p>
  <!-- 블록 페이징처리 시작(BS4 스타일적용) -->
	<div class="container">
		<ul class="pagination justify-content-center">
			<c:if test="${pageVo.totPage == 0}"><p style="text-align:center"><b>자료가 없습니다.</b></p></c:if>
			<c:if test="${pageVo.totPage != 0}">
			  <c:if test="${pageVo.pag != 1}">
			    <li class="page-item"><a href="qrCodeTicket?pag=1&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}" title="첫페이지" class="page-link text-secondary">◁◁</a></li>
			  </c:if>
			  <c:if test="${pageVo.curBlock > 0}">
			    <li class="page-item"><a href="qrCodeTicket?pag=${(pageVo.curBlock-1)*pageVo.blockSize + 1}&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}" title="이전블록" class="page-link text-secondary">◀</a></li>
			  </c:if>
			  <c:forEach var="i" begin="${(pageVo.curBlock*pageVo.blockSize)+1}" end="${(pageVo.curBlock*blockSize)+pageVo.blockSize}">
			    <c:if test="${i == pageVo.pag && i <= pageVo.totPage}">
			      <li class="page-item active"><a href='qrCodeTicket?pag=${i}&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}' class="page-link text-light bg-secondary border-secondary">${i}</a></li>
			    </c:if>
			    <c:if test="${i != pageVo.pag && i <= pageVo.totPage}">
			      <li class="page-item"><a href='qrCodeTicket?pag=${i}&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}' class="page-link text-secondary">${i}</a></li>
			    </c:if>
			  </c:forEach>
			  <c:if test="${pageVo.curBlock < pageVo.lastBlock}">
			    <li class="page-item"><a href="qrCodeTicket?pag=${(pageVo.curBlock+1)*pageVo.blockSize + 1}&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}" title="다음블록" class="page-link text-secondary">▶</a>
			  </c:if>
			  <c:if test="${pageVo.pag != pageVo.totPage}">
			    <li class="page-item"><a href="qrCodeTicket?pag=${pageVo.totPage}&startJumun=${pageVo.startJumun}&endJumun=${pageVo.endJumun}" title="마지막페이지" class="page-link" style="color:#555">▷▷</a>
			  </c:if>
			</c:if>
		</ul>
	</div>
	<!-- 블록 페이징처리 끝 -->
</div>
<p><br/></p>
</body>
</html>