package TCPAuctionServer;


import java.net.*;
import java.util.*;

public class MasterList 
{
	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<Socket> soc = new ArrayList<Socket>();
	
	public static void adduser(String n, Socket s)
	{
		name.add(n);
		soc.add(s);
	}
}
