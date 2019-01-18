package jmp.chart.model.chartModel;

import java.awt.Color;

public class MapChartRenderingModel extends ChartRenderingModel
{
	private Color colors[];
	private int intColors[][];
	private final static int BLACK[] = {0,0,0,0};

	public MapChartRenderingModel()
	{
	}
	
	public Color getValueColor(int index)
	{
		if (this.colors == null) return Color.BLACK;
        if (index < 0)
            index = 0;
        if (index >= this.colors.length)
            index = this.colors.length-1;
        return this.colors[index];
	}
	
	public void setValueColors(Color[] colors)
	{
		if (this.colors == colors) return;
		this.colors = colors;
		this.setupIntColors(colors);
		this.modelChange();
	}
	
	protected void setupIntColors(Color[] colors)
	{
		this.intColors = new int[colors.length][4];
		for(int i=0; i<colors.length;i++)
		{
			this.intColors[i][0] = colors[i].getRed();
			this.intColors[i][1] = colors[i].getGreen();
			this.intColors[i][2] = colors[i].getBlue();	
		}
	}
	
	public int[] getIntValueColor(int index)
	{
		if (this.colors == null) return BLACK;
        if (index < 0)
            index = 0;
        if (index >= this.colors.length)
            index = this.colors.length-1;
        return this.intColors[index];
	}
}
