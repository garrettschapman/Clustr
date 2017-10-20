package com.teamclustr.clustrapp.representation;
import java.io.Serializable;
import com.teamclustr.clustrapp.communication.Post;
import java.util.ArrayList;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: Group.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class Group implements Serializable {
	
//variables for group
//test for push
	private ArrayList<User> member = new ArrayList<User>();
	private ArrayList<User> moderators = new ArrayList<User>();
	private ArrayList<User> bannedUsers = new ArrayList<User>();
	private ArrayList<Post> posts = new ArrayList<Post>();
	private ArrayList<String> categories = new ArrayList<String>();
	private ArrayList<String> tags = new ArrayList<String>();
	private String groupName = "";
//end variables
	
	public Group(User owner, String name /*icon*/){
		groupName = name;
		member.add(owner);
		moderators.add(owner);
		
	}

	public boolean addMember (User user){
		if(bannedUsers.contains(user)){
			return false;
		}
		else{
			member.add(user);
			return true;
		}
	}
	
	public void leaveGroup(User user){
		member.remove(user);
	}
	
	public void banUser(User user){
		member.remove(user);
		bannedUsers.add(user);
	}
	
	public void changeLeader(User owner){
		if(moderators.contains(owner)){
			moderators.remove(owner);
			
			//gui should insert a 
		}
	}
	
}
