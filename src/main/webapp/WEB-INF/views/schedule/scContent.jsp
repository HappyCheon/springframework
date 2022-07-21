<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>scContent.jsp</title>
	<%@ include file="/WEB-INF/views/include/bs4.jsp" %>
</head>
<body>
<div class="container">
  <p><br/></p>
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">분류 : ${vo.part}</h4>
        <button type="button" class="close"  onclick="window.close()">&times;</button>
      </div>
      <div class="modal-body">
        <hr/>
        - <b>일정날짜</b> : ${fn:substring(vo.SDate,0,10)}
        <hr/>
        <table class='table table-bordered'>
	    	<tr><th>분류</th><td>${vo.part}</td></tr>
	    	<tr><th>내용</th><td>${fn:replace(vo.content,newLine,'<br/>')}</td></tr>
	    	</table>
        <hr/>
      </div>
      <div class="modal-footer">
      	<button type="button" class="btn btn-danger" onclick="window.close()">Close</button>
      </div>
    </div>
  </div>
  <p><br/></p>
</div>
</body>
</html>