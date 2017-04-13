/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.nicknelson._hit337.serverlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nick Nelson <dev@nicknelson.io>
 */
public class DerbyServerCatalogue implements ServerCatalogue {

  private final String DB_NAME;

  public DerbyServerCatalogue(String dbName) throws SQLException {
    this.DB_NAME = dbName;
    initDB();
  }

  @Override
  public List<ServerItem> getAllServers() throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      // Prepare query
      String queryString = "SELECT * FROM SERVERS";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);

      // Run query
      ResultSet rs = prepStatement.executeQuery();
      // Init resultObjects list
      List resultObjects = new ArrayList();

      // Iterate over the ResultSet, creating ServerItem from each and adding to list
      while (rs.next()) {
        ServerItem s = new ServerItem(
                rs.getInt("ID"),
                rs.getString("OWNER"),
                rs.getString("HOSTNAME"),
                rs.getString("IP"),
                rs.getString("LOCATION"));
        resultObjects.add(s);
      }

      return resultObjects;
    }
  }

  @Override
  public List<ServerItem> getAllServersForUser(String owner) throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "SELECT * FROM SERVERS "
              + "WHERE OWNER = ?";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);
      prepStatement.setString(1, owner);

      ResultSet rs = prepStatement.executeQuery();

      List resultObjects = new ArrayList();

      while (rs.next()) {
        ServerItem s = new ServerItem(
                rs.getInt("ID"),
                rs.getString("OWNER"),
                rs.getString("HOSTNAME"),
                rs.getString("IP"),
                rs.getString("LOCATION"));
        resultObjects.add(s);
      }

      return resultObjects;
    }
  }
  
  @Override
  public ServerItem getServerWithID(int id) throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "SELECT * FROM SERVERS "
              + "WHERE ID = ?";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);
      prepStatement.setInt(1, id);

      ResultSet rs = prepStatement.executeQuery();

      // Should only be one item here as ID is enforced unique in the DB
      rs.next();
      ServerItem s = new ServerItem(
              rs.getInt("ID"),
              rs.getString("OWNER"),
              rs.getString("HOSTNAME"),
              rs.getString("IP"),
              rs.getString("LOCATION"));

      return s;
    }
  }

  @Override
  public int addServer(ServerItem server) throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "INSERT INTO SERVERS (OWNER, HOSTNAME, IP, LOCATION) "
              + "VALUES (?, ?, ?, ?)";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);
      prepStatement.setString(1, server.getOwner());
      prepStatement.setString(2, server.getHostname());
      prepStatement.setString(3, server.getIP());
      prepStatement.setString(4, server.getLocation());

      int affectedRows = prepStatement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Failed to insert server into database.");
      } else {
        // This should be 1
        return affectedRows;
      }
    }
  }

  @Override
  public void updateServer(ServerItem server) throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "UPDATE SERVERS "
              + "SET OWNER = ?, HOSTNAME = ?, IP = ?, LOCATION = ? "
              + "WHERE ID = ?";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);
      prepStatement.setString(1, server.getOwner());
      prepStatement.setString(2, server.getHostname());
      prepStatement.setString(3, server.getIP());
      prepStatement.setString(4, server.getLocation());
      prepStatement.setInt(5, server.getID());
      
      int affectedRows = prepStatement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Failed to update entry in SERVERS with ID = "
                + server.getID()
                + ". Matching entry not found.");
      }
    }
  }

  @Override
  public void removeServer(int id) throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "DELETE FROM SERVERS WHERE ID = ?";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);
      prepStatement.setInt(1, id);

      int affectedRows = prepStatement.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Failed to delete entry from SERVERS with ID = "
                + id
                + ". Matching entry not found.");
      }
    }
  }

  private Connection getConnection(boolean createDatabase) throws SQLException {
    checkDriverLoaded();

    String attributes = "";
    if (createDatabase) {
      attributes = ";create=true";
    }
    Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/" + this.DB_NAME + attributes);
    return conn;
  }

  private void checkDriverLoaded() throws RuntimeException {
    try {
      // Check that JDBC driver is loaded
      Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to load jdbc driver", e);
    }
  }

  // Check if the table exists, if it does, do nothing, if not, create it
  private void initDB() throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(true)) {
      DatabaseMetaData meta = conn.getMetaData();
      ResultSet res = meta.getTables(null, null, "SERVERS", new String[]{"TABLE"});
      // If there is nothing in the ResultSet, the table doesn't exist
      if (!res.next()) {
        this.createTable();;
      }
    }
  }

  private void createTable() throws SQLException {
    // Try-with-resources will automatically call close() on the conn object
    try (Connection conn = getConnection(false)) {
      String queryString = "CREATE TABLE SERVERS "
              + "(ID int generated always as identity, "
              + "OWNER varchar(32), "
              + "HOSTNAME varchar(32), "
              + "IP varchar(15), "
              + "LOCATION varchar(100))";
      PreparedStatement prepStatement = conn.prepareStatement(queryString);

      prepStatement.execute();
    }
  }

}
