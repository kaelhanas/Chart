package jmp.chart.view;

import jmp.chart.model.chartModel.AutoScaleStrategy;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.AxisRenderingModel;
import jmp.chart.model.chartModel.ChartModel;
import jmp.chart.model.chartModel.ChartRenderingModel;
import jmp.chart.model.chartModel.CursorModel;
import jmp.chart.model.chartModel.CursorRenderingModel;
import jmp.chart.model.chartModel.CursorsModel;
import jmp.chart.model.chartModel.CursorsRenderingModel;
import jmp.chart.model.chartModel.Side;
import jmp.chart.renderer.ChartRenderer;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public abstract class ChartView extends View
{
	public final static String X_AXIS_MODEL = "xam";
	public final static String X_AXIS_RENDERING_MODEL = "xarm";
	public final static String Y_AXIS_MODEL = "yam";
	public final static String Y_AXIS_RENDERING_MODEL = "yarm";
	public final static String CHART_MODEL = "cm";
	public final static String CHART_RENDERING_MODEL = "crm";
	private static final String CURSORS_RENDERING_MODEL = "cursorsm";
	private static final String CURSORS_MODEL = "cursorsrm";

	public ChartView(AxisModel xAxis, AxisModel yAxis)
	{
		super();
	
		xAxis.setSide(Side.Bottom);
		yAxis.setSide(Side.Left);

		ModelComposit m = new DefaultModelComposit();
		m.addModel(X_AXIS_MODEL, xAxis);
		m.addModel(Y_AXIS_MODEL, yAxis);
		m.addModel(X_AXIS_RENDERING_MODEL, new AxisRenderingModel());
		m.addModel(Y_AXIS_RENDERING_MODEL, new AxisRenderingModel());
		m.addModel(CURSORS_MODEL, new CursorsModel());
		m.addModel(CURSORS_RENDERING_MODEL, new CursorsRenderingModel());
		this.setModel(m);
		//in order to update display rectangle
		this.addComponentListener((ChartRenderer)this.renderer());
	}
	
	public AxisModel xAxisModel()
	{
		return (AxisModel) ((ModelComposit)this.getModel()).getModel(X_AXIS_MODEL);
	}
	
	public AxisModel yAxisModel()
	{
		return (AxisModel) ((ModelComposit)this.getModel()).getModel(Y_AXIS_MODEL);
	}
	
	public AxisRenderingModel xAxisRenderingModel()
	{
		return (AxisRenderingModel) ((ModelComposit)this.getModel()).getModel(X_AXIS_RENDERING_MODEL);
	}
	
	public AxisRenderingModel yAxisRenderingModel()
	{
		return (AxisRenderingModel) ((ModelComposit)this.getModel()).getModel(Y_AXIS_RENDERING_MODEL);
	}
	
	public ChartRenderingModel chartRenderingModel()
	{
		return (ChartRenderingModel) ((ModelComposit)this.getModel()).getModel(CHART_RENDERING_MODEL);
	}
	
	public ChartModel chartModel()
	{
		return (ChartModel) ((ModelComposit)this.getModel()).getModel(CHART_MODEL);
	}

	public CursorsRenderingModel cursorsRenderingModel()
	{
		return (CursorsRenderingModel) ((ModelComposit)this.getModel()).getModel(CURSORS_RENDERING_MODEL);
	}
	
	public CursorsModel cursorsModel()
	{
		return (CursorsModel) ((ModelComposit)this.getModel()).getModel(CURSORS_MODEL);
	}

	public void autoScale(AutoScaleStrategy asx, AutoScaleStrategy asy)
	{
		this.xAxisModel().autoScale(this.chartModel().getData().xMin(), this.chartModel().getData().xMax(), asx);
		this.yAxisModel().autoScale(this.chartModel().getData().yMin(), this.chartModel().getData().yMax(), asy);
	}
	
	public void autoScaleX(AutoScaleStrategy as)
	{
		this.xAxisModel().autoScale(this.chartModel().getData().xMin(), this.chartModel().getData().xMax(), as);
	}
	
	public void autoScaleY(AutoScaleStrategy as)
	{
		this.yAxisModel().autoScale(this.chartModel().getData().yMin(), this.chartModel().getData().yMax(), as);
	}
	
	public void addCursor(CursorModel cm, CursorRenderingModel crm)
	{
		this.cursorsModel().addCursor(cm);
		this.cursorsRenderingModel().addCursor(cm, crm);
	}

	public void removeCursor(CursorModel cm)
	{
		this.cursorsModel().removeCursor(cm);
		this.cursorsRenderingModel().removeCursor(cm);
	}
	
}
