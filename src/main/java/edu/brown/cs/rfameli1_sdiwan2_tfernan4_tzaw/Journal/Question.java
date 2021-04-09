package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

public class Question implements JournalText {
  private final String text;
  private final Set<String> tags;

  public Question(String text, Set<String> tags) {
    this.text = text;
    this.tags = tags;
  }

  @Override
  public TreeMap<String, Integer> getTags() {
    TreeMap<String, Integer> tagMap = new TreeMap<>();
    Integer counter = 1;
    for (String tag : tags) {
      tagMap.put(tag, counter);
      counter++;
    }
    return tagMap;
  }

  @Override
  public JournalTextType getType() {
    return JournalTextType.QUESTION;
  }

  @Override
  public String getText() {
    return text;
  }

  @Override
  public String stringRepresentation() {
    return "{@" + text + "}";
  }
}
