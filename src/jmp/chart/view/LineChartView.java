package jmp.chart.view;

import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.LineChartRenderingModel;
import jmp.chart.renderer.LineChartRenderer;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class LineChartView extends ChartView
{

	public LineChartView(AxisModel xAxis, AxisModel yAxis)
	{
		super(xAxis, yAxis);
	
		ModelComposit m = (ModelComposit) this.getModel();
		m.addModel(CHART_MODEL, new LineChartModel());
		m.addModel(CHART_RENDERING_MODEL, new LineChartRenderingModel());
		
	}

	public Renderer defaultRenderer(View view)
	{
		return new LineChartRenderer(view);
	}
	
}
