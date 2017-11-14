package tk.hugo4715.golema.timesaver.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

@SuppressWarnings("unused")
public class SingleLineFormatter extends Formatter {

  Date dat = new Date();
  private final static String format = "{0,date} {0,time}";
  private MessageFormat formatter;
private Object args[] = new Object[1];

  // Line separator string.  This is the value of the line.separator
  // property at the moment that the SimpleFormatter was created.
  //private String lineSeparator = (String) java.security.AccessController.doPrivileged(
  //        new sun.security.action.GetPropertyAction("line.separator"));
  private String lineSeparator = "\n";

  /**
   * Format the given LogRecord.
   * @param record the log record to be formatted.
   * @return a formatted log record
   */
  public synchronized String format(LogRecord record) {
    StringBuilder sb = new StringBuilder();

    String message = formatMessage(record);

    // Level
    sb.append(record.getLevel().getName());
    sb.append("  TimeSaver: ");

    sb.append(message);
    sb.append(lineSeparator);
    
    if (record.getThrown() != null) {
      try {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        record.getThrown().printStackTrace(pw);
        pw.close();
        sb.append(sw.toString());
      } catch (Exception ex) {
      }
    }
    return sb.toString();
  }
}