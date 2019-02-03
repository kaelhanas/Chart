package tests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.MapChartConfig;
import jmp.entry.entry.AudioStaticEntry;
import jmp.factory.ChartFactory;
import jmp.factory.MapChartFactory;
import jmp.utils.Constants;

public class MapChartAudioStaticData extends JFrame{

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

		final MapChartAudioStaticData app = new MapChartAudioStaticData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// Use of Factories
				final ChartFactory factory = new MapChartFactory();
				try {
					Config config = new MapChartConfig(Constants.configMapfilePath);
					MapChartView chartView = (MapChartView) factory.buildChart(app, config);

					AudioStaticEntry entry = new AudioStaticEntry(Constants.AudioFilepath);
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
