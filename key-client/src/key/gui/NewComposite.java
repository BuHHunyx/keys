package key.gui;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import key.model.KeyException;
import key.model.KeyGenerator;
import key.model.SetData;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class NewComposite extends Composite {

	private static final Pattern PATTERN_ALPHANUM = Pattern.compile("\\p{Alnum}+");
	private static final int CUSTOM_OCTET_LENGTH = 6;

	private Spinner spinnerOctet;
	private Spinner spinnerCount;
	private Text textOctet;
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
		label.setText("Количество октетов:");
		spinnerOctet = new Spinner(this, SWT.BORDER);
		spinnerOctet.setLayoutData(gd);
		spinnerOctet.setMinimum(2);
		spinnerOctet.setMaximum(8);

		label = new Label(this, SWT.NONE);
		label.setText("Количество ключей:");
		spinnerCount = new Spinner(this, SWT.BORDER);
		spinnerCount.setLayoutData(gd);
		spinnerCount.setMinimum(1);
		spinnerCount.setMaximum(40000);

		label = new Label(this, SWT.NONE);
		label.setText("Настраиваемый октет:");
		textOctet = new Text(this, SWT.BORDER);
		textOctet.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		textOctet.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent event) {
				int len = event.text.length();
				if (len > 0) {
					int allowed = CUSTOM_OCTET_LENGTH - ((Text) event.widget).getText().length();
					if (len > allowed) {
						event.text = event.text.substring(0, allowed);
					}
					if (!PATTERN_ALPHANUM.matcher(event.text).matches()) {
						event.doit = false;
					} else {
						event.text = event.text.toUpperCase();
					}
				}
			}
		});

		label = new Label(this, SWT.NONE);
		label.setText("Комментарий:");
		textComment = new Text(this, SWT.BORDER);
		textComment.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Button button = new Button(this, SWT.PUSH);
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/new.gif")));
		GridData buttonGridData = new GridData(SWT.NONE, SWT.NONE, false, false, 2, 1);
		button.setLayoutData(buttonGridData);
		button.setText("Создать");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
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
		buttonSave.setText("Сохранить");
		buttonSave.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/export.gif")));
		buttonSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
				buttonSave.setEnabled(false);
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

		final int octet = spinnerOctet.getSelection();
		final String octetValue = textOctet.getText();
		final int keys = spinnerCount.getSelection();
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					monitor.beginTask("Генерация ключей…", keys);
					for (int i = 0; i < keys; ++i) {
						String key;
						do {
							key = KeyGenerator.generateKey(octetValue, octet);
						} while (setData.isKeyExists(key));
						setData.addKey(key);
						monitor.worked(1);
					}
				}
			});
		} catch (InvocationTargetException e) {
			throw new KeyException("Ошибка при генерации ключей", e.getCause());
		} catch (InterruptedException e) {
		}
		tableKey.setValues(setData.getKeys());
	}

	private void save() {
		setData.save();
		tableSet.setValues(setData);
	}
}
