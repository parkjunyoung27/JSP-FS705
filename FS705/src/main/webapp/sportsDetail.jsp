<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스포츠 게시판</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<script type="text/javascript" src="./js/sportsDetail.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
/*------------------------------- reset css -------------------------------*/
*{padding:0; margin:0;list-style:none;font-family:'빙그레 메로나체';}
a:link, a:visited{text-decoration:none;color:#333;}

/*------------------------------- wrapper css -------------------------------*/
#wrapper{width:1280px;margin:0 auto;}

/*------------------------------- container css -------------------------------*/
	#container{width:82%;float:left;height:100vh;}
		.boardBox{width:46.3%;margin:1.7%;float:left;}
			.boardBox h2{width:100%;height:18px;line-height:18px;font-size:18px;font-weight:300;border-bottom:1px solid #eee;padding-bottom:5px;}
				.boardBox h2 img{display:inline-block;width:16px;height:16px;vertical-align:middle;}
			.boardBox ul{width:100%;height:34px;border-bottom:1px solid #eee;border-right:1px solid #eee;cursor:pointer;transition:0.3s;}
			.boardBox ul:hover{background-color:#D6EAF8}
				.boardBox ul li{height:35px;line-height:42px;float:left;overflow:hidden;font-size:12px;}
				.boardBox ul li:first-child{width:60%;text-indent:5px;border-left:5px solid #85C1E9;border-radius:5px 0 0 5px;font-size:16px;line-height:35px;}
				.boardBox ul li:nth-child(2){width:17%;}
				.boardBox ul li:nth-child(3){width:10%;}
					.boardBox ul li:nth-child(3) img{height:15px;float:left;padding-top:12px;padding-right:1px;}
				.boardBox ul li:last-child{width:10%;}

</style>
<link href="./css/sports.css" rel="stylesheet">
<link href="./css/header.css" rel="stylesheet">
<link href="./css/footer.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
			<table>
				<tr>
					<th>제목</th>
					<td>${dto.btitle }
					<c:if test="${sessionScope.id eq dto.id }"> 
						<button onclick="sports(${dto.bno}, 'm');">
							<img alt="modify" src="./img/edit.png" height="10px"> 수정
						</button>
						<button onclick="sports(${dto.bno}, 'd');">
							<img alt="delete" src="./img/delete.png" height="10px"> 삭제
						</button>
					</c:if>
					</td>
				</tr>
				<tr>
					<th>글쓴이</th>
					<td>${dto.name }</td>
				</tr>
				<tr>
					<th>쓴날짜</th>
					<td>${dto.bdate }</td>
				</tr>
				<tr>
					<th>조회수</th>
					<td>${dto.bcount }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>${dto.bcontent }
						<br>
						<c:if test="${dto.bfile ne null }">						
						<img alt="그림" src="./upload/${dto.bfile }" width="100%">
						</c:if>
					</td>
				</tr>
			</table>
			
	<a href="./sports">스포츠 게시판으로</a>
	
	</div>
		
	<c:import url="plusBar.jsp"/>
	<c:import url="footer.jsp"/>
	
</div>
</body>
</html>