package jmp.factory.factory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.entry.entry.Entry;

public abstract class ChartFactory{

	public abstract ChartView buildChart();
	public abstract ChartView buildChart(JFrame frame);
	public abstract ChartView buildChart(JFrame frame, Config config);
	
	/*public ChartView getChartView(String chartViewType, JFrame frame)
	{
		LineChartFactory f = new LineChartFactory();
		return (LineChartView)f.buildChart(frame);
	}*/
	
}
