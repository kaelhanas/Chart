package jmp.entry.test;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.entry.entry.AudioStaticEntry;
import jmp.entry.entry.CSVStaticEntry;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.infral.Constants;

public class TestAudioStaticEntry extends JFrame{

	// TEST OK
	
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

		final TestAudioStaticEntry app = new TestAudioStaticEntry();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					// Use of Factories
					final ChartFactory factory = new LineChartFactory();
					final String path = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\bin\\jmp\\config\\DefaultConfig.myConfig";
					Config config = new LineChartConfig(path);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);

					
					AudioStaticEntry entry = new AudioStaticEntry(Constants.path, chartView);
					entry.init();
					
					
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
