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
                <a href="./gameBoard?category=1">
                    리그오브레전드
                </a>
            </li>
            <li>
                <a href="./gameBoard?category=2">
                    오버워치
                </a>
            </li>
            <li>
                <a href="./gameBoard?category=3">
                    배틀그라운드
                </a>
            </li>
            <li>
                <a href="./gameBoard?category=4">
                    모바일
                </a>
            </li>
            <li>
                <a href="./gameBoard">
                기타
                </a>
            </li>
</ul>
</div>
</body>
</html>