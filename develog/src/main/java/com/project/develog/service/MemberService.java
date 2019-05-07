package com.project.develog.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.project.develog.dto.MemberDTO;

public interface MemberService {
	public Map<String, Object> insertMember(Map<String, Object> paramMap, String root);
	public boolean registerCheck(String email);
	public Map<String, Object> loginCheck(String email, String passwd);
	public boolean keepLogin(String uuid, String session_key, Date session_limit);
	public MemberDTO checkMemberWithSessionKey(String session_key);
	public boolean lastLoginUpdate(String uuid);
	public void reSendAuthEmail(String email, String root);
	public boolean authEmailUpdate(String email, String auth_key);
	public void sendPasswdEditEmail(String email, String root);
	public boolean rePasswd(String uuid, String passwd);
	public MemberDTO loadMemberInfo(String uuid);
	
	//메일 전송 service 메소드
	public void mailSendPasswd(String email, String name, String uuid, String message, String root);
	public void mailSendAuth(String email, String name, String auth_key, String message, String root);
	
	//account setting
	public boolean changeName(String uuid, String name);
	public Map<String, Object> changePasswd(String uuid, String currentPasswd, String newPasswd);
	public boolean photoUpdate(String uuid, String upDirOriginal, String upDirThumnail);
	public boolean photoDelete(String uuid);
	public String memberDelete(String uuid, String passwd);
	
}
