/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularArray<T> implements Iterable<T>
{

    protected T[] buffer;
    private int front = 0;
    protected int insertLoc = 0;
    private int size = 0;

    public CircularArray(int bufferSize)
    {
        this.buffer = (T[]) new Object[bufferSize];
        this.clear();
    }

    final public void clear()
    {
        this.front = 0;
        this.insertLoc = 0;
        this.size = 0;
    }

    public void add(T v)
    {
        this.buffer[this.insertLoc] = v;
        this.handelAdd();
    }

    protected void handelAdd()
    {
        this.insertLoc = (this.insertLoc + 1) % this.buffer.length;
        /**
         * If the queue is full, this means we just overwrote the front of the
         * queue. So increment the front location.
         */
        if (this.size == this.buffer.length)
            this.front = (this.front + 1) % this.buffer.length;
        //else
            this.size++;

    }
    
    public boolean isEmpty()
    {
        return this.size == 0;
    }

    public int size()
    {
        return this.size;
    }

    //get & remove the older element
    public T removeFront()
    {
        if (this.isEmpty())
            throw new NoSuchElementException();

        T res = this.buffer[this.front];
        this.front = (this.front + 1) % this.buffer.length;
        this.size--;
        return res;
    }

    //get the older element without removing it
    public T peekFront()
    {
        return (this.isEmpty()) ? null : this.buffer[this.front];
    }

    //get the last inserted element without removing it
    public synchronized T peekLast()
    {
        return (this.isEmpty()) ? null : this.buffer[this.lastInsertLocation()];
    }

    private int lastInsertLocation()
    {
        final int lastInsertLoc = this.insertLoc - 1;
        if (lastInsertLoc < 0)
            return buffer.length - 1;
        return lastInsertLoc;
    }

    public T get(int i)
    {
        if (this.size == 0 || i >= this.size || i < 0)
            throw new NoSuchElementException();

        final int index = (this.front + i) % this.buffer.length;
        return this.buffer[index];
    }

    public String toString()
    {
        StringBuilder tmp = new StringBuilder();
        for (T v : this)
        {
            tmp.append(v).append(",");
        }

        if (tmp.length() > 0)
            tmp.deleteCharAt(tmp.length() - 1);
        return "CircularArray[" + tmp.toString() + ']';
    }

    @Override
    public Iterator<T> iterator()
    {
        return new Iterator<T>()
        {

            private int index = front % buffer.length;
            private int limit = size;

            @Override
            public boolean hasNext()
            {
                return limit != 0;
            }

            @Override
            public T next()
            {
                T res = buffer[index];
                limit--;
                index = (index + 1) % buffer.length;
                return res;
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException("remove not supported");
            }
        };
    }

    public static void main(String[] args)
    {
        CircularArray<Integer> array = new CircularArray<Integer>(5);
        Integer buff[] =
        {
            1, 2, 3, 4, 5
        };

        for (Integer v : buff)
        {
            array.add(v);
        }

        System.out.println(array.size());
        System.out.println(array);

        for (int i = 0; i < array.size(); i++)
        {
            System.out.println(array.get(i));
        }

        for (int i = 6; i < 10; i++)
        {
            array.add(i);
            System.out.print(array.peekFront() + " " + array.peekLast() + " ");
            System.out.println(array);
        }

        for (Integer v : array)
        {
            System.out.print(v);
        }

        System.out.println();
        array.removeFront();
        System.out.println(array);

        System.out.println();
        while (!array.isEmpty())
        {
            System.out.println(array.size() + " " + array.removeFront() + " " + array);
        }

    }
}
