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
	${id}
	<c:choose>
		<c:when test="${!empty id2}">
			회원님의 아이디는 [ ${id2} ] 입니다.<br>
			<button type="button" onclick="location.href='./index.jsp'">로그인하러 가기</button>
		</c:when>
		<c:otherwise>
			존재하지 않는 정보입니다. 다시 시도해주세요.<br>
			<button type="button" onclick="location.href='./idFind.jsp'">돌아가기</button>
		</c:otherwise>
	</c:choose>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>