package tests;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.entry.entry.AudioStaticEntry;
import jmp.factory.ChartFactory;
import jmp.factory.LineChartFactory;
import jmp.utils.Constants;

public class LineChartAudioStaticData extends JFrame{

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

		final LineChartAudioStaticData app = new LineChartAudioStaticData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					// Factory 
					final ChartFactory factory = new LineChartFactory();
					Config config = new LineChartConfig(Constants.configLineFilePath);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);

					// Data
					AudioStaticEntry entry = new AudioStaticEntry(Constants.AudioFilepath);
					entry.insertTo(chartView);
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				app.setup();

			}
		}

		);
	}

}
