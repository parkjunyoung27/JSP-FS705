<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Password Update Result</title>
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
	<c:choose>
		<c:when test="${updateResult eq 1}">
			패스워드 변경에 성공했습니다.
			<button type="button" onclick="location.href='./index.jsp'">로그인하러 가기</button>
		</c:when>
		<c:otherwise>
			변경에 실패했습니다. 다시 시도해주세요.<br>
			<button type="button" onclick="location.href='./pwFind.jsp'">돌아가기</button>
		</c:otherwise>
	</c:choose>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>