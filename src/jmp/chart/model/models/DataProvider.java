/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.chart.model.models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import jmp.utils.CircularArray;

/**
 *
 * @author jmp
 */
public class DataProvider<T> implements DataChangedListener<T>
{
    private BlockingQueue<T> newData;
    private int index;

    public DataProvider()
    {
        this.newData = new LinkedBlockingQueue<T>();
        this.reset();
    }

    @Override
    public void onDataChanged(CircularArray<T> dataChanged)
    {
    	final int dataSize = dataChanged.size();
        for (; this.index < dataSize; this.index++)
        {
            this.newData.add(dataChanged.get(this.index));
        }
    }

    public void reset()
    {
        this.newData.clear();
        this.index = 0;
    }

    public BlockingQueue<T> getNewData()
    {
        return this.newData;
    }
}
