package jmp.entry.entry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jmp.chart.data.xy.DefaultXYVectorData;
import jmp.chart.data.xy.XYData;
import jmp.chart.data.xy.XYVectorData;
import jmp.chart.model.chartModel.DefaultAutoScaleStrategy;
import jmp.chart.view.BarChartView;
import jmp.chart.view.ChartView;

public class CSVStaticEntry extends StaticEntry {

	private int xIndex, yIndex;
	private XYVectorData data;

	public CSVStaticEntry(String filePath) {
		super(filePath);
		xIndex = 0;
		yIndex = 1;

	}

	public CSVStaticEntry(String filePath, int xIndex, int yIndex) {
		super(filePath);
		this.xIndex = xIndex;
		this.yIndex = yIndex;

	}


	private DefaultXYVectorData readCSV(String path, ChartView chartView, int xIndex, int yIndex)
			throws IOException, FileNotFoundException, IndexOutOfBoundsException {

		if (xIndex < 0 || yIndex < 0) {
			throw new IndexOutOfBoundsException();
		}

		List<Double> xdatas = new ArrayList<Double>();
		List<Double> ydatas = new ArrayList<Double>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line;
		boolean labelCheck = false;
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;

		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			/*
			 * for (String s : fields) { System.out.print(s + ", "); }
			 * 
			 * System.out.println("\n");
			 */
			if (xIndex >= fields.length || yIndex >= fields.length) {
				br.close();
				throw new IndexOutOfBoundsException();
			}

			if (!labelCheck && hasLabel(path)) {
				chartView.xAxisModel().setLabel(fields[xIndex]);
				chartView.yAxisModel().setLabel(fields[yIndex]);
				labelCheck = true;
			}

			else {
				double x = Double.parseDouble(fields[xIndex]);
				if (x > xMax) {
					xMax = x;
				}
				if (x < xMin) {
					xMin = x;
				}
				xdatas.add(x);
				double y = Double.parseDouble(fields[yIndex]);
				if (y > yMax) {
					yMax = y;
				}
				if (y < yMin) {
					yMin = y;
				}
				ydatas.add(y);
			}

		}

		br.close();

		XYVectorData data = new DefaultXYVectorData(xdatas.size(), yMin, yMax, xMin, xMax);
		for (int i = 0; i < xdatas.size(); i++) {
			// data.addX(xdatas.get(i));
			// data.addY(ydatas.get(i));
			data.add(xdatas.get(i), ydatas.get(i));
		}


		return (DefaultXYVectorData) data;

	}

	private static Boolean hasLabel(String path) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		boolean hasLabel = true;

		String line = reader.readLine();
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			String data = scanner.next();
			if (data.matches("[0-9]*"))
				hasLabel = false;

		}

		scanner.close();
		reader.close();
		return hasLabel;
	}
	
	public void insertTo(ChartView chartView) {

		try {
			data = readCSV(this.getPath(),chartView, xIndex, yIndex);
		} catch (IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
		}

		chartView.chartModel().setData(data);
		chartView.autoScale(new DefaultAutoScaleStrategy(1), new DefaultAutoScaleStrategy(1));
	}
	
	public void insertTo(BarChartView chartView) {
		
	}

}
