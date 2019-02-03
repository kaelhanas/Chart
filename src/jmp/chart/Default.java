package jmp.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

import jmp.ui.utilities.JMSwingUtilities;

public class Default
{
	//Axis
	public static final int DEFAULT_TICK_RATIO = 10;
	public final static int MINOR_TICK_COUNT = 4;
	public final static double TICK_LENGTH = 8;
	public final static double MINOR_TICK_LENGTH = 4;
	public final static int SEPARATOR_SIZE = 4;
	
	public final static Color AXIS_COLOR = Color.BLACK;
	public final static Color TICK_COLOR = Color.BLACK;
	public final static Color MINOR_TICK_COLOR = Color.DARK_GRAY;
	public final static float AXIS_THIKNESS = 1;
	public final static float TICK_THIKNESS = 0.5f;
	public final static float MINOR_TICK_THIKNESS = 0.5f;
	public final static Font LABEL_FONT = JMSwingUtilities.DEFAULT_GRAPHICS.getFont();
	public final static Font TICK_LABEL_FONT = JMSwingUtilities.DEFAULT_GRAPHICS.getFont();
	public final static Color LABEL_COLOR = Color.BLACK;
	public final static Color TICK_LABEL_COLOR = Color.BLACK;
	
	//Chart
	public static final Color CHART_CHARTBACKGROUND_COLOR = Color.GRAY;
	public final static Color CHART_BACKGROUND_COLOR = Color.LIGHT_GRAY;
	public final static Color H_GRIDLINE_COLOR = Color.LIGHT_GRAY;
	public final static Color V_GRIDLINE_COLOR = H_GRIDLINE_COLOR;
	public final static float H_GRIDLINE_THICKNESS = 0.5f;
	public final static float V_GRIDLINE_THICKNESS = 0.5f;
	public final static double MARGINS[]= {50, 50, 50, 50}; //{Bottom,Left, Right,Top};
	public static final Color CHARTLINE_COLOR = Color.BLUE;
	public static final BasicStroke CHARTLINE_STROKE = new BasicStroke(0.5f);
	
	//Cursors
	public static final int CURSOR_SELECTION_SENSIBILITY = 5;
	public static final Color CURSOR_LABELCOLOR = Color.WHITE;
	public static final Font CURSOR_LABELFONT = JMSwingUtilities.DEFAULT_GRAPHICS.getFont();
	public static final Color CURSOR_MARK_COLOR = Color.WHITE;
	private static final float pattern[] = { 8, 5, 2, 5};
	public static final BasicStroke CURSOR_LINE_STROKE = new  BasicStroke(0.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10, pattern, 0);
	public static final Color CURSOR_LINE_COLOR = Color.WHITE;
	public static final int CURSOR_SIZE = 5;
}
