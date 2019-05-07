package com.project.develog.dto;

public class MemberDTO {
	private int m_no;
	private String uuid;
	private String email;
	private String name;
	private String passwd;
	private String auth_key;
	private int auth_status;
	private String regdt;
	private String last_login;
	private String session_key;
	private String session_limit;
	private String updir_original;
	private String updir_thumnail;
	
	public String getUpdir_original() {
		return updir_original;
	}
	public void setUpdir_original(String updir_original) {
		this.updir_original = updir_original;
	}
	public String getUpdir_thumnail() {
		return updir_thumnail;
	}
	public void setUpdir_thumnail(String updir_thumnail) {
		this.updir_thumnail = updir_thumnail;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getSession_limit() {
		return session_limit;
	}
	public void setSession_limit(String session_limit) {
		this.session_limit = session_limit;
	}
	public int getM_no() {
		return m_no;
	}
	public void setM_no(int m_no) {
		this.m_no = m_no;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getAuth_key() {
		return auth_key;
	}
	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}
	public int getAuth_status() {
		return auth_status;
	}
	public void setAuth_status(int auth_status) {
		this.auth_status = auth_status;
	}
	public String getRegdt() {
		return regdt;
	}
	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}
	public String getLast_login() {
		return last_login;
	}
	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	@Override
	public String toString() {
		return "MemberDTO [m_no=" + m_no + ", uuid=" + uuid + ", email=" + email + ", name=" + name + ", passwd="
				+ passwd + ", auth_key=" + auth_key + ", auth_status=" + auth_status + ", regdt=" + regdt
				+ ", last_login=" + last_login + "]";
	}
	
}
