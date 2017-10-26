package com.teamclustr.clustrapp;

import com.teamclustr.clustrapp.communication.Post;
import com.teamclustr.clustrapp.representation.User;
import com.teamclustr.clustrapp.representation.Group;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * BRIEF CLASS DESCRIPTION.
 *
 * @author Team Clustr
 * @version 1.0 File: Server.java Created: 10/17/2017 Copyright (c) 2017, Team
 * Clustr, All rights reserved. Summary of Modifications: N/A
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
        sessionUser = null; // who is logged into the system
        users = new ArrayList<User>(16);
        groups = new ArrayList<Group>(16);

        // add some users, groups, and posts
        User usr = new User("TestUser", "TestPAssword", 
                "test@email.com", "5555555555", "THIS IS A BIO");
        Group gp = new Group(usr, "First Group", "First, Group", "#First, #Java");
        Post pst = new Post(usr, "Body Of Post", "TITLE");
        
        users.add(usr);
        sessionUser = usr; // TODO: MAKE THE SESSION USER A PARAMETER OF CONSTRUCTOR
        groups.add(gp);
        gp.leavePost(pst);
    }

    /**
     * Get the active session user.
     *
     * @return session user
     */
    public User getSessionUser() {
        return sessionUser;
    }

    public ArrayList getGroupList() {
        return this.groups;
    }

    public ArrayList getUserList() {
        return this.users;
    }

    public void createGroup(String name, String categories, String tags) {
        this.groups.add(new Group(this.sessionUser, name, categories, tags));
    }

    public Group getGroup(int row) {
        return this.groups.get(row);
    }
}
