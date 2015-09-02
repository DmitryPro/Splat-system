import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableShellExample {

	Display d;

	Shell s;

	TableShellExample() {
		d = new Display();
		s = new Shell(d);

		s.setSize(250, 200);

		s.setText("A Table Shell Example");
		s.setLayout(new FillLayout());

		final Table t = new Table(s, SWT.BORDER);

		TableColumn tc1 = new TableColumn(t, SWT.CENTER);
		TableColumn tc2 = new TableColumn(t, SWT.CENTER);
		TableColumn tc3 = new TableColumn(t, SWT.CENTER);
		tc1.setText("Provider id");
		tc2.setText("id");
		tc3.setText("value");
		tc1.setWidth(70);
		tc2.setWidth(70);
		tc3.setWidth(80);
		t.setHeaderVisible(true);
		new Thread(new Client()).start();
		TableItem item1 = new TableItem(t, SWT.NONE);
		item1.setText(new String[] { "1", "5", "5" });
		TableItem item2 = new TableItem(t, SWT.NONE);
		item2.setText(new String[] { "1", "5", "5" });
		TableItem item3 = new TableItem(t, SWT.NONE);
		item3.setText(new String[] { "1", "7", "7" });
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				while (true) {
					if (ClientWebSocket.data.size() > 0) {
						ExpandedDataObject obj = ClientWebSocket.data.remove(0);
						TableItem item = new TableItem(t, SWT.NULL);
						item.setText(new String[] { obj.providerId + "", obj.id + "", obj.value + "" });
						// table.getColumn(tsize++).pack();
						s.pack();
					} else {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		Thread thread = new Thread() {
			public void run() {
				while (true) {
					if (ClientWebSocket.data.size() > 0) {
						ExpandedDataObject obj = ClientWebSocket.data.remove(0);
						TableItem item = new TableItem(t, SWT.NULL);
						item.setText(new String[] { obj.providerId + "", obj.id + "", obj.value + "" });
						// table.getColumn(tsize++).pack();
						s.pack();

						try {
							Thread.sleep(500);
						} catch (Throwable t) {
						}
					}
				}
			}
		};
		thread.start();
		s.open();
		while (!s.isDisposed()) {
			if (!d.readAndDispatch())
				d.sleep();
		}
		d.dispose();
	}

	public static void main(String[] argv) {
		new TableShellExample();
	}

}