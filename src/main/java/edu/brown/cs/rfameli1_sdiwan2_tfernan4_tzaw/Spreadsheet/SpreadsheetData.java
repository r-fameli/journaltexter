package edu.brown.cs.rfameli1_sdiwan2_tfernan4_tzaw.Spreadsheet;

import java.util.ArrayList;
import java.util.List;

public class SpreadsheetData {
  private List<String> headers;
  private List<List<String>> rows;

  public SpreadsheetData(List<String> headers, List<List<String>> rows) {
    this.headers = headers;
    this.rows = rows;
  }

  public List<String> headers() {
    return new ArrayList<>(headers);
  }

  public List<List<String>> getRows() {
    // TODO make defensive copies of inner lists too
    return new ArrayList<>(rows);
  }
}