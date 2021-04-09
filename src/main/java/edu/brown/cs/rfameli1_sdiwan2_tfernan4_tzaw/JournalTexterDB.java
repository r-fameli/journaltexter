package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw;

import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal.Entry;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal.JournalText;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal.Question;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal.Response;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.HeaderException;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.SpreadsheetData;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.SpreadsheetReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JournalTexterDB {
  private Connection conn = null;

  public JournalTexterDB() { }

  public void setConnection(Connection connection) {
    this.conn = connection;
  }

  public Connection getConnection() {
    return this.conn;
  }

  public void checkConnection() throws SQLException {
    if (conn == null) {
      throw new SQLException("Database connection has not been set up in JournalTexter Database");
    }
  }

  public void clearTable(String tableName) throws SQLException {
    checkConnection();
    PreparedStatement ps = conn.prepareStatement("DELETE FROM ?");
    ps.setString(1, tableName);
    ps.executeUpdate();
  }

  public boolean loadDataFromSpreadsheet(String filename) {
    try {
      SpreadsheetData sd = SpreadsheetReader.parseSpreadsheet(filename, "\t",
          Arrays.asList("Question", "Tags"));
      List<List<String>> rows = sd.getRows();
      PreparedStatement ps;
      ResultSet rs;

      for (List<String> r : rows) {
        // Check if the question already exists in the table
        String question = r.get(0);
        ps = conn.prepareStatement("SELECT * FROM questions WHERE question_text=?;");
        ps.setString(1, question);
        rs = ps.executeQuery();
        boolean questionAlreadyInTable = rs.next();
        // insert question if its not already in the table
        if (!questionAlreadyInTable) {
          ps = conn.prepareStatement("INSERT INTO questions (question_text) VALUES (?);");
          ps.setString(1, question);
          ps.executeUpdate();
        }
        // Get the tags from the second column of the spreadsheet
        List<String> tags = Arrays.asList(r.get(1).split(","));
        for (String tag : tags) {
          // Check if the tag-question relation already exists in the table
          ps = conn.prepareStatement("SELECT * FROM tags WHERE tag_text=? AND "
              + "question_id=(SELECT id FROM questions WHERE question_text=?);");
          ps.setString(1, tag);
          ps.setString(2, question);
          rs = ps.executeQuery();
          // insert tag if the tag-question relation does not already exist
          if (!rs.next()) {
            ps = conn.prepareStatement("INSERT INTO tags (tag_text, question_id) VALUES (?, "
                + "(SELECT id FROM questions WHERE question_text=?));");
            ps.setString(1, tag);
            ps.setString(2, question);
            ps.executeUpdate();
          }
        }
      }
      return true;
    } catch (HeaderException | IOException | SQLException e) {
      System.out.println(e.getMessage());
      return false;
    }
  }

  public List<Question> findQuestionsFromTag(String tag) throws SQLException {
    checkConnection();
    List<Question> questions = new ArrayList<>();

    PreparedStatement ps = conn.prepareStatement("SELECT question_text FROM questions "
        + "WHERE id=(SELECT question_id FROM tags WHERE tag_text=?)");
    ps.setString(1, tag);
    ResultSet questionsResults = ps.executeQuery();
    while (questionsResults.next()) {
      String questionText = questionsResults.getString(1);
      questions.add(new Question(questionText));
    }
    return questions;
  }


  public List<Entry<JournalText>> getUserEntriesByUsername(String username) throws SQLException {
    checkConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM entries WHERE author=?");
    ps.setString(1, username);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      Integer id = rs.getInt(1);
      Date date = rs.getDate(2);
      String text = rs.getString(3);
      String author = rs.getString(4);
      // TODO make this into
    }
    return null;
  }

  public Set<String> getAllTagsFromDB() throws SQLException {
    checkConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT tag_text FROM tags");
    ResultSet rs = ps.executeQuery();
    Set<String> allTags = new HashSet<>();
    while (rs.next()) {
      allTags.add(rs.getString(1));
    }
    return allTags;
  }

}
