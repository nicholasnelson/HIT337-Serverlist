package io.nicknelson._hit337.serverlist;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class ServerItem {

  private int id;
  private String owner;
  private String hostname;
  private String ip;
  private String location;

  public ServerItem(int id, String owner, String hostname, String ip, String location) {
    this.id = id;
    this.owner = owner;
    this.hostname = hostname;
    this.ip = ip;
    this.location = location;
  }
  
  // Empty constructor
  public ServerItem() {
    this.id = -1;
    this.owner = "";
    this.hostname = "";
    this.ip = "";
    this.location = "";}

  public int getID() {
    return this.id;
  }

  public String getOwner() {
    return this.owner;
  }

  public String getHostname() {
    return this.hostname;
  }

  public String getIP() {
    return this.ip;
  }

  public String getLocation() {
    return this.location;
  }
}
