<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${gdto eq null}">
	<c:redirect url="./index"/>
</c:if>
<html>
<head>
<meta charset="UTF-8">
<title>welcome</title>
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
<script>
$(function(){
	//a태그 hover이벤트 tab키 먹히게 하는 기능
	$(".title a").bind("mouseover focus",function(){
		$(this).css({"border":"none"});
		$(this).parents(".main").css({"background-color":"#D6EAF8"});
	});
	$(".title a").bind("mouseout focusout",function(){
		$(this).parents(".main").css({"background-color":"transparent"});
	});
});
</script>
</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<div id="editorChoice" class="boardBox">
			<h2><img alt="에디터 초이스" src="./img/best.png"> 공지사항</h2>
			<!-- 구간반복지점 -->
				<ul class="main" onclick="location.href=''">
					<li class="title"><a href="">자바 웹 개발자를 위한 개발 가이드</a></li>
					<li>아이디</li>
					<li><img alt="좋아요" src="./img/like.png"> 54k</li>
					<li>11일 전</li>
				</ul>
			<!-- 구간반복지점 -->
		</div>
		<div id="weeklyBest" class="boardBox">
			<h2><img alt="위클리 베스트" src="./img/week.png"> Weekly Best</h2>
			<!-- 구간반복지점 -->
				<ul class="main" onclick="location.href=''">
					<li class="title"><a href="">자바 웹 개발자를 위한 개발 가이드</a></li>
					<li>아이디</li>
					<li><img alt="좋아요" src="./img/like.png"> 54k</li>
					<li>11일 전</li>
				</ul>
			<!-- 구간반복지점 -->
		</div>
		<div id="food" class="boardBox">
			<h2><img alt="맛집" src="./img/food.png"> 맛집</h2>
			<!-- 구간반복지점 -->
			<c:forEach items="${fdto }" var="fdto">
				<ul class="main" onclick="">
					<li class="title"><a href="foodView?bno=${fdto.bno }">[<c:choose>
						<c:when test="${fdto.subCategory eq 1 }">
						한식
						</c:when>
						<c:when test="${fdto.subCategory eq 2 }">
						중식
						</c:when>
						<c:when test="${fdto.subCategory eq 3 }">
						일식
						</c:when>
						<c:when test="${fto.subCategory eq 4 }">
						카페·디저트
						</c:when>
						<c:otherwise>
						기타
						</c:otherwise>
					</c:choose>]
					 ${fdto.btitle }</a></li>
					<li>${fdto.id }</li>
					<li><img alt="좋아요" src="./img/like.png">${fdto.blike }</li>
					<li>
					<c:if test="${fdto.bdate eq 0}">오늘</c:if>
					<c:if test="${fdto.bdate ne 0}">${fdto.bdate }일 전</c:if>
					</li>
				</ul>
				</c:forEach>
			<!-- 구간반복지점 -->
		</div>
		<div id="sports" class="boardBox">
			<h2><img alt="스포츠" src="./img/sports.png"> 스포츠</h2>
			<!-- 구간반복지점 -->
				<ul class="main" onclick="location.href=''">
					<li class="title"><a href="">자바 웹 개발자를 위한 개발 가이드</a></li>
					<li>아이디</li>
					<li><img alt="좋아요" src="./img/like.png"> 54k</li>
					<li>11일 전</li>
				</ul>
			<!-- 구간반복지점 -->
		</div>
		<div id="game" class="boardBox">
			<h2><img alt="게임" src="./img/game.png"> 게임</h2>
			<!-- 구간반복지점 -->
			<c:forEach items="${gdto }" var="gdto">
				<ul class="main" onclick="">
					<li class="title"><a href="gameView?bno=${gdto.bno }">[<c:choose>
						<c:when test="${gdto.subCategory eq 1 }">
						리그오브레전드
						</c:when>
						<c:when test="${gdto.subCategory eq 2 }">
						오버워치
						</c:when>
						<c:when test="${gdto.subCategory eq 3 }">
						배틀그라운드
						</c:when>
						<c:when test="${gdto.subCategory eq 4 }">
						모바일
						</c:when>
						<c:otherwise>
						기타
						</c:otherwise>
					</c:choose>]
					 ${gdto.btitle }</a></li>
					<li>${gdto.id }</li>
					<li><img alt="좋아요" src="./img/like.png">${gdto.blike }</li>
					<li>
					<c:if test="${gdto.bdate eq 0}">오늘</c:if>
					<c:if test="${gdto.bdate ne 0}">${gdto.bdate }일 전</c:if>
					</li>
				</ul>
				</c:forEach>
			<!-- 구간반복지점 -->
		</div>
		<div id="funny" class="boardBox">
			<h2><img alt="유머" src="./img/fun.png"> 유머</h2>
			<!-- 구간반복지점 -->
			<c:forEach items="${hdto }" var="hdto">
				<ul class="main" onclick="">
					<li class="title"><a href="humorView?bno=${hdto.bno }">[<c:choose>
						<c:when test="${hdto.subCategory eq 1 }">
						잡담
						</c:when>
						<c:when test="${hdto.subCategory eq 2 }">
						이슈
						</c:when>
						<c:when test="${hdto.subCategory eq 3 }">
						감동
						</c:when>
						<c:when test="${hdto.subCategory eq 4 }">
						정치
						</c:when>
						<c:otherwise>
						기타
						</c:otherwise>
					</c:choose>]
					 ${hdto.btitle }</a></li>
					<li>${hdto.id }</li>
					<li><img alt="좋아요" src="./img/like.png">${hdto.blike }</li>
					<li>
					<c:if test="${hdto.bdate eq 0}">오늘</c:if>
					<c:if test="${hdto.bdate ne 0}">${hdto.bdate }일 전</c:if>
					</li>
				</ul>
				</c:forEach>
			<!-- 구간반복지점 -->
		</div>
	</div>
	<c:import url="plusBar.jsp"/>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>