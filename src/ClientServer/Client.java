//package ClientServer;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Client {

	// -- port and host name of server
	private final int PORT = 8000;
	
	/*
	 From ipconfig:
	 
	 Wireless LAN adapter Wireless Network Connection:

     Connection-specific DNS Suffix  . : clunet.edu
     Link-local IPv6 Address . . . . . : fe80::1083:3e22:f5a1:a3ec%11
     IPv4 Address. . . . . . . . . . . : 199.107.222.115 <=======This address works
     Subnet Mask . . . . . . . . . . . : 255.255.240.0
     Default Gateway . . . . . . . . . : 199.107.210.2
	 */
    private final String HOST = "localhost";//"199.107.222.115";//"localhost";//"127.0.0.1";
	// -- the actual host IP address of the machine can
	//    be found using ipconfig from a command console
	// private final String HOST = "192.168.20.4";
	
	// -- socket variable for peer to peer communication
	private Socket socket;

	// -- stream variables for peer to peer communication
	//    to be opened on top of the socket
	private BufferedReader datain;
	private DataOutputStream dataout;
	private GUIRoot _guiRoot;
	//loging values sent from connection thread
	private String flowValues = "";
	//user values
	private int usersConnected;
	
	// -- set the gmail host URL
	final static private String host = "smtp.gmail.com";

	// -- You must have a valid gmail username/password pair to use
	// gmail as a SMTP service
	final static private String username = "CLUCSC335";
	final static private String password = "CLUC$C335";
	
	//Username character min value
	public static final int USERMIN = 4;
	  	
	public String getflowValues() {
		return flowValues;
	}

	public void setUserQueryValue(int users)
	{
		this.usersConnected = users;
	}
	public int getUserQueryValue()
	{
		return usersConnected;
	}
	public void setflowValues(String Values) {
		this.flowValues = Values;
	}


	public Client (GUIRoot gr)
	{
		_guiRoot = gr;
		try {
			System.out.print("Connect:");
			// -- construct the peer to peer socket
			socket = new Socket(HOST, PORT);
			// -- wrap the socket in stream I/O objects
			datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dataout = new DataOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			System.out.println("Host " + HOST + " at port " + PORT + " is unavailable.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Unable to create I/O streams.");
			System.exit(1);
		}
		
	}
	
	
	public String sendString (String _msg, String account, String password, String email )
	{
		String rtnmsg = "";
		try {
			// -- the server only receives String objects that are
			//    terminated with a newline \n"
			// -- send the String making sure to flush the buffer
			dataout.writeBytes(_msg + "," + account+ "," + password + "," + email + "\n");
			dataout.flush();
			
			// -- receive the response from the server
			//    The do/while makes this a blocking read. Normally BufferedReader.readLine() is non-blocking.
			//    That is, if there is no String to read, it will read "". Doing it this way does not allow
			//    that to occur.
			rtnmsg = "";
			do {
				
				rtnmsg = datain.readLine();
				//put into if statements for potential future use
				String delims = "[,]";
				String recievedData[] = rtnmsg.split(delims);
				if(recievedData[0].equals("success") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("invalid") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("missing") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("locked") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("exists") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("created") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("failedpasschange") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}
				else if(recievedData[0].equals("oldpasswordfail") && recievedData[1].equals("account"))
				{
					flowValues = rtnmsg;
				}	
				else if(recievedData[0].equals("users"))
				{
					usersConnected = Integer.parseInt(recievedData[1]); 
				}
				else
				{
					System.out.println("Client Error");
				}
			} while (rtnmsg.equals(""));
						
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return rtnmsg;
		
	}
	
	public void disconnect ()
	{
    	String text = "DiScOnNeCt";
		try {
			// -- the server only receives String objects that are
			//    terminated with a newline "\n"

        	// -- send a special message to let the server know 
        	//    that this client is shutting down
			text += "\n";
			dataout.writeBytes(text);
			dataout.flush();

			// -- close the peer to peer socket
			socket.close();
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public static void sendEmail(String to, String content) {
		// -- set up host properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");

		// -- Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		// -- Set up the sender's email account information
		String from = "CLUCSC335@gmail.com";
				
		try {
			// -- Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// -- Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// -- Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// -- Set Subject: header field
			message.setSubject("CSC335 Project");

			// Now set the actual message
			message.setText(content);

			// -- Send message
			// -- use either these three lines or...
			// Transport t = session.getTransport("smtp");
			// t.connect();
			// t.sendMessage(message, message.getAllRecipients());
			
			// -- .. this one (which ultimately calls sendMessage(...)
			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void main(String[] args) {
		// -- instantiate a Client object
		//    the constructor will attempt to connect to the server
		//Client client = new Client();
		
		// -- send message to server and receive reply.
	/*	String commandString = "hello";
		System.out.println("CLIENT send:  " + commandString);
		//String replyString = client.sendString(commandString);
		//System.out.println("CLIENT receive: " + replyString);
		
		for (int i = 0; i < 60; ++i) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			commandString = "hello";
			System.out.println("CLIENT send:  " + commandString);
			replyString = client.sendString(commandString);
			System.out.println("CLIENT receive: " + replyString);
		}

		client.disconnect();**/
	}

}
