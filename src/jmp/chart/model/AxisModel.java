package jmp.chart.model;

import java.util.ArrayList;
import java.util.List;

import jmp.chart.Default;
import jmp.ui.mvc.DefaultModel;

public class AxisModel extends DefaultModel
{
	private double displayLength;
	private double lowerBound;
	private double upperBound;
	private double scale;
	
	private String label;
	private Side side;
	
	private boolean tickVisible;
	private double tickUnit;
	private boolean minorTickVisible;
	private int minorTickCount;
	
	private List<Double> tickValues;
	private List<Double> minorTickValues;

	public AxisModel()
	{
		this("",0,0,0);
	}
	
	public AxisModel(double lowerBound, double upperBound, double tickUnit)
	{
		this("",lowerBound,upperBound,tickUnit);
	}
	
	public AxisModel(String label, double lowerBound, double upperBound,double tickUnit)
	{
		this.label = label;
		this.checkRange(lowerBound, upperBound);
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.tickUnit = tickUnit;
		this.side = Side.Bottom;
		this.minorTickCount = Default.MINOR_TICK_COUNT;
		this.minorTickVisible = true;
		this.tickVisible = true;
		this.tickValues = new ArrayList<Double>();
		this.minorTickValues = new ArrayList<Double>();
		this.calculateTickValues(this.tickValues);
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
	}

	public double getLowerBound()
	{
		return this.lowerBound;
	}

	public void setLowerBound(double lowerBound)
	{
		if (this.lowerBound == lowerBound) return;
		this.checkRange(lowerBound, this.upperBound);
		this.lowerBound = lowerBound;
		this.scale = this.computeScale(this.lowerBound, this.upperBound, this.displayLength);
		this.calculateTickValues(this.tickValues);
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
		this.modelChange();
	}

	public double getUpperBound()
	{
		return this.upperBound;
	}

	public void setUpperBound(double upperBound)
	{
		if (this.upperBound == upperBound) return;
		this.checkRange(this.lowerBound, upperBound);
		this.upperBound = upperBound;
		this.scale = this.computeScale(this.lowerBound, this.upperBound, this.displayLength);
		this.calculateTickValues(this.tickValues);
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
		this.modelChange();
	}

	public String getLabel()
	{
		return this.label;
	}

	public void setLabel(String label)
	{
		if (this.label == label) return;
		this.label = label;
		this.modelChange();
	}

	public Side getSide()
	{
		return this.side;
	}

	public void setSide(Side side)
	{
		if (this.side == side) return;
		this.side = side;
		this.modelChange();
	}

	public boolean isTickVisible()
	{
		return this.tickVisible;
	}

	public void setTickVisible(boolean tickVisible)
	{
		if (this.tickVisible == tickVisible) return;
		this.tickVisible = tickVisible;
		this.modelChange();
	}

	public double getTickUnit()
	{
		return this.tickUnit;
	}

	public void setTickUnit(double tickUnit)
	{
		if (this.tickUnit == tickUnit) return;
		this.tickUnit = tickUnit;
		this.calculateTickValues(this.tickValues);
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
		this.modelChange();
	}

	public boolean isMinorTickVisible()
	{
		return this.minorTickVisible;
	}

	public void setMinorTickVisible(boolean minorTickVisible)
	{
		if (this.minorTickVisible == minorTickVisible) return;
		this.minorTickVisible = minorTickVisible;
		this.modelChange();
	}

	public int getMinorTickCount()
	{
		return this.minorTickCount;
	}

	public void setMinorTickCount(int minorTickCount)
	{
		if (this.minorTickCount == minorTickCount) return;
		this.minorTickCount = minorTickCount;
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
		this.modelChange();
	}

	public double getScale()
	{
		return this.scale;
	}
	
	public double getDisplayPosition(double value)
	{
		double chartPos = value-this.lowerBound;
		return this.scale*chartPos;
	}
	
	public double getValueForDisplay(double displayPosition)
	{
		return displayPosition/this.scale + this.lowerBound;
	}

	public boolean isValueOnAxis(double value)
	{
		return value >= this.lowerBound && value <= this.upperBound;
	}
	
	public double getDisplayLength()
	{
		return displayLength;
	}

	public void setDisplayLength(double displayLength)
	{
		if (this.displayLength == displayLength) return;
		this.displayLength = displayLength;
		this.scale = this.computeScale(this.lowerBound, this.upperBound, displayLength);
		this.modelChange();
	}
	
	final protected double computeScale(double lowerBound, double upperBound, double displayLength)
	{
		return displayLength/(upperBound-lowerBound);
	}
	
	final protected void checkRange(double lowerBound, double upperBound)
	{
		if (upperBound < lowerBound) throw new IllegalArgumentException("upper bound < lower bound");
	}
	
	protected void calculateMinorTickValues(List<Double> tickValues, List<Double> minorTickMarks)
	{
		this.minorTickValues.clear();
		final double step = this.tickUnit/(this.minorTickCount+1);
		for(int tick=0; tick< tickValues.size()-1; tick++)
		{
			double minorTick = tickValues.get(tick).doubleValue()+step;
			for(int i=0; i< this.minorTickCount;i++,minorTick+=step) 
			{
				minorTickMarks.add(minorTick);
			}
		}
	}
	
	protected void calculateTickValues(List<Double> tickValues) 
	{
		tickValues.clear();
		if (this.lowerBound == this.upperBound) return;
		for(double tick= this.lowerBound; tick <= this.upperBound; tick+= this.tickUnit)
			tickValues.add(tick);
	}

	public List<Double> getTickValues()
	{
		return this.tickValues;
	}

	public List<Double> getMinorTickValues()
	{
		return this.minorTickValues;
	}

	public void autoScale(double lowerBound, double upperBound, AutoScaleStrategy as)
	{
		this.checkRange(lowerBound, upperBound);
		as.process(lowerBound, upperBound);
		this.lowerBound = as.getLowerBound();
		this.upperBound = as.getUpperBound();
		this.tickUnit = as.getTickUnit();
		this.scale = this.computeScale(this.lowerBound, this.upperBound, this.displayLength);
		this.calculateTickValues(this.tickValues);
		this.calculateMinorTickValues(this.tickValues, this.minorTickValues);
		this.modelChange();
	}
	
	
}
