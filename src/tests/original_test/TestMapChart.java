package tests.original_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
import jmp.chart.controller.DefaultChartController;
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultMapSampleData;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.chartModel.MapChartRenderingModel;
import jmp.chart.model.chartModel.Side;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.MapChartView;
import jmp.utils.Constants;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class TestMapChart extends JFrame
{
	private RecorderSimulator recorder;
	private DataModel dataModel;
    private SpectrumModel spectrumModel;
	private MapChartView chartView;
	
	public TestMapChart()
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
		this.chartView = new MapChartView(new AxisModel(0, 30,1), new AxisModel("yLabel",  0,4000,500));
		this.chartView.xAxisModel().setMinorTickVisible(true);
		this.chartView.yAxisModel().setMinorTickVisible(true);
		this.chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.xAxisModel().setLabel("xLabel");
		this.chartView.chartModel().setHoriziontalGridLineVisible(false);
		this.chartView.chartModel().setVerticalGridLineVisible(false);
		this.chartView.chartRenderingModel().setChartBackGroundColor(Color.BLACK);
		this.chartView.chartRenderingModel().setMargin(Side.Left, 100);
		((MapChartRenderingModel)this.chartView.chartRenderingModel()).setValueColors(this.infralColor());
		this.getContentPane().add(this.chartView, BorderLayout.CENTER);
		this.chartView.setSize(new Dimension(1000,500));
		this.chartView.setController(new DefaultChartController());
	}
	
	private Color[] infralColor()
	{
	        Color colors[] = new Color[256];
	        int red, green, blue;
	        for (int i = 0; i < 256; i++)
	        {
	            red = i < 170 ? Math.max((i - 85) * 3, 0) : 255;
	            green = i < 170 ? Math.max((i - 85) * 3, 0) : (255 - i) * 3;
	            blue = i < 85 ? i * 3 : Math.max((170 - i) * 3, 0);
	            colors[i] = new Color(red, green, blue);
	        }
	        return colors;
	}
	

	private void init()
	{
		try
		{
			final int DATA_SIZE = 300000;
			final int SPECTRUM_SIZE = 1024;
			final int SPECTRUM_STEP = 100;

			this.recorder = new RecorderSimulator(Constants.AudioFilepath);
			dataModel = new DataModel(DATA_SIZE);
	        
			this.spectrumModel = new SpectrumModel(DATA_SIZE/SPECTRUM_STEP,SPECTRUM_SIZE, SPECTRUM_STEP);
            this.dataModel.addDataChangedListener(this.spectrumModel);

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
			
			
			MapSampledCircularData chartData = new DefaultMapSampleData(DATA_SIZE, sap,SPECTRUM_STEP);
			((MapChartModel)this.chartView.chartModel()).setData(chartData);
			
			
			for (double[] v : this.spectrumModel.data())
			{
				chartData.add(v);
			}
			
			
			/*
			InfralDataMap chartData = new InfralDataMap(DATA_SIZE,SPECTRUM_SIZE/2, sap, SPECTRUM_STEP);
			((MapChartModel)this.chartView.chartModel()).setData(chartData);
			
			int sample = 0;
			for (double[] v : this.spectrumModel.data())
			{
				chartData.addValues(v);
				chartData.addX(chartData.getXSampleValue(sample++));
			}
			for(sample=0;sample < chartData.ySize(); sample++)
				chartData.setY(sample,chartData.getYSampleValue(sample));
			*/
			this.chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			this.chartView.autoScaleY(new DefaultAutoScaleStrategy(1));


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
		final TestMapChart app = new TestMapChart();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}

}
