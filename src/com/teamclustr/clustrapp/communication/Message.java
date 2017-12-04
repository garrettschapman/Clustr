package com.teamclustr.clustrapp.communication;

import java.io.Serializable;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: Message.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class Message implements Serializable {
	private User author;
	private User recipient;
	private String body;
	private LocalDateTime date;
	
	/*
	 * Constructor
	 */
	public Message(User author, User recipient, String body, LocalDateTime date) {
		this.author = author;
		this.recipient = recipient;
		this.body = body;
		this.date = date;
	} //end of constructor
	
	//getter statements
	public User getAuthor() {
		return this.author;
	}
	public User getRecipient() {
		return this.recipient;
	}
	public String getBody() {
		return this.body;
	}
	public LocalDateTime getDate() {
		return this.date;
	}
} //end of class Message
