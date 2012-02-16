package key.model;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CsvExport {

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final static String ENABLED = "enabled";
	private final static String DISABLED = "disabled";

	private CsvExport() {
	}

	public static final void export(String filename, SetData setData) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(filename), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
		String from = DATE_FORMAT.format(setData.getFrom());
		String to = DATE_FORMAT.format(setData.getTo());
		for (KeyData key : setData.getKeys()) {
			writer.writeNext(new String[] {
					key.getHash(),
					from,
					to,
					new Date().before(setData.getTo()) ? ENABLED : DISABLED
				});
		}
		writer.close();
	}

}
