package key.gui;

import java.text.DateFormat;
import java.util.Locale;

import key.model.SetData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SetTable {

	private final static DateFormat DATE_FORMAT = DateFormat.getDateInstance(
			DateFormat.SHORT,
			new Locale(System.getProperty("user.language")));
	//new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final static String[] LABELS = {
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

	public void addSelectionListener (final SetSelectionListener listener) {
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				listener.selected((SetData)table.getSelection()[0].getData());
			}
		});
	}

	public void setValues(SetData setData) {
		if (columnMode) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(INDEX_N, Integer.toString(setData.getId()));
			item.setText(INDEX_DATE, DATE_FORMAT.format(setData.getCreated()));
			item.setText(INDEX_COMMENT, setData.getComment());
			item.setText(INDEX_FROM, DATE_FORMAT.format(setData.getFrom()));
			item.setText(INDEX_TO, DATE_FORMAT.format(setData.getTo()));
			item.setData(setData);
		} else {
			table.getItem(INDEX_N).setText(1, Integer.toString(setData.getId()));
			table.getItem(INDEX_DATE).setText(1, DATE_FORMAT.format(setData.getCreated()));
			table.getItem(INDEX_COMMENT).setText(1, setData.getComment());
			table.getItem(INDEX_FROM).setText(1, DATE_FORMAT.format(setData.getFrom()));
			table.getItem(INDEX_TO).setText(1, DATE_FORMAT.format(setData.getTo()));
		}
	}

	public void reset() {
		if (columnMode) {
			for (TableItem tableItem : table.getItems()) {
				tableItem.dispose();
			}
		}
	}

	public static interface SetSelectionListener {

		void selected(SetData setData);

	}
}
