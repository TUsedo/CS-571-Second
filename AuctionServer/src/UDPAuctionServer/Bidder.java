package UDPAuctionServer;

import java.net.*;

public class Bidder extends Thread {
  private InetAddress serveraddress;
  private int bidder;
  public DatagramSocket biddersocket;
  DatagramPacket buyerreader;
  DatagramPacket buyerwriter;
  String bufferreader;
  byte receivebuffer[] = new byte[1500];
  byte sendbuffer[] = new byte[1500];

  public Bidder(InetAddress saddr, int socket) throws Exception {
    this.serveraddress = saddr;
    this.bidder = socket;
  }

  public void run() {
    try {
      DatagramSocket biddersocket = new DatagramSocket();
      sendbuffer = "HI".getBytes();
      buyerwriter = new DatagramPacket(sendbuffer, sendbuffer.length, serveraddress, bidder);
      biddersocket.send(buyerwriter);
      while (true) {
        buyerreader = new DatagramPacket(receivebuffer, receivebuffer.length);
        biddersocket.receive(buyerreader);
        bufferreader = new String(receivebuffer);
        bufferreader = bufferreader.trim();
        System.out.println(bufferreader);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
