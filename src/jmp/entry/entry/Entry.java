package jmp.entry.entry;

import jmp.chart.data.xy.XYData;
import jmp.chart.view.ChartView;

public abstract class Entry {

	private ChartView chartView;
	private XYData data;
	
	public Entry(ChartView chartView)
	{
		this.chartView = chartView;
	}
	
	public XYData data()
	{
		return this.data;
	}
	
	protected void setData(XYData data)
	{
		this.data = data;
	}
	
	protected ChartView getChartView()
	{
		return this.chartView;
	}
	
	
}
