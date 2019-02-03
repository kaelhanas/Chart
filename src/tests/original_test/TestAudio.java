package tests.original_test;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.JButton;
import javax.swing.JFrame;

import jmp.audio.AudioPlayer;
import jmp.audio.AudioRecorder;
import jmp.audio.AudioUtils;

public class TestAudio extends JFrame
{
	private JButton recordButton;
	private JButton stopButton;
	private JButton playButton;
	private AudioRecorder recorder;
	private AudioPlayer player;

	public void setup()
	{
		setTitle("Test audio");
		this.setupComponentsPane();
		this.setupActions();

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

	private void setupComponentsPane()
	{
		this.getContentPane().setLayout(new FlowLayout());

		this.recordButton = new JButton("record");
		this.stopButton = new JButton("stop");
		this.playButton = new JButton("play");

		this.getContentPane().add(this.recordButton);
		this.getContentPane().add(this.stopButton);
		this.getContentPane().add(this.playButton);
		
	}

	private void setupActions()
	{
		this.recorder = new AudioRecorder();

		this.recordButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					System.out.println("*****Record button pressed");
					recorder.setup(TestAudio.getAudioFormat());
					recorder.startRecording();
				}
				catch (LineUnavailableException e)
				{
					e.printStackTrace();
					System.exit(0);
				}

				recordButton.setEnabled(false);
				playButton.setEnabled(false);
				stopButton.setEnabled(true);

				Runnable rAction = new Runnable()
				{
					@Override
					public void run()
					{
						recorder.records();
						//recordButton.setEnabled(true);
						//playButton.setEnabled(true);
					}
				};
				new Thread(rAction).start();
			}
		});

		this.stopButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("*****Stop button pressed");
				//recordButton.setEnabled(true);
				playButton.setEnabled(true);
				
				if(recorder.isRecording()){
					recorder.stopRecording();
				}
				
				else {
					player.stop(); try {
					Thread.currentThread().sleep(1000);
					player.reset();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}


			}
		});

		this.player = new AudioPlayer();
		this.playButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					System.out.println("*****Play button pressed");
					AudioFormat af = TestAudio.getAudioFormat();
					//player.setup(recorder.dataToRecord(), af);

					player.setup(byte2int2byte(recorder.dataToRecord(), af), af);
				}
				catch (LineUnavailableException e1)
				{
					e1.printStackTrace();
					System.exit(0);
				}

				recordButton.setEnabled(false);
				playButton.setEnabled(false);

				Runnable pAction = new Runnable()
				{
					@Override
					public void run()
					{
						player.play();
						playButton.setEnabled(true);
					}
				};
				new Thread(pAction).start();
			}
		});

	}

	public byte[] byte2int2byte(byte[] data, AudioFormat af)
	{
		int[] intData = AudioUtils.getIntArray(af, data);
		return AudioUtils.getByteArray(af, intData);
	}
	
	public static AudioFormat getAudioFormat()
	{
		float sampleRate = 8000;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = true;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}

	public static void testMixer()
	{
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		System.out.println("Available mixers:");
		for (Mixer.Info mi : mixerInfo)
			System.out.println(mi);
	}

	public static void main(String args[])
	{
		testMixer();
		final TestAudio app = new TestAudio();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
