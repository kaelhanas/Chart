package jmp.chart.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import jmp.chart.model.AxisModel;
import jmp.chart.view.LineChartView;

public class TestChart extends JFrame
{
	private LineChartView chartView;

	public TestChart()
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
	}

	

	private void setupComponentsPane()
	{		
		this.chartView = new LineChartView(new AxisModel(0,1,0.1), new AxisModel("yLabel",0,100,20));
		this.chartView.xAxisModel().setMinorTickVisible(true);
		this.chartView.yAxisModel().setMinorTickVisible(true);
		this.chartView.xAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.yAxisRenderingModel().setMinortickColor(Color.RED);
		this.chartView.xAxisModel().setLabel("xLabel");
		this.chartView.chartModel().setHoriziontalGridLineVisible(true);
		this.chartView.chartModel().setVerticalGridLineVisible(true);
		this.getContentPane().add(this.chartView, BorderLayout.CENTER);
	}
	
	public static void main(String[] args)
	{
		final TestChart app = new TestChart();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}