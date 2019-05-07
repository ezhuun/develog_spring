package com.project.develog.dao;

import java.util.Date;

import com.project.develog.dto.MemberDTO;

public interface MemberDAO {
	public boolean insertMember(MemberDTO dto);
	public boolean registerCheck(String email);
	public MemberDTO loginCheck(String email);
	public boolean keepLogin(String uuid, String session_key, Date session_limit);
	public MemberDTO checkMemberWithSessionKey(String session_key);
	public boolean lastLoginUpdate(String uuid);
	public boolean authEmailUpdate(String email, String auth_key);
	public boolean rePasswd(String uuid, String passwd);
	public MemberDTO loadMemberInfo(String uuid);
	public boolean changeName(String uuid, String name);
	public boolean photoUpdate(String uuid, String upDirOriginal, String upDirThumnail);
	public boolean photoDelete(String uuid);
	public boolean memberDelete(String uuid);
	
}
