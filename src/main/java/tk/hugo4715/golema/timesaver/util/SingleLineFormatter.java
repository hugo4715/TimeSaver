package tk.hugo4715.golema.timesaver.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class SingleLineFormatter extends Formatter {

	Date dat = new Date();
	private String lineSeparator = "\n";

	/**
	 * Format the given LogRecord.
	 * 
	 * @param record
	 *            the log record to be formatted.
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