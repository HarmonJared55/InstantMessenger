//Message.java

//Created on: 12/1/18
//Last Edit:  12/1/18

//Author: Jared Harmon

/*
 * This Class is the implemtation of the Message Class
 * 
 * This class is intented to store a message and allows for linked list capabilities
 */

public class Message{

	private String message;
	private String user;
	private Message next;

	public Message(String message, Message next){

		user = message.substring(0,message.indexOf(" "));
		this.message = message.substring(message.indexOf(" "));
		this.next = next;	

	}//end message

	public String getMessage(){

		return message;
	
	}//end getMessage

	public String getUser(){

		return user;

	}//end getUser

	public Message next(){

		return next;

	}//end getNext()

	public void setNext(Message next){

		this.next = next;

	}//end setNext()
}//end Message class
