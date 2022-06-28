<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>pdsInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
	  'use strict';
		let cnt = 1;
	  
	  function fCheck() {
    	let maxSize = 1024 * 1024 * 20;
    	let title = $("#title").val();
    	let pwd = $("#pwd").val();
    	let file = $("#file").val();
    	
    	if(file == "" || file == null) {
    		alert("업로드할 파일을 선택하세요.");
    		return false;
    	}
    	else if(title.trim() == "") {
    		alert("파일제목을 입력하세요.");
    		document.getElementById("title").focus();
    		return false;
    	}
    	else if(pwd.trim() == "") {
    		alert("비밀번호를 입력하세요.");
    		document.getElementById("pwd").focus();
    		return false;
    	}
    	
    	let fileSize = 0;
    	let files = document.getElementById("file").files;
    	//let file = "";
    	for(let i=0; i<files.length; i++) {
    		file = files[i];
    		let fName;
  			
    		if(file.name != "") {
	  			fName = file.name;
	  			let ext = fName.substring(fName.lastIndexOf(".")+1)		// 확장자추출
		    	let uExt = ext.toUpperCase();
	  			
	  			if(uExt != "JPG" && uExt != "GIF" && uExt != "PNG" && uExt != "ZIP" && uExt != "HWP" && uExt != "PPT" && uExt != "PPTX" && uExt != "PDF") {
		    		alert("업로드 가능한 파일은 'JPG/GIF/PNG/ZIP/HWP/PPT/PDF' 입니다.")
		    		return false;
		    	}
	  			else {
	  				fileSize += file.size;
	  			}
    		}
    	}
    	
    	if(fileSize > maxSize) {
    		alert("업로드할 파일의 최대용량은 20MByte 입니다.")
    		return false;
    	}
    	else {
    		myForm.fileSize.value = fileSize;
    		myForm.submit();
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <form name="myForm" method="post" enctype="multipart/form-data">
    <h2>자 료 올 리 기</h2>
    <div class="form-group">
      <input type="file" name="file" id="file" multiple="multiple" class="form-control-file border" accept=".jpg,.gif,.png,.zip,.ppt,.pptx,.hwp,.pdf"/>
    </div>
    <div class="form-group">올린이 : ${sNickName}</div>
    <div class="form-group">
      <label for="title">제목 : </label>
      <input type="text" name="title" id="title" placeholder="자료 제목을 입력하세요" class="form-control" required />
    </div>
    <div class="form-group">
      <label for="content">내용 : </label>
      <textarea rows="4" name="content" id="content" class="form-control"></textarea>
    </div>
    <div class="form-group">
      <label for="part">분류 : </label>
      <select name="part" id="part" size="1" class="form-control">
        <option value="학습">학습</option>
        <option value="여행" selected>여행</option>
        <option value="음식">음식</option>
        <option value="기타">기타</option>
      </select>
    </div>
    <div class="form-group">
      <label for="openSw">공개여부 : </label> &nbsp;
      <input type="radio" name="openSw" value="공개" checked />공개 &nbsp;&nbsp;
      <input type="radio" name="openSw" value="비공개" />비공개
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 : </label>
      <input type="password" name="pwd" id="pwd" placeholder="비밀번호를 입력하세요" class="form-control"/>
    </div>
    <div class="form-group">
      <input type="button" value="자료올리기" onclick="fCheck()" class="btn btn-primary"/> &nbsp;
      <input type="reset" value="다시쓰기" class="btn btn-info"/> &nbsp;
      <input type="button" value="돌아가기" onclick="location.href='${ctp}/pds/pdsList?pag=${pageVo.pag}&pageSize=${pageVo.pageSize}&part=${pageVo.part}';" class="btn btn-secondary"/>
    </div>
    <input type="hidden" name="mid" value="${sMid}"/>
    <input type="hidden" name="nickName" value="${sNickName}"/>
    <input type="hidden" name="fileSize"/>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>