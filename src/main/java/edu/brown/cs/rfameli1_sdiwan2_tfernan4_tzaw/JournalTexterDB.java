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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JournalTexterDB {
  private Connection conn = null;

  public JournalTexterDB() { }

  public void setConnection(Connection conn) {
    this.conn = conn;
  }

  public void checkConnection() throws SQLException {
    if (conn == null) {
      throw new SQLException("Database connection has not been set up in JournalTexter Database");
    }
  }


  public boolean loadDataFromSpreadsheet(String filename) {
    try {
      SpreadsheetData sd = SpreadsheetReader.parseSpreadsheet(filename, "\t",
          Arrays.asList("questions", "tags"));
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
      ps = conn.prepareStatement("SELECT tag_text FROM tags WHERE "
          + "(SELECT question_id FROM questions WHERE question_text=?)");
      Set<String> tags = new HashSet<>();
      ResultSet tagsResults = ps.executeQuery();
      while (tagsResults.next()) {
        tags.add(tagsResults.getString(1));
      }
      questions.add(new Question(questionText, tags));
    }
    return questions;
  }


  public List<Entry> getUserEntriesByUserId(Integer userId) throws SQLException {
    checkConnection();
    PreparedStatement ps = conn.prepareStatement("SELECT * FROM entries WHERE user_id=?");
    ps.setInt(1, userId);
    ResultSet rs = ps.executeQuery();
    return null;
    // For every entry, I would have to query the database to find the tags for every question
    // that was presented in the entry given the current structure of the classes

    // TODO
//      List<Entry> userEntries = new ArrayList<>();
//      while (rs.next()) {
//        userEntries.add(new Entry(
//
//        ))
//      }
  }

}
