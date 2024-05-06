import java.io.*;
import java.net.Socket;

public class Client{
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public Client() {
		try {
			socket = new Socket("127.0.0.1",7778);
			System.out.println("Connection is establish");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		startReading();
		startWriting();
	}
	 
	private void startWriting() {
		//thread
		Runnable sw = ()->{
		System.out.println("writer started..");
		try {	
			while(true) {
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				String content = br1.readLine();
				out.println(content);
				out.flush();
				if(content.equals("exit")) {
					System.out.println("Client terminated the chat");
					socket.close();
					break;
				}
			}
		}
			catch (Exception e) {
			e.printStackTrace();
			}
//			System.out.println("Connection is closed");
		};
		Thread t1 = new Thread(sw);
		t1.start();
	}

	private void startReading() {
		//thread
		Runnable sr = ()->{
			System.out.println("reader started..");
		try {
			while(!socket.isClosed()) {
				String msg = br.readLine();
				
				System.out.println("Server: " + msg);
				
				if(msg.equals("exit")) {
					System.out.println("Server terminated the chat");
					socket.close();
					break;
				}
			}
		}
				catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Connection is closed");
				}
		};
		Thread t2 = new Thread(sr);
		t2.start();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("This is Client...");
		new Client();
	}

}
