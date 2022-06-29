<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    'use strict';
    
    // 부분자료 검색처리
    function partCheck() {
    	let part = partForm.part.value;
    	location.href = "${ctp}/pds/pdsList?part="+part;
    }
    
    // 다운로드 수 증가처리
    function downNumCheck(idx) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/pds/pdsDownNum",
    		data : {idx : idx},
    		success:function() {
    			location.reload();
    		},
    		error : function() {
    			alert("전송오류");
    		}
    	});
    }
    
    // 동적폼을 이용한 비밀번호 박스 보여주기
    function pdsDelCheckOpen(idx) {
    	$(".deletePwd").slideUp(300);
    	$(".delCheckOpenBtn").show();
    	$(".delCheckCloseBtn").hide();
    	
    	let str = '';
    	str += '<div id="deletePwd'+idx+'" class="text-center deletePwd p-2">';
    	str += '현자료의 비밀번호를 입력하세요 : ';
    	str += '<input type="password" name="pwd" id="pwd'+idx+'" />&nbsp;';
    	str += '<input type="button" value="비밀번호확인" onclick="pwdCheck('+idx+')"/>';
    	str += '</div>';
    	
    	$("#delCheckOpenBtn"+idx).hide();
    	$("#delCheckCloseBtn"+idx).show();
    	$("#deleteForm"+idx).slideDown(300);
    	$("#deleteForm"+idx).html(str);
    }
    
    // 비밀번호 확인처리(확인처리후 자료파일 삭제처리수행...)
    function pwdCheck(idx) {
    	let pwd = $("#pwd"+idx).val();
    	if(pwd.trim() == "") {
    		alert("비밀번호를 입력하세요!");
    		$("#pwd"+idx).focus();
    		return false;
    	}
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/pds/pdsPwdCheck",
    		data : {
    			idx : idx,
    			pwd : pwd
    		},
    		success:function(res) {
    			if(res != "1") {
    				alert("비밀번호가 틀립니다.");
    				$("#pwd"+idx).focus();
    			}
    			else {
    				alert("삭제 되었습니다.");
    				location.reload();
    			}
    		},
    		error : function() {
    			alert("전송오류!!!");
    		}
    	});
    }
    
    function pdsDelCheckClose(idx) {
    	$("#delCheckOpenBtn"+idx).show();
    	$("#delCheckCloseBtn"+idx).hide();
    	$("#deleteForm"+idx).slideUp(300);
    }
    
    function newWindow(idx) {
    	let url = "${ctp}/pds/pdsContent?idx="+idx;
    	window.open(url,"newWindow","width=450px,height=600px");
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <h2 class="text-center">자 료 실 리 스 트(${pageVo.part})</h2>
  <br/>
  <table class="table table-borderless">
  	<tr>
  	  <td class="text-left" style="width:20%">
  	    <form name="partForm">
  	      <select name="part" onchange="partCheck()" class="form-control">
  	        <option value="전체" ${pageVo.part == '전체' ? 'selected' : ''}>전체</option>
  	        <option value="학습" ${pageVo.part == '학습' ? 'selected' : ''}>학습</option>
  	        <option value="여행" ${pageVo.part == '여행' ? 'selected' : ''}>여행</option>
  	        <option value="음식" ${pageVo.part == '음식' ? 'selected' : ''}>음식</option>
  	        <option value="기타" ${pageVo.part == '기타' ? 'selected' : ''}>기타</option>
  	      </select>
  	    </form>
  	  </td>
  	  <td class="text-right"><a href="${ctp}/pds/pdsInput" class="btn btn-outline-success">자료올리기</a></td>
  	</tr>
  </table>
  <table class="table table-hover table-striped text-center">
    <tr class="table-dark text-dark">
      <th>번호</th>
      <th>자료제목</th>
      <th>올린이</th>
      <th>올린날짜</th>
      <th>분류</th>
      <th>파일명(사이즈)</th>
      <th>다운수</th>
      <th>비고</th>
    </tr>
    <c:set var="curScrStartNo" value="${pageVo.curScrStartNo}"/>
    <c:forEach var="vo" items="${vos}">
      <tr>
        <td>${curScrStartNo}</td>
        <td>
          <c:if test="${vo.openSw == '공개' || sMid == vo.mid || sLevel == 0}">
          	<a href="javascript:newWindow(${vo.idx});">${vo.title}</a>
          </c:if>
          <c:if test="${vo.openSw != '공개' && sMid != vo.mid && sLevel != 0}">
          	${vo.title}
          </c:if>
        </td>
        <td>${vo.nickName}</td>
        <td>${fn:substring(vo.FDate,0,10)}</td>
        <td>${vo.part}</td>
        <td>
          <c:if test="${vo.openSw == '공개' || sMid == vo.mid || sLevel == 0}">
            <%-- ${vo.fName}<br/> --%>
            <c:set var="fNames" value="${fn:split(vo.FName,'/')}"/>
            <c:set var="fSNames" value="${fn:split(vo.FSName,'/')}"/>
            <c:forEach var="fName" items="${fNames}" varStatus="st">
              <a href="${ctp}/pds/${fSNames[st.index]}" download="${fName}" onclick="downNumCheck(${vo.idx})">${fName}</a><br/>
            </c:forEach>
            (<fmt:formatNumber value="${vo.FSize / 1024}" pattern="#,##0"/>KByte)
          </c:if>
          <c:if test="${vo.openSw != '공개' && sMid != vo.mid && sLevel != 0}">
            비공개
          </c:if>
        </td>
        <td>${vo.downNum}</td>
        <td>
          <c:if test="${sMid == vo.mid || sLevel == 0}">
          	<a href="${ctp}/pds/pdsTotalDown?idx=${vo.idx}&fName=${vo.FName}&fSName=${vo.FSName}&title=${vo.title}" class="btn btn-primary btn-sm">전체다운</a>
          	<a href="javascript:pdsDelCheckOpen(${vo.idx})" id="delCheckOpenBtn${vo.idx}" class="btn btn-danger btn-sm delCheckOpenBtn">삭제</a>
          	<a href="javascript:pdsDelCheckClose(${vo.idx})" id="delCheckCloseBtn${vo.idx}" class="btn btn-info btn-sm delCheckCloseBtn" style="display:none">닫기</a>
          </c:if>
        </td>
      </tr>
      <tr><td colspan="8" class="p-0"><div id="deleteForm${vo.idx}"></div></td></tr>
      <c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
    </c:forEach>
    <tr><td colspan="8" class="p-0"></td></tr>
  </table>
</div>

<!-- 블록 페이징 처리 시작 -->
<div class="text-center">
  <ul class="pagination justify-content-center">
	  <c:if test="${pageVo.pag > 1}">
	    <li class="page-item"><a href="pdsList?part=${pageVo.part}&pag=1" class="page-link text-secondary">◁◁</a></li>
	  </c:if>
	  <c:if test="${pageVo.curBlock > 0}">
	    <li class="page-item"><a href="pdsList?part=${pageVo.part}&pag=${(pageVo.curBlock-1)*pageVo.blockSize + 1}" class="page-link text-secondary">◀</a></li>
	  </c:if>
	  <c:forEach var="i" begin="${(pageVo.curBlock*pageVo.blockSize)+1}" end="${(pageVo.curBlock*pageVo.blockSize)+pageVo.blockSize}">
	    <c:if test="${i <= pageVo.totPage && i == pageVo.pag}">
	      <li class="page-item active"><a href="pdsList?part=${pageVo.part}&pag=${i}" class="page-link text-light bg-secondary border-secondary">${i}</a></li>
	    </c:if>
	    <c:if test="${i <= pageVo.totPage && i != pageVo.pag}">
	      <li class="page-item"><a href='pdsList?part=${pageVo.part}&pag=${i}' class="page-link text-secondary">${i}</a></li>
	    </c:if>
	  </c:forEach>
	  <c:if test="${pageVo.curBlock < pageVo.lastBlock}">
	    <li class="page-item"><a href="pdsList?part=${pageVo.part}&pag=${(pageVo.curBlock+1)*pageVo.blockSize + 1}" class="page-link text-secondary">▶</a></li>
	  </c:if>
	  <c:if test="${pageVo.pag != pageVo.totPage}">
	    <li class="page-item"><a href="pdsList?part=${pageVo.part}&pag=${pageVo.totPage}" class="page-link text-secondary">▷▷</a></li>
	  </c:if>
  </ul>
</div>
<!-- 블록 페이징 처리 끝 -->

<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>