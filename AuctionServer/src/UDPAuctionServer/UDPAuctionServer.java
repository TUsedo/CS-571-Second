<<<<<<< HEAD
package UDPAuctionServer;

public class UDPAuctionServer {	
	static String [] seller = new String[]{"Seller"};
	static String [] buyer = new String[]{"Alice","Amy","Bob","Dave","Pam","Tom"};
	static boolean connection = false;	
	public static void main(String[] args) {
		new SellerSocket().start();
		new BuyerSocket().start();
	}
	public static boolean validateseller(String id)
	{	
	for(int i=0;i<seller.length;i++)
	{
		if(seller[i].equalsIgnoreCase(id))
			connection = true;
	}
		return connection;
	}	
	public static boolean validatebuyer(String id)
	{	
	for(int i=0;i<buyer.length;i++)
	{
		if(buyer[i].equalsIgnoreCase(id))
			connection = true;
	}
		return connection;
	}
}

=======
package UDPAuctionServer;


public class UDPAuctionServer {
	
	static String [] seller = new String[]{"Seller"};
	static String [] buyer = new String[]{"Alice","Amy","Bob","Dave","Pam","Tom"};
	static boolean connection = false;
	
	public static void main(String[] args) {

		new SellerSocket().start();
		new BuyerSocket().start();
	}

	public static boolean validateseller(String id)
	{	
	for(int i=0;i<seller.length;i++)
	{
		if(seller[i].equalsIgnoreCase(id))
			connection = true;
	}
		return connection;
	}
	
	public static boolean validatebuyer(String id)
	{	
	for(int i=0;i<buyer.length;i++)
	{
		if(buyer[i].equalsIgnoreCase(id))
			connection = true;
	}
		return connection;
	}
}

>>>>>>> 108246bef810bb18d00f79005017861b50d9c9ca
