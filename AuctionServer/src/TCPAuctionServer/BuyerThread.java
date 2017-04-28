package TCPAuctionServer;

import java.net.Socket;
import java.io.*;

public class BuyerThread extends Thread {
	private Socket buyersocket;
	private Socket biddersocket;
	String command, userid, buyercommand;
	String [] cbrake;

	boolean connection = false;


	public BuyerThread(Socket b, Socket bid)
	{
		this.buyersocket = b;
		this.biddersocket = bid;
	}
	public void run()
	{

		try
		{
			BufferedReader buyerreader = new BufferedReader(new InputStreamReader(buyersocket.getInputStream()));
			PrintWriter buyerwriter = new PrintWriter(buyersocket.getOutputStream(),true);
			command = buyerreader.readLine();
			cbrake = command.split(" ");


			if(cbrake[0].equalsIgnoreCase("login"))
			{
				userid = cbrake[1];
				if(TCPAuctionServer.validatebuyer(userid))
				{
					connection = true;
					buyerwriter.println(connection);
					buyerwriter.println(userid+ " You are now Connected to the Auction Server");
					System.out.println(userid+" has been Succesfully Connected to the Server");
				}
				else
				{
					buyerwriter.println(connection);
					buyerwriter.println(userid+" has Failed to connect to the Server As it is not a valid User");
					System.out.println(userid+" is a invalid User");
					buyersocket.close();
				}
			}
			else
			{
				buyerwriter.println(connection);
				buyerwriter.println("Failed to Execute Your Request As You are not Logged into the Server(Connection Terminated)");
				buyersocket.close();
			}

			if(connection)
			{
				MasterList.adduser(userid,biddersocket);

				while(buyersocket.isConnected())
				{
					buyerwriter.println("Please Enter the Command You want to Execute on the Server");
					buyercommand = buyerreader.readLine();
					if(buyercommand.length()== 0)
						buyerwriter.println("Please Enter a command");
					else
					{
						System.out.println("Received "+buyercommand+" from "+userid);
						cbrake = buyercommand.split(" ");
						command = cbrake[0];
						if (command.equalsIgnoreCase("bid"))
						{	

							if(cbrake.length == 3)
							{
								try
								{
									Integer itemnum = Integer.parseInt(cbrake[1]);
									Integer bidamount = Integer.parseInt(cbrake[2]);

									for(int index=0;index<ItemList.itemnum.size();index++)
									{	
										if(ItemList.itemnum.get(index) == itemnum)
										{	
											if(ItemList.bidamount.get(index) == 0)
											{	
												String name = ItemList.itemname.get(index);
												ItemList.bidamount.set(index, bidamount);
												ItemList.hbidder.set(index, userid);
												String listitem = ("INum:-"+itemnum+" IName:-"+name+" HBid:-"+bidamount+" BName:-"+userid);
												ItemList.listitems.set(index,listitem);
												System.out.println(userid+" is the Highest Bidder on Item "+itemnum);
												buyerwriter.println(userid+" Congratulation you are the Highest Bidder on Item "+itemnum);
											}
											else if(ItemList.bidamount.get(index) >= bidamount)
											{
												System.out.println(userid+" Your bid is not High Enough to OutBid The current Highest Bid on Item "+itemnum);
												buyerwriter.println(userid+" Your bid is not High Enough to OutBid The current Highest Bid on Item "+itemnum);
											}
											else if(ItemList.itemnum.get(index) < bidamount)
											{	
												String previousbiddername = ItemList.hbidder.get(index);
												for(int i=0;i<MasterList.soc.size();i++)
												{
													if(MasterList.name.get(i).equalsIgnoreCase(previousbiddername))
													{
														Socket previousbidder = MasterList.soc.get(i);
														PrintWriter previouswriter = new PrintWriter(previousbidder.getOutputStream(),true);
														previouswriter.println(previousbiddername+" You Have Been OutBided on Item "+itemnum+" by "+userid);
														System.out.println(previousbiddername+" has been Outbided on Item "+itemnum+" by "+userid);
													}

												}
												String name = ItemList.itemname.get(index);
												ItemList.bidamount.set(index, bidamount);
												ItemList.hbidder.set(index, userid);
												String listitem = ("INum:-"+itemnum+" IName:-"+name+" HBid:-"+bidamount+" BName:-"+userid);
												ItemList.listitems.set(index,listitem);
												System.out.println(userid+" is the Highest Bidder on Item "+itemnum);
												buyerwriter.println(userid+" Congratulation you are Now the Highest Bidder on Item "+itemnum);
											}
										}
										else
										{
											System.out.println("Item "+itemnum+" is not on the Auction List");
											buyerwriter.println(userid+" Item "+itemnum+" is not on the Auction List");
										}
									}

								}
								catch(NumberFormatException nfe)
								{
									buyerwriter.println("Invalid Bid Command Format (BID Item_Number Bid_Amount)");
									System.out.println("Invalid Bid Command Format (BID Item_Number Bid_Amount)");
								}
							}
							else
							{
								buyerwriter.println("Improper Arguments there should be exactly Two Arguments with BID Command");
								System.out.println("Improper Arguments there should be exactly Two Arguments with BID Command");
							}
						}
						else if(command.equalsIgnoreCase("list"))
						{
							buyerwriter.println("List:- "+ItemList.listitems);
							System.out.println("List:- "+ItemList.listitems);

						}
						else if(command.equalsIgnoreCase("exit"))
						{
							buyerreader.close();
							buyerwriter.close();
							buyersocket.close();
							biddersocket.close();
						}
						else
							buyerwriter.println("Invalid Command " +command+ " Please Enter a Valid Command");
					}
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}