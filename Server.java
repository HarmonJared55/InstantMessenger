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

		//init rooms and users
		//call the run method

	}//end Server Constructor

	public void run(){

		while(true){
			
			try{
			
				server_socket = new ServerSocket(PORT);
				listenForConnection();		

			}catch(Exception e){
				
				System.out.println("Server Creation Failed...");	

			}//end try

		}//end while

	}//end run 

	public int menu(){

	}//end menu

	public void listenForConnection(){

		try{

			new_conneciton = server_socket.accept();

		}catch(Exception e){
	
			System.out.println("No conneciton...");
		}//end try

	}//end listenForConnection

	public static void main(String[] args){

		Server s = new Server();
		s.listenForConneciton();
		
	}//end main test	
}//end Class Server
