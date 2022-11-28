let id = false, pw1 = false, pw2 = false, emailid = false, authCheck = false;

let index = {
    init: function () {
        //회원가입
        $("#btn-save").on("click", () => { //function(){}  , ()=>{} this를 바인딩하기 위해서 !
            this.save();
        });

        //정보 업데이트
        $("#btn-update").on("click", () => { //function(){}  , ()=>{} this를 바인딩하기 위해서 !
            this.update();
        });

        //아이디 중복 체크
        $('#accountIdCheck').on("click", () => {
            this.idcheck();
        })
        //이메일 인증 번호 전송
        $('#accountEmailAuthNumberSend').on('click', () => {
            this.emailauth();
        })
        //이메일 인증 번호 확인
        $('#auto_num_collect').on('click', () => {
            this.emailnumcheck();
        })


    },

    save: function () {
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $('#accountEmailId').val() + '@' + $('#accountEmailAddress').val()
        };
        if (id === true && pw1 === true && pw2 === true && emailid === true && authCheck === true) {

        $.ajax({
            //회원가입 수행 요청
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),  //json 문자열로 변환 --http body 데이터임
            contentType: "application/json;charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼로와서 문자열
                             //, 생긴게(json)이라면 =>javascript 오브젝트로 변경해줌
        }).done(function (resp) {

            if (resp.status === 500) {
                alert("회원 가입에 실패하였습니다.");
            } else {
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            }

        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
        }else{
            alert('아이디 ,비밀번호 , 이메일 ,인증번호를  확인해 주세요.')
        }
    },

    update: function () {
        //alert("save 함수 호출됨");
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };

        $.ajax({
            //회원가입 수행 요청
            type: "PUT",
            url: "/user",
            data: JSON.stringify(data),  //json 문자열로 변환 --http body 데이터임
            contentType: "application/json;charset=utf-8", //body 데이터가 어떤 타입인지(MIME)
            dataType: "json" //요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 버퍼로와서 문자열
                             //, 생긴게(json)이라면 =>javascript 오브젝트로 변경해줌
        }).done(function (resp) {
            alert("회원수정이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    //아이디 중복 확인
    idcheck: function () {
        let username = $('#username').val();
        if (username === '') {
            alert('아이디를 입력해 주세요.');
            return;
        }

        $.ajax({
            type: "GET",
            url: "/check/id/" + username,
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 200) {

                alert('사용하실수 있는 아이디입니다.');
                $('#accountId').attr('readonly', true);
                id = true;
            } else {
                alert('사용 불가능한 아이디 입니다. 다시 입력해 주세요');
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },




    //이메일 인증번호 전송
    emailauth: function () {
        let email = $('#accountEmailId').val() + '@' + $('#accountEmailAddress').val();

        if ($('#accountEmailId').val() === '') {
            alert('이메일을 다시 입력해 주세요');
            return;
        }

        $.ajax({
            type: "GET",
            url: "/check/email/" + email,
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 200) {
                $('#accountEmailAuthNumberCheck').attr('disabled', false);
                alert('이메일에 인증번호를 전송하였습니다.');
                emailid = true;
                $('#authnum').val(resp.data);
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    },
    //이메일 인증번호 확인
    emailnumcheck: function () {
        let authnum = $('#accountEmailAuthNumberCheck').val();
        if (authnum === '') {
            alert('인증 번호를 입력해 주세요.');
            return;
        }

        $.ajax({
            type: "GET",
            url: "/check/emailauthnum/" + authnum,
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 200) {
                $('#authnum').attr('disabled', false);

                authCheck = true;
                $('#auth_same').text('인증번호가 일치합니다.');
            } else {
                return;
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    }


}

index.init();