//MessageList.java

//Created on: 12/1/18
//Last Edit:  12/1/18

//Author: Jared Harmon
//Version 1.0

/*
 * This Class implements a linked list of messages called MessageList
 *
 * This class allows for the last 100 messages sent on the server to be stored
 * These messages are persistant through the use of the serializable interface
 */

import java.io.*;

class MessageList implements Serializable{

	private static final int MAX_MESSAGES = 100;
	private static Message head;
	private static int num_of_messages;
	
	public MessageList(){

		num_of_messages = 0;

	}//end MessageList()

	public void addMessage(String message){
		
		if(head == null){

			//head is null
			head = new Message(message,null);
			num_of_messages++;
		}else{

			//head is not null
			if(num_of_messages < MAX_MESSAGES){

				//list not full
				Message temp = new Message(message,null);
				Message current = head;
				
				while(current.next() != null){

					current = current.next();

				}//end while

				num_of_messages++;
				current.setNext(temp);
	
			}else{
				
				//list is full
				//add message to end of linked list and cut off the tail
				Message temp = new Message(message,null);
				Message current = head;

				while(current.next() != null){

					current = current.next();

				}//end while			

				current.setNext(temp);
				head = head.next();
				num_of_messages++;
				
			}//end if

		}//end if

	}//end addMessage

	public void listMessages(PrintWriter out){

		Message current = head;
		
		while(current != null){
			
			out.println("<" + current.getUser() + "> " + current.getMessage());
			current = current.next();

		}//end while

	}//end listMessages
	

	public void listMessages(PrintWriter out,String user_name){

		Message current = head;
		
		while(current != null ){
			
			if(current.getUser().equalsIgnoreCase(user_name.substring(1))){

				out.println("<" + current.getUser() + "> " + current.getMessage());
				current = current.next();

			}//end if
		}//end while

	}//end listMessages
		


	
}//end MessageList
