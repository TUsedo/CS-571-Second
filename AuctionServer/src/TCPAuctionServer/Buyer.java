package TCPAuctionServer;
import java.net.*;
import java.io.*;
public class Buyer {
	public static void main(String[] args) throws Exception {		
		String readin;
		Integer buyer_port = 6000;
		System.out.println("This is Buyer Program");
		InetAddress serveraddress = InetAddress.getLocalHost();
		try{
			Socket tcpbuyer = new Socket(serveraddress,buyer_port);
			new Bidder(serveraddress,buyer_port).start();
			PrintWriter serverwriter = new PrintWriter(tcpbuyer.getOutputStream(),true);
			BufferedReader serverreader = new BufferedReader(new InputStreamReader(tcpbuyer.getInputStream()));
			BufferedReader consolereader = new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Please Enter The First Command for the Server");
			readin = consolereader.readLine();
			serverwriter.println(readin);

			if(Boolean.parseBoolean(serverreader.readLine())) {
				System.out.println(serverreader.readLine());
				while(tcpbuyer.isConnected()) {
					System.out.println(serverreader.readLine());
					readin = consolereader.readLine();
					if(readin.equalsIgnoreCase("exit"))
						System.exit(0);
					System.out.println("You are going to Execute "+readin+" on the Server");
					serverwriter.println(readin);
					System.out.println("Server's Reply :-  " +serverreader.readLine());
				}				
				}
			else {
				System.out.println(serverreader.readLine());
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
	