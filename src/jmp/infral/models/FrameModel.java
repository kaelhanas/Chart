/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.infral.models;

import jmp.utils.CircularArray;

/**
 *
 * @author jmp
 */
public class FrameModel extends AbstractModel<Integer> implements DataChangedListener<Integer>
{

    private int dataPosition;
    private final int pointsPerFrame;

    public FrameModel(int size, int pointsPerFrame)
    {
        super(new CircularArray<Integer>(size));
        this.dataPosition = 0;
        this.pointsPerFrame = pointsPerFrame;
    }

    @Override
    public void clear()
    {
        super.clear();
        this.dataPosition = 0;
    }

    @Override
    public synchronized void onDataChanged(CircularArray<Integer> datachanged)
    {
        final int dataSize = datachanged.size();
        while (this.dataPosition + this.pointsPerFrame < dataSize)
        {
            int min = datachanged.get(this.dataPosition), max = min;
            for (int i = 1; i < this.pointsPerFrame; i++)
            {
                int value = datachanged.get(this.dataPosition + i);
                if (value > max)
                    max = value;
                if (value < min)
                    min = value;
            }
            this.data().add(min);
            this.data().add(max);
            this.dataPosition += this.pointsPerFrame;

        }
        this.fireListeners(this.data());
    }

    public int getPointsPerFrame()
    {
        return this.pointsPerFrame;
    }
}
