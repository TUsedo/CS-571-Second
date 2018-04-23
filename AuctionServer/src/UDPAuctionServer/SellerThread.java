package UDPAuctionServer;

import java.net.*;
import TCPAuctionServer.ItemList;

public class SellerThread extends Thread {
  String command, userid, sellercommand;
  String[] cbrake;

  boolean connection = false;
  boolean addition = true;
  boolean removal = false;
  DatagramSocket sellersocket; 
  DatagramPacket serverreader;
  DatagramPacket serverwriter;
  byte receivebuffer[] = new byte[1500];
  byte sendbuffer[] = new byte[1500];
  String bufferreader;

  public SellerThread(DatagramSocket s, DatagramPacket p) {
    this.sellersocket = s;
    this.serverreader = p;
  }

  public void run() {
    try {
      receivebuffer = serverreader.getData();
      InetAddress selleraddress = serverreader.getAddress();
      int sellerport = serverreader.getPort();
      bufferreader = new String(receivebuffer);
      bufferreader = bufferreader.trim();
      command = bufferreader;
      cbrake = command.split(" ");
      if (cbrake[0].equalsIgnoreCase("login")) {
        userid = cbrake[1];
        if (UDPAuctionServer.validateseller(userid)) {
          connection = true;
          sendbuffer = new String("Successful").getBytes();
          serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
          sellersocket.send(serverwriter);
          sendbuffer = new String(userid + " You are now Connected to the Auction Server").getBytes();
          serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
          sellersocket.send(serverwriter);
        } else {
          sendbuffer = new String("UnSuccessful").getBytes();
          serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
          sellersocket.send(serverwriter);
          sendbuffer = new String(userid + " has Failed to connect to the Server As it is not a valid User").getBytes();
          serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
          sellersocket.send(serverwriter);
          System.out.println(userid + " is a invalid User");
          sellersocket.close();
        }
      } else {
        sendbuffer = new String("UnSuccessful").getBytes();
        serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
        sellersocket.send(serverwriter);
        sendbuffer = new String(
            "Failed to Execute Your Request As You are not Logged into the Server(Connection Terminated)").getBytes();
        serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
        sellersocket.send(serverwriter);
        sellersocket.close();
      }
      if (connection) {

        while (true) {
          sendbuffer = new String("Please Enter the Command You want to Execute on the Server").getBytes();
          serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
          sellersocket.send(serverwriter);

          serverreader = new DatagramPacket(receivebuffer, receivebuffer.length);
          sellersocket.receive(serverreader);
          bufferreader = new String(receivebuffer);
          bufferreader = bufferreader.trim();

          sellercommand = bufferreader;

          if (sellercommand.length() == 0) {
            sendbuffer = new String("Please Enter a command").getBytes();
            serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
            sellersocket.send(serverwriter);
          } else {
            System.out.println("Received " + sellercommand + " from " + userid);
            cbrake = sellercommand.split(" ");
            command = cbrake[0];
            if (command.equalsIgnoreCase("add")) {
              addition = true;
              if (cbrake.length == 3) {
                try {
                  Integer itemnum = Integer.parseInt(cbrake[1]);
                  String itemname = cbrake[2];
                  for (int index = 0; index < ItemList.itemnum.size(); index++) {
                    if (ItemList.itemnum.get(index) == itemnum) {
                      addition = false;
                      System.out.println("Item Number Already Exists Enter some Other Item Number");
                      sendbuffer = new String("Item Number Already Exists Enter some Other Item Number").getBytes();
                      serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                      sellersocket.send(serverwriter);
                    }
                  }
                  if (addition) {
                    ItemList.additem(itemnum, itemname, 0, "");
                    sendbuffer = new String("Item " + itemname + " Successfully Added To the Auction List").getBytes();
                    serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                    sellersocket.send(serverwriter);
                    System.out.println("Item " + itemname + " Successfully Added To the Auction List");
                  }
                } catch (NumberFormatException nfe) {
                  sendbuffer = new String("Invalid Add Command Format (ADD Item_Number Item_Name)").getBytes();
                  serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                  sellersocket.send(serverwriter);
                  System.out.println("Invalid Add Command Format (ADD Item_Number Item_Name)");
                }
              } else if (command.equalsIgnoreCase("list")) {
                sendbuffer = new String("List:- " + ItemList.listitems).getBytes();
                serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                sellersocket.send(serverwriter);
                System.out.println("List:- " + ItemList.listitems);

              } else if (command.equalsIgnoreCase("sell")) {
                removal = false;
                int index = 0;
                if (cbrake.length == 2) {
                  try {

                    Integer itemnum = Integer.parseInt(cbrake[1]);
                    for (int i = 0; i < ItemList.itemnum.size(); i++) {
                      if (ItemList.itemnum.get(i) == itemnum) {
                        removal = true;
                        index = i;
                        // String biddername = ItemList.hbidder.get(i);
                        // for(int j=0;j<MasterList.soc.size();j++)
                        // {
                        // if(MasterList.name.get(j) == biddername)
                        // {
                        // //DatagramSocket biddersocket = MasterList.soc.get(j);
                        //
                        // //bidderwriter.println("You have Won The Bid on Item "+itemnum);
                        // }
                        // }
                        ItemList.bidamount.remove(index);
                        ItemList.hbidder.remove(index);
                        ItemList.itemname.remove(index);
                        ItemList.itemnum.remove(index);
                        ItemList.listitems.remove(index);
                        sendbuffer = new String("Item Has been Succesfully Sold to ").getBytes();
                        serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                        sellersocket.send(serverwriter);
                        System.out.println("Item Has been Succesfully Sold to ");
                      }
                    }
                    if (removal) {

                    } else {
                      sendbuffer = new String("Item Number Does Not Exits").getBytes();
                      serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                      sellersocket.send(serverwriter);
                      System.out.println("Item Number Does Not Exits");
                    }
                  } catch (NumberFormatException nfe) {
                    sendbuffer = new String("Invalid Sell Command Format (Sell Item_Number)").getBytes();
                    serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                    sellersocket.send(serverwriter);
                    System.out.println("Invalid Sell Command Format (Sell Item_Number)");
                  }
                } else {
                  sendbuffer = new String("Improper Arguments Sell Command has only one Argument(Sell Item_Number)")
                      .getBytes();
                  serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                  sellersocket.send(serverwriter);
                  System.out.println("Improper Arguments Sell Command has only one Argument(Sell Item_Number)");
                }
              } else if (command.equalsIgnoreCase("exit")) {
                sendbuffer = new String(userid + " The Connection is closed Thank You").getBytes();
                serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                sellersocket.send(serverwriter);
                System.out.println(userid + " Has Logged Out from the Server");
                sellersocket.close();
              } else {
                sendbuffer = new String("Invalid Command " + command + " Please Enter a Valid Command").getBytes();
                serverwriter = new DatagramPacket(sendbuffer, sendbuffer.length, selleraddress, sellerport);
                sellersocket.send(serverwriter);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
