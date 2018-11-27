package jmp.chart.tests.infral;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.Default;
import jmp.chart.data.xy.InfralVectorData;
import jmp.chart.data.xy.XYCircularData;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.AutoScaleStrategy;
import jmp.chart.model.AxisModel;
import jmp.chart.model.DefaultAutoScaleStrategy;
import jmp.chart.model.LineChartModel;
import jmp.chart.model.Side;
import jmp.chart.view.LineChartView;
import jmp.fileReader.CSVReader;
import jmp.infral.Constants;
import jmp.infral.SoundAcquisitionParams;
import jmp.infral.audioRecorder.RecorderListener;
import jmp.infral.audioRecorder.RecorderSimulator;
import jmp.infral.models.DataModel;
import jmp.readenFile.ReadenCSV;
import jmp.utils.CircularArray;
import wave.WavFileException;


public class TestCsvFromStaticLineChart extends JFrame
{
	//private DataModel dataModel;
	private LineChartView chartView;
	private String filePath;
	
	public TestCsvFromStaticLineChart(String path)
	{
		this.filePath = path;
	}
	
	public void setup() throws NumberFormatException, IOException
	{
		setTitle("Test chart from CSV");
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
		this.chartView.setController(new InfralChartController());
	}
	
	
	private void init() throws NumberFormatException, IOException, IndexOutOfBoundsException
	{
		CSVReader r = new CSVReader(filePath);
		ReadenCSV rcsv = r.read(0, 2);
		InfralVectorData chartData = rcsv.toInfralVectorData();
		((LineChartModel)this.chartView.chartModel()).setData(chartData);
		
		
		//TO-DO ajout lecture label de la premiere ligne du CSV si label il y'a avec potentiellement selection de l'index de colonne a choisir pour x et y
		
		this.chartView.autoScaleX(new DefaultAutoScaleStrategy(1));
		this.chartView.autoScaleY(new DefaultAutoScaleStrategy(1));
		
	}
		
	public static void main(String[] args)
	{
		final TestCsvFromStaticLineChart app = new TestCsvFromStaticLineChart(Constants.csvPath);
		//final TestCsvFromStaticLineChart app = new TestCsvFromStaticLineChart(Constants.csvPath);
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					app.setup();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
