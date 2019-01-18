package jmp.chart.model.chartModel;

import jmp.chart.Default;

public class DefaultAutoScaleStrategy extends AutoScaleStrategy
{
	private final double decimal;

	public DefaultAutoScaleStrategy(double decimal)
	{
		this.decimal = decimal;
	}
	
	@Override
	public void process(double lowerBound, double upperBound)
	{
		this.adjustBounds(lowerBound, upperBound);
		
	}

	protected void adjustBounds(double lowerBound, double upperBound)
	{
		this.lowerBound =  lowerBound * decimal;
		this.upperBound =  upperBound * decimal;
		
		this.lowerBound =  Math.floor(this.lowerBound)/decimal;
		this.upperBound =  Math.ceil(this.upperBound)/decimal;
		this.tickUnit = (this.upperBound-this.lowerBound)/Default.DEFAULT_TICK_RATIO;
	}
}
