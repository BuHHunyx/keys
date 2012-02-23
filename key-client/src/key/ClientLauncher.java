package key;

import key.gui.ClientWindow;

import org.eclipse.swt.widgets.Display;

public class ClientLauncher {

	private static final String APP_NAME = "key-client";

	public static void main(String[] args) {
		if (!AppLock.setLock(APP_NAME)) {
			return;
		}
		try {
			Display display = new Display();
			new ClientWindow().open();
			display.dispose();
		} finally {
			AppLock.releaseLock();
		}
	}

}
