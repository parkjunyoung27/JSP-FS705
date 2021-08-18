<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	#container{width:82%;float:left;height:100vh;}
		#FS705Write{width:100%;padding-bottom:10px;padding-top:10px;}
		#writeArea{width:100%;min-height:650px;overflow:hidden;clear:both;padding-bottom:10px;}
			#writeArea p{width:1030px;padding:10px;height:50px;line-height:50px;}
				#title input{width:1010px;height:40px;padding:10px;font-size:20px;font-weight:600;}
				#wcontent{width:1010px;clear:both;}
				#textC{width:1010px;padding:10px;}
				#textC textarea{width:100%;min-height:500px;padding:10px;resize:none;}
				#wcontent .filebox{width:1010px;height:30px;text-align:left;padding-left:10px;}
		#writeSub{width:100%;height:50px;line-height:50px;padding-top:25px;}
			#writeSub button{width:100%;height:50px;cursor:pointer;font-weight:600;font-size:17px;background-color:#5296D5;color:white;transition:0.3s;border:none;letter-spacing:10px;}
				#writeSub button:hover, #write_sub button:focus{background-color:#668D67;}

.filebox input[type="file"] {width: 0;height: 0;padding: 0;overflow: hidden;border: 0;}
.filebox label {display: inline-block;padding: 10px 20px;color: #999;vertical-align: middle;background-color: #fdfdfd;cursor: pointer;border: 1px solid #ebebeb;border-radius: 5px;}
/* named upload */
.filebox .upload{display: inline-block;width:400px;height: 35px;font-size:18px; padding: 0 10px;vertical-align: middle;background-color: #f5f5f5;border: 1px solid #ebebeb;border-radius: 5px;color:#999;}

</style>
<script>
$(document).ready(function(){
	$("#file").on('change',function(){
	  var fileName = $("#file").val();
	  $(".upload").val(fileName);
	});
	$("#wsBtn").click(function(){
		if(confirm("글쓰기를 완료하시겠습니까?")){
			if($("#file").val() == null || $("#file").val() == ""){
				if(!confirm("파일이 없을 경우 게시판에 표시되는 썸네일은 다른 사진으로 대체됩니다. 마무리 하시겠습니까?")){
					return false;
				}
			}
		}else{
			return false;
		}
	});
});
</script>
</head>
<body>
<div id="wrapper">
	<c:import url="/header.jsp"/>
	<div id="container">
		<div id="FS705Write">
			<form action="./sportsModify" method="post" enctype="multipart/form-data">
				<select name="category">
				    <option value="">카테고리</option>
				    <option value="맛집">맛집</option>
				    <option value="스포츠">스포츠</option>
				    <option value="게임">게임</option>
				    <option value="유머">유머</option>
				</select>
				<select name="semiCate">
				    <option value="">---</option>
				    <option value="1">야구</option>
				    <option value="2">배구</option>
				    <option value="3">축구</option>
				    <option value="4">농구</option>
				</select>
				<div id="writeArea">
					<p id="title">
						<input type="text" name="title" placeholder="제목을 입력하시오" value="${modifyImport.btitle }">
					</p>
					<div id="wcontent">
						<div id="textC">
							<textarea name="content" placeholder="내용을 입력하시오.">${modifyImport.bcontent }</textarea>
						</div>
						<div class="filebox"> 
							<label for="file">업로드</label> 
							<input type="file" id="file" name="file1" accept=".gif, .png, .jpg, .jpeg" value="./upload/sportsFile/${modifyImport.bfile }"> 
							<input type="hidden" name="file2" value="${modifyImport.bfile }">
							<input class="upload" value="파일을 선택해주세요." disabled="disabled">
						</div>
					</div>
				</div>
				<p id="writeSub">
					<input type="hidden" name="bno" value="${modifyImport.bno }">
					<button type="submit" id="wsBtn">글 수정하기</button>
				</p>
			</form>
		</div>
	</div>
	<c:import url="/plusBar.jsp"/>
	<c:import url="/footer.jsp"/>
</div>
</body>
</html>