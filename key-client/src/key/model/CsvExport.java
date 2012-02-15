package key.model;

import com.csvreader.CsvWriter;

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
		CsvWriter writer = new CsvWriter(filename);
		for (ExportData data : exportDatas) {
			writer.writeRecord(new String[] {
				data.getMd5(),
				DATE_FORMAT.format(data.getFrom()),
				DATE_FORMAT.format(data.getTo()),
				data.isActive() ? ENABLED : DISABLED
			});
		}
		writer.close();
	}

}
