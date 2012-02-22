package key.gui;

import java.io.IOException;
import java.util.List;

import key.model.CsvExport;
import key.model.KeyData;
import key.model.KeyException;
import key.model.SetData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

public class EditComposite extends Composite {

	private Text textFilter;
	private SetTable tableSet;
	private KeyTable tableKey;
	private Button buttonDeleteSet;
	private Button buttonDeleteKey;
	private Button buttonExport;

	private SetData selectedSetData;
	private KeyData selectedKeyData;

	public EditComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		Group groupFilter = new Group(this, SWT.NONE);
		groupFilter.setText("Поиск ключей");
		groupFilter.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		groupFilter.setLayout(new GridLayout(2, false));

		textFilter = new Text(groupFilter, SWT.BORDER);
		textFilter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Button button = new Button(groupFilter, SWT.PUSH);
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/search.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				filter();
			}
		});

		button = new Button(this, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		button.setText("Показать все");
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/refresh.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		SashForm sash = new SashForm(this, SWT.VERTICAL);
		sash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		tableSet = new SetTable(sash, true);
		tableSet.addSelectionListener(new SetTable.SetSelectionListener() {
			@Override
			public void selected(SetData setData) {
				selectedSetData = setData;
				List<KeyData> keys = null;
				if (null != setData) {
					keys = setData.getKeys();
				}
				tableKey.setValues(keys);
				buttonExport.setEnabled((keys != null) && !keys.isEmpty());
				buttonDeleteSet.setEnabled(keys != null);
			}
		});

		Composite keyComposite = new Composite(sash, SWT.NONE);
		GridLayout gl = new GridLayout(2, false);
		gl.marginWidth = 0;
		keyComposite.setLayout(gl);
		tableKey = new KeyTable(keyComposite);
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 3));
		tableKey.addSelectionListener(new KeyTable.KeySelectionListener() {
			@Override
			public void selected(KeyData keyData) {
				selectedKeyData = keyData;
				buttonDeleteKey.setEnabled(keyData != null);
			}
		});

		buttonDeleteSet = new Button(keyComposite, SWT.PUSH);
		buttonDeleteSet.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		buttonDeleteSet.setText("Удаление пачки");
		buttonDeleteSet.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		buttonDeleteSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedSet();
			}
		});
		buttonDeleteSet.setEnabled(false);

		buttonDeleteKey = new Button(keyComposite, SWT.PUSH);
		buttonDeleteKey.setText("Удаление ключа");
		buttonDeleteKey.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		buttonDeleteKey.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		buttonDeleteKey.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedKey();
			}
		});
		buttonDeleteKey.setEnabled(false);

		buttonExport = new Button(keyComposite, SWT.PUSH);
		buttonExport.setLayoutData(new GridData(SWT.FILL, SWT.NONE, false, false));
		buttonExport.setText("Экспорт пачки…");
		buttonExport.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/saveas.gif")));
		buttonExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				export();
			}
		});
		buttonExport.setEnabled(false);

	}

	private void filter() {
		tableSet.reset();
		for(SetData setData : SetData.listFilter(textFilter.getText())) {
			tableSet.setValues(setData);
		}
	}

	private void refresh() {
		tableSet.reset();
		for(SetData setData : SetData.listAll()) {
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

	private void deleteSelectedSet() {
		MessageBox mb = new MessageBox(getShell(), SWT.YES | SWT.NO);
		mb.setText("Удаление пачки");
		mb.setMessage("Вы уверены?");
		if(SWT.YES == mb.open()) {
			selectedSetData.delete();
			tableSet.deleteCurrent();
		}
	}

	private void deleteSelectedKey() {
		MessageBox mb = new MessageBox(getShell(), SWT.YES | SWT.NO);
		mb.setText("Удаление ключа");
		mb.setMessage("Вы уверены?");
		if(SWT.YES == mb.open()) {
			selectedSetData.deleteKey(selectedKeyData);
			tableKey.deleteCurrent();
		}
	}

}
