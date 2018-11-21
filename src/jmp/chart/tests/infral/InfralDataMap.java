package jmp.chart.tests.infral;

import jmp.chart.data.map.MapCircularData;
import jmp.infral.SoundAcquisitionParams;

public class InfralDataMap extends MapCircularData
{
	private final static double SCALE = 1.9;
	
	private final SoundAcquisitionParams soundParams;
	private final int fftStep;
	
	public InfralDataMap(int xsize,int ySize, SoundAcquisitionParams sp, int fftStep)
	{
		super(xsize, ySize);
		this.soundParams = sp;
		this.fftStep = fftStep;
	}
	
	protected double getXSampleValue(int sample)
	{
		return this.soundParams.computeTime(sample)*this.fftStep;
	}

	protected double getYSampleValue(int sample)
	{
		return this.soundParams.computeFrequency(sample, this.ySize()*2);
	}
	
	private double normalizeColor(double d) {
		return  Math.max(Math.pow(Math.max(Math.log(Math.abs(d)), 0), 2) * SCALE, 0);
	}

	@Override
	public double xMin()
	{
		return 0;
	}

	@Override
	public double yMin()
	{
		return 0;
	}

	@Override
	public double xMax()
	{
		return this.getXSampleValue(this.xSize());
	}

	@Override
	public double yMax()
	{
		return this.getYSampleValue(this.ySize());
	}
	
	@Override
	public void addValues(double[] value)
	{
		for(int i=0; i< value.length; i++)
			value[i] = this.normalizeColor(value[i]);
		super.addValues(value);
	}
}
