package jmp.chart.tests.infral;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.concurrent.BlockingQueue;

import javax.swing.JFrame;
import javax.swing.Timer;

import jmp.chart.controller.DefaultChartController;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.AxisModel;
import jmp.chart.model.LineChartModel;
import jmp.chart.model.Side;
import jmp.chart.view.LineChartView;
import jmp.infral.SoundAcquisitionParams;
import jmp.infral.audioRecorder.MicroRecorder;
import jmp.infral.audioRecorder.Recorder;
import jmp.infral.audioRecorder.RecorderListener;
import jmp.infral.models.DataModel;
import jmp.infral.models.DataProvider;

public class TestMicro extends JFrame
{
	private Recorder recorder;
	private DataModel dataModel;
    private DataProvider<Integer> dataProvider;

	private LineChartView chartView;
	
	public TestMicro()
	{
	}
	
	public void setup()
	{
		setTitle("Test micro charts");
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
		this.chartView = new LineChartView(new AxisModel(0,30,1), new AxisModel("yLabel",  -6000,6000,500));
		this.chartView.xAxisModel().setMinorTickVisible(true);
		this.chartView.yAxisModel().setMinorTickVisible(true);
		this.chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.xAxisModel().setLabel("xLabel");
		this.chartView.chartModel().setHoriziontalGridLineVisible(true);
		this.chartView.chartModel().setVerticalGridLineVisible(true);
		this.chartView.chartRenderingModel().setMargin(Side.Left, 100);
		this.getContentPane().add(this.chartView, BorderLayout.CENTER);
		this.chartView.setSize(new Dimension(1000,500));
		this.chartView.setController(new DefaultChartController());
	}
	
	private void init()
	{
		try
		{
			final int DATA_SIZE = 300000;
			this.recorder = new MicroRecorder();

			this.dataModel = new DataModel(DATA_SIZE);
			
			this.dataProvider = new DataProvider<Integer>();
            this.dataModel.addDataChangedListener(this.dataProvider);

			this.recorder.addAppendRecorderListener(new RecorderListener()
			{

				@Override
				public void onStop()
				{
                    System.out.println("song loaded");
				}

				@Override
				public void onRecord(int[] newdata)
				{
					dataModel.append(newdata);
				}

				@Override
				public void onStart()
				{
                    System.out.println("song loading...");
				}
			});

			this.recorder.start(50);

	
			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);
			
			final XYSampledCircularData chartData = new InfralSampleDataXY(DATA_SIZE, sap);
			((LineChartModel)this.chartView.chartModel()).setData(chartData);
							
			Timer swingTimer = new Timer(50, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					BlockingQueue<Integer> newFrames = dataProvider.getNewData();
                    while (true)
                    {
                        Integer v = newFrames.poll();
                        if (v == null)
                        {
                            break;
                        }
        				chartData.add(v.doubleValue());
        				chartView.chartModel().modelChange();
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
		final TestMicro app = new TestMicro();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}

}
