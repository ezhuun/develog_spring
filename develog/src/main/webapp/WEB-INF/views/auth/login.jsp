<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>develog</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css" />
  </head>
  <body>
      <div class="wrapper-login">
      
        <div class="wrap__logo"><div class="logo_a"></div></div>

        <div class="wrap__sign">
          <form id="frm_login" method="post" onsubmit="return false">
            <div class="signBox-dark">
              <div class="signBox_flex_column">
                <div class="signBox_title">로그인이 필요합니다</div>
              </div>
              <div class="signBox_flex_column">
                <div class="signBox_input">
               		이메일<input type="email" id="login_email" name="email" class="input-text-dark" tabindex="1" required />
                	<input type="checkbox" name="isLogin" value="y" onchange="rememberMe(this);"/><span class="textSmall">로그인을 기억합니다</span>
                </div>
              </div>
              <div class="signBox_flex_column">
                <div class="signBox_input">
                	비밀번호<input type="password" id="login_passwd" name="passwd" class="input-text-dark" tabindex="2" required />
                	<a href="javascript:handlePasswdEdit();">비밀번호를 잊어버리셨습니까?</a>
                </div>
              </div>
              <div class="signBox_flex_column">
                <div class="signBox_btn">
                  <button onclick="handleClickLogin()">
                  	<div id="loading" class="none">
                  		<div></div>
                  		<div></div>
                  		<div></div>
                  		<div></div>
                  	</div>
                  	<span class="loginStr">로그인</span>
                  </button>
                  
                </div>
              </div>
              <div class="signBox_flex_column">
                <div class="signBox_signup">
                  	계정이 없으신가요? <a href="javascript:location.href='${pageContext.request.contextPath}/register'">가입하기</a>
                </div>
              </div>
            </div>
          </form>
        </div>

      </div>
      
  	  <span id="contextPathHolder" data-contextPath="${pageContext.request.contextPath}" style="display:none;"></span>
	  <div class="__popupLayout">
		  <div class="__popupBase">
	        <div class="popup_container" tabindex="0">
	            <h3>알려드립니다</h3>
	            <span class="popup_txt"></span>
	            <div class="button_area">
	            	<div class="added_button"></div>
	            	<button id="alertConfirm">확인</button>
	            </div>
	        </div>
	      </div>
      </div>
	<div class="modal_background">
		<div class="modal_container" tabindex="0">
			<div class="modal_title"></div>
			<div class="modal_form"></div>
			<div class="modal_button"></div>
		</div>
	</div>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/utils.js"></script>
      <script type="text/javascript" src="${pageContext.request.contextPath}/js/auth.js"></script>
  </body>
</html>

