<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${dto eq null }">
	<c:redirect url="sports"/>
</c:if>
<!DOCTYPE html>
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
	#container{width:72%;float:left;height:100vh;}
		.boardBox{width:87%;margin:1.7%;float:left;}
			.boardBox h2{width:100%;height:18px;line-height:18px;font-size:18px;font-weight:300;border-bottom:1px solid #eee;padding-bottom:5px;}
				.boardBox h2 img{display:inline-block;width:16px;height:16px;vertical-align:middle;}
			.boardBox .main{width:100%;border-bottom:1px solid #eee;border-right:1px solid #eee;cursor:pointer;transition:0.3s;}
			.boardBox .main:hover{background-color:#D6EAF8;}
				.boardBox ul li{height:63px;line-height:70px;float:left;overflow:hidden;font-size:12px;}
				.boardBox ul li:first-child{width:60%;text-indent:5px;border-left:5px solid #85C1E9;border-radius:5px 0 0 5px;font-size:14px;line-height:35px;}
				.boardBox ul li:nth-child(2){width:17%;}
				.boardBox ul li:nth-child(3){width:10%;}
					.boardBox ul li:nth-child(3) img{height:15px;float:left;padding-top:12px;padding-right:1px;}
				.boardBox ul li:last-child{width:10%;}
 		.writeBox{height:35px; wdith: 50px; line-height:42px;float:right;overflow:hidden;font-size:16px;border:1px solid #eee;c}
 		.paging{width:87%;margin:0 auto;float:center;}
 		
 #divPaging {
	clear: both;
	margin: 0 auto;
	width: 100%;
	height: 50px;
}

#divPaging>div {
	float: left;
	width: 40px;
	margin: 0 auto;
	text-align: center;
}
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
function select(){
	var ip = document.getElementById("ip").value;
	var target = document.getElementById("target").value;
	location.href='admin?ip='+ip+'&target='+target;
}
</script>
</head>
<body>
<div id="wrapper">
	<c:import url="/header.jsp"/>
	<c:import url="/sports/categoryBar.jsp"/>
	<div id="container">	
		<div id="sports" class="boardBox">
			<h2><img alt="스포츠" src="./img/sports.png"> 스포츠</h2>
				<ul>
					<li>제목</li>
					<li>작성자</li>
					<li>좋아요</li>
					<li>날짜</li>
				</ul>
				<c:forEach items="${dto }" var="dto">
				<ul class="main" onclick="">
					<li class="title"><a href="sportsDetail?bno=${dto.bno }">
					<c:choose>
						<c:when test="${dto.bthumbnail eq null }">
							<img alt="no image" src="img/noimage.jpg"  style="vertical-align: middle; height: 80px;">
						</c:when>
						<c:otherwise>
							<img alt="thumb" src="./upload/sportsThumbnail/${dto.bthumbnail }" style="vertical-align: middle; height: 80px;">
						</c:otherwise>
					</c:choose>
					[<c:choose>
						<c:when test="${dto.subCategory eq 1 }">
						야구
						</c:when>
						<c:when test="${dto.subCategory eq 2 }">
						배구
						</c:when>
						<c:when test="${dto.subCategory eq 3 }">
						축구
						</c:when>
						<c:when test="${dto.subCategory eq 4 }">
						농구
						</c:when>
						<c:otherwise>
						기타
						</c:otherwise>
					</c:choose>]
					 ${dto.btitle }[${dto.commentCount }]</a></li>
					<li>${dto.id }(${dto.name })</li>
					<li><img alt="좋아요" src="./img/like.png">${dto.blike }</li>
					<li>${dto.bdate }</li>
				</ul>
				</c:forEach>
		
			<div id="divPaging">
			<c:set var="pageName" value="sports" scope="request"/>
			<c:set var="PAGENUMBER" value="10" scope="request"/>
			<c:import url="/sports/sportspaging.jsp"/>									
			</div>
			<div class="writeBox">
			<a href="sportsWrite">글쓰기</a>	
			</div>
			<!-- 검색 폼 -->
				<div id="liSearchOption">
				<form action="./sports" method="post">
					<select name="searchOption">
						<option value='btitle'>제목</option>
						<option value='TC'>제목+내용</option>
						<option value='bcontent'>내용</option>
					</select> <input name="search" /> <button type="submit">검색</button>
				</form>
				</div>
		</div>		
	</div>	
	<c:import url="/footer.jsp"/>
</div>
<c:import url="/plusBar.jsp"/>
</body>
</html>