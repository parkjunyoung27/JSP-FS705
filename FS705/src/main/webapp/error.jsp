<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ERROR</title>
<style type="text/css">
/*------------------------------- reset css -------------------------------*/
*{padding:0; margin:0;list-style:none;font-family:'빙그레 메로나체';}
a:link, a:visited{text-decoration:none;color:#333;}

/*------------------------------- wrapper css -------------------------------*/
#wrapper{width:1280px;margin:0 auto;}

/*------------------------------- container css  구동해주세요-------------------------------*/
	#container{width:82%;float:left;}
	#error{padding-top:200px;color:black;text-align:center;}

	#goToTop{display:none;}
	
	#goHome{
	text-align: center;
	margin-top: 100px;
	font-size: x-large;
	font-weight: bold;
	}
	
</style>
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<div id="error">
			${param.code }로 인하여 에러가 발생<br>
			<c:choose>
				<c:when test="${param.code eq '400' }">
					<h1>서버에 문제가 발생하였습니다.</h1>
					<h2>관리자에게 문의하세요.</h2>
				</c:when>
				<c:when test="${param.code eq '404' }">
					<h1>페이지를 찾을 수 없습니다.</h1>
					<h2>올바른 경로로 접근하시기 바랍니다.</h2>
				</c:when>
				<c:when test="${param.code eq '500' }">
					<h1>서버가 응답하지 않습니다</h1>
					<h2>관리자에게 문의하세요.</h2>
				</c:when>
				<c:when test="${param.code eq '505' }">
					<h1>브라우저 버전이 낮습니다.</h1>
					<h2>버전을 업그레이드 하시거나 크롬으로 접속하십시오.</h2>
				</c:when>
				<c:otherwise>
					<h1>심각한 오류가 발생하였습니다.</h1>
					<h2>관리자에게 분의하세요.</h2>
				</c:otherwise>
			</c:choose>
		</div>
		<div id="goHome">
		<a href="index" target="_bank">홈으로</a>		
		</div>
	</div>
	<c:import url="plusBar.jsp"/>
</div>
</body>
</html>
