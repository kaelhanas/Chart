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
public interface DataChangedListener<T>
{
    public void onDataChanged(CircularArray<T> dataChanged);
}
