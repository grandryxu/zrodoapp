package com.app.achartengine;

import android.view.View;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

/**
 * Created by grandry.xu on 15-11-12.
 */
public abstract class BaseChartGenerate {

    protected abstract XYMultipleSeriesDataset getSeriesDateset();

    protected abstract View getView();

    protected abstract XYMultipleSeriesRenderer getSeriesRender();

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                    String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
                                    int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }
}
