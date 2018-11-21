package jmp.infral.audioRecorder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import jmp.audio.AudioUtils;
import jmp.audio.TestAudio;

public class MicroRecorder extends Recorder
{
	private AudioFormat audioFormat;
	private volatile boolean recording;
	private byte recordingBuffer[];
	private TargetDataLine targetDataLine; 
	
	public MicroRecorder()
	{
		this.recording = false; 
	}

	protected void setup(AudioFormat audioFormat)  throws LineUnavailableException
	{
		this.audioFormat = audioFormat;
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, this.audioFormat);
		this.targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
		final int bufferSize = (int)this.audioFormat.getSampleRate()  * this.audioFormat.getFrameSize();
		this.recordingBuffer = new byte[bufferSize];
		this.targetDataLine.open(audioFormat);
	}
	
	@Override
	public void start(long period)
	{
		try
		{
			this.setup(TestAudio.getAudioFormat());
			this.recording = true;
			this.targetDataLine.start();
			new Thread(new RecorderTask()).start();
			this.onStart();
		}
		catch (LineUnavailableException e)
		{
			this.stop() ;
			e.printStackTrace();
		}
	}

	@Override
	public void stop()
	{
		this.recording = false;
		this.onStop();
	}

	@Override
	public void record()
	{
		while (this.recording)
		{
			int count = this.targetDataLine.read(this.recordingBuffer, 0, this.recordingBuffer.length);
			if (count > 0)
			{
				this.onRecord(AudioUtils.getIntArray(this.audioFormat, this.recordingBuffer, count));
			}
		}
	}

	private class RecorderTask implements Runnable
    {
        @Override
        public void run()
        {
            record();
        }
    }
}
