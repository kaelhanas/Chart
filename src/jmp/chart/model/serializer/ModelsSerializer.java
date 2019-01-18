/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmp.chart.model.serializer;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import jmp.chart.model.models.DataModel;
import jmp.chart.model.models.FrameModel;
import jmp.chart.model.models.SpectrumModel;
import jmp.utils.CircularArray;
import jmp.utils.SoundAcquisitionParams;

/**
 *
 * @author jmp
 */
public class ModelsSerializer
{

    private static final String COL_SEPARATOR = " ";

    public static void serialize(String path, DataModel model, SoundAcquisitionParams sap)
    {
        PrintStream ps = null;
        try
        {
            ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(path)));
            CircularArray<Integer> data = model.data();
            int sample = 0;
            for (Integer v : data)
            {
                ps.print(sap.computeTime(sample++));
                ps.print(COL_SEPARATOR);
                ps.print(v);
                ps.println();
            }
        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (ps != null)
                ps.close();
        }
    }

    public static void serialize(String path, FrameModel model, SoundAcquisitionParams sap)
    {
        PrintStream ps = null;
        try
        {
            ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(path)));
            CircularArray<Integer> data = model.data();
            int sample = 0;
            boolean mustIncrementSample = false;
            for (Integer v : data)
            {
                ps.print(sap.computeTime(sample*model.getPointsPerFrame()));
                if (mustIncrementSample)
                    sample++;
                mustIncrementSample = !mustIncrementSample;
                ps.print(COL_SEPARATOR);
                ps.print(v);
                ps.println();
            }
        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (ps != null)
                ps.close();
        }

    }

    public static void serialize(String path, SpectrumModel model, int firstSpectrum, int lasSpectrum, SoundAcquisitionParams sap)
    {
        PrintStream ps = null;
        try
        {
            ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(path)));
            CircularArray<double[]> spectrums = model.data();

            if (spectrums.isEmpty())
                return;

            //print times line
            ps.print(0);
            for (int time = firstSpectrum; time < lasSpectrum; time++)
            {
                ps.print(COL_SEPARATOR);
                ps.print(sap.computeTime(time));
            }
            ps.println();

            //print spectrums
            final int fMax = spectrums.get(0).length;

            for (int freq = 0; freq < fMax; freq++)
            {
                ps.print(sap.computeFrequency(freq, model.getPointsPerSpectrum()));
                
                for (int time = firstSpectrum; time < lasSpectrum; time++)
                {
                    ps.print(COL_SEPARATOR);
                    ps.print( spectrums.get(time)[freq]);
                }
                ps.println();
            }

        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } finally
        {
            if (ps != null)
                ps.close();
        }

    }
}
