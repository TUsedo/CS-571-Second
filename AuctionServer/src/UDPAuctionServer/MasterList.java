<<<<<<< HEAD

package UDPAuctionServer;
import java.net.*;
import java.util.*;
public class MasterList 
{	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<DatagramSocket> soc = new ArrayList<DatagramSocket>();
	public static void adduser(String n, DatagramSocket s)
	{	name.add(n);
		soc.add(s);
	}    }
=======
package UDPAuctionServer;


import java.net.*;
import java.util.*;

public class MasterList 
{
	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<DatagramSocket> soc = new ArrayList<DatagramSocket>();
	
	public static void adduser(String n, DatagramSocket s)
	{
		name.add(n);
		soc.add(s);
	}
}
>>>>>>> 108246bef810bb18d00f79005017861b50d9c9ca
