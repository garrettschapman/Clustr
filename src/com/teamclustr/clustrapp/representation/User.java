package com.teamclustr.clustrapp.representation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.teamclustr.clustrapp.communication.Post;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr (Jirawat is in charge of this task)
 * @version 1.0
 * File: User.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class User implements Serializable {
	// MEMEBR DATA.
	private String Username;
	private String Password;
	private String Email;
	private String PhoneNum; 
	private String Bio; 
	private Post post;
	private ArrayList<User> Friends;
	private ArrayList<User> Enemies;
	private ArrayList<Group> GroupList; 
	public ArrayList<Post> Posts; 
	private ArrayList<Post> Comments;
	
	
	//Constructor 
	public User(String Username,
                String Password, String Email, String PhoneNum, String Bio) {
		
		createAccount(Username, Password, Email, PhoneNum, Bio);
                
                /*
                TODO: WHY WERE THESE THREE METHODS CALLED HERE?
                */ /*Jirawat- I thought I had to call it so that the- 
					system class can use it, but I am probably wrong so thank you 
					for correcting me  */
		
		//addFriend(Username);
		//addEnemies(Username); 
		//removeFriend(Username);   

		Friends = new ArrayList<User>();
		Enemies = new ArrayList<User>();
		GroupList = new ArrayList<Group>();
		Posts = new ArrayList<Post>();
		Comments = new ArrayList<Post>();
	
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
	
	public void addFriend(User Username) {  //add friend to friend list
		this.Friends.add(Username);	
	}
	
	public void addEnemies(User Username) { //add blocked user to enemy list
		this.Enemies.add(Username);
	}

	public void removeFriend(User Username) {
		this.Friends.remove(Username);
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
	
	public Post getPosts(){
		return this.post;
	}

	
}
