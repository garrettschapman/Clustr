package com.teamclustr.clustrapp;

import com.teamclustr.clustrapp.communication.Post;
import com.teamclustr.clustrapp.representation.User;
import com.teamclustr.clustrapp.representation.Group;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * BRIEF CLASS DESCRIPTION.
 *
 * @author Team Clustr
 * @version 1.0 File: Server.java Created: 10/17/2017 Copyright (c) 2017, Team
 * Clustr, All rights reserved. Summary of Modifications: N/A
 */
@SuppressWarnings("serial")
public class Server implements Serializable {

    // MEMEBR DATA.
    // The user active on this current session.
    private transient User activeUser;

    // All users.
    private ArrayList<User> users;

    // All groups.
    private ArrayList<Group> groups;
    private ArrayList<Group> searchedGroups;
    
    // The active group and post being interacted with at a certain time
    private Group activeGroup;
    private Post activePost;
    
    //flag for search in progress
    private boolean	search = false;

    // MEMBER METHODS.
    /**
     * Construct a System.
     */
    public Server() {

        // Initialize fields.
        activeUser = null; // who is logged into the system
        users = new ArrayList<User>(16);
        groups = new ArrayList<Group>(16);
        searchedGroups = new ArrayList<Group>(16);
    }

    //searches overall group array, copies matching values into a visible arrayList
    public ArrayList<Group> searchedGroups(String s){
    	for(Group group : groups){
    		//ArrayList<String> cat = group.getCatList();
    		String name = group.getName();
    		s = s.toLowerCase();
    		name = name.toLowerCase();
    		if(name.equals(s)){
    			//this.searchedGroups.clear();
    			this.searchedGroups.add(group);
    			this.search = true;
    		}else if(s.equals("")){
    			this.searchedGroups.clear();
    			this.search = false;
    		}
    	}
        return this.searchedGroups;
    }
    

    public Group getActiveGroup() throws NullPointerException{
        if(this.activeGroup != null){
            return this.activeGroup;
        } else {
            throw new NullPointerException("No Active Group");
        }
    }
    
    public void setActiveGroup(Group grp){
        this.activeGroup = grp;
    }
    
    public Post getActivePost() throws NullPointerException{
        if(this.activePost != null){
            return this.activePost;
        } else {
            throw new NullPointerException("No Active Post");
        }
    }
    
    public void setActivePost(Post pst){
        this.activePost = pst;
    }
    
    /**
     * Get the active session user.
     *
     * @return session user
     */
    public User getActiveUser() {
        return activeUser;
    }

    public ArrayList<Group> getGroupList() {
    	if(search){
    		return searchedGroups;
    	}else{
    		return groups;
    	}
        
    }
    
    public void setGroupList(ArrayList<Group> groupList) {
    	this.groups = groupList;
    	
    	for(Group group : this.groups) {
    		group.updateMembers(this.users);
    	}
    }

    public ArrayList<User> getUserList() {
        return this.users;
    }
    
    public void setUserList(ArrayList<User> userList) {
    	this.users = userList;
    }

    public Group createGroup(String name, String categories, String tags) {
        Group group = new Group(this.activeUser, name, categories, tags);
        this.groups.add(group);
        this.setActiveGroup(group);
        
        return group;
    }
    
    public User createUser(String username, String password) {
    
	    // Create user.
	    User newUser = new User(username, password, "example@example.com", "1234567890", "This is a bio.", 0, "Male", "Caucasian", false, "Accounting", "Freshman", "SoVi");
	    
	    // Add user to server.
	    this.users.add(newUser);
	    
	    return newUser;
    }

    public Group getGroupByName(String name) throws NullPointerException {
        for (Group group : this.groups) {
            if (group.getName().equals(name)) {
                return group;
            }
        }
        throw new NullPointerException("Group Does Not Exist!");
    }

    public boolean groupExists(String text) {
        for (Group group : this.groups) {
            if (group.getName().equals(text)) {
                return true;
            }
        }
        return false;
    }
    
    public User getUserFromUsername(String username) {
	    
		// Search for user.
		for (User curUser : users) {

			// Check if username matches.
			if (curUser.getUsername().equals(username)) {

				return curUser;
			}
		}
		
		// User not found.
		return null;
    }
    
    public void setSessionUser(User newSessionUser) {
	    
	    this.activeUser = newSessionUser;
    }
}
