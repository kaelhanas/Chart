package jmp.entry;

import jmp.chart.data.xy.DefaultVectorData;
import jmp.chart.data.xy.XYData;

public abstract class StaticEntry implements Entry{

	private String filePath;
	private XYData data;
	
	public StaticEntry(String filePath) {
	
		this.filePath = filePath;
	}
	
	@Override
	public XYData data() {

		return this.data;
	}

}
