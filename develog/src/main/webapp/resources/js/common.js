//===================================================================================
//left menu toggle
//-----------------------------------------------------------------------------------
const TOGGLE_LEFT_BUTTON = document.querySelector(".header-toggle span");
const LEFT_AREA = document.querySelector(".wrap__left");
TOGGLE_LEFT_BUTTON.addEventListener("click", function() {
  toggleMenu(LEFT_AREA.clientWidth);
});
const toggleMenu = function(leftWidth) {
  if (leftWidth > 0) {
      toggleMenuClose();
  } else {
      toggleMenuOpen();
  }
}
const toggleMenuClose = function() {
  LEFT_AREA.style.width = "0";
  LEFT_AREA.style.minWidth = "0";
  TOGGLE_LEFT_BUTTON.style.transform = "rotate3d(0, 1, 0, 180deg)";
}
const toggleMenuOpen = function() {
  LEFT_AREA.style.width = "13.12rem";
  LEFT_AREA.style.minWidth = "13.12rem";
  TOGGLE_LEFT_BUTTON.style.transform = "rotate3d(0, 0, 0, 0)";
}

//===================================================================================
//account menu toggle
//-----------------------------------------------------------------------------------
const TOGGLE_ACCOUNT_BUTTON = document.querySelector(".header-account span");
const ACCOUNT_AREA = document.querySelector(".account_menu");
TOGGLE_ACCOUNT_BUTTON.addEventListener("click", function() {
  if (ACCOUNT_AREA.style.visibility == "visible") {
      toggleAccountClose();
  } else {
      toggleAccountOpen();
  }
});
document.addEventListener("click", function(e) {
  const accountContainer = [e.target.className, e.target.parentNode.className];
  const valid = accountContainer.includes('account_menu') || accountContainer.includes('fas fa-sort-down');
  if (!valid) {
      if (ACCOUNT_AREA.style.visibility == "visible") {
          toggleAccountClose();
      }
  }
});
const toggleAccountOpen = function() {
  ACCOUNT_AREA.style.visibility = "visible";
}
const toggleAccountClose = function() {
  ACCOUNT_AREA.style.visibility = "hidden";
}
