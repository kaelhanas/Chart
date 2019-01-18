/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.utils;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 *
 * @author jmp
 */
public class FFT
{

    private final int bufferSize;
    private double[] in;
    private final double[] hannWindow;
    private DoubleFFT_1D fft;

    public FFT(int bufferSize)
    {
        this.bufferSize = bufferSize;
        this.in = new double[bufferSize];
        this.hannWindow = new double[bufferSize];
        FFT.hannWindow(this.hannWindow);
        this.fft = new DoubleFFT_1D(bufferSize);
    }

    public static void hannWindow(double buffer[])
    {
        final int sz = buffer.length;
        for (int i = 0; i < sz; ++i)
        {
            buffer[i] = (0.5 * (1 - Math.cos(2 * Math.PI * i / (sz - 1))));
        }
    }

    public static double[] toDouble(int intBuffer[], double doubleBuffer[])
    {
        if (intBuffer.length != doubleBuffer.length)
            throw new RuntimeException("intBuffer size: " + intBuffer.length + " is different of doubleBuffer size: " + doubleBuffer.length);

        for (int i = 0; i < intBuffer.length; ++i)
        {
            doubleBuffer[i] = (double) intBuffer[i];
        }
        return doubleBuffer;
    }

    protected void forward(double[] buffer)
    {
        if (buffer.length != this.bufferSize)
            throw new RuntimeException("Supplied buffer has not the defined " + in.length + " size.");

        for (int i = 0; i < this.bufferSize; i++)
        {
            this.in[i] *= this.hannWindow[i];
        }
        this.fft.realForward(this.in);
    }

    public double[] computeSpectrum(int[] buffer)
    {
        FFT.toDouble(buffer, this.in);
        this.forward(this.in);
        return this.spectrum(this.in, new double[this.in.length / 2]);
    }
    
    protected double[] spectrum(double[] fftdata ,double[] fftResult)
    {
        final int limit = fftdata.length / 2 - 1;
        for (int k = 0; k < limit - 1; k++)
        {
            fftResult[k] = Math.sqrt(Math.pow(fftdata[2 * k], 2) + Math.pow(fftdata[2 * k + 1], 2));
        }
        return fftResult;
    }

    public double[] computeSpectrum(int[] buffer, double[] fftResult)
    {
        if (fftResult.length != buffer.length/2)
            throw new RuntimeException(" buffer size must be equal to 2* fftResult size");

        FFT.toDouble(buffer, this.in);
        this.forward(this.in);
        return this.spectrum(this.in, fftResult);
    }
}
