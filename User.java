//User.java

//Author: Jared Harmon
//File Created: 10/29/18
//Last Edited : 10/31/18

//Version 2.0

/* This file is the implementaion of the class User
 *
 * The user class extends from the java class thread. 
 * It allows multiple dicreate clients to connect to the server
 * and communicate.
 */

//imported classes
import java.io.*;
import java.net.*;

public class User extends Thread{

	//final member variables
	//for this project the host and port will never change
	//can be easily extened to allow for actual server hosting
	private final int PORT = 1234;
	private final String IP = "127.0.0.1";

	//member varibales
	private MessageList ml = new MessageList();
	private int max_users;
	private String user_name;
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;
	private boolean has_user_exited;
	private String message;

	//array of all other user threads for easy communication
	private User[] users;


	/*
 	 * This is the constructor for the User Class
 	 *
 	 * This constructor completes the vital functions of setting up the input 
 	 * and output streams for this thread, sets the max_users size for use 
 	 * through out the class, and seting up the users array
 	 *
	 */
	public User(Socket s, User[] users){

		//set up input and output streams
		//get and set user name
		try{
		
			connection = s;
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(),true); 
			max_users = users.length;
			this.users = users;
		
			out.println("\nSuccessfully connected to server| IP: " + IP + " PORT: " + PORT + "\n");
			out.println("******************************************");
			out.println("*                                        *");
			out.println("*          Instant Messenger             *");
			out.println("*                 v2.0                   *");
			out.println("*             HighG Studio               *");
			out.println("*                                        *");
			out.println("******************************************");

			out.println("\nEnter a username");
			user_name = in.readLine();
			has_user_exited = false;
	
		}catch(Exception e){

			System.out.println("Connection Error!!!");		
	
		}//end try
		
	}//end User Constructor

	/*
 	 * Run function
 	 * 
 	 * Called from main server class when thread is created
 	 *
 	 * This class is the bread and butter of the user thread
 	 * It gets the user name that the client enters, welcomes and announces
 	 * the user, and starts the main messaging loop
 	 * 
 	 * It controls all the input and output to all the users. 
 	 * It also parses the messages and looks for commands
 	 */		

	public void run(){

		try{
			
			//welcome the user to the chatroom and let them know what they can do
			out.println("\nWelcome to the chat room " + user_name + "!");
			out.println("\nType /help for a list of commands");			
			out.println("-------------------CHAT----------------------\n");
			//announce entrance of new user to all users
			for(int i = 0; i < max_users; i++){
	
				if(users[i] != null && !users[i].getUserName().equalsIgnoreCase(user_name)){			
	
					users[i].getOUT().println("User " + user_name + " connected!");

				}//end if

			}//end for	

			//start messaging
			while(true){

		        	message = in.readLine();
			
				if(message.equals("/exit")){

					has_user_exited = true;
					out.close();
					in.close();
					connection.close();
					break;
				
				}else if(message.startsWith("@")){

					String target_user_name = message.substring(1,message.indexOf(" "));
	
					message = message.substring(message.indexOf(" "));	
						
					for(int i = 0; i < max_users; i++){

						if(users[i].getUserName().equalsIgnoreCase(target_user_name)){
			
							users[i].getOUT().println("Private Message from <" + user_name + "> " + message);
							break;

						}//end if
		
					}//end for
					

				}else if(message.equalsIgnoreCase("/help")){
						
					out.println("\n-------------Commands------------------------");
					listCommands();
					out.println("---------------------------------------------");
				
				}else if(message.equalsIgnoreCase("/list")){
					
					out.println("------------------------------------------------");
					ml.listMessages(out);	
					out.println("\n---------------Last 100 Messages---------------");
	
				}else if(message.equalsIgnoreCase("/users")){
			
					out.println("\n---------------Users Connected------------------");		
					for(int i = 0; i < max_users; i++){

						if(users[i] != null){

							out.println(users[i].getUserName());

						}//end if

					}//end for
					out.println("--------------------------------------------------");	

				}else{

					
					ml.addMessage("<" + user_name + "> " + message);

					//user sends a public message
					for(int i = 0; i < max_users; i++){

						if(users[i] != null && !users[i].getUserName().equalsIgnoreCase(user_name)){

							//if there is a user and that user is not the current thread
							users[i].getOUT().println("<" + user_name + "> " + message);

						}//end if

					}//end for loop					

				}//end else


			}//end while
			
	        }catch(Exception e){
		
			e.printStackTrace();		
			out.println("User not found");

		}//end try	

	}//end run

	/*
         * addUser Method
         *
         * This method is here to make sure that all user threads are aware of all the other user threads
         * 
         * This method takes in a user and adds it to the next null index in the user arrays
         * the user array should not beocme out of order compared to all the other threads
         * however the functions that use the array will not care about the order 
         *
         */ 

	
	public void addUser(User user){

		if(!user.getUserName().equalsIgnoreCase(user_name)){

			for(int i = 0; i < max_users; i++){

				if(users[i].getUserName().equalsIgnoreCase(user_name)){
					break;
				}else if(users[i] == null){
				
					//if the users[i] index is null
					users[i] = user;		
					break;
	
				}//end if
	
			}//end for

		}//end if

	}//end add User

	/*
 	* deleteUser Method
 	*
 	* allows for the deletion of a thread from the users array 
 	* deletes the user based on username
 	*
 	* Returns true or false if deletion is successful or failed
 	*/ 	 	

	public boolean deleteUser(String user_name){

		for(int i = 0; i < max_users; i++){

			if(!this.user_name.equalsIgnoreCase(user_name)){

				users[i] = null;
				return true;

			}//end if

		}//end for
		
		return false;

	}//end deleteUser

	/*
 	 * lists out all avaliable commands
 	 *
 	 * easily extensible
 	 */ 	
	public void listCommands(){

		out.println("Exit:\n");
		out.println("\t/exit -Exits the program\n");
		out.println("Private Message: \n");
		out.println("\t-type @username message");
		out.println("\t Example: @Bob this is a private message\n");
		out.println("List Messages:\n");
		out.println("\t/list -lists the last 100 messages sent on the server\n");
		out.println("List Users: \n");
		out.println("\t/users -lists all users currently connected to the server\n");
		out.println("List Commands:\n");
		out.println("\t/help -shows current list");
	}//end list commands

	/*
 	* Getters and Setters
 	*/

	public boolean hasUserExited(){
	
		return has_user_exited;

	}//end hasUserExited

	public String getUserName(){
		
		return user_name;

	}//end getUserName	

	public void setUserName(String user_name){

		this.user_name = user_name;

	}//end setUserName

	public BufferedReader getIN(){
	
		return in;

	}//end getIN

	public PrintWriter getOUT(){

		return out;
		
	}//end getOUT

}//end class User
