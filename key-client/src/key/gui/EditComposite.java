package key.gui;

import java.io.IOException;
import java.util.Collection;

import key.model.CsvExport;
import key.model.DBLayer;
import key.model.KeyData;
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

public class EditComposite extends Composite {

	private Button buttonExport;
	private SetTable tableSet;
	private KeyTable tableKey;
	
	private SetData selectedSetData;

	public EditComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(3, false));

		Button button = new Button(this, SWT.PUSH);
		button.setText("ќбновить");
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/refresh.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		buttonExport = new Button(this, SWT.PUSH);
		buttonExport.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		buttonExport.setText("Ёкспорт...");
		buttonExport.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/export.gif")));
		buttonExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				export();
			}
		});
		buttonExport.setEnabled(false);

		tableSet = new SetTable(this, true);
		GridData gd = new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1);
		gd.heightHint = 50;
		tableSet.setLayoutData(gd);
		tableSet.addSelectionListener(new SetTable.SetSelectionListener() {
			@Override
			public void selected(SetData setData) {
				selectedSetData = setData;
				Collection<KeyData> keys = setData.getKeys();
				tableKey.setValues(keys);
				buttonExport.setEnabled(!keys.isEmpty());
			}
		});
		tableKey = new KeyTable(this);
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
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
				e.printStackTrace();
			}
        }
	}
}
