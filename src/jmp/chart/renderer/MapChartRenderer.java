package jmp.chart.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.HashSet;
import java.util.Set;

import jmp.chart.data.map.MapData;
import jmp.chart.model.AxisModel;
import jmp.chart.model.ChartModel;
import jmp.chart.model.ChartRenderingModel;
import jmp.chart.model.MapChartModel;
import jmp.chart.model.MapChartRenderingModel;
import jmp.ui.mvc.View;

public class MapChartRenderer extends ChartRenderer
{
	private int xSamples;
	private int ySamples;
	private int color[];

	public MapChartRenderer(View view)
	{
		super(view);
		this.color = new int[4];
	}

	@Override
	protected void renderChart(Image image, Graphics2D g, ChartModel cm, ChartRenderingModel crm, Rectangle2D.Double displayRect)
	{
		MapData data = ((MapChartModel) cm).getMapData();
		if (data == null) return;
		//this.renderChart(g, (MapChartModel) cm, (MapChartRenderingModel) crm, displayRect, data);
		this.renderChart(((BufferedImage) image).getRaster(), (MapChartModel) cm, (MapChartRenderingModel) crm, displayRect, data);

	}

	public void updateSamplesLength(ChartModel cm, AxisModel xam, AxisModel yam,  MapData data)
    {
		
        Set<Double> sx = new HashSet<Double>();
        Set<Double> sy = new HashSet<Double>();

        final double xlb = xam.getLowerBound();
        final double xub = xam.getUpperBound();
        final double ylb = yam.getLowerBound();
        final double yub = yam.getUpperBound();
        
		for (int i = 1; i < data.xSize(); i++)
        {
            final double x = data.x(i);
			for (int j = 1; j < data.ySize(); j++)
			{
                final double y = data.y(j);

                if (x >= xlb && x <= xub)
                    sx.add(new Double(x));
                if (y >= ylb && y <= yub)
                    sy.add(new Double(y));
            }
        }
        this.xSamples = sx.size();
        this.ySamples = sy.size();
    }

	public void updateSamplesLengthForSortedData(ChartModel cm, AxisModel xam, AxisModel yam,  MapData data)
    {
        final double xlb = xam.getLowerBound();
        final double xub = xam.getUpperBound();
        final double ylb = yam.getLowerBound();
        final double yub = yam.getUpperBound();
        
        int xSamples =0,ySamples = 0;
		for (int i = 1; i < data.xSize(); i++)
        {
            final double x = data.x(i);
			for (int j = 1; j < data.ySize(); j++)
			{
                final double y = data.y(j);

                if (x >= xlb && x <= xub)
                    xSamples++;
                if (y >= ylb && y <= yub)
                    ySamples++;
            }
        }
        this.xSamples = xSamples;
        this.ySamples = ySamples;
    }

	protected void renderChart(Graphics2D g, MapChartModel cm, MapChartRenderingModel crm, Rectangle2D.Double displayRect, MapData data)
	{

		int rectDrawn = 0;
		long time = System.currentTimeMillis();

		AxisModel xam = this.view().xAxisModel();
		AxisModel yam = this.view().yAxisModel();
		
		this.updateSamplesLengthForSortedData(cm, xam, yam, data);
				
		int w = (int) Math.round(displayRect.width / this.xSamples);
		int h = (int) Math.round(displayRect.height / this.ySamples);
		w = (w == 0) ? 1 : w+1;
		h = (h == 0) ? 1 : h+1;

		//System.out.println(w + " " + h);
		//System.out.println(this.xSamples + " " + this.ySamples);
		//System.out.println(displayRect.width+ " " + displayRect.height);

		int x = 0, y = 0;
		for (int i = 1; i < data.xSize(); i++)
		{
			if (xam.isValueOnAxis(data.x(i)))
			{
				x = this.x(xam.getDisplayPosition(data.x(i)));
				for (int j = 1; j < data.ySize(); j++)
				{
					if (yam.isValueOnAxis(data.y(j)))
					{
						y = this.y(yam.getDisplayPosition(data.y(j)));
						g.setColor(crm.getValueColor((int) data.value(i, j)));
						g.fillRect(x, y, w, h);
						rectDrawn++;
					}
				}
			}
		}
		//System.out.println("rectangles drawn->"+ rectDrawn + " in ms->"+ (System.currentTimeMillis()-time));
	}
	
	protected void renderChart(WritableRaster r, MapChartModel cm, MapChartRenderingModel crm, Rectangle2D.Double displayRect, MapData data)
	{
		int rectDrawn = 0;
		long time = System.currentTimeMillis();

		AxisModel xam = this.view().xAxisModel();
		AxisModel yam = this.view().yAxisModel();
		
		this.updateSamplesLengthForSortedData(cm, xam, yam, data);
				
		int w = (int) Math.round(displayRect.width / this.xSamples);
		int h = (int) Math.round(displayRect.height / this.ySamples);
		w = (w == 0) ? 1 : w+1;
		h = (h == 0) ? 1 : h+1;

		//System.out.println(w + " " + h);
		//System.out.println(this.xSamples + " " + this.ySamples);
		//System.out.println(displayRect.width+ " " + displayRect.height);

		int x = 0, y = 0;
		for (int i = 1; i < data.xSize(); i++)
		{
			if (xam.isValueOnAxis(data.x(i)))
			{
				x = this.x(xam.getDisplayPosition(data.x(i)));
				for (int j = 1; j < data.ySize(); j++)
				{
					if (yam.isValueOnAxis(data.y(j)))
					{
						y = this.y(yam.getDisplayPosition(data.y(j)));
						final Color color = crm.getValueColor((int) data.value(i, j));
						this.color[0] = color.getRed();
						this.color[1] = color.getGreen();
						this.color[2] = color.getBlue();
						//final int[] color = crm.getIntValueColor((int) data.value(i, j));
						this.drawRasterRect(r, x, y, w, h, this.color);
						rectDrawn++;
					}
				}
			}
		}
		//System.out.println("segment drawn->"+ rectDrawn + " in ms->"+ (System.currentTimeMillis()-time));
	}
	
	protected void drawRasterRect(WritableRaster r, int x,int y,int w, int h,int[] color)
	{
		for (int rx = 0; rx < w; rx++)
			for (int ry = 0; ry < h; ry++)
				r.setPixel(rx+x, ry+y, color);
	}
	
}
