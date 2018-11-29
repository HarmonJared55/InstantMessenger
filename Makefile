instantmessenger: Server.java User.java
	javac Server.java User.java

runserver: 
	java Server
	
runuser:
	java User

clean: 
	rm *.class
