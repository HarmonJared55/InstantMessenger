//Server.java

//Author: Jared Harmon
//File Created: 10/27/18
//Last Editied: 10/29/18

/* This class is the main class of the server
 *
 * It serves two main functions
 *
 * 	1: It listens for clients trying to connect, prompts them to enter a username
 * 	    then moves them to their requested room
 *
 * 	2: Has main control over the program allowing for creation/deletion of 
 * 	   rooms and deletion of users.
 */

//Imported Classes
import java.io.*;
import java.net.*;
import java.util.*;

class Server implements Runnable{

	//inmutable class memebers
	private final int PORT = 1234;

	//mutable class members
	private ServerSocket server_socket;
	private Socket new_connection;
	//private Arraylist<User> users;	
	//private Arraylist<Room> rooms;
	private int num_of_users;

	public Server(){

		//init rooms, users and server_socket
		try{

			server_socket = new ServerSocket(PORT);

		}catch(Exception e){
		
			System.out.println("Failed to create sever socket");		

		}//end try

		run();
	}//end Server Constructor

	public void run(){

		while(true){
			
				listenForConnection();		

		}//end while

	}//end run 

	public int menu(){

		return 0;

	}//end menu

	public void listenForConnection(){

		try{
			System.out.println("Listening for connection...");
			new_connection = server_socket.accept();
			System.out.println("Connection established...");
		}catch(Exception e){
	
			System.out.println("No conneciton...");
		}//end try

	}//end listenForConnection

	public static void main(String[] args){

		Server s = new Server();
		
	}//end main test	
}//end Class Server
