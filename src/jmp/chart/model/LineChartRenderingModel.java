package jmp.chart.model;

import java.awt.BasicStroke;
import java.awt.Color;

import jmp.chart.Default;

public class LineChartRenderingModel extends ChartRenderingModel
{
	private Color lineColor;
	private BasicStroke lineStroke;
	
	public LineChartRenderingModel()
	{
		this.lineColor = Default.CHARTLINE_COLOR;
		this.lineStroke = Default.CHARTLINE_STROKE;
	}
	
	public Color getLineColor()
	{
		return this.lineColor;
	}
	
	public void setLineColor(Color lineColor)
	{
		if (this.lineColor.equals(lineColor)) return;
		this.lineColor = lineColor;
		this.modelChange();
	}
	
	public BasicStroke getLineStroke()
	{
		return this.lineStroke;
	}
	
	public void setLineStroke(BasicStroke lineStroke)
	{
		if (this.lineStroke.equals(lineStroke)) return;
		this.lineStroke = lineStroke;
		this.modelChange();

	}
	
	
}
