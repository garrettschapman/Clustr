package com.teamclustr.clustrapp;

import com.teamclustr.clustrapp.representation.User;
import com.teamclustr.clustrapp.representation.Group;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: System.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class System implements Serializable {
	
	// MEMEBR DATA.
//	private User user;
	
	// The user active on this current session.
	private transient User activeUser;
	
	// All users.
	private ArrayList<User> users;
	
	// All groups.
	private ArrayList<Group> groups;
	
	// MEMBER METHODS.
	
	/**
	 * Construct a System.
	 */
	public System() {
		
		// Initialize fields.
		activeUser = null;
		users = new ArrayList<User>(16);
		groups = new ArrayList<Group>(16);
	}
	
	/**
	 * Get the active session user.
	 * @return active user
	 */
	public User getSessionUser() {
		
		return activeUser;
	}
}
