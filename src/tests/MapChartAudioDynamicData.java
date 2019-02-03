package tests;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.MapChartConfig;
import jmp.entry.entry.MicroRecorderEntry;
import jmp.factory.ChartFactory;
import jmp.factory.MapChartFactory;
import jmp.utils.Constants;

public class MapChartAudioDynamicData extends JFrame{

	public void setup() {
		this.setTitle("Test charts");
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent winEvt) {
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

	

	public static Color[] infralColor() {
		Color colors[] = new Color[256];
		int red, green, blue;
		for (int i = 0; i < 256; i++) {
			red = i < 170 ? Math.max((i - 85) * 3, 0) : 255;
			green = i < 170 ? Math.max((i - 85) * 3, 0) : (255 - i) * 3;
			blue = i < 85 ? i * 3 : Math.max((170 - i) * 3, 0);
			colors[i] = new Color(red, green, blue);
		}
		return colors;
	}

	public static void main(String[] args) {

		final MapChartWithoutData app = new MapChartWithoutData();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// Use of Factories
				final ChartFactory factory = new MapChartFactory();
				try {
					Config config = new MapChartConfig(Constants.configMapfilePath);
					MapChartView chartView = (MapChartView) factory.buildChart(app, config);

					MicroRecorderEntry entry = new MicroRecorderEntry();
					entry.insertTo(chartView);
					
					//ColorsValue has a default one, you need to setup a new one like bellow in case you want to change it
					//((MapChartRenderingModel) chartView.chartRenderingModel()).setValueColors(infralColor());

					app.setup();

				} catch (IOException e) {
					e.printStackTrace();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}

			}
		}

		);
	}

}
