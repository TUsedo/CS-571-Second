package tcp;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.net.Socket;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class Buyer {
  
  private Buyer(){}
  
  public static void main(String [] args) {
    
    final Logger logger = LogManager.getLogger();
    String input;
    int serverPort = 6000;
    
    try 
    (Socket client = new Socket(InetAddress.getByName("localhost"),serverPort);
     BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
     BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
     PrintWriter clientWriter = new PrintWriter(client.getOutputStream(),true);) 
    
    {
      Thread bidder = new Thread(new Bidder(serverPort));
      bidder.start();
      System.out.println("Connecting to the Buyer Port on the Auction Server");
      System.out.println("You ned to login to the Auction Server");
      
      while(client.isConnected()) {
        input = consoleReader.readLine();
        clientWriter.println(input);
        input = clientReader.readLine();
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
