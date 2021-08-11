<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>plusBar</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
#plusBar{width:18%;float:right;}
	.your{width:90%;height:35px;position:relative;margin:10px auto;}
		.ye_span{display:block;width:100%;height:10px;font-size:17px;line-height:10px;color:gray;
			position: absolute;top:13px;left:17px;transition:0.3s;font-size:15px;}
		.your input{display: inline-block;width:100%;height:35px;border:none;font-size:15px;font-weight:400;box-sizing:border-box;
		    padding:10px 16px 0 16px;color: #333;border: 1px solid #d6d6d6;}
	.submit{display:block;width:90%;height:30px;margin:0 auto;border:none; box-shadow:none; border-radius:0;cursor:pointer;}
	.newCrew{text-align:center;font-size:12px;padding-top:5px;}
		.newCrew a:hover, .newCrew a:focus{color:blue;text-decoration:underline;}
	
	#goToTop{position:fixed;top:80%;right:20%;cursor:pointer;width:40px;height:40px;border-radius:20px;background-color:#eee;text-align:center;line-height:18px;box-shadow: 3px 3px 3px gray;}
	
	#userData{width:100%;}
		#imgGrade{width:100%;}
			.igFloat{float:left;}
			.igFloat:first-child{padding-left:10px;}
			.igFloat:nth-child(2){width:150px;padding-top:23px;}
		#nameLv{clear:both;text-align:center;}
		#userBye{width:100%;text-align:center;padding-top:10px;}
@media all and (min-width:10px) and (max-width:1300px){
	#goToTop{position:fixed;top:80%;right:50px;}
}
</style>
<script>
$(function(){
	$("input").focus(function(){
		$(this).prev("span").css({"top":"5px", "font-size":"12px"});
	});
	$(".your").click(function(){
		$(this).children("span").css({"top":"5px", "font-size":"12px"});
		$(this).children("input").focus();
	});
	$(".your").focusout(function(){
		if($(this).children("input").val() == null || $(this).children("input").val() == ""){
			$(this).children("span").css({"top":"13px", "font-size":"15px"});				
		}
	});
});
</script>
</head>
<body>
<div id="plusBar">
	<div id="login">
		<c:choose>
			<c:when test="${sessionScope.id ne null}">
				<div id="userData">
					<div id="imgGrade">
						<c:choose>
							<c:when test="${sessionScope.profile ne null }">
								<p class="igFloat">
									<img alt="그림" src="./upload/${sessionScope.profile }"/>
								</p>							
							</c:when>
							<c:otherwise>
								<p class="igFloat">
									<img alt="사용자 이미지" src="./img/user.png"/>
								</p>
							</c:otherwise>
						</c:choose>
						<p class="igFloat">${sessionScope.name }<small>(${sessionScope.id })님 안뇽!</small></p>
					</div>
					<div id="nameLv">현재 나의 등급은 ${sessionScope.grade } 입니다.</div>
					<div id="userBye"><button onclick="location='./logout'" name="logout" class="submit">로그아웃</button></div>
				</div>
			</c:when>
		<c:otherwise>
			<form action="./login" method="post">
				<p class="your">
					<span class="ye_span">아이디</span>			
					<input type="text" name="id" id="id">
				</p>
				<p class="your">
					<span class="ye_span">패스워드</span>			
					<input type="password" name="pw" id="pw">
				</p>
				<button type="submit" class="submit">로그인</button>
				<p class="newCrew"><a href="">ID/PW 찾기</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="./join.jsp">회원가입</a></p>
			</form>
		</c:otherwise>
		</c:choose>
	</div>
</div>
<div id="goToTop" onclick="window.scrollTo(0,0);">▲<br>TOP</div>
</body>
</html>