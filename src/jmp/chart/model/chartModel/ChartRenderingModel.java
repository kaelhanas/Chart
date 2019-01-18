package jmp.chart.model.chartModel;

import java.awt.BasicStroke;
import java.awt.Color;

import jmp.chart.Default;
import jmp.ui.mvc.DefaultModel;

public class ChartRenderingModel extends DefaultModel
{	
	private Color horiziontalGridLineColor;
	private Color verticalGridLineColor;
	private BasicStroke horiziontalGridLineThickness;
	private BasicStroke verticalGridLineThickness;
	private Color chartBackGroundColor;
	private Color backGroundColor;

	private double margins[];;
	
	public ChartRenderingModel()
	{
		this.backGroundColor = Default.CHART_BACKGROUND_COLOR;
		this.chartBackGroundColor = Default.CHART_CHARTBACKGROUND_COLOR;
		this.horiziontalGridLineColor = Default.H_GRIDLINE_COLOR;
		this.verticalGridLineColor = Default.V_GRIDLINE_COLOR;
		this.horiziontalGridLineThickness = new BasicStroke(Default.H_GRIDLINE_THICKNESS);
		this.verticalGridLineThickness = new BasicStroke(Default.V_GRIDLINE_THICKNESS);
		this.margins = new double[4];
		System.arraycopy(Default.MARGINS, 0, this.margins,0, this.margins.length);
	}

	public Color getHoriziontalGridLineColor()
	{
		return this.horiziontalGridLineColor;
	}

	public void setHoriziontalGridLineColor(Color horiziontalGridLineColor)
	{
		if (this.horiziontalGridLineColor.equals(horiziontalGridLineColor)) return;
		this.horiziontalGridLineColor = horiziontalGridLineColor;
		this.modelChange();
	}

	public Color getVerticalGridLineColor()
	{
		return this.verticalGridLineColor;
	}

	public void setVerticalGridLineColor(Color verticalGridLineColor)
	{
		if (this.verticalGridLineColor.equals(verticalGridLineColor)) return;
		this.verticalGridLineColor = verticalGridLineColor;
		this.modelChange();
	}

	public BasicStroke getHoriziontalGridLineThickness()
	{
		return this.horiziontalGridLineThickness;
	}

	public void setHoriziontalGridLineThickness(float horiziontalGridLineThickness)
	{
		if (this.horiziontalGridLineThickness.getLineWidth() == horiziontalGridLineThickness) return;
		this.horiziontalGridLineThickness = new BasicStroke(horiziontalGridLineThickness);
		this.modelChange();
	}

	public BasicStroke getVerticalGridLineThickness()
	{
		return this.verticalGridLineThickness;
	}

	public void setVerticalGridLineThickness(float verticalGridLineThickness)
	{
		if (this.verticalGridLineThickness.getLineWidth() == verticalGridLineThickness) return;
		this.verticalGridLineThickness = new BasicStroke(verticalGridLineThickness);
		this.modelChange();
	}

	public void setChartBackGroundColor(Color backGroundColor)
	{
		if (this.chartBackGroundColor.equals(backGroundColor)) return;
		this.chartBackGroundColor = backGroundColor;
		this.modelChange();
	}

	public Color getChartBackGroundColor()
	{
		return this.chartBackGroundColor;
	}

	public void setBackGroundColor(Color backGroundColor)
	{
		if (this.backGroundColor.equals(backGroundColor)) return;
		this.backGroundColor = backGroundColor;
		this.modelChange();
	}

	public Color getBackGroundColor()
	{
		return this.backGroundColor;
	}


	public void setMargin(Side side, double value)
	{
		if (this.margins[side.ordinal()] == value) return;
		this.margins[side.ordinal()] = value;
		this.modelChange();
	}

	public double getMargin(Side side)
	{
		return this.margins[side.ordinal()];
	}
}
