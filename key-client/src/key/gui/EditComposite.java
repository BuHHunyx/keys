package key.gui;

import java.io.IOException;
import java.util.Collection;

import key.model.CsvExport;
import key.model.DBLayer;
import key.model.KeyData;
import key.model.KeyException;
import key.model.SetData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;

public class EditComposite extends Composite {

	private Button buttonExport;
	private SetTable tableSet;
	private KeyTable tableKey;
	private Button buttonDeleteSet;

	private SetData selectedSetData;

	public EditComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(3, false));

		GridData buttonGridData = new GridData(SWT.NONE, SWT.NONE, false, false);

		Button button = new Button(this, SWT.PUSH);
		button.setLayoutData(buttonGridData);
		button.setText("Обновить");
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/refresh.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		buttonExport = new Button(this, SWT.PUSH);
		buttonExport.setLayoutData(buttonGridData);
		buttonExport.setText("Экспорт...");
		buttonExport.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/export.gif")));
		buttonExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				export();
			}
		});
		buttonExport.setEnabled(false);

		new Label(this, SWT.NONE);

		tableSet = new SetTable(this, true);
		GridData gd = new GridData(SWT.FILL, SWT.NONE, true, false, 3, 1);
		gd.heightHint = 50;
		tableSet.setLayoutData(gd);
		tableSet.addSelectionListener(new SetTable.SetSelectionListener() {
			@Override
			public void selected(SetData setData) {
				selectedSetData = setData;
				Collection<KeyData> keys = null;
				if (null != setData) {
					keys = setData.getKeys();
				}
				tableKey.setValues(keys);
				buttonExport.setEnabled((keys != null) && !keys.isEmpty());
				buttonDeleteSet.setEnabled(keys != null);
			}
		});
		tableKey = new KeyTable(this);
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 2));

		buttonDeleteSet = new Button(this, SWT.PUSH);
		buttonDeleteSet.setLayoutData(buttonGridData);
		buttonDeleteSet.setText("Удаление пачки");
		buttonDeleteSet.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		buttonDeleteSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSet(selectedSetData);
			}
		});
		buttonDeleteSet.setEnabled(false);

		button = new Button(this, SWT.PUSH);
		button.setText("Удаление ключа");
		button.setLayoutData(buttonGridData);
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteKey();
			}
		});
		button.setEnabled(false);
	}

	private void refresh() {
		tableSet.reset();
		for(SetData setData : DBLayer.load()) {
			tableSet.setValues(setData);
		}
	}

	private void export() {
		FileDialog fd = new FileDialog(getShell(), SWT.SAVE);
		String[] filterExt = { "*.csv" };
		fd.setFilterExtensions(filterExt);
        String selected = fd.open();
        if (null != selected) {
    		try {
				CsvExport.export(selected, selectedSetData);
			} catch (IOException e) {
				throw new KeyException("Ошибка при экспорте", e);
			}
        }
	}

	private void deleteSet(SetData setData) {
		DBLayer.delete(setData);
		tableSet.deleteCurrent();
	}

	private void deleteKey() {
//		tableKey;
	}

}
