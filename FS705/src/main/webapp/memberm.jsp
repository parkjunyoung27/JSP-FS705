<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Member</title>
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

#adminMenu{
	width: 100%;
	height: 50px;
	background-color: midnightblue;
	color: white;
	line-height:50px;
	overflow:hidden;
	font-weight: bold;
	margin-top: 30px;
}

#adminMenu a{
	text-decoration: none;
	color: white;
}
#adminMain{
	width: 100%;
	background-color: gainsboro;
	min-height: 700px;
}

table{ /* 테이블*/ 
	margin: 0 auto;
	height: 600px;
	width : 90%;
	background-color: #ffffff;
	border-collapse: collapse;
	text-align: center;
	overflow: scroll;
}

table th{ /*메뉴 제목*/
	font-size: 110%;
	font-weight: bold;
	border-bottom: 4px #2B55B1 solid;
	height: 50px;
}

table td{ /* 제목*/ 
	border-bottom : 1px #2B55B1 solid;
}

h1{ /* 제목*/
	color: black;
	text-align: center;
	font-size: 180%;
	margin-top: 0px;
}

tr:hover{
	transition: 1s;
	transition-delay: 0.5s; 
	background-color: #D0E5E0;
}

#adminSearch {
    text-align: center;
    margin: 10px;
}

#searchform{
	text-align:center;
}

#Paging{
	text-align: center;	
	margin-top: 20px;
}

#Paging a{
	text-decoration: none;
	color: black;
	margin: 5px;
}

#order {
    margin-left: 50px;
    margin-bottom: 10px;
}

#dbutton{
	margin: 10px 50px;
}
</style>
<script type="text/javascript">

function select(){
	var grade = document.getElementById("grade").value;
	var gender = document.getElementById("gender").value;
	location.href='memberm?grade='+grade+'&gender='+gender+'&page=' + 1;
	}
</script>
</head>

<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<div id="adminMenu">
			&ensp; <a href='./admin'>로그관리</a>  | 
			<a href='./memberm'><b>회원 관리</b></a>  |  
			<a href='./boardm'>게시글관리</a>
		</div>
		<div id = "adminMain">
			<h1>로그관리 </h1>
			<form action="admin" method="get" id ="orderform">
			<select name="order" id ="order">
				<option value='old' selected>오래된순</option>
				<option value='new'>최신순</option>
			</select>
			</form>	 
		
			<c:choose>
			<c:when test="${fn:length(list) > 0}">
			<table>
				<tr>
					<th>번호&ensp;<input type="checkbox" name="${l.getLogNo() }" value="${l.getLogNo() } "></th>
					<th>이름</th>
					<th>아이디</th>
					<th>패스워드</th>
					<th><c:out value="${gender }"></c:out>
					<select onchange="select()" id="gender">
					<option value="" selected>성별 전체</option>
						<c:forEach items="${genderList}" var="g">
							<c:if test="${g eq gender }"> <!-- ip가 같으면 선택됨  -->
								<option value="${g }" selected>${g }</option>
							</c:if>
							<c:if test="${g ne gender }">
								<option value="${g }">${g }</option>
							</c:if>
						</c:forEach>
					</select>
					</th>
					<th>이메일</th>
					<th>가입일</th>
					<th>생일</th>
					<th><c:out value="${grade }"></c:out>
					<select onchange="select()" id="grade">
					<option value="" selected>등급 전체</option>
						<c:forEach items="${gradeList}" var="g">
							<c:if test="${g eq grade }"> <!-- ip가 같으면 선택됨  -->
								<option value="${g }" selected>${g }</option>
							</c:if>
							<c:if test="${g ne grade }">
								<option value="${g }">${g }</option>
							</c:if>
						</c:forEach>
					</select>
					</th>
					<th>힌트</th>
					<th>답</th>
					<th>사진</th>
				</tr> 
				
				<c:forEach items='${list }' var="l">
				<tr>
					<td>${l.getNo() } <input type="checkbox" name="${l.getNo() }" value="${l.getNo() } "> </td>
					<td>${l.getName() }</td>
					<td>${l.getId() }</td>
					<td>${l.getPw() }</td>
					<td>${l.getSex() }</td>
					<td>${l.getEmail() }</td>
					<td>${fn:substring(l.getJoindate(), 0, 19 )}</td>
					<td>${l.getBirthdate() }</td>
					<td>${l.getGrade() }</td>
					<td>${l.getHint() }</td>
					<td>${l.getHintAnswer() }</td>
					<td>${l.getProfile() }</td>
				</tr>				
				</c:forEach>
				
			</table>
			<div id="dbutton">
				 <button type="submit">선택 삭제</button>			
				 <button type="submit">전체 삭제</button>	
			</div>		 
			</c:when>
				<c:otherwise>
					<h2>출력할 로그가 없습니다.</h2>
				</c:otherwise>
			</c:choose>
		</div>
		&emsp;&emsp;전체 글 수 : ${totalCount } 개 / 현재 페이지 : ${page }	
		
		<form action="admin" method="post" id ="searchform">
			<select name="searchname">
				<option value='all' selected>전체</option>
				<option value='logIp'>ip</option>
				<option value='logTarget'>target</option>
				<option value='logEtc'>etc</option>
			</select>
			<input type="text" id="adminsearch" name="adminsearch">
			<button type="submit">검색</button>
		</form>	 

	<div id="Paging">
		<c:set var="pageName" value="admin" scope="request"/>
		<!-- 한 쪽당 데이터 20개씩 나열 -->
		<c:set var="PAGENUMBER" value="20" scope="request"/> 
		<!-- 한 쪽당 페이지 10개씩 나열  -->
		<c:set var="LIMIT" value="5" scope="request"/>
		<c:import url="paging.jsp"/>
	</div>


	</div>
	
	<c:import url="plusBar.jsp"/>
	<c:import url="footer.jsp"/>
</div>

</body>
</html>
