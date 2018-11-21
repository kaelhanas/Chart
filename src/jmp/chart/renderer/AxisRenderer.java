package jmp.chart.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import jmp.chart.Default;
import jmp.chart.model.AxisModel;
import jmp.chart.model.AxisRenderingModel;
import jmp.ui.utilities.JMSwingUtilities;

public class AxisRenderer
{
	private ChartRenderer chartRenderer;
	private NumberFormat tickLabelformatter;
	private List<Integer> ticksPositions;
	
	public AxisRenderer(ChartRenderer chartRenderer, String axisModel, String axisRenderingModel)
	{
		this.chartRenderer = chartRenderer;
		this.tickLabelformatter = new DecimalFormat("##.##");
		this.ticksPositions = new ArrayList<Integer>();
	}

	public void renderView(Graphics2D g, AxisModel am, AxisRenderingModel arm, Rectangle2D.Double displayRect)
	{
		this.renderAxis(g, am, arm, displayRect);
		int maxWidthLabel = this.renderTicks(g, am, arm, displayRect);
		if (am.isMinorTickVisible()) this.renderMinorTicks(g, am, arm, displayRect);
		this.renderLabel(g, am, arm, maxWidthLabel, displayRect);
	}

	public void renderAxis(Graphics2D g, AxisModel am, AxisRenderingModel arm, Rectangle2D.Double displayRect)
	{
		int x0 = 0, y0 = 0, x1 = 0, y1 = 0;
		switch (am.getSide())
		{
			case Bottom :
				x0 = this.chartRenderer.x(0);
				y0 = y1 = this.chartRenderer.y(0);
				x1 = this.chartRenderer.x(displayRect.width);
				break;
			case Top :
				x0 = this.chartRenderer.x(0);
				y0 = y1 = this.chartRenderer.y(displayRect.height);
				x1 = this.chartRenderer.x(displayRect.width);
				break;
			case Left :
				x0 = x1 = this.chartRenderer.x(0);
				y0 = this.chartRenderer.y(0);
				y1 = this.chartRenderer.y(displayRect.height);
				break;
			case Right :
				x0 = x1 = this.chartRenderer.x(displayRect.width);
				y0 = this.chartRenderer.y(0);
				y1 = this.chartRenderer.y(displayRect.height);
				break;
		}
		g.setColor(arm.getAxisColor());
		g.setStroke(arm.getAxisThickness());
		g.drawLine(x0, y0, x1, y1);
	}

	public int renderTicks(Graphics2D g, AxisModel am, AxisRenderingModel arm, Rectangle2D.Double displayRect)
	{
		int maxWidthLabel=0;
		List<Double> ticks = am.getTickValues();
		
		this.ticksPositions.clear();
		g.setColor(arm.getTickColor());
		g.setStroke(arm.getTickThickness());

		int x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		for (Double tick : ticks)
		{
			switch (am.getSide())
			{
				case Bottom :
					x0 = x1 = this.chartRenderer.x(am.getDisplayPosition(tick));
					y0 = this.chartRenderer.y(0);
					y1 = this.chartRenderer.y(-arm.getTickLength());
					this.ticksPositions.add(x0);
					break;
				case Top :
					x0 = x1 = this.chartRenderer.x(am.getDisplayPosition(tick));
					y0 = this.chartRenderer.y(displayRect.height);
					y1 = this.chartRenderer.y(displayRect.height + arm.getTickLength());
					this.ticksPositions.add(x0);
					break;
				case Left :
					x0 = this.chartRenderer.x(0);
					x1 = this.chartRenderer.x(-arm.getTickLength());
					y0 = y1 = this.chartRenderer.y(am.getDisplayPosition(tick));
					this.ticksPositions.add(y0);
					break;
				case Right :
					x0 = this.chartRenderer.x(displayRect.width);
					x1 = this.chartRenderer.x(displayRect.width + arm.getTickLength());
					y0 = y1 = this.chartRenderer.y(am.getDisplayPosition(tick));
					this.ticksPositions.add(y0);
					break;
			}
			g.drawLine(x0, y0, x1, y1);
			
			if (am.isTickVisible()) 
			{
				maxWidthLabel = Math.max(maxWidthLabel,this.renderTickLabel(g, am, arm, tick, x1, y1));
			}
		}
		return maxWidthLabel;
	}

	
	public void renderMinorTicks(Graphics2D g, AxisModel am, AxisRenderingModel arm, Rectangle2D.Double displayRect)
	{
		List<Double> ticks = am.getMinorTickValues();

		g.setColor(arm.getMinorTickColor());
		g.setStroke(arm.getMinorTickThickness());

		int x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		for (Double tick : ticks)
		{
			switch (am.getSide())
			{
				case Bottom :
					x0 = x1 = this.chartRenderer.x(am.getDisplayPosition(tick));
					y0 = this.chartRenderer.y(0);
					y1 = this.chartRenderer.y(-arm.getMinorTickLength());
					break;
				case Top :
					x0 = x1 = this.chartRenderer.x(am.getDisplayPosition(tick));
					y0 = this.chartRenderer.y(displayRect.height);
					y1 = this.chartRenderer.y(displayRect.height + arm.getMinorTickLength());
					break;
				case Left :
					x0 = this.chartRenderer.x(0);
					x1 = this.chartRenderer.x(-arm.getMinorTickLength());
					y0 = y1 = this.chartRenderer.y(am.getDisplayPosition(tick));
					break;
				case Right :
					x0 = this.chartRenderer.x(displayRect.width);
					x1 = this.chartRenderer.x(displayRect.width + arm.getMinorTickLength());
					y0 = y1 = this.chartRenderer.y(am.getDisplayPosition(tick));
					break;
			}
			g.drawLine(x0, y0, x1, y1);
		}
	}

