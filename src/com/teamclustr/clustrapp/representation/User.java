package com.teamclustr.clustrapp.representation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: User.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class User implements Serializable {
	//Its jirawat sfdgdfadfa dsafsdfsd
	// MEMEBR DATA.
	private String Username;
	private String Password;
	private String Email;
	private String PhoneNum; 
	private String Bio; // bio of user
	private ArrayList<String> Friends = new ArrayList<String>();
	private ArrayList<String> Enemies = new ArrayList<String>();
	private ArrayList<String> Group = new ArrayList<String>(); //group list
	//Constructor 
	public User() {
		
		createAccount(Username, Password, Email, PhoneNum, Bio);
	
	}
	
	// MEMBER METHODS.
	
	public void createAccount(String Username, String Password, String Email, 
			String PhoneNum, String Bio) {  
		this.setUsername(Username);
		this.setPassword(Password);
		this.setEmail(Email);
		this.setPhoneNum(PhoneNum);
		this.setBio(Bio);
	}
	
	public void addFriend(String Username) {  //add friend to friend list
		//this.Friends.add(Username);
		this.Friends.add(Username);	
	}
	
	public void addEnemies(String Username) { //add blocked user to enemy list
		this.Enemies.add(Username);
	}


	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}
	
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}

	public String getBio() {
		return Bio;
	}

	public void setBio(String bio) {
		Bio = bio;
	}

	
}
