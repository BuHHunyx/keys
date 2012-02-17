package key.gui;

import java.util.Collection;

import key.model.KeyData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class KeyTable {

	private final static String[] COLUMNS_KEY = {"Ключ", "MD5" };

	private final static int COLUMN_KEY = 0;
	private final static int COLUMN_MD5 = 1;

	private Table table;

	private KeySelectionListener selectionListener;

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

	public void addSelectionListener (KeySelectionListener listener) {
		this.selectionListener = listener;
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionListener.selected((KeyData)table.getSelection()[0].getData());
			}
		});
	}

	public void setValues(Collection<KeyData> keys) {
		for (TableItem tableItem : table.getItems()) {
			tableItem.dispose();
		}
		if (null != keys) {
			for (KeyData key : keys) {
				TableItem item = new TableItem(table, SWT.NONE);
				item.setText(COLUMN_KEY, key.getKey());
				item.setText(COLUMN_MD5, key.getHash());
				item.setData(key);
			}
		}
		if (null != selectionListener) {
			selectionListener.selected(null);
		}
	}

	public void deleteCurrent() {
		TableItem[] items = table.getSelection();
		if (items.length > 0) {
			items[0].dispose();
			if (null != selectionListener) {
				selectionListener.selected(null);
			}
		}
	}

	public static interface KeySelectionListener {

		void selected(KeyData setData);

	}

}
