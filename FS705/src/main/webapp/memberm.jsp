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

#adminMain button{
	margin: 10px 63px;
}

table{ /* 테이블*/ 
	margin: 0 auto;
	height: 600px;
	width : 90%;
	background-color: #ffffff;
	border-collapse: collapse;
	text-align: center;
	display: block;
	overflow: auto;
}

tbody {
    min-width: 1000px;
    display: inline-block;
    padding: 5px;
}

table th{ /*메뉴 제목*/
	font-size: 110%;
	font-weight: bold;
	border-bottom: 4px #2B55B1 solid;
	height: 75px;
}

table td{ /* 제목*/ 
	border-bottom : 1px #2B55B1 solid;
	height: 150px;
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

.gradeButton {
    display: block;
}

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">

var grade1 = document.getElementById("grade").value;

	function select(){
		var order = document.getElementById("order").value;
		var grade = document.getElementById("grade").value;
		var gender = document.getElementById("gender").value;
		var searchType = document.getElementById("searchType").value;
		var searchText = document.getElementById("searchText").value;
		
		location.href = 'memberm?order=' + order
				+'&grade=' + grade + '&gender=' + gender + '&searchType=' + searchType
				+'&searchText=' + searchText + '&page=' +1;
		}
	
	function select2(){
		var order = document.getElementById("order").value;
		var grade = document.getElementById("grade").value;
		var gender = document.getElementById("gender").value;
		var searchType = document.getElementById("searchType").value;
		var searchText = document.getElementById("searchText").value;
		
		location.href = 'memberm?order=' + order
				+'&grade=' + grade1 + '&gender=' + gender + '&searchType=' + searchType
				+'&searchText=' + searchText + '&page=' +1;
		}
	
	function checkSelectAll()  {
		  // 전체 체크박스
		  const checkboxes //전체 갯수 선택했을때 갯수 세기 
		    = document.querySelectorAll('input[name="check"]');
		  // 선택된 체크박스
		  const checked // 체크된 갯수 세기
		    = document.querySelectorAll('input[name="check"]:checked');
		  // select all 체크박스
		  const selectAll 
		    = document.querySelector('input[name="checkall"]');
		  
		  if(checkboxes.length == checked.length)  { //전체선택했을 때
		    selectAll.checked = true;
		  }else {
		    selectAll.checked = false;
		  }

		}
	
	
	function selectAll(selectAll)  { // 전체체크 선택시
	  const checkboxes  //check의 element를 모두 찾아서 노드리스트형태로 리턴, 20개의 체크박스 엘리먼트가 리턴
	     = document.getElementsByName('check');
	  
	//이 목록을 반복하여 cehcked값을  전체선택의 check값과 동일하게 변경
	  checkboxes.forEach((checkbox) => {
	    checkbox.checked = selectAll.checked
	  })
	}
	
	function checkDelete(form){
		var sum = 0;
		var count = form.check.length; //form.name값.길이
		for(var i =0; i<count; i++){
			if(form.check[i].checked == true){ //checkbox 체크되어있는지 확인
				sum += 1;
			}
		}
		
		if(sum > 0){
			var test= confirm(sum+"개 삭제하시겠습니까?");
			if(test == true){
				$(".deleteform").submit();
				alert("삭제됐습니다.");
			}else{
				alert("취소됐습니다.");
			}
		}else{
			return false;
		}		
	}
	
	function upGrade(uno){
		const url3 = window.location.href;
		const url2 = url3.split("/");
		const url = url2[4];
		const divide = url.split("?");
		const front = divide[0];
		const back = divide[1];
		var grade = document.getElementById("grade").value;
		grade1 = parseInt(grade)+1;
		
		if(confirm("등급을 올리시겠습니까?")){
			if(!back){
				location.href = front +'?uno=' + uno;
				alert("등급을 올렸습니다.");		
				select2();		
			}else{
				location.href = front +'?uno=' + uno+'&'+back;				
				alert("등급을 올렸습니다.");
				select2();
			}
				return false;
		}
	}
	function downGrade(dno){
		const url3 = window.location.href;
		const url2 = url3.split("/");
		const url = url2[4];
		const divide = url.split("?");
		const front = divide[0];
		const back = divide[1];
		var grade = document.getElementById("grade").value;
		grade1 = parseInt(grade)-1;

		if(confirm("등급을 내리시겠습니까?")){
			if(!back){
				location.href = front +'?dno=' + dno;
				alert("등급을 내렸습니다.");		
				select2();		
			}else{
				location.href = front +'?dno=' + dno+'&'+back;				
				alert("등급을 내렸습니다.");
				select2();
			}
				return false;
		}
	}
	</script>
</head>

<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
	
		<div id="adminMenu">
			&ensp; <a href='./admin'>로그 관리</a>  | 
			<a href='./memberm'><b>회원 관리</b></a>  |  
			<a href='./boardm'>게시글 관리</a>
		</div>
		
		<div id = "adminMain">
			<h1>회원 관리 </h1>
			<form action="memberm" method="get" id ="orderform">
			<select onchange="select()" id="order" name="order">
				<option value=""
				<c:if test="${'' eq order }">selected</c:if>>
				등록일순</option>
				<option value="DESC" <c:if test="${'DESC' eq order }">selected </c:if>>
				최신순</option>
			</select>
			</form>	 
		
			<c:choose>
			<c:when test="${fn:length(list) > 0}">
			
			<form action="./memberm" method="post" class="deleteform">
				<table>
					<tr>
						<th>
						<input type="checkbox" name="checkall" onclick="selectAll(this)">
						</th>
						<th>번호</th>
						<th>이름</th>
						<th>아이디</th>
						<th>패스워드</th>
						<th>
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
						<th>
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
						<td> <input type="checkbox" name="check" 
						value="${l.getNo() } " onclick='checkSelectAll()'></td>
						<td>${l.getNo() }</td>
						<td>${l.getName() }</td>
						<td>${l.getId() }</td>
						<td>${l.getPw() }</td>
						<td>${l.getSex() }</td>
						<td>${l.getEmail() }</td>
						<td>${fn:substring(l.getJoindate(), 0, 19 )}</td>
						<td>${l.getBirthdate() }</td>
						<td>
							<c:choose>
								<c:when test="${l.getGrade() eq 9}">
									<button type="button" class="gradeButton" disabled ="disabled">▲</button>
								</c:when>
								<c:otherwise>
									<button type="button" onclick="upGrade(${l.getNo()})" class="gradeButton">▲</button>								
								</c:otherwise>
							</c:choose>
								${l.getGrade() }
							<c:choose>
								<c:when test="${l.getGrade() eq 1}">
									<button type="button" class="gradeButton" disabled="disabled">▼</button>
								</c:when>
								<c:otherwise>
									<button type="button" onclick="downGrade(${l.getNo()})" class="gradeButton">▼</button>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${l.getHint() }</td>
						<td>${l.getHintAnswer() }</td>
						<td>${l.getProfile() }</td>
					</tr>				
					</c:forEach>
				</table>
				<button type="button" onclick="checkDelete(this.form)"> 삭제</button>			
			</form>
			
			</c:when>
				<c:otherwise>
					<h2>출력할 로그가 없습니다.</h2>
				</c:otherwise>
			</c:choose>
		</div>
		&emsp;&emsp;전체 글 수 : ${totalCount } 개 / 현재 페이지 : ${page }	
		
		<form action="memberm" id="searchform" method="get" onsubmit="select()">
			<input type="hidden" name="order" value="${order }" >
			<input type="hidden" name="grade" value="${grade }" >
			<input type="hidden" name="gender" value="${gender }" >
			<select name="searchType" id="searchType">
				<option value='all' selected>전체</option>
				<option value='name'>이름</option>
				<option value='email'>이메일</option>
			</select>
			<input type="text" id="searchText" name="searchText">
			<button type="submit">검색</button>
			<input type="hidden" name="page" value="${page }" >
		</form>	 

		<div id="Paging">
			<c:set var="pageName" value="memberm" scope="request"/>
			<!-- 한 쪽당 데이터 20개씩 나열 -->
			<c:set var="PAGENUMBER" value="3" scope="request"/> 
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
