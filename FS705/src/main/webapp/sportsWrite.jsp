<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link href="./css/sports.css" rel="stylesheet">
<link href="./css/header.css" rel="stylesheet">
<link href="./css/footer.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
			<form action="./sportsWrite" method="post" enctype="multipart/form-data">
				<input type="text" name="title" 
					required="required" placeholder="제목을 적어주세요">
				<textarea name="content" required="required"></textarea>
				<input type="file" name="file1" accept=".gif, .png, .jpg">
				<button type="submit">글쓰기</button>
			</form>
			<br>
				<p onclick="location.href='./sports'">게시판으로</p>
		</div>
		
	<c:import url="plusBar.jsp"/>
	<c:import url="footer.jsp"/>
	
</div>
	
</body>
</html>