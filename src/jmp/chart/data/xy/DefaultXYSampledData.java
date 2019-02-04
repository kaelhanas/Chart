package jmp.chart.data.xy;

import jmp.utils.SoundAcquisitionParams;

public class DefaultXYSampledData extends XYSampledCircularData
{
	private final SoundAcquisitionParams soundParams;
	private double yMin, yMax;
	
	public DefaultXYSampledData(int size, SoundAcquisitionParams sp)
	{
		super(size);
		this.soundParams = sp;
		this.yMax = Double.MIN_VALUE;
		this.yMin = Double.MAX_VALUE;
	}

	@Override
	protected double getSampleValue(int sample)
	{
		return this.soundParams.computeTime(sample);
	}

	@Override
	public double xMin()
	{
		return 0;
	}

	@Override
	public double yMin()
	{
		return this.yMin;
	}

	@Override
	public double xMax()
	{
		return this.getSampleValue(this.xSize());
	}

	@Override
	public double yMax()
	{
		return this.yMax;
	}
	
	@Override
	public void add(double value)
	{
		super.add(value);
		if (value > this.yMax) this.yMax = value;
		if (value < this.yMin) this.yMin = value;
	}
}