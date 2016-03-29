package com.app.achartengine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;


import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.Random;

/**
 * Created by grandry.xu on 15-11-12.
 */
public class ZrodoColumnChart extends BaseChartGenerate {
    private Context mContext;

    public ZrodoColumnChart(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public XYMultipleSeriesDataset getSeriesDateset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 10;
        Random r = new Random();
        for (int i = 0; i < 2; i++) {
            CategorySeries series = new CategorySeries("Demo series " + (i + 1));
            for (int k = 0; k < nr; k++) {
                series.add(100 + r.nextInt() % 100);
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    @Override
    public View getView() {
        XYMultipleSeriesRenderer renderer = getSeriesRender();
        setChartSettings(renderer);
        View view= ChartFactory.getBarChartView(mContext,getSeriesDateset(),renderer, BarChart.Type.DEFAULT);
        return view;
    }

    public Intent getIntent(){
        XYMultipleSeriesRenderer renderer = getSeriesRender();
        setChartSettings(renderer);
        return ChartFactory.getBarChartIntent(mContext,getSeriesDateset(),renderer, BarChart.Type.DEFAULT);
    }

    @Override
    public XYMultipleSeriesRenderer getSeriesRender() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setExternalZoomEnabled(false);
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);
        renderer.setInScroll(true);
       // renderer.setMargins(new int[]{20, 30, 15, 0});
        renderer.setMargins(new int[]{0, 3, 0, 0});
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.GREEN);
        renderer.addSeriesRenderer(r);
        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setChartTitle("Chart demo");
        renderer.setXTitle("x values");
        renderer.setYTitle("y values");
        renderer.setXAxisMin(0);
        renderer.setXAxisMax(12);
        renderer.setXLabels(12);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(210);
    }
}
