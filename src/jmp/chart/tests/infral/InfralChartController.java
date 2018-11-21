package jmp.chart.tests.infral;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import jmp.chart.Default;
import jmp.chart.data.xy.XYData;
import jmp.chart.model.AxisModel;
import jmp.chart.model.CursorModel;
import jmp.chart.model.CursorRenderingModel;
import jmp.chart.model.CursorsModel;
import jmp.chart.renderer.ChartRenderer;
import jmp.chart.view.ChartView;
import jmp.ui.mvc.Controller;

public class InfralChartController extends Controller
{
	private DragContext dragContext;
	private CursorModel cursorSelected;
	private Rectangle cursorSelection;

	public InfralChartController()
	{
		super(null);
		this.dragContext = new DragContext();
		this.cursorSelection = new Rectangle(Default.CURSOR_SELECTION_SENSIBILITY, Default.CURSOR_SELECTION_SENSIBILITY);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		this.dragContext.lastEventLocation(e);
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			this.cursorSelected = this.getCursor(e);
			if (e.isControlDown())
				this.removeCursor(e, this.cursorSelected);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.dragContext.dxdy(e);
		this.dragContext.lastEventLocation(e);
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			this.cursorSelected = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			if (!e.isControlDown())
				this.insertCursor(e);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		AxisModel xam = ((ChartView) this.view()).xAxisModel();
		AxisModel yam = ((ChartView) this.view()).yAxisModel();

		this.dragContext.dxdy(e);

		if (e.getButton() == MouseEvent.BUTTON3)
		{
			CursorsModel cursorsm = ((ChartView) this.view()).cursorsModel();
			this.shiftCursor(e, this.cursorSelected, cursorsm, xam);
			return;
		}

		if (!e.isShiftDown() && !e.isMetaDown()) this.shiftXChart(e, xam);
		if (e.isShiftDown() && !e.isMetaDown()) this.shiftYChart(e, yam);
		if (!e.isShiftDown() && e.isMetaDown()) this.zoomXChart(e, xam);
		if (e.isShiftDown() && e.isMetaDown()) this.zoomYChart(e, yam);
	}
	
	protected void insertCursor(MouseEvent e)
	{
		AxisModel xam = ((ChartView) this.view()).xAxisModel();

		XYData data = ((ChartView) this.view()).chartModel().getData();
		ChartRenderer chartRenderer = (ChartRenderer) this.view().renderer();

		double x = xam.getValueForDisplay(chartRenderer._x(e.getX()));

		for(int sample=0; sample < data.xSize(); sample++)
		{
			double value = data.x(sample);
			if (value>=x)
			{
				//System.out.println(sample);
				System.out.println("(x: "+data.x(sample)+" , y: "+data.y(sample)+")");
				((ChartView) this.view()).addCursor(new CursorModel("", sample, value, data.y(sample)), new CursorRenderingModel());
				break;
			}
		}	
	}
	
	private void removeCursor(MouseEvent e, CursorModel cursor)
	{
		if (cursor == null) return;
		((ChartView) this.view()).removeCursor(cursor);
		
	}

	protected void translateCursorSelection(double x, double y)
	{
		double dx = x - this.cursorSelection.width / 2;
		double dy = y - this.cursorSelection.height / 2;
		this.cursorSelection.x = (int) dx;
		this.cursorSelection.y = (int) dy;
	}

	public CursorModel getCursor(MouseEvent e)
	{
		CursorsModel cursorsm = ((ChartView) this.view()).cursorsModel();
		AxisModel xam = ((ChartView) this.view()).xAxisModel();
		AxisModel yam = ((ChartView) this.view()).yAxisModel();
		ChartRenderer chartRenderer = (ChartRenderer) this.view().renderer();

		this.translateCursorSelection(e.getX(), e.getY());
		for (Iterator<CursorModel>it = cursorsm.iterator(); it.hasNext();)
		{
			CursorModel cursor = it.next();

			double x = chartRenderer.x(xam.getDisplayPosition(cursor.getX()));
			double y = chartRenderer.y(yam.getDisplayPosition(cursor.getY()));

			if (this.cursorSelection.contains(x, y))
				return cursor;
		}

		return null;
	}
	
