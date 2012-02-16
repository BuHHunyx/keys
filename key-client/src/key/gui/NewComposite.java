package key.gui;

import java.util.Calendar;
import java.util.Date;

import key.model.DBLayer;
import key.model.KeyGenerator;
import key.model.SetData;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

		label = new Label(this, SWT.NONE);
		label.setText("Количество октетов:");
		comboOctet = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboOctet.setItems(new String[] {"2", "3", "4"});
		comboOctet.select(0);

		label = new Label(this, SWT.NONE);
		label.setText("Количество ключей:");
		spinnerCount = new Spinner(this, SWT.BORDER);
		spinnerCount.setMinimum(1);

		label = new Label(this, SWT.NONE);
		label.setText("Комментарий:");
		textComment = new Text(this, SWT.BORDER);
		textComment.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Button button = new Button(this, SWT.PUSH);
		GridData buttonGridData = new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1);
		button.setLayoutData(buttonGridData);
		button.setText("Создать");
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
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		buttonSave = new Button(this, SWT.PUSH);
		buttonSave.setLayoutData(buttonGridData);
		buttonSave.setText("Сохранить");
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
			String key = KeyGenerator.generateKey(octet);
			setData.addKey(key);
		}
		tableKey.setValues(setData.getKeys());
	}

	private void save() {
		int id = DBLayer.save(setData);
		setData.setId(id);
		tableSet.setValues(setData);
	}
}
