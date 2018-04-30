<<<<<<< HEAD
  package UDPAuctionServer;
import java.net.*;
public class SellerSocket extends Thread
{
	SellerSocket(){}
	public void run()
	{
		try{
			int seller_port = 5001;
			DatagramSocket sellersocket = new DatagramSocket(seller_port);
			byte frombuffer [];
			System.out.println("Waiting for the Seller Client to Respond");
			while(true)
			{
				frombuffer = new byte [500];
				DatagramPacket serverreader = new DatagramPacket(frombuffer, frombuffer.length);
				sellersocket.receive(serverreader);
				new SellerThread(sellersocket, serverreader).start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
=======
package UDPAuctionServer;

import java.net.*;

public class SellerSocket extends Thread
{
	SellerSocket(){}

	public void run()
	{
		try{
			int seller_port = 5001;
			DatagramSocket sellersocket = new DatagramSocket(seller_port);
			byte frombuffer [];
			System.out.println("Waiting for the Seller Client to Respond");
			while(true)
			{
				frombuffer = new byte [500];
				DatagramPacket serverreader = new DatagramPacket(frombuffer, frombuffer.length);
				sellersocket.receive(serverreader);
				new SellerThread(sellersocket, serverreader).start();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
>>>>>>> 108246bef810bb18d00f79005017861b50d9c9ca
}