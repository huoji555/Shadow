package Profile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class Profiler extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    private Timeable timeable;

    /**
     * Timeable defines the methods an object must provide to work with Profiler
     *
     */
    public interface Timeable {
        //执行在启动计时之前所需的任何工作
        public void setup(int n);

        //执行我们试图测量的任何操作
        public void timeMe(int n);
    }


    public Profiler(String title, Timeable timeable) {
        super(title);
        this.timeable = timeable;
    }


    /**
     * Invokes timeIt with a range of `n` from `startN` until runtime exceeds `endMillis`.
     * 调用TimeIt 追踪从n到StartN(数据规模),直到超过运行时间
     * @return
     */
    public XYSeries timingLoop(int startN, int endMillis) {
        final XYSeries series = new XYSeries("Time (ms)");

        int n = startN;
        for (int i=0; i<20; i++) {
            // run it once to warm up(233333,wram up)
            timeIt(n);

            // then start timing
            long total = 0;

            // run 10 times and add up total runtime(增加十次数据规模递增)
            for (int j=0; j<10; j++) {
                total += timeIt(n);
            }
            System.out.println(n + ", " + total);

            // don't store data until we get to 4ms
            if (total > 4) {
                series.add(n, total);
            }

            // stop when the runtime exceeds the end threshold
            if (total > endMillis) {
                break;
            }
            // otherwise double the size and continue（递增倍率Double）
            n *= 2;
        }
        return series;
    }


    /**
     * Invokes setup and timeMe on the embedded Timeable.（计时器）
     */
    public long timeIt(int n) {
        timeable.setup(n);
        final long startTime = System.currentTimeMillis();
        timeable.timeMe(n);
        final long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    /**
     * Plots the results.（绘制结果）
     */
    public void plotResults(XYSeries series) {
        double slope = estimateSlope(series);
        System.out.println("Estimated slope= " + slope);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                "",          // chart title
                "",               // domain axis label
                "",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL,
                false,                     // include legend
                true,
                false
        );

        final XYPlot plot = chart.getXYPlot();
        final NumberAxis domainAxis = new LogarithmicAxis("Problem size (n)");
        final NumberAxis rangeAxis = new LogarithmicAxis("Runtime (ms)");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(rangeAxis);
        plot.setOutlinePaint(Color.black);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        setContentPane(chartPanel);
        pack();
        RefineryUtilities.centerFrameOnScreen(this);

        // customize the appearance of the graph
        plot.setBackgroundPaint(Color.white);
        int seriesCount = plot.getSeriesCount();

        for (int i = 0; i < seriesCount; i++) {
            plot.getRenderer().setSeriesStroke(i, new BasicStroke(3));
        }
        Font font24 = new Font("Dialog", Font.PLAIN, 24);
        plot.getDomainAxis().setLabelFont(font24);
        plot.getRangeAxis().setLabelFont(font24);
        Font font20 = new Font("Dialog", Font.PLAIN, 20);
        plot.getDomainAxis().setTickLabelFont(font20);
        plot.getRangeAxis().setTickLabelFont(font20);

        setVisible(true);
    }

    /**
     * Uses simple regression to estimate the slope of the series.(使用简单回归来预估序列的斜率)
     * @return
     */
    public double estimateSlope(XYSeries series) {
        SimpleRegression regression = new SimpleRegression();

        for (Object item: series.getItems()) {
            XYDataItem xy = (XYDataItem) item;
            regression.addData(Math.log(xy.getXValue()), Math.log(xy.getYValue()));
        }
        return regression.getSlope();
    }


}