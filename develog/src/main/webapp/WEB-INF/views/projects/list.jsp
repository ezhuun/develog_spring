<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>develog</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css" />
    <style type="text/css">
      /* proejct */
	.projectBox {
		-ms-user-select: none;
		-moz-user-select: -moz-none;
		-khtml-user-select: none;
		-webkit-user-select: none;
		user-select: none;
		width: 100%;
		padding-left: 1rem;
		box-sizing: border-box;
		padding-bottom: 20px;
	}
	
	.projects,
	.new-projects {
		display: inline-flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 24rem;
		height: 13.75rem;
		margin-right: 1rem;
		margin-bottom: 1rem;
		border-radius: 4px;
		cursor: default;
	}
	
	.projects {
		border: 1px solid #CECECE;
	}
	
	.projects.active {
		border: 1px solid #18a0fb;
	}
	
	.new-projects {
		border: 1px dashed #88CCFA;
		color: #88CCFA;
		font-weight: bold;
	}
	
	.new-projects:hover {
		border: 1px dashed #18A0FB;
		color: #18A0FB;
	}
	
	@media (max-width: 970px) {
		.projects,
		.new-projects {
			width: 95%;
		}
	}
	
	.projects-title {
		font-size: 1.25rem;
		font-weight: bold;
		color: #999999;
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
              <li class="active" onclick="location.href='${pageContext.request.contextPath}/projects/list';">
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
            <span class="title-describe">Projects</span>
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
        
        
            <div class="projectBox">
              <div class="projects" data_id="0">
                <div class="projects-title">
                  Proejct1
                </div>
              </div>
              <div class="projects" data_id="1">
                <div class="projects-title">
                  Proejct2
                </div>
              </div>
              <div class="projects" data_id="2">
                <div class="projects-title">
                  Proejct3
                </div>
              </div>
              <div class="projects" data_id="3">
                <div class="projects-title">
                  Proejct4
                </div>
              </div>
              <div class="projects" data_id="4">
                <div class="projects-title">
                  Proejct5
                </div>
              </div>
              <div id="createProejct" class="new-projects">+ New Project</div>
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
    //new project
    const createProejct = document.querySelector("#createProejct");
    createProejct.addEventListener("click", function() {
        utils.alert('open create proejct form');
    });

    //onClick line paint&remove
    const projectBox = document.querySelector(".projectBox");
    projectBox.addEventListener("click", function(e) {
        const projects = projectBox.children;
        //onClick line remove
        for (let i = 0; i < projects.length; i++) {
            if (projects[i].classList.contains('projects')) {
                if (projects[i].classList.contains('active')) {
                    projects[i].classList.remove('active');
                }
            }
        }
        //onClick line painting
        const projectContainer = [e.target, e.target.parentNode];
        const projectClassContainer = [e.target.className, e.target.parentNode.className];
        const indexof = projectClassContainer.indexOf('projects');
        if (indexof > -1) {
            const target = projectContainer[indexof];
            target.classList.add('active');
        }
    });

    
    //read proejct
    const readProject = function(e){
        const projectContainer = [e.target, e.target.parentNode];
        const projectClassContainer = [e.target.className, e.target.parentNode.className];
        const indexof = projectClassContainer.indexOf('projects') && projectClassContainer.indexOf('projects active');
        if (indexof > -1) {
            const target = projectContainer[indexof];
            const data_id = target.attributes.data_id.value;

            utils.alert(data_id);
        }
    }
    
    //web or mobile check click or dblclick event
    if(isMobile.any()){
    	projectBox.addEventListener("click", readProject);
    }else{
    	projectBox.addEventListener("dblclick", readProject);    	
    }
    </script>
  </body>
</html>
