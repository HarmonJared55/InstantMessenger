instantmessenger: Server.java User.java Client.java Message.java MessageList.java
	javac Server.java User.java Client.java Message.java MessageList.java

runserver: 
	java Server
	
runuser:
	java User

clean: 
	rm *.class
