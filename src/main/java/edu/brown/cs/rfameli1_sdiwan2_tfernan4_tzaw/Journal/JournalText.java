package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal;

import java.util.Map;
import java.util.TreeMap;

// change M to treemap
public interface JournalText {
  JournalTextType getType();
  String getText();
  String stringRepresentation();
  TreeMap<String, Integer> getTags();
}
