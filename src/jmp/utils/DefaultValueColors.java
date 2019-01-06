package jmp.utils;

import java.awt.Color;

public class DefaultValueColors {

	public Color[] defaultValueColor()
	{
	        Color colors[] = new Color[256];
	        int red, green, blue;
	        for (int i = 0; i < 256; i++)
	        {
	            red = i < 170 ? Math.max((i - 85) * 3, 0) : 255;
	            green = i < 170 ? Math.max((i - 85) * 3, 0) : (255 - i) * 3;
	            blue = i < 85 ? i * 3 : Math.max((170 - i) * 3, 0);
	            colors[i] = new Color(red, green, blue);
	        }
	        return colors;
	}
	
}
