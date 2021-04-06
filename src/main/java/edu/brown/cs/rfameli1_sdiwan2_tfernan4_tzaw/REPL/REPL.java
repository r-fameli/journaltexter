package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.REPL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REPL {
  private final Pattern regexParse = Pattern.compile("(\".*?\"|\\S+)");
  private final HashSet<REPLCommand> registeredCommands;


  public REPL() {
    this.registeredCommands = new HashSet<>();
  }

  public void registerCommand(REPLCommand replCommand) {
    registeredCommands.add(replCommand);
  }

  public void start() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input = br.readLine();
    String command;
    List<String> args;

    while (input != null) {
      Matcher inputMatcher = regexParse.matcher(input);
      List<String> parsedUserInput = new ArrayList<>();
      while (inputMatcher.find()) {
        parsedUserInput.add(inputMatcher.group());
      }
      if (parsedUserInput.size() != 0) {
        command = parsedUserInput.get(0);
        parsedUserInput.remove(0);
        args = parsedUserInput;
        ArgHolder argHolder = new ArgHolder();
        // Run through every command and check if the input is valid for each
        for (REPLCommand aCommand : registeredCommands) {
          if (aCommand.getCallWord().equals(command)) {
            // Use the ArgValidator to ensure that the arguments matches one of the commands
            // possible argument formats (e.g. string, string, double)
            argHolder = ArgValidator.parseInputByFormats(command, aCommand.getArgFormats(), args);
            aCommand.run(argHolder);
          }
        }
      } else {
        System.out.println("ERROR: Invalid command entered");
      }
      // loop
      input = br.readLine();
    }
    br.close();
  }
}


