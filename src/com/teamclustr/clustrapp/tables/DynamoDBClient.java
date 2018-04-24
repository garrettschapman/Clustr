package com.teamclustr.clustrapp.tables;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeAction;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.AttributeValueUpdate;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.DeleteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteItemResult;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteTableResult;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateItemResult;
import com.amazonaws.services.dynamodbv2.model.UpdateTableRequest;
import com.amazonaws.services.dynamodbv2.model.UpdateTableResult;
import com.teamclustr.clustrapp.communication.Post;
import com.teamclustr.clustrapp.representation.Group;
import com.teamclustr.clustrapp.representation.User;

public class DynamoDBClient {
	private AmazonDynamoDBClient client = null;
	private static final String AWS_KEY = "AKIAIOSPNHD3KKXPQ6SQ";
	private static final String AWS_SECRET = "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";
	
	public DynamoDBClient() {
		AWSCredentials credentials = new BasicAWSCredentials(AWS_KEY, AWS_SECRET);
			
		client = new AmazonDynamoDBClient(credentials);
	}
	
	public static void logMessage (String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + " ==> " + msg);
	}
	
	/*
	 * Methods for all tables in general
	 * get status, describe, update, and list
	 */
	public String getTableStatus(String tableName) {
		TableDescription tableDescription = client.describeTable(new DescribeTableRequest().withTableName(tableName)).getTable();
		
		return tableDescription.getTableStatus();
	}
	
	public void describeTable(String tableName) {
		logMessage("Describing table " + tableName);
		TableDescription tableDescription = client.describeTable(new DescribeTableRequest().withTableName(tableName)).getTable();
		
		String desc = String.format("%s: %s \t ReadCapacityUnits: %d \t WriteCapacityUnits: %d",
				tableDescription.getTableStatus(), tableDescription.getTableName(), 
				tableDescription.getProvisionedThroughput().getReadCapacityUnits(), 
				tableDescription.getProvisionedThroughput().getWriteCapacityUnits());
		
		logMessage(desc);
	}
	
	public void updateTable(String tableName) {
		logMessage("Updating table " + tableName);
		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
				.withReadCapacityUnits(5L).withWriteCapacityUnits(10L);

		UpdateTableRequest updateTableRequest = new UpdateTableRequest()
				.withTableName(tableName).withProvisionedThroughput(
						provisionedThroughput);

		UpdateTableResult result = client.updateTable(updateTableRequest);
		logMessage("Updated table "
				+ result.getTableDescription().getTableName());
	}

	public void listTables() {
		logMessage("Listing tables");
		// Initial value for the first page of table names.
		String lastEvaluatedTableName = null;
		do {

			ListTablesRequest listTablesRequest = new ListTablesRequest()
					.withLimit(10).withExclusiveStartTableName(
							lastEvaluatedTableName);

			ListTablesResult result = client.listTables(listTablesRequest);
			lastEvaluatedTableName = result.getLastEvaluatedTableName();

			for (String name : result.getTableNames()) {
				logMessage(name);
			}

		} while (lastEvaluatedTableName != null);
	}
	/*
	 * End of methods for tables
	 */
	
	
	/*
	 * Methods for users
	 * login, create, pull data, delete, and update (by deleting and re-creating)
	 */
	public Boolean CheckLogin(String username, String password) {
		String tableName = "ClusterUser";
		
		username = "{S: " + username + ",}";
		password = "{S: " + password + ",}";
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			AttributeValue userName = item.get("UserName");
			AttributeValue passWord = item.get("Password");
			
			String userNameString = userName.toString();
			String passWordString = passWord.toString();
			
			if(username.equals(userNameString) && password.equals(passWordString)) {
				return true;
			}
		}
		
		return false;
	}
	
	public User getUserData(String username) {
		String tableName = "ClusterUser";
		User user = new User(username, "password", "example@example.com", "1234567890", "This is a bio.", 0, "Male", "Caucasian", false, "Accounting", "Freshman", "SoVi");
		
		String USERNAME = "{S: " + username + ",}";
		String password, email, phone, bio, gender, ethnicity, major, year, location;
		int age;
		Boolean married;
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			AttributeValue userName = item.get("UserName");
			String userNameString = userName.toString();
			
			if(USERNAME.equals(userNameString)) {
				password = item.get("Password").getS();
				email = item.get("Email").getS();
				phone = item.get("PhoneNum").getS();
				bio = item.get("Bio").getS();
				age = Integer.parseInt((item.get("Age").getN()));
				gender = item.get("Gender").getS();
				ethnicity = item.get("Ethnicity").getS();
				married = item.get("Married").getBOOL();
				major = item.get("Major").getS();
				year = item.get("Year").getS();
				location = item.get("Location").getS();
				
				user = new User(username, password, email, phone, bio, age, gender, ethnicity, married, major, year, location);
				user.setInterest(item.get("Interest1").getS(), 0);
				user.setInterest(item.get("Interest2").getS(), 1);
				user.setInterest(item.get("Interest3").getS(), 2);
				return user;
			}
		}
		
		return user;
	}
	
	public ArrayList<User> getAllUsers() {
		String tableName = "ClusterUser";
		ArrayList<User> userList = new ArrayList<User>(0);
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			String username = item.get("UserName").getS();
			User user = this.getUserData(username);
			userList.add(user);
		}
		
		return userList;
	}
	
	public void PutUser(User user) {
		String tableName = "ClusterUser";
		
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		
		item.put("UserName", new AttributeValue().withS(user.getUsername()));
		
		item.put("Age", new AttributeValue().withN(Integer.toString(user.getAge())));
		item.put("Bio", new AttributeValue().withS(user.getBio()));
		item.put("Email", new AttributeValue().withS(user.getEmail()));
		item.put("Ethnicity", new AttributeValue().withS(user.getEthnicity()));
		item.put("Gender", new AttributeValue().withS(user.getGender()));
		item.put("Interest1", new AttributeValue().withS(user.getInterest(0)));
		item.put("Interest2", new AttributeValue().withS(user.getInterest(1)));
		item.put("Interest3", new AttributeValue().withS(user.getInterest(2)));
		item.put("Location", new AttributeValue().withS(user.getLocation()));
		item.put("Major", new AttributeValue().withS(user.getMajor()));
		item.put("Married", new AttributeValue().withBOOL(user.getMaritalStatus()));
		item.put("Password", new AttributeValue().withS(user.getPassword()));
		item.put("PhoneNum", new AttributeValue().withS(user.getPhoneNum()));
		item.put("Year", new AttributeValue().withS(user.getYear()));
		
		PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
		client.putItem(itemRequest);
	}
	
	public void DeleteUser(User user) {
		String tableName = "ClusterUser";
		
		Map<String, ExpectedAttributeValue> expectedValues = new HashMap<String, ExpectedAttributeValue>();
		HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put("UserName", new AttributeValue().withS(user.getUsername()));
		
		expectedValues.put("UserName", new ExpectedAttributeValue().withValue(new AttributeValue().withS(user.getUsername())));
		
		ReturnValue returnValues = ReturnValue.ALL_OLD;
		
		DeleteItemRequest deleteItemRequest = new DeleteItemRequest().withTableName(tableName).withKey(key).withExpected(expectedValues).withReturnValues(returnValues);
		
		@SuppressWarnings("unused")
		DeleteItemResult result = client.deleteItem(deleteItemRequest);
	}
	/*
	 * End of methods for users
	 */
	
	
	/*
	 * Methods for groups
	 */
	public void PutGroup(Group group) {
		String tableName = "ClusterGroup";
		
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		
		item.put("GroupName", new AttributeValue().withS(group.getName()));
		
		item.put("Owner", new AttributeValue().withS(group.getOwner().getUsername()));
		item.put("Categories", new AttributeValue().withSS(group.getCatList()));
		item.put("Tags", new AttributeValue().withSS(group.getTagList()));
		item.put("Members", new AttributeValue().withSS(group.getMemberList()));
		item.put("Size", new AttributeValue().withN(Integer.toString(group.getSize())));
		
		if(group.getPostNames().size() > 0) {
			item.put("Posts", new AttributeValue().withSS(group.getPostNames()));
			
			for(Post post : group.getPosts()) {
				this.PutPost(post);
			}
		}
		
		PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
		client.putItem(itemRequest);
	}
	
	public void DeleteGroup(Group group) {
		String tableName = "ClusterGroup";
		
		Map<String, ExpectedAttributeValue> expectedValues = new HashMap<String, ExpectedAttributeValue>();
		HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put("GroupName", new AttributeValue().withS(group.getName()));
		
		expectedValues.put("GroupName", new ExpectedAttributeValue().withValue(new AttributeValue().withS(group.getName())));
		
		ReturnValue returnValues = ReturnValue.ALL_OLD;
		
		DeleteItemRequest deleteItemRequest = new DeleteItemRequest().withTableName(tableName).withKey(key).withExpected(expectedValues).withReturnValues(returnValues);
		
		@SuppressWarnings("unused")
		DeleteItemResult result = client.deleteItem(deleteItemRequest);
	}
	
	public Group GetGroupData(String groupname) {
		String tableName = "ClusterGroup";
		Group group = new Group(null, groupname, "", "");
		
		String GROUPNAME = "{S: " + groupname + ",}";
		User owner;
		ArrayList<String> categories = new ArrayList<String>(0);
		ArrayList<String> tags = new ArrayList<String>(0);
		ArrayList<String> memberNames = new ArrayList<String>(0);
		ArrayList<String> postNames = new ArrayList<String>(0);
		int size;
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			AttributeValue groupName = item.get("GroupName");
			String groupNameString = groupName.toString();
			
			if(GROUPNAME.equals(groupNameString)) {
				owner = this.getUserData(item.get("Owner").getS());
				categories = (ArrayList<String>) item.get("Categories").getSS();
				tags = (ArrayList<String>) item.get("Tags").getSS();
				memberNames = (ArrayList<String>) item.get("Members").getSS();
				try {
					postNames = (ArrayList<String>) item.get("Posts").getSS();
				}catch(NullPointerException e) {
					postNames = new ArrayList<String>(0);
				}
				
				size = Integer.parseInt(item.get("Size").getN());
				
				group.setOwner(owner);
				group.setCatList(categories);
				group.setTagList(tags);
				group.setPostNames(postNames);
				group.setSize(size);
				
				for(String memberName : memberNames) {
					if(!(memberName.equals(group.getOwner().getUsername()))) {
						group.addMemberName(memberName);
					}
				}
				
				ArrayList<Post> posts = new ArrayList<Post>(0);
				for(String postName : postNames) {
					Post post = this.GetPostData(postName);
					posts.add(post);
				}
				
				for(Post post : posts) {
					group.leavePost(post);
				}
				return group;
			}
		}
		
		return group;
	}
	
	public ArrayList<Group> GetAllGroups() {
		String tableName = "ClusterGroup";
		ArrayList<Group> groupList = new ArrayList<Group>(0);
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			String groupname = item.get("GroupName").getS();
			Group group = this.GetGroupData(groupname);
			groupList.add(group);
		}
		
		return groupList;
	}
	/*
	 * End of methods for groups
	 */
	
	/*
	 * Methods for posts
	 */
	public void PutPost(Post post) {
		String tableName = "ClusterPost";
		
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		
		item.put("Title", new AttributeValue().withS(post.getTitle()));
		
		item.put("Owner", new AttributeValue().withS(post.getOwner()));
		item.put("Body", new AttributeValue().withS(post.getBody()));
		item.put("Points", new AttributeValue().withN(Integer.toString(post.getPoints())));
		
		if(post.getCommentList().size() > 0) {
			item.put("Comments", new AttributeValue().withSS(post.getCommentTitles()));
		}
		
		for(Post comment : post.getCommentList()) {
			this.PutPost(comment);
		}
		
		PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
		client.putItem(itemRequest);
	}
	
	public void DeletePost(Post post) {
		for(Post comment : post.getCommentList()) {
			this.DeletePost(comment);
		}
		
		String tableName = "ClusterPost";
		
		Map<String, ExpectedAttributeValue> expectedValues = new HashMap<String, ExpectedAttributeValue>();
		HashMap<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put("Title", new AttributeValue().withS(post.getTitle()));
		
		expectedValues.put("Title", new ExpectedAttributeValue().withValue(new AttributeValue().withS(post.getTitle())));
		
		ReturnValue returnValues = ReturnValue.ALL_OLD;
		
		DeleteItemRequest deleteItemRequest = new DeleteItemRequest().withTableName(tableName).withKey(key).withExpected(expectedValues).withReturnValues(returnValues);
		
		@SuppressWarnings("unused")
		DeleteItemResult result = client.deleteItem(deleteItemRequest);
	}
	
	public void UpdateGroupPosts(Group group) {
		this.DeleteGroup(group);
		this.PutGroup(group);
		
		for(Post post : group.getPosts()) {
			try {
				this.DeletePost(post);
			}catch(Exception e) {
				
			}
			
			this.PutPost(post);
		}
	}
	
	public Post GetPostData(String title) {
		String tableName = "ClusterPost";
		Post post = new Post("", "", title);
		
		String owner = "", body = "";
		int points = 0;
		ArrayList<Post> comments = new ArrayList<Post>(0);
		ArrayList<String> commentNames = new ArrayList<String>(0);
		
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			AttributeValue Title = item.get("Title");
			String titleString = Title.getS();
			
			if(title.equals(titleString)) {
				owner = item.get("Owner").getS();
				body = item.get("Body").getS();
				points = Integer.parseInt(item.get("Points").getN());
				
				try {
					commentNames = (ArrayList<String>) item.get("Comments").getSS();
					
					for(String commentName : commentNames) {
						Post newPost = this.GetPostData(commentName);
						comments.add(newPost);
					}
				}catch(Exception e) {
					
				}
				
				post.setOwner(owner);
				post.setBody(body);
				post.setPoints(points);
				post.setCommentList(comments);
				post.setCommentTitles(commentNames);
			}
		}
		
		return post;
	}
	/*
	 * End of methods for posts
	 */
}