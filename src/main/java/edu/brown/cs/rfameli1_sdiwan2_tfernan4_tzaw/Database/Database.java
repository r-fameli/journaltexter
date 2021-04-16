package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for managing a Connection to a SQLite3 database.
 */
public class Database {
  private final String sqlUrl;

  /**
   * Sets up a Connection to the provided SQLite3 database.
   *
   * @param filename The SQLite3 database to open
   * @throws SQLException if the provided file does not refer to a database or if another error
   * occurs when connecting
   * @throws ClassNotFoundException should never happen, since SQLite class name is well-formed
   */
  public Database(String filename) throws SQLException, ClassNotFoundException {
    Class.forName("org.sqlite.JDBC");
    sqlUrl = "jdbc:sqlite:" + filename;
    getNewConnection();
  }

  /**
   * Get the Connection to the database.
   * @return The Connection to the database.
   */
  public Connection getNewConnection() throws SQLException {
    return createNewConnection();
  }

  /**
   * Creates a new Connection (used when a Connection has been closed).
   * @return a new Connection to the Database
   * @throws SQLException if an error occurs creating the new connection
   */
  private Connection createNewConnection() throws SQLException {
    Connection conn = DriverManager.getConnection(sqlUrl);
    // these two lines tell the database to enforce foreign keys during operations.
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON;");
    return conn;
  }
}
