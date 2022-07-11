<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>error500.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide2.jsp"/>
<p><br/></p>
<div class="container">
  <h2>이용에 불편을 주셔서 죄송합니다.</h2>
  <h3>빠른시일내에 복구하도록 하겠습니다.</h3>
  <hr/>
  <div class="text-center"><img src="${ctp}/images/m1.jpg" width="300px"/></div>
  <hr/>
  <p><a href="${ctp}/study/personInput" class="btn btn-secondary">돌아가기</a></p>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>