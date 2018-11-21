package jmp.chart.data.xy;

import jmp.utils.CircularArray;

public abstract class XYCircularData implements XYData
{
	private CircularArray<Double> xdata;
	private CircularArray<Double> ydata;

	public XYCircularData(int size)
	{
		this.xdata = new CircularArray<Double>(size);
		this.ydata = new CircularArray<Double>(size);
	}
	
	@Override
	final public int xSize()
	{
		return this.xdata.size();
	}

	@Override
	final public double y(int i)
	{
		return this.ydata.get(i).doubleValue();
	}
	
	final public double x(int i)
	{
		return this.xdata.get(i).doubleValue();
	}
	
	protected CircularArray<Double> getXData()
	{
		return this.xdata;
	}
	
	protected CircularArray<Double> getYData()
	{
		return this.ydata;
	}
	
	public void addX(double value)
	{
		this.xdata.add(value);
	}
	
	public void addY(double value)
	{
		this.ydata.add(value);
	}
}
