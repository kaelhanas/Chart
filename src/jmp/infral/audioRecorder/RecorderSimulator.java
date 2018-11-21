/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.infral.audioRecorder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import jmp.infral.Constants;
import wave.WavFile;
import wave.WavFileException;
import wave.WaveUtils;

/**
 *
 * @author jmp
 */
public class RecorderSimulator extends Recorder
{
    private WavFile wavFile;
    private int numChannels;
    private Timer timer;
    private int recordingBuffer[];

    public RecorderSimulator(String filePath) throws FileNotFoundException, WavFileException, IOException
    {
        this.timer = new Timer();
        InputStream in = new FileInputStream(filePath);
        this.wavFile = WavFile.openWavFile(in);
        this.numChannels = wavFile.getNumChannels();
        this.recordingBuffer = new int[Constants.RECORDER_BUFFER_SIZE * numChannels];
    }

    public void loadSong() throws IOException, WavFileException
    {
        numChannels = wavFile.getNumChannels();
        int[] buffer = new int[(int) wavFile.getFramesRemaining()];
        this.wavFile.readFrames(buffer, buffer.length);
        this.onRecord(WaveUtils.deinterleave(buffer, numChannels));
    }
    
    
    public void start(long period)
    {
        this.timer.scheduleAtFixedRate(new ScheduledProcessor(), 0, period);
        this.onStart();
    }

    public void stop()
    {
        this.timer.cancel();
        this.onStop();
    }

	public void record()
	{
		try
		{
			if (wavFile.readFrames(this.recordingBuffer, Constants.RECORDER_BUFFER_SIZE) != 0)
			{
				this.onRecord(WaveUtils.deinterleave(this.recordingBuffer, numChannels));
			}
			else
			{
				this.stop();
				wavFile.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

    private class ScheduledProcessor extends TimerTask
    {
        @Override
        public void run()
        {
            record();
        }
    }
}
