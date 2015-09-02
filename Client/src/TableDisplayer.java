
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class TableDisplayer {

	public static void main(String[] args) {
		final Display display = new Display();
		final Image image = new Image(display, 16, 16);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillRectangle(image.getBounds());
		gc.dispose();
		final Shell shell = new Shell(display);
		shell.setText("Lazy Table");
		shell.setLayout(new FillLayout());
		final Table table = new Table(shell, SWT.BORDER |SWT.MULTI);
		table.setSize(600, 400);
		table.setHeaderVisible(true);
	    final String[] titles = { "Provider id", "id", "value" };
	    for (int i = 0; i < titles.length; i++) {
	        TableColumn column = new TableColumn(table, SWT.NULL);
	        column.setText(titles[i]);
	    }
		new Thread(new Client()).start();
		Thread thread = new Thread() {
			@Override
			public void run() {
				for (;;) {
					if (table.isDisposed())
						return;
					display.asyncExec(new Runnable() {
						@Override
						public void run() {

							if (table.isDisposed())
								return;
							if (ClientWebSocket.data.size() > 0) {
								ExpandedDataObject obj = ClientWebSocket.data.remove(0);
								TableItem item = new TableItem(table, SWT.NULL);
								item.setText(new String[] { obj.providerId + "", obj.id + "", obj.value + "" });
								for(int i = 0; i < titles.length;i++)
									table.getColumn(i).pack();
								
							}
							/*
							 * TableItem item = new TableItem (table, SWT.NONE);
							 * item.setText ("Table Item " + index [0]);
							 */
						}
					});
				}
			}
		};
		thread.start();
		shell.setSize(600, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
		display.dispose();
	}
}