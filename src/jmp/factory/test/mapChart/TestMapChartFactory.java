package jmp.factory.test.mapChart;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultSampleDataMap;
import jmp.chart.model.DefaultAutoScaleStrategy;
import jmp.chart.model.MapChartModel;
import jmp.chart.model.MapChartRenderingModel;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.config.config.MapChartConfig;
import jmp.factory.factory.ChartFactory;
import jmp.factory.factory.LineChartFactory;
import jmp.factory.factory.MapChartFactory;
import jmp.factory.test.lineChart.TestLineChartFactory;
import jmp.infral.Constants;
import jmp.infral.SoundAcquisitionParams;
import jmp.infral.audioRecorder.RecorderListener;
import jmp.infral.audioRecorder.RecorderSimulator;
import jmp.infral.models.DataModel;
import jmp.infral.models.SpectrumModel;
import wave.WavFileException;

public class TestMapChartFactory extends JFrame {

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

	public static void init(MapChartView chartView) {
		try {
			final int DATA_SIZE = 300000;
			final int SPECTRUM_SIZE = 1024;
			final int SPECTRUM_STEP = 100;

			RecorderSimulator recorder = new RecorderSimulator(Constants.path);
			final DataModel dataModel = new DataModel(DATA_SIZE);

			SpectrumModel spectrumModel = new SpectrumModel(DATA_SIZE / SPECTRUM_STEP, SPECTRUM_SIZE, SPECTRUM_STEP);
			dataModel.addDataChangedListener(spectrumModel);

			recorder.addAppendRecorderListener(new RecorderListener() {
				@Override
				public void onStop() {
				}

				@Override
				public void onRecord(int[] newdata) {
					dataModel.append(newdata);
				}

				@Override
				public void onStart() {
				}
			});

			recorder.loadSong();

			SoundAcquisitionParams sap = new SoundAcquisitionParams(8000);

			MapSampledCircularData chartData = new DefaultSampleDataMap(DATA_SIZE, sap, SPECTRUM_STEP);
			((MapChartModel) chartView.chartModel()).setData(chartData);

			for (double[] v : spectrumModel.data()) {
				chartData.add(v);
			}

			chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
			chartView.autoScaleY(new DefaultAutoScaleStrategy(1));

		} catch (WavFileException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
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

		final TestLineChartFactory app = new TestLineChartFactory();
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				// Use of Factories
				final ChartFactory factory = new MapChartFactory();
				final String path = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\bin\\jmp\\config\\DefaultConfig.myConfig";
				try {
					Config config = new MapChartConfig(path);
					MapChartView chartView = (MapChartView) factory.buildChart(app, config);

					
					//ColorsValue has a default one, you need to setup a new one like bellow in case you want to change it
					//((MapChartRenderingModel) chartView.chartRenderingModel()).setValueColors(infralColor());

					app.setup();
					init(chartView);

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
