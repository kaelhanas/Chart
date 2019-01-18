package jmp.config.config;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import jmp.chart.model.chartModel.Side;
import jmp.utils.MyColor;

public class MapChartConfig extends Config{

	private int chartHeight;
	private int chartWidth;
	
	
	public MapChartConfig(String configFilePath) throws IOException {
		super(configFilePath);
		readConfig(configFilePath);
		this.chartHeight = 0;
		this.chartWidth = 0;
	}

	@Override
	public void readConfig(String path) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line = null;
		Scanner scanner = null;
		
		while ((line = reader.readLine()) !=null)
		{
			// Doesn't Read Commentary line and empty line
			if(!line.startsWith("//") && !line.startsWith(" "))
			{
				line = line.replace(" ", "");

				scanner = new Scanner(line);
				scanner.useDelimiter("=");
				
				if(scanner.hasNext())
				{
					String dataType = scanner.next();
					
					if(scanner.hasNext()) {
						String data = scanner.next();
						dataTypeAnalysis(dataType, data);
					}
					
				}
			}
		}
		
		scanner.close();
		reader.close();
		
	}
	
	private void dataTypeAnalysis(String dataType, String data) throws IllegalArgumentException
	{
		//System.out.println("I'm in "+dataType+" Sir");
				switch(dataType){
				
					case "X_AXIS_MINORTICK_VISIBLE" : 
						this.setX_AXIS_MINORTICK_VISIBLE(Boolean.parseBoolean(data)); break;
					
					case "Y_AXIS_MINORTICK_VISIBLE" : 
						this.setY_AXIS_MINORTICK_VISIBLE(Boolean.parseBoolean(data)); break;
					
					case "HORIZONTAL_GRIDLINE_VISIBLE" : 
						this.setHORIZONTAL_GRIDLINE_VISIBLE(Boolean.parseBoolean(data)); break;
					
					case "VERTICAL_GRIDLINE_VISIBLE" : 
						this.setVERTICAL_GRIDLINE_VISIBLE(Boolean.parseBoolean(data)); break;
						
					case "MARGIN_SIDE" : 
						switch(data.toLowerCase()){
							case "left" :
								this.setMARGIN_SIDE(Side.Left); break;
							case "right" :
								this.setMARGIN_SIDE(Side.Right); break;
							case "top" :
								this.setMARGIN_SIDE(Side.Top); break;
							case "bottom" :
								this.setMARGIN_SIDE(Side.Bottom); break;
							default: throw new IllegalArgumentException();
						} break;
					
					case "MARGIN_VALUE" : 
						this.setMARGIN_VALUE(Double.parseDouble(data));; break;
				
					
					case "CHART_WIDTH" :
						this.chartWidth = Integer.parseInt(data);
						this.setVIEW_SIZE(new Dimension(this.chartWidth, this.chartHeight));; break;
					
					case "CHART_HEIGHT" :
						this.chartHeight = Integer.parseInt(data);
						this.setVIEW_SIZE(new Dimension(this.chartWidth, this.chartHeight));; break;
					
					case "X_AXIS_LABEL" : 
						this.setX_AXIS_LABEL(data);; break;
					
					case "Y_AXIS_LABEL" : 
						this.setY_AXIS_LABEL(data);; break;
						
					case "X_AXIS_MINORTICK_COLOR" : 
						MyColor xColor = new MyColor();
						//System.out.println(xColor.getColor(data));
						this.setX_AXIS_MINORTICK_COLOR(xColor.getColor(data)); break;
					
					case "Y_AXIS_MINORTICK_COLOR" : 
						MyColor yColor = new MyColor();
						//System.out.println(yColor.getColor(data));
						this.setY_AXIS_MINORTICK_COLOR(yColor.getColor(data)); break;
						
					//Map configuration
					case "DATA_SIZE" :
						break;
					case "SPECTRUM_SIZE" :
						break;
					case "SPECTRUM_STEP" :
						break;
					case "BACKGROUND_COLOR" :
							break;
						
					//Allow to inform the user that his config file contains an error
					default : throw new IllegalArgumentException();
	
				}
	}

	@Override
	public ConfigType getConfigType() {
		return ConfigType.MapChartCOnfig;
	}

}
