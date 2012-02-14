package key;

import key.gui.ClientDialog;

import org.eclipse.swt.widgets.Display;

public class ClientLauncher {

	public static void main(String[] args) {
	    Display display = new Display ();
	    ClientDialog dlg = new ClientDialog(null);
	    dlg.open();
	    display.dispose ();
	}

}
