package com.project.develog.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.develog.dao.MemberDAO;

@Controller
@RequestMapping("/projects/*")
public class ProjectsController {

	//private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	
	@Inject
	MemberDAO dao;
	
	@RequestMapping("list")
	public ModelAndView list(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/projects/list");
		
		return mv;
	}
}
