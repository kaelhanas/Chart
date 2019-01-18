package jmp.entry.entry;

import java.io.FileNotFoundException;
import java.io.IOException;

import jmp.audio.audioRecorder.RecorderListener;
import jmp.audio.audioRecorder.RecorderSimulator;
import jmp.chart.Default;
import jmp.chart.data.xy.DefaultSampleDataXY;
import jmp.chart.data.xy.XYSampledCircularData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.model.models.DataModel;
import jmp.chart.view.ChartView;
import jmp.utils.SoundAcquisitionParams;
import wave.WavFileException;

public class AudioStaticEntry extends StaticEntry{

	private int dataSize;
	private double SAP_SampleRate;
	
	public AudioStaticEntry(String filePath, ChartView chartView, int dataSize, double SAP_SampleRate) {
		super(filePath, chartView);
		this.dataSize = dataSize;
		this.SAP_SampleRate = SAP_SampleRate;
	}
	
	public AudioStaticEntry(String filePath, ChartView chartView)
	{
		super(filePath, chartView);
		this.dataSize = 300000;
		this.SAP_SampleRate = 8000;
	}

	
	public void init()
	{
		try
		{
			RecorderSimulator recorder = new RecorderSimulator(getPath());
			final DataModel dataModel = new DataModel(dataSize);
			
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
			
			XYSampledCircularData chartData = new DefaultSampleDataXY(dataSize, sap);
			getChartView().chartModel().setData(chartData);
			
			int count =0;
			for (Integer v : dataModel.data())
			{
				chartData.add(v.doubleValue());
				count+=1;
			}
			System.out.print(count);
			
			getChartView().autoScaleX(new DefaultAutoScaleStrategy(100));
		
			
			getChartView().autoScaleY(new DefaultAutoScaleStrategy(0.1)
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
			
			setData(chartData);
			
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
	
	
}
