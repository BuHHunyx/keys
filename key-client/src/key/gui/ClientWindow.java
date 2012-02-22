package key.gui;

import key.model.KeyException;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ClientWindow extends Window implements Window.IExceptionHandler {

	public ClientWindow() {
		super((Shell)null);
		setBlockOnOpen(true);
		setExceptionHandler(this);
	}

	@Override
	protected Control createContents(Composite parent) {
		CTabFolder tab = new CTabFolder(parent, SWT.TOP | SWT.BORDER);
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

		//createButton(parent, IDialogConstants.CANCEL_ID, "Выход", false);
		return tab;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Клиент");
		newShell.setImage(new Image(newShell.getDisplay(), getClass().getResourceAsStream("/logo.gif")));
	}

	@Override
	public void handleException(Throwable t) {
		Throwable cause = (t instanceof KeyException) ? ((KeyException)t).getCause() : t;
		ErrorDialog.openError(getShell(), "Ошибка", t.getMessage(), new Status(IStatus.ERROR, "key-client", cause.getMessage(), cause));
	}

}
