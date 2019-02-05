package jmp.entry.entry;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

import javax.swing.Timer;

import jmp.audio.audioRecorder.MicroRecorder;
import jmp.audio.audioRecorder.Recorder;
import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
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
import wave.WavFileException;

public class RecorderSimulationEntry extends RecorderEntry {

	private String audioFilePath;

	private boolean timerStarted;

	public RecorderSimulationEntry(String audioFilePath) {
		super();
		this.timerStarted = false;
		this.audioFilePath = audioFilePath;
	}

	public RecorderSimulationEntry(String audioFilePath, double SAP_sampleRate, int dataSize, int spectrumSize,
			int spectrumStep) {
		super(SAP_sampleRate, dataSize, spectrumSize, spectrumStep);
		this.timerStarted = false;
		this.audioFilePath = audioFilePath;
	}

	public void insertTo(final ChartView chartView) {

		try {

			RecorderSimulator recorder = new RecorderSimulator(audioFilePath);
			final DataModel dataModel = new DataModel(getDataSize());

			// LineChart
			final DataProvider<Integer> lineDataProvider = new DataProvider<Integer>();
			dataModel.addDataChangedListener(lineDataProvider);

			// MapChart
			SpectrumModel spectrumModel = new SpectrumModel(getDataSize() / getSpectrum_Size(), getSpectrum_Size(), getSpectrum_Step());
			dataModel.addDataChangedListener(spectrumModel);
			final DataProvider<double[]> mapDataProvider = new DataProvider<double[]>();
			spectrumModel.addDataChangedListener(mapDataProvider);

			recorder.addAppendRecorderListener(new RecorderListener() {

				@Override
				public void onStop() {
				}

				@Override
				public void onStart() {
				}

				@Override
				public void onRecord(int[] newdata) {
					dataModel.append(newdata);
					// TO-DO proposer l'autoscaling en parametre de la m�thode
					// par d�faut pas d'autoscale en X
					// X autoscale en param�tre l'utiliser ici
					// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
					chartView.autoScaleY(new DefaultAutoScaleStrategy(1));
				}
			});

			if (!this.timerStarted) {
				recorder.start(50);
				timerStarted = true;
			} else {
				throw new IllegalAction("This Entry is already beeing used");
			}

			SoundAcquisitionParams sap = new SoundAcquisitionParams(getSAP_SampleRate());

			// LineChart
			if (chartView instanceof LineChartView) {
				final XYSampledCircularData lineChartData = new DefaultXYSampledData(getDataSize(), sap);
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
							chartView.chartModel().modelChange();
						}
					}
				});
				swingTimer.start();
			}

			// MapChart
			else if (chartView instanceof MapChartView) {
				final MapSampledCircularData mapChartData = new DefaultMapSampleData(getDataSize(), sap, getSpectrum_Step());
				((MapChartModel) chartView.chartModel()).setData(mapChartData);

				Timer swingTimer = new Timer(50, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						BlockingQueue<double[]> newSpectrumFrames = mapDataProvider.getNewData();
						// chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
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

			RecorderSimulator recorder = new RecorderSimulator(audioFilePath);
			final DataModel dataModel = new DataModel(getDataSize());

			// LineChart
			final DataProvider<Integer> lineDataProvider = new DataProvider<Integer>();
			dataModel.addDataChangedListener(lineDataProvider);

			// MapChart
			SpectrumModel spectrumModel = new SpectrumModel(getDataSize() / getSpectrum_Size(), getSpectrum_Size(), getSpectrum_Step());
			dataModel.addDataChangedListener(spectrumModel);
			final DataProvider<double[]> mapDataProvider = new DataProvider<double[]>();
			spectrumModel.addDataChangedListener(mapDataProvider);

			recorder.addAppendRecorderListener(new RecorderListener() {

				@Override
				public void onStop() {

				}

				@Override
				public void onStart() {

				}

				@Override
				public void onRecord(int[] newdata) {
					dataModel.append(newdata);
					// TO-DO proposer l'autoscaling en parametre de la m�thode
					// par d�faut pas d'autoscale en X
					// X autoscale en param�tre l'utiliser ici
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

			SoundAcquisitionParams sap = new SoundAcquisitionParams(getSAP_SampleRate());

			// LineChart
			final XYSampledCircularData lineChartData = new DefaultXYSampledData(getDataSize(), sap);
			((LineChartModel) lineChartView.chartModel()).setData(lineChartData);

			// MapChart
			final MapSampledCircularData mapChartData = new DefaultMapSampleData(getDataSize(), sap,getSpectrum_Step());
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
