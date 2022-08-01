<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>thumbnailView.jsp(원본이미지와 썸네일 이미지 출력)</title>
  <%@ include file="/WEB-INF/views/include/bs4.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/nav.jsp" %>
<%@ include file="/WEB-INF/views/include/slide.jsp" %>
<p><br/></p>
<div class="container">
  <h2>썸네일 이미지 확인하기</h2>
  <hr/>
  <p>원본그림
    <img src="${ctp}/data/thumbnail/${oFileName}"/>
  </p><br/><hr/>
  <p>썸네일 이미지
    <img src="${ctp}/data/thumbnail/${tFileName}"/>
  </p><br/><hr/>
  <p><a href="${ctp}/study2/thumbnail" class="btn btn-secondary">돌아가기</a></p>
</div>
<p><br/></p>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>