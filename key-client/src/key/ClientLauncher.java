package key;

import key.gui.ClientDialog;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ClientLauncher {

	public static void main(String[] args) {
	    Display display = new Display ();
	    Shell shell = new Shell (display);
	    ClientDialog dlg = new ClientDialog(shell);
	    dlg.open();
	    display.dispose ();
	}

}
