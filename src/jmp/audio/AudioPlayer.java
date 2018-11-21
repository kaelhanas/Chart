package jmp.audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer
{
	private AudioFormat audioFormat;
	private AudioInputStream audioInputStream;
	private SourceDataLine sourceDataLine;
	private byte playingBuffer[] = new byte[10000];

	public AudioPlayer()
	{
		this.playingBuffer = new byte[10000];
	}

	public void setup(byte dataToPlay[], AudioFormat audioFormat) throws LineUnavailableException
	{
		this.audioFormat = audioFormat;
		InputStream byteArrayInputStream = new ByteArrayInputStream(dataToPlay);
		this.audioInputStream = new AudioInputStream(byteArrayInputStream, audioFormat, dataToPlay.length / audioFormat.getFrameSize());
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
		this.sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
	}

	public void play()
	{
		try
		{
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			int cnt;
			// Keep looping until the input read method
			// returns -1 for empty stream.
			try
			{
				while ((cnt = audioInputStream.read(this.playingBuffer, 0, this.playingBuffer.length)) != -1)
				{
					if (cnt > 0)
					{
						// Write data to the internal buffer of
						// the data line where it will be
						// delivered to the speaker.
						this.sourceDataLine.write(this.playingBuffer, 0, cnt);
					}
				}
				// Block and wait for internal buffer of the data line to empty.
				sourceDataLine.drain();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				sourceDataLine.close();
			}
		}
		catch (LineUnavailableException e1)
		{
			e1.printStackTrace();
		}
	}
}
