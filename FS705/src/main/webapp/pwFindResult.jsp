<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Password Update Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script type="text/javascript">
$(function(){
	$("#joinBtn").prop("disabled", true);
});
function isSame() {
	var pw1 = document.getElementById("pw1");
	var pw2 = document.getElementById("pw2");
	var pwResult = document.getElementById("isSameWrite");	
	var btn = document.getElementById("pwModifyBtn");
	if(pw1.value !='' && pw2.value !='') {
		if(pw1.value == pw2.value) {
			pwResult.innerHTML='비밀번호가 일치합니다.';
			btn.disabled = false;
		} else {
			pwResult.innerHTML='비밀번호가 일치하지 않습니다.';
		}
	} else {
		pwResult.innerHTML='값을 넣어주세요.';
	}
}

$(function(){
	$("#pwModifyBtn").prop("disabled", true);
});

</script>
<body>
<div id="wrapper">
	<c:import url="header.jsp"/>
	<div id="container">
	<c:choose>
		<c:when test="${checkAnswer eq 1}">
			비밀번호 변경<br>
			
			<form action="./pwModify" method="post" id="pwForm" name="pwForm">
				<input type="hidden" name="id" value="${id }">
				<input type="password" method="post" id="pw1" name="pw1" placeholder="비밀번호 입력" required><br>
				<input type="password" method="post" id="pw2" name="pw2" placeholder="비밀번호 확인" onchange="isSame()" required><br>
				<span id="isSameWrite"></span><br>
				<button type="submit" id="pwModifyBtn">변경하기</button>
			</form>
		</c:when>
		<c:otherwise>
			올바른 값이 아닙니다. 다시 시도해주세요.
			<button type="button" onclick="location.href='./pwFind.jsp'">돌아가기</button>
		</c:otherwise>
	</c:choose>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>