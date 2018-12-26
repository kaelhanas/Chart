package jmp.readenFile;

import java.util.ArrayList;

import jmp.chart.data.xy.DefaultVectorData;

public class ReadenCSV implements ReadenFile{

	private String xLabel;
	private String yLabel;

	private ArrayList<Double> xdata;
	private ArrayList<Double> ydata;

	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;

	public ReadenCSV()
	{
		this.xdata = new ArrayList<Double>();
		this.ydata = new ArrayList<Double>();
		this.xMin = Double.MAX_VALUE;
		this.xMax = Double.MIN_VALUE;
		this.yMin = Double.MAX_VALUE;
		this.yMax = Double.MIN_VALUE;
		this.xLabel = "defaultXLabel";
		this.yLabel = "defaultYLabel";
	}
	
	public ReadenCSV(ArrayList<Double> xdata, double xMin, double xMax, ArrayList<Double> ydata, double yMin, double yMax) {
		this.xdata = xdata;
		this.ydata = ydata;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.xLabel = "defaultXLabel";
		this.yLabel = "defaultYLabel"; 
	}

	public ReadenCSV(String xLabel, ArrayList<Double> xdata, double xMin, double xMax, String yLabel, ArrayList<Double> ydata,
			double yMin, double yMax) {
		this.xdata = xdata;
		this.ydata = ydata;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}

	public String getxLabel() {
		return xLabel;
	}

	public void setxLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}

	public void setyLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public ArrayList<Double> getXdata() {
		return xdata;
	}

	public void addX(Double x) {
		this.xdata.add(x);
	}

	public ArrayList<Double> getYdata() {
		return ydata;
	}

	public void addY(Double y) {
		this.ydata.add(y);
	}

	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}
	
	public DefaultVectorData toInfralVectorData()
	{
		DefaultVectorData tmp = new DefaultVectorData(this.xdata.size(), yMin, yMax, xMin, xMax);
		for(int i=0; i<this.xdata.size(); i++)
		{
			tmp.addX(xdata.get(i));
			tmp.addY(ydata.get(i));
		}
		
		return tmp;
		
	}

}
