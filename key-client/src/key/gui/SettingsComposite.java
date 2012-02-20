package key.gui;

import key.model.DBLayer;
import key.model.KeyProperties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SettingsComposite extends Composite {

	private Text textDbUrl;
	private Text textDbUsername;
	private Text textDbPassword;

	public SettingsComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout());

		Button button = new Button(this, SWT.PUSH);
		button.setText("Create tables");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				create();
			}
		});

		Group groupDB = new Group(this, SWT.NONE);
		groupDB.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		groupDB.setLayout(new GridLayout(2, false));
		groupDB.setText("��������� ���������� � ����� ������");

		Label label;

		label = new Label(groupDB, SWT.NONE);
		label.setText("URL-����� �����������");
		textDbUrl = new Text(groupDB, SWT.BORDER);
		textDbUrl.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		textDbUrl.setText(KeyProperties.getDbUrl());

		label = new Label(groupDB, SWT.NONE);
		label.setText("������������");
		textDbUsername = new Text(groupDB, SWT.BORDER);
		textDbUsername.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		textDbUsername.setText(KeyProperties.getDbUsername());

		label = new Label(groupDB, SWT.NONE);
		label.setText("������");
		textDbPassword = new Text(groupDB, SWT.BORDER | SWT.PASSWORD);
		textDbPassword.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));
		textDbPassword.setText(KeyProperties.getDbPassword());

		button = new Button(groupDB, SWT.PUSH);
		button.setText("���������");
		button.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/save.gif")));
		button.setLayoutData(new GridData(SWT.RIGHT, SWT.NONE, false, false, 2, 1));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				save();
			}
		});
	}

	private void create() {
		DBLayer.create();
	}

	private void save() {
		KeyProperties.saveDbProperties(textDbUrl.getText(), textDbUsername.getText(), textDbPassword.getText());
	}
}
