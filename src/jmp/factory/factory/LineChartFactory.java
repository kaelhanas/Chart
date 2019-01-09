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
import jmp.config.config.ConfigType;
import jmp.config.config.IllegalConfigTypeException;
import jmp.entry.entry.Entry;

public class LineChartFactory extends ChartFactory{


	@Override
	public ChartView buildChart(JFrame frame, Config config) {
		
		ChartView chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel",  -1000,1000,500));
		try {
			if(config.getConfigType()!=ConfigType.LineChartConfig) {throw new IllegalConfigTypeException();}
			config.readConfig(config.configFilePath());
		
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
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalConfigTypeException e) {
			System.out.println("Config argument should be a LineChartConfig one");
			e.printStackTrace();
		}
		
	
		return chartView;
	}
	
	
}
