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
	
	// The user account active for this session.
	private transient Account activeAccount;
	
	// All user accounts on the system.
	private ArrayList<Account> accounts;
	
	// All groups on the system.
	private ArrayList<Group> groups;
	
	// MEMBER METHODS.
	
}
