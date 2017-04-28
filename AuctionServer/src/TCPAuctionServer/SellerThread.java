package TCPAuctionServer;

import java.io.*;
import java.net.*;

public class SellerThread extends Thread {

	private Socket sellersocket;
	String command, userid, sellercommand;
	String [] cbrake;

	boolean connection = false;
	boolean addition = true;
	boolean removal = false;
	public SellerThread(Socket s){

		this.sellersocket = s;
	}

	public void run(){
		try{
			BufferedReader sellerreader = new BufferedReader(new InputStreamReader(sellersocket.getInputStream()));
			PrintWriter sellerwriter = new PrintWriter(sellersocket.getOutputStream(),true);

			command = sellerreader.readLine();
			cbrake = command.split(" ");

			if(cbrake[0].equalsIgnoreCase("login"))
			{	
				userid = cbrake[1];
				if(TCPAuctionServer.validateseller(userid))
				{
					connection = true;
					sellerwriter.println(connection);
					sellerwriter.println(userid+ " You are now Connected to the Auction Server");
					System.out.println(userid+" has been Succesfully Connected to the Server");
				}
				else
				{
					sellerwriter.println(connection);
					sellerwriter.println(userid+" has Failed to connect to the Server As it is not a valid User");
					System.out.println(userid+" is a invalid User");
					sellersocket.close();
				}
			}
			else
			{
				sellerwriter.println(connection);
				sellerwriter.println("Failed to Execute Your Request As You are not Logged into the Server(Connection Terminated)");
				sellersocket.close();
			}

			if(connection)
			{
				while(sellersocket.isConnected())
				{
					sellerwriter.println("Please Enter the Command You want to Execute on the Server");
					sellercommand = sellerreader.readLine();
					if(sellercommand.length()== 0)
						sellerwriter.println("Please Enter a command");
					else
					{
						System.out.println("Received "+sellercommand+" from "+userid);
						cbrake = sellercommand.split(" ");
						command = cbrake[0];
						if (command.equalsIgnoreCase("add"))
						{	
							addition = true;
							if(cbrake.length == 3)
							{
								try
								{
									Integer itemnum = Integer.parseInt(cbrake[1]);
									String itemname = cbrake[2];
									for(int index=0;index<ItemList.itemnum.size();index++)
									{
										if(ItemList.itemnum.get(index)== itemnum)
										{
											addition = false;
											System.out.println("Item Number Already Exists Enter some Other Item Number");
											sellerwriter.println("Item Number Already Exists Enter some Other Item Number");
										}										
									}
									if (addition)
									{
										ItemList.additem(itemnum, itemname, 0, "");	
										sellerwriter.println("Item "+itemname+" Successfully Added To the Auction List");
										System.out.println("Item "+itemname+" Successfully Added To the Auction List");
									}

								}
								catch(NumberFormatException nfe)
								{
									sellerwriter.println("Invalid Add Command Format (ADD Item_Number Item_Name)");
									System.out.println("Invalid Add Command Format (ADD Item_Number Item_Name)");
								}
							}
							else
							{
								sellerwriter.println("Improper Arguments there should be exactly Two Arguments with ADD Command");
								System.out.println("Improper Arguments there should be exactly Two Arguments with ADD Command");
							}
						}
						else if(command.equalsIgnoreCase("list"))
						{
							sellerwriter.println("List:- "+ItemList.listitems);
							System.out.println("List:- "+ItemList.listitems);

						}
						else if (command.equalsIgnoreCase("sell"))
						{	
							removal = false;
							int index = 0;
							if(cbrake.length == 2)
							{
								try
								{

									Integer itemnum = Integer.parseInt(cbrake[1]);
									for(int i=0;i<ItemList.itemnum.size();i++)
									{
										if(ItemList.itemnum.get(i)== itemnum)
										{
											removal = true;
											index = i;
											String biddername = ItemList.hbidder.get(i);
											for(int j=0;j<MasterList.soc.size();j++)
											{
												if(MasterList.name.get(j) == biddername)
												{
													Socket biddersocket = MasterList.soc.get(j);
													PrintWriter bidderwriter = new PrintWriter(biddersocket.getOutputStream(),true);
													bidderwriter.println("You have Won The Bid on Item "+itemnum);
												}
											}
											System.out.println("Name "+ItemList.itemnum.get(index));
											ItemList.bidamount.remove(index);
											ItemList.hbidder.remove(index);
											ItemList.itemname.remove(index);
											ItemList.itemnum.remove(index);
											ItemList.listitems.remove(index);
											sellerwriter.println("Item Has been Succesfully Sold to " +biddername);
											System.out.println("Item Has been Succesfully Sold to "+biddername);
										}
									}
									if(removal)
									{	

									}
									else
									{
										sellerwriter.println("Item Number Does Not Exits");
										System.out.println("Item Number Does Not Exits");
									}
								}
								catch(NumberFormatException nfe)
								{
									sellerwriter.println("Invalid Sell Command Format (Sell Item_Number)");
									System.out.println("Invalid Sell Command Format (Sell Item_Number)");
								}
							}
							else
							{
								sellerwriter.println("Improper Arguments Sell Command has only one Argument(Sell Item_Number)");
								System.out.println("Improper Arguments Sell Command has only one Argument(Sell Item_Number)");
							}
						}
						else if(command.equalsIgnoreCase("exit"))
						{
							sellerwriter.println(userid+" The Connection is closed Thank You");
							System.out.println(userid+" Has Logged Out from the Server");
							sellerwriter.close();
							sellerreader.close();
							sellersocket.close();
						}
						else
							sellerwriter.println("Invalid Command " +command+ " Please Enter a Valid Command");
					}
				}
			}

		}
		catch (Exception e){
			e.printStackTrace();
		}
	}



}