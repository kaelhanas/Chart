/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.utils;

/**
 *
 * @author infral
 */
public class ManagedCircularArray<T> extends CircularArray<T> {

    private ElementAllocator<T> allocator;

    public ManagedCircularArray(int bufferSize, ElementAllocator<T> allocator) {
        super(bufferSize);
        this.allocator = allocator;
        this.allocateElements();
    }

    final private void allocateElements() {
        for (int i=0; i<this.buffer.length;i++) {
            this.buffer[i] = this.allocator.newElement();
        }
    }
    
    public T getAndAdd()
    {
        T res= this.buffer[this.insertLoc];
        this.handelAdd();
        return res;
    }
}
