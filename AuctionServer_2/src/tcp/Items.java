package tcp;

public class Items {
  
  private String itemName;
  private int itemNumber;
  private int highestBid;
  private String highestBidder;
  
  public Items(int number, String name) {
    
    itemNumber = number;
    itemName = name;
    highestBid = 0;
    highestBidder = " ";
    
  }
  
  public void setHighestBid(int highestBid) {
    this.highestBid = highestBid;
  }

  public void setHighestBidder(String highestBidder) {
    this.highestBidder = highestBidder;
  }
  public String getItemName() {
    return itemName;
  }

  public int getItemNumber() {
    return itemNumber;
  }

  public int getHighestBid() {
    return highestBid;
  }

  public String getHighestBidder() {
    return highestBidder;
  }
}

