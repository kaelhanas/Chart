package jmp.chart.data.map;

public abstract class MapSampledData implements MapData
{
	final public double x(int i)
	{
		return this.getXSampleValue(i);
	}
	
	final public double y(int i)
	{
		return this.getYSampleValue(i);
	}
	
	protected  abstract double getXSampleValue(int sample);
	protected  abstract double getYSampleValue(int sample);
}
