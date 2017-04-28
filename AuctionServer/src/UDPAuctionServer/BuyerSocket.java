package UDPAuctionServer;

import java.io.*;
import java.net.*;


public class BuyerSocket extends Thread
{
	BuyerSocket(){}
	
	public void run(){
		try{
			int buyer_port = 6001;
			DatagramSocket buyersocket = new DatagramSocket(buyer_port);
			byte  buyerbuffer [];
			//byte  bidderbuffer [];
			System.out.println("Waiting for the Buyer Client to Respond");
			while(true)
			{
				buyerbuffer = new byte[1500];
				DatagramPacket serverreader = new DatagramPacket(buyerbuffer,buyerbuffer.length);
				buyersocket.receive(serverreader);
				//bidderbuffer = new byte[1500];
				new BuyerThread(buyersocket,serverreader).start();
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}


	}
}