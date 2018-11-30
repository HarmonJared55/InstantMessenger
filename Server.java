//Server.java

//Author: Jared Harmon
//File Created: 10/27/18
//Last Editied: 10/30/18

//Version 1.0

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
	private final int MAX_USERS = 10;

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

			server_socket = new ServerSocket(PORT);
			users = new User[MAX_USERS]; 

		}catch(Exception e){

			System.out.println("Failed to create sever socket");		

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

			        //create new user, notify all threads of creation
			 	addUser();			

			}catch(Exception e){
				System.out.println("No conneciton...");
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

		for(int i = 0; i < MAX_USERS; i++){

			if(users[i] == null){

				users[i] = new User(new_connection, users);
				notifyUsers(users[i]);					
				users[i].start();
				break;
			
			}//end if

		}//end for	

	}//end addUser

	/*
 	 * notifyUsers Method
 	 *
 	 * Notifys all other user threads of creation of new users
 	 * calls user class addUser method
 	 *
 	 * Does not call adduser on itsself becuase it will already have a pointer to itsself in its array of users
 	 */ 
	public void notifyUsers(User user){

		for(int i = 0; i < MAX_USERS; i++){
			
			if(users[i] != null){

				users[i].addUser(user);

			}//end if 

		}//end for
	
	}//end notify

	public static void main(String[] args){

		Server server = new Server();

	}//end main

}//end Class Server
