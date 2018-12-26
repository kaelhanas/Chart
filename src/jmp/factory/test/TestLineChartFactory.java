package jmp.factory.test;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jmp.chart.view.LineChartView;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;

public class TestLineChartFactory extends JFrame {

	
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
				final ChartFactory factory = new LineChartFactory();
				LineChartView chartView = (LineChartView) factory.buildChart(app);
				
				app.setup();
				
			}
		}

		);
	}

}