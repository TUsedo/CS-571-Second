package tcp;

public class AuctionServer {
  
  private AuctionServer(){}

  public static void main(String[] args) { 
    
    Server server = new Server();
    Thread seller = new Thread(new SellerSocket(5000, server));
    Thread buyer = new Thread(new BuyerSocket(6000, server));
    seller.start();
    buyer.start();   
  }

}