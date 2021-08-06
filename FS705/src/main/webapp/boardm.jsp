<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Board</title>
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

#searchBox{ width:720px; height:480px; border:5px solid #5499C7; position:absolute; 
         left:50%; top:50%; transform:translate(-50%, -50%);
         box-shadow:10px 10px 10px rgba(0,0,0,0.5);
         padding:10px;
         background-color:white;
         z-index:99;
         display:none;
         overflow:auto;
      }
.searchResult{width:100%;height:34px;border-bottom:1px solid #eee;border-right:1px solid #eee;cursor:pointer;transition:0.3s;}

#searchBack{width:100%; height:100%; background-color:rgba(0,0,0,0.5); position:fixed; left:0; top:0;z-index:98;display:none;}

             
</style>
<script type="text/javascript">

function select(){
	var category = document.getElementById("category").value;
	var subCategory = document.getElementById("subCategory").value;
	location.href='boardm?category='+category+'&subCategory='+ subCategory + '&page=' + 1;
	}
	
</script>

</head>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<div id="adminMenu">
			&ensp; <a href='./admin'>로그관리</a>  | 
			<a href='./memberm'> 회원 관리</a>  |  
			<a href='./boardm'><b>게시글관리</b></a>
		</div>
		<div id = "adminMain">
			<h1>게시글관리 </h1>
			<form action="boardm" method="get" id ="orderform">
			<select name="order" id ="order">
				<option value='old' selected>오래된순</option>
				<option value='new'>최신순</option>
			</select>
			</form>	 
			
			<c:choose>
				<c:when test="${fn:length(list) > 0 }">
				<table>
					<tr>
						<th>글번호&ensp;<input type="checkbox" name="${l.getLogNo() }" value="${l.getLogNo() } "></th>
						<th>카테고리
							<select onchange="select()" id="category">
								<option value="" selected>카테고리</option>
								<c:forEach items="${categoryList }" var="c">
									<c:if test="${category eq c }">
										<option value="${c }" selected>${c }</option>
									</c:if>
									<c:if test="${category ne c }">
										<option value="${c }">${c }</option>
									</c:if>
								</c:forEach>
							</select>
						</th>
						<th>서브카테고리
						<c:choose>
						<c:when test="${category ne '' }">
							<select onchange="select()" id="subCategory">
								<option value="" selected>target 선택</option>
								<c:forEach items="${subCategoryList }" var="s">
									<c:if test="${subCategory eq s }">
										<option value="${t }" selected>${s }</option>
									</c:if>
									<c:if test="${subCategory ne s }">
										<option value="${s }">${s }</option>
									</c:if>
								</c:forEach>
							</select>
						</c:when>	
						<c:otherwise>
							<select onchange="select()" id="subCategory" disabled>
								<option value="" selected>target 선택</option>
								<c:forEach items="${subCategoryList }" var="s">
									<c:if test="${subCategory eq s }">
										<option value="${t }" selected>${s }</option>
									</c:if>
									<c:if test="${subCategory ne s }">
										<option value="${s }">${s }</option>
									</c:if>
								</c:forEach>
							</select>
						</c:otherwise>
						</c:choose>
						</th>
						<th>제목</th>
						<th>내용</th>
						<th>날짜</th>
						<th>조회수</th>
						<th>회원번호</th>
						<th>사진파일</th>
						<th>좋아요</th>
						<th>싫어요</th>
						<th>댓글 수</th>
					</tr> 
					
					<c:forEach items='${list }' var="l">
					<tr onclick="test()">
						<td>${l.getBno() } <input type="checkbox" name="${l.getBno() }" value="${l.getBno() } "> </td>
						<td>${l.getBcategory() }</td>
						<td>${l.getSubCategory() }</td>
						<td>${l.getBtitle() }</td>
						<td>${fn:substring(l.getBcontent(), 0, 15 )}</td>
						<td>${fn:substring(l.getBdate(), 0, 19 )}</td>
						<td>${l.getBcount() }</td>
						<td>${l.getNo() }</td>
						<td>${l.getBthumbnail() }</td>
						<td>${l.getBlike() }</td>
						<td>${l.getBdislike() }</td>
						<td>${l.getCommentCount() }</td>
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
<div id="searchBack">
</div>
<div id="searchBox">
</div>
</body>
</html>

