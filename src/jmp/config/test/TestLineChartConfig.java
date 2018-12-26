package jmp.config.test;

import java.awt.Color;
import java.io.IOException;

import jmp.config.config.Config;
import jmp.config.config.LineChartConfig;
import jmp.utils.MyColor;

public class TestLineChartConfig {

	
	public static void main(String[] args) throws IOException
	{
		
		final String path = "D:\\Documents\\ENSISA\\3A_IR\\S1\\Projet_Perronne\\chart\\bin\\jmp\\config\\DefaultConfig.myConfig";

		Config config = new LineChartConfig(path);
		config.readConfig(path);
		System.out.println(config.toString());
		
		//Test OK
		
	}
	
}
