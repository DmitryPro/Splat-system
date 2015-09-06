import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.csstudio.swt.xygraph.dataprovider.CircularBufferDataProvider;
import org.csstudio.swt.xygraph.dataprovider.Sample;
import org.csstudio.swt.xygraph.figures.Axis;
import org.csstudio.swt.xygraph.figures.Trace;
import org.csstudio.swt.xygraph.figures.XYGraph;
import org.csstudio.swt.xygraph.figures.Trace.BaseLine;
import org.csstudio.swt.xygraph.figures.Trace.ErrorBarType;
import org.csstudio.swt.xygraph.figures.Trace.TraceType;
import org.csstudio.swt.xygraph.linearscale.Range;
import org.csstudio.swt.xygraph.linearscale.AbstractScale.LabelSide;
import org.csstudio.swt.xygraph.util.XYGraphMediaFactory;
import org.eclipse.draw2d.Figure;
import org.eclipse.swt.widgets.Display;


public class XYGraphTest extends Figure
{
    public Runnable updater;

    private double updateIndex = 0;

    public CircularBufferDataProvider traceProvider;

    boolean running = false;

    private long t;

    XYGraph xyGraph;

    ArrayList<Integer> list;

    int current = 0;

    Trace trace;


    public XYGraphTest()
    {

        xyGraph = new XYGraph();
        xyGraph.setTitle("XY Graph Test");
        xyGraph.setFont(XYGraphMediaFactory.getInstance().getFont(XYGraphMediaFactory.FONT_TAHOMA));
        xyGraph.primaryXAxis.setTitle("Time");
        xyGraph.primaryYAxis.setTitle("Value");
        xyGraph.primaryXAxis.setRange(new Range(0, 200));
        xyGraph.primaryYAxis.setRange(new Range(0, 200));
        xyGraph.primaryXAxis.setDateEnabled(true);
        xyGraph.primaryYAxis.setAutoScale(true);
        xyGraph.primaryXAxis.setAutoScale(true);
        xyGraph.primaryXAxis.setShowMajorGrid(true);
        xyGraph.primaryYAxis.setShowMajorGrid(true);
        xyGraph.primaryXAxis.setAutoScaleThreshold(0);

        final Axis x2Axis = new Axis("Id=1", false);
        x2Axis.setTickLableSide(LabelSide.Secondary);
        // x2Axis.setAutoScale(true);
        xyGraph.addAxis(x2Axis);

        traceProvider = new CircularBufferDataProvider(true);
        traceProvider.setBufferSize(100);
        traceProvider.setUpdateDelay(100);

        trace = new Trace("Trace 2", xyGraph.primaryXAxis, xyGraph.primaryYAxis, traceProvider);
        trace.setDataProvider(traceProvider);
        trace.setTraceType(TraceType.SOLID_LINE);
        trace.setLineWidth(1);
        // trace2.setPointStyle(PointStyle.CIRCLE);//remove | from dots
        // trace2.setPointSize(1);
        trace.setBaseLine(BaseLine.NEGATIVE_INFINITY);
        trace.setAreaAlpha(100);
        trace.setAntiAliasing(true);
        // trace2.setErrorBarEnabled(true);
        // trace2.setDrawYErrorInArea(true);
        trace.setYErrorBarType(ErrorBarType.BOTH);
        trace.setXErrorBarType(ErrorBarType.NONE);
        trace.setErrorBarCapWidth(3);
        xyGraph.addTrace(trace);

        add(xyGraph);

        list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            list.add(i);

        t = Calendar.getInstance().getTimeInMillis();
        traceProvider.addSample(new Sample(t, 10));

        updater = new Runnable()
        {

            public void run()
            {

                t += 6000;
                if (((updateIndex >= 10) && (updateIndex <= 10.5)) || ((updateIndex >= 20) && (updateIndex <= 20.2)))
                {
                    traceProvider.addSample(new Sample(t, Double.NaN));
                    System.out.println("stopped");
                    running = false;
                }
                else
                {
                    final Sample sampe = new Sample(t, (new Random().nextInt(100) + 1));
                    traceProvider.addSample(sampe);
                }

                traceProvider.setCurrentYDataTimestamp(t);
                updateIndex += 0.1;

                if (running)
                {
                    Display.getCurrent().timerExec(1, this);
                }
            }
        };

        Display.getCurrent().timerExec((int) (1000), updater);
        running = true;

    }


    @Override
    protected void layout()
    {
        xyGraph.setBounds(bounds.getCopy().shrink(5, 5));
        super.layout();
    }


    public synchronized void addPoint(ExpandedDataObject dataObject)
    {
        t = Calendar.getInstance().getTimeInMillis();
        // System.out.println((dataObject==null) + ",");
        traceProvider.addSample(new Sample(t, dataObject.value));

    }
}