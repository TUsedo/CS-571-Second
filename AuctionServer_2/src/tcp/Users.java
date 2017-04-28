package tcp;

import java.util.HashSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.net.Socket;

public class Users {
  
  private HashSet <String> userSet = new HashSet<>();
  private HashMap <String, Socket> userSocketList = new HashMap<>();
  
  private static final Logger logger = LogManager.getLogger(Users.class);
  
  public Users () {
    
    userSet.add("Alice");
    userSet.add("Tom");
    userSet.add("Bob");
    userSet.add("Pam");
    userSet.add("Dave");
    userSet.add("Amy");
    
  }
  
  public boolean validateBuyer(String userName) {   
    return userSet.contains(userName);    
   }
  
  public boolean validateSeller(String userName) {   
    return ("seller").equalsIgnoreCase(userName);    
   }
  
  public boolean addUser(String userName) { 
    return userSet.add(userName);     
  }
 
  public void adduserSocket (String userName, Socket userSocket) {
   
    if(!userSocketList.containsKey(userName))
     userSocketList.put(userName, userSocket);
   else
     logger.error("(Users addUserSocket Method) Failed to add user to UserSocketList ("+userName+") already exits in the List");
  }
  
  public Socket getUserSocket (String userName) {
    return userSocketList.get(userName);
  }
}
