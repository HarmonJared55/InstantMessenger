instantmessenger: Server.java User.java Client.java
	javac Server.java User.java Client.java

runserver: 
	java Server
	
runuser:
	java User

clean: 
	rm *.class
