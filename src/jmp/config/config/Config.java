package jmp.config.config;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;

import jmp.chart.model.Side;

public abstract class Config {

	private String configFilePath;
	
	private boolean X_AXIS_MINORTICK_VISIBLE;
	private boolean Y_AXIS_MINORTICK_VISIBLE;
	private boolean HORIZONTAL_GRIDLINE_VISIBLE;
	private boolean VERTICAL_GRIDLINE_VISIBLE;

	private Side MARGIN_SIDE;
	private double MARGIN_VALUE;

	private Color X_AXIS_MINORTICK_COLOR;
	private Color Y_AXIS_MINORTICK_COLOR;

	private String X_AXIS_LABEL;
	private String Y_AXIS_LABEL;

	private Dimension VIEW_SIZE;

	public Config(String configFilePath) 
	{
		this.configFilePath = configFilePath;
	}

	public abstract void readConfig(String path) throws IOException;

	public String configFilePath()
	{
		return configFilePath;
	}
	
	public boolean xAxisMinorTickVisible() {
		return X_AXIS_MINORTICK_VISIBLE;
	}

	public boolean yAxisMinorTickVisible() {
		return Y_AXIS_MINORTICK_VISIBLE;
	}

	public boolean horizontalGridLineVisible() {
		return HORIZONTAL_GRIDLINE_VISIBLE;
	}

	public boolean verticalGridLineTickVisible() {
		return VERTICAL_GRIDLINE_VISIBLE;
	}

	public Side marginSide() {
		return MARGIN_SIDE;
	}

	public double marginValue() {
		return MARGIN_VALUE;
	}

	public Color xAxisMinorTickColor() {
		return X_AXIS_MINORTICK_COLOR;
	}

	public Color yAxisMinorTickColor() {
		return Y_AXIS_MINORTICK_COLOR;
	}

	public String xAxisLabel() {
		return X_AXIS_LABEL;
	}

	public String yAxisLabel() {
		return Y_AXIS_LABEL;
	}

	public Dimension viewSize() {
		return VIEW_SIZE;
	}

	protected void setX_AXIS_MINORTICK_VISIBLE(boolean x_AXIS_MINORTICK_VISIBLE) {
		X_AXIS_MINORTICK_VISIBLE = x_AXIS_MINORTICK_VISIBLE;
	}

	protected void setY_AXIS_MINORTICK_VISIBLE(boolean y_AXIS_MINORTICK_VISIBLE) {
		Y_AXIS_MINORTICK_VISIBLE = y_AXIS_MINORTICK_VISIBLE;
	}

	protected void setHORIZONTAL_GRIDLINE_VISIBLE(boolean hORIZONTAL_GRIDLINE_VISIBLE) {
		HORIZONTAL_GRIDLINE_VISIBLE = hORIZONTAL_GRIDLINE_VISIBLE;
	}

	protected void setVERTICAL_GRIDLINE_VISIBLE(boolean vERTICAL_GRIDLINE_VISIBLE) {
		VERTICAL_GRIDLINE_VISIBLE = vERTICAL_GRIDLINE_VISIBLE;
	}

	protected void setMARGIN_SIDE(Side mARGIN_SIDE) {
		MARGIN_SIDE = mARGIN_SIDE;
	}

	protected void setMARGIN_VALUE(double mARGIN_VALUE) {
		MARGIN_VALUE = mARGIN_VALUE;
	}

	protected void setX_AXIS_MINORTICK_COLOR(Color x_AXIS_MINORTICK_COLOR) {
		X_AXIS_MINORTICK_COLOR = x_AXIS_MINORTICK_COLOR;
	}

	protected void setY_AXIS_MINORTICK_COLOR(Color y_AXIS_MINORTICK_COLOR) {
		Y_AXIS_MINORTICK_COLOR = y_AXIS_MINORTICK_COLOR;
	}

	protected void setX_AXIS_LABEL(String x_AXIS_LABEL) {
		X_AXIS_LABEL = x_AXIS_LABEL;
	}

	protected void setY_AXIS_LABEL(String y_AXIS_LABEL) {
		Y_AXIS_LABEL = y_AXIS_LABEL;
	}

	protected void setVIEW_SIZE(Dimension vIEW_SIZE) {
		VIEW_SIZE = vIEW_SIZE;
	}
	
	public String toString()
	{
		return "[X_AXIS_MINORTICK_VISIBLE::" +this.xAxisMinorTickVisible()+
				"\nY_AXIS_MINORTICK_VISIBLE::" +this.yAxisMinorTickVisible()+
				"\nHORIZONTAL_GRIDLINE_VISIBLE::" +this.horizontalGridLineVisible()+ 
				"\nVERTICAL_GRIDLINE_VISIBLE::" + this.verticalGridLineTickVisible()+
				"\nMARGIN_SIDE::" +this.marginSide().toString()+ 
				"\nMARGIN_VALUE::" +this.marginValue()+ 
				"\nX_AXIS_MINORTICK_COLOR::" +this.xAxisMinorTickColor()+ 
				"\nY_AXIS_MINORTICK_COLOR::" +this.yAxisMinorTickColor()+ 
				"\nX_AXIS_LABEL::" +this.xAxisLabel()+ 
				"\nY_AXIS_LABEL::" +this.yAxisLabel()+ 
				"\nVIEW_SIZE::"+this.viewSize().toString()+"]";
	}

}
