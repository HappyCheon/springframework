<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>personInput.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <hr/>
  <pre>
    <h3>Transaction 이란?</h3>
    두개의 자료를 동시에 입력처리 시키고자 할때,
    1개의 자료가 먼저 입력되고,
    2번째 자료처리에서 문제가 발생된다면,
    이때 기존에 입력된 1번째 자료도 다시 입력을 거둬줘야한다. - Roll Back
    따라서 두개 테이블이 모두 입력처리 되지 않도록 처리하여,
    입력전과 같은 상태로 만들어주는 것이 주 목적이다.(원자성)
  </pre>
  <hr/>
  <form name="myForm" method="post">
	  <h2>인적 자원 정보 등록하기</h2>
    <table class="table table-bordered text-center">
			<tr>
			  <th>아이디</th>
			  <td><input type="text" name="mid" class="form-control" autofocus /></td>
			</tr>
			<tr>
			  <th>비밀번호</th>
			  <td><input type="password" name="pwd" class="form-control"></td>
			</tr>
			<tr>
			  <th>성명</th>
			  <td><input type="text" name="name" class="form-control"></td>
			</tr>
			<tr>
			  <th>나이</th>
			  <td><input type="number" name="age" value="20" class="form-control"></td>
			</tr>
			<tr>
			  <th>주소</th>
			  <td><input type="text" name="address" class="form-control"></td>
			</tr>
			<tr>
			  <td colspan="2">
			  	<input type="submit" value="등록" class="btn btn-secondary"/> &nbsp;
			  	<input type="reset" value="다시입력" class="btn btn-danger"/> &nbsp;
			  	<input type="button" value="리스트 가기" onclick="location.href='personList';" class="btn btn-info"/>
			  </td>
			</tr>
    </table>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>