package com.huige.cloud.model;


import java.io.Serializable;

/**
 *@Author xiezh
 *@Description 用户
 *@Date 2018/5/15 12:30
 */
public class User implements Serializable {

	private int id;

	private String username;

	private String password;

	private String truename;

	private String email;

	private String phone;

	private int state;

	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public int getState() {
		return state;
	}

	public void setState(int state){
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}