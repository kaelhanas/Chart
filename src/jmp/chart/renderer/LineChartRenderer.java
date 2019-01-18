package jmp.chart.renderer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D.Double;

import jmp.chart.data.xy.XYData;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.ChartModel;
import jmp.chart.model.chartModel.ChartRenderingModel;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.LineChartRenderingModel;
import jmp.ui.mvc.View;

public class LineChartRenderer extends ChartRenderer
{
	public LineChartRenderer(View view)
	{
		super(view);
	}

	@Override
	protected void renderChart(Image image, Graphics2D g, ChartModel cm, ChartRenderingModel crm, Double displayRect)
	{
		XYData data = ((LineChartModel)cm).getData();
		if (data == null) return;
		
		this.renderChart(g, (LineChartModel) cm, (LineChartRenderingModel) crm, displayRect ,data);
	}

	protected void renderChart(Graphics2D g, LineChartModel cm, LineChartRenderingModel crm, Double displayRect, XYData data)
	{
		int segmentDrawn = 0;
		long time = System.currentTimeMillis();
		
		
		AxisModel xam = this.view().xAxisModel();
		AxisModel yam = this.view().yAxisModel();

		g.setColor(crm.getLineColor());
		g.setStroke(crm.getLineStroke());

		int x0 = 0, x1 = 0, y0 = 0, y1 = 0;
		for (int i = 1; i < data.xSize(); i++)
		{
			if (xam.isValueOnAxis(data.x(i - 1)))
			{
				x0 = this.x(xam.getDisplayPosition(data.x(i - 1)));
				y0 = this.y(yam.getDisplayPosition(data.y(i - 1)));
				// System.out.println("x0 : " + data.x(i)+"->"+x0);
				// System.out.println("y0 : " + data.y(i)+"->"+y0);
				
				if (xam.isValueOnAxis(data.x(i)))
				{
					x1 = this.x(xam.getDisplayPosition(data.x(i)));
					y1 = this.y(yam.getDisplayPosition(data.y(i)));
					double distance = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
					if (distance > 1)
					{
						g.drawLine(x0, y0, x1, y1);
						segmentDrawn++;
					}
					// System.out.println("x1 : " + data.x(i)+"->"+x1);
					// System.out.println("y1 : " + data.y(i)+"->"+y1);
				}
			}
		}
		//System.out.println("segments drawn->"+ segmentDrawn + " in ms->"+ (System.currentTimeMillis()-time));

	}
}
