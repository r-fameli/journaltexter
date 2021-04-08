package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterREPL;

import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterDB;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.ArgHolder;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.ArgTypes;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.REPLCommand;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.HeaderException;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.SpreadsheetData;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet.SpreadsheetReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoadQuestionsCommand implements REPLCommand {

  private final JournalTexterDB jtDatabase;
  public LoadQuestionsCommand(JournalTexterDB jtDatabase) {
    this.jtDatabase = jtDatabase;
  }


  @Override
  public String getCallWord() {
    return "load";
  }

  @Override
  public List<List<ArgTypes>> getArgFormats() {
    return Collections.singletonList(Collections.singletonList(ArgTypes.STRING));
  }

  @Override
  public void run(ArgHolder arguments) {
    String spreadsheetFile = arguments.nextString();
    if (jtDatabase.loadDataFromSpreadsheet(spreadsheetFile)) {
      System.out.println("Successfully loaded in questions");
    }
  }
}
