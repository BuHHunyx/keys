package key;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ClientDialog extends MessageDialog {

	private final static Random random = new Random();

	private Table table;
	private Combo comboOctet;
	private Combo comboCount;
	
	public ClientDialog(Shell parentShell) {
		super(parentShell, "Клиент", null, "Укажите параметры", 0, new String[] {"Выход"}, 0);
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		Composite area = new Composite(parent, SWT.NONE);
		area.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		area.setLayout(new GridLayout(2, false));

		Label label;

		label = new Label(area, SWT.NONE);
		label.setText("Количество октетов:");
		comboOctet = new Combo(area, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboOctet.setItems(new String[] {"2", "3", "4"});
		comboOctet.select(0);

		label = new Label(area, SWT.NONE);
		label.setText("Количество ключей:");
		comboCount = new Combo(area, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboCount.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"});
		comboCount.select(0);

		
		Button button = new Button(area, SWT.PUSH);
		button.setText("Создать");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = table.getItems();
				for (TableItem tableItem : items) {
					tableItem.dispose();
				}

				int cnt = Integer.valueOf(comboCount.getText());
				int octet = Integer.valueOf(comboOctet.getText());
				for (int i = 0; i < cnt; ++i) {
					TableItem item = new TableItem(table, SWT.NONE);
					String key = generateKey(octet);
					item.setText(0, key);
					try {
						String md5 = getMD5(key);
						item.setText(1, md5);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		label = new Label(area, SWT.NONE);

		table = new Table(area, /*SWT.SINGLE | SWT.FULL_SELECTION*/SWT.BORDER);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setLinesVisible (true);
		table.setHeaderVisible (true);

		TableColumn columnKey = new TableColumn(table, SWT.LEFT);
		columnKey.setText("Ключ");
		columnKey.setWidth(200);
		TableColumn columnMd5 = new TableColumn(table, SWT.LEFT);
		columnMd5.setText("MD5");
		columnMd5.setWidth(200);
		return area;
	}

	private static final String generateKey(int octet) {
		String result = "XXXX";
		
		for (int i = 0; i < (octet - 1); ++i) {
			result += '-';
			for(int j = 0; j < 4; ++j) {
				result += random.nextInt(10);
			}
		}
		return result;
	}

	private static final String getMD5(String value) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		byte[] bytesOfMessage = value.getBytes("UTF-8");
		byte[] thedigest = md.digest(bytesOfMessage);
		BigInteger bigInt = new BigInteger(1,thedigest);
		String md5 = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while(md5.length() < 32 ){
			md5 = "0" + md5;
		}

		return md5;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#setShellStyle(int)
	 */
	protected void setShellStyle(int newShellStyle)
	{
	    super.setShellStyle(newShellStyle | SWT.RESIZE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#getInitialSize()
	 */
	protected Point getInitialSize()
	{
		Point pt = super.getInitialSize();
		pt.x = Math.max(pt.x, 400);
		pt.y = Math.max(pt.y, 500);
		return pt;
	}
}
