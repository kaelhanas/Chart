package jmp.chart.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import jmp.chart.Default;
import jmp.ui.mvc.DefaultModel;

public class AxisRenderingModel extends DefaultModel
{
	private Color axisColor;
	private Color tickColor;
	private Color minorTickColor;
	private BasicStroke axisThickness;
	private BasicStroke tickThickness;
	private BasicStroke minorTickThickness;
	private double tickLength;
	private double minorTickLength;
	private Font labelFont;
	private Font tickLabelFont;
	private Color labelColor;
	private Color tickLabelColor;
	private double separatorSize;
	
	public AxisRenderingModel()
	{
		this.axisColor = Default.AXIS_COLOR;
		this.tickColor = Default.TICK_COLOR;
		this.minorTickColor = Default.MINOR_TICK_COLOR;
		this.axisThickness = new BasicStroke(Default.AXIS_THIKNESS);
		this.tickThickness = new BasicStroke(Default.TICK_THIKNESS);
		this.minorTickThickness = new BasicStroke(Default.MINOR_TICK_THIKNESS);
		this.labelFont = Default.LABEL_FONT;
		this.tickLabelFont = Default.TICK_LABEL_FONT;
		this.labelColor = Default.LABEL_COLOR;
		this.tickLabelColor = Default.TICK_LABEL_COLOR;
		this.tickLength = Default.TICK_LENGTH;
		this.minorTickLength =Default.MINOR_TICK_LENGTH;
		this.separatorSize =Default.SEPARATOR_SIZE;
	}

	public Color getAxisColor()
	{
		return this.axisColor;
	}

	public void setAxisColor(Color axisColor)
	{
		if (this.axisColor.equals(axisColor)) return;
		this.axisColor = axisColor;
		this.modelChange();
	}

	public Color getTickColor()
	{
		return this.tickColor;
	}

	public void setTickColor(Color tickColor)
	{
		if (this.tickColor.equals(tickColor)) return;
		this.tickColor = tickColor;
		this.modelChange();
	}

	public Color getMinorTickColor()
	{
		return this.minorTickColor;
	}

	public void setMinortickColor(Color minortickColor)
	{
		if (this.minorTickColor.equals(minortickColor)) return;
		this.minorTickColor = minortickColor;
		this.modelChange();
	}

	public Font getLabelFont()
	{
		return this.labelFont;
	}

	public void setLabelFont(Font labelFont)
	{
		if (this.labelFont.equals(labelFont)) return;
		this.labelFont = labelFont;
		this.modelChange();
	}

	public Font getTickLabelFont()
	{
		return this.tickLabelFont;
	}

	public void setTickLabelFont(Font tickLabelFont)
	{
		if (this.tickLabelFont.equals(tickLabelFont)) return;
		this.tickLabelFont = tickLabelFont;
		this.modelChange();
	}

	public Color getLabelColor()
	{
		return this.labelColor;
	}

	public void setLabelColor(Color labelColor)
	{
		if (this.labelColor.equals(labelColor)) return;
		this.labelColor = labelColor;
		this.modelChange();
	}

	public Color getTickLabelColor()
	{
		return this.tickLabelColor;
	}

	public void setTickLabelColor(Color tickLabelColor)
	{
		if (this.tickLabelColor.equals(tickLabelColor)) return;
		this.tickLabelColor = tickLabelColor;
		this.modelChange();
	}

	public double getTickLength()
	{
		return this.tickLength;
	}

	public void setTickLength(double tickLength)
	{
		if (this.tickLength == tickLength) return;
		this.tickLength = tickLength;
		this.modelChange();
	}

	public double getMinorTickLength()
	{
		return this.minorTickLength;
	}

	public void setMinorTickLength(double minorTickLength)
	{
		if (this.minorTickLength == minorTickLength) return;
		this.minorTickLength = minorTickLength;
		this.modelChange();
	}

	public void setAxisThickness(float axisThickness)
	{
		if (this.axisThickness.getLineWidth() == axisThickness) return;
		this.axisThickness = new BasicStroke(axisThickness);
		this.modelChange();
	}

	public void setTickThickness(float tickThickness)
	{
		if (this.tickThickness.getLineWidth() == tickThickness) return;
		this.tickThickness = new BasicStroke(tickThickness);
		this.modelChange();
	}

	public void setMinorTickThickness(float minorTickThickness)
	{
		if (this.minorTickThickness.getLineWidth() == minorTickThickness) return;
		this.minorTickThickness = new BasicStroke(minorTickThickness);
		this.modelChange();
	}
	
	public void setSeparatorSize(double separatorSize)
	{
		if (this.separatorSize == separatorSize) return;
		this.separatorSize = separatorSize;
		this.modelChange();
	}
	
	public BasicStroke getAxisThickness()
	{
		return this.axisThickness;
	}

	public BasicStroke getTickThickness()
	{
		return this.tickThickness;
	}

	public BasicStroke getMinorTickThickness()
	{
		return this.minorTickThickness;
	}

	public double getSeparatorSize()
	{
		return this.separatorSize;
	}
}
