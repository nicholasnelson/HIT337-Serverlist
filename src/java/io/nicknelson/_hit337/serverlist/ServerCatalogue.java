package io.nicknelson._hit337.serverlist;

import java.util.List;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public interface ServerCatalogue {

  public List<ServerItem> getAllServers() throws Exception;

  public List<ServerItem> getAllServersForUser(String username) throws Exception;
  
  public ServerItem getServerWithID(int id) throws Exception;

  public int addServer(ServerItem server) throws Exception;

  public void updateServer(ServerItem server) throws Exception;

  public void removeServer(int id) throws Exception;

}
