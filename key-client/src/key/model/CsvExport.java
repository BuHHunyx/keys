package key.model;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class CsvExport {

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private final static String ENABLED = "enabled";
	private final static String DISABLED = "disabled";

	private CsvExport() {
	}

	public static final void export(String filename, ExportData[] exportDatas) throws IOException {
		CSVWriter writer = new CSVWriter(new FileWriter(filename), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
		for (ExportData data : exportDatas) {
			writer.writeNext(new String[] {
					data.getMd5(),
					DATE_FORMAT.format(data.getFrom()),
					DATE_FORMAT.format(data.getTo()),
					data.isActive() ? ENABLED : DISABLED
				});
		}
		writer.close();
	}

}
