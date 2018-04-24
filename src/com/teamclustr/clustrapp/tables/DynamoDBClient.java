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
import com.teamclustr.clustrapp.representation.User;

public class DynamoDBClient {
	private AmazonDynamoDBClient client = null;
	private static final String AWS_KEY = "AKIAJ3BL4X6VCWDW2KAQ";
	private static final String AWS_SECRET = "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";
	
	public DynamoDBClient() {
		AWSCredentials credentials = new BasicAWSCredentials(AWS_KEY, AWS_SECRET);
			
		client = new AmazonDynamoDBClient(credentials);
	}
	
	public static void logMessage (String msg) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + " ==> " + msg);
	}
	
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
	
	public void PutUser(User user) {
		String tableName = "ClusterUser";
		
		logMessage("Putting items into table " + tableName);
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
		
		PutItemRequest itemRequest = new PutItemRequest().withTableName(tableName).withItem(item);
		client.putItem(itemRequest);
	}
}