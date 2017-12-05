package com.teamclustr.clustrapp.representation;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.teamclustr.clustrapp.communication.Message;
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
	private ArrayList<User> Friends;
	private ArrayList<Group> GroupList; 
	private ArrayList<Post> Posts; 
	private ArrayList<Post> Comments;
	private ArrayList<User> BlockUsers; //blocked users
	
	//Constructor 
	public User(String Username,
                String Password, String Email, String PhoneNum, String Bio) {
		
		createAccount(Username, Password, Email, PhoneNum, Bio);  

		Friends = new ArrayList<User>();
		GroupList = new ArrayList<Group>();
		Posts = new ArrayList<Post>();
		Comments = new ArrayList<Post>();
		BlockUsers = new ArrayList<User>();
	
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
	
	public void addPost(Post post) { //add post to post list
		this.Posts.add(post);
	}
	
	public void addFriend(User Username) {  //add friend to friend list
		this.Friends.add(Username);	
	}
	
	public void removeFriend(User Username) { //delete friend
		this.Friends.remove(Username);
	}
	
	public void addBlockUser(User Username) { //add blocked user to block user list
		this.BlockUsers.add(Username);
	}
	
	public void unBlockUser(User Username) {  //unblock the blocked user
		this.BlockUsers.remove(Username);
	}
	
	public Post getPostByTitle(String postName) {
		for (Post post : this.Posts) {
			if (post.getTitle().equals(postName)) {
				return post;
			}
		}
		return null;
	}
	
	/*
	 * Method to write a message to a user
	 * Checks if the author is blocked by the recipient
	 */
	public Message writeMessage(User recipient, String body, LocalDateTime date) {
		Boolean isBlocked = false;
		ArrayList<User> blockedUsers = recipient.getBlockedUsers();
		Message newMessage;
		
		//for loop to determine if the author is blocked
		for(int i = 0; i < blockedUsers.size(); i++) {
			if(this.equals(blockedUsers.get(i))) {
				isBlocked = true; //user is blocked
				break;
			}
		} //end of for loop
		
		newMessage = new Message(this, recipient, body, date, isBlocked);
		
		return newMessage;
	} //end of method writeMessage
	
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
	
	public ArrayList<Post> getPosts(){
		return this.Posts;
	}
	
	public ArrayList<User> getBlockedUsers(){
		return this.BlockUsers;
	} //end of method getBlockedUsers
}
