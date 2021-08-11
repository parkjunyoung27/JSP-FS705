<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>header</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link href="./css/header.css" rel="stylesheet">
<script>
//로그인화면
$(function(){
	brandHeight = parseInt($("#brand").css("height"));
	headerH = $("#nav").height();
	headH = brandHeight + headerH;
	header = $("#menubar");

	$(window).scroll(function(){
		var nowScroll = $(document).scrollTop();
		if(nowScroll >= headH){
			header.addClass("fixed");
		}
		else{
			header.removeClass("fixed");
		}
	});
});
</script>
</head>
<body>
<div id="header">
	<div id="brand">
		<div id="logo">
			<h1><img alt="FS705" src="./img/logo.png"></h1>
		</div>
		<div id="search">
			<form action="">
				<input type="text" name="search"/>
				<button>검색</button>
			</form>
		</div>
	</div>
	<div id="nav">
		<ul id="menubar">
			<li><a href="" title="포텐터짐">포텐터짐</a><span class="line">|</span></li>
			<li><a href="./foodBoard" title="맛집">맛집</a><span class="line">|</span></li>
			<li><a href="" title="스포츠">스포츠</a><span class="line">|</span></li>
			<li><a href="" title="게임">게임</a><span class="line">|</span></li>
			<li><a href="" title="유머">유머</a><span class="line">|</span></li>
			<li><a href="" title="전체게시판">전체 게시판</a></li>
			<li>
				<span class="line">|</span>
				<c:if test="${sessionScope.grade eq 9 }">
						<a href="./admin" title="관리자">관리자</a>
				</c:if>			
			</li>	
		</ul>
	</div>
</div>
</body>
</html>