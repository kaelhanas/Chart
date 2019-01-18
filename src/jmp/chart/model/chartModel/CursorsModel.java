package jmp.chart.model.chartModel;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import jmp.chart.Default;
import jmp.ui.mvc.DefaultModel;

public class CursorsModel extends DefaultModel
{
	private Set<CursorModel> cursors;
	private Rectangle2D.Double selection;

	public CursorsModel()
	{
		this.cursors = new HashSet<CursorModel>();
		this.selection = new Rectangle2D.Double(0, 0, Default.CURSOR_SELECTION_SENSIBILITY, Default.CURSOR_SELECTION_SENSIBILITY);
	}

	public void addCursor(CursorModel c)
	{
		this.cursors.add(c);
		this.modelChange();
	}

	public void removeCursor(CursorModel c)
	{
		this.cursors.remove(c);
		this.modelChange();
	}

	protected void translateSelection(double x, double y)
	{
		double dx = x - this.selection.width / 2;
		double dy = y - this.selection.height / 2;
		this.selection.x = dx;
		this.selection.y = dy;
	}

	public CursorModel getCursor(double x, double y)
	{
		this.translateSelection(x, y);
		System.out.println(this.selection);
		for (CursorModel cursor : this.cursors)
		{
			System.out.println(cursor.getX()+"->"+cursor.getY());
			if (this.selection.contains(cursor.getX(), cursor.getY()))
				return cursor;
		}
		return null;
	}

	public void moveCursorTo(CursorModel cursor, double x, double y)
	{
		cursor.update(x, y);
		this.modelChange();
	}

	public void moveCursorTo(CursorModel cursor, int sample, double x, double y)
	{
		cursor.update(sample, x, y);
		this.modelChange();
	}

	public boolean isLabelVisible(CursorModel c)
	{
		return c.isLabelVisible();
	}

	public void setLabelVisible(CursorModel c, boolean labelVisible)
	{
		if (c.isLabelVisible() == labelVisible) return;
		c.setLabelVisible(labelVisible);
		this.modelChange();
	}

	public boolean isxLineVisible(CursorModel c)
	{
		return c.isxLineVisible();
	}

	public void setxLineVisible(CursorModel c, boolean xLineVisible)
	{
		if (c.isxLineVisible() == xLineVisible) return;
		c.setLabelVisible(xLineVisible);
		this.modelChange();
	}

	public boolean isyLineVisible(CursorModel c)
	{
		return c.isyLineVisible();
	}

	public void setyLineVisible(CursorModel c, boolean yLineVisible)
	{
		if (c.isyLineVisible() == yLineVisible) return;
		c.setLabelVisible(yLineVisible);
		this.modelChange();
	}

	public boolean isMarkVisible(CursorModel c)
	{
		return c.isMarkVisible();
	}

	public void setMarkVisible(CursorModel c, boolean markVisible)
	{
		if (c.isMarkVisible() == markVisible) return;
		c.setLabelVisible(markVisible);
		this.modelChange();
	}

	public Iterator<CursorModel> iterator()
	{
		return this.cursors.iterator();
	}

}
