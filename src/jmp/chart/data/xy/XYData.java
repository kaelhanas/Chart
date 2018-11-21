package jmp.chart.data.xy;

public interface XYData
{
	int xSize();
	double x(int i);
	double y(int i);
	double xMin();
	double yMin();
	double xMax();
	double yMax();
}
