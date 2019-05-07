package com.project.develog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AutoLoginInterceptor extends HandlerInterceptorAdapter {
	
	

	public AutoLoginInterceptor() {
		System.out.println("==============  인터셉터실행[AutoLogin] ======================");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute("member") != null) {
			response.sendRedirect(request.getContextPath() + "/projects/list");
			return false;
		}
		
		return true;
		
	}

}
