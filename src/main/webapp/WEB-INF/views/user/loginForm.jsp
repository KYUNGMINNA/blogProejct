<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>
<div class="container">
    <form action="/auth/loginProc" method="post">

        <div class="form-group">
            <label for="username">ID</label>
            <input type="text" name="username" class="form-control" placeholder="ID" id="username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control" placeholder="Password" id="password">
        </div>

        <div class="btn-group ">

            <button id="btn-login" class="btn btn-primary" style="width: 150px; height: 38px; margin-right:10px; ">로그인</button>
            <a href="/oauth2/authorization/kakao" style="margin-right: 10px;">
                <img height="38"  width="150" src="/image/kakao_login_large_narrow.png">
            </a>


            <a href="/oauth2/authorization/google" style="margin-right: 10px;">
                <img height="40"  width="150" src="/image/google_login_button.png">
            </a>

            <a href="/oauth2/authorization/naver">
                <img height="38"  width="150" src="/image/naver_login_button.png">
            </a>
        </div>
    </form>

</div>

<br>
<script src="/js/user.js"></script>
<%@ include file="../layout/footer.jsp" %>
