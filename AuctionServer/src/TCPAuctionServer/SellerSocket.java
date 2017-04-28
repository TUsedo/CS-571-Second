package TCPAuctionServer;

import java.net.*;
import java.io.*;

public class SellerSocket extends Thread{
	ServerSocket sellerserver = null;
	Socket  sellerclient = null;
	SellerSocket(){}
	public void run(){
		try{
			boolean b = true;
			sellerserver = new ServerSocket(5000);
			System.out.println("Waiting for the Seller to Respond");
			while(b) {	
				sellerclient = sellerserver.accept();
				System.out.println("A New Seller Client has Joined");
				new SellerThread(sellerclient).start();
			}	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}