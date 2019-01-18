package jmp.chart.view;

import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.chartModel.MapChartRenderingModel;
import jmp.chart.renderer.MapChartRenderer;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class MapChartView extends ChartView
{

	public MapChartView(AxisModel xAxis, AxisModel yAxis)
	{
		super(xAxis, yAxis);

		ModelComposit m = (ModelComposit) this.getModel();
		m.addModel(CHART_MODEL, new MapChartModel());
		m.addModel(CHART_RENDERING_MODEL, new MapChartRenderingModel());
	}

	public Renderer defaultRenderer(View view)
	{
		return new MapChartRenderer(view);
	}
	
}
