package com.teamclustr.clustrapp.representation;

import java.io.Serializable;
import java.util.ArrayList;

import com.teamclustr.clustrapp.communication.Post;

/**
 * This User class receives general information of a user and keeps track of the user's 
 * friends list, group list, posts, comments, and blocked users.
 * 
 * @author Team Clustr (Jirawat is in charge of this task)
 * @version 1.0 File: User.java Created: 10/17/2017 Copyright (c) 2017, Team
 *          Clustr, All rights reserved. Summary of Modifications: N/A
 */
@SuppressWarnings("serial")
public class User implements Serializable {
	// MEMEBR DATA.
	private String Username;
	private String Password;
	private String Email;
	private String PhoneNum;
	private String Bio;
	private int Age;
	private String Major;
	private String Year;
	private String Location;
	private String[] Interests = {"","",""};
	private String Ethnicity;
	private String Gender;
	private Boolean MaritalStatus;
	@SuppressWarnings("unused")
	private ArrayList<Group> GroupList;
	private ArrayList<Post> Posts;
	@SuppressWarnings("unused")
	private ArrayList<Post> Comments;

	// Constructor with parameters of user's general information
	public User(String Username, String Password, String Email, String PhoneNum, String Bio, int Age, String Gender, String Ethnicity, Boolean MaritalStatus,
            String Major, String Year, String Location) {

		this.setUsername(Username);
		this.setPassword(Password);
		this.setEmail(Email);
		this.setPhoneNum(PhoneNum);
		this.setBio(Bio);
        this.setAge(Age);
        this.setGender(Gender);
        this.setEthnicity(Ethnicity);
        this.setMaritalStatus(MaritalStatus);
        this.setMajor(Major);
        this.setYear(Year);
        this.setLocation(Location);
        this.Interests[0] = "Drawing";
        this.Interests[1] = "Video Games";
        this.Interests[2] = "Baseball";

		GroupList = new ArrayList<Group>();

	}

	// MEMBER METHODS.

	public void addPost(Post post) { // add post to post list
		this.Posts.add(post);
	}

	public Post getPostByTitle(String postName) { // get the post from title
		for (Post post : this.Posts) {
			if (post.getTitle().equals(postName)) {
				return post;
			}
		}
		return null;
	}

	// Getters and Setters
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
	
	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getMajor() {
		return Major;
	}

	public void setMajor(String major) {
		Major = major;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getInterest(int i) {
		return Interests[i];
	}

	public void setInterest(String interest, int i) {
		Interests[i] = interest;
	}

	public String getEthnicity() {
		return Ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		Ethnicity = ethnicity;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public Boolean getMaritalStatus() {
		return MaritalStatus;
	}

	public void setMaritalStatus(Boolean MaritalStatus) {
		this.MaritalStatus = MaritalStatus;
	}

	public ArrayList<Post> getPosts() {
		return this.Posts;
	}
	// end of Getters and Setters 
}