package jmp.factory.factory;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.controller.DefaultChartController;
import jmp.chart.model.AxisModel;
import jmp.chart.view.ChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;

public class MapChartFactory extends ChartFactory {

	@Override
	public ChartView buildChart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartView buildChart(JFrame frame) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChartView buildChart(JFrame frame, Config config) {
		
		ChartView chartView = new MapChartView(new AxisModel(0,30,1) , new AxisModel("yLabel", -1000,1000, 500));
		try {
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
		}
		
		return null;
	}

}
