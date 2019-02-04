package tests.original_test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import jmp.audio.AudioPlayer;
import jmp.audio.AudioUtils;
import jmp.audio.audioRecorder.MicroRecorder;
import jmp.audio.audioRecorder.Recorder;
import jmp.audio.audioRecorder.RecorderListener;
import jmp.chart.Default;
import jmp.chart.controller.DefaultChartController;
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultMapSampleData;
import jmp.chart.data.xy.DefaultXYSampledData;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.AxisModel;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.chartModel.MapChartRenderingModel;
import jmp.chart.model.chartModel.Side;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.DataProvider;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.utils.SoundAcquisitionParams;

public class TestMicro2 extends JFrame
{
	private final static Dimension VIEW_SIZE = new Dimension(1000, 500);
	private final static double LEFT_MARGIN = 100;
	private final static Color MINOR_TICK_COLOR = Color.RED;
	
	private final int DATA_SIZE =300000;
	private final int SPECTRUM_SIZE = 1024;
	private final int SPECTRUM_STEP = 100;

	
	private AudioPlayer player;
	private Recorder recorder;
	private DataModel dataModel;
	private SpectrumModel spectrumModel;
    private DataProvider<Integer> linedataProvider;
	private DataProvider<double[]> mapdataProvider;

	private LineChartView lineChartView;
	private MapChartView mapChartView;
	private JPanel controlPanel;
	
	public TestMicro2()
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
		final JPanel centerPane = new JPanel();
		centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
		this.setupLineCharView();
		this.setupMapChartView();
		this.setupControlPanel();
		this.getContentPane().add(this.controlPanel, BorderLayout.NORTH);
		this.getContentPane().add(centerPane, BorderLayout.CENTER);
		centerPane.add(this.mapChartView);
		centerPane.add(this.lineChartView);
		
		
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
				Dimension nd = new Dimension(centerPane.getWidth(), centerPane.getHeight()/2);
				//Dimension nd = new Dimension(pane.getWidth(), (pane.getHeight()-controlPanel.getHeight())/2);

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
	
	private void setupControlPanel()
	{
		this.controlPanel = new JPanel(new FlowLayout());
		
		this.controlPanel.setBackground(Default.CHART_BACKGROUND_COLOR);
		final JButton recordButton = new JButton("record");
		JButton stopButton = new JButton("stop");
		final JButton playButton = new JButton("play");

		recordButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				recordButton.setEnabled(false);
				playButton.setEnabled(false);
				recorder.start(0);
			}
		});
		
		stopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				recordButton.setEnabled(true);
				playButton.setEnabled(true);
				recorder.stop();
			}
		});

		playButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					final AudioFormat af =  TestAudio.getAudioFormat();
					player.setup(AudioUtils.getByteArray(af, dataModel.asIntBuffer()),af);
				}
				catch (LineUnavailableException e1)
				{
					e1.printStackTrace();
					System.exit(0);
				}

				recordButton.setEnabled(false);
				playButton.setEnabled(false);

				Runnable pAction = new Runnable()
				{
					@Override
					public void run()
					{
						player.play();
						recordButton.setEnabled(true);
						playButton.setEnabled(true);
					}
				};
				new Thread(pAction).start();

			}
		});
		
	
		this.controlPanel.add(recordButton);
		this.controlPanel.add(stopButton);
		this.controlPanel.add(playButton);
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
			this.player = new AudioPlayer();
			//this.recorder = new RecorderSimulator("/Users/jmp/Documents/Personnel/dvlpt/java/chart/songs/ALB.wav");
			this.recorder = new MicroRecorder();
			this.dataModel = new DataModel(DATA_SIZE);

			this.linedataProvider = new DataProvider<Integer>();
            this.dataModel.addDataChangedListener(this.linedataProvider);

			this.spectrumModel = new SpectrumModel(DATA_SIZE/SPECTRUM_STEP,SPECTRUM_SIZE, SPECTRUM_STEP);
			this.dataModel.addDataChangedListener(this.spectrumModel);
			this.mapdataProvider = new DataProvider<double[]>();
            this.spectrumModel.addDataChangedListener(this.mapdataProvider);

			this.recorder.addAppendRecorderListener(new RecorderListener()
			{
				@Override
				public void onStop()
				{
                    System.out.println("song loaded");
                    /*
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

                    mapChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
        			mapChartView.autoScaleY(new DefaultAutoScaleStrategy(1));
				*/
				}

				@Override
				public void onRecord(int[] newdata)
				{
					System.out.println("appending record");
					dataModel.append(newdata);
				}

				@Override
				public void onStart()
				{
                    System.out.println("song loading...");
				}
			});

			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);

			//Line chart View data
			final XYSampledCircularData lineChartData = new DefaultXYSampledData(DATA_SIZE, sap);
			((LineChartModel) this.lineChartView.chartModel()).setData(lineChartData);

			//Map chart View data
			final MapSampledCircularData mapChartData = new DefaultMapSampleData(DATA_SIZE, sap,SPECTRUM_STEP);
			((MapChartModel)this.mapChartView.chartModel()).setData(mapChartData);

			Timer swingTimer = new Timer(50, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					BlockingQueue<Integer> newDataFrames = linedataProvider.getNewData();
					BlockingQueue<double[]> newSpectrumFrames = mapdataProvider.getNewData();

                    while (true)
                    {
                        Integer v = newDataFrames.poll();
                        if (v == null)
                        {
                            break;
                        }
                        lineChartData.add(v.doubleValue());
        				lineChartView.chartModel().modelChange();
                    }
                    while (true)
                    {        				
                        double[] s = newSpectrumFrames.poll();
                        if (s == null)
                        {
                            break;
                        }
                        mapChartData.add(s);
        				mapChartView.chartModel().modelChange();
                    }
				}
			}); 
			swingTimer.start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		final TestMicro2 app = new TestMicro2();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}

}
