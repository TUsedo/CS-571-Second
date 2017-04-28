package tcp;

import java.net.Socket;
import java.net.ServerSocket;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;;

public class BuyerSocket implements Runnable {
  
  static final Logger logger = LogManager.getLogger();
  private int buyerPort;
  Server server;
  
  public BuyerSocket(int serverPort, Server s) {
    buyerPort = serverPort;
    server =s;
  }
  
  @Override
  public void run(){
   Socket client;
   Socket bidder;
   try(ServerSocket serverSocket = new ServerSocket(buyerPort)) {  
     System.out.println("Waiting for a client on the Buyer Port");
     while(!serverSocket.isClosed()) {
       client = serverSocket.accept();
       bidder = serverSocket.accept();
       System.out.println("A new Client has joined the Auction Server at Buyer Port");
       Thread buyerThread = new Thread(new BuyerThread(server,client,bidder));
       buyerThread.start();
     }     
   }
   catch(IOException ex) {
     logger.error("Failure to open the ServerSocket (BuyerSocket Class) "+ex);
   }
    
  }

}
