//Client.java

//File Created: 11/30/18
//Last Edited:  11/30/18

//Version 1.0

/*
 * This class is the Client Side application
 *
 * This class implements everything that is need for the client
 *
 * Current Build-
 * 	
 * 		This current build is as simple as can be to allow for testing of server and user clases, will add gui later on
 *
 * ...TBD
 */

//imported classes
import java.util.*;
import java.io.*;
import java.net.*;

public class Client{

	//imutable member variables
	private final String IP = "127.0.0.1";	
	private final int PORT = 1234;

	//member variables
	private Socket connection;
	private BufferedReader in;
	private PrintWriter out;

	private boolean stillRunning = true;
	private Scanner input = new Scanner(System.in);

	/*
  	 * Client Constructor
  	 *
  	 * Inits the connnection, input and output streams, and starts the recieving thread
  	 */ 
	public Client(){

		try{
			System.out.println("Connecting to Server..");
			connection = new Socket(IP,PORT);
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			out = new PrintWriter(connection.getOutputStream(),true);		
			
			Thread message_reciving_thread = new Thread(){
				
				
				public void run(){

					String message;
					while(stillRunning == true){
							
						try{	

							message = in.readLine();
							System.out.println(message);

						}catch(Exception e){
	
							System.out.println("Failed to read in message");

						}//end try
					}//end while

				}//end run method

			};//end thread

			message_reciving_thread.start();

			while(stillRunning == true){
			
				String message = input.nextLine(); 
				
				if(message.equalsIgnoreCase("/exit")){
					
					stillRunning = false;
					System.out.println("GoodBye");
					System.exit(0);

				}else{
	
					out.println(message);

				}//end if

			}//end while

		}catch(Exception e){

			System.out.println("Conneciton failed");

		}//end try

	}//end Client Constructor	

	public static void main(String[] args){

		Client client = new Client();

	}//end main

}//end class Client
