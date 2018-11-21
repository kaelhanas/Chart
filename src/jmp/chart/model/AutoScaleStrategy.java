package jmp.chart.model;

public abstract class AutoScaleStrategy
{
	protected double lowerBound;
	protected double upperBound;
	protected double tickUnit;
	
	public abstract void process(double lowerBound, double upperBound);

	public double getLowerBound()
	{
		return this.lowerBound;
	}

	public double getUpperBound()
	{
		return this.upperBound;
	}

	public double getTickUnit()
	{
		return this.tickUnit;
	}
}