	protected void shiftCursor(MouseEvent e, CursorModel cursor, CursorsModel cm, AxisModel xam)
	{
		if (cursor == null) return;
		
		XYData data = ((ChartView) this.view()).chartModel().getData();
		ChartRenderer chartRenderer = (ChartRenderer) this.view().renderer();

		double x = xam.getValueForDisplay(chartRenderer._x(e.getX()));
		int sample =  cursor.getSample();

		if (this.dragContext.dx > 0)
		{
			for (; sample < data.xSize()-1; sample++)
			{
				if (data.x(sample) >= x) break;
			}
		}
		if (this.dragContext.dx < 0)
		{
			for (; sample >0; sample--)
			{
				if (data.x(sample) <= x) break;
			}
		}

		cm.moveCursorTo(cursor, sample, data.x(sample), data.y(sample));
		
		this.dragContext.lastEventLocation(e);
	}

	protected void shiftXChart(MouseEvent e, AxisModel xam)
	{
		final double dx = this.computeMinorTicksDelta(this.dragContext.dx, xam);
		if (dx != 0)
		{
			xam.setLowerBound(xam.getLowerBound() - dx);
			xam.setUpperBound(xam.getUpperBound() - dx);
			this.dragContext.lastEventLocation(e);
		}
	}

	protected void shiftYChart(MouseEvent e, AxisModel yam)
	{
		final double dy = this.computeMinorTicksDelta(this.dragContext.dy, yam);
		if (dy != 0)
		{
			yam.setLowerBound(yam.getLowerBound() + dy);
			yam.setUpperBound(yam.getUpperBound() + dy);
			this.dragContext.lastEventLocation(e);
		}
	}

	//compute delta in relation to minorticks
	protected double computeMinorTicksDelta(double mouseDelta, AxisModel am)
	{
		final double minorTickValue = am.getTickUnit() / am.getMinorTickCount();
		double delta = mouseDelta / am.getScale();

		return (Math.abs(delta) >= minorTickValue) ? delta = Math.round(delta / minorTickValue) * minorTickValue : 0;
	}

	private void zoomXChart(MouseEvent e, AxisModel xam)
	{
		final double dx = this.computeMinorTicksDelta(this.dragContext.dx, xam);
		if (dx != 0)
		{
			xam.setLowerBound(xam.getLowerBound() + dx);
			xam.setUpperBound(xam.getUpperBound() - dx);
			this.dragContext.lastEventLocation(e);
		}
		/*
		 * double xalb = xam.getLowerBound() + (this.dragContext.dx /
		 * xam.getScale()); double xaub = xam.getUpperBound() -
		 * (this.dragContext.dx / xam.getScale());
		 * 
		 * xam.setLowerBound(xalb); xam.setUpperBound(xaub);
		 * this.dragContext.lastEventLocation(e);
		 */
	}

	private void zoomYChart(MouseEvent e, AxisModel yam)
	{
		final double dy = this.computeMinorTicksDelta(this.dragContext.dy, yam);
		if (dy != 0)
		{
			yam.setLowerBound(yam.getLowerBound() - dy);
			yam.setUpperBound(yam.getUpperBound() + dy);
			this.dragContext.lastEventLocation(e);
		}
	}

	private class DragContext
	{

		protected double lastx, lasty;
		protected double dx, dy;

		public void lastEventLocation(MouseEvent e)
		{
			this.lastx = e.getX();
			this.lasty = e.getY();
		}

		public void dxdy(MouseEvent e)
		{
			this.dx = e.getX() - lastx;
			this.dy = e.getY() - lasty;
		}
	}

}
