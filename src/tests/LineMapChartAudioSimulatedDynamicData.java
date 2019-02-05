package tests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.config.config.MapChartConfig;
import jmp.entry.entry.MicroRecorderEntry;
import jmp.entry.entry.RecorderSimulationEntry;
import jmp.factory.ChartFactory;
import jmp.factory.LineChartFactory;
import jmp.factory.MapChartFactory;
import jmp.utils.Constants;

public class LineMapChartAudioSimulatedDynamicData extends JFrame{

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

		final LineMapChartAudioSimulatedDynamicData app = new LineMapChartAudioSimulatedDynamicData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				app.setLayout(new BoxLayout(app.getContentPane(), BoxLayout.Y_AXIS));
				
				// Use of Factories
				final ChartFactory factory = new LineChartFactory();
				final ChartFactory factory2 = new MapChartFactory();
				try {
					Config config = new LineChartConfig(Constants.configLineFilePath);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);

					RecorderSimulationEntry entry = new RecorderSimulationEntry(Constants.AudioFilepath);
					entry.insertTo(chartView);
					
					Config config2 = new MapChartConfig(Constants.configMapfilePath);
					MapChartView chartView2 = (MapChartView) factory2.buildChart(app, config2);
					
					RecorderSimulationEntry entry2 = new RecorderSimulationEntry(Constants.AudioFilepath);
					entry2.insertTo(chartView2);


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
