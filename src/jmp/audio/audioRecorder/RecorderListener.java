/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.audio.audioRecorder;

/**
 *
 * @author jmp
 */
public interface RecorderListener
{

    public void onStart();

    public void onStop();

    public void onRecord(int[] newdata);
}
