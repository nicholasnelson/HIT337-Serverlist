package io.nicknelson._hit337.serverlist;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
import io.nicknelson._hit337.serverlist.DerbyServerCatalogue;
import io.nicknelson._hit337.serverlist.DerbyServerCatalogue;
import io.nicknelson._hit337.serverlist.ServerCatalogue;
import io.nicknelson._hit337.serverlist.ServerCatalogue;
import io.nicknelson._hit337.serverlist.ServerItem;
import io.nicknelson._hit337.serverlist.ServerItem;
import java.sql.SQLException;
import java.util.List;
import org.junit.Test;

public class TestDerbyServerCatalogue {

  String DB_NAME = "SERVERLIST";

  @Test
  public void testDatabaseInit() throws SQLException {
    new DerbyServerCatalogue(DB_NAME);
  }

  @Test
  public void testAdd() throws Exception {
    ServerCatalogue sc = new DerbyServerCatalogue(DB_NAME);
    sc.addServer(new ServerItem(0, "Nick", "TestPC", "8.8.8.8", "Somewhere"));
  }
  
  @Test
  public void testGetAll() throws Exception{
    ServerCatalogue sc = new DerbyServerCatalogue(DB_NAME);
    sc.getAllServers();
  }
  
  @Test
  public void testGetUserServers() throws Exception {
    ServerCatalogue sc = new DerbyServerCatalogue(DB_NAME);
    sc.getAllServersForUser("Nick");
  }

}
