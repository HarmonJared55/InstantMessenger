//Server.java

//Author: Jared Harmon
//File Created: 10/27/18
//Last Editied: 10/30/18

//Version 2.0

/* This class is the main class of the server
 *
 * The job of the server is to control and create all of the individual user threads
 * The server keeps an array of size n of users
 * When a user joins the server, it will notify all the previously created threads
 * the server checks every loop for a user that has exited, will delete the thread, and notify the other threads
 */

//Imported Classes
import java.io.*;
import java.net.*;
import java.util.*;

class Server{

	//inmutable class memebers
	private final int PORT = 1234;
	private final int MAX_USERS = 50;

	// class members
	private ServerSocket server_socket;
	private Socket new_connection;
	private User[] users;
		

	/*
 	* Server Constructor
 	*
 	* This constructor initalizes the server at given port and ip
 	* creats an array of users of size given 
 	* 
 	* it then calls the method listenForConnection
 	*/
 	
	public Server(){

		//init rooms, users and server_socket
		try{
			System.out.println("Createing server on Port: " + PORT);
			server_socket = new ServerSocket(PORT);
			users = new User[MAX_USERS]; 

		}catch(Exception e){

			e.printStackTrace();

		}//end try
		
		listenForConnection();

	}//end Server Constructor

	/*
 	 * listen for connections
 	 *
 	 * this is the main while loop of the program
 	 *
 	 * This function listens for a connection
 	 * When a connection is established, it then adds a new user
 	 * loops back to listneing for a connection 
 	 *
 	 */ 
	public void listenForConnection(){

		while(true){

			try{				

				//listen for connection
				System.out.println("Listening for connection...");
				new_connection = server_socket.accept();
				System.out.println("Connection established...");
				
				closeConnections();
			        //create new user, notify all threads of creation
			 	addUser();			

			}catch(Exception e){
			
				e.printStackTrace();
			
			}//end try

		}//end while

	}//end listenForConnection

	/*
 	 * addUser method
 	 *
 	 * This method adds a user to the user array for access
 	 * It then calls the notifyUsers method and starts the thread
 	 */ 
	public void addUser(){

	User temp = null;	

		for(int i = 0; i < MAX_USERS; i++){

			if(users[i] == null){

				users[i] = new User(new_connection, users);
				temp = users[i];
				users[i].start();
				break;
			
			}//end if

		}//end for	

		notifyUsers(temp);					
	}//end addUser

	/*
 	 * notifyUsers Method
 	 *
 	 * Notifys all other user threads of creation of new users
 	 * calls user class addUser method
 	 */ 
	public void notifyUsers(User user){

		for(int i = 0; i < MAX_USERS; i++){
			
			if(users[i] != null){

				users[i].addUser(user);
				break;	
	
			}//end if 

		}//end for
	
	}//end notify

	public void closeConnections(){

		System.out.println("Before loop");
		for(int i = 0; i < MAX_USERS; i++){

			
			if(users[i] != null){

				System.out.println(users[i].hasUserExited());
				if(users[i].hasUserExited()){
							
					users[i].deleteUser(users[i].getUserName());
					users[i] = null;
					System.out.println("Successfull Deletion");

				}//end if

			}//end if	

		}//end for

	}//end close connections

	public static void main(String[] args){

		Server server = new Server();

	}//end main

}//end Class Server
