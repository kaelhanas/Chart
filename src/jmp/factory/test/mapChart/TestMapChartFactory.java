package jmp.factory.test.mapChart;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.factory.factory.MapChartFactory;
import jmp.factory.test.lineChart.TestLineChartFactory;

public class TestMapChartFactory extends JFrame{

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
	
	public static void main(String[] args) {

		
		final TestLineChartFactory app = new TestLineChartFactory();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				// Use of Factories
				final ChartFactory factory = new MapChartFactory();
				MapChartView chartView = (MapChartView) factory.buildChart(app);
				
				app.setup();
				
			}
		}

		);
	}
	
}
