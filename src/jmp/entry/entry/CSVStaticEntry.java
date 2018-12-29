package jmp.entry.entry;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import jmp.chart.data.xy.DefaultVectorData;
import jmp.chart.data.xy.XYData;
import jmp.chart.data.xy.XYVectorData;
import jmp.chart.model.DefaultAutoScaleStrategy;
import jmp.chart.view.ChartView;

public class CSVStaticEntry extends StaticEntry {

	private int xIndex, yIndex;
	private XYVectorData data;

	public CSVStaticEntry(String filePath, ChartView chartView) {
		super(filePath, chartView);
		xIndex = 0;
		yIndex = 1;
		
	}

	public CSVStaticEntry(String filePath, ChartView chartView, int xIndex, int yIndex) {
		super(filePath, chartView);
		this.xIndex = xIndex;
		this.yIndex = yIndex;

	}

	public void init()
	{
		
		try {
			data = readCSV(this.getPath(), xIndex, yIndex);
		} catch (IndexOutOfBoundsException | IOException e) {
			e.printStackTrace();
		}
		
		this.getChartView().chartModel().setData(data);
		this.getChartView().autoScale(new DefaultAutoScaleStrategy(1), new DefaultAutoScaleStrategy(1));
	}

	private DefaultVectorData readCSV(String path, int xIndex, int yIndex)
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
			/*for (String s : fields) {
				System.out.print(s + ", ");
			}
			
			System.out.println("\n");*/
			if (xIndex >= fields.length || yIndex >= fields.length) {
				throw new IndexOutOfBoundsException();
			}
			
			if (!labelCheck && hasLabel(path)) {
				this.getChartView().xAxisModel().setLabel(fields[xIndex]);
				this.getChartView().yAxisModel().setLabel(fields[yIndex]);
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

		XYVectorData data = new DefaultVectorData(xdatas.size(), yMin, yMax, xMin, xMax);
		for (int i = 0; i < xdatas.size(); i++) {
			data.addX(xdatas.get(i));
			data.addY(ydatas.get(i));
		}

		//System.out.println(xdatas.toString() + "\n" + ydatas.toString());

		
		setData(data); //Data setting in Entry class for an eventual public call
		
		return (DefaultVectorData) data;

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

	
	public static void main(String[] args) throws FileNotFoundException, IndexOutOfBoundsException, IOException {

		/*LineChartView chartView = new LineChartView(new AxisModel(0, 30, 1), new AxisModel("yLabel", -4000, 4000, 500));
		readCSV(Constants.csvPath, 0, 1, chartView);
		System.out.println(
				"xlabel::" + chartView.xAxisModel().getLabel() + "\nylabel::" + chartView.yAxisModel().getLabel());*/

	}

}
