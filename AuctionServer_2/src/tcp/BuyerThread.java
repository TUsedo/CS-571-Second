package tcp;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class BuyerThread implements Runnable {
  
  private Socket client;
  private Socket bidder;
  private Server server;
  private PrintWriter output;
  private boolean logged_in =false;
  private String userName;
  
  public static final Logger logger = LogManager.getLogger();
  
  public BuyerThread(Server server, Socket client, Socket bidder) {
    this.client = client;
    this.server = server;
    this.bidder = bidder;
  }
  
  
  @Override
  public void run(){
    String command;
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new PrintWriter(client.getOutputStream(),true);
      while(client.isConnected()) {
        command = input.readLine();
        if(validCommandType(command) && verifyLogin(command)) {
          executeCommand(command);
        }
      }
    }
    catch(IOException ex) {
     logger.error("Failure to get I/O for the Buyer connection (BuyerThread Class) "+ex); 
    }    
  }
  
    
  private boolean validCommandType(String command) {    
    boolean result = server.validateCommand(command, false);
    if(!result) {
      output.println("Unrecognized Command ("+command+")Valid Commands (login/bid/list)");
      logger.error("Unrecognized Command ("+command+") BuyerThread run Method");
    }
    return result;
  }
  
    
  private boolean verifyLogin(String command) {       
    String [] commandSplit = command.split(" ",3);
    if(!logged_in && ("login").equalsIgnoreCase(commandSplit[0])) {
      try {
        logged_in = server.validateLogin(commandSplit[1], false, bidder);
        if(!logged_in) 
          output.println("Login failed(Invalid UserName) !!! Try Again");
        else {
            output.println("Login Successful");
            userName = commandSplit[1];
        }
      }
      catch (ArrayIndexOutOfBoundsException ex) {
        output.println("Login Failed (No UserName)!!! Try Again");
        logger.error("(Seller)No username provided for Login "+ex);
      }
    }
    return logged_in;
  }
  
  
  private void executeBidCommand(String command) {
    String [] commandSplit = command.split(" ",3);    
    try {
      int itemNumber = Integer.parseInt(commandSplit[1]);
      int bidAmount = Integer.parseInt(commandSplit[2]);
      int result = server.bidItem(itemNumber, bidAmount, userName);
      if(result == 1)
        output.println("You now have the Highest bid on Item "+itemNumber);
      else if (result == 0)
        output.println("Your bid is not high enough for the Item "+itemNumber);
      else
        output.println("Unrecognized Item "+itemNumber+" check the List of Items on the Auction");
    }
    catch(NumberFormatException ex) {
      output.println("Unrecognized command "+command+" bid <ItemNumber> <BidAmount>");
      logger.error("Unrecognized BID command "+command+" "+ex);
    }
    catch(ArrayIndexOutOfBoundsException ex) {
      output.println("Missing Arguments "+command+"(bid <Item Number> <Bid Amount>)");
      logger.error("Missing Arguments for "+command+" "+ex);
    }
  }
  
  
  private void executeCommand(String command) {    
    String [] commandSplit = command.split(" ", 2);    
    if(("bid").equalsIgnoreCase(commandSplit[0]))
      executeBidCommand(command);
    else if(("list").equalsIgnoreCase(commandSplit[0]))
      executeListCommand();      
  }
  
  
  private void executeListCommand() {
    String[] items = server.listItems();
    StringBuilder sb = new StringBuilder();
    if(items.length > 0) {
      for(String s: items) {
        sb.append(s+"  ");
      }
      output.println(sb);
    }
    else
      output.println("There are no items for auction");
  }
    
}
