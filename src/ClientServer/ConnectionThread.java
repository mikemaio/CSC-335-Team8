package ClientServer;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;



public class ConnectionThread extends Thread {
	private boolean go;
	private String name;
	private int id;
	private DBaseConnection db = new DBaseConnection();
	private GUIRoot gr;
	//Panels to Use
	private String[] args = {};	
	//3306
	// -- the main server (port listener) will supply the socket
	//    the thread (this class) will provide the I/O streams
	//    BufferedReader is used because it handles String objects
	//    whereas DataInputStream does not (primitive types only)
	private BufferedReader datain;
	private DataOutputStream dataout;
	
	// -- this is a reference to the "parent" Server object
	//    it will be set at time of construction
	private Server server;
	



	public ConnectionThread (int id, Socket socket, Server server) 
	{
		this.server = server;
		this.id = id;
		this.name = Integer.toString(id);
		go = true;
		
		// -- create the stream I/O objects on top of the socket
		try {
			datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dataout = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	public String toString ()
	{
		return name;
	}
	
	public String getname ()
	{
		return name;
	}
	
	public void Stop () {
		go = false;
	}
	

	public void run () {
		// -- server thread runs until the client terminates the connection
		//put if from send string
		while (go) {
			try {
				// -- always receives a String object with a newline (\n)
				//    on the end due to how BufferedReader readLine() works.
				//    The client adds it to the user's string but the BufferedReader
				//    readLine() call strips it off
				String txt = datain.readLine();
				String delims = "[,]";
				String recievedData[] = txt.split(delims); 
				//System.out.println("SERVER receive: " + txt);
				// -- if it is not the termination message, send it back adding the
				//    required (by readLine) "\n"

				// -- if the special "termination" string is received then 
				//    close the socket, remove this thread object from the
				//    server's active client thread list, and terminate the thread
				//db.main(args);
				if (txt.equals("DiScOnNeCt")) {
					datain.close();
					server.removeID(id);
					go = false;
				}
				else if (recievedData[0].equals("loginprocedure")) 
				{
					//dataout.writeBytes(":)" + "/n");
					db.setSelection(2);
					db.executeLogin(recievedData[1], recievedData[2]);
					dataout.writeBytes("Sent Hello" + "\n");
					dataout.flush();
					//dataout.flush();
					
					
				
				}
				else if (txt.equals("register"))
				{
					db.setSelection(2);
					db.executeQ();
					dataout.writeBytes("Sent Hello" + "\n");
					dataout.flush();
					
					
				}
				else {
					System.out.println("unrecognized command >>" + txt + "<<");
					dataout.writeBytes(txt + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
				go = false;
			}
			
		}
	}


}
