<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Page</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script type="text/javascript">

//현재 날짜 표시 
document.getElementById('birthDate').value = new Date().toISOString().substring(0, 10);;

//패스워드 검사
function isSame() {
	var pw1 = document.getElementById("pw1");
	var pw2 = document.getElementById("pw2");
	var pwResult = document.getElementById("isSameWrite");	
	if(pw1.value !='' && pw2.value !='') {
		if(pw1.value == pw2.value) {
			pwResult.innerHTML='비밀번호가 일치합니다.';
		} else {
			pwResult.innerHTML='비밀번호가 일치하지 않습니다.';
		}
	}
}

//가입하기 버튼 비활성화
$(function(){
	$("#joinBtn").prop("disabled", true);
});

//ID 중복확인 - 중복확인이 안됨.
function idCheck() {
	var id = $("#id").val();
	if(!id || id.length < 1){
		alert("아이디를 입력해주세요.");
		$("#id").focus();
		return false;
	}
	$.ajax({
		type:'post',
		dataType:'text',
		data:'id='+id,
		url:'./idCheck',
		success: function(rData, textStatus, xhr){
			if(rData == 1){
				alert(id + "는 이미 등록되어 있습니다.");
				$("#joinBtn").prop("disabled", true);
				$("#resultText").text(id + "는 이미 등록되어 있습니다.");
				
			}else{
				alert(id + "는 가입 할 수 있습니다.");
				$("#joinBtn").prop("disabled", false);
				$("#resultText").text(id + "는 가입 할 수 있습니다.");
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
		<form action="./join" method="post" id="join" name="join" onsubmit="return join()">	
			<p id="join_id">
				ID<br>
				<input type="text" method="post" id="id" name="id" placeholder="아이디 입력" onsubmit="idCheck()" required>
				<button type="button" id="id_check" onclick="idCheck()">중복확인</button>
				<p id="id_Result"></p>
			</p>
			<p id="join_pw">
				PASSWORD<br>
				<input type="password" id="pw1" name="pw1" placeholder="비밀번호 입력" required><br>
				<input type="password" id="pw2" name="pw2" placeholder="비밀번호 확인" onchange="isSame()" required><br>
				<span id="isSameWrite"></span>
			</p>
			<p id="join_nickName">
				이 게시판은 실명제로 운영됩니다. 실제 이름을 입력해주세요.<br> 
				이름 (아이디/비밀번호 찾기 시 이용)<br>
				<input type="text" id="nickName" name="nickName" placeholder="실제 이름 ex)김민수" required>
			</p>
			<p id="join_sex">
				성별
				<select id="sex" name="sex">
					<option value="man" id="man" name="man">남</option>
					<option value="woman" id="woman" name="woman">여</option>			
				</select>
			</p>
			<p id="join_birthDate">
				생일<br>
				<input type="date" id="birthDate" name="birthDate" min="1900-01-01" required>
			</p>
			<p id="join_email">
				아이디/비밀번호 찾기에 사용할 이메일<br>
				<input type="email" id="email" name="email" required>
			</p>
			<p id="join_hint">
				비밀번호 힌트<br>
				<input type="text" id="hint" name="hint" placeholder="비밀번호를 찾기 위한 힌트를 적어주세요." required>
			</p>
			<p id="join_hintAnswer">
				힌트의 답<br>
				<input type="text" id="hintAnswer" name="hintAnswer" placeholder="답을 적어주세요." required>
 			</p>
 			<p id="join_joinBox">
	 			<button type="submit" id="joinBtn">가입하기</button>
	 			<button type="button" onclick="location.href='./index.jsp'">뒤로가기</button>
			</p>
			</form>
	</div>
	<c:import url="footer.jsp"/>
</div>
</body>
</html>