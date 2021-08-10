<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- totalCount = 총 글수, PAGENUMBE = 한 화면에 나타낼 글 수  startPage = 그 페이지쪽수 단위의 첫번째 endPage = 그 페이지 쪽수의 마지막 쪽-->
<%//System.out.println("페이징에 넘어간 ip:" +  request.getParameter("ip")); %>
<%//System.out.println("페이징에 넘어간 target:"+request.getParameter("target")); %>

<c:choose>
	<c:when test="${ip ne null && target ne null }">
		<c:set var = "pageName" value="${pageName }?order=${order }&ip=${ip }&target=${target }&searchType=${searchType }&searchText=${searchText }&" scope="request"/>
	</c:when>
	<c:when test="${grade ne null && gender ne null}">
		<c:set var = "pageName" value="${pageName }?order=${order }&grade=${grade }&gender=${gender }&searchType=${searchType }&searchText=${searchText }&" scope="request"/>
	</c:when>
	<c:when test="${pageName eq 'boardm' && category eq null}">
		<c:set var = "pageName" value="${pageName }?order=${order }&" scope="request"/>
	</c:when>
	<c:when test="${pageName eq 'boardm' && category ne null}">
		<c:set var = "pageName" value="${pageName }?order=${order }&category=${category }&subCategory=${subCategory }&searchType=${searchType }&searchText=${searchText }&" scope="request"/>
	</c:when>	
	<c:otherwise>
		<c:set var = "pageName" value="${pageName }?" scope="request"/>	
	</c:otherwise>
</c:choose>

<fmt:parseNumber integerOnly="true" var="totalPage" value="${totalCount / PAGENUMBER }" scope="request"/> 
<c:if test="${totalCount % PAGENUMBER ne 0 }"> 
	<c:set var="totalPage" value="${totalPage+1 }" scope="request"/> 
</c:if>
			  
<c:if test="${page % LIMIT ne 0}">
<!--  예 2/5 = 2 => 0, 7/5 = 2 --> 
	<fmt:parseNumber integerOnly="true" var="startPage" value="${page / LIMIT }" scope="request"/> 
	<c:set value="${(startPage * LIMIT)+1 }" var="startPage" scope="request"/> <!-- 배수에서 +1 -->
	<!--  11,12 일때 -->
</c:if>
			 
<c:if test="${page % LIMIT eq 0 }">   <!-- 예  5, 10, 15 => 1로 가게-->
	<c:set value="${page-(LIMIT-1) }" var="startPage" scope="request"/>  <!-- 10쪽 설정 -->
</c:if>

<!-- LIMIT단위의 마지막 페이지 -->
<c:set value="${startPage+(LIMIT-1) }" var="endPage" scope="request"/> <!-- 10개씩 -->

<!-- 총 페이지수가 endpage보다 작을때 -->
<c:if test="${startPage+(LIMIT-1) gt totalPage}"> 
	<c:set var ="endPage" value="${totalPage }" scope="request"/>
</c:if>
			 
 <button onclick="location.href='./${pageName}page=1'">맨앞으로</button>
	
	<c:if test="${page lt (LIMIT+1)}"><!--현재page < 5(페이지넘버)+1 -->  <!-- 12345 에서 67810넘어갈때  -->
		<button disabled ="disabled">이전</button>
	</c:if>
	
	<c:if test="${page gt LIMIT}"> 
		<c:if test="${(page % LIMIT) eq 0 }">
		<button onclick="location.href='./${pageName}page=${endPage-LIMIT}'">이전</button>
		</c:if>
		<c:if test="${(page % LIMIT) ne 0 }">
		<button onclick="location.href='./${pageName}page=${page-(page % LIMIT)}'">이전</button>
		</c:if>
	</c:if>
					
		<c:forEach begin="${startPage }" end="${endPage }" var="i"> 
			<c:if test= "${i eq page }">
				<a href="./${pageName }page=${i}"><b>${i }</b></a>
			</c:if>
			<c:if test= "${i ne page }">
				<a href="./${pageName }page=${i}">${i }</a>
			</c:if>
		</c:forEach>
				
	<!-- 현재 페이지의 endpage가  총 페이지수 보다 작다면 -->
	<c:if test="${startPage+(LIMIT-1) lt totalPage}"> <!-- 현재 페이지 < 총 페이지   -->
		<button onclick="location.href='./${pageName }page=${startPage+LIMIT}'">다음 </button>
	</c:if>
	<c:if test="${startPage+(LIMIT-1) gt totalPage }">
		<button disabled="disabled">다음</button>
	</c:if>

	<button onclick="location.href='./${pageName }page=${totalPage}'">끝으로</button>	
			 