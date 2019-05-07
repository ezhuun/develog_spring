package com.project.develog;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.project.develog.dto.MemberDTO;
import com.project.develog.service.MemberService;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Inject
	MemberService service;

	
	
	public AuthenticationInterceptor() {
		System.out.println("===================   인터럽트실행[AuthEntication]    ===================");
	}

	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("member");
		if ( obj == null ){
			Cookie cookieLogin = WebUtils.getCookie(request, "cookieLogin");
			if(cookieLogin != null) {
				String session_key = cookieLogin.getValue();
				MemberDTO dto = service.checkMemberWithSessionKey(session_key);

				if(dto != null) {
					session.setAttribute("member", dto);
					return true;
				}
			}
			
			// 아래는 로그인도 안되있고 쿠키도 존재하지 않는 경우니까 다시 로그인 폼으로 돌려보내면 된다.
			// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
			response.sendRedirect(request.getContextPath()+"/login");
			return false;
		}
		
		// preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
		// 따라서 true로하면 컨트롤러 uri로 가게 됨.
		return true;
	}
	
	// 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	
	
	
}
