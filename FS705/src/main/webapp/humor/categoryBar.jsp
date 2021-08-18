<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>plusBar</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style type="text/css">
#categoryBar{width:10%;float:left;margin-top:10%;text-align:center;}
#categoryBar li{border:1px solid #eee;}
 li:hover{background-color:#D6EAF8;}
</style>
</head>
<body>
<div id="categoryBar">
 <ul>
            <li>
                <a href="./humorBoard">
                	전체보기
                </a>
            </li>
            <li>
                <a href="./humorBoard?category=1">
                    잡담
                </a>
            </li>
            <li>
                <a href="./humorBoard?category=2">
                    이슈
                </a>
            </li>
            <li>
                <a href="./humorBoard?category=3">
                    감동
                </a>
            </li>
            <li>
                <a href="./humorBoard?category=4">
                    정치
                </a>
            </li>
</ul>
</div>
</body>
</html>