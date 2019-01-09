package jmp.factory.factory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.controller.DefaultChartController;
import jmp.chart.model.AxisModel;
import jmp.chart.model.MapChartRenderingModel;
import jmp.chart.view.ChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;

public class MapChartFactory extends ChartFactory {

	private Color[] infralColor() {
		Color colors[] = new Color[256];
		int red, green, blue;
		for (int i = 0; i < 256; i++) {
			red = i < 170 ? Math.max((i - 85) * 3, 0) : 255;
			green = i < 170 ? Math.max((i - 85) * 3, 0) : (255 - i) * 3;
			blue = i < 85 ? i * 3 : Math.max((170 - i) * 3, 0);
			colors[i] = new Color(red, green, blue);
		}
		return colors;
	}
	
	@Override
	public ChartView buildChart(JFrame frame, Config config) {

		ChartView chartView = new MapChartView(new AxisModel(0, 30, 1), new AxisModel("yLabel", -1000, 1000, 500));
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
			((MapChartRenderingModel) chartView.chartRenderingModel()).setValueColors(this.infralColor());

			return chartView;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		throw new NullPointerException("No MapChartView returned there");

	}

}
