package TCPAuctionServer;
import java.io.*;
import java.net.*;
public class Bidder extends Thread {
	private	InetAddress serveraddress;
	private int bidder ;
	public Socket biddersocket;
	public Bidder (InetAddress saddr, int socket)throws Exception {
		this.serveraddress = saddr;
		this.bidder = socket;
	}		
		public void run() {	
			try {
			Socket biddersocket = new Socket(InetAddress.getLocalHost(),bidder);
			BufferedReader bidderreader = new BufferedReader(new InputStreamReader(biddersocket.getInputStream()));
			while(true) {
				System.out.println(bidderreader.readLine());
			}		
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
}