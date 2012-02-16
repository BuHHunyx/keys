package key.gui;

import key.model.DBLayer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class SettingsComposite extends Composite {

	public SettingsComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(2, false));

		Button button = new Button(this, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		button.setText("Создать базу");
		//button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/.gif")));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				create();
			}
		});
	}

	private void create() {
		DBLayer.create();
	}

}
