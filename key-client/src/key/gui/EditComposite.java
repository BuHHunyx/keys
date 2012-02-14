package key.gui;

import java.util.Date;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class EditComposite extends Composite {

	private Button buttonExport;
	private SetTable tableSet;
	private KeyTable tableKey;

	public EditComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(3, false));

		Button button = new Button(this, SWT.PUSH);
		button.setText("ќбновить");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				refresh();
			}
		});

		buttonExport = new Button(this, SWT.PUSH);
		buttonExport.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		buttonExport.setText("Ёкспорт...");
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
		tableSet.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Table table = (Table)e.widget;
				buttonExport.setEnabled(table.getSelection().length > 0);
			}
		});
		tableKey = new KeyTable(this);
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	}

	private void refresh() {
		tableSet.setValues(1, new Date(), "123", new Date(), new Date());
		MessageDialog.openWarning(getShell(), null, "UNDER CONSTRUCTION");
	}

	private void export() {
		MessageDialog.openWarning(getShell(), null, "UNDER CONSTRUCTION");
	}
}
