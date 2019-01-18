package jmp.factory.test;

import java.awt.Color;
import java.awt.Dimension;
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
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultSampleDataMap;
import jmp.chart.data.xy.DefaultSampleDataXY;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.config.config.MapChartConfig;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.factory.factory.MapChartFactory;
import jmp.utils.Constants;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class TestLineMapChartFactory_WithConfig_WithData extends JFrame {

	private final static Dimension VIEW_SIZE = new Dimension(1000, 500);
	private final static double LEFT_MARGIN = 100;
	private final static Color MINOR_TICK_COLOR = Color.RED;
	
	private final int DATA_SIZE = 300000;
	private final int SPECTRUM_SIZE = 1024;
	private final int SPECTRUM_STEP = 100;
	
	public void setup()
	{
		this.setTitle("Test charts");
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}
	
	private void init(LineChartView lineChartView, MapChartView mapChartView)
	{
		try
		{
			RecorderSimulator recorder = new RecorderSimulator(Constants.path);
			final DataModel dataModel = new DataModel(DATA_SIZE);

			SpectrumModel spectrumModel = new SpectrumModel(DATA_SIZE/SPECTRUM_STEP,SPECTRUM_SIZE, SPECTRUM_STEP);
            dataModel.addDataChangedListener(spectrumModel);

			recorder.addAppendRecorderListener(new RecorderListener()
			{

				@Override
				public void onStop()
				{}

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

			
			
			//
			//Line chart View data
			//
			XYSampledCircularData lineChartData = new DefaultSampleDataXY(DATA_SIZE, sap);
			((LineChartModel) lineChartView.chartModel()).setData(lineChartData);

			for (Integer v : dataModel.data())
			{
				lineChartData.add(v.doubleValue());
			}

			lineChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			lineChartView.autoScaleY(new DefaultAutoScaleStrategy(0.1)
			{
				@Override
				public void process(double lowerBound, double upperBound)
				{
					super.process(lowerBound, upperBound);
					double min = Math.abs(this.lowerBound);
					double max = this.upperBound;
					double limit = (min > max) ? min : max;
					this.lowerBound = -limit;
					this.upperBound = +limit;
					this.tickUnit = (this.upperBound - this.lowerBound) / Default.DEFAULT_TICK_RATIO;
				}
			});

			//
			//Map chart View data
			//
			MapSampledCircularData mapChartData = new DefaultSampleDataMap(DATA_SIZE, sap,SPECTRUM_STEP);
			((MapChartModel)mapChartView.chartModel()).setData(mapChartData);
			
			for (double[] v : spectrumModel.data())
			{
				mapChartData.add(v);
			}
	
			mapChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			mapChartView.autoScaleY(new DefaultAutoScaleStrategy(1));

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

		
		final TestLineMapChartFactory_WithConfig_WithData app = new TestLineMapChartFactory_WithConfig_WithData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				app.setLayout(new BoxLayout(app.getContentPane(), BoxLayout.Y_AXIS));
				
				
				// Use of Factories
				final ChartFactory lineChartFactory = new LineChartFactory();
				final ChartFactory mapChartFactory = new MapChartFactory();
				
				try {
					final Config lineChartConfig = new LineChartConfig(Constants.configFilePath);
					final Config mapChartConfig = new MapChartConfig(Constants.configFilePath);
					
					LineChartView chartView = (LineChartView) lineChartFactory.buildChart(app, lineChartConfig);
					MapChartView chartView2 = (MapChartView) mapChartFactory.buildChart(app, mapChartConfig);
					
					app.setup();
					app.init(chartView, chartView2);
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		);
	}

}
