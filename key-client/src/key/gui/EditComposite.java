package key.gui;

import java.io.IOException;
import java.util.Collection;

import key.model.CsvExport;
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
import org.eclipse.swt.widgets.MessageBox;

public class EditComposite extends Composite {

	private Button buttonExport;
	private SetTable tableSet;
	private KeyTable tableKey;
	private Button buttonDeleteSet;
	private Button buttonDeleteKey;

	private SetData selectedSetData;
	private KeyData selectedKeyData;

	public EditComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(3, false));

		GridData buttonGridData = new GridData(SWT.NONE, SWT.NONE, false, false);

		Button button = new Button(this, SWT.PUSH);
		button.setLayoutData(buttonGridData);
		button.setText("��������");
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/refresh.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		buttonExport = new Button(this, SWT.PUSH);
		buttonExport.setLayoutData(buttonGridData);
		buttonExport.setText("�������...");
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
		tableKey.addSelectionListener(new KeyTable.KeySelectionListener() {
			@Override
			public void selected(KeyData keyData) {
				selectedKeyData = keyData;
				buttonDeleteKey.setEnabled(keyData != null);
			}
		});

		buttonDeleteSet = new Button(this, SWT.PUSH);
		buttonDeleteSet.setLayoutData(buttonGridData);
		buttonDeleteSet.setText("�������� �����");
		buttonDeleteSet.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		buttonDeleteSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedSet();
			}
		});
		buttonDeleteSet.setEnabled(false);

		buttonDeleteKey = new Button(this, SWT.PUSH);
		buttonDeleteKey.setText("�������� �����");
		buttonDeleteKey.setLayoutData(buttonGridData);
		buttonDeleteKey.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/delete.gif")));
		buttonDeleteKey.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				deleteSelectedKey();
			}
		});
		buttonDeleteKey.setEnabled(false);
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
				throw new KeyException("������ ��� ��������", e);
			}
        }
	}

	private void deleteSelectedSet() {
		MessageBox mb = new MessageBox(getShell(), SWT.YES | SWT.NO);
		mb.setText("�������� �����");
		mb.setMessage("�� �������?");
		if(SWT.YES == mb.open()) {
			selectedSetData.delete();
			tableSet.deleteCurrent();
		}
	}

	private void deleteSelectedKey() {
		MessageBox mb = new MessageBox(getShell(), SWT.YES | SWT.NO);
		mb.setText("�������� �����");
		mb.setMessage("�� �������?");
		if(SWT.YES == mb.open()) {
			selectedSetData.deleteKey(selectedKeyData);
			tableKey.deleteCurrent();
		}
	}

}
