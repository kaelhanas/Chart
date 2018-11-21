package jmp.chart.data.map;

import jmp.utils.CircularArray;

public abstract class MapSampledCircularData extends MapSampledData
{
	private CircularArray<double[]> data;

	public MapSampledCircularData(int size)
	{
		this.data = new CircularArray<double[]>(size);
	}
	
	@Override
	public int ySize()
	{
		return (this.data.size() ==0)? 0: this.data.get(0).length;
	}

	@Override
	public double value(int i, int j)
	{
		return this.data.get(i)[j];
	}

	@Override
	public int xSize()
	{
		return this.data.size();
	}

	protected CircularArray<double[]> getData()
	{
		return this.data;
	}
	
	public void add(double[] value)
	{
		this.data.add(value);
	}
}
