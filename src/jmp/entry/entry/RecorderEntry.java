package jmp.entry.entry;

public abstract class RecorderEntry extends DynamicEntry{

	private int dataSize;
	private double SAP_SampleRate;
	private int Spectrum_Size;
	private int Spectrum_Step;
	
	public RecorderEntry() {
		this.dataSize = 300000;
		this.SAP_SampleRate = 8000;
		this.Spectrum_Size = 1024;
		this.Spectrum_Step = 100;
	}

	public RecorderEntry(double SAP_sampleRate, int dataSize, int spectrumSize,
			int spectrumStep) {
		this.dataSize = dataSize;
		this.SAP_SampleRate = SAP_sampleRate;
		this.Spectrum_Size = spectrumSize;
		this.Spectrum_Step = spectrumStep;

	}

	protected int getDataSize() {
		return dataSize;
	}

	protected double getSAP_SampleRate() {
		return SAP_SampleRate;
	}

	protected int getSpectrum_Size() {
		return Spectrum_Size;
	}

	protected int getSpectrum_Step() {
		return Spectrum_Step;
	}
	
	
	
}
