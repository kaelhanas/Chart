package jmp.chart.model.chartModel;

import java.awt.Color;

import jmp.chart.Default;

public class BarChartRenderingModel extends ChartRenderingModel {

	private Color[] rectColor;
	private Color lineColor;

	public BarChartRenderingModel() {
		this.rectColor = defaultColors();
		this.lineColor = Default.CHARTLINE_COLOR;
	}
	
	public Color[] defaultColors() {
		Color[] temp = new Color[] { Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.ORANGE };
		return temp;
	}
	
	public Color[] getRectColors() {
		return this.rectColor;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		if (this.lineColor.equals(lineColor)) return;
		this.lineColor = lineColor;
		this.modelChange();
	}

	public void setRectColor(Color[] rectColor) {
		if (this.rectColor.equals(rectColor)) return;
		this.rectColor = rectColor;
		this.modelChange();
	}
	
	
}
