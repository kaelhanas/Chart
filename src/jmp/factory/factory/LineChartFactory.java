package jmp.factory.factory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.controller.DefaultChartController;
import jmp.chart.model.AxisModel;
import jmp.chart.model.Side;
import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.entry.Entry;

public class LineChartFactory implements ChartFactory{

	public LineChartFactory()
	{}
	
	public ChartView buildChart()
	{
		ChartView chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel",  -1000,1000,500));
		chartView.xAxisModel().setMinorTickVisible(true);
		chartView.yAxisModel().setMinorTickVisible(true);
		chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		chartView.xAxisModel().setLabel("xLabel");
		chartView.chartModel().setHoriziontalGridLineVisible(true);
		chartView.chartModel().setVerticalGridLineVisible(true);
		chartView.chartRenderingModel().setMargin(Side.Left, 100);
		//getContentPane().add(chartView, BorderLayout.CENTER);
		chartView.setSize(new Dimension(1000,500));
		chartView.setController(new DefaultChartController());
	
		return chartView;
	}
	
	public ChartView buildChart(Entry entry, Config config)
	{
		return null;
	}

	@Override
	public ChartView buildChart(JFrame frame) {
		
		ChartView chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel",  -1000,1000,500));
		chartView.xAxisModel().setMinorTickVisible(true);
		chartView.yAxisModel().setMinorTickVisible(true);
		chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		chartView.xAxisModel().setLabel("xLabel");
		chartView.chartModel().setHoriziontalGridLineVisible(true);
		chartView.chartModel().setVerticalGridLineVisible(true);
		chartView.chartRenderingModel().setMargin(Side.Left, 100);
		frame.getContentPane().add(chartView, BorderLayout.CENTER);
		chartView.setSize(new Dimension(1000,500));
		chartView.setController(new DefaultChartController());
	
		return chartView;
	}

	@Override
	public ChartView buildChart(JFrame frame, Config config) {
		
		ChartView chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel",  -1000,1000,500));
		try {
			config.readConfig(config.configFilePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chartView.xAxisModel().setMinorTickVisible(config.xAxisMinorTickVisible());
		chartView.yAxisModel().setMinorTickVisible(config.yAxisMinorTickVisible());
		chartView.xAxisRenderingModel().setMinortickColor(config.xAxisMinorTickColor());
		chartView.yAxisRenderingModel().setMinortickColor(config.yAxisMinorTickColor());
		chartView.xAxisModel().setLabel(config.xAxisLabel());
		chartView.yAxisModel().setLabel(config.yAxisLabel());
		chartView.chartModel().setHoriziontalGridLineVisible(config.horizontalGridLineVisible());
		chartView.chartModel().setVerticalGridLineVisible(config.verticalGridLineTickVisible());
		chartView.chartRenderingModel().setMargin(config.marginSide(), config.marginValue());
		frame.getContentPane().add(chartView, BorderLayout.CENTER);
		chartView.setSize(config.viewSize());
		chartView.setController(new DefaultChartController());
		
	
		return chartView;
	}
	
	
}
