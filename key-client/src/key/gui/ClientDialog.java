package key.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ClientDialog extends Dialog {

	public ClientDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		CTabFolder tab = new CTabFolder(parent, SWT.TOP);
		tab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		CTabItem tabItem;

		tabItem = new CTabItem(tab, SWT.NONE);
		tabItem.setText("Редактирование");
		tabItem.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/edit.gif")));
		tabItem.setControl(new EditComposite(tab, SWT.NONE));

		tabItem = new CTabItem(tab, SWT.NONE);
		tabItem.setText("Создание");
		tabItem.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/add.gif")));
		tabItem.setControl(new NewComposite(tab, SWT.NONE));

		tabItem = new CTabItem(tab, SWT.NONE);
		tabItem.setText("Настройки");
		tabItem.setImage(new Image(getShell().getDisplay(), getClass().getResourceAsStream("/settings.gif")));
		tabItem.setControl(new SettingsComposite(tab, SWT.NONE));
		return tab;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, "Выход", false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Клиент");
		newShell.setImage(new Image(newShell.getDisplay(), getClass().getResourceAsStream("/logo.gif")));
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		Point pt = super.getInitialSize();
		pt.x = Math.max(pt.x, 600);
		pt.y = Math.max(pt.y, 400);
		return pt;
	}
}
