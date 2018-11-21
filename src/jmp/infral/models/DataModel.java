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
public class DataModel extends AbstractModel<Integer>
{

    public DataModel(int size)
    {
        super(new CircularArray<Integer>(size));
    }

    public void append(int[] newData)
    {
        for (int d : newData)
        {
            this.data().add(d);
        }
        this.fireListeners(this.data());
    }

    public int[] asIntBuffer()
    {
        int[] buffer = new int[this.data().size()];
        int i=0;
        for(Integer v: this.data())
             buffer[i++] = v;
        return buffer;
    }
}
