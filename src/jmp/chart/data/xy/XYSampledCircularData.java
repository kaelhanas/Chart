package jmp.chart.data.xy;

import jmp.utils.CircularArray;

public abstract class XYSampledCircularData extends XYSampledData
{
	private CircularArray<Double> data;
	
	public XYSampledCircularData(int size)
	{
		this.data = new CircularArray<Double>(size);
	}
	
	@Override
	final public int xSize()
	{
		return this.data.size();
	}

	@Override
	final public double y(int i)
	{
		return this.data.get(i).doubleValue();
	}
	
	protected CircularArray<Double> getData()
	{
		return this.data;
	}
	
	public void add(double value)
	{
		this.data.add(value);
	}
}
