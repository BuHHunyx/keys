package key;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class ClientComposite extends Composite {

	private final static Random random = new Random();
	private final static String[] ROWS_SET = new String[] {"Номер пачки", "Дата формирования", "Комментарий" , "Дата начала действия", "Дата окончания действия",  };
	private final static String[] COLUMNS_KEY = new String[] {"Ключ", "MD5" };

	private final static int ROW_N = 0;
	private final static int ROW_DATE = 1;
	private final static int ROW_COMMENT = 2;
	private final static int ROW_FROM = 3;
	private final static int ROW_TO = 4;

	private final static int COLUMN_KEY = 0;
	private final static int COLUMN_MD5 = 1;
	
	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private int num = 0;

	private Table tableSet;
	private Table tableKey;
	private Combo comboOctet;
	private Combo comboCount;
	private Text textComment;

	public ClientComposite(Composite parent, int style) {
		super(parent, style);

		setLayout(new GridLayout(2, false));

		Label label;

		label = new Label(this, SWT.NONE);
		label.setText("Количество октетов:");
		comboOctet = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboOctet.setItems(new String[] {"2", "3", "4"});
		comboOctet.select(0);

		label = new Label(this, SWT.NONE);
		label.setText("Количество ключей:");
		comboCount = new Combo(this, SWT.DROP_DOWN | SWT.READ_ONLY);
		comboCount.setItems(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9"});
		comboCount.select(0);

		label = new Label(this, SWT.NONE);
		label.setText("Комментарий:");
		textComment = new Text(this, SWT.BORDER);
		textComment.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false));

		Button button = new Button(this, SWT.PUSH);
		button.setText("Создать");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fillResults();
			}
		});
		label = new Label(this, SWT.NONE);

		tableSet = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		tableSet.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 2, 1));
		tableSet.setLinesVisible (true);
//		tableSet.setHeaderVisible (true);

		new TableColumn(tableSet, SWT.LEFT).setWidth(200);
		new TableColumn(tableSet, SWT.LEFT).setWidth(200);
		for (String rowName : ROWS_SET) {
			TableItem item = new TableItem(tableSet, SWT.NONE);
			item.setText(0, rowName);
		}

		tableKey = new Table(this, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		tableKey.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		tableKey.setLinesVisible (true);
		tableKey.setHeaderVisible (true);

		for (String columnName : COLUMNS_KEY) {
			TableColumn column = new TableColumn(tableKey, SWT.LEFT);
			column.setText(columnName);
//			column.pack();
			column.setWidth(200);
		}
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

	private void fillResults() {
		++num;
		tableSet.getItem(ROW_N).setText(1, Integer.toString(num));
		Date date = new Date();
		tableSet.getItem(ROW_DATE).setText(1, DATE_FORMAT.format(date));
		tableSet.getItem(ROW_COMMENT).setText(1, textComment.getText());
		tableSet.getItem(ROW_FROM).setText(1, DATE_FORMAT.format(date));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, 1);
		tableSet.getItem(ROW_TO).setText(1, DATE_FORMAT.format(calendar.getTime()));

		TableItem[] items = tableKey.getItems();
		for (TableItem tableItem : items) {
			tableItem.dispose();
		}

		int cnt = Integer.valueOf(comboCount.getText());
		int octet = Integer.valueOf(comboOctet.getText());
		for (int i = 0; i < cnt; ++i) {
			TableItem item = new TableItem(tableKey, SWT.NONE);
			String key = generateKey(octet);
			item.setText(COLUMN_KEY, key);
			String md5 = DigestUtils.md5Hex(key);
			item.setText(COLUMN_MD5, md5);
		}
//		for(TableColumn column : tableSet.getColumns()) {
//			column.pack();
//		}
	}
}
