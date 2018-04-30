package UDPAuctionServer;

import java.net.*;

public class BuyerThread extends Thread
{
	String command, userid, sellercommand;
	String [] cbrake;

	boolean connection = false;
	boolean addition = true;
	boolean removal = false;
	DatagramSocket buyersocket;
	DatagramPacket serverreader;
	DatagramPacket serverwriter;
	DatagramSocket biddersocket;
	DatagramPacket bidderreader;
	byte receivebuffer[] = new byte[1500];
	byte sendbuffer [] = new byte[1500];
	String bufferreader;

	public BuyerThread(DatagramSocket s, DatagramPacket p)
	{
		this.buyersocket = s;
		this.serverreader = p;
		//this.bidderreader = bidread;
	}

	public void run()
	{
		try
		{
			receivebuffer = serverreader.getData();
			InetAddress buyeraddress = serverreader.getAddress();
			int buyerport = serverreader.getPort();

			bufferreader = new String(receivebuffer);
			bufferreader = bufferreader.trim();
			command = bufferreader;
			cbrake = command.split(" ");

			if(cbrake[0].equalsIgnoreCase("login"))
			{	
				userid = cbrake[1];				
				if(UDPAuctionServer.validatebuyer(userid))
				{
					connection = true;
					sendbuffer = new String("Successful").getBytes();
					serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
					buyersocket.send(serverwriter);
					sendbuffer = new String(userid+ " You are now Connected to the Auction Server").getBytes();
					serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
					buyersocket.send(serverwriter);
				}
				else
				{
					sendbuffer = new String("UnSuccessful").getBytes();
					serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
					buyersocket.send(serverwriter);
					sendbuffer = new String(userid+" has Failed to connect to the Server As it is not a valid User").getBytes();
					serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
					buyersocket.send(serverwriter);
					System.out.println(userid+" is a invalid User");
					buyersocket.close();
				}
			}
			else
			{
				sendbuffer = new String("UnSuccessful").getBytes();
				serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
				buyersocket.send(serverwriter);
				sendbuffer = new String("Failed to Execute Your Request As You are not Logged into the Server(Connection Terminated)").getBytes();
				serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length,buyeraddress,buyerport);
				buyersocket.send(serverwriter);
				buyersocket.close();
			}
			if(connection)
			{

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}

}


