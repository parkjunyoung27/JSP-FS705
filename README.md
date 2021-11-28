# JSP를 활용하여 취미게시판을 구현했습니다. 

## C조 FS705팀 - 국비학원 팀프로젝트입니다.

## skills 
1. OS - linux(ubuntu 20.04LTS)
2. Develop tool - 이클립스, HeidiSQL 11.3
3. DB - MariaDB
4. Language - Java, JavaScript, AJAX, HTML5, JSP, JSTL
5. Server - Apache tomcat9

## 목적 
+ 조금 더 간편한 UI로 사용자가 쉽게 자신의 취미에 관련된 얘기를 나눌 수 있게 하기위해 만들었습니다.

##  참고

+ 디테일 페이지에 댓글쓰는 기능은 지금 `sessionScope.id eq null`로 해서 보이게끔 해놨는데,
  나중에 로그인 기능 구현되고 나면 `sessionScope.id ne null`로 바꿔야합니다~

## 화면 구현

1. 메인페이지<br>
![image](https://user-images.githubusercontent.com/73810338/143730091-3e9a75b8-a8b7-40da-92af-80a33dc9f867.png)<br>
[설명] - 통합테이블에서 각각 카테고리별로 쿼리문을 작성해 리스트 정렬

2.관리자 페이지

![관리자1](https://user-images.githubusercontent.com/73810338/143730163-fb2f7d54-8fba-4782-aaa0-15b6e14c0317.png)<br>
[설명] 관리자 로그인<br>
![관리자2](https://user-images.githubusercontent.com/73810338/143730165-ab742061-5bb5-4601-98ad-58c39bdb1030.png)<br>
[설명] 회원 관리 기능<br>
![관리자3](https://user-images.githubusercontent.com/73810338/143730166-91cd6f17-5b2f-423a-bbaf-1ac7faa32eac.png)<br>
[설명] 게시글 관리<br>
![관리자4](https://user-images.githubusercontent.com/73810338/143730167-b08aa563-91eb-4a61-b209-31d1aba5ac9e.png)<br>
[설명] 공통 검색기능<br>

## db설계 
![DB설계](https://user-images.githubusercontent.com/73810338/143729942-00642237-3baa-45a2-9eda-e0caa7bb7474.PNG) <br>
[설명] 통합보드를 구현하여 한개의 게시판 테이블에서 처리가능할 수 있게 설계 

***
#개선점 
1. 여러 선택 옵션 검색시에 페이징 문제와 쿼리문을 좀 더 간편하게 쓸 수 있는 방법 찾아보기 
