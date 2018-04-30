package TCPAuctionServer;

import java.net.*;
import java.io.*;

public class Seller {
	public static void main(String[] args) throws Exception {
		String readin;
		Integer seller_port = 5000;
		System.out.println("This is Seller Program");
		InetAddress serveraddress = InetAddress.getLocalHost();
		try{
			Socket tcpseller = new Socket(serveraddress,seller_port);
			PrintWriter serverwriter = new PrintWriter(tcpseller.getOutputStream(),true);
			BufferedReader serverreader = new BufferedReader(new InputStreamReader(tcpseller.getInputStream()));
			BufferedReader consolereader = new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Please Enter The First Command for the Server");
			readin = consolereader.readLine();
			serverwriter.println(readin);

			if(Boolean.parseBoolean(serverreader.readLine())) {
				System.out.println(serverreader.readLine());
				while(tcpseller.isConnected()) {
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