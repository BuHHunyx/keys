package key.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ClientDialog extends Dialog {

	public ClientDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		TabFolder tab = new TabFolder(parent, SWT.TOP);
		TabItem tabItem;
		
		tabItem = new TabItem(tab, SWT.NONE);
		tabItem.setText("Создание");
		tabItem.setControl(new NewComposite(tab, SWT.NONE));

		tabItem = new TabItem(tab, SWT.NONE);
		tabItem.setText("Редактирование");
		tabItem.setControl(new EditComposite(tab, SWT.NONE));

		tab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return tab;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID,
				"Выход", false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Клиент");
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
