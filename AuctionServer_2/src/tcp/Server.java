package tcp;

import java.util.HashMap;
import java.util.Map;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Server {  
 static final Logger logger = LogManager.getLogger(); 
 private Users user = new Users();
 private HashMap <Integer,Items> itemList = new HashMap<>();
 private HashMap <String, Socket> bidderList = new HashMap<>();
 private Object objLock = new Object();
  
  
  public boolean validateLogin(String userName, boolean seller, Socket bidder) {    
    synchronized (objLock) {      
      boolean result;
      if(seller) 
        result = user.validateSeller(userName);
      else {
        result = user.validateBuyer(userName);
        if(result)
          bidderList.put(userName, bidder);
      }
      return result;
    }
  }

  
  public boolean validateCommand(String command, boolean seller) {    
    synchronized (objLock) {
      String pattern;
      if(seller)
        pattern = "^\\b(add|sell|login|list)\\b.*";
      else
        pattern = "^\\b(bid|login|list)\\b.*";
      Pattern pat = Pattern.compile(pattern);
      Matcher mat = pat.matcher(command);
      return mat.matches();
    }
  }
  
  
  public boolean addItem(int itemNumber, String itemName) {   
    synchronized(objLock) {      
      if(!itemList.containsKey(itemNumber)) {
        Items item = new Items(itemNumber,itemName);
        itemList.put(itemNumber, item);
        return true;
      }
      else
        return false;
    }
  }
  
  
  public int bidItem(int itemNumber, int bidAmount, String bidderName) {    
    synchronized (objLock) {
      int result;
      String previousBidder;
     if(itemList.containsKey(itemNumber)) {
       Items item = itemList.get(itemNumber);
       previousBidder = updateBidInfo(item,bidAmount, bidderName);
       result = 1;
       if(previousBidder != null) 
         lostBidder(previousBidder,itemNumber);  
       else 
         result = 0;
    }
     else
       result = -1;   
     return result;
    }
  }

  
  private String updateBidInfo(Items item, int bidAmount, String bidderName) {    
      String previousBidder = "";
      if(Integer.compare(bidAmount, item.getHighestBid()) > 0 ) {
        if(item.getHighestBid() > 0)
          previousBidder = item.getHighestBidder();
        item.setHighestBid(bidAmount);
        item.setHighestBidder(bidderName);
      }
      else
        previousBidder = null;
      return previousBidder;    
  } 

  
  private void lostBidder (String userName, int itemNumber) {
    if(bidderList.containsKey(userName)) {
      Socket bidder = bidderList.get(userName);
      try(PrintWriter bidderIn = new PrintWriter(bidder.getOutputStream(),true)) {
        bidderIn.println("You have been OutBid on Item "+ itemNumber);
      }
      catch(IOException ex) {
        logger.error("Failure to create I/O for the Bidder (Server Class) "+ex);
      }
    }
  }
  
  
  public String[] listItems() {
    synchronized (objLock) {
      String [] listItem = new String [itemList.size()];
      int count = 0;
      if(itemList.size() > 0) {
        for(Map.Entry<Integer, Items> entry : itemList.entrySet()) {
          Items item = entry.getValue();
          String value = "ItemNumber:"+item.getItemNumber()+" Name:"+item.getItemName()+
              " Highest Bid:"+item.getHighestBid()+" Bidder"+item.getHighestBidder();
          listItem[count] = value;
          count++;
        }      
      }
      return listItem;      
    }
  }
  
  
  public String sellItem(int itemNumber) {
    synchronized(objLock) {
      if(itemList.containsKey(itemNumber)) {
        Items item = itemList.get(itemNumber);
        String bidder = item.getHighestBidder(); 
        winnigBidder(bidder, itemNumber);
        itemList.remove(itemNumber);
        return bidder;
      }
      return null;   
    }       
  }
  
  
  private void winnigBidder(String userName, int itemNumber) {
    Socket bidder;
    if(bidderList.containsKey(userName)) {
      bidder = bidderList.get(userName);
      try(PrintWriter bidderIn = new PrintWriter(bidder.getOutputStream(),true)) {
        bidderIn.println("You have Won the Bid on Item "+ itemNumber);
      }
      catch(IOException ex) {
        logger.error("Failure to create I/O for the Bidder (Server Class) "+ex);
      }
    }
  }
  

}
