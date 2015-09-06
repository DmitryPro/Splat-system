
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;


/**
 * Class
 * 
 * @author Pavel Gordon
 *
 */
public class TableDisplayer
{
    private Display display;

    private Shell shell;

    private Table table;

    private String[] columnTitles;


    public TableDisplayer()
    {
        display = new Display();
        shell = new Shell(display);
        shell.setText("Splat Client");
        shell.setLayout(new FillLayout());
        table = new Table(shell, SWT.BORDER | SWT.MULTI);
        table.setSize(600, 400);
        table.setHeaderVisible(true);
        columnTitles = new String[] { "Provider id", "id", "value" };
        for (int i = 0; i < columnTitles.length; i++)
        {
            TableColumn column = new TableColumn(table, SWT.NULL);
            column.setText(columnTitles[i]);
        }
        // new Thread(new MakeNewThread()).start();
        /*
         * Some boilerplate code below. TODO make this look better
         */
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                for (;;)
                {
                    if (table.isDisposed())
                        return;
                    display.asyncExec(new Runnable()
                    {
                        @Override
                        public void run()
                        {

                            if (table.isDisposed())
                                return;
                            if (ClientEventHandler.data.size() > 0)
                            {
                                ExpandedDataObject obj = ClientEventHandler.data.remove(0);
                                TableItem item = new TableItem(table, SWT.NULL);
                                item.setText(new String[] { obj.providerId + "", obj.id + "", obj.value + "" });
                                for (int i = 0; i < columnTitles.length; i++)
                                    table.getColumn(i).pack();

                            }

                        }
                    });
                }
            }
        };
        thread.start();
        /*
         * starts connection to server in other thread. Maybe is must run when btn "connect" is clicked
         */

        new Thread(new WebSocketClient()).start();

        shell.setSize(600, 400);
        shell.open();

        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }


    public class MakeNewThread implements Runnable
    {
        @Override
        public void run()
        {

            while (true)
            {
                if (table.isDisposed())
                    return;
                display.asyncExec(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (table.isDisposed())
                            return;
                        if (ClientEventHandler.data.size() > 0)
                        {
                            ExpandedDataObject obj = ClientEventHandler.data.remove(0);
                            TableItem item = new TableItem(table, SWT.NULL);
                            item.setText(new String[] { obj.providerId + "", obj.id + "", obj.value + "" });
                            for (int i = 0; i < columnTitles.length; i++)
                                table.getColumn(i).pack();

                        }

                    }
                });
            }

        }
    }


    public static void main(String[] args)
    {
        TableDisplayer td = new TableDisplayer();
        // new Thread(new WebSocketClient()).start();

    }
}