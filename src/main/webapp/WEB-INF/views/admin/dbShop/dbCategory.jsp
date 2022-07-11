<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>dbCategory.jsp(상품 분류)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
  	'use strict';
  
    // 대분류 등록하기 
    function categoryMainCheck() {
    	let categoryMainCode = categoryMainForm.categoryMainCode.value;
    	let categoryMainName = categoryMainForm.categoryMainName.value;
    	if(categoryMainCode == "" || categoryMainName == "") {
    		alert("대분류명(코드)을 입력하세요");
    		categoryMainForm.categoryMainName.focus();
    		return false;
    	}
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/categoryMainInput",
    		data : {
    			categoryMainCode : categoryMainCode,
    			categoryMainName : categoryMainName
    		},
    		success:function(res) {
    			if(res == "0") {
    				alert("같은 상품이 등록되어 있습니다.\n확인하시고 다시 입력하세요.");
    			}
    			else {
    				alert("대분류가 등록되었습니다.");
    				location.reload();
    			}
    		},
    		error : function() {
    			alert('전송오류!');
    		}
    	});
    }
    
    // 중분류 등록하기 
    function categoryMiddleCheck() {
    	let categoryMainCode = categoryMiddleForm.categoryMainCode.value;
    	let categoryMiddleCode = categoryMiddleForm.categoryMiddleCode.value;
    	let categoryMiddleName = categoryMiddleForm.categoryMiddleName.value;
    	if(categoryMainCode == "") {
    		alert("대분류명을 선택하세요");
    		return false;
    	}
    	if(categoryMiddleCode == "") {
    		alert("중분류코드를 입력하세요(01/02/03/...)");
    		categoryMiddleForm.categoryMiddleCode.focus();
    		return false;
    	}
    	if(categoryMiddleName == "") {
    		alert("중분류명을 입력하세요");
    		categoryMiddleForm.categoryMiddleName.focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/categoryMiddleInput",
    		data : {
    			categoryMainCode   : categoryMainCode,
    			categoryMiddleCode : categoryMiddleCode,
    			categoryMiddleName : categoryMiddleName
    		},
    		success:function(data) {
    			if(data == "0") {
    				alert("같은 상품이 등록되어 있습니다.\n확인하시고 다시 입력하세요.");
    			}
    			else {
    				alert("중분류가 등록되었습니다.");
    				location.reload();
    			}
    		},
    		error : function() {
    			alert('전송오류!');
    		}
    	});
    }
    
    // 소분류 등록하기 
    function categorySubCheck() {
    	let categoryMainCode = categorySubForm.categoryMainCode.value;
    	let categoryMiddleCode = categorySubForm.categoryMiddleCode.value;
    	let categorySubCode = categorySubForm.categorySubCode.value;
    	let categorySubName = categorySubForm.categorySubName.value;
    	if(categoryMainCode == "") {
    		alert("대분류명을 선택하세요");
    		return false;
    	}
    	if(categoryMiddleCode == "") {
    		alert("중분류명을 선택하세요");
    		return false;
    	}
    	if(categorySubCode == "") {
    		alert("소분류코드를 입력하세요");
    		categorySubForm.categorySubCode.focus();
    		return false;
    	}
    	if(categorySubName == "") {
    		alert("소분류명을 입력하세요");
    		categorySubForm.categorySubName.focus();
    		return false;
    	}
    	
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/categorySubInput",
    		data : {
    			categoryMainCode : categoryMainCode,
    			categoryMiddleCode : categoryMiddleCode,
    			categorySubCode : categorySubCode,
    			categorySubName : categorySubName
    		},
    		success:function(data) {
    			if(data == "0") {
    				alert("같은 상품이 등록되어 있습니다.\n확인하시고 다시 입력하세요.");
    			}
    			else {
    				alert("소분류가 등록되었습니다.");
    				location.reload();
    			}
    		},
    		error : function() {
    			alert('전송오류!');
    		}
    	});
    }
    
    // 대분류 삭제
    function delCategoryMain(categoryMainCode) {
    	let ans = confirm("대분류항목을 삭제하시겠습니까?");
    	if(!ans) return false;
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/delCategoryMain",
    		data : {categoryMainCode : categoryMainCode},
    		success:function(data) {
    			if(data == 0) {
    				alert("하위항목이 있기에 삭제할 수 없습니다.\n하위항목을 먼저 삭제하세요.");
    			}
    			else {
    				alert("대분류 항목이 삭제 되었습니다.");
    				location.reload();
    			}
    		}
    	});
    }
    
    // 중분류 삭제
    function delCategoryMiddle(categoryMiddleCode) {
    	let ans = confirm("중분류항목을 삭제하시겠습니까?");
    	if(!ans) return false;
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/delCategoryMiddle",
    		data : {categoryMiddleCode : categoryMiddleCode},
    		success:function(data) {
    			if(data == 0) {
    				alert("하위항목이 있기에 삭제할 수 없습니다.\n하위항목을 먼저 삭제하세요.");
    			}
    			else {
    				alert("중분류 항목이 삭제 되었습니다.");
    				location.reload();
    			}
    		}
    	});
    }
    
    // 소분류 삭제
    function delCategorySub(categorySubCode) {
    	let ans = confirm("소분류항목을 삭제하시겠습니까?");
    	if(!ans) return false;
    	$.ajax({
    		type : "post",
    		url  : "${ctp}/dbShop/delCategorySub",
    		data : {categorySubCode : categorySubCode},
    		success:function(data) {
    			if(data == "0") {
    				alert("하위항목이 있기에 삭제할 수 없습니다.\n하위항목을 먼저 삭제하세요.");
    			}
    			else {
    				alert("소분류 항목이 삭제 되었습니다.");
    				location.reload();
    			}
    		}
    	});
    }
    
    // 소분류 입력창에서 대분류 선택(Change)시 수행
    function categoryMainChange() {
    	let categoryMainCode = categorySubForm.categoryMainCode.value;
			$.ajax({
				type : "post",
				url  : "${ctp}/dbShop/categoryMiddleName",
				data : {categoryMainCode : categoryMainCode},
				success:function(data) {
					let str = "";
					str += "<option value=''>중분류</option>";
					for(let i=0; i<data.length; i++) {
						str += "<option value='"+data[i].categoryMiddleCode+"'>"+data[i].categoryMiddleName+"</option>";
					}
					$("#categoryMiddleCode").html(str);
				},
				error : function() {
					alert("전송오류!");
				}
			});
    }
  </script>
</head>
<body>
<p><br></p>
<div class="container">
  <h2>상품 분류 등록하기</h2>
  <hr/>
  <form name="categoryMainForm">
  	<h5>대분류(제조사) 관리(코드는 영문대문자1자리)</h5>
  	대분류코드(A,B,C,...)
  	<input type="text" name="categoryMainCode" size="2"/>, &nbsp;
  	대분류명
  	<input type="text" name="categoryMainName" size="8"/> &nbsp;
  	<input type="button" value="대분류등록" onclick="categoryMainCheck()" class="btn btn-primary btn-sm"/>
	  <table class="table table-hover m-3">
	    <tr class="table-dark text-dark text-center">
	      <th>대분류코드</th>
	      <th>대분류명</th>
	      <th>삭제</th>
	    </tr>
	    <c:forEach var="mainVo" items="${mainVos}" varStatus="st">
	    	<tr class="text-center">
	    	  <td>${mainVo.categoryMainCode}</td>
	    	  <td>${mainVo.categoryMainName}</td>
	    	  <td><input type="button" value="삭제" onclick="delCategoryMain('${mainVo.categoryMainCode}')" class="btn btn-danger btn-sm"/></td>
	    	</tr>
	    </c:forEach>
	  </table>
  </form>
  <hr/><hr/>
  <form name="categoryMiddleForm">
  	<h5>중분류 관리(코드는 숫자 2자리)</h5>
  	<select name="categoryMainCode">
  	  <option value="">대분류명</option>
  	  <c:forEach var="mainVo" items="${mainVos}">
  	    <option value="${mainVo.categoryMainCode}">${mainVo.categoryMainName}</option>
  	  </c:forEach>
  	</select> &nbsp; &nbsp;
  	- 중분류코드(01,02,...)
  	<input type="text" name="categoryMiddleCode" size="2"/> , &nbsp;
  	중분류명
  	<input type="text" name="categoryMiddleName" size="8"/> &nbsp;
  	<input type="button" value="중분류등록" onclick="categoryMiddleCheck()" class="btn btn-success btn-sm"/>
	  <table class="table table-hover m-3">
	    <tr class="table-dark text-dark text-center">
	      <th>중분류코드명</th>
	      <th>중분류명</th>
	      <th>대분류명</th>
	      <th>삭제</th>
	    </tr>
	    <c:forEach var="middleVo" items="${middleVos}" varStatus="st">
	    	<tr class="text-center">
	    	  <td>${middleVo.categoryMiddleCode}</td>
	    	  <td>${middleVo.categoryMiddleName}</td>
	    	  <td>${middleVo.categoryMainName}</td>
	    	  <td><input type="button" value="삭제" onclick="delCategoryMiddle('${middleVo.categoryMiddleCode}')" class="btn btn-danger btn-sm"/></td>
	    	</tr>
	    </c:forEach>
	  </table>
  </form>
  <hr/><hr/>
  <form name="categorySubForm">
  	<h5>소분류 관리(코드는 숫자 3자리)</h5>
  	<select name="categoryMainCode" onchange="categoryMainChange()">
  	  <option value="">대분류명</option>
  	  <c:forEach var="mainVo" items="${mainVos}">
  	    <option value="${mainVo.categoryMainCode}">${mainVo.categoryMainName}</option>
  	  </c:forEach>
  	</select>
  	<select name="categoryMiddleCode" id="categoryMiddleCode">
  	  <option value="">중분류명</option>
  	  <c:forEach var="middleVo" items="${middleVos}">
  	    <option value=""></option>
  	  </c:forEach>
  	</select>
  	소분류코드(001,002,..)
  	<input type="text" name="categorySubCode" size="1"/> , &nbsp;
  	소분류명
  	<input type="text" name="categorySubName" size="3"/> &nbsp;
  	<input type="button" value="소분류등록" onclick="categorySubCheck()" class="btn btn-info btn-sm"/>
	  <table class="table table-hover m-3">
	    <tr class="table-dark text-dark text-center">
	      <th>소분류코드명</th>
	      <th>소분류명</th>
	      <th>중분류명</th>
	      <th>대분류명</th>
	      <th>삭제</th>
	    </tr>
	    <c:forEach var="subVo" items="${subVos}" varStatus="st">
	    	<tr class="text-center">
	    	  <td>${subVo.categorySubCode}</td>
	    	  <td>${subVo.categorySubName}</td>
	    	  <td>${subVo.categoryMiddleName}</td>
	    	  <td>${subVo.categoryMainName}</td>
	    	  <td><input type="button" value="삭제" onclick="delCategorySub('${subVo.categorySubCode}')" class="btn btn-danger btn-sm"/></td>
	    	</tr>
	    </c:forEach>
	  </table>
  </form>
  <hr/><hr/>
</div>
<br/>
</body>
</html>