package com.proejct.develog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.develog.dto.MemberDTO;
import com.project.develog.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/root-context.xml", 
									"file:src/main/webapp/WEB-INF/spring/**/spring-security.xml" })
public class MemberServiceTest {
	
	@Inject
	private MemberService service;
	
	//@Test
	public void testInsertMember() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", "ljhadf3@naver.com");
		map.put("name", "신사임당");
		map.put("passwd", "1234");
		String root = "http://localhost:8080/develog";
		
		Map<String, Object> resultMap = service.insertMember(map, root);
		
		System.out.println(resultMap);
	}
	
	//@Test
	public void testRegisterCheck() {
		String email = "ljhadf@naver.com";
		boolean flag = service.registerCheck(email);
		
		if(flag) {
			System.out.println("이미 존재하는 이메일입니다");
		}else {
			System.out.println("생성가능한 이메일입니다");
		}
	}
	
	//@Test
	public void testLoginCheck() {
		String email = "ljhadf@naver.com";
		String passwd = "1234";
		
		Map<String, Object> map = service.loginCheck(email, passwd);
		
		System.out.println(map);
	}
	
	//@Test
	public void testKeepLogin() {
		String uuid = "e311f0c75212468d90d6fa84edef68c2";
		String session_key = "sadfasdf";
	    Calendar cal = new GregorianCalendar();
	    cal.add(Calendar.DATE, 1);
		Date session_limit = cal.getTime();
		
		boolean flag = service.keepLogin(uuid, session_key, session_limit);
		
		if(flag) {
			System.out.println("로그인 세션 유지 업데이트 성공");
		}else {
			System.out.println("로그인 세션 유지 업데이트 실패");
		}
		
	}
	
	//@Test
	public void testCheckMemberWithSessionKey() {
		String session_key = "sadfasdf";
		
		MemberDTO dto = service.checkMemberWithSessionKey(session_key);
		
		if(dto != null) {
			System.out.println("세션정보가 일치하는 데이터가 존재합니다");
			System.out.println(dto);
		}else {
			System.out.println("세션정보에 부합하는 데이터가 없습니다");
		}
	}
	
	//@Test
	public void testLastLoginUpdate() {
		String uuid = "e311f0c75212468d90d6fa84edef68c2";
		boolean flag = service.lastLoginUpdate(uuid);
		
		if(flag) {
			System.out.println("최종로그인정보 업데이트 완료");
		}else {
			System.out.println("최종로그인정보 업데이트 실패");
		}
	}
	
	//@Test
	public void reSendAuthEmail() {
		String email = "ljhadf@naver.com";
		String root = "http://localhost:8080/develog";
		service.reSendAuthEmail(email, root);
	}
	
	//@Test
	public void authEmailUpdate() {
		String email = "ljhadf3@naver.com";
		String auth_key = "09489617cfab44e1825a354129f6028b";
		
		boolean flag = service.authEmailUpdate(email, auth_key);
		
		if(flag) {
			System.out.println("메일인증상태 업데이트 성공");
		}else {
			System.out.println("메일인증상태 업데이트 실패");
		}
	}
	
	@Test
	public void sendPasswdEditEmail() {
		String email = "ljhadf@naver.com";
		String root = "http://localhost:8080/develog";
		
		service.sendPasswdEditEmail(email, root);
	}
	
	//@Test
	public void rePasswd() {
		String uuid = "e311f0c75212468d90d6fa84edef68c2";
		String passwd = "1111";
		
		boolean flag = service.rePasswd(uuid, passwd);
		if(flag) {
			System.out.println("비밀번호 재설정 완료");
		}else {
			System.out.println("비밀번호 재설정 실패");
		}
	}
	
	
	
	//service 내부에서 작동하는 메일 관련 메소드
	//mailSendPasswd() {}
	//mailSendAuth() {}
}
