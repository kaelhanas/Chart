package jmp.entry.entry;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
import jmp.chart.Default;
import jmp.chart.data.map.MapSampledCircularData;
import jmp.chart.data.xy.DefaultMapSampleData;
import jmp.chart.data.xy.DefaultXYSampledData;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.chartModel.MapChartModel;
import jmp.chart.model.chartModel.MapChartRenderingModel;
import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.SpectrumModel;
import jmp.chart.view.ChartView;
import jmp.chart.view.LineChartView;
import jmp.chart.view.MapChartView;
import jmp.config.config.Config;
import jmp.config.config.MapChartConfig;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class AudioStaticEntry extends StaticEntry{

	private int dataSize;
	private double SAP_SampleRate;
	private int Spectrum_Size;
	private int Spectrum_Step;
	
	
	public AudioStaticEntry(String filePath) {
		super(filePath);
		this.dataSize = 300000;
		this.SAP_SampleRate = 8000;
		this.Spectrum_Size = 1024;
		this.Spectrum_Step = 100;
	}
	
	public AudioStaticEntry(String filePath, double SAP_sampleRate, int dataSize, int spectrumSize, int spectrumStep) {
		super(filePath);
		this.dataSize = dataSize;
		this.SAP_SampleRate = SAP_sampleRate;
		this.Spectrum_Size = spectrumSize;
		this.Spectrum_Step = spectrumStep;
	}

	
	public void insertTo(ChartView chartView)
	{
		try
		{
			RecorderSimulator recorder = new RecorderSimulator(getPath());
			final DataModel dataModel = new DataModel(dataSize);
			
			SpectrumModel spectrumModel = new SpectrumModel(dataSize/Spectrum_Step, Spectrum_Size, Spectrum_Step);
			dataModel.addDataChangedListener(spectrumModel);
			
			recorder.addAppendRecorderListener(new RecorderListener() {
				
				@Override
				public void onStop() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onRecord(int[] newdata) {
					// TODO Auto-generated method stub
					dataModel.append(newdata);
				}
			});
			
			recorder.loadSong();
			
			SoundAcquisitionParams sap = new SoundAcquisitionParams(SAP_SampleRate);
			
			//LineChart
			if (chartView instanceof LineChartView) {
				final XYSampledCircularData LineChartData;
				LineChartData = new DefaultXYSampledData(dataSize, sap);
				chartView.chartModel().setData(LineChartData);
				

				for (Integer v : dataModel.data())
				{
					LineChartData.add(v.doubleValue());
				}
				
				chartView.autoScaleX(new DefaultAutoScaleStrategy(100));
				
				
				chartView.autoScaleY(new DefaultAutoScaleStrategy(0.1)
				{
					@Override
					public void process(double lowerBound, double upperBound)
					{
						super.process(lowerBound, upperBound);
						double min = Math.abs(this.lowerBound);
						double max = this.upperBound;
						double limit = (min>max)? min : max;
						this.lowerBound = -limit;
						this.upperBound = +limit;
						this.tickUnit = (this.upperBound-this.lowerBound)/Default.DEFAULT_TICK_RATIO;
					}	
				});
				
			}
			
			//MapChart
			else if (chartView instanceof MapChartView)
			{
				final MapSampledCircularData mapChartData = new DefaultMapSampleData(dataSize, sap, Spectrum_Step);
				((MapChartModel)chartView.chartModel()).setData(mapChartData);
				
				for (double[] v : spectrumModel.data())
				{
					mapChartData.add(v);
				}
				
				chartView.autoScaleX(new DefaultAutoScaleStrategy(0.1));
				chartView.autoScaleY(new DefaultAutoScaleStrategy(1));

			}
			
			else {throw new IllegalArgumentException();}
			
			
			
		}
		
		catch (WavFileException ex)
		{
			ex.printStackTrace();
		}
		catch (FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void insertTo(LineChartView lcv, MapChartView mcv) {
		//Reproduire le code de la méthode insertTo(ChartView) en insertTo(LineChartView, MapChartView) en prenant exemple sur celle faite dans la classe "MicroRecorderEntry"
	}
	
	
}
