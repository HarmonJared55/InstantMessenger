//User.java

//Author: Jared Harmon
//File Created: 10/29/18
//Last Edited : 10/30/18

/* This file is the implementaion of the user class
 *
 * This class is intened to be run as a thread so that multiple clients can connect to the server at the same time. 
 * Each thread holds the username of the user, input and output streams and the actual socket connection
 * It also contains a copy of the users array that is part of its constructors paramater
 *
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
	private int max_users;
	private String user_name;
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;

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
				
		}catch(Exception e){

			System.out.println("Failed to create user");		
	
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
 	 */		

	public void run(){

		try{

			//get user_name
			out.println("Enter User Name:");
			user_name = in.readLine();

			//welcome the user to the chatroom
			out.println("Welcome " + user_name + " to the chat room!");
			out.println("Type your message or '/exit' to exit");

			//announce entrance of new user to all users
			for(int i = 0; i < max_users; i++){
				
				users[i].getOUT().println("User " + user_name + " connected!");

			}//end for	
	
			//start messaging
			while(true){
			
				String message = in.readLine();
		
				if(message.equalsIgnoreCase("/exit")){

					//user wants to exit
					break;

				}else{

					//user sends a public message
					for(int i = 0; i < max_users; i++){

						if(users[i] != null && !users[i].getUserName().equalsIgnoreCase(user_name)){

							//if there is a user and that user is not the current thread
							users[i].getOUT().println(message);

						}//end if

					}//end for loop					

				}//end else

			}//end while
		
	        }catch(Exception e){
	
			System.out.println("Thread failed...");		

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

		for(int i = 0; i < max_users; i++){

			if(users[i] == null){
				
				//if the users[i] index is null
				users[i] = user;		

			}//end if

		}//end for

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

			if(users[i].getUserName().equalsIgnoreCase(user_name)){

				users[i] = null;
				return true;

			}//end if

		}//end for
		
		return false;

	}//end deleteUser

	/*
 	* Getters and Setters
 	*/

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

















