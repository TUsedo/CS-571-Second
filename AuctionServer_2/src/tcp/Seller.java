package tcp;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
 

public class Seller {
    
  private Seller(){}
  
  public static void main(String[] args){
    
    final Logger logger = LogManager.getLogger();
    int sellerPort = 5000;
    String input;   
    
    try(Socket client = new Socket(InetAddress.getByName("localhost"),sellerPort);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter clientWriter = new PrintWriter(client.getOutputStream(),true);) {      
      
      System.out.println("Connecting to the seller port of the Auction Server");
      System.out.println("Login to connect to the Auction Server");    
      
      while(client.isConnected()) {
        input = consoleReader.readLine();
        clientWriter.println(input);
        input = clientReader.readLine();
        System.out.println(input);
      }
    }
    catch(UnknownHostException ex) {
      logger.error("Unknown Host Error (Seller Client) "+ex);
    }
    catch (IOException ex) {      
      logger.error("Failure to get I/O for the connection (Seller Client) "+ex);
    }  
              

  }

}
