package jmp.entry.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.Timer;

import jmp.audio.audioRecorder.MicroRecorder;
import jmp.audio.audioRecorder.Recorder;
import jmp.audio.audioRecorder.RecorderListener;
import jmp.chart.Default;
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultMapSampleData;
import jmp.chart.data.xy.DefaultXYSampledData;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.LineChartModel;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.DataProvider;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.utils.SoundAcquisitionParams;

public class MicroRecorderEntry extends DynamicEntry {

	private int dataSize;
	private double SAP_SampleRate;
	private int Spectrum_Size;
	private int Spectrum_Step;

	private boolean timerStarted;

	public MicroRecorderEntry() {
		this.dataSize = 300000;
		this.SAP_SampleRate = 8000;
		this.Spectrum_Size = 1024;
		this.Spectrum_Step = 100;
		this.timerStarted = false;
	}

	public MicroRecorderEntry(double SAP_sampleRate, int dataSize, int spectrumSize, int spectrumStep) {
		this.dataSize = dataSize;
		this.SAP_SampleRate = SAP_sampleRate;
		this.Spectrum_Size = spectrumSize;
		this.Spectrum_Step = spectrumStep;
		this.timerStarted = false;
	}

	public void insertTo(final ChartView chartView) {

		try {
			boolean isLineChart;
			boolean isMapChart;

			Recorder recorder = new MicroRecorder();
			final DataModel dataModel = new DataModel(dataSize);

			// LineChart
			final DataProvider<Integer> lineDataProvider = new DataProvider<Integer>();
			dataModel.addDataChangedListener(lineDataProvider);

			// MapChart
			SpectrumModel spectrumModel = new SpectrumModel(dataSize / Spectrum_Step, Spectrum_Size, Spectrum_Step);
			dataModel.addDataChangedListener(spectrumModel);
			final DataProvider<double[]> mapDataProvider = new DataProvider<double[]>();
			spectrumModel.addDataChangedListener(mapDataProvider);

			recorder.addAppendRecorderListener(new RecorderListener() {

				@Override
				public void onStop() {
					System.out.println("song loaded");

				}

				@Override
				public void onStart() {
					System.out.println("song loading...");
				}

				@Override
				public void onRecord(int[] newdata) {
					System.out.println("appending record");
					dataModel.append(newdata);
					// TO-DO proposer l'autoscaling en parametre de la méthode
					// par défaut pas d'autoscale en X
					// X autoscale en paramètre l'utiliser ici
					// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
					chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1));
				}
			});

			if (!this.timerStarted) {
				recorder.start(50);
				timerStarted = true;
			} else {
				throw new IllegalAction("This Entry is already beeing used");
			}

			SoundAcquisitionParams sap = new SoundAcquisitionParams(SAP_SampleRate);

			// LineChart
			if (isLineChart = (chartView instanceof LineChartView)) {
				final XYSampledCircularData lineChartData = new DefaultXYSampledData(dataSize, sap);
				((LineChartModel) chartView.chartModel()).setData(lineChartData);

				Timer swingTimer = new Timer(50, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						BlockingQueue<Integer> newFrames = lineDataProvider.getNewData();
						while (true) {
							Integer v = newFrames.poll();
							if (v == null) {
								break;
							}
							lineChartData.add(v.doubleValue());
							// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
							// chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1));
							chartView.chartModel().modelChange();
						}
					}
				});
				swingTimer.start();
			}

			// MapChart
			else if (isMapChart = (chartView instanceof MapChartView)) {
				final MapSampledCircularData mapChartData = new DefaultMapSampleData(dataSize, sap, Spectrum_Step);
				((MapChartModel) chartView.chartModel()).setData(mapChartData);

				Timer swingTimer = new Timer(50, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						BlockingQueue<double[]> newSpectrumFrames = mapDataProvider.getNewData();
						chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
						chartView.autoScaleY(new DefaultAutoScaleStrategy(1));
						while (true) {
							double[] s = newSpectrumFrames.poll();
							if (s == null) {
								break;
							}
							mapChartData.add(s);
							chartView.chartModel().modelChange();
						}
					}
				});
				swingTimer.start();

			}

			else {
				throw new IllegalArgumentException();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void insertTo(final LineChartView lineChartView, final MapChartView mapChartView) {

		try {

			if (!(lineChartView instanceof LineChartView)) {
				throw new IllegalArgumentException("The first argument isn't a LineChartView !");
			}
			
			if (!(mapChartView instanceof MapChartView)) {
				throw new IllegalArgumentException("The first argument isn't a MapChartView !");
			}

			Recorder recorder = new MicroRecorder();
			final DataModel dataModel = new DataModel(dataSize);

			// LineChart
			final DataProvider<Integer> lineDataProvider = new DataProvider<Integer>();
			dataModel.addDataChangedListener(lineDataProvider);

			// MapChart
			SpectrumModel spectrumModel = new SpectrumModel(dataSize / Spectrum_Step, Spectrum_Size, Spectrum_Step);
			dataModel.addDataChangedListener(spectrumModel);
			final DataProvider<double[]> mapDataProvider = new DataProvider<double[]>();
			spectrumModel.addDataChangedListener(mapDataProvider);

			recorder.addAppendRecorderListener(new RecorderListener() {

				@Override
				public void onStop() {
					System.out.println("song loaded");

				}

				@Override
				public void onStart() {
					System.out.println("song loading...");
				}

				@Override
				public void onRecord(int[] newdata) {
					System.out.println("appending record");
					dataModel.append(newdata);
					// TO-DO proposer l'autoscaling en parametre de la méthode
					// par défaut pas d'autoscale en X
					// X autoscale en paramètre l'utiliser ici
					// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
					lineChartView.autoScaleY(new DefaultAutoScaleStrategy(0.1));
				}
			});

			if (!this.timerStarted) {
				recorder.start(50);
				timerStarted = true;
			} else {
				throw new IllegalAction("This Entry is already beeing used");
			}

			SoundAcquisitionParams sap = new SoundAcquisitionParams(SAP_SampleRate);

			// LineChart
			final XYSampledCircularData lineChartData = new DefaultXYSampledData(dataSize, sap);
			((LineChartModel) lineChartView.chartModel()).setData(lineChartData);

			//MapChart
			final MapSampledCircularData mapChartData = new DefaultMapSampleData(dataSize, sap, Spectrum_Step);
			((MapChartModel) mapChartView.chartModel()).setData(mapChartData);
			
			Timer swingTimer = new Timer(50, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					BlockingQueue<Integer> newFrames = lineDataProvider.getNewData();
					BlockingQueue<double[]> newSpectrumFrames = mapDataProvider.getNewData();
					
					while (true) {
						Integer v = newFrames.poll();
						if (v == null) {
							break;
						}
						lineChartData.add(v.doubleValue());
						// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
						// chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1));
						lineChartView.chartModel().modelChange();
					}
					
					mapChartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
					mapChartView.autoScaleY(new DefaultAutoScaleStrategy(1));
					while (true) {
						double[] s = newSpectrumFrames.poll();
						if (s == null) {
							break;
						}
						mapChartData.add(s);
						mapChartView.chartModel().modelChange();
					}
					
					
				}
			});
			swingTimer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
