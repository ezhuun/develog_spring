//contextPath = global variable of utils.js

const initHelp = function() {
    const help = document.querySelectorAll(".signBox-dark .help");
    const inputTag = document.querySelectorAll(".signBox-dark .input-text-dark");
    const inputForm = document.querySelectorAll(".signBox-dark .signBox_input");

    for (let i = 0; i < help.length; i++) {
        help[i].remove();
    }
    for (let i = 0; i < inputForm.length; i++) {
        inputForm[i].classList.remove("colorRed");
        inputTag[i].classList.remove("lineRed");
    }
}

const createHelp = function(index, str) {
    const inputTag = document.querySelectorAll(".signBox-dark .input-text-dark");
    const inputForm = document.querySelectorAll(".signBox-dark .signBox_input");

    let span = document.createElement("span");
    span.classList.add("help");
    span.innerText = str;
    inputForm[index].classList.add("colorRed");
    inputTag[index].classList.add("lineRed");
    inputTag[index].before(span);
    inputTag[index].focus();
}

//===================================================================================
//login script
//-----------------------------------------------------------------------------------
const loading = document.querySelector(".signBox_btn button #loading");
const loginStr = document.querySelector(".signBox_btn button span");

const rememberMe = function(radio) {
    if (radio.checked === true) {
        utils.alert('우리는 최소 3일간 당신을 기억하겠습니다');
    }
}

const handlePasswdEdit = function() {
    initHelp();
    const email = document.querySelector('#login_email').value;
    if(email == ""){
        createHelp(0, "- 이메일을 입력하세요");
        return;
    }
    var regex = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    if (regex.test(email) == false) {
        createHelp(0, "- 형식이 올바르지 않습니다");
        return;
    }

    const param = {
        "email": email
    };
    $.ajax({
        url: contextPath + "/passwdEdit",
        method: "post",
        type: "json",
        data: param,
        beforeSend: function() {
            loading.classList.remove("none");
            loginStr.classList.add("none");
        },
        complete: function() {
            loading.classList.add("none");
            loginStr.classList.remove("none");
        },
        error: function(err) {
            utils.alert('서버통신오류');
            loading.classList.add("none");
            loginStr.classList.remove("none");
        },
        success: function(data) {
            if (JSON.stringify(data).trim() == "\"\"") {
                createHelp(0, "- 존재하지 않은 아이디입니다");
            } else if (data.result == 1) {
                utils.alert('계정 비밀번호 변경을 위해 이메일이 전송되었습니다. 받은 편지함과 스팸함을 확인해보세요.<br>만약, 비밀번호 변경을 원치 않으실 경우 메일은 무시하셔도 됩니다');
            }
        }
    });
}

const authMailSend = function() {
    const param = {
        "email": document.querySelector('#login_email').value
    };

    $.ajax({
        url: contextPath + "/reSendAuthEmail",
        method: "post",
        type: "json",
        data: param,
        beforeSend: function() {},
        complete: function() {},
        error: function(err) {
            utils.alert('서버통신오류');
        },
        success: function(data) {
            utils.alertClose();
            utils.alert('인증메일을 보냈습니다');
        }
    });
}

const handleClickLogin = function() {

    initHelp();
    const email = document.querySelector("#login_email").value;
    const passwd = document.querySelector("#login_passwd").value;
    let valid = ![email, passwd].includes('');

    if (valid) {
        var regex = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
        if (regex.test(email) == false) {
            valid = false;
            createHelp(0, "- 형식이 올바르지 않습니다");
            return;
        }
    }

    if (valid) {
        const param = $("#frm_login").serialize();
        $.ajax({
            url: contextPath + "/loginCheck",
            method: "post",
            type: "json",
            data: param,
            beforeSend: function() {
                loading.classList.remove("none");
                loginStr.classList.add("none");
            },
            complete: function() {
                loading.classList.add("none");
                loginStr.classList.remove("none");
            },
            error: function(err) {
                utils.alert('서버통신오류');
                loading.classList.add("none");
                loginStr.classList.remove("none");
            },
            success: function(data) {
                if (JSON.stringify(data).trim() == "\"\"") {
                    createHelp(0, "- 존재하지 않은 아이디입니다");
                } else {
                    if (data.passwdcheck == 0) {
                        createHelp(1, "- 비밀번호가 일치하지 않습니다");
                    } else {
                        //메일인증구현 완료시 아래 체크 1 => 0로 변경
                        if (data.auth_status == 0) {
                            utils.alert('이메일 인증이 완료되지 않았습니다<br>메일이 도착하지 않았을 경우 다시보내기 버튼을 눌러주세요', '',
                                '<button onclick=\'authMailSend();\'>다시보내기</button>');
                        } else {
                            location.href = contextPath + '/projects/list';
                        }
                    }
                }
            }
        });
    }
}

