/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.infral;

/**
 *
 * @author jmp
 */
public class SoundAcquisitionParams {

    
    private final double sampleRate;
    private final double sampleTime;

    //sampleRate in HZ
    public SoundAcquisitionParams(double sampleRate)
    {
    	this.sampleRate = sampleRate;
    	this.sampleTime = 1./sampleRate;
    }
    
    public double sampleTime()
    {
        return this.sampleTime;
    }
    
    public double computeTime(int sample)
    {
        return sample*this.sampleTime;
    }
    
    public double computeFrequency(int sample, int FFTSize)
    {
        return sample*this.sampleRate/FFTSize;
    }
}
