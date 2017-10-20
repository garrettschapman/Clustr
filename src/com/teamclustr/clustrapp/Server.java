package com.teamclustr.clustrapp;

import com.teamclustr.clustrapp.representation.User;
import com.teamclustr.clustrapp.representation.Group;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 File: Server.java
 Created: 10/17/2017
 Copyright (c) 2017, Team Clustr, All rights reserved.
 Summary of Modifications:
  N/A
 */
public class Server implements Serializable {
	
	// MEMEBR DATA.
	
	// The user active on this current session.
	private transient User sessionUser;
	
	// All users.
	private ArrayList<User> users;
	
	// All groups.
	private ArrayList<Group> groups;
	
	// MEMBER METHODS.
	
	/**
	 * Construct a System.
	 */
	public Server() {
		
		// Initialize fields.
		sessionUser = null;
		users = new ArrayList<User>(16);
		groups = new ArrayList<Group>(16);
                
	}
	
	/**
	 * Get the active session user.
	 * @return session user
	 */
	public User getSessionUser() {
		
		return sessionUser;
	}
        
        public ArrayList getGroupList(){
            return this.groups;
        }
}
