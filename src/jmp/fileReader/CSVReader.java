package jmp.fileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import jmp.chart.data.xy.DefaultVectorData;
import jmp.readenFile.ReadenCSV;
import jmp.readenFile.ReadenFile;

public class CSVReader extends FileReader{

	private String csvPath;

	public CSVReader(String csvPath) throws FileNotFoundException {
		super(csvPath);
		this.csvPath = csvPath;
	}

	protected static Boolean hasLabel(String path) throws IOException {
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

	public ReadenCSV read(String xlabelName, String yLabelName) {
		//TO-DO chercher les colonnes par label, necessite que les colonnes du CSV aient des label => création d'une exception lié à "hasLabel"
		return null;
	}

	public ReadenCSV read(int xLabelIndex, int yLabelIndex) throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(this.csvPath));

		String line = null;
		Scanner scanner = null;
		int index = 0, i = 0;
		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;

		ReadenCSV readenCSV = new ReadenCSV();

		while ((line = reader.readLine()) != null) {
			scanner = new Scanner(line);
			scanner.useDelimiter(",");
			// System.out.println("i::"+i);

			if (i == 0 && hasLabel(csvPath)) {
				while (scanner.hasNext() && index <= Math.max(xLabelIndex, yLabelIndex)) {
					String data = scanner.next();
					// System.out.println("index0::"+index);

					if (index == xLabelIndex) {
						// System.out.println("Enter in Xlabel at index ="+index);
						readenCSV.setxLabel(data);
						;
					}

					else if (index == yLabelIndex) {
						// System.out.println("Enter in Ylabel at index ="+index);
						readenCSV.setyLabel(data);
						;
					}
					index++;
				}
				index = 0;
			}

			while (scanner.hasNext() && index <= Math.max(xLabelIndex, yLabelIndex)) {
				String data = scanner.next();
				// System.out.println("index::"+index);

				if (index == xLabelIndex) {
					readenCSV.addX(Double.parseDouble(data));

					if (Double.parseDouble(data) >= xMax)
						xMax = Double.parseDouble(data);
					else if (Double.parseDouble(data) <= xMin)
						xMin = Double.parseDouble(data);
				}

				else if (index == yLabelIndex) {
					readenCSV.addY(Double.parseDouble(data));

					if (Double.parseDouble(data) >= yMax)
						yMax = Double.parseDouble(data);
					else if (Double.parseDouble(data) <= yMin)
						yMin = Double.parseDouble(data);
				}

				index++;
			}
			index = 0;
			i++;
		}

		readenCSV.setxMax(xMax);
		readenCSV.setxMin(xMin);
		readenCSV.setyMax(yMax);
		readenCSV.setyMin(yMin);

		scanner.close();
		reader.close();

		return readenCSV;
	}

	public static void main(String[] args) throws IOException {

		String csvFile = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\csv\\CSV_test.csv";
		String csvFile2 = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\csv\\test2.csv";

		// System.out.println(hasLabel(csvFile));
		// System.out.println(hasLabel(csvFile2));

		CSVReader tmp = new CSVReader(csvFile);
		ReadenCSV result = tmp.read(0, 1);

		System.out.println("\nFile 1 :\n\nXlabel::" + result.getxLabel() + " data::" + result.getXdata().toString()
				+ " (xMin,xMax)::(" + result.getxMin() + "," + result.getxMax() + ")");
		System.out.println("Ylabel::" + result.getyLabel() + " data::" + result.getYdata().toString()
				+ " (yMin,yMax)::(" + result.getyMin() + ", " + result.getyMax() + ")\n\n");

		CSVReader tmp2 = new CSVReader(csvFile2);
		result = tmp2.read(0, 1);

		System.out.println("\nFile 2 :\n\nXlabel::" + result.getxLabel() + " data::" + result.getXdata().toString()
				+ " (xMin,xMax)::(" + result.getxMin() + "," + result.getxMax() + ")");
		System.out.println("Ylabel::" + result.getyLabel() + " data::" + result.getYdata().toString()
				+ " (yMin,yMax)::(" + result.getyMin() + ", " + result.getyMax() + ")");

	}

}
