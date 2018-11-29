//User.java

//Author: Jared Harmon
//File Created: 10/29/18
//Last Edited : 10/29/18

/* This file is the implementaion of the user class
 *
 * It allows for a encapsulated storage of all of the data needed to facilitate easy
 * communication and allow easy transfer of user data
 *
 */

//imported classes
import java.io.*;
import java.net.*;

public class User{

	//final member variables
	//for this project the host and port will never change
	//can be easily extened to allow for actual server hosting
	private final int PORT = 1234;
	private final String IP = "127.0.0.1";

	//member varibales
	private String user_name;
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;

	
	public User(String user_name){

		//set username
		this.user_name = user_name;

		//set up input and output streams
		try{
		
			System.out.println("Connecting to server...");
				
			connection = new Socket(IP,PORT);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(),true); 

			System.out.println("User "+ user_name + " created, connected to server at IP: " + 
						IP + " on Port: " + PORT);

		}catch(Exception e){

			System.out.println("Failed to create user!");		
	
		}//end try

		
	}//end User Constructor

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

	/* User Test
 	 * Creates user and tests all the functions
 	 * Expected output
 	 * 	"Connecting to server..."
 	 * 	"User jar545  created, connected to server at IP: 127.0.0.1 on Port: 1234"
 	 * 	"jar545"
 	 * 	"thatjarhead"
 	 */

	public static void main(String[] args){

		User u = new User("jar545");
		System.out.println(u.getUserName()); 		
		u.setUserName("thatjarhead");
		System.out.println(u.getUserName()); 		

	}//end main

}//end class User

















