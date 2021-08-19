<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
/*------------------------------- reset css -------------------------------*/
*{padding:0; margin:0;list-style:none;font-family:'빙그레 메로나체';}
a:link, a:visited{text-decoration:none;color:#333;}

/*------------------------------- wrapper css -------------------------------*/
#wrapper{width:1280px;margin:0 auto;}

/*------------------------------- container css -------------------------------*/
	#container{width:82%;float:left;padding-bottom:100px;}
		#content{width:100%;min-height:600px;padding-bottom:10px;border-bottom:1px solid gray;}
		#boardlist{width:1010px;margin:0 auto;padding-top:10px;border-bottom:1px solid gray;}
			#boardlist div{width:100%;background-color:white;margin-bottom:10px;overflow:hidden;}
			#boardlist .btop{height:45px;line-height:45px;font-weight:600;text-align:left;text-indent:10px;font-size:18px;border-bottom:1px solid gray;}
			#boardlist .bmiddle{border-bottom:1px solid gray;line-height:20px;}
				#boardlist .bmiddle .bmfloat{width:100%;height:30px;clear:both;overflow:hidden;}
					#boardlist .bmiddle .bmfloat li{float:left;}
					#boardlist .bmiddle .bmfloat li:first-child{width:60%;text-align:left;text-indent:10px;}
					#boardlist .bmiddle .bmfloat li:nth-child(2){width:24%;text-align:right;}
					#boardlist .bmiddle .bmfloat li:nth-child(3){width:9%;}
					#boardlist .bmiddle .bmfloat li:last-child{width:7%;}	
						#boardlist .bmiddle .bmfloat .line{height:15px;background-color:gray;border:1px solid gray;display:inline-block;float:right;margin-top:1px;overflow:hidden;clear:both;}
			#boardlist .bbottom{width:990px;min-height:500px;padding:10px;margin-bottom:0px;text-align:left;}
			#likeDislike{width:100%;text-align:center;}
					#likeDislike img{width:40px;}

#ccount{display:inline-block;height:16px;line-height:16px;font-weight:300;}	

/* ---------------------------------------------e댓글 화면 구현--------------------------------------------- */
	#comment{width:100%;padding-top:10px;}		
		#comment ul{width:100%;}
			#comment ul li{width:100%;}
			#commentTrue{background-color:#ccc;text-align:center;}
			#noComment{text-align:center;}
			#commentSee{background-color:#fff;transition:0.3s;cursor:pointer;}
				#commentSee:hover{background-color:#eee;}
			#commentContainer{background-color:#fff;border-top:1px solid #eee;}
				#commentbox{width:80%;margin:0 auto;padding-bottom:10px;}
					#cbPlus{width:100%;height:30px;line-height:30px;overflow:hidden;border-bottom:1px dashed #525976;}
						#reWrite{font-size:10px;display:inline-block;border:1px solid gray;height:12px;line-height:12px;cursor:pointer;}
						#cbPlus p{float:left;}
						#cbPlus p:first-child{width:60%;text-align:left;}
						#cbPlus p:nth-child(2){width:30%;text-align:right;}
						#cbPlus p:last-child{width:10%;}
					#cbContent{width:100%;min-height:40px;padding-bottom:10px;padding-top:10px;border-bottom:1px solid gray;text-align:left;}
					
					
			#commentWrite{padding-top:20px;}
				#cwArea{width:80%;border:1px solid gray;height:80px;margin:0 auto;overflow:hidden;}
					#cwArea p{float:left;}
						#cwText{width:90%;height:80px;text-align:left;}
							#cwText textarea{width:99%;height:75px;padding-top:5px;padding-left:1%;overflow:auto;resize: none;}
						#cwTSend{width:10%;height:80px;font-size:12px;}
							#cwTSBtn{width:100%;height:100%;line-height:80px;cursor:pointer;transition:0.3s;}
								#cwTSBtn:hover, #cwTSBtn:focus{background-color:#5499C7;}
textarea:focus {
  outline: none;
}
button:focus, button:hover {
  outline: none;
}


	#write{width:100%;height:35px;line-height:35px;margin-top:20px;background-color:#668D67;border-radius:5px;cursor:pointer;transition:0.3s;}
		#write a{display:block;width:100%;height:100%;color:white;text-align:center;}
		#write:hover, #write:focus{background-color:#5296D5;}
		
		#cwArea2{width:100%;border:1px solid gray;height:80px;margin:0 auto;overflow:hidden;z-index:7;}
			#cwArea2 p{float:left;}
				#cwText2{width:85%;height:80px;text-align:left;}
					#cwText2 textarea{width:99%;height:75px;padding-top:5px;padding-left:1%;overflow:auto;resize: none;}
				#cwTSend2{width:15%;height:80px;font-size:12px;}
					#cwTSend2 button{width:100%;height:100%;line-height:80px;cursor:pointer;transition:0.3s;}
						#cwTSend2 button:hover, #cwTSend2 button:focus{background-color:#6FB3A3;}
#nextPrev{
	text-align:center;
}
</style>
<script>
$(function(){
	//a태그 hover이벤트 tab키 먹히게 하는 기능
	$(".title a").bind("mouseover focus",function(){
		$(this).css({"border":"none"});
		$(this).parents(".main").css({"background-color":"#D6EAF8"});
	});
	$(".title a").bind("mouseout focusout",function(){
		$(this).parents(".main").css({"background-color":"transparent"});
	});
	
	var now = 0;
	$("#comment_container").hide();
	$("#comment_see").bind("click focus",function(){
		var offset = $("#comment_write").offset();
		$("html, body").animate({scrollTop:offset.top},400);
		if(now == 0){		
			$("#comment_container").slideDown(1000);
			$(this).text("▲");
			now=1;
		}
		else{
			$("#comment_container").slideUp(1000);
			$(this).text("▼");
			now=0;
		}
	});
	var txtw = 0;
	$(".modifyWrite").hide();
	$(".clear1").bind("click focus",function(){
		if(txtw == 0){		
			$(this).parent().hide();
			$(this).parent().siblings().show();
			txtw=1;
		}
		else{
			$(this).parent().siblings().show();
			$(this).parent().hide();
			txtw=0;
		}
	});
});  
function foodVote(bno, code){
	if(code == 'blike'){
		if(confirm("해당 글을 추천하시겠습니까?")){
			location.href="./noticeViewVote?vote=" + code + "&bno=" + bno;
		}
	}else if(code == 'bdislike'){
		if(confirm("해당 글을 싫어하시겠습니까?")){
			location.href="./noticeViewVote?vote=" + code + "&bno=" + bno;
		}			
	}
}
function foodcVote(cno, bno){
	if(confirm("해당 댓글을 추천하시겠습니까?")){
			location.href="./noticeViewVote?bno=" + bno + "&cno=" + cno;	
	}
}
</script>
</head>
<body>
<div id="wrapper">
	<c:import url="/header.jsp"/>
	<div id="container">
		<div id="content">
			<div id="boardlist">
				<div id="nextPrev">
                     <c:if test="${dto.bno gt dto.bno2}">
                        <a href="./foodView?bno=${dto.bno }&nextPrev=1" title="이전글">이전글&nbsp;&nbsp;</a>               
                     </c:if>
                     <c:if test="${dto.bno lt dto.bno3}">
                        <a href="./foodView?bno=${dto.bno }&nextPrev=2" title="다음글" >&nbsp;&nbsp;다음글</a>                  
                     </c:if>
                 </div>
				<div class="btop">${dto.btitle }<small id="ccount">(${dto.bno })</small></div>
				<div class="bmiddle">
					<ul class="bmfloat">
						<li>${dto.name }</li>
						<li>${dto.bdate }&nbsp;&nbsp;&nbsp;<span class="line"></span></li>
						<li>&nbsp;조회수: ${dto.bcount }<span class="line"></span></li>
						<li>&nbsp;댓글: ${dto.foodcommentcount }</li>
					</ul>
				</div>
				<div class="bbottom">
					<br>
					<c:if test="${dto.bfile ne null}">
						<img alt="${dto.bfile }" src="./upload/foodUpload/${dto.bfile }" width="60%">
					</c:if>
					<br>
					${dto.bcontent }
					<br>
				</div>
				<div id="likeDislike">
					<button id="likeBtn"><img src="./img/like2.png" alt="좋아요">좋아요</button>
					<button id="disLikeBtn"><img src="./img/dislike.png" alt="싫어요">싫어요</button>
				</div>
			</div>
				<div id="comment">
				<ul>
					<c:choose>
						<c:when test="${dto.foodcommentcount > 0 }">
							<li id="commentTrue">${dto.foodcommentcount }개의 댓글이 있습니다.</li>
							<li id="commentSee">▼</li>
							<li id="commentContainer">
							
								<c:choose>
									<c:when test="${true }">
										<c:forEach items="${cmtdto }" var="cmt">
											<div id="commentbox">
												<div id="cbPlus">
													<p>${cmt.name}(${cmt.id })</p>
													<p>${cmt.cdate }</p>
													<p>  <a href='#' onclick="foodcVote(${cmt.cno }, ${cmt.bno });">
													<img src="./img/like.png" alt="좋아요" style="width:14px;cursor:pointer;"></a>&nbsp;${cmt.clike }</p>
												</div>
												<div class="modifyInputP">
													<div class="modifyInput"> 
													 	<div id="cbContent">${cmt.ccontent }</div>
													 	
													 	<c:if test="${cmt.id eq sessionScope.id }">
														<span id="reWrite" class="clear1">수정하기</span>
													 	<div class="fno" style="display: none;">${cmt.bno }</div>
													 	<div class="fcno" style="display: none;">${cmt.cno }</div>
													 	</c:if>
													 	<c:if test="${cmt.id eq sessionScope.id || sessionScope.grade eq 9 }">
														<div class="commentDelete">
														<form action="./foodCommentDelete" method="post">
															<input type="hidden" name="bno" value="${cmt.bno }"><input type="hidden" name="cno" value="${cmt.cno }">
															<button id="reWrite">삭제하기</button>
														</form>
														</div>
														</c:if>
													
													</div>
													<div class="modifyWrite">
														<form action="./foodCommentModify" method="post">
															<textarea name="ccontent" style="width:100%;height:100px;overflow:auto;resize: none;">${cmt.ccontent }</textarea>
															<input type="hidden" name="bno" value="${cmt.bno }"><input type="hidden" name="cno" value="${cmt.cno }">
															<button id="reWrite">수정하기</button>
														</form>
								 						<span id="reWrite" class="clear1">수정취소</span>
													</div>
												</div>
											</div>		
										</c:forEach>									
									</c:when>
								</c:choose>
							</li>
						</c:when>
						<c:otherwise>
							<li id="noComment">댓글이 없습니다. 댓글을 달아주세요.</li>
						</c:otherwise>
					</c:choose>
					<c:if test="${sessionScope.id ne null }">
						<li id="commentWrite">
							<div id="cwArea">
								<form action="./foodCommentWrite" method="post">
									<p id="cwText">
										<textarea name="ccontent"></textarea>
										<input type="hidden" name="bno" value="${dto.bno }">
									</p>
									<p id="cwTSend">
										<button id="cwTSBtn">댓글 보내기</button>
									</p>											
								</form>
							</div>
						</li>
					</c:if>
				</ul>
			</div>
			<c:if test="${sessionScope.id ne null }">
		<div class="writeBox" align="right">
		<a href="./foodWrite">글쓰기</a>	
		</div>
		</c:if>
		<c:if test="${dto.id eq sessionScope.id }">
		<div>
		<a href="./foodModify?bno=${dto.bno }">수정하기</a>	
		</div>
		</c:if>
		<c:if test="${dto.id eq sessionScope.id || sessionScope.grade eq 9 }">
		<div>
		<a href="./foodDelete?bno=${dto.bno }">삭제하기</a>	
		</div>
		</c:if>
		</div>
	<div id="write"><a href="./foodBoard">게시판으로 이동</a></div>
	</div>
	<c:import url="/plusBar.jsp"/>
	<c:import url="/footer.jsp"/>
</div>
</body>
</html>
