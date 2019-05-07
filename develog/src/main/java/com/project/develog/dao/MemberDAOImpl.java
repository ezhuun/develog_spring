package com.project.develog.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.project.develog.dto.MemberDTO;

@Service
public class MemberDAOImpl implements MemberDAO {
	@Inject
	private SqlSession sqlSession;
	private static final String NAMESPACE = "MemberMapper";
	
	
	public MemberDAOImpl() {
		System.out.println("==============    MEMBER [DAO]    ==============");
	}

	@Override
	public boolean insertMember(MemberDTO dto) {
		boolean flag = false;
		int cnt = sqlSession.insert(NAMESPACE+".insertMember", dto);
		if(cnt > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean registerCheck(String email) {
		boolean flag = false;
		int cnt = sqlSession.selectOne(NAMESPACE+".registerCheck", email);
		if(cnt > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public MemberDTO loginCheck(String email) {
		return sqlSession.selectOne(NAMESPACE+".loginCheck", email);
	}

	@Override
	public boolean keepLogin(String uuid, String session_key, Date session_limit) {
		boolean flag = false;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", uuid);
		map.put("session_key", session_key);
		map.put("session_limit", session_limit);
		
		int cnt = sqlSession.update(NAMESPACE+".keepLogin", map);
		
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public MemberDTO checkMemberWithSessionKey(String session_key) {
		return sqlSession.selectOne(NAMESPACE+".checkMemberWithSessionKey", session_key);
	}

	@Override
	public boolean lastLoginUpdate(String uuid) {
		boolean flag = false;
		
		int cnt = sqlSession.update(NAMESPACE+".lastLoginUpdate", uuid);
		
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean authEmailUpdate(String email, String auth_key) {
		boolean flag = false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("auth_key", auth_key);
		
		int cnt = sqlSession.update(NAMESPACE+".authEmailUpdate", map);
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean rePasswd(String uuid, String passwd) {
		boolean flag = false;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", uuid);
		map.put("passwd", passwd);
		
		int cnt = sqlSession.update(NAMESPACE+".rePasswd", map);
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public MemberDTO loadMemberInfo(String uuid) {
		return sqlSession.selectOne(NAMESPACE+".loadMemberInfo", uuid);
	}

	@Override
	public boolean changeName(String uuid, String name) {
		boolean flag = false;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", uuid);
		map.put("name", name);
		
		int cnt = sqlSession.update(NAMESPACE+".changeName", map);
		
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean photoUpdate(String uuid, String upDirOriginal, String upDirThumnail) {
		boolean flag = false;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uuid", uuid);
		map.put("upDirOriginal", upDirOriginal);
		map.put("upDirThumnail", upDirThumnail);
		
		int cnt = sqlSession.update(NAMESPACE+".photoUpdate", map);
		if(cnt > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean photoDelete(String uuid) {
		boolean flag = false;
		
		int cnt = sqlSession.update(NAMESPACE+".photoDelete", uuid);
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean memberDelete(String uuid) {
		boolean flag = false;
		
		int cnt = sqlSession.update(NAMESPACE+".memberDelete", uuid);
		if(cnt > 0) {
			flag = true;
		}
		
		return flag;
	}
	
	
	
	
}
