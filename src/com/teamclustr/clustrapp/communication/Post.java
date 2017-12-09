package com.teamclustr.clustrapp.communication;

//import statements
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.teamclustr.clustrapp.representation.User;

/**
 * Class file for Post Creates and stores information about posts Have an
 * author, a title, a body, a date, points, and comments Comments are structured
 * in the same way as posts (like sub-posts)
 * 
 * @author Garrett Chapman (Team Clustr)
 * @version 1.0 File: Post.java Created: 10/17/2017 Copyright (c) 2017, Team
 *          Clustr, All rights reserved. Summary of Modifications: N/A
 */
public class Post implements Serializable {

	// Variables for Post
	private User owner; // owner (creator) of the post
	private String body; // body of the post
	private ArrayList<Post> comments; // all comments on the post
	private int points; // points for the post
	private LocalDateTime date; // time the post was created
	private String title;
	private ArrayList<String> votedUsers; // users who voted
	private int countVote = 0; //check up and down vote 

	/*
	 * Constructor for Post Uses the owner and the body (entered by the User) as
	 * parameters The owner is the current User The body will be requested by and
	 * entered into the System
	 */
	public Post(User owner, String body, String title) {
		this.owner = owner;
		this.body = body;
		this.comments = new ArrayList<Post>(0); // no current comments
		this.points = 0; // owner automatically likes their post
		this.date = LocalDateTime.now(); // sets date to date of posting
		this.title = title; // set the title
		votedUsers = new ArrayList<String>(); // users who voted 
	} // end of constructor

	// Methods for Posts
	/*
	 * Getter for the body of the Post
	 */
	public String getBody() {
		return this.body;
	} // end of getter

	/*
	 * Getter for the owner of the Post
	 */
	public User getOwner() {
		return this.owner;
	} // end of getter

	/*
	 * Method to edit the body of the Post System will have to use the getter to
	 * allow user to change body Call this method with the edited body in order to
	 * create changes
	 */
	public void setBody(String newBody) {
		this.body = newBody; // sets the body to the new body
	} // end of method

	// Methods for comments
	/*
	 * Getter for the body of a comment Uses the index of the comment to find it
	 */
	public String getComment(int index) {
		return this.comments.get(index).getBody();
	} // end of getter
	
	//get voted users
	public void addVotedUsers(String username) { 
		votedUsers.add(username);
	}

	//increment post points
	public void incrementPoints(String username) {

		for (int i = -1; i < votedUsers.size(); i++) {
			if (votedUsers.contains(username)) {
				// does not increment points unless down vote was clicked first
				if (this.countVote == 0) {
					this.points++;
					this.countVote = 1;
				} 
				break;
			} else { //increments points if up vote was clicked first
				this.points++; // increments
				this.countVote = 1;
				break;
			}
		}

	}
	
	//decrement post points
	public void decrementPoints(String username) {
		for (int i = -1; i < votedUsers.size(); i++) {
			if (votedUsers.contains(username)) {
				// does not decrement points unless up vote was clicked first
				if (this.countVote == 1) {
					this.points--;
					this.countVote = 0;
				}
				break;
			} else { // decrements points if down vote was clicked first
				if (this.countVote == 1) {
				this.points--; // decrements
				this.countVote = 0;
				break;
				}
			}
		}
	}
	
	public ArrayList<Post> getCommentList() {
		return this.comments;
	}

	/*
	 * Method to add a new comment to the Post Takes the owner and body (entered by
	 * the User) The owner is the current User
	 */
	public void addComment(User cowner, String cbody, String title) {
		Post newComment = new Post(cowner, cbody, title); // creates the comment
		this.comments.add(newComment); // adds the comment to the array list
	} // end of method

	/*
	 * Method to edit a comment Uses the index of the comment to find it Uses Post
	 * editor to edit comment
	 */
	public void setComment(int index, String newComment) {
		this.comments.get(index).setBody(newComment); // edits comment
	} // end of method

	/*
	 * Method to delete a comment on the Post Leaves a record of the comment
	 * existing Comment owner removed and body set to say that it was deleted
	 */
	public void deleteComment(int index) {
		Post deleted = new Post(null, "[Deleted]", "[Deleted]"); // creates new deleted Post
		this.comments.set(index, deleted); // sets comment to deleted Post
	} // end of method

	// Methods for points
	/*
	 * Getter for the points of the Post
	 */
	public int getPoints() {
		return this.points;
	} // end of getter

	/*
	 * Method to add a point to the Post
	 */
	public void addPoint() {
		this.points++; // increments by one
	} // end of method addPoint

	/*
	 * Method to remove a point from the Post
	 */
	public void removePoint() {
		this.points--; // decrements by one
	} // end of method removePoint

	// Misc. getters
	/*
	 * Getter for the date
	 */
	public LocalDateTime getDate() {
		return date;
	} // end of getter

	/*
	 * Getter for the title
	 */
	public String getTitle() {
		return this.title;
	} // end of getter
} // end of class Post
