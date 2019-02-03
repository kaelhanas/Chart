package tests;

import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.config.config.MapChartConfig;
import jmp.factory.ChartFactory;
import jmp.factory.LineChartFactory;
import jmp.factory.MapChartFactory;
import jmp.utils.Constants;

public class LineMapChartWithoutData extends JFrame{

	
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

		
		final LineMapChartWithoutData app = new LineMapChartWithoutData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				app.setLayout(new BoxLayout(app.getContentPane(), BoxLayout.Y_AXIS));
				
				
				// Use of Factories
				final ChartFactory lineChartFactory = new LineChartFactory();
				final ChartFactory mapChartFactory = new MapChartFactory();
				
				try {
					final Config lineChartConfig = new LineChartConfig(Constants.configLineFilePath);
					final Config mapChartConfig = new MapChartConfig(Constants.configLineFilePath);
					
					LineChartView chartView = (LineChartView) lineChartFactory.buildChart(app, lineChartConfig);
					MapChartView chartView2 = (MapChartView) mapChartFactory.buildChart(app, mapChartConfig);
					
					app.setup();
					
					
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		);
	}

}
