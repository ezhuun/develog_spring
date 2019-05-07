package com.project.develog.controller;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.project.develog.MediaUtils;
import com.project.develog.UploadFileUtils;
import com.project.develog.dto.MemberDTO;
import com.project.develog.service.MemberService;

@Controller
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	MemberService service;
	
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	public MemberController() {
		System.out.println("==============    MEMBER [Controller]    ==============");
	}
	
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		//쿠키 및 세션 초기화
		Object obj = session.getAttribute("member");
		
		if(obj != null) {
			MemberDTO dto = (MemberDTO)obj;
			session.removeAttribute("member");
			session.invalidate();
			
			Cookie cookieLogin = WebUtils.getCookie(request, "cookieLogin");
			if(cookieLogin != null) {
				cookieLogin.setPath("/");
				cookieLogin.setMaxAge(0);
				response.addCookie(cookieLogin);
				service.keepLogin(dto.getUuid(), "none", new Date());
			}
		}
		
		mv.setViewName("redirect:/login");
		
		return mv;
	}
	
	
	@RequestMapping({"/", "/login"})
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/login");
		
		return mv;
	}
	
	
	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/register");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/loginCheck", method=RequestMethod.POST)
	public Map<String, Object> loginCheck(
			@RequestParam("email") String email,
			@RequestParam("passwd") String passwd,
			@RequestParam(value="isLogin", required=false, defaultValue="n") String isLogin,
			HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session
			){
		Map<String, Object> map = service.loginCheck(email, passwd);

		//init
		if ( session.getAttribute("member") != null ){
			session.removeAttribute("member");
		}
		
		if(map != null) {
			String passwdcheck = String.valueOf(map.get("passwdcheck"));
			String auth_status = String.valueOf(map.get("auth_status"));
			String uuid = String.valueOf(map.get("uuid"));
			
			if(
					(!passwdcheck.equals("0"))
					 &&
					//메일인증구현 완료시 아래 체크 1 => 0로 변경
					(!auth_status.equals("0"))
			) {
				//로그인 성공
				
				//마지막 로그인일자 저장
				service.lastLoginUpdate(uuid);
				
				//세션 유지 기간 설정
				int amount = (60 * 60 * 24) * 3;
				//amount = 10;
				
				
				//유저 uuid 세션 기록
				session.setMaxInactiveInterval(amount);
				session.setAttribute("member", service.loadMemberInfo(uuid));
				

				//자동로그인 체크시 쿠키 데이터 저장
				if(isLogin.equals("y")) {

					Cookie cookie = new Cookie("cookieLogin", session.getId());
					cookie.setMaxAge(amount);
					cookie.setPath("/");
					response.addCookie(cookie);
					
					Date session_limit = new Date(System.currentTimeMillis() + (1000*amount));
					service.keepLogin(uuid, session.getId(), session_limit);
				}
			}
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/registerCheck", method=RequestMethod.POST)
	public Map<String, Object> registerCheck(
			@RequestParam("email") String email,
			@RequestParam("name") String name,
			@RequestParam("passwd") String passwd,
			HttpServletRequest request
			) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("email", email);
		paramMap.put("name", name);
		paramMap.put("passwd", passwd);
		
		Map<String, Object> map = service.insertMember(paramMap, request.getContextPath());
		
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/reSendAuthEmail", method=RequestMethod.POST)
	public Map<String, Object> reSendAuthEmail(@RequestParam("email") String email, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		//email 이용해서 auth_key name 확인하여 mail보낸다
		service.reSendAuthEmail(email, request.getContextPath());
		
		return map;
	}
	
	@RequestMapping(value="/authEmail", method=RequestMethod.GET)
	public ModelAndView authEmail(
			@RequestParam("email") String email,
			@RequestParam("auth_key") String auth_key
			) {
		ModelAndView mv = new ModelAndView();
		
		//인증처리 완료후 확인처리url로 이동
		boolean flag = service.authEmailUpdate(email, auth_key);
		
		String url = "";
		if(flag) {
			url = "auth/authSuccess";
		}else {
			url = "auth/authFail";
		}
		
		mv.setViewName(url);
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/passwdEdit", method=RequestMethod.POST)
	public Map<String, Object> passwdEdit(@RequestParam("email") String email, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map = service.loginCheck(email, "");
		if(map != null) {
			service.sendPasswdEditEmail(email, request.getContextPath());
			
			//굳이 회원정보를 리턴할 이유가 없으므로 인증확인 됬는지만 리턴한다
			map.clear();
			map.put("result", "1");
		}
		
		return map;
	}
	
	@RequestMapping(value="/reRegisterPassword", method=RequestMethod.GET)
	public ModelAndView reRegisterPassword(@RequestParam("uuid") String uuid) {
		return new ModelAndView("auth/reRegisterPasswd");
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value="/rePasswdCheck", method=RequestMethod.POST)
	public Map<String, Object> rePasswdCheck(
			@RequestParam("uuid") String uuid,
			@RequestParam("passwd") String passwd
			){
		Map<String, Object> map = new HashMap<String, Object>();
		
		boolean flag = service.rePasswd(uuid, passwd);
		
		if(flag) {
			map.put("result", "1");
		}
		
		return map;
	}
	
	@RequestMapping(value="/account/setting")
	public ModelAndView accountSetting(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("auth/accountSetting");
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/changeName", method=RequestMethod.POST)
	public Map<String, Object> changeName(
			@RequestParam("uuid") String uuid,
			@RequestParam("name") String name,
			HttpSession session
			){
		Map<String, Object> map = new HashMap<String, Object>();
		
		boolean flag = service.changeName(uuid, name);
		
		if(flag) {
			MemberDTO dto = (MemberDTO)session.getAttribute("member");
			dto.setName(name);
			session.setAttribute("member", dto);
			map.put("result", "1");
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/changePasswd", method=RequestMethod.POST)
	public Map<String, Object> changePasswd(
			@RequestParam("uuid") String uuid,
			@RequestParam("currentPasswd") String currentPasswd,
			@RequestParam("newPasswd") String newPasswd,
			HttpSession session
			){
		Map<String, Object> map = service.changePasswd(uuid, currentPasswd, newPasswd);
		
		String result = (String)map.get("result");
		if(result == "1") {
			MemberDTO dto = (MemberDTO)session.getAttribute("member");
			dto.setPasswd(newPasswd);
			session.setAttribute("member", dto);
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/upload/uploadAjax", method=RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public String uploadAjax(MultipartFile file, 
			@RequestParam String uuid, 
			HttpSession session) throws Exception{
		String result = "0";
		
		logger.info("originalName: " + file.getOriginalFilename());
		logger.info("size: " + file.getSize());
		logger.info("contentType: " + file.getContentType());
		
		MemberDTO dto = (MemberDTO)session.getAttribute("member");
		//이미지 변경시 기존파일을 삭제
		if(dto.getUpdir_original() != null && dto.getUpdir_thumnail() != null) {
			String[] files = {dto.getUpdir_original(), dto.getUpdir_thumnail()};
			
			for (String fileName : files) {
				String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
				MediaType mType = MediaUtils.getMediaType(formatName);
				
				if(mType != null) {
					String front = fileName.substring(0, 12);
					String end = fileName.substring(14);
					new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
				}
				
				new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
			}
		}
		
		String upDirThumnail = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		String upDirOriginal = null;
		
		if(upDirThumnail != null) {
			String dir = upDirThumnail.substring(0, 12);
			String oriFileName = upDirThumnail.substring(14, upDirThumnail.length());
			upDirOriginal = dir+oriFileName;
				
			boolean flag = service.photoUpdate(uuid, upDirOriginal, upDirThumnail);
			if(flag) {
				result = "1";
				
				dto.setUpdir_original(upDirOriginal);
				dto.setUpdir_thumnail(upDirThumnail);
				session.setAttribute("member", dto);
			}
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value="/upload/deleteAjax", method=RequestMethod.POST)
	public Map<String, Object> deleteAjax(
			@RequestParam("uuid") String uuid,
			HttpSession session
			){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "0");
		
		MemberDTO dto = (MemberDTO)session.getAttribute("member");
		
		logger.info("delete file1: " + dto.getUpdir_original());
		logger.info("delete file2: " + dto.getUpdir_thumnail());
		
		String[] files = {dto.getUpdir_original(), dto.getUpdir_thumnail()};
		
		for (String fileName : files) {
			String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			if(mType != null) {
				String front = fileName.substring(0, 12);
				String end = fileName.substring(14);
				new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
			}
			
			new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
		}
		
		
		boolean flag = service.photoDelete(uuid);
		if(flag) {
			map.put("result", "1");
			
			dto.setUpdir_original(null);
			dto.setUpdir_thumnail(null);
			session.setAttribute("member", dto);
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value="/memberDelete", method=RequestMethod.POST)
	public Map<String, Object> memberDelete(
			@RequestParam("uuid") String uuid,
			@RequestParam("passwd") String passwd,
			HttpSession session
			){
		Map<String, Object> map = new HashMap<String, Object>();
		
		String result = service.memberDelete(uuid, passwd);
		
		//성공적으로 삭제되었다면
		if(result == "1") {
			//저장되었던 파일이 있는지 확인한 후 삭제한다
			MemberDTO dto = (MemberDTO)session.getAttribute("member");
			if(dto.getUpdir_original() != null && dto.getUpdir_thumnail() != null) {
				String[] files = {dto.getUpdir_original(), dto.getUpdir_thumnail()};
				
				for (String fileName : files) {
					String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
					MediaType mType = MediaUtils.getMediaType(formatName);
					
					if(mType != null) {
						String front = fileName.substring(0, 12);
						String end = fileName.substring(14);
						new File(uploadPath + (front+end).replace('/', File.separatorChar)).delete();
					}
					new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();
				}
			}
		}
		
		map.put("result", result);
		
		
		return map;
	}
}
