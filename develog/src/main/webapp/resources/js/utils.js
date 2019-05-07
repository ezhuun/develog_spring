//===================================================================================
//get contextPath
//-----------------------------------------------------------------------------------
const contextPath = document.querySelector("#contextPathHolder").attributes["data-contextpath"].value;


//===================================================================================
//popup_modal
//-------------------------------------[use]-----------------------------------------
//utils.alert(str, goUrl, addedBtn);
//utils.alertClose();
//utils.popupForm(title, inForm, btn);
//utils.popupFormClose();
//-----------------------------------------------------------------------------------
const POPUP_LAYOUT = document.querySelector(".__popupLayout");
const POPUP_MODAL = document.querySelector(".popup_container");
POPUP_LAYOUT.addEventListener("click", function(e) {
    e.preventDefault();
    POPUP_MODAL.focus();
});
POPUP_MODAL.addEventListener("keydown", function(e) {
    if (e.code === "Enter" || e.code === "Escape") {
        utils.alertClose();
    }
});

const MODAL_BACKGROUND = document.querySelector(".modal_background");
const MODAL_CONTAINER = document.querySelector(".modal_container");
MODAL_CONTAINER.addEventListener("mousedown", function(e){
	e.stopPropagation();
});
MODAL_BACKGROUND.addEventListener("mousedown", function(e){
	utils.popupFormClose();
});
MODAL_CONTAINER.addEventListener("keyup", function(e){
	if(e.code=="Escape"){
		utils.popupFormClose();
	}
});

const utils = {
    alert: function(str, goUrl, addedBtn) {
        const POPUP_TEXT_AREA = document.querySelector(".popup_txt");
        
        POPUP_TEXT_AREA.innerHTML = str;

        const btnArea = document.querySelector(".__popupBase .added_button");
        if (addedBtn !== undefined && addedBtn != "") {
            btnArea.innerHTML = addedBtn;
        } else {
            btnArea.innerHTML = "";
        }

        const alertConfirm = document.querySelector("#alertConfirm");
        if (goUrl !== undefined && goUrl != "") {
            alertConfirm.onclick = function() {
                utils.alertClose(goUrl);
            }
        } else {
            alertConfirm.onclick = function() {
                utils.alertClose();
            }
        }

        POPUP_LAYOUT.style.display = "block";
        POPUP_MODAL.focus();
    },
    alertClose: function(goUrl) {
        POPUP_LAYOUT.style.display = "none";
        if (goUrl !== undefined && goUrl != "") location.href = goUrl;
    },
    popupForm: function(title, inForm, btn){
    	const modal_title = document.querySelector(".modal_title");
    	const modal_form = document.querySelector(".modal_form");
    	const modal_button = document.querySelector(".modal_button");
    	if (title !== undefined && title != "") {
    		modal_title.innerHTML = title;
    	}
    	if (inForm !== undefined && inForm != "") {
    		modal_form.innerHTML = inForm;
    	}
    	if (btn !== undefined && btn != "") {
    		modal_button.innerHTML = btn;
    	}
    	MODAL_BACKGROUND.style.visibility = "visible";
    	MODAL_CONTAINER.focus();
    },
    popupFormClose: function(){
    	MODAL_BACKGROUND.style.visibility = "hidden";
    }
};


//===================================================================================
//mobile check
//use -> if(isMobile.any()) console.log('Mobile');
//-----------------------------------------------------------------------------------
const isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i);
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i);
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i);
    },
    Opera: function() {
        return navigator.userAgent.match(/Opera Mini/i);
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i) || navigator.userAgent.match(/WPDesktop/i);
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows());
    }
};
