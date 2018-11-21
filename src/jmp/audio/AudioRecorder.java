package jmp.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import wave.WaveUtils;

public class AudioRecorder
{
	private AudioFormat audioFormat;
	private volatile boolean recording;
	private byte recordingBuffer[];
	private TargetDataLine targetDataLine; 
	private ByteArrayOutputStream byteArrayOutputStream;
	
	public AudioRecorder() 
	{
		this.recording = false; 
	}

	public void setup(AudioFormat audioFormat) throws LineUnavailableException
	{
		this.audioFormat = audioFormat;
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, this.audioFormat);
		this.targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
		final int bufferSize = (int)this.audioFormat.getSampleRate()  * this.audioFormat.getFrameSize();
		this.recordingBuffer = new byte[bufferSize];
		this.targetDataLine.open(audioFormat);
	}

	public void records() 
	{
		try
		{
			this.byteArrayOutputStream = new ByteArrayOutputStream();
			
			while (this.recording)
			{
				int count = this.targetDataLine.read(this.recordingBuffer, 0, this.recordingBuffer.length);
				if (count > 0)
				{
					this.byteArrayOutputStream.write(this.recordingBuffer, 0, count);
				}
			}
			this.byteArrayOutputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void startRecording()
	{
		this.recording = true;
		this.targetDataLine.start();
	}
	
	public synchronized void stopRecording()
	{
		this.recording = false;
	}

	public synchronized boolean isRecording()
	{
		return this.recording;
	}

	public synchronized byte[] dataToRecord()
	{
		return this.byteArrayOutputStream.toByteArray();
	}

}
