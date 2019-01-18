package jmp.chart.renderer;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.CursorModel;
import jmp.chart.model.chartModel.CursorsModel;
import jmp.chart.model.chartModel.CursorsRenderingModel;

public class CursorsRenderer
{
	//private ChartRenderer chartRenderer;
	private CursorRenderer cursorRenderer;
	
	public CursorsRenderer(ChartRenderer chartRenderer)
	{
		//this.chartRenderer = chartRenderer;
		this.cursorRenderer = new CursorRenderer(chartRenderer);
	}


	public void renderCursors(Graphics2D g, CursorsModel csm, CursorsRenderingModel csrm, AxisModel xam, AxisModel yam, Rectangle2D.Double displayRect)
	{
		for(Iterator<CursorModel> it= csm.iterator(); it.hasNext();)
		{
			CursorModel cm = it.next();
			this.cursorRenderer.renderCursor(g, cm, csrm.getModel(cm), xam, yam, displayRect);
		}
	}
}
