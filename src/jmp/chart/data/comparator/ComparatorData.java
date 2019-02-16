package jmp.chart.data.comparator;

import java.util.HashMap;

import jmp.chart.data.xy.XYData;

public class ComparatorData implements XYData{

	private HashMap<String, Double> data;
	private int dataSetNumber;
	
	public ComparatorData(int dataSetNumber) {
		this.data = new HashMap<String, Double>();
		this.dataSetNumber=dataSetNumber;
	}
	
	public ComparatorData(int Size, int dataSetNumber) {
		this.data = new HashMap<String, Double>();
		this.dataSetNumber=dataSetNumber;
	}
	
	@Override
	public int xSize() {
		return this.data.size()*this.dataSetNumber;
	}

	@Override
	public double x(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double y(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double xMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double yMin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double xMax() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double yMax() {
		// TODO Auto-generated method stub
		return 0;
	}

}
