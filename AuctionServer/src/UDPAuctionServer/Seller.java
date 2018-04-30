<<<<<<< HEAD
package UDPAuctionServer;

import java.io.*;
import java.net.*;

public class Seller {
	public static void main(String[] args) throws Exception {
		int seller_port = 5001;
		String readin;
		byte receivebuffer[] = new byte[1500];
		byte sendbuffer [] = new byte[1500];
		DatagramPacket sellerwriter;
		DatagramPacket sellerreader;
		String bufferreader;
		
		System.out.println("This is Seller Program");
		InetAddress serveraddress = InetAddress.getByName("localhost");
		try
		{
			DatagramSocket udpseller = new DatagramSocket();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter The First Command for the Server");

			readin  = in.readLine();
			sendbuffer = readin.getBytes();
			sellerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,seller_port);
			udpseller.send(sellerwriter);

			sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
			udpseller.receive(sellerreader);
			bufferreader = new String(receivebuffer);
			bufferreader = bufferreader.trim();

			if(bufferreader.equalsIgnoreCase("Successful"))
			{
				sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(sellerreader);
				bufferreader = new String(receivebuffer);
				bufferreader = bufferreader.trim();
				System.out.println(bufferreader);
				while(true)
				{
					sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(sellerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println(bufferreader);

					readin = in.readLine();
					System.out.println("You are going to Execute "+readin+" on the Server");
					sendbuffer = readin.getBytes();
					sellerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,seller_port);
					udpseller.send(sellerwriter);

					sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(sellerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println("Server's Reply :- "+bufferreader);	
				}
			}
			else
			{
				sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(sellerreader);
				bufferreader = new String(receivebuffer);
				bufferreader = bufferreader.trim();
				System.out.println(bufferreader);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
=======
package UDPAuctionServer;

import java.io.*;
import java.net.*;

public class Seller {



	public static void main(String[] args) throws Exception {

		int seller_port = 5001;
		String readin;
		byte receivebuffer[] = new byte[1500];
		byte sendbuffer [] = new byte[1500];
		DatagramPacket sellerwriter;
		DatagramPacket sellerreader;
		String bufferreader;
		
		System.out.println("This is Seller Prgram");
		InetAddress serveraddress = InetAddress.getByName("localhost");
		try
		{
			DatagramSocket udpseller = new DatagramSocket();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter The First Command for the Server");

			readin  = in.readLine();
			sendbuffer = readin.getBytes();
			sellerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,seller_port);
			udpseller.send(sellerwriter);

			sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
			udpseller.receive(sellerreader);
			bufferreader = new String(receivebuffer);
			bufferreader = bufferreader.trim();

			if(bufferreader.equalsIgnoreCase("Successful"))
			{
				sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(sellerreader);
				bufferreader = new String(receivebuffer);
				bufferreader = bufferreader.trim();
				System.out.println(bufferreader);
				while(true)
				{
					sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(sellerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println(bufferreader);

					readin = in.readLine();
					System.out.println("You are going to Execute "+readin+" on the Server");
					sendbuffer = readin.getBytes();
					sellerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,seller_port);
					udpseller.send(sellerwriter);

					sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(sellerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println("Server's Reply :- "+bufferreader);	
				}
			}
			else
			{
				sellerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(sellerreader);
				bufferreader = new String(receivebuffer);
				bufferreader = bufferreader.trim();
				System.out.println(bufferreader);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
>>>>>>> 108246bef810bb18d00f79005017861b50d9c9ca
