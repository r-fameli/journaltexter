package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal;

import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterDB;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to hold data for a given entry in the journal.
 * @param <T> a class that implements the JournalText interface
 */
public class Entry<T extends JournalText> {
  private final Date date;
  private final String stringRepresentation;
  private final List<T> questionsAndResponses;
  private TreeMap<String, Integer> tags = null;

  public Entry(Date date, List<T> questionsAndResponses) {
    this.date = date;
    this.questionsAndResponses = questionsAndResponses;
    String stringRep = "";
    // Iterate through every question/response
    for (T questionOrResponse : questionsAndResponses) {
      // Concatenate the questionOrResponse's stringRepresentation
      stringRep += questionOrResponse.stringRepresentation();
    }
    this.stringRepresentation = stringRep;
  }

  public Date getDate() {
    return this.date;
  }

  public String getString() {
    return this.stringRepresentation;
  }

  public List<T> getQuestionsAndResponses() {
    return this.questionsAndResponses;
  }

  /**
   * Gets the tags from jtDatabase based on the most common words found in all responses.
   * @param jtDatabase the JournalTexterDB to base tags off of
   * @return a Map of tags and their frequencies in responses
   */
  public Map<String, Integer> getTagMap(JournalTexterDB jtDatabase) {
    TreeMap<String, Integer> singleResponseTags;
    Map<String, Integer> entryTagMap = new TreeMap<>();

//      if (questionOrResponse.getType() == JournalTextType.RESPONSE) {
//        singleResponseTags = questionOrResponse.getTags();
//        for (String tag : singleResponseTags.keySet()) {
//          Integer currentCount = entryTagMap.get(tag);
//          if (currentCount == null) {
//            entryTagMap.put(tag, singleResponseTags.get(tag));
//          } else {
//            entryTagMap.put(tag, currentCount + singleResponseTags.get(tag));
//          }
//        }
//      }
    // TODO add wordVecCount stuff here
    return this.tags;
  }

}
