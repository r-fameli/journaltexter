package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal;

import java.util.Map;

public interface JournalText<M extends Map<String, Integer>> {
  JournalTextType getType();
  String getText();
  String stringRepresentation();
  M getTags();
}
