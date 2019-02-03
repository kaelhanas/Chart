package tests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.config.config.MapChartConfig;
import jmp.entry.entry.MicroRecorderEntry;
import jmp.factory.ChartFactory;
import jmp.factory.LineChartFactory;
import jmp.factory.MapChartFactory;
import jmp.utils.Constants;

public class LineChartAudioDynamicData extends JFrame{

	public void setup() {
		this.setTitle("Test charts");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {

		final MapChartWithoutData app = new MapChartWithoutData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// Use of Factories
				final ChartFactory factory = new LineChartFactory();
				try {
					Config config = new LineChartConfig(Constants.configLineFilePath);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);

					MicroRecorderEntry entry = new MicroRecorderEntry();
					entry.insertTo(chartView);

					app.setup();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}
		}

		);
	}

}
