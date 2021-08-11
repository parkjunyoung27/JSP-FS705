<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Admin Board</title>
<style type="text/css">
/*------------------------------- reset css -------------------------------*/
*{padding:0; margin:0;list-style:none;font-family:'빙그레 메로나체';}
a:link, a:visited{text-decoration:none;color:#333;}

/*------------------------------- wrapper css -------------------------------*/
#wrapper{width:1280px;margin:0 auto;}

/*------------------------------- container css -------------------------------*/
	#container{width:82%;float:left;}
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

    #my_modal {
        display: none;
        width: 300px;
        padding: 20px 60px;
        background-color: #fefefe;
        border: 1px solid #888;
        border-radius: 3px;
    }

    #my_modal .modal_close_btn {
        position: absolute;
        top: 10px;
        right: 10px;
    }

             
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script type="text/javascript">

	//1차적으로
	//정렬만
	function select(){
		var order = document.getElementById("order").value;
		location.href= 'boardm?order='+order +'&page='+1;
		}

	//2차
	function secondSelect(){
		var order = document.getElementById("orderSecond").value;
		var category = document.getElementById("category").value;
		var subCategory = document.getElementById("subCategory").value;
		var searchType = document.getElementById("searchType").value;
		var searchText = document.getElementById("searchText").value;
		location.href= './boardm?order=' + order + '&category='
				+ category + '&subCategory=' + subCategory
				+ '&searchType=' + searchType
				+ '&searchText=' + searchText + '&page='+1;
	}	
	
	function checkSelectAll() {
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
	function detailShow(title, content){
		$("#searchBack, #searchBox").show();
		$("#searchBox").text(title + content);
	     return false;
	   
	}
	$(function(){
	   $("#searchBack, #searchBox").click(function(){
	      $("#searchBack, #searchBox").hide();
	   });		
	});
	
</script>
</head>
<body>
<input type="hidden" name="order" value="${order }" id="orderSecond" >
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<div id="adminMenu">
			&ensp; <a href='./admin'>로그관리</a>  | 
			<a href='./memberm'> 회원 관리</a>  |  
			<a href='./boardm'><b>게시글관리</b></a>
		</div>

		<div id= "adminMain">
			<h1>게시글 관리 </h1>
			<c:choose>
				<c:when test="${fn:length(list) > 0 }">
				<select onchange="select()" id ="order" name="order">
					<option value='' <c:if test="${'' eq order }">selected</c:if>>
					등록순</option>
					<option value='bno' <c:if test="${'bno' eq order }">selected</c:if>>
					최신순</option>
					<option value='bcount' <c:if test="${'bcount' eq order }">selected</c:if>>
					조회순</option>
					<option value='blike' <c:if test="${'blike' eq order }">selected</c:if>>
					좋아요순</option>
				</select>
			
				<form action="./boardm" method="post" class="deleteform">
		
				<table>
					<tr>
						<th>
						<input type="checkbox" name="checkall" onclick="selectAll(this)">
						</th>
						<th>글번호</th>
						<th>카테고리
							<select onchange="secondSelect()" id="category">
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
							<select onchange="secondSelect()" id="subCategory">
								<option value="" selected>선택</option>
								<c:forEach items="${subCategoryList }" var="s">
									<c:if test="${subCategory eq s }">
										<option value="${s }" selected>${s }</option>
									</c:if>
									<c:if test="${subCategory ne s }">
										<option value="${s }">${s }</option>
									</c:if>
								</c:forEach>
							</select>
						</c:when>	
						<c:otherwise> <!-- category가 null일 때 -->
							<select disabled>
								<option>전체</option>
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
					<tr>
						<td> <input type="checkbox" name="check" 
						value="${l.getBno() }"  onclick='checkSelectAll()'></td>
						<td>${l.getBno() }</td>
						<td>${l.getBcategory() }</td>
						<td>${l.getSubCategory() }</td>
						<td onclick="detailShow('${l.getBtitle() }','${l.getBcontent() }')">${l.getBtitle() }</td>
						<td>${fn:substring(l.getBcontent(), 0, 15 )}</td>
						<td>${fn:substring(l.getBdate(), 0, 19 )}</td>
						<td>${l.getBcount() }</td>
						<td>${l.getNo() }</td>
						<td>
							<c:choose>
								<c:when test="${l.getBthumbnail() != null}">
								${l.getBthumbnail() }
								</c:when>
								<c:otherwise>
								null
								</c:otherwise>
							</c:choose>
						</td>
						<td>${l.getBlike() }</td>
						<td>${l.getBdislike() }</td>
						<td>${l.getCommentCount() }</td>
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
		
		<form action="boardm" id ="searchform" method="get" onsubmit="secondSelect()">
			<input type="hidden" name="order" value="${order }" >
			<input type="hidden" name="category" value="${category }" >	
			<input type="hidden" name="subCategory" value="${subCategory }" >	
			<select name="searchType" id="searchType">
				<option value='all' selected>전체</option>
				<option value='btitle'>제목</option>
				<option value='bcontent'>내용</option>
			</select>
			<input type="text" id="searchText" name="searchText">
			<button type="submit">검색</button>
			<input type="hidden" name="page" value="${page }" >
		</form>	 

		<div id="Paging">
			<c:set var="pageName" value="boardm" scope="request"/>
			<!-- 한 쪽당 데이터 20개씩 나열 -->	 
			<c:set var="PAGENUMBER" value="10" scope="request"/> 
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

