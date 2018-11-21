package jmp.chart.model;

import jmp.chart.data.xy.XYData;
import jmp.ui.mvc.DefaultModel;

public class ChartModel extends DefaultModel
{		
	private boolean horiziontalGridLineVisible;
	private boolean verticalGridLineVisible;
	private XYData data;

	public ChartModel()
	{
		this.horiziontalGridLineVisible = false;
		this.verticalGridLineVisible = false;
	}
	
	public boolean isHoriziontalGridLineVisible()
	{
		return this.horiziontalGridLineVisible;
	}

	public void setHoriziontalGridLineVisible(boolean horiziontalGridLineVisible)
	{
		if (this.horiziontalGridLineVisible == horiziontalGridLineVisible) return;
		this.horiziontalGridLineVisible = horiziontalGridLineVisible;
		this.modelChange();
	}

	public boolean isVerticalGridLineVisible()
	{
		return this.verticalGridLineVisible;
	}

	public void setVerticalGridLineVisible(boolean verticalGridLineVisible)
	{
		if (this.verticalGridLineVisible == verticalGridLineVisible) return;
		this.verticalGridLineVisible = verticalGridLineVisible;
		this.modelChange();
	}
	
	public XYData getData()
	{
		return this.data;
	}
	
	public void setData(XYData data)
	{
		if (this.data == data) return;
		this.data = data;
		this.modelChange();
	} 
}
