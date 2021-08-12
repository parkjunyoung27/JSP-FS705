<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:parseNumber integerOnly="true" var="totalPage" value="${totalCount / PAGENUMBER }" scope="request" />
<c:if test="${totalCount % PAGENUMBER ne 0 }">
	<c:set var="totalPage" value="${totalPage + 1 }" scope="request"  />
</c:if>

<fmt:parseNumber integerOnly="true" var="startPage"	value="${page / PAGENUMBER }" scope="request" />
<c:if test="${page % PAGENUMBER ne 0 }">
	<c:set value="${startPage * PAGENUMBER + 1 }" var="startPage" scope="request" />
</c:if>
<c:if test="${page % PAGENUMBER eq 0 }">
	<c:set value="${page - (PAGENUMBER - 1) }" var="startPage" scope="request" />
</c:if>

<c:set value="${startPage + (PAGENUMBER - 1) }" var="endPage" scope="request" />
<c:if test="${startPage + (PAGENUMBER - 1) gt totalPage }">
	<c:set var="endPage" value="${totalPage }" scope="request" />
</c:if>


<div>
	<a href="./${pageName }?page=1">처음</a>
</div>
<div>
	<c:if test="${page lt PAGENUMBER + 1 }">
		<a href="./${pageName }?page=${startPage }">이전</a>
	</c:if>
	<c:if test="${page gt PAGENUMBER + 1 }">
		<a href="./${pageName }?page=${page - PAGENUMBER}">이전</a>
	</c:if>
</div>

<div>
	<c:if test="${page eq 1 }">
		<a href="./${pageName }?page=${page }">◀</a>
	</c:if>
	<c:if test="${page ne 1 }">
		<a href="./${pageName }?page=${page - 1}">◀</a>
	</c:if>
</div>
<div></div>
<c:forEach begin="${startPage }" end="${endPage }" var="i">
	<c:if test="${i eq page }">

	</c:if>

	<div>
		<a href="./${pageName }?page=${i }"> <c:choose>
				<c:when test="${i eq 1 && page eq 0 }">
					<b>${i }</b>
				</c:when>
				<c:when test="${i eq page }">
					<b>${i }</b>
				</c:when>
				<c:otherwise>
								${i }
							</c:otherwise>
			</c:choose>
		</a>
	</div>
</c:forEach>
<div>
	<c:if test="${page eq totalPage }">
		<a href="./${pageName }?page=${totalPage }">▶</a>
	</c:if>
	<c:if test="${page ne totalPage }">
		<a href="./${pageName }?page=${page + 1 }">▶</a>
	</c:if>

</div>

<div>
	<c:if test="${page lt totalPage - (PAGENUMBER - 1) }">
		<a href="./${pageName }?page=${page + PAGENUMBER }">다음</a>
	</c:if>
	<c:if test="${page gt totalPage - PAGENUMBER }">
		<a href="./${pageName }?page=${totalPage }">다음</a>
	</c:if>
</div>
<div>
	<a href="./${pageName }?page=${totalPage }">끝</a>
</div>