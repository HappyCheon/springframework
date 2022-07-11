<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>personList.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <h2>인적자원 리스트</h2>
  <hr/>
  <table class="table table-hover text-center">
    <tr>
      <th>번호</th>
      <th>아이디</th>
      <th>비밀번호</th>
      <th>성명</th>
      <th>나이</th>
      <th>주소</th>
    </tr>
    <c:forEach var="vo" items="${vos}" varStatus="st">
      <tr>
        <td>${st.count}</td>
        <td>${vo.mid}</td>
        <td>${vo.pwd}</td>
        <td>${vo.name}</td>
        <td>${vo.age}</td>
        <td>${vo.address}</td>
      </tr>
    </c:forEach>
    <tr><td colspan="6" class="p-0"></td></tr>
  </table>
  <br/>
  <div class="text-center">
    <input type="button" value="등록창으로가기" onclick="location.href='personInput';" class="btn btn-secondary"/>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>