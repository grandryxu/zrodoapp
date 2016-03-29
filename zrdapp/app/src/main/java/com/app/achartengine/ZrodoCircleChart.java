package com.app.achartengine;

import android.content.Context;
import android.graphics.Color;
import android.view.View;


import com.zrodo.zrdapp.R;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

/**
 * Created by grandry.xu on 15-11-25.
 */
public class ZrodoCircleChart extends BaseChartGenerate {
    private Context mContext;

    public ZrodoCircleChart(Context context){
        this.mContext=context;
    }
    @Override
    protected XYMultipleSeriesDataset getSeriesDateset() {

        return null;
    }

    @Override
    public View getView() {
        View v= ChartFactory.getPieChartView(this.mContext,buildCategoryDataset(),buildCategoryRenderer());
        return v;
    }

    public View getView(double[] val,String[] detecItems){
        View v= ChartFactory.getPieChartView(this.mContext,buildCategoryDataset(val,detecItems),buildCategoryRenderer());
        return v;
    }

    @Override
    protected XYMultipleSeriesRenderer getSeriesRender() {

        return null;
    }

    private CategorySeries buildCategoryDataset() {
        double[] values=new double[]{ 12, 14, 11};
        CategorySeries series = new CategorySeries("zrodo");
        int k=0;
        for(double value:values){
            series.add("Project"+ ++k,value);
        }
        return series;

    }

    private CategorySeries buildCategoryDataset(double[] values,String[] valNames){
        CategorySeries series = new CategorySeries("zrodo");
        int len=values.length;
        for(int i=0;i<len;i++){
            series.add(valNames[i],values[i]);
        }
        return series;

    }

    private DefaultRenderer buildCategoryRenderer(){
        int[] colors=new int[]{Color.BLUE,Color.CYAN,Color.GREEN};
        DefaultRenderer renderer=new DefaultRenderer();
        renderer.setLabelsTextSize(30);
        renderer.setLabelsColor(R.color.dark);
        renderer.setLegendTextSize(30);
       // renderer.setMargins(new int[] { 20, 30, 15, 0 });
        renderer.setMargins(new int[] { 0, 0, 0, 0 });
       // renderer.setFitLegend(true);
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(false);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        renderer.setPanEnabled(false);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(false);
/*        r.setGradientStart(0, Color.BLUE);
        r.setGradientStop(0, Color.GREEN);*/
        r.setHighlighted(true);
        return renderer;
    }
}
