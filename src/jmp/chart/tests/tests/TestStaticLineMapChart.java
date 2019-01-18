package jmp.chart.tests.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
import jmp.chart.Default;
import jmp.chart.controller.DefaultChartController;
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultSampleDataMap;
import jmp.chart.data.xy.DefaultSampleDataXY;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.chartModel.MapChartRenderingModel;
import jmp.chart.model.chartModel.Side;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.utils.Constants;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class TestStaticLineMapChart extends JFrame
{
	private final static Dimension VIEW_SIZE = new Dimension(1000, 500);
	private final static double LEFT_MARGIN = 100;
	private final static Color MINOR_TICK_COLOR = Color.RED;
	
	private final int DATA_SIZE = 300000;
	private final int SPECTRUM_SIZE = 1024;
	private final int SPECTRUM_STEP = 100;

	
	private RecorderSimulator recorder;
	private DataModel dataModel;
	private SpectrumModel spectrumModel;

	private LineChartView lineChartView;
	private MapChartView mapChartView;

	public TestStaticLineMapChart()
	{}

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
		
		final Container pane = this.getContentPane();
		pane.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setupLineCharView();
		this.setupMapChartView();
		pane.add(this.mapChartView);
		pane.add(this.lineChartView);
		
		
		pane.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentHidden(ComponentEvent arg0)
			{				
			}

			@Override
			public void componentMoved(ComponentEvent arg0)
			{				
			}

			@Override
			public void componentResized(ComponentEvent arg0)
			{
				Dimension nd = new Dimension(pane.getWidth(), pane.getHeight()/2);
				lineChartView.setSize(nd);
				mapChartView.setSize(nd);
			}

			@Override
			public void componentShown(ComponentEvent arg0)
			{				
			}
			
		});

		/*
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.Y_AXIS));
		
		this.setupLineCharView();
		this.setupMapChartView();
		
		this.componentsPane.add(this.mapChartView);
		this.componentsPane.add(this.lineChartView);

		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);*/
	}
	
	private void setupLineCharView()
	{
		this.lineChartView = new LineChartView(new AxisModel("time (s)", 0, 30, 1), new AxisModel("amplitude", -4000, 4000, 500));
		this.lineChartView.xAxisModel().setMinorTickVisible(true);
		this.lineChartView.yAxisModel().setMinorTickVisible(true);
		this.lineChartView.xAxisRenderingModel().setMinortickColor(MINOR_TICK_COLOR);
		this.lineChartView.yAxisRenderingModel().setMinortickColor(MINOR_TICK_COLOR);
		this.lineChartView.chartModel().setHoriziontalGridLineVisible(true);
		this.lineChartView.chartModel().setVerticalGridLineVisible(true);
		this.lineChartView.chartRenderingModel().setMargin(Side.Left, LEFT_MARGIN);
		this.lineChartView.setSize(VIEW_SIZE);
		this.lineChartView.setController(new DefaultChartController());
	}

	private void setupMapChartView()
	{		
		this.mapChartView = new MapChartView(new AxisModel("frequency (Hz)", 0, 30,1), new AxisModel("module",  0,4000,500));
		this.mapChartView.xAxisModel().setMinorTickVisible(true);
		this.mapChartView.yAxisModel().setMinorTickVisible(true);
		this.mapChartView.xAxisRenderingModel().setMinortickColor(MINOR_TICK_COLOR);
		this.mapChartView.yAxisRenderingModel().setMinortickColor(MINOR_TICK_COLOR);
		this.mapChartView.chartModel().setHoriziontalGridLineVisible(false);
		this.mapChartView.chartModel().setVerticalGridLineVisible(false);
		this.mapChartView.chartRenderingModel().setChartBackGroundColor(Color.BLACK);
		this.mapChartView.chartRenderingModel().setMargin(Side.Left, LEFT_MARGIN);
		((MapChartRenderingModel)this.mapChartView.chartRenderingModel()).setValueColors(this.infralColor());
		this.getContentPane().add(this.mapChartView, BorderLayout.CENTER);
		this.mapChartView.setSize(VIEW_SIZE);
		this.mapChartView.setController(new DefaultChartController());
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
			this.recorder = new RecorderSimulator(Constants.path);
			dataModel = new DataModel(DATA_SIZE);

			this.spectrumModel = new SpectrumModel(DATA_SIZE/SPECTRUM_STEP,SPECTRUM_SIZE, SPECTRUM_STEP);
            this.dataModel.addDataChangedListener(this.spectrumModel);

			this.recorder.addAppendRecorderListener(new RecorderListener()
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

			this.recorder.loadSong();

			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);

			
			
			//
			//Line chart View data
			//
			XYSampledCircularData lineChartData = new DefaultSampleDataXY(DATA_SIZE, sap);
			((LineChartModel) this.lineChartView.chartModel()).setData(lineChartData);

			for (Integer v : this.dataModel.data())
			{
				lineChartData.add(v.doubleValue());
			}

			this.lineChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			this.lineChartView.autoScaleY(new DefaultAutoScaleStrategy(0.1)
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
			((MapChartModel)this.mapChartView.chartModel()).setData(mapChartData);
			
			for (double[] v : this.spectrumModel.data())
			{
				mapChartData.add(v);
			}
	
			this.mapChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			this.mapChartView.autoScaleY(new DefaultAutoScaleStrategy(1));

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
		final TestStaticLineMapChart app = new TestStaticLineMapChart();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}

}
