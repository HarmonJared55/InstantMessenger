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

class MessageList serializable{

	private final int MAX_MESSAGES = 100;
	private Message head;
	private int num_of_messages;
	
	public MessageList(){

		head = null;
		num_of_messages = 0;			

	}//end MessageList Constructor	

	public static void addMessage(String message){

		if(head == null){

			//head is null
			head = new Message(message,null);

		}else{

			//head is not null
			if(num_of_messages < MAX_MESSAGES){

				//add message to front of linked list
				head = new Message(message,head);
			}else{

				//and message to front of linked list and cut off the tail
				head = new Message(message,head);
				Message current = head;
				
				while(current.next().next() != null){

					current = current.next();					

				}
					
				current.next() = null;
				
			}//end if

		}//end if

	}//end addMessage

}//end MessageList
