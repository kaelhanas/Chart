package jmp.chart.data.xy;


public abstract class XYSampledData implements XYData
{
	
	final public double x(int i)
	{
		return this.getSampleValue(i);
	}
	
	protected  abstract double getSampleValue(int sample);
	
}
