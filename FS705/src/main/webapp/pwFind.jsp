<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>join page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script type="text/javascript">
function pwQuestion() {
	$.ajax({
		type:'post',
		dataType:'text',
		data:{"id":id.value, "email":email.value},
		url:'./pwQuestion',
		success: function(rData){
			if(rData == null || rData == "") {
				alert("아이디/이메일 을 다시 확인해주세요.");
			} else {
				$("#pwQuestion").text(rData);
			}
		},
		error: function(xhr, status, e){
			alert("문제 발생 : " + e);
		}
	});
}

</script>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
		<!-- 필요한 정보 -->
		<form action="./pwFind" method="post" id="pwFind" name="pwFind">	
			<p id="pwFind_id">
				가입시 사용한 ID<br>
				<input type="text" method="post" id="id" name="id" placeholder="ID" onsubmit="pwQuestion()" required>
			</p>
			<p id="pwFind_email">
				가입 시 사용한 이메일<br>
				<input type="email" method="post" id="email" name="email" placeholder="XXX@YYY.ZZZ" onsubmit="pwQuestion()" required><br>
				<button type="button" id="pwQuestionBtn" onclick="pwQuestion()" >질문확인</button>				 
			</p>
			<p id="pwFind_pwQuestion">
			Q. 질문 : <span id="pwQuestion" name="pwQuestion"></span>
			</p>
			<p id="pwFind_email">
				질문의 답<br>
				<input type="text" id="pwAnswer" name="pwAnswer" placeholder="답변" required>
			</p>
			<p id="pwFind_findBox">
	 			<button id="pwFindBtn" onclick="./pwFind">비밀번호 변경</button>
	 			<button type="button" onclick="location.href='./index.jsp'">뒤로가기</button>
			</p>
		</form>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>