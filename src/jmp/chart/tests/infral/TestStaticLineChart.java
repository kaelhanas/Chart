package jmp.chart.tests.infral;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.Default;
import jmp.chart.controller.DefaultChartController;
import jmp.chart.data.xy.XYCircularData;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.AutoScaleStrategy;
import jmp.chart.model.AxisModel;
import jmp.chart.model.DefaultAutoScaleStrategy;
import jmp.chart.model.LineChartModel;
import jmp.chart.model.Side;
import jmp.chart.view.LineChartView;
import jmp.infral.Constants;
import jmp.infral.SoundAcquisitionParams;
import jmp.infral.audioRecorder.RecorderListener;
import jmp.infral.audioRecorder.RecorderSimulator;
import jmp.infral.models.DataModel;
import jmp.utils.CircularArray;
import wave.WavFileException;

public class TestStaticLineChart extends JFrame
{
	private RecorderSimulator recorder;
	private DataModel dataModel;
	private LineChartView chartView;
	
	public TestStaticLineChart()
	{
	}
	
	public void setup()
	{
		setTitle("Test charts");
		this.setupComponentsPane();

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
		
		this.init();
	}

	private void setupComponentsPane()
	{		
		this.chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel", -4000,4000,500));
		this.chartView.xAxisModel().setMinorTickVisible(true);
		this.chartView.yAxisModel().setMinorTickVisible(true);
		this.chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.xAxisModel().setLabel("xLabel");
		this.chartView.chartModel().setHoriziontalGridLineVisible(true);
		this.chartView.chartModel().setVerticalGridLineVisible(true);
		this.chartView.chartRenderingModel().setMargin(Side.Left, 100);
		
		/*this.chartView.chartRenderingModel().setMargin(Side.Right, 100);
		this.chartView.yAxisModel().setSide(Side.Right);
		this.chartView.xAxisModel().setSide(Side.Top);*/
		
		this.getContentPane().add(this.chartView, BorderLayout.CENTER);
		this.chartView.setSize(new Dimension(1000,500));
		this.chartView.setController(new DefaultChartController());
	}
	
	
	private void init()
	{
		try
		{
			final int DATA_SIZE = 300000;

			this.recorder = new RecorderSimulator(Constants.path);
			dataModel = new DataModel(DATA_SIZE);

			this.recorder.addAppendRecorderListener(new RecorderListener()
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

			this.recorder.loadSong();

			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);
			
			
			XYSampledCircularData chartData = new InfralSampleDataXY(DATA_SIZE, sap);
			((LineChartModel)this.chartView.chartModel()).setData(chartData);
						
			int count =0;
			for (Integer v : this.dataModel.data())
			{
				chartData.add(v.doubleValue());
				count+=1;
			}
			System.out.print(count);
			
			this.chartView.autoScaleX(new DefaultAutoScaleStrategy(100));
		
			
			this.chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1)
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

			
			/*
			InfralCircularData chartData = new InfralCircularData(DATA_SIZE, sap);
			((LineChartModel)this.chartView.chartModel()).setData(chartData);
						
			int sample=0;
			for (Integer v : this.dataModel.data())
			{
				chartData.addY(v.doubleValue());
				chartData.addX(sap.computeTime(sample++));
			}
			*/
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
		
	public static void main(String[] args)
	{
		final TestStaticLineChart app = new TestStaticLineChart();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}

}
