package key.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ClientDialog extends MessageDialog {

	public ClientDialog(Shell parentShell) {
		super(parentShell, "Клиент", null, /*"Укажите параметры"*/null, 0, new String[] {"Выход"}, 0);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Control area = new ClientComposite(parent, SWT.NONE);
		area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return area;
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
