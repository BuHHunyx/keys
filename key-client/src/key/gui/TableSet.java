package key.gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableSet {

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final static String[] ROWS_SET = new String[] {
		"Номер пачки",
		"Дата формирования",
		"Комментарий",
		"Дата начала действия",
		"Дата окончания действия"
	};

	private final static int ROW_N = 0;
	private final static int ROW_DATE = 1;
	private final static int ROW_COMMENT = 2;
	private final static int ROW_FROM = 3;
	private final static int ROW_TO = 4;

	private Table table;

	public TableSet(Composite parent, Object layoutData) {
		table = new Table(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLayoutData(layoutData);
		table.setLinesVisible (true);

		new TableColumn(table, SWT.LEFT).setWidth(200);
		new TableColumn(table, SWT.LEFT).setWidth(200);
		for (String rowName : ROWS_SET) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, rowName);
		}
	}

	public void setValues(int n, Date date, String comment, Date from, Date to) {
		table.getItem(ROW_N).setText(1, Integer.toString(n));
		table.getItem(ROW_DATE).setText(1, DATE_FORMAT.format(date));
		table.getItem(ROW_COMMENT).setText(1, comment);
		table.getItem(ROW_FROM).setText(1, DATE_FORMAT.format(from));
		table.getItem(ROW_TO).setText(1, DATE_FORMAT.format(to));
	}
}
