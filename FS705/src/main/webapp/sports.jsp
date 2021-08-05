<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${list eq null }">
	<!-- 서블릿을 지나와야 list가 생성됩니다. -->
	<!-- list가 없다면 다시 서블릿으로 이동하게 설정 -->
	<c:redirect url="./sports"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sports</title>
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
		<c:choose>
			<c:when test="${fn:length(list) > 0 }">
				<ul id="ulTable">
			<li>
				<ul>
					<li>No</li>
					<li>Title</li>
					<li>Writer</li>
					<li>Date</li>
					<li>Count</li>
				</ul>
			</li>
			<c:forEach items="${list }" var="l">
			<li>
				<ul>
					<li>${l.bno }</li>
					<li>
					<a href="./sportsDetail?sno=${l.bno }">
						<c:choose>
							<c:when test="${l.bthumbnail eq null }">
								<img alt="no image" src="img/noimage.jpg"  style="vertical-align: middle; height: 80px;">
							</c:when>
							<c:otherwise>
								<img alt="thumb" src="./thumbnail/${l.bthumbnail }" style="vertical-align: middle; height: 80px;">
							</c:otherwise>
						</c:choose>
						${l.btitle }
					</a>
				
					</li>
					<li>${l.name }</li>
					<li>${l.bdate }</li>
					<li>${l.bcount }</li>
				</ul>
			</li>
			</c:forEach>
		</ul>
			</c:when>
			<c:otherwise>찍어줄 글이 없습니다</c:otherwise>
		</c:choose>
	
		<c:if test="${sessionScope.id ne null }">
		<a href="./sportsWrite">글쓰기</a>
		</c:if>
	
		</div>
	<c:import url="plusBar.jsp"/>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>