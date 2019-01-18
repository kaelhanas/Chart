/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.chart.model.models;

import java.util.ArrayList;
import java.util.List;
import jmp.utils.CircularArray;

/**
 *
 * @author jmp
 */
public abstract class AbstractModel<T>
{

    private List<DataChangedListener<T>> listeners;
    private CircularArray<T> data;

    public AbstractModel(CircularArray<T> data)
    {
        this.listeners = new ArrayList<DataChangedListener<T>>();
        this.data = data;
    }

    public void clear()
    {
        this.data.clear();
    }

    public void addDataChangedListener(DataChangedListener<T> l)
    {
        this.listeners.add(l);
    }

    public void removeDataChangedListener(DataChangedListener<T> l)
    {
        this.listeners.remove(l);
    }

    protected void fireListeners(CircularArray<T> data)
    {
        for (DataChangedListener<T> l : this.listeners)
        {
            l.onDataChanged(data);
        }
    }
    
    public CircularArray<T> data()
    {
        return this.data;
    }
}
