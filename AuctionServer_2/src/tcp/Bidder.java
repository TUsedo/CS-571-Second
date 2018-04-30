package tcp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.io.InputStreamReader;


public class Bidder implements Runnable{
  
  static final Logger logger = LogManager.getLogger();
  private int bidderPort;
  
  public Bidder(int bidderPort) {
    this.bidderPort = bidderPort;
  }
  
  @Override
  public void run(){
    
    try(Socket client = new Socket(InetAddress.getByName("localhost"),bidderPort);
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
      
      while(client.isConnected()) {
        String input = clientReader.readLine(); 
        if(input != null)
          System.out.println(input);
      }
  }
    catch(UnknownHostException ex) {
      logger.error("Unknown Host Error (Buyer Client) "+ex);
    }
    catch (IOException ex) {      
      logger.error("Failure to get I/O for the connection (Buyer Client) "+ex);
    }  
 }  

}
