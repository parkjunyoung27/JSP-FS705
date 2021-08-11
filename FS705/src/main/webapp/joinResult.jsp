<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Result</title>
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
	<c:choose>
		<c:when test="${count eq 1}">
			${id}님, 가입이 완료되었습니다.
			<button type="button" onclick="location.href='./index.jsp'">로그인하러 가기</button>
		</c:when>
		<c:otherwise>
			${id}님, 문제가 발생하였습니다. 다시 시도해주세요.
			<button type="button" onclick="location.href='./join.jsp'">회원가입하러 가기</button>
			
		</c:otherwise>
	</c:choose>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>