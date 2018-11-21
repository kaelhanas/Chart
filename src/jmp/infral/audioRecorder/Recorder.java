package jmp.infral.audioRecorder;

import java.util.ArrayList;
import java.util.List;

public abstract class Recorder
{
	protected List<RecorderListener> listeners;

	public Recorder()
	{
		this.listeners = new ArrayList<RecorderListener>();
	}

	public void addAppendRecorderListener(RecorderListener l)
	{
		this.listeners.add(l);
	}

	public void removeRecorderListener(RecorderListener l)
	{
		this.listeners.remove(l);
	}

	protected void onRecord(int[] newdata)
	{
		for (RecorderListener l : this.listeners)
		{
			l.onRecord(newdata);
		}
	}

	protected void onStop()
	{
		for (RecorderListener l : this.listeners)
		{
			l.onStop();
		}
	}

	protected void onStart()
	{
		for (RecorderListener l : this.listeners)
		{
			l.onStart();
		}
	}

	public abstract void start(long period);
	public abstract void stop();
	public abstract void record();
}
