<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>thumbnail.jsp(썸네일 이미지 연습)</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp"/>
  <script>
    function imgCheck() {
    	// 그림파일 체크...
    	myform.submit();
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp"/>
<jsp:include page="/WEB-INF/views/include/slide.jsp"/>
<p><br/></p>
<div class="container">
  <h2>썸네일 이미지 생성 연습</h2>
  <form name="myform" method="post" enctype="multipart/form-data">
    <p>파일명 : 
      <input type="file" name="file" id="file" class="form-control-file border" accept=".jpg,.gif,.png"/>
    </p>
    <p>
      <input type="button" value="등록" onclick="imgCheck()" class="btn btn-secondary"/>
      <input type="reset" value="다시등록" class="btn btn-secondary"/>
    </p>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp"/>
</body>
</html>