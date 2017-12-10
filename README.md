

	DESCRIPTION:
	Clustr is an application that allows people to create and find groups of people with similar interests.
	People can create posts in their groups in order to schedule events and discuss things related to their group, 
	and can create and join groups whenever they want.

	PROJECT SOURCE:
	https://bitbucket.org/findyourselfagroup/clustrapp/src
					 					

	AUTHORS:
	A group of Software Engineering students from Florida Gulf Coast University 
	Tyler Dalbora: Team Leader and GUI Design
	Garrett Fairburn: GUI Design and Implementation
	Garrett Chapman: Communication Classes
	Jirawat Hirunkum: User Class
	Thomas Price-Gedrites: Groups and Testing
		

	MOTIVATION:
	Our team was formed to build this project as a part of the Software Engineering Fundamentals course		  

	PROGRAMMING STYLE AND CODE EXAMPLE:
	The GUI was built by using NetBeans GUI Builder.
	The back-end programming was done using Eclipse.
	
    Here are sections of codes on how we structured our User class, Group class, and Post class:
    
 * Section of code from User class:

	// Constructor with parameters of user's general information
	public User(String Username, String Password, String Email, 
	  String PhoneNum, String Bio) {

		createAccount(Username, Password, Email, PhoneNum, Bio);

		Friends = new ArrayList<User>();
		GroupList = new ArrayList<Group>();
		Posts = new ArrayList<Post>();
		Comments = new ArrayList<Post>();
		BlockUsers = new ArrayList<User>();

	}
	// MEMBER METHODS.

	// function to create user's account with general info
	public void createAccount(String Username, String Password, 
	  String Email, String PhoneNum, String Bio) {
	  
		this.setUsername(Username);
		this.setPassword(Password);
		this.setEmail(Email);
		this.setPhoneNum(PhoneNum);
		this.setBio(Bio);
	}

 * Section of code from Group class:

	public Group(User owner, String name, String categories, String tags) {
	        groupName = name;
	        members.add(owner);
	        moderators.add(owner);

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
        if (bannedUsers.contains(user)) {
            return false;
        } else {
            members.add(user);
            return true;
        }
    }
	//leaves the group

    public void leaveGroup(User user) {
        members.remove(user);
    }

 * Section of code from Post class:
 
	 /*
	 * Constructor for Post Uses the owner and the body (entered by the User) as
	 * parameters The owner is the current User The body will be requested by and
	 * entered into the System
	 */
	public Post(User owner, String body, String title) {
		this.owner = owner;
		this.body = body;
		this.comments = new ArrayList<Post>(0); // no current comments
		this.points = 1; // owner automatically likes their post
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
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
 

