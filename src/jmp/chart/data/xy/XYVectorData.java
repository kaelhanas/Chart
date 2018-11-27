package jmp.chart.data.xy;

import java.util.ArrayList;

public abstract class XYVectorData implements XYData{
	
	private ArrayList<Double> xdata;
	private ArrayList<Double> ydata;
	
	public XYVectorData(int size)
	{
		this.xdata = new ArrayList<Double>(size);
		this.ydata = new ArrayList<Double>(size);
	}
	

	final public int xSize()
	{
		return this.xdata.size();
	}
	
	final public int ySize()
	{
		return this.ydata.size();
	}
	
	final public double x(int i)
	{
		return this.xdata.get(i).doubleValue();
	}
	
	final public double y(int i)
	{
		return this.ydata.get(i).doubleValue();
	}
	
	final protected void setXData(ArrayList<Double> x)
	{
		this.xdata=x;
	}
	
	final protected void setYData(ArrayList<Double> y)
	{
		this.ydata=y;
	}
	
	
	protected ArrayList<Double> getXData()
	{
		return this.xdata;
	}
		
	protected ArrayList<Double> getYData()
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