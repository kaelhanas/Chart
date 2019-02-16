package jmp.chart.renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D.Double;

import jmp.chart.model.chartModel.ChartModel;
import jmp.chart.model.chartModel.ChartRenderingModel;
import jmp.ui.mvc.View;

public class BarChartRenderer extends ChartRenderer {

	public BarChartRenderer(View view) {
		super(view);
	}

	@Override
	protected void renderChart(Image image, Graphics2D g, ChartModel cm, ChartRenderingModel crm, Double displayRect) {
		
		

	}

}
