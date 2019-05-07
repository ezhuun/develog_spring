<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>develog</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
    <style type="text/css">
      /* account setting */
		.user_view_container {
			box-sizing: border-box;
			padding-left: 2rem;
			color: #919191;
			max-width: 37.5rem;
		}
		
		.user_view_group {
			position: relative;
			display: flex;
		}
		
		.user_view_rows {
			flex: 1;
			margin: 0 0 1rem;
		}
		
		.user_view_cols {
			margin: 0 0 1.75rem;
		}
		
		.user_view_selectionHeader {
			margin: 0 0 0.75rem;
			font-size: 1.05rem;
		}
		
		.user_view_info {
			margin: 0 0 0.75rem;
			font-size: 0.875rem;
			word-break: break-all;
		}
		
		.user_view_link {
			color: #3CABFA;
			font-size: 0.875rem;
		}
		.user_view_link span{
			cursor: pointer;
		}
		
		.user_profile_photo_area {
			position: relative;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: flex-start;
		}
		
		.user_profile_circle {
			position: relative;
			color: #fff;
			width: 7rem;
			height: 7rem;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 100%;
			margin-bottom: .5rem;
			overflow: hidden;
			background-color: #18A0FB;
		}
		
		.user_profile_circle span {
			font-size: 2rem;
			font-weight: bold;
		}
		
		.user_profile_circle input {
			display: none;
		}
		
		.user_profile_circle label::after {
			content: '\270E';
			position: absolute;
			top: 0;
			left: 0;
			width: 7rem;
			height: 7rem;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 100%;
			font-size: 1rem;
			color: #fff;
			background-color: rgba(0, 0, 0, .2);
			opacity: 0;
		}
		
		.user_profile_circle label:hover::after {
			opacity: 1;
		}
		
		.user_profile_circle img {
			position: absolute;
			top: 0;
			left: 0;
		}
		
		@media (max-width: 768px) {
			.user_view_group {
				display: flex;
    			flex-direction: column;
    			align-items: flex-start;
    			justify-content: center;
			}
		}
    </style>
  </head>
  <body>

    <div class="__wrap">
    
      <div class="wrap__left">
        <div class="lef-top"><div class="logo_b" onclick="location.href='${pageContext.request.contextPath}/projects/list';"></div></div>
        <div class="lef-content">
          <div class="lef-search">
            <span class="fas fa-search"></span><input type="text" placeholder="Search"/>
          </div>
          <div class="lef-menus">
            <ul class="menuList">
              <li onclick="location.href='${pageContext.request.contextPath}/projects/list';">
                <span class="far fa-copy"></span>Projects
              </li>
              <li>
                <span class="far fa-newspaper"></span>Board
              </li>
              <li>
                <span class="fas fa-download"></span>Archive
              </li>
              <li>
                <span class="far fa-image"></span>Gallery
              </li>
              <li class="line"></li>
              <li>
                <span class="fas fa-comments"></span>Chat
              </li>
            </ul>
          </div>
        </div>
      </div>
      
      <div class="wrap__right">
        <header class="header">
          <div class="header-toggle">
            <span class="fas fa-arrow-left"></span>
          </div>
          <div class="header-item">
            <span class="title-describe">Account Setting</span>
          </div>
          <div class="header-account">
            <div class="account_circle">
       			<c:if test="${not empty member.updir_thumnail}">
       				<img id="profileThumnail" src="${pageContext.request.contextPath}/upload${member.updir_thumnail}" width="24" onError="this.style.visibility='hidden'">
       			</c:if>
            	E
            </div>
            <div class="account_name">${member.name}</div>
            <span class="fas fa-sort-down"></span>
            <div class="account_menu">
              <div onclick="location.href='${pageContext.request.contextPath}/account/setting';">Account setting</div>
              <div onclick="location.href='${pageContext.request.contextPath}/logout'">Sign out</div>
            </div>
          </div>
        </header>
        <div class="container">
        	<div class="user_view_container">
	        	<h4>Account Setting</h4>
	        	
	        	<div class="user_view_group">
	        		<div class="user_view_rows">
			        	<div class="user_view_cols">
				        	<div class="user_view_selectionHeader">Uid</div>
				        	<div class="user_view_info"><span id="currentUuid">${member.uuid}</span></div>
			        	</div>
			        	<div class="user_view_cols">
				        	<div class="user_view_selectionHeader">Email</div>
				        	<div class="user_view_info">${member.email}</div>
			        	</div>
			        	<div class="user_view_cols">
				        	<div class="user_view_selectionHeader">Name</div>
				        	<div class="user_view_info"><span id="currentName">${member.name}</span></div>
				        	<div class="user_view_link"><span id="changeName">Change name</span></div>
			        	</div>
			        	<div class="user_view_cols">
				        	<div class="user_view_selectionHeader">Password</div>
				        	<div class="user_view_link"><span id="changePasswd">Change password</span></div>
			        	</div>
		        	</div>
		        	<div class="user_profile_photo_area">
		        		<div class="user_profile_circle">
		        			<c:if test="${not empty member.updir_original}">
		        				<img id="profileImage" src="${pageContext.request.contextPath}/upload${member.updir_original}" width="112" onError="this.style.visibility='hidden'">
		        			</c:if>
		        			<span>E</span>
		        			<label for="profile">
		        				<input type="file" accept="image/*" id="profile" name="profile">
		        			</label>
		        		</div>
		        		<div class="user_view_link">
		        			<span id="delProfilePhoto">
		        				<c:if test="${not empty member.updir_original}">
		        					Delete Profile Photo
		        				</c:if>
		        			</span>
		        		</div>
		        	</div>
	        	</div>
	        	
	        	<h4>Account</h4>
	        	<div class="user_view_link"><span id="memberDelete">Delete Account</span></div>
        	</div>
        	
        </div>
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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
    <script type="text/javascript">
		//===================================================================================
		//account setting
		//-----------------------------------------------------------------------------------
		const initHelp2 = function () {
			const modalFormCols = document.querySelectorAll(".modal_form_cols");
			const modalFormColsInput = document.querySelectorAll(".modal_form_cols input");
			const help = document.querySelectorAll(".modal_form_cols .help");
		
			for (let i = 0; i < help.length; i++) {
				help[i].remove();
			}
			for (let i = 0; i < modalFormCols.length; i++) {
				modalFormCols[i].classList.remove("colorRed");
				modalFormColsInput[i].classList.remove("lineRed");
			}
		}
		
		
		const createHelp2 = function (index, str) {
			const modalFormCols = document.querySelectorAll(".modal_form_cols");
			const modalFormColsInput = document.querySelectorAll(".modal_form_cols input");
		
			let span = document.createElement("span");
			span.classList.add("help");
			span.innerText = str;
		
			modalFormCols[index].classList.add("colorRed");
			modalFormColsInput[index].classList.add("lineRed");
			modalFormColsInput[index].before(span);
			modalFormColsInput[index].focus();
		}

		const handleClickChangePasswd = function () {
			initHelp2();
			const currentUuid = document.querySelector("#currentUuid").innerText.trim();
			const currentPasswdCheck = document.querySelector("#currentPasswdCheck").value.trim();
			const newPasswd = document.querySelector("#newPasswd").value.trim();
			const newPasswdCheck = document.querySelector("#newPasswdCheck").value.trim();

			if (currentPasswdCheck == "") {
				createHelp2(0, " - 내용을 입력해주세요");
				return;
			}
			if (newPasswd == "") {
				createHelp2(1, " - 내용을 입력해주세요");
				return;
			}
			if (newPasswdCheck == "") {
				createHelp2(2, " - 내용을 입력해주세요");
				return;
			}
			if (newPasswd.length < 4) {
				createHelp2(1, " - 최소 4자리 이상 입력해주세요");
				return;
			}
			if (newPasswd != newPasswdCheck) {
				createHelp2(1, " - 비밀번호가 일치하지 않습니다");
				return;
			}

			const param = {
				"uuid": currentUuid,
				"currentPasswd": currentPasswdCheck,
				"newPasswd": newPasswd
			};

			$.ajax({
				url: contextPath + "/changePasswd",
				method: "post",
				type: "json",
				data: param,
				success: function (data) {
					if (data.result == "2") {
						createHelp2(0, " - 비밀번호가 일치하지 않습니다");
						return;
					} else if (data.result == "1") {
						utils.alert('비밀번호가 변경되었습니다');
						utils.popupFormClose();
					} else {
						utils.alert('비밀번호변경 실패');
						utils.popupFormClose();
					}
				}
			});
		}

		const handleClickChangeName = function () {
			initHelp2();
			const currentName = document.querySelector("#currentName").innerText.trim();
			const changedName = document.querySelector("#name").value.trim();
			const currentUuid = document.querySelector("#currentUuid").innerText.trim();

			if (changedName == "") {
				createHelp2(0, " - 내용을 입력해주세요");
				return;
			}
			if (changedName.length < 2) {
				createHelp2(0, " - 최소 2자리 이상 입력해주세요");
				return;
			}
			if (changedName == currentName) {
				createHelp2(0, " - 현재 이름과 동일합니다");
				return;
			}

			const btnStr = document.querySelector(".modal_button span");
			const param = {
				"uuid": currentUuid,
				"name": changedName
			};

			$.ajax({
				url: contextPath + "/changeName",
				method: "post",
				type: "json",
				data: param,
				success: function (data) {
					if (data.result == "1") {
						utils.alert('이름이 변경되었습니다');
						utils.popupFormClose();
						document.querySelector("#currentName").innerText = changedName;
						document.querySelector(".account_name").innerText = changedName;
					}
				}
			});

		};		
		
		const changeName = document.querySelector("#changeName");
		changeName.addEventListener("click", function () {
			const title = "이름 변경";
			let form = "";
			form += "<div class='modal_form_cols'><span>새로운 이름</span><input type='text' id='name' onkeydown='javascript:if(event.keyCode==13)handleClickChangeName();'/></div>";
			const button = "<button onclick='handleClickChangeName();'>저장</button>";
			utils.popupForm(title, form, button);
		});
		
		const changePasswd = document.querySelector("#changePasswd");
		changePasswd.addEventListener("click", function () {
			const title = "비밀번호 변경";
			let form = "";
			form += "<div class='modal_form_cols'><span>현재 비밀번호</span> <input type='password' id='currentPasswdCheck' onkeydown='javascript:if(event.keyCode==13)handleClickChangePasswd();'/></div>";
			form += "<div class='modal_form_cols'><span>새로운 비밀번호</span> <input type='password' id='newPasswd' onkeydown='javascript:if(event.keyCode==13)handleClickChangePasswd();'/></div>";
			form += "<div class='modal_form_cols'><span>비밀번호 확인</span> <input type='password' id='newPasswdCheck' onkeydown='javascript:if(event.keyCode==13)handleClickChangePasswd();'/></div>";
			const button = "<button onclick='handleClickChangePasswd();'>저장</button>";
			utils.popupForm(title, form, button);
		});    
    	
    	const profile = document.querySelector("#profile");
    	profile.addEventListener("change", function(e){
    		let file = e.target.files[0];
    		let formData = new FormData();
    		formData.append("file", file);
    		formData.append("uuid", document.querySelector("#currentUuid").innerText.trim());
    		
			$.ajax({
				url: contextPath + "/upload/uploadAjax",
				method: "post",
				type: "json",
				data: formData,
				processData: false,
				contentType: false,
				success: function (data) {
					if(data == "1"){
						utils.alert('프로필 사진이 변경되었습니다', window.location.href);
					}
				}
			});
    	});
    	
    	const delProfilePhoto = document.querySelector("#delProfilePhoto");
    	delProfilePhoto.addEventListener("click", function(){
    		const uuid = document.querySelector("#currentUuid").innerText.trim();
    		
    		const param = {
    				"uuid": uuid
    		};
			$.ajax({
				url: contextPath + "/upload/deleteAjax",
				method: "post",
				type: "json",
				data: param,
				success: function (data) {
					if(data.result == "1"){
						utils.alert('프로필 사진을 삭제했습니다', window.location.href);
					}
				}
			});
    	});
    	
    	
    	const handleClickMemberDelete = function (){
    		initHelp2();
    		const uuid = document.querySelector("#currentUuid").innerText.trim();
    		const deleteMemberPasswd = document.querySelector("#deleteMemberPasswd").value.trim();
    		
    		if(deleteMemberPasswd == ""){
				createHelp2(0, " - 내용을 입력해주세요");
				return;
    		}
    		
			const param = {
					"uuid": uuid,
					"passwd": deleteMemberPasswd
				};
			
			$.ajax({
				url: contextPath + "/memberDelete",
				method: "post",
				type: "json",
				data: param,
				success: function (data) {
					console.log(data);
					if (data.result == "1") {
						utils.popupFormClose();
						utils.alert('삭제되었습니다', contextPath+'/logout');
					}else if(data.result == "2"){
						createHelp2(0, " - 비밀번호가 일치하지 않습니다");
						return;
					}
				}
			});
    	}
    	
    	const memberDelete = document.querySelector("#memberDelete");
    	memberDelete.addEventListener("click", function(){
			const title = "계정 삭제";
			let form = "";
			form += "<div class='modal_form_cols'><span>비밀번호</span> <input type='password' id='deleteMemberPasswd' onkeydown='javascript:if(event.keyCode==13)handleClickMemberDelete();'/></div>";
			const button = "<button onclick='handleClickMemberDelete();'>삭제</button>";
			
			utils.popupForm(title, form, button);
    	});
    	
    </script>
  </body>
</html>
