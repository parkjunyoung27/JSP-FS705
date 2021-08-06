<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Log</title>
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

</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">

	function select(){
		//value값을 가져오고 싶다면 ?
		var order = document.getElementById("order").value;
		var ip = document.getElementById("ip").value;
		var target = document.getElementById("target").value;
		var searchname = document.getElementById("searchname").value;
		var adminsearch = document.getElementById("adminsearch").value;
		//값 오는 것이 확인된다면 서블릿을 보내서 해당 ip것만 받도록 합니다.
		//location.href='admin?ip=' + ip + '&page=' + ${page };
		//location.href='admin?ip='+ip+'&target='+ target+'&'+searchname+'='+adminsearch+'&page=' + 1;
		location.href='admin?order='+order+'&ip='+ip+'&target='+ target+'&searchname='+searchname+'&adminsearch='+adminsearch+'&page=' + 1;
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
		  
		  if(checkboxes.length === checked.length)  { //전체선택했을 때
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
				$("#deleteform").submit();
				alert("삭제됐습니다.");
			}else{
				alert("취소됐습니다.");
			}
		}else{
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
			&ensp; <a href='./admin'><b>로그관리</b></a>  | 
			<a href='./memberm'> 회원 관리</a>  |  
			<a href='./boardm'>게시글관리</a>
		</div>
		<div id = "adminMain">
			<h1>로그관리 </h1>
			<form action="admin" method="get" id ="orderform">
			<select onchange="select()" id="order" name="order">
				<option value= "" 
				<c:if test="${ '' eq order }">selected</c:if>>
				등록일순</option>
				<option value= "DESC" <c:if test="${'DESC' eq order }"> selected </c:if>>
				최신순</option>				
			</select>
			</form>	 
		
			<c:choose>
			<c:when test="${fn:length(list) > 0}">
			
			<form action="./admin" method="post" id="deleteform">
				<table>
					<tr>
						<th>
						<input type="checkbox" name="checkall" onclick="selectAll(this)">
						</th>
						<th> 번호</th>
						<th>
						<select onchange="select()" id="ip">
						<option value="" selected>ip 선택
						</option>
							<c:forEach items="${ipList }" var="i">
								<c:if test="${i eq ip }"> <!-- ip가 같으면 선택됨  -->
									<option value="${i }" selected>${i }</option>
								</c:if>
								<c:if test="${i ne ip }">
									<option value="${i }">${i }</option>
								</c:if>
							</c:forEach>
						</select>
						</th>
						<th>날짜</th>
						<th>
						<select onchange="select()" id="target">
							<option value="" selected>target 선택</option>
							<c:forEach items="${targetList }" var="t">
								<c:if test="${target eq t }">
									<option value="${t }" selected>${t }</option>
								</c:if>
								<c:if test="${target ne t }">
									<option value="${t }">${t }</option>
								</c:if>
							</c:forEach>
						</select>
						</th>
						<th>아이디</th>
						<th>기타</th>
						<th>Method</th>
					</tr> 
					<c:forEach items='${list }' var="l">
						<tr>
							<td>
							<input type="checkbox" name="check" value="${l.getLogNo()}"  onclick='checkSelectAll()' >
							</td> 
							<td>${l.getLogNo() } </td>
							<td>${l.getLogIp() }</td>
							<td>${l.getLogDate() }</td>
							<td>${l.getLogTarget() }</td>
							<td>
								<c:choose>
									<c:when test="${l.getLogdId() != null }">
									${l.getLogdId() }
									</c:when>
									<c:otherwise>
									null
									</c:otherwise>
								</c:choose>
							</td>		
							<td>${fn:substring(l.getLogEtc(), 0, 15 )}</td>
							<td>${l.getLogMethod() }</td>
						</tr>				
					</c:forEach>		
				</table>
				<button type="button" onclick="checkDelete(this.form)">삭제</button>		
			</form>
				 
			</c:when>
			<c:otherwise>
				<h2>출력할 로그가 없습니다.</h2>
			</c:otherwise>
			</c:choose>
		</div>
		&emsp;&emsp;전체 글 수 : ${totalCount } 개 / 현재 페이지 : ${page }	
		
		<form action="admin" id ="searchform" method="get" onsubmit="select()">
			<input type="hidden" name="order" value="${order }" >
			<input type="hidden" name="ip" value="${ip }" >
			<input type="hidden" name="target" value="${target }" >
			<select name="searchname" id="searchname">
				<option value='all' selected>전체</option>
				<option value='logIp'>ip</option>
				<option value='logTarget'>target</option>
				<option value='logEtc'>etc</option>
			</select>
			<input type="text" name="adminsearch" id="adminsearch" value="${adminsearch}">
			<button type="submit">검색</button>
			<input type="hidden" name="page" value="${page }" >
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


<!-- 
체크박스 배열로 들어옴  
리퀘스트겟벨류로 설정
String one = request.getParameterValues("sizes")[0];
String[] sizes = request.getParameterValues("sizes");
 -->





