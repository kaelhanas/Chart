package jmp.chart.data.map;

import jmp.utils.CircularArray;

public abstract class MapCircularData implements MapData
{
	private CircularArray<Double> xdata;
	private double[] ydata;
	private CircularArray<double[]> vdata;


	public MapCircularData(int xsize, int ysize)
	{
		this.vdata = new CircularArray<double[]>(xsize);
		this.xdata = new CircularArray<Double>(xsize);
		this.ydata = new double[ysize];
	}
	
	@Override
	public int xSize()
	{
		return this.xdata.size();
	}

	@Override
	public int ySize()
	{
		return this.ydata.length;
	}

	public int vSize()
	{
		return this.vdata.size();
	}

	@Override
	public double value(int x, int y)
	{
		return this.vdata.get(x)[y];
	}

	@Override
	public double x(int i)
	{
		return this.xdata.get(i).doubleValue();
	}

	@Override
	public double y(int i)
	{
		return this.ydata[i];
	}
	
	protected CircularArray<Double> getXData()
	{
		return this.xdata;
	}
	
	protected double[] getYData()
	{
		return this.ydata;
	}

	protected CircularArray<double[]> getVData()
	{
		return this.vdata;
	}

	public void addX(double value)
	{
		this.xdata.add(value);
	}
	
	public void setY(int i, double value)
	{
		this.ydata[i] = value;
	}
	
	public void addValues(double[] values)
	{
		this.vdata.add(values);
	}
}
