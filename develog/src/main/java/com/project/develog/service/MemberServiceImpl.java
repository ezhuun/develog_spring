package com.project.develog.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.develog.MailHandler;
import com.project.develog.dao.MemberDAO;
import com.project.develog.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	MemberDAO dao;
	@Inject
	BCryptPasswordEncoder passwordEncoder;
	@Inject
	private JavaMailSender mailSender;
	
	public MemberServiceImpl() {
		System.out.println("==============    MEMBER [Service]    ==============");
	}

	@Transactional
	@Override
	public Map<String, Object> insertMember(Map<String, Object> paramMap, String root) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		MemberDTO dto = new MemberDTO();
		dto.setEmail((String)paramMap.get("email"));
		dto.setName((String)paramMap.get("name"));
		dto.setPasswd((String)paramMap.get("passwd"));
		
		//우선 registerCheck를 통해 이메일 중복여부를 확인한 후에
		//등록절차가 이어지도록...
		boolean registerChceckFlag = registerCheck(dto.getEmail());
		
		if(registerChceckFlag == false) {
			//이메일 중복검사 통과
			
			//비밀번호 암호화 처리
			if(dto.getPasswd() != null) {
				String encPasswd = passwordEncoder.encode(dto.getPasswd());
				dto.setPasswd(encPasswd);
			}
			
			//UUID 생성
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			dto.setUuid(uuid);
			
			//auth_key 생성
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
			dto.setAuth_key(uuid);
			
			boolean flag = dao.insertMember(dto);
			
			if(!flag) {
				map.put("result", 0);
				map.put("msg", "회원db입력오류");
			}else {
				map.put("result", 1);
				map.put("msg", "회원등록성공");
				
				//메일 보내기
				StringBuffer message = new StringBuffer();
				message.append("DEVELOG에 가입해 주셔서 너무 감사드립니다. <br/>");
				message.append("시작하기 앞서, 저희는 당신이 맞는지 확인해야 합니다.<br/> 이메일 인증 주소버튼을 클릭해주세요:)<br/>");
				mailSendAuth(dto.getEmail(), dto.getName(), dto.getAuth_key(), message.toString(), root);
			}
		}else {
			map.put("result", 2);
			map.put("msg", "회원이메일 중복");
		}
		
		//result=0 insert 실패
		//result=1 insert 성공
		//result=2 이메일 중복
		return map;
	}

	


	@Override
	public boolean registerCheck(String email) {
		return dao.registerCheck(email);
	}

	@Override
	public Map<String, Object> loginCheck(String email, String passwd) {
		Map<String, Object> map = null;
		
		MemberDTO dto = dao.loginCheck(email);
		
		if(dto != null) {
			map = new HashMap<String, Object>();
			map.put("email", dto.getEmail());
			map.put("auth_status", dto.getAuth_status());
			map.put("uuid", dto.getUuid());
			
			int passFlag = 0;
			if(passwordEncoder.matches(passwd, dto.getPasswd())) {
				passFlag = 1;
			}
			map.put("passwdcheck", passFlag);
		}
		
		return map;
		
	}

	@Override
	public boolean keepLogin(String uuid, String session_key, Date session_limit) {
		return dao.keepLogin(uuid, session_key, session_limit);
	}

	@Override
	public MemberDTO checkMemberWithSessionKey(String session_key) {
		return dao.checkMemberWithSessionKey(session_key);
	}

	@Override
	public boolean lastLoginUpdate(String uuid) {
		return dao.lastLoginUpdate(uuid);
	}
	
	@Override
	public void reSendAuthEmail(String email, String root) {
		MemberDTO dto = dao.loginCheck(email);
		
		if(dto != null) {
			StringBuffer message = new StringBuffer();
			message.append("DEVELOG를 이용해주셔서 감사합니다 <br/>");
			message.append("로그인을 하시려면 아래 이메일 인증 주소버튼을 클릭해주세요<br/><br/>");
			mailSendAuth(dto.getEmail(), dto.getName(), dto.getAuth_key(), message.toString(), root);
		}
	}

	@Override
	public boolean authEmailUpdate(String email, String auth_key) {
		return dao.authEmailUpdate(email, auth_key);
	}

	@Override
	public void sendPasswdEditEmail(String email, String root) {
		MemberDTO dto = dao.loginCheck(email);
		
		if(dto != null) {
			StringBuffer message = new StringBuffer();
			message.append("당신의 DEVELOG 비밀번호는 아래 버튼을 통해 재설정이 가능합니다.<br/>");
			message.append("만약, 직접 메일을 요청하지 않았거나 비밀번호 재설정을 원치 않으실 경우 해당 메일은 무시해주세요:)<br/>");
			mailSendPasswd(dto.getEmail(), dto.getName(), dto.getUuid(), message.toString(), root);
		}
	}
	
	@Override
	public boolean rePasswd(String uuid, String passwd) {
		String encPasswd = passwordEncoder.encode(passwd);
		return dao.rePasswd(uuid, encPasswd);
	}



	@Override
	public MemberDTO loadMemberInfo(String uuid) {
		return dao.loadMemberInfo(uuid);
	}

	//비밀번호 변경시 메일전송
	public void mailSendPasswd(String email, 
			String name, 
			String uuid, 
			String message, 
			String root) {
		MailHandler sendMail;
		
		try {
			
			sendMail = new MailHandler(mailSender);
			sendMail.setSubject("[Re Register Password for DEVELOG]");
			StringBuffer string = new StringBuffer();
			
			string.append("<div style='width:100%; padding:5%; box-sizing: border-box;'>");
			string.append("<div style='text-align:center; background-color: #efefef; border-radius:4px'>");
			string.append("<div style='font-weight:bold; font-size:1.8rem; color:#999;'>DEVELOG</div>");
			string.append("<div style='position:relative; box-shadow: 0px 1px 5px rgba(0,0,0,0.1); background-color:#fff; top:5px; padding:5%;'>");
			string.append("<h2>안녕하세요 ");
			string.append(name);
			string.append("님</h2><br/>");
			string.append(message);
			string.append("<br/><br/><a style='text-decoration:none; background-color: #18A0FB; box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25); border-radius: 8px; border:none; color:#fff; font-weight:bold; padding:1rem; cursor:pointer;' href='");
			string.append("http://localhost:8080");
			string.append(root);
			string.append("/reRegisterPassword");
			string.append("?tempKey=");
			string.append(UUID.randomUUID().toString().replaceAll("-", ""));
			string.append("&uuid=");
			string.append(uuid);
			string.append("'>비밀번호 재설정</a><br/><br/>");
			string.append("</div></div></div>");
			
			sendMail.setText(string.toString());
			sendMail.setFrom("develog55", "DEVELOG");
			sendMail.setTo(email);
			sendMail.send();
			
			System.out.println("인증메일을 전송하였습니다");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {}
	}
	
	//회원가입, 로그인 시 메일다시보내기
	public void mailSendAuth(String email, 
			String name, 
			String auth_key,
			String message, 
			String root) {
		MailHandler sendMail;
		
		try {
			
			sendMail = new MailHandler(mailSender);
			sendMail.setSubject("[Auth Email Address for DEVELOG]");
			StringBuffer string = new StringBuffer();
			
			string.append("<div style='width:100%; padding:5%; box-sizing: border-box;'>");
			string.append("<div style='text-align:center; background-color: #efefef; border-radius:4px'>");
			string.append("<div style='font-weight:bold; font-size:1.8rem; color:#999;'>DEVELOG</div>");
			string.append("<div style='position:relative; box-shadow: 0px 1px 5px rgba(0,0,0,0.1); background-color:#fff; top:5px; padding:5%;'>");
			string.append("<h2>안녕하세요 ");
			string.append(name);
			string.append("님</h2><br/>");
			string.append(message);
			string.append("<br/><br/><a style='text-decoration:none; background-color: #18A0FB; box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25); border-radius: 8px; border:none; color:#fff; font-weight:bold; padding:1rem; cursor:pointer;' href='");
			string.append("http://localhost:8080");
			string.append(root);
			string.append("/authEmail?email=");
			string.append(email);
			string.append("&auth_key=");
			string.append(auth_key);
			string.append("'>이메일 인증하기</a><br/><br/>");
			string.append("</div></div></div>");
			
			sendMail.setText(string.toString());
			sendMail.setFrom("develog55", "DEVELOG");
			sendMail.setTo(email);
			sendMail.send();
			
			System.out.println("인증메일을 전송하였습니다");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {}

	}

	@Override
	public boolean changeName(String uuid, String name) {
		return dao.changeName(uuid, name);
	}

	@Override
	public Map<String, Object> changePasswd(String uuid, String currentPasswd, String newPasswd) {
		//uuid와 현재비밀번호가 맞는지 체크
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "0");
		
		MemberDTO dto = dao.loadMemberInfo(uuid);
		if(dto != null) {
			boolean pflag = passwordEncoder.matches(currentPasswd, dto.getPasswd());
			
			if(pflag) {
				boolean flag = dao.rePasswd(uuid, passwordEncoder.encode(newPasswd));
				if(flag) {
					map.put("result", "1");
				}
			}else {
				map.put("result", "2");
			}
		}
		
		//result
		//1 = 변경성공
		//2 = 비밀번호 확인 실패
		return map;
	}

	@Override
	public boolean photoUpdate(String uuid, String upDirOriginal, String upDirThumnail) {
		return dao.photoUpdate(uuid, upDirOriginal, upDirThumnail);
	}

	@Override
	public boolean photoDelete(String uuid) {
		return dao.photoDelete(uuid);
	}
	
	@Override
	public String memberDelete(String uuid, String passwd) {
		String result = "0";
		
		MemberDTO dto = dao.loadMemberInfo(uuid);
		if(dto != null) {
			boolean pflag = passwordEncoder.matches(passwd, dto.getPasswd());
			if(pflag) {
				boolean flag = dao.memberDelete(uuid);
				if(flag) {
					result = "1";
				}
			}else {
				result = "2";
			}
		}
		
		//0 = 삭제실패
		//1 = 삭제성공
		//2 = 비밀번호 일치하지 않음
		return result;
	}

	
	
}