//===================================================================================
//register script
//-----------------------------------------------------------------------------------
const handleClickRegister = function() {
    initHelp();
    const email = document.querySelector("#register_email").value.trim();
    const name = document.querySelector("#register_name").value.trim();
    const passwd = document.querySelector("#register_passwd").value.trim();

    let valid = ![email, name, passwd].includes('');
    if (!valid) {
        return;
    }
    var regex = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
    if (regex.test(email) == false) {
        valid = false;
        createHelp(0, "- 형식이 올바르지 않습니다");
        return;
    }
    if (name.length < 2) {
        valid = false;
        createHelp(1, "- 최소 2자리 이상 입력해주세요");
        return;
    }
    if (passwd.length < 4) {
        valid = false;
        createHelp(2, "- 최소 4자리 이상 입력해주세요");
        return;
    }
    if (valid == true) {
        const param = $("#frm_register").serialize();

        $.ajax({
            url: contextPath + "/registerCheck",
            method: "post",
            type: "json",
            data: param,
            beforeSend: function() {
                loading.classList.remove("none");
                loginStr.classList.add("none");
            },
            complete: function() {
                loading.classList.add("none");
                loginStr.classList.remove("none");
            },
            error: function(err) {
                utils.alert('서버통신오류');
                loading.classList.add("none");
                loginStr.classList.remove("none");
            },
            success: function(data) {
                if (JSON.stringify(data).trim() == "\"\"") {
                    utils.alert('결과 반환값이 존재하지 않습니다');
                } else {
                    if (data.result == 0) {
                        utils.alert('DB Error');
                    } else if (data.result == 2) {
                        createHelp(0, '- 중복된 이메일입니다');
                    } else if (data.result == 1) {
                        utils.alert('가입이 완료되었습니다.<br>인증 이메일을 보내드렸으니 인증완료 후 이용가능합니다',
                            contextPath + '/login');
                    }
                }
            }
        });
    }
}

//===================================================================================
//re register password script
//-----------------------------------------------------------------------------------
const rePasswdCheck = function() {
    initHelp();
    const passwd = document.frm_rePasswd.passwd.value.trim();
    const passwd_check = document.frm_rePasswd.passwd_check.value.trim();
    
    if(passwd != passwd_check){
        createHelp(0, "- 비밀번호가 일치하지 않습니다");
        return;
    }

    if (passwd.length < 4) {
        createHelp(0, "- 최소 4자리 이상 입력해주세요");
        return;
    }
    
    const loading = document.querySelector(".signBox_btn button #loading");
    const loginStr = document.querySelector(".signBox_btn button span");
    const param = $("#frm_rePasswd").serialize();
    $.ajax({
        url: contextPath + "/rePasswdCheck",
        method: "post",
        type: "json",
        data: param,
        beforeSend: function() {
            loading.classList.remove("none");
            loginStr.classList.add("none");
        },
        complete: function() {
            loading.classList.add("none");
            loginStr.classList.remove("none");
        },
        error: function(err) {
            utils.alert('서버통신오류');
            loading.classList.add("none");
            loginStr.classList.remove("none");
        },
        success: function(data) {
            console.log(data);
            if (data.result == 1) {
                utils.alert('비밀번호를 변경했습니다', contextPath + '/');
            } else {
                utils.alert('비밀번호를 변경하지 못했습니다');
            }
        }
    });
}

