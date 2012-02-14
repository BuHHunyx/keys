package key.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class ClientDialog extends MessageDialog {

	public ClientDialog(Shell parentShell) {
		super(parentShell, "Клиент", null, /*"Укажите параметры"*/null, 0, new String[] {"Выход"}, 0);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
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

	protected void setShellStyle(int newShellStyle) {
	    super.setShellStyle(newShellStyle | SWT.RESIZE);
	}

	protected Point getInitialSize() {
		Point pt = super.getInitialSize();
		pt.x = Math.max(pt.x, 600);
		pt.y = Math.max(pt.y, 400);
		return pt;
	}
}
