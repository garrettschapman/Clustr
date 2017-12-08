package com.teamclustr.clustrapp.communication;

//import statements
import java.io.Serializable;
import java.time.LocalDateTime;

import com.teamclustr.clustrapp.representation.User;

/**
 * Class file for Message
 * Creates and stores information about messages
 * Messages have an author, recipient, body, and date
 * Messages can be hidden or unhidden
 * Messages from blocked users are hidden
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
	 * User will get the message body back and be able to adjust it in the GUI
	 * Uses the adjusted body as a parameter
	 */
	public void setMessage(String newBody, LocalDateTime editDate) {
		this.body = newBody;
		this.date = editDate;
	} //end of method setMessage
	
	/*
	 * Method to set if the message is hidden
	 * If the author is blocked, the recipient does not see the message
	 * If/when the recipient unblocks the author, they can see all messages
	 * If the recipient blocks the author, they can no longer see past messages
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
