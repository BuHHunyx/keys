package key.gui;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SetTable {

	private final static DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(
			DateFormat.SHORT,
			DateFormat.SHORT,
			new Locale(System.getProperty("user.language")));
	//new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final static String[] LABELS = new String[] {
		"Номер пачки",
		"Дата формирования",
		"Комментарий",
		"Дата начала действия",
		"Дата окончания действия"
	};

	private final static int INDEX_N = 0;
	private final static int INDEX_DATE = 1;
	private final static int INDEX_COMMENT = 2;
	private final static int INDEX_FROM = 3;
	private final static int INDEX_TO = 4;

	private final boolean columnMode;
	
	private Table table;

	public SetTable(Composite parent, boolean columnMode) {
		this.columnMode = columnMode;

		table = new Table(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible(true);

		if (columnMode) {
			table.setHeaderVisible (true);
			for (String columnName : LABELS) {
				TableColumn column = new TableColumn(table, SWT.LEFT);
				column.setText(columnName);
				column.setWidth(100);
			}
		} else {
			new TableColumn(table, SWT.LEFT).setWidth(200);
			new TableColumn(table, SWT.LEFT).setWidth(200);
			for (String rowName : LABELS) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(0, rowName);
			}
		}
	}

	public void setLayoutData (Object layoutData) {
		table.setLayoutData(layoutData);
	}

	public void addSelectionListener (SelectionListener listener) {
		table.addSelectionListener(listener);
	}

	public void setValues(int n, Date date, String comment, Date from, Date to) {
		if (columnMode) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(INDEX_N, Integer.toString(n));
			item.setText(INDEX_DATE, DATE_FORMAT.format(date));
			item.setText(INDEX_COMMENT, comment);
			item.setText(INDEX_FROM, DATE_FORMAT.format(from));
			item.setText(INDEX_TO, DATE_FORMAT.format(to));
		} else {
			table.getItem(INDEX_N).setText(1, Integer.toString(n));
			table.getItem(INDEX_DATE).setText(1, DATE_FORMAT.format(date));
			table.getItem(INDEX_COMMENT).setText(1, comment);
			table.getItem(INDEX_FROM).setText(1, DATE_FORMAT.format(from));
			table.getItem(INDEX_TO).setText(1, DATE_FORMAT.format(to));
		}
	}

	public void reset() {
		if (columnMode) {
			for (TableItem tableItem : table.getItems()) {
				tableItem.dispose();
			}
		}
	}

}
