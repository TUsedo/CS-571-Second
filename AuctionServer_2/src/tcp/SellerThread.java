package tcp;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SellerThread implements Runnable {
  
  private static final Logger logger = LogManager.getLogger(SellerThread.class);  
  private Socket client;
  private PrintWriter output;
  private Server server;
  private boolean logged_in =false;
  
  public SellerThread (Socket s, Server server) {    
    this.server = server;
    client = s;
  }
  
  @Override
  public void run() {
    try {
      String command;        
      BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
      output = new PrintWriter(client.getOutputStream(),true);      
            
      while(client.isConnected()) {
        
        command = input.readLine();
        if(validCommandType(command) && verifyLogin(command)) {
          executeCommand(command);
        }
    }
      client.close();
   }
    catch(IOException ex) {
     logger.error("Failure to get I/O for the connection (Seller Thread Class) "+ex); 
    }
    
  }
  
  private boolean validCommandType (String command) {
    
    boolean result = server.validateCommand(command, true);
    if(!result) {
      output.println("Unrecognized Command ("+command+")Valid Commands (login/add/sell/list)");
      logger.error("Unrecognized Command ("+command+") SellerThread run Method");
    }
    return result;
  }
  
  private boolean verifyLogin(String command) {   
    
    String [] commandSplit = command.split(" ",3);
    if(!logged_in && ("login").equalsIgnoreCase(commandSplit[0])) {
      try {
        logged_in = server.validateLogin(commandSplit[1], true, null);
        if(!logged_in) 
          output.println("Login failed(Invalid UserName) !!! Try Again");
        else 
          output.println("Login Successful");
      }
      catch (ArrayIndexOutOfBoundsException ex) {
        output.println("Login Failed (No UserName)!!! Try Again");
        logger.error("(Seller)No username provided for Login "+ex);
      }
    }
    return logged_in;
  }
  
  private void executeCommand (String command) {
    
    String [] commandSplit = command.split(" ", 2);
        
    if(("add").equalsIgnoreCase(commandSplit[0]))
      executeAddCommand(command);
    else if(("sell").equalsIgnoreCase(commandSplit[0]))
      executeSellCommand(command);
    else if(("list").equalsIgnoreCase(commandSplit[0]))
      executeListCommand();      
  }
  
  private void executeAddCommand(String command) {
    String [] commandSplit = command.split(" ",3);    
    try {
      int itemNumber = Integer.parseInt(commandSplit[1]);
      String itemName = commandSplit[2];
      if(server.addItem(itemNumber, itemName))
        output.println("Item "+itemName+" successfully added to the auction list");
      else
        output.println("Can't add item as the Item Number \""+itemNumber+" is already in use (change the item number and try again)");
    }
    catch(NumberFormatException ex) {
      output.println("Unrecognized command format "+command+" add <ItemNumber> <ItemName>");
      logger.error("Unrecognized ADD command "+command+" "+ex);
    }
    catch(ArrayIndexOutOfBoundsException ex) {
      output.println("Missing Arguments "+command+"(add <Item Number> <Item Name>)");
      logger.error("Missing Arguments for "+command+" "+ex);
    }
  }
  
  private void executeSellCommand(String command) {
    String [] commandSplit = command.split(" ",2);    
    try {
      int itemNumber = Integer.parseInt(commandSplit[1]);
      String winner = server.sellItem(itemNumber); 
      if(winner != null)
        output.println("Item "+itemNumber+" was successfully sold to "+winner);
      else
        output.println("Item "+itemNumber+"was already sold");
    }
    catch(NumberFormatException ex) {
      output.println("Unrecognized command format "+command+" add <ItemNumber>");
      logger.error("Unrecognized ADD command "+command+" "+ex);
    }
    catch(ArrayIndexOutOfBoundsException ex) {
      output.println("Missing Arguments "+command+"(add <Item Number>)");
      logger.error("Missing Arguments for "+command+" "+ex);
    }    
  }
  
  private void executeListCommand() {
    String[] items = server.listItems();
    StringBuilder sb = new StringBuilder();
    if(items.length > 0) {
      for(String s: items) {
        sb.append(s+"    ");
      }
      output.println(sb);
    }
    else
      output.println("There are no items for auction");
  }
   
}
