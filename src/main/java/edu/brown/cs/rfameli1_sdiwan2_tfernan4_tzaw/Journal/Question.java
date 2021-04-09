package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Journal;

public class Question implements JournalText {
  private final String text;

  public Question(String text) {
    this.text = text;
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
