package com.davidmedinaospina.appnumercico;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Grafica extends AppCompatActivity {

    private XYPlot plot;

    class MyLineLabelRenderer extends XYGraphWidget.LineLabelRenderer {

        @Override
        protected void drawLabel(Canvas canvas, String text, Paint paint,
                                 float x, float y, boolean isOrigin) {
            if(isOrigin) {
                // make the origin labels red:
                final Paint originPaint = new Paint(paint);
                originPaint.setColor(Color.RED);
                super.drawLabel(canvas, text, originPaint, x, y , isOrigin);
            } else {
                super.drawLabel(canvas, text, paint, x, y , isOrigin);
            }
        }
    }


    class MySecondaryLabelRenderer extends Grafica.MyLineLabelRenderer {


        @Override
        public void drawLabel(Canvas canvas, XYGraphWidget.LineLabelStyle style,
                              Number val, float x, float y, boolean isOrigin) {
            if(val.doubleValue() % 2 == 0) {
                final Paint paint = style.getPaint();
                if(!isOrigin) {
                    paint.setColor(Color.GRAY);
                }
                super.drawLabel(canvas, style, val, x, y, isOrigin);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        int ranx1 = -6;
        int ranx2 = 6;
        int rany1 = -6;
        int rany2 = 6;

        Bundle bundle = getIntent().getExtras();
        final String x1 = bundle.getString("ranX1");
        final String x2 = bundle.getString("ranX2");
        final String y1 = bundle.getString("ranY1");
        final String y2 = bundle.getString("ranY2");
        if(!x1.isEmpty() && !x2.isEmpty() && !y1.isEmpty() && !y2.isEmpty()){
            ranx1 = Integer.parseInt(x1);
            ranx2 = Integer.parseInt(x2);
            rany1 = Integer.parseInt(y1);
            rany2 = Integer.parseInt(y2);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1);
        plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1);
        plot.setDomainBoundaries(ranx1,ranx2,BoundaryMode.FIXED);
        plot.setRangeBoundaries(rany1,rany2,BoundaryMode.FIXED);


        // create formatters to use for drawing a series using LineAndPointRenderer
        // and configure them from xml:
        LineAndPointFormatter series1Format =
                new LineAndPointFormatter(Color.RED, Color.RED, null, null);

        // use our custom renderer to make origin labels red
        plot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.BOTTOM, new Grafica.MyLineLabelRenderer());
        plot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.LEFT, new Grafica.MyLineLabelRenderer());

        // skip every other line for top and right edge labels
        plot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.RIGHT, new Grafica.MySecondaryLabelRenderer());
        plot.getGraph().setLineLabelRenderer(XYGraphWidget.Edge.TOP, new Grafica.MySecondaryLabelRenderer());

        // don't show decimal places for top and right edge labels
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.TOP).setFormat(new DecimalFormat("0"));
        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.RIGHT).setFormat(new DecimalFormat("0"));

        // create a dash effect for domain and range grid lines:
        DashPathEffect dashFx = new DashPathEffect(
                new float[]{PixelUtils.dpToPix(3), PixelUtils.dpToPix(3)}, 0);
        plot.getGraph().getDomainGridLinePaint().setPathEffect(dashFx);
        plot.getGraph().getRangeGridLinePaint().setPathEffect(dashFx);

        // add a new series' to the xyplot:
        try {
            plot.addSeries(generateSeries(ranx1, ranx2, 100), series1Format);
        }
        catch (RuntimeException err)
        {
            System.out.println(err.getMessage());
        }

    }
    protected XYSeries generateSeries(double minX, double maxX, double resolution) {
        Bundle bundle = getIntent().getExtras();
        final String exp = bundle.getString("expr");
        final double range = maxX - minX;
        final double step = range / resolution;
        List<Number> xVals = new ArrayList<>();
        List<Number> yVals = new ArrayList<>();

        double x = minX;
        while (x <= maxX) {
            Expression e = new ExpressionBuilder(exp)
                    .variables("x")
                    .build()
                    .setVariable("x", x);
            xVals.add(x);
            yVals.add(e.evaluate());
            x +=step;
        }

        return new SimpleXYSeries(xVals, yVals, "Funcion");
    }

}

