package jmp.chart.view;

import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.BarChartModel;
import jmp.chart.model.chartModel.BarChartRenderingModel;
import jmp.chart.renderer.BarChartRenderer;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class BarChartView extends ChartView{

	public BarChartView(AxisModel xAxis, AxisModel yAxis) {
		super(xAxis, yAxis);
		ModelComposit m = (ModelComposit) this.getModel();
		m.addModel(CHART_MODEL, new BarChartModel());
		m.addModel(CHART_RENDERING_MODEL, new BarChartRenderingModel());
	}
	
	public Renderer defaultRenderer(View view) {
		return new BarChartRenderer(view);
	}

}
