package jmp.chart.model.chartModel;


public class CursorModel
{
	private double x;
	private double y;
	private int sample;
	
	private boolean labelVisible;
	private String label;
	 
	private boolean xLineVisible;
	private boolean yLineVisible;
	private boolean markVisible;

	public CursorModel(String label, int sample, double x, double y)
	{
		this.x = x;
		this.y = y;
		this.sample = sample;
		this.label = label;
		this.labelVisible = true;
		this.xLineVisible = true;
		this.yLineVisible = true;
		this.markVisible = true;
	}
	
	public CursorModel()
	{
		this("",0,0,0);
	}
	
	public void update(double x, double y)
	{
		this.x=x;
		this.y=y;
	}

	public String getLabel()
	{
		return this.label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public double getX()
	{
		return this.x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return this.y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public boolean isLabelVisible()
	{
		return this.labelVisible;
	}

	public void setLabelVisible(boolean labelVisible)
	{
		this.labelVisible = labelVisible;
	}

	public boolean isxLineVisible()
	{
		return this.xLineVisible;
	}

	public void setxLineVisible(boolean xLineVisible)
	{
		this.xLineVisible = xLineVisible;
	}

	public boolean isyLineVisible()
	{
		return this.yLineVisible;
	}

	public void setyLineVisible(boolean yLineVisible)
	{
		this.yLineVisible = yLineVisible;
	}

	public boolean isMarkVisible()
	{
		return this.markVisible;
	}

	public void setMarkVisible(boolean markVisible)
	{
		this.markVisible = markVisible;
	}

	public void update (int sample, double x, double y)
	{
		this.sample = sample;
		this.update(x, y);
	}
	
	public void setSample(int sample)
	{
		this.sample = sample;
	}
	
	public int getSample()
	{
		return this.sample;
	}

}
