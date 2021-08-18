<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join page</title>
</head>
<script type="text/javascript">
</script>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<!-- 필요한 정보 -->
		<form action="./idFind" method="post" id="idFind" name="idFind">	
			<p id="idFind_name">
				이름<br>
				<input type="text" method="post" id="name" name="name" placeholder="가입 시 사용한 이름" required>
			</p>
			<p id="idFind_sex">
				성별
				<select id="sex" name="sex">
					<option value="man" id="man" name="man">남</option>
					<option value="woman" id="woman" name="woman">여</option>			
				</select>
			</p>
			<p id="idFind_birthDate">
				생일<br>
				<input type="date" id="birthDate" name="birthDate" min="1900-01-01" required>
			</p>
			<p id="idFind_findBox">
	 			<button type="submit" id="idFindBtn">id찾기</button>
	 			<button type="button" onclick="location.href='./index.jsp'">뒤로가기</button>
			</p>
			</form>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>