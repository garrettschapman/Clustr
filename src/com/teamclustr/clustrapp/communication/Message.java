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
	private Boolean isHidden;
	
	/*
	 * Constructor
	 */
	public Message(User author, User recipient, String body, LocalDateTime date, Boolean isHidden) {
		this.author = author;
		this.recipient = recipient;
		this.body = body;
		this.date = date;
		this.isHidden = isHidden;
	} //end of constructor
	
	/*
	 * Method to edit a message
	 */
	public void setMessage(String newBody, LocalDateTime editDate) {
		this.body = newBody;
		this.date = editDate;
	} //end of method setMessage
	
	/*
	 * Method to set if the message is hidden
	 */
	public void setIsHidden(Boolean isBlocked) {
		this.isHidden = isBlocked;
	} //end of method setIsHidden
	
	/*
	 * Getters for variables
	 */
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
	public Boolean getIsHidden() {
		return this.isHidden;
	} //end of getter methods
} //end of class Message
