package jmp.chart.model.chartModel;

import jmp.chart.data.map.MapData;

public class MapChartModel extends ChartModel
{
	public MapData getMapData()
	{
		return (MapData) this.getData();
	}
}
