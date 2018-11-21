package jmp.chart.tests.infral;

import jmp.chart.data.xy.XYCircularData;
import jmp.infral.SoundAcquisitionParams;

public class InfralCircularData extends XYCircularData
{
	private final SoundAcquisitionParams soundParams;
	private double yMin, yMax;
	
	public InfralCircularData(int size, SoundAcquisitionParams sp)
	{
		super(size);
		this.soundParams = sp;
		this.yMax = Double.MIN_VALUE;
		this.yMin = Double.MAX_VALUE;
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
		return this.soundParams.computeTime(this.xSize());
	}

	@Override
	public double yMax()
	{
		return this.yMax;
	}
	
	@Override
	public void addY(double value)
	{
		super.addY(value);
		if (value > this.yMax) this.yMax = value;
		if (value < this.yMin) this.yMin = value;
	}
}
