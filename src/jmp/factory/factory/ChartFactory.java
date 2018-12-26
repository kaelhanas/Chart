package jmp.factory.factory;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jmp.chart.view.ChartView;
import jmp.config.config.Config;
import jmp.entry.Entry;

public interface ChartFactory{

	ChartView buildChart();
	ChartView buildChart(JFrame frame);
	ChartView buildChart(JFrame frame, Config config);
	ChartView buildChart(Entry entry, Config config);
	
}
