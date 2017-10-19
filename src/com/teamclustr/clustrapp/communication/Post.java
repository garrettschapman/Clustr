package com.teamclustr.clustrapp.communication;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.teamclustr.clustrapp.representation.User;

/**
 * BRIEF CLASS DESCRIPTION.
 * 
 * @author Team Clustr
 * @version 1.0
 * File: Post.java
 * Created: 10/17/2017
 * Copyright (c) 2017, Team Clustr, All rights reserved.
 * Summary of Modifications:
 *  N/A
 */
public class Post implements Serializable {
	
//Variables for Post
	private User owner; //owner (creator) of the post
	private String body; //body of the post
	private ArrayList<Post> comments; //all comments on the post
	private int points; //points for the post
	private LocalDateTime date; //time the post was created
//End of variables	
	
	/*
	 * Constructor for Post
	 * Uses the owner and the body (entered by the User) as parameters
	 * The owner is the current User
	 * The body will be requested by and entered into the System
	 */
	public Post(User owner, String body) {
		this.owner = owner;
		this.body = body;
		comments = new ArrayList<Post>(0); //no current comments
		this.points = 1; //owner automatically likes their post
		this.date = LocalDateTime.now(); //sets date to date of posting
	} //end of constructor
	
//Methods for Posts
	/*
	 * Getter for the body of the Post
	 */
	public String getBody() {
		return this.body;
	} //end of getter
	
	/*
	 * Getter for the owner of the Post
	 */
	public User getOwner() {
		return this.owner;
	} //end of getter
	
	/*
	 * Method to edit the body of the Post
	 */
	public void editBody() {
		
	} //end of method

	
	
//Methods for comments
	/*
	 * Getter for the body of a comment
	 * Uses the index of the comment to find it
	 * Once it has the comment, it gets the body normally
	 */
	public String getComment(int index) {
		//temporary variables
		Post comment;
		String cbody;
		
		comment = this.comments.get(index); //sets temporary comment
		cbody = comment.getBody(); //gets body from temporary comment
		
		return cbody;
	} //end of getter
	
	/*
	 * Method to add a new comment to the Post
	 * Takes the owner and body (entered by the User)
	 * The owner is the current User
	 */
	public void addComment(User cowner, String cbody) {
		Post newComment = new Post(cowner, cbody); //creates the comment
		this.comments.add(newComment); //adds the comment to the array list
	} //end of method
	
	/*
	 * Method to edit a comment
	 * Uses the index of the comment to find it
	 * Uses Post editor to edit comment
	 */
	public void editComment(int index) {
		this.comments.get(index).editBody(); //edits comment
	} //end of method
	
	/*
	 * Method to delete a comment on the Post
	 * Leaves a record of the comment existing
	 * Comment owner removed and body set to say that it was deleted
	 */
	public void deleteComment(int index) {
		Post deleted = new Post(null, "[Deleted]"); //creates new deleted Post
		this.comments.set(index, deleted); //sets comment to deleted Post
	} //end of method


	
//Methods for points
	/*
	 * Getter for the points of the Post
	 */
	public int getPoints() {
		return this.points;
	} //end of getter
	
	/*
	 * Method to add a point to the Post
	 */
	public void addPoint() {
		this.points++; //increments by one
	} //end of method
	
	/*
	 * Method to remove a point from the Post
	 */
	public void removePoint() {
		this.points--; //decrements by one
	} //end of method
	
	
	
//Methods for the date
	/*
	 * Getter for the date
	 */
	public LocalDateTime getDate() {
		return date;
	} //end of getter


	
} //end of class
