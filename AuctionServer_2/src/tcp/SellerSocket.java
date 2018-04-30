package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SellerSocket implements Runnable{
  
  private int serverPort;
  private Server server;
  
  public static final Logger logger = LogManager.getLogger(SellerSocket.class);
  
  public SellerSocket(int port, Server server) {
    serverPort = port;
    this.server = server;
  }
  
  public void run() {
    Socket client = null;
    try(ServerSocket serverSocket = new ServerSocket(serverPort)) {
      
      System.out.println("Waiting for a Client on the Seller Port");
      
      while(!serverSocket.isClosed()) {
        client = serverSocket.accept();
        System.out.println("A new Client has Joined the Auction on the Seller Port");
        Thread seller = new Thread(new SellerThread(client,server));
        seller.start();
      }
    }
    catch(IOException ex) {
      logger.error("Failure to open the ServerSocket (SellerSocket Class) "+ex);
    }
    
  }

}
