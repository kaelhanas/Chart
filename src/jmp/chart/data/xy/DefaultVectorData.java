package jmp.chart.data.xy;

public class DefaultVectorData extends XYVectorData{
	
	private double yMin, yMax, xMin, xMax;
	
	public DefaultVectorData()
	{
		super();
	}
	
	public DefaultVectorData(int size, double yMin, double yMax, double xMin, double xMax)
	{
		super(size);
		this.yMax = yMax;
		this.yMin = yMin;
		this.xMax = xMax;
		this.xMin = xMin;
	}	
	

	@Override
	public double xMin() {

		return this.xMin;
	}


	@Override
	public double yMin() {

		return this.yMin;
	}


	@Override
	public double xMax() {

		return this.xMax;
	}


	@Override
	public double yMax() {

		return this.yMax;
	}


	public void setyMin(double yMin) {
		this.yMin = yMin;
	}


	public void setyMax(double yMax) {
		this.yMax = yMax;
	}


	public void setxMin(double xMin) {
		this.xMin = xMin;
	}


	public void setxMax(double xMax) {
		this.xMax = xMax;
	}
	
	

}
