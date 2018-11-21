package jmp.chart.data.map;

import jmp.chart.data.xy.XYData;

public interface MapData extends XYData
{
	int ySize();
	double value(int i, int j);
}
