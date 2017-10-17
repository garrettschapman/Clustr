package com.teamclustr.clustrapp;

import com.teamclustr.clustrapp.representation.Account;
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
	
	// The user account active for this system.
	private transient Account activeAccount;
	
	// All user accounts on this system.
	private ArrayList<Account> accounts;
	
	// All groups on this system.
	private ArrayList<Group> groups;
	
	// MEMBER METHODS.
	
	/**
	 * Construct a System.
	 */
	public System() {
		
		// Initialize fields.
		activeAccount = null;
		accounts = new ArrayList<Account>(16);
		groups = new ArrayList<Group>(16);
	}
	
	/**
	 * Get this system's active account.
	 * @return active user account
	 */
	public Account getActiveAccount() {
		
		return activeAccount;
	}
}
