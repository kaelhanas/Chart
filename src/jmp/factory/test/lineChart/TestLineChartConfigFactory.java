package jmp.factory.test.lineChart;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jmp.audio.audioRecorder.Recorder;
import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
import jmp.chart.Default;
import jmp.chart.data.xy.DefaultSampleDataXY;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.models.DataModel;
import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.utils.Constants;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class TestLineChartConfigFactory extends JFrame {

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
	
	private void init(LineChartView chartView)
	{
		try
		{
			final int DATA_SIZE = 300000;

			RecorderSimulator recorder = new RecorderSimulator(Constants.path);
			final DataModel dataModel = new DataModel(DATA_SIZE);

			recorder.addAppendRecorderListener(new RecorderListener()
			{

				@Override
				public void onStop()
				{
				}

				@Override
				public void onRecord(int[] newdata)
				{
					dataModel.append(newdata);
				}

				@Override
				public void onStart()
				{}
			});

			recorder.loadSong();

			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);
			
			
			XYSampledCircularData chartData = new DefaultSampleDataXY(DATA_SIZE, sap);
			((LineChartModel) chartView.chartModel()).setData(chartData);
						
			int count =0;
			for (Integer v : dataModel.data())
			{
				chartData.add(v.doubleValue());
				count+=1;
			}
			System.out.print(count);
			
			chartView.autoScaleX(new DefaultAutoScaleStrategy(100));
		
			
			chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1)
			{
				@Override
				public void process(double lowerBound, double upperBound)
				{
					super.process(lowerBound, upperBound);
					double min = Math.abs(this.lowerBound);
					double max = this.upperBound;
					double limit = (min>max)? min : max;
					this.lowerBound = -limit;
					this.upperBound = +limit;
					this.tickUnit = (this.upperBound-this.lowerBound)/Default.DEFAULT_TICK_RATIO;
				}	
			});

		}
		catch (WavFileException ex)
		{
			ex.printStackTrace();
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {

		final TestLineChartConfigFactory app = new TestLineChartConfigFactory();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {

					// Use of Factories
					final ChartFactory factory = new LineChartFactory();
					final String path = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\bin\\jmp\\config\\DefaultConfig.myConfig";
					Config config = new LineChartConfig(path);
					LineChartView chartView = (LineChartView) factory.buildChart(app, config);
					System.out.print("Coucou");

					
					// Data section
					app.init(chartView);
					
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
