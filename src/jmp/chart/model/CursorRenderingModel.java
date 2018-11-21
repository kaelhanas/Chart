package jmp.chart.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import jmp.chart.Default;

public class CursorRenderingModel
{
	private Color labelColor;
	private Font labelFont;
	private Color markColor;
	private BasicStroke lineStroke;
	private Color lineColor;
	
	public CursorRenderingModel()
	{
		this.labelColor = Default.CURSOR_LABELCOLOR;
		this.labelFont = Default.CURSOR_LABELFONT;
		this.markColor = Default.CURSOR_MARK_COLOR;
		this.lineStroke = Default.CURSOR_LINE_STROKE;
		this.lineColor = Default.CURSOR_LINE_COLOR;
	}

	public Color getLabelColor()
	{
		return this.labelColor;
	}

	public void setLabelColor(Color labelColor)
	{
		this.labelColor = labelColor;
	}

	public Font getLabelFont()
	{
		return this.labelFont;
	}

	public void setLabelFont(Font labelFont)
	{
		this.labelFont = labelFont;
	}

	public Color getMarkColor()
	{
		return this.markColor;
	}

	public void setMarkColor(Color markColor)
	{
		this.markColor = markColor;
	}

	public BasicStroke getLineStroke()
	{
		return this.lineStroke;
	}

	public void setLineStroke(BasicStroke lineStroke)
	{
		this.lineStroke = lineStroke;
	}

	public Color getLineColor()
	{
		return this.lineColor;
	}

	public void setLineColor(Color lineColor)
	{
		this.lineColor = lineColor;
	}
	
	
}
