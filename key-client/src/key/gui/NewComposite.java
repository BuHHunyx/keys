package key.gui;

import java.util.Calendar;
import java.util.Date;

import key.model.KeyGenerator;
import key.model.SetData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class NewComposite extends Composite {

	private Combo comboOctet;
	private Spinner spinnerCount;
	private Text textComment;
	private SetTable tableSet;
	private KeyTable tableKey;
	private Button buttonSave;

	private SetData setData;

	public NewComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(2, false));

		Label label;
		
		GridData gd = new GridData(SWT.NONE, SWT.NONE, false, false); 

		label = new Label(this, SWT.NONE);
		label.setText("���������� �������:");
		comboOctet = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboOctet.setLayoutData(gd);
		comboOctet.setItems(new String[] {"2", "3", "4"});
		comboOctet.select(0);

		label = new Label(this, SWT.NONE);
		label.setText("���������� ������:");
		spinnerCount = new Spinner(this, SWT.BORDER);
		spinnerCount.setLayoutData(gd);
		spinnerCount.setMinimum(1);

		label = new Label(this, SWT.NONE);
		label.setText("�����������:");
		textComment = new Text(this, SWT.BORDER);
		textComment.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Button button = new Button(this, SWT.PUSH);
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/new.gif")));
		GridData buttonGridData = new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1);
		button.setLayoutData(buttonGridData);
		button.setText("�������");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				generate();
				buttonSave.setEnabled(true);
			}
		});

		tableSet = new SetTable(this, false);
		tableSet.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		tableKey = new KeyTable(this);
		GridData gdKeyTable = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gdKeyTable.heightHint = 50;
		tableKey.setLayoutData(gdKeyTable);

		buttonSave = new Button(this, SWT.PUSH);
		buttonSave.setLayoutData(buttonGridData);
		buttonSave.setText("���������");
		buttonSave.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/export.gif")));
		buttonSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
		buttonSave.setEnabled(false);
	}

	private void generate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		setData = new SetData(date, textComment.getText(), date, calendar.getTime());
		tableSet.setValues(setData);

		int cnt = Integer.valueOf(spinnerCount.getText());
		int octet = Integer.valueOf(comboOctet.getText());
		for (int i = 0; i < cnt; ++i) {
			String key;
			do {
				key = KeyGenerator.generateKey(octet);
			} while (setData.isKeyExists(key));
			setData.addKey(key);
		}
		tableKey.setValues(setData.getKeys());
	}

	private void save() {
		setData.save();
		tableSet.setValues(setData);
	}
}
