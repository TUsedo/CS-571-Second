package TCPAuctionServer;

import java.net.*;
import java.io.*;

public class BuyerSocket extends Thread{
	ServerSocket buyerserver = null;
	Socket  buyerclient = null;
	Socket biddersocket = null;
	BuyerSocket(){}
	public void run(){
		try{
		  boolean b = true;
			buyerserver = new ServerSocket(6000);
			System.out.println("Waiting for the Buyer Client to Respond");
			while(b)
			{	
				buyerclient = buyerserver.accept();
				System.out.println("A New Buyer Client has Joined");
				biddersocket = buyerserver.accept();
				new BuyerThread(buyerclient,biddersocket).start();
			}	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}