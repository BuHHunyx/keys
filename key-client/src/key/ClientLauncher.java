package key;

import key.gui.ClientWindow;

import org.eclipse.swt.widgets.Display;

public class ClientLauncher {

	public static void main(String[] args) {
	    Display display = new Display();
	    new ClientWindow().open();
	    display.dispose();
	}

}
