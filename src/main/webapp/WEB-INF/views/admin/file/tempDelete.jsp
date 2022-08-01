<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>임시파일삭제</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
  <script>
    'use strict';
  
    function fileCheck(folderName) {
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/tempFileLoad",
    		data : {folderName : folderName},
    		success:function(data) {
    			str = ''
		    	str += '<h4>(서버에 저장되어 있는 파일 리스트)</h4>';
		  	  str += '<p>${ctp}/data/~~*.*~~ ('+folderName+')</p>';
		  	  str += '<hr/>';
		  	  str += '<table class="table table-hover text-center">';
		  	  str += '  <tr class="table-dark text-dark">';
		  	  str += '    <th>번호</th>';
		  	  str += '    <th>파일명</th>';
		  	  str += '    <th>그림파일/일반파일</th>';
		  	  str += '    <th>삭제</th>';
		  	  str += '  </tr>';
		  	  for(var i in data) {
		  		  str += '<tr>';
		  		  str += '<td>'+i+'</td>';
		  		  str += '<td>'+data[i]+'</td>';
		  		  str += '<td><a href="${ctp}/data/'+data[i]+'" download="'+data[i]+'">';
		  		  let extNameArr = data[i].split('.');
		  		  extName = extNameArr[extNameArr.length-1].toLowerCase();
		  		  if(extName == 'jpg' || extName == 'png' || extName == 'gif' || extName == 'jpeg') {
		  			  if(folderName == 'DATA') {
		  		  		str += '<img src="${ctp}/data/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'THUMB') {
		  		  		str += '<img src="${ctp}/data/thumbnail/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'DBSHOP') {
		  		  		str += '<img src="${ctp}/data/dbShop/'+data[i]+'" width="150px"/>';
		  			  }
		  			  else if(folderName == 'BOARD') {
		  		  		str += '<img src="${ctp}/data/dbShop/'+data[i]+'" width="150px"/>';
		  			  }
		  		  } else {
		  		  	str += data[i];
		  		  }
		  		  str += '</a></td>';
			  	  str += '<td><a href="javascript:fileDelete(\''+folderName+'\',\''+data[i]+'\')" class="btn btn-outline-secondary btn-sm">삭제</a></td>';
		  		  str += '</tr>';
		  	  }
		  	  str += '</table>';
		  	  document.getElementById("demo").innerHTML = str;
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 파일 1개씩 삭제처리
    function fileDelete(folderName,file) {
    	let ans = confirm("파일을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/fileDelete",
    		data : {file : file,
    			      folderName : folderName},
    		success:function(res) {
    			if(res == "1") {
    				alert("삭제처리 되었습니다.");
    				location.reload();
    			}
    			else {
    				alert("삭제 실패(폴더는 삭제하실수 없습니다.)~~~");
    			}
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    // 임시파일이 저장되어 있는 폴더의 그림가져와서 보여주기
    function folderCheck(folder) {
    	let folderName = folder.value
    	if(folderName == "") {
    		alert("폴더명을 선택하세요!");
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/admin/tempFileLoad",
    		data : {folderName : folderName},
    		success:function(data) {
    			let str = "";
    			str += '<form name="myForm">';
		    	str += '<h4>(서버에 저장되어 있는 파일 리스트)</h4>';
		  	  str += '<p>${ctp}/data/~~*.*~~ ('+folderName+')</p>';
		  	  str += '<hr/>';
		  	  str += '<div>';
		  	  str += '<input type="checkbox" id="checkAll" onclick="checkAllCheck()"/>전체선택/해제 &nbsp;';
		  		str += '<input type="checkbox" id="reverseAll" onclick="reverseAllCheck()"/>선택반전 &nbsp;';
		  		str += '<input type="button" value="선택항목삭제" onclick="selectDelCheck()" class="btn btn-danger btn-sm"/>';
		  		str += '</div>';
		  	  str += '<table class="table table-hover text-center">';
		  	  str += '  <tr class="table-dark text-dark">';
		  	  str += '    <th>선택</th>';
		  	  str += '    <th>번호</th>';
		  	  str += '    <th>파일명</th>';
		  	  str += '    <th>그림파일/일반파일</th>';
		  	  str += '    <th>삭제</th>';
		  	  str += '  </tr>';
		  	  for(var i in data) {
		  		  if(data[i] != 'board' && data[i] != 'product') {
			  		  str += '<tr>';
			  		  str += '<td><input type="checkbox" name="chk" class="chk" value="'+data[i]+'"/></td>';
			  		  str += '<td>'+(parseInt(i)+1)+'</td>';
			  		  str += '<td>'+data[i]+'</td>';
			  		  let extNameArr = data[i].split('.');
			  		  let extName = extNameArr[extNameArr.length-1].toLowerCase();
			  		  if(extName == 'jpg' || extName == 'png' || extName == 'gif' || extName == 'jpeg') {
			  			  if(folderName == 'ckeditor') {
					  		  str += '<td><a href="${ctp}/data/ckeditor/'+data[i]+'" download="'+data[i].substring(13)+'">';
			  		  		str += '<img src="${ctp}/data/ckeditor/'+data[i]+'" width="150px"/>';
			  			  }
			  			  else if(folderName == 'dbShop') {
					  		  str += '<td><a href="${ctp}/data/dbShop/'+data[i]+'" download="'+data[i].substring(13)+'">';
			  		  		str += '<img src="${ctp}/data/dbShop/'+data[i]+'" width="150px"/>';
			  			  }
			  		  }
			  		  else {
			  		  	str += '<td>'+data[i]+'</td>';
			  		  }
			  		  str += '</a></td>';
				  	  str += '<td><a href="javascript:fileDelete(\''+folderName+'\',\''+data[i]+'\')" class="btn btn-outline-secondary btn-sm">삭제</a></td>';
			  		  str += '</tr>';
		  		  }
		  	  }
		  	  str += '</table>';
		  	  str += '</form>';
		  	  document.getElementById("demo").innerHTML = str;
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
	  // 전체선택
    function checkAllCheck() {
  		if($("#checkAll").prop("checked")) {
    		$(".chk").prop("checked", true);
  		}
  		else {
    		$(".chk").prop("checked", false);
  		}
    }
    
    // 선택항목 반전
    function reverseAllCheck() {
  		$(".chk").prop("checked", function(){
  			return !$(this).prop("checked");
  		});
    }
	    
    // 선택항목 삭제하기(ajax처리하기)
    function selectDelCheck() {
    	let ans = confirm("선택된 모든 게시물을 삭제 하시겠습니까?");
    	let folderName = $("#folderName").val();
    	if(!ans) return false;
    	let delItems = "";
    	for(var i=0; i<myForm.chk.length; i++) {
    		if(myForm.chk[i].checked == true) delItems += myForm.chk[i].value + "/";
    	}
  		
    	$.ajax({
    		type : "post",
    		data : {
    			folderName : folderName,
    			delItems : delItems},
    		success:function() {
    			alert("선택항목을 모두 삭제처리하였습니다.");
    			location.reload();
    		},
    		error  :function() {
    			alert("전송오류!!");
    		}
    	});
    }
    
    // 부드럽게 화면 스크롤시키기
    $(document).ready(function() {
	    $(window).scroll(function() {
	    	if($(this).scrollTop() > 200) {
	    		$( '.top' ).fadeIn();
	    	}
	    	else {
	    		$('.top').fadeOut();
	    	}
	    });
	    $('.top').click(function(){
	    	$('html, body').animate({scrollTop:0}, 400);
	    	return false;
	    });
    });
  </script>
  <style>
  /* 
    div.container {
      margin: auto;
      width: 500px;
      height: 2000px;
      border: 1px solid #bcbcbc;
    }
     */
    a.top {
      position: fixed;
      right: 0%;
      bottom: 50px;
      display: none;
    }
  </style>
</head>
<body>
<p><br></p>
<div class="container">
  <h2>임시파일 삭제 처리</h2>
  <hr/>
  <%-- 
  <p>
    <input type="button" value="파일보기" onclick="fileCheck('CKEDITOR')" class="btn btn-secondary btn-sm"/> /
  	<a href="${ctp}/admin/ckeditorTempDelete" class="btn btn-secondary btn-sm">CKEditor 임시파일 삭제</a>
  </p>
  <p>
    <input type="button" value="파일보기" onclick="fileCheck('DBSHOP')" class="btn btn-secondary btn-sm"/> /
    <a href="${ctp}/admin/productTempDelete" class="btn btn-secondary btn-sm">상품등록 임시파일 삭제</a>
  </p>
  <p>
    <input type="button" value="파일보기" onclick="fileCheck('DATA')" class="btn btn-secondary btn-sm"/> /
    <a href="${ctp}/admin/dataTempDelete" class="btn btn-secondary btn-sm">데이터폴더의 임시파일 삭제</a>
  </p>
  <p>
    <input type="button" value="파일보기" onclick="fileCheck('THUMB')" class="btn btn-secondary btn-sm"/> /
    <a href="${ctp}/admin/thumbnailTempDelete" class="btn btn-secondary btn-sm">썸네일 연습파일 삭제</a>
  </p>
   --%>
  <p>
    임시파일 저장폴더명 선택
    <select name="folderName" id="folderName" onchange="folderCheck(this)">
      <option value="">폴더명선택</option>
      <option>ckeditor</option>
      <option>dbShop</option>
    </select>
  </p>
  
  <hr/>
  <div id="demo"></div>
  <hr/>
</div>
<div><a href="#" class="top">Top</a></div>
<br/>
</body>
</html>