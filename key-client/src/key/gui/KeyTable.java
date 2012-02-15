package key.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class KeyTable {

	private final static String[] COLUMNS_KEY = {"Ключ", "MD5" };

	private final static int COLUMN_KEY = 0;
	private final static int COLUMN_MD5 = 1;

	private Table table;

	public KeyTable(Composite parent) {
		table = new Table(parent, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		table.setLinesVisible (true);
		table.setHeaderVisible (true);

		for (String columnName : COLUMNS_KEY) {
			TableColumn column = new TableColumn(table, SWT.LEFT);
			column.setText(columnName);
//			column.pack();
			column.setWidth(200);
		}
	}

	public void setLayoutData (Object layoutData) {
		table.setLayoutData(layoutData);
	}

	public void setValues(String key, String md5) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(COLUMN_KEY, key);
		item.setText(COLUMN_MD5, md5);
//		for(TableColumn column : tableSet.getColumns()) {
//		column.pack();
//	}
	}

	public void reset() {
		for (TableItem tableItem : table.getItems()) {
			tableItem.dispose();
		}
	}

}
