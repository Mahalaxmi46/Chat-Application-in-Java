import java.io.*;
import java.net.*;

class Server{
	ServerSocket server;
	Socket socket;
	
	BufferedReader br;
	PrintWriter out;
	
	public Server() {
		
		try {
			
			server = new ServerSocket(7778);
			System.out.println("Server is ready to accept connection");
			socket = server.accept();
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		startReading();
		startWriting();
	}
	
	
    private void startWriting() {
		// thread for writing msg in user console and storing the msg and sending to client
    	//lamba expression [ -> ]
    	// multithreading thread creation
		Runnable sw = ()->{        
		try {	
			while(true) {
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String content = br1.readLine();
					out.println(content);
					out.flush();
					if(content.equals("exit")) {
						System.out.println("Server terminated the chat");
						socket.close();
						break;
					}
				}
		}	
			 catch (Exception e) {
				e.printStackTrace();    //On which line of console error occur specifies
			 }
		};
		Thread t1 = new Thread(sw);
		t1.start();
	}


	private void startReading() {
		// thread for read and store msg
		Runnable sr=()->{
		try {
			while(!socket.isClosed()) {
				
					String msg = br.readLine();
					
					System.out.println("Client: " + msg);
					
					if(msg.equals("exit")) {
						System.out.println("Client terminated the chat");
						socket.close();
						break;
					}
				}
		}	
				catch (Exception e) {
					System.out.println("Connection is closed");
				}
		};
		Thread t2 = new Thread(sr);
		t2.start();
	}


	public static void main(String [] args) {
		System.out.println("This is Server... ready for connection");
		new Server();
	}
}
