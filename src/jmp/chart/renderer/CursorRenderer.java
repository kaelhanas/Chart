package jmp.chart.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import jmp.chart.Default;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.CursorModel;
import jmp.chart.model.chartModel.CursorRenderingModel;
import jmp.ui.utilities.JMSwingUtilities;

public class CursorRenderer
{
	private ChartRenderer chartRenderer;
	private NumberFormat Labelformatter;

	public CursorRenderer(ChartRenderer chartRenderer)
	{
		this.chartRenderer = chartRenderer;
		this.Labelformatter = new DecimalFormat("##.##");

	}


	public void renderCursor(Graphics2D g, CursorModel cm, CursorRenderingModel crm, AxisModel xam, AxisModel yam, Rectangle2D.Double displayRect)
	{
		final double x = xam.getDisplayPosition(cm.getX());
		final double y = yam.getDisplayPosition(cm.getY());
		
		this.renderMak(g, cm, crm, x, y);
		this.renderLines(g, cm, crm, xam, yam, displayRect, x, y);
		this.renderLabel(g, cm, crm, displayRect, x, y);
	}


	private void renderLabel(Graphics2D g, CursorModel cm, CursorRenderingModel crm, Rectangle2D.Double displayRect, double x, double y)
	{
		String label = this.Labelformatter.format(cm.getX()) + ", " + this.Labelformatter.format(cm.getY());
		if (!cm.getLabel().trim().equals("")) label = cm.getLabel() + " : " +label;	
		
		final Dimension labelDim = JMSwingUtilities.getStringDimension(label, crm.getLabelFont());

		int xs = 0;
		double labelSize =  Default.CURSOR_SIZE + labelDim.getWidth();
		if (x + labelSize > displayRect.width) 
			xs = this.chartRenderer.x(x-labelSize);
		else
			xs = this.chartRenderer.x(x+Default.CURSOR_SIZE);
		
		int ys = this.chartRenderer.y(y);

		g.setColor(crm.getLabelColor());
		g.setFont(crm.getLabelFont());
		g.drawString(label, xs, ys);
		
	}


	private void renderLines(Graphics2D g, CursorModel cm, CursorRenderingModel crm, AxisModel xam, AxisModel yam, Rectangle2D.Double displayRect, double x, double y)
	{
		g.setColor(crm.getLineColor());
		g.setStroke(crm.getLineStroke());			
		
		int x0=0, y0=0, x1=0, y1=0;	
		if (cm.isxLineVisible())
		{
			y1 = y0 = this.chartRenderer.y(y);

			switch(yam.getSide())
			{
				case Left:
					x0 = this.chartRenderer.x(x-Default.CURSOR_SIZE);
					x1 = this.chartRenderer.x(0);
					break;
				case Right:
					x0 = this.chartRenderer.x(x+Default.CURSOR_SIZE);
					x1 = this.chartRenderer.x(displayRect.width);
					break;
			}
			g.drawLine(x0, y0, x1, y1);
		}
		
		if (cm.isyLineVisible())
		{
			x1 = x0 = this.chartRenderer.x(x);
			switch(xam.getSide())
			{
				case Bottom:
					y0 = this.chartRenderer.y(y + Default.CURSOR_SIZE);
					y1 = this.chartRenderer.y(0);
					break;
				case Top:
					y0 = this.chartRenderer.y(y - Default.CURSOR_SIZE);
					y1 = this.chartRenderer.y(displayRect.height);
					break;
			}
			g.drawLine(x0, y0, x1, y1);	
		}	
	}


	private void renderMak(Graphics2D g, CursorModel cm, CursorRenderingModel crm, double x, double y)
	{
		if (!cm.isMarkVisible()) return;
		
		g.setColor(crm.getMarkColor());
		g.setStroke(new BasicStroke(crm.getLineStroke().getLineWidth()));
		
		int x0 = this.chartRenderer.x(x-Default.CURSOR_SIZE);
		int y0 = this.chartRenderer.y(y);
		int x1 = this.chartRenderer.x(x+Default.CURSOR_SIZE);
		int y1 = y0;
		g.drawLine(x0, y0, x1, y1);
		
		x0 = this.chartRenderer.x(x);
		y0 = this.chartRenderer.y(y-Default.CURSOR_SIZE);
		x1 = x0;
		y1 = this.chartRenderer.y(y+Default.CURSOR_SIZE);
		g.drawLine(x0, y0, x1, y1);
	}
}
