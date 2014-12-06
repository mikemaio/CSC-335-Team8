//package ClientServer;



import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect {
	private Server owner;
	
	// -- the socket that waits for client connections
	private ServerSocket serversocket;
	
	// -- the port number used for client communication
	private final int PORT = 8000;

	public Connect(Server owner) {
		super();

		this.owner = owner;
		
		try {
			// -- open the server socket
			serversocket = new ServerSocket(this.owner.getPort());
			
			// -- listen for a connection request on the server socket
			listen();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}		
	}

	private void listen ()
	{
		// -- server runs until we manually shut it down
		while (true) {
			try {
				// -- block until a client comes along
				Socket socket = serversocket.accept();
				
				owner.peerconnection(socket);
								
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
