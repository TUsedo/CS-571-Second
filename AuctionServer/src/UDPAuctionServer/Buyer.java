package UDPAuctionServer;

import java.net.*;
import java.io.*;

public class Buyer {



	public static void main(String[] args) throws Exception {
		
		int buyer_port = 6001;
		String readin;
		byte receivebuffer[] = new byte[1500];
		byte sendbuffer [] = new byte[1500];
		DatagramPacket buyerwriter;
		DatagramPacket buyerreader;
		String bufferreader;
		
		System.out.println("This is Buyer Program");
		InetAddress serveraddress = InetAddress.getLocalHost();
		
		try
		{
			DatagramSocket udpseller = new DatagramSocket();
			//new Bidder(serveraddress,buyer_port).start();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Please Enter The First Command for the Server");

			readin  = in.readLine();
			sendbuffer = readin.getBytes();
			buyerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,buyer_port);
			udpseller.send(buyerwriter);

			buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
			udpseller.receive(buyerreader);
			bufferreader = new String(receivebuffer);
			bufferreader = bufferreader.trim();

			if(bufferreader.equalsIgnoreCase("Successful"))
			{
				buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(buyerreader);
				bufferreader = new String(receivebuffer);
				bufferreader = bufferreader.trim();
				System.out.println(bufferreader);
				while(true)
				{
					buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(buyerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println(bufferreader);

					readin = in.readLine();
					System.out.println("You are going to Execute "+readin+" on the Server");
					sendbuffer = readin.getBytes();
					buyerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress,buyer_port);
					udpseller.send(buyerwriter);

					buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
					udpseller.receive(buyerreader);
					bufferreader = new String(receivebuffer);
					bufferreader = bufferreader.trim();
					System.out.println("Server's Reply :- "+bufferreader);	
				}
			}
			else
			{
				buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
				udpseller.receive(buyerreader);
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
