package jmp.entry.entry;

import jmp.chart.data.xy.XYData;
import jmp.chart.view.ChartView;

public abstract class StaticEntry extends Entry{

	private final String filePath;
	
	public StaticEntry(String filePath, ChartView chartView) {
		super(chartView);
		this.filePath = filePath;
	}
	
	
	protected String getPath()
	{
		return filePath;
	}

	
}
