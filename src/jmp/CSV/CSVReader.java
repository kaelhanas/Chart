package jmp.CSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import jmp.chart.data.xy.InfralVectorData;

public class CSVReader {

	private String csvPath;
	
	
	public CSVReader(String path)
	{
		this.csvPath = path;
	}
	
	public static int getArraySize(String path) throws IOException
	{
		int size = 0;
		BufferedReader reader = new BufferedReader(new FileReader(path));
		
		while ((reader.readLine()) != null)
		{
			size++;
		}
		
		
		return size;
	}
	
	public InfralVectorData read() throws NumberFormatException, IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(this.csvPath));
		
		String line = null;
		Scanner scanner = null;
		int index = 0;
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		
		InfralVectorData xydata = new InfralVectorData(getArraySize(csvPath), yMin, yMax, xMin, xMax);
		
		while ((line = reader.readLine()) != null) 
		{
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			
			while(scanner.hasNext())
			{
				String data = scanner.next();
				
				if(index == 0)
				{
					xydata.addX(Double.parseDouble(data));
					
					if(Double.parseDouble(data) >= xMax)
						xMax = Double.parseDouble(data);
					else if(Double.parseDouble(data) <= xMin)
						xMin = Double.parseDouble(data);
				}
				
				else if(index == 1)
				{
					xydata.addY(Double.parseDouble(data));
					
					if(Double.parseDouble(data) >= yMax)
						yMax = Double.parseDouble(data);
					else if(Double.parseDouble(data) <= yMin)
						yMin = Double.parseDouble(data);
				}
				
				else
					System.out.println("Invalid Data::"+data);
				
				index++;
			}
			index=0;
		}
		
		xydata.setxMax(xMax);
		xydata.setxMin(xMin);
		xydata.setyMax(yMax);
		xydata.setyMin(yMin);
		
		reader.close();
		
		return xydata;
	}

	
	public static void main(String[] args) throws IOException
	{
		
		
		String csvFile = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\csv\\CSV_test.csv";
		CSVReader r = new CSVReader(csvFile);
		InfralVectorData data = r.read();
		
		/*System.out.println(data.xMin());
		System.out.println(data.xMax());
		System.out.println(data.yMin());
		System.out.println(data.yMax());
		System.out.println(data.getXData().toString());
		System.out.println(data.getYData().toString());*/
		
		
		
		
		/*
		BufferedReader reader = new BufferedReader(new FileReader(csvFile));
	
		String line = null;
		Scanner scanner = null;
		int index = 0;
		ArrayList<Double> xdata = new ArrayList<Double>();
		ArrayList<Double> ydata = new ArrayList<Double>();
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		
	
		while ((line = reader.readLine()) != null) 
		{
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			
			while(scanner.hasNext())
			{
				String data = scanner.next();
				
				if(index == 0)
				{
					xdata.add(Double.parseDouble(data));
					
					if(Double.parseDouble(data) >= xMax)
						xMax = Double.parseDouble(data);
					else if(Double.parseDouble(data) <= xMin)
						xMin = Double.parseDouble(data);
				}
				
				else if(index == 1)
				{
					ydata.add(Double.parseDouble(data));
					
					if(Double.parseDouble(data) >= yMax)
						yMax = Double.parseDouble(data);
					else if(Double.parseDouble(data) <= yMin)
						yMin = Double.parseDouble(data);
				}
				
				else
					System.out.println("Invalid Data::"+data);
				
				index++;
			}
			index=0;
			
		}

		// A utiliser plus tard pour la detection de la premiere ligne si label il y a
		
		String s1 = "0123";
		String s2 = "je ne suis pas un nombre";
		System.out.println(s1.matches("[0-9]*"));		
		System.out.println(s2.matches("[0-9]*"));
		
		reader.close();
		
		System.out.println(xdata.toString());
		System.out.println(ydata.toString());
		System.out.println("xMin::"+xMin+", xMax::"+xMax+", yMin::"+yMin+", yMax::"+yMax);
	
		*/
	}
	

}
