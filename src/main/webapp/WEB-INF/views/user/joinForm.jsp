<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
    <form>

        <div class="form-group">
            <label for="username">ID</label>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="ID" id="username" style="margin-right: 10px">
                <button type="button"  id="accountIdCheck" class="account_id_check_btn btn btn-secondary" >아이디 중복 체크</button>
            </div>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" placeholder="****" id="password">
            <span  id="pass"></span>
        </div>

        <div class="form-group">
            <label for="passwordcheck">Password Check</label>
            <input type="password" class="form-control" placeholder="****" id="passwordcheck">
            <span  id="pass_same_check"></span>
        </div>

        <div class="form-group">
            <label for="accountEmailId">Email</label>
            <div class="input-group">
                <input type="text" class="form-control input-group-append" placeholder="email" id="accountEmailId" style="margin-right:10px">
                <h4 style="margin-right: 10px">@</h4>
                <select class="account_email_address form-control" id="accountEmailAddress" name="accountEmailAddress" style="margin-right: 10px">
                    <option>naver.com</option>
                    <option>daum.net</option>
                    <option>gmail.com</option>
                    <option>hanmail.com</option>
                    <option>yahoo.co.kr</option>
                </select>
                <button type="button" name="accountEmailAuthNumberSend" id="accountEmailAuthNumberSend" class="accountEmailAuthNumberSend btn btn-secondary">인증 번호 전송</button>

            </div>
        </div>

        <div class="form-group">
            <label for="accountEmailAuthNumberCheck"></label>
            <input type="text" name="accountEmailAuthNumberCheck" id="accountEmailAuthNumberCheck" class="accountEmailAuthNumberCheck col-3"  maxlength="8" disabled="disabled" style="margin-left: -6px; margin-right: 10px;" pattern="[0-9]{8}">
            <button type="button" id="auto_num_collect" class="btn btn-secondary">인증번호 확인</button>
            <br>
            <span id="auth_same"></span>
        </div>

        <input type="hidden" id="email" name="email">
    </form>

        <div class="d-flex justify-content-center">
            <button id="btn-save" class=" btn btn-primary">회원가입</button>
        </div>
</div>

<br>
<!-- 정적 파일은 static이 기본 경로 -->
<script src="/js/user.js"></script>
<script>
    //비밀번호 비밀번호 확인란 일치 여부
    $('#passwordcheck').focusout(function () {

        if($('#passwordcheck').val()===''){
            alert("비밀번호를 입력해 주세요.");
            return;
        }

        if ($('#password').val() === $('#passwordcheck').val()) {
            pw1=true;
            pw2=true;

            $('#pass_same_check').text('비밀번호가 일치합니다.');
        } else {
            $('#pass_same_check').text('비밀번호가 일치하지않습니다');
        }
    });

</script>
<%@ include file="../layout/footer.jsp"%>
