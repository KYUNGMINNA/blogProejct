<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- com.cos.blog.auth.PrincipalDetail 클래스 파일이  var ="principal" 임!!! -->
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>


</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

    <!--원래 스크립트는 footer body바로위에가 좋으나 , footer에서 js를 src 하는데 , jquery를 사용해서 이렇게 옮김 -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

    <!--summber note -->
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <a class="navbar-brand" href="/"><h3>홈</h3></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="collapsibleNavbar">

        <c:choose>
            <c:when test="${empty principal}">
                <ul class="navbar-nav">
                    <li class="nav-item">            <!--/auth 는 로그인 안된사람이 이용가능한 것-->
                        <a class="nav-link" href="/auth/loginForm"><h3>로그인</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/joinForm"><h3>회원가입</h3></a>
                    </li>
                </ul>
            </c:when>

            <c:otherwise><h4></h4>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/board/saveForm"><h3>글쓰기</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/updateForm"><h3>회원정보</h3></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout"><h3>로그아웃</h3></a>
                    </li>
                </ul>
            </c:otherwise>
        </c:choose>




    </div>
</nav>
<br/>
