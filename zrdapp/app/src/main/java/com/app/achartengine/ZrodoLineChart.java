package com.app.achartengine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by grandry.xu on 15-11-25.
 */
public class ZrodoLineChart extends BaseChartGenerate {
    private Context mContext;

    public ZrodoLineChart(Context context){
        this.mContext=context;
    }
    @Override
    protected XYMultipleSeriesDataset getSeriesDateset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        String[] titles = new String[] { "Crete", "Corfu", "Thassos", "Skiathos" };
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        }
        List<double[]> values = new ArrayList<double[]>();
        values.add(new double[] { 12.3, 12.5, 13.8, 16.8, 20.4, 24.4, 26.4, 26.1, 23.6, 20.3, 17.2,
                13.9 });
        values.add(new double[] { 10, 10, 12, 15, 20, 24, 26, 26, 23, 18, 14, 11 });
        values.add(new double[] { 5, 5.3, 8, 12, 17, 22, 24.2, 24, 19, 15, 9, 6 });
        values.add(new double[] { 9, 10, 11, 15, 19, 23, 26, 25, 22, 18, 13, 10 });
        addXYSeries(dataset, titles, x, values, 0);
        return dataset;
    }

    /**
     * demo
     * @return
     */

    @Override
    public View getView() {
        XYMultipleSeriesRenderer renderer=getSeriesRender();
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "Average temperature", "Month", "Temperature", 0.5, 12.5, -10, 40,
                Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
        renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });

        XYMultipleSeriesDataset dataset =getSeriesDateset();
        XYSeries series = dataset.getSeriesAt(0);
        series.addAnnotation("Vacation", 6, 30);
        return ChartFactory.getLineChartView(this.mContext,dataset,renderer);
    }

    public View getView(String[] items,List<double[]> xValues,List<double[]> yValues){
        XYMultipleSeriesRenderer renderer=createSeriesRender();
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, "检测项目年度对比", "", "", 0.5, 12.5, -10, 8000,
                Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
        renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset,items,xValues,yValues,0);
        XYSeries series = dataset.getSeriesAt(0);
        series.addAnnotation("zrodo", 6, 30);
        return ChartFactory.getLineChartView(this.mContext,dataset,renderer);


    }

    @Override
    protected XYMultipleSeriesRenderer getSeriesRender() {
        int[] colors=new int[]{ Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW};
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE, PointStyle.SQUARE };
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
      //  renderer.setMargins(new int[] { 20, 30, 15, 20 });
        renderer.setMargins(new int[] { 0, 0, 0, 0 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected XYMultipleSeriesRenderer createSeriesRender(){
        int[] colors=new int[]{ Color.BLUE, Color.GREEN, Color.CYAN};
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE };
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        //  renderer.setMargins(new int[] { 20, 30, 15, 20 });
        renderer.setMargins(new int[] { 0, 0, 0, 0 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }


    /**
     *
     * @param dataset
     * @param titles
     * @param xValues
     * @param yValues
     * @param scale
     */
    public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues,
                            List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }
}
