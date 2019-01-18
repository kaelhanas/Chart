/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.chart.model.models;

import jmp.utils.CircularArray;
import jmp.utils.ElementAllocator;
import jmp.utils.FFT;
import jmp.utils.ManagedCircularArray;

/**
 *
 * @author jmp
 */
public class SpectrumModel extends AbstractModel<double[]> implements DataChangedListener<Integer>
{

    private int dataPosition;
    private final int pointsPerSpectrum;
    private final int step;
    private FFT fft;
    private int fftIn[];

    public SpectrumModel(int size, final int pointsPerSpectrum, int step)
    {
        super(new ManagedCircularArray<double[]>(size, new ElementAllocator<double[]>(){
            public double[] newElement()
            {
                return new double[pointsPerSpectrum/2];
            }
        }));
        
        this.dataPosition = 0;
        this.pointsPerSpectrum = pointsPerSpectrum;
        this.step = step;
        this.fft = new FFT(pointsPerSpectrum);
        this.fftIn = new int[pointsPerSpectrum];
    }

    public void clear()
    {
        super.clear();
        this.dataPosition = 0;
    }

    @Override
    public void onDataChanged(CircularArray<Integer> dataChanged)
    {
        if (dataChanged.size() < this.pointsPerSpectrum)
            return;
        final int dataSize = dataChanged.size();
        while (this.dataPosition + this.pointsPerSpectrum < dataSize)
        {
            for (int i = 0; i < this.pointsPerSpectrum; i++)
            {
                this.fftIn[i] = dataChanged.get(this.dataPosition + i);
            }
            double[] fftResultBuffer = ((ManagedCircularArray<double[]>)this.data()).getAndAdd();
            this.fft.computeSpectrum(fftIn,fftResultBuffer);
            this.dataPosition += this.step;
        }
        this.fireListeners(this.data());
    }

    public int getStep()
    {
        return this.step;
    }

    public int getPointsPerSpectrum()
    {
        return this.pointsPerSpectrum;
    }
}
