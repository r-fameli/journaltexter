package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterREPL;

import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Database;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.JournalTexterDB;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.ArgHolder;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.ArgTypes;
import edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL.REPLCommand;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SetDatabaseCommand implements REPLCommand {
  private final JournalTexterDB jtDatabase;

  public SetDatabaseCommand(JournalTexterDB jtDatabase) {
    this.jtDatabase = jtDatabase;
  }

  @Override
  public String getCallWord() {
    return "database";
  }

  @Override
  public List<List<ArgTypes>> getArgFormats() {
    return Collections.singletonList(Collections.singletonList(ArgTypes.STRING));
  }

  @Override
  public void run(ArgHolder arguments) {
    String databaseName = arguments.nextString();
    try {
      Database questionsDB = new Database(databaseName);
      jtDatabase.setConnection(questionsDB.getConnection());
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
