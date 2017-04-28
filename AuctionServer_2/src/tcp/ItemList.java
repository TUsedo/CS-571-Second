package tcp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemList {
  
  private static final Logger logger = LogManager.getLogger(ItemList.class);
  private HashMap <Integer, Items>list = new HashMap<>();
  

  
  /*Method to add Item to the List
   * Will also check whether the Item Number 
   * is valid*/   
  public boolean addItem (int number, String name) {
    
    Items item;
    boolean result =false;
    if(list.isEmpty() || !list.containsKey(number)) {
      item = new Items(number, name);
      list.put(number,item);
      result = true;
    }
    if(!result)
      logger.error("(ItemList) Item Number already Exits "+number);
    return result;
  }
  
  /*Removes item from the list once 
   *they are sold*/
  
  public void removeItem (int number) {
    
    list.remove(number);
  }
  
  /*Returns the item for the given
   * Item Number*/
  
  public Items getItem(int number) {
    
    Items items = null;
    if(list.containsKey(number))
      items = list.get(number);
    return items;
  }
  
  /*Prints the all the Items in the List*/
  
  public void listItems() {
    
      Iterator <Entry<Integer, Items>> itr = list.entrySet().iterator();
      while(itr.hasNext()) {
        Entry<Integer, Items> entry = itr.next();
        Items i = entry.getValue();
        System.out.println("Item #: "+i.getItemNumber()+"Name: "+i.getItemName()+
            "Bid: "+i.getHighestBid()+"Bidder"+i.getHighestBidder());
      }
  }

}