	public int renderTickLabel(Graphics2D g, AxisModel am, AxisRenderingModel arm, double tickValue, int xTick, int yTick)
	{
		final String label = this.tickLabelformatter.format(tickValue);
		final Dimension labelDim = JMSwingUtilities.getStringDimension(label, arm.getTickLabelFont());

		g.setColor(arm.getTickLabelColor());
		g.setFont(arm.getTickLabelFont());

		int x = 0, y = 0;
		switch (am.getSide())
		{
			case Bottom :
				x = xTick - labelDim.width / 2;
				y = yTick + labelDim.height + Default.SEPARATOR_SIZE;
				break;
			case Top :
				x = xTick - labelDim.width / 2;
				y = yTick - Default.SEPARATOR_SIZE;
				break;
			case Left :
				x = xTick - labelDim.width - Default.SEPARATOR_SIZE;
				y = yTick + labelDim.height / 2;
				break;
			case Right :
				x = xTick + Default.SEPARATOR_SIZE;
				y = yTick + labelDim.height / 2;
				break;
		}
		g.drawString(label, x, y);
		return labelDim.width;
	}

	public void renderLabel(Graphics2D g, AxisModel am, AxisRenderingModel arm, int maxWidthLabel,Rectangle2D.Double displayRect)
	{
		AffineTransform tmp = g.getTransform();
		final String label = am.getLabel();
		final Dimension labelDim = JMSwingUtilities.getStringDimension(label, arm.getTickLabelFont());
		
		g.setColor(arm.getTickLabelColor());
		g.setFont(arm.getTickLabelFont());
		int x = 0, y = 0;
		switch (am.getSide())
		{
			case Bottom :
				x = this.chartRenderer.x((displayRect.width/2)-(labelDim.width / 2));
				y = this.chartRenderer.y(-(arm.getTickLength()+JMSwingUtilities.getAscent(arm.getTickLabelFont())+labelDim.height + (2*Default.SEPARATOR_SIZE)));
				break;
			case Top :
				x = this.chartRenderer.x((displayRect.width/2)-(labelDim.width / 2));
				y = this.chartRenderer.y(displayRect.height+(arm.getTickLength()+JMSwingUtilities.getAscent(arm.getTickLabelFont()) + (2*Default.SEPARATOR_SIZE)));
			break;
			case Left :
				x = this.chartRenderer.x(-arm.getTickLength()-(2*Default.SEPARATOR_SIZE)-maxWidthLabel-(labelDim.height/2));
				y = this.chartRenderer.y((displayRect.height/2)-(labelDim.width / 2));
				AffineTransform atLeft = new AffineTransform();
			    atLeft.setToRotation(-Math.PI/2.0,x,y);
			    g.setTransform(atLeft);
				break;
			case Right :
				x = this.chartRenderer.x(displayRect.width+arm.getTickLength()+(2*Default.SEPARATOR_SIZE)+maxWidthLabel+(labelDim.height/2));
				y = this.chartRenderer.y((displayRect.height/2)-(labelDim.width / 2));
				AffineTransform atRight = new AffineTransform();
			    atRight.setToRotation(-Math.PI/2.0,x,y);
			    g.setTransform(atRight);
				break;
		}
		g.drawString(label, x, y);
		g.setTransform(tmp);
	}

	//updated after axis ticks rendering
	public List<Integer> getTicksPositions()
	{
		return this.ticksPositions;
	}

}
