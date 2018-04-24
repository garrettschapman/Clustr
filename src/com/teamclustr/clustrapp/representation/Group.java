package com.teamclustr.clustrapp.representation;

import java.io.Serializable;
import com.teamclustr.clustrapp.communication.Post;
import java.util.ArrayList;

/**
 * BRIEF CLASS DESCRIPTION.
 *
 * @author Team Clustr
 * @version 1.0 File: Group.java Created: 10/17/2017 Copyright (c) 2017, Team
 * Clustr, All rights reserved. Summary of Modifications: N/A
 */
@SuppressWarnings("serial")
public class Group implements Serializable {

//variables for group
//test for push
	private User owner;
	private int groupSize;
    private ArrayList<User> members = new ArrayList<User>();
    private ArrayList<String> memberList = new ArrayList<String>();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private ArrayList<String> postNames = new ArrayList<String>();
    private ArrayList<String> categories = new ArrayList<String>();
    private ArrayList<String> tags = new ArrayList<String>();
    private String groupName = "";
//end variables

    public Group(User owner, String name, String categories, String tags) {
    	groupName = name;
    	this.owner = owner;
        members.add(owner);
        groupSize = 1;
        
        try{
        	memberList.add(this.owner.getUsername());
        }catch(NullPointerException e) {
        	
        }

        //deal with the tags and categories
        for (String s : categories.split(", ")) {
            this.categories.add(s);
        }

        for (String s : tags.split(", ")) {
            this.tags.add(s);
        }

    }
//adds members

    public boolean addMember(User user) {
        members.add(user);
        memberList.add(user.getUsername());
        groupSize++;
        return true;
    }
//leaves the group

    public void leaveGroup(User user) {
        members.remove(user);
        memberList.remove(user.getUsername());
        groupSize--;
    }

    public void leavePost(Post pst) {
        this.posts.add(pst);
        this.postNames.add(pst.getTitle());
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }
    
    public User getOwner() {
    	return this.owner;
    }
    
    public void setOwner(User owner) {
    	this.owner = owner;
    	memberList.add(this.owner.getUsername());
    }
    
    public ArrayList<String> getMemberNames() {
    	ArrayList<User> members = this.getMembers();
    	ArrayList<String> memberNames = new ArrayList<String>(0);
    	
    	for(User user : members) {
    		memberNames.add(user.getUsername());
    	}
    	
    	return memberNames;
    }

    public String getTags() {
        return this.tags.toString();
    }
    
    public void setTagList(ArrayList<String> tags) {
    	this.tags = tags;
    }
    
    public void setCatList(ArrayList<String> categories) {
    	this.categories = categories;
    }

    public String getName() {
        return this.groupName;
    }
    public ArrayList<String> getCatList(){
    	return categories;
    }
    public ArrayList<String> getTagList(){
    	return tags;
    }
    public String getCategories() {
        return this.categories.toString();
    }

    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    public Post getPostByTitle(String postName) {
        for (Post post : this.posts) {
            if (post.getTitle().equals(postName)) {
                return post;
            }
        }
        return null;
    }

    public boolean isMember(User usr) {
        for(String username : memberList) {
        	if(username.equals(usr.getUsername())) {
        		return true;
        	}
        }
        
        return false;
    }

    public boolean removePost(Post activePost) {
        try{
            return this.posts.remove(activePost);
        } catch (Exception e){
            // do nothing
            e.printStackTrace();
            return false;
        }
    }
    
    public void addMemberName(String username) {
    	this.memberList.add(username);
    }

    public void updateMembers(ArrayList<User> userList) {
    	for(User user : userList) {
    		if(!(isMember(user)) && this.memberList.contains(user.getUsername())) {
    			this.addMember(user);
    		}
    	}
    }
    
    public ArrayList<String> getMemberList() {
    	return this.memberList;
    }
    
    public void setSize(int size) {
    	groupSize = size;
    }
    
    public int getSize() {
    	return groupSize;
    }
    
    public ArrayList<String> getPostNames(){
    	ArrayList<String> updatedPostNames = new ArrayList<String>(0);
    	
    	for(String name : this.postNames) {
    		if(!(updatedPostNames.contains(name))) {
    			updatedPostNames.add(name);
    		}
    	}
    	this.postNames = updatedPostNames;
    	
    	return this.postNames;
    }
    
    public void setPostNames(ArrayList<String> names) {
    	this.postNames = names;
    }
}
