package UDPAuctionServer;

import java.util.ArrayList;

public class ItemList 
{
	public static ArrayList<Integer> itemnum = new ArrayList<Integer>(); 
	public static ArrayList<String> itemname= new ArrayList<String>();
	public static ArrayList<Integer> bidamount = new ArrayList<Integer>();
	public static ArrayList<String> hbidder = new ArrayList<String>();
	public static ArrayList<String> listitems = new ArrayList<String>();
	
	public static void additem(Integer i, String name, Integer amt, String hname)
	{
		itemnum.add(i);
		itemname.add(name);
		bidamount.add(amt);
		hbidder.add(hname);
		String tempi = Integer.toString(i);
		String amount = Integer.toString(amt);
		String listitem = ("INum:-"+tempi+" IName:-"+name+" HBid:-"+amount+" BName:-"+hname);
		listitems.add(listitem);
	}
	
	
}