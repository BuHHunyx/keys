package key.gui;

import java.util.Calendar;
import java.util.Date;

import key.model.DBLayer;
import key.model.KeyGenerator;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.jface.dialogs.MessageDialog;
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

public class ClientComposite extends Composite {

	private int num = 0;

	private TableSet tableSet;
	private TableKey tableKey;
	private Combo comboOctet;
	private Spinner spinnerCount;
	private Text textComment;

	public ClientComposite(Composite parent, int style) {
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
		button.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		button.setText("Создать");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				generate();
			}
		});

		tableSet = new TableSet(this, new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));

		tableKey = new TableKey(this, new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		button = new Button(this, SWT.PUSH);
		button.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1));
		button.setText("Сохранить");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
	}

	private void generate() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		tableSet.setValues(++num, date, textComment.getText(), date, calendar.getTime());

		tableKey.reset();

		int cnt = Integer.valueOf(spinnerCount.getText());
		int octet = Integer.valueOf(comboOctet.getText());
		for (int i = 0; i < cnt; ++i) {
			String key = KeyGenerator.generateKey(octet);
			String md5 = DigestUtils.md5Hex(key);
			tableKey.setValues(key, md5);
		}
	}

	private void save() {
		DBLayer.save();
	}
}
