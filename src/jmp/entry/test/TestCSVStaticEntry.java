package jmp.entry.test;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jmp.chart.Default;
import jmp.chart.data.xy.DefaultSampleDataXY;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.DefaultAutoScaleStrategy;
import jmp.chart.model.LineChartModel;
import jmp.chart.model.Side;
import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.entry.entry.CSVStaticEntry;
import jmp.entry.entry.StaticEntry;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.infral.Constants;
import jmp.infral.SoundAcquisitionParams;
import jmp.infral.audioRecorder.Recorder;
import jmp.infral.audioRecorder.RecorderListener;
import jmp.infral.audioRecorder.RecorderSimulator;
import jmp.infral.models.DataModel;
import wave.WavFileException;

public class TestCSVStaticEntry extends JFrame {

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

		final TestCSVStaticEntry app = new TestCSVStaticEntry();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					// Use of Factories
					final ChartFactory factory = new LineChartFactory();
					final String path = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\bin\\jmp\\config\\DefaultConfig.myConfig";
					Config config = new LineChartConfig(path);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);

					
					// Data section
					//app.init(chartView);
					
					CSVStaticEntry entry = new CSVStaticEntry(Constants.csvPath2, chartView);
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
