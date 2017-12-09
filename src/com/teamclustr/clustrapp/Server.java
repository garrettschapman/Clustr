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
        
        

        // add some users, groups, and posts
        
        users.add(new User("test", "test", "test@email.com", "5555555555", "BIO"));
        
        User usr1 = new User(
                "TestUser1",
                "TestPassword1",
                "test1@email.com",
                "5555555555",
                "THIS IS A BIO"
        );
        
        Group gp1 = new Group(
                usr1, 
                "First Group", 
                "First, Group", 
                "#First, #Java"
        );
        
        Post pst1 = new Post(
                usr1, 
                "Body Of Post", 
                "TITLE");

        pst1.addComment(usr1, "THIS IS A TRIUMPH", "THE CAKE IS A LIE");
        
        users.add(usr1);
        groups.add(gp1);
        gp1.leavePost(pst1);

        User usr2 = new User(
                "TestUser2",
                "TestPassword2",
                "test2@email.com",
                "5555555555",
                "THIS IS ANOTHER BIO"
        );

        Group gp2 = new Group(
                usr2, 
                "Second Group", 
                "Second, Group",
                "#Second, #Python"
        );
        
        Post pst2 = new Post(
                usr2, 
                "HERE IS SOME TEXT", 
                "THIS IS A TITLE"
        );
        users.add(usr2);
        groups.add(gp2);
        gp2.leavePost(pst2);
        
        Group gp3 = new Group(
                usr1, 
                "Group Three",
                "Third, Blah, Blah",
                "#ThirdGroup"
        );
        groups.add(gp3);
        gp3.addMember(usr2);

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

    public ArrayList<User> getUserList() {
        return this.users;
    }

    public void createGroup(String name, String categories, String tags) {
        this.groups.add(new Group(this.activeUser, name, categories, tags));
    }
    
    public User createUser(String username, String password) {
    
	    // Create user.
	    User newUser = new User(username, password, "", "", "");
	    
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
