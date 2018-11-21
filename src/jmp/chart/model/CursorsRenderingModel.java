package jmp.chart.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import jmp.ui.mvc.DefaultModel;


public class CursorsRenderingModel extends DefaultModel
{
	public Map<CursorModel, CursorRenderingModel> cursors;
	public CursorsRenderingModel()
	{
		super();
		this.cursors = new HashMap<CursorModel, CursorRenderingModel>();
	}

	public void addCursor(CursorModel c, CursorRenderingModel crm)
	{
		this.cursors.put(c, crm);
	}
	
	public void removeCursor(CursorModel c)
	{
		this.cursors.remove(c);
	}
	
	public CursorRenderingModel getModel(CursorModel c)
	{
		CursorRenderingModel crm = this.cursors.get(c);
		if (crm==null) throw new NoSuchElementException();
		return crm;
	}
	
	public Color getLabelColor(CursorModel c)
	{
		return this.getModel(c).getLabelColor();
	}

	public void setLabelColor(CursorModel c, Color labelColor)
	{
		CursorRenderingModel crm = this.getModel(c);
		if (crm.getLabelColor().equals(labelColor)) return;
		crm.setLabelColor(labelColor);
		this.modelChange();
	}

	public Font getLabelFont(CursorModel c)
	{
		return this.getModel(c).getLabelFont();
	}

	public void setLabelFont(CursorModel c, Font labelFont)
	{
		CursorRenderingModel crm = this.getModel(c);
		if (crm.getLabelFont().equals(labelFont)) return;
		crm.setLabelFont(labelFont);
		this.modelChange();
	}

	public Color getMarkColor(CursorModel c)
	{
		return this.getModel(c).getMarkColor();
	}

	public void setMarkColor(CursorModel c, Color cursorColor)
	{
		CursorRenderingModel crm = this.getModel(c);
		if (crm.getLabelColor().equals(cursorColor)) return;
		crm.setMarkColor(cursorColor);
		this.modelChange();
	}
	
	public BasicStroke getLineStroke(CursorModel c)
	{
		return this.getModel(c).getLineStroke();
	}

	public void setLineStroke(CursorModel c, BasicStroke lineStroke)
	{
		CursorRenderingModel crm = this.getModel(c);
		if (crm.getLineStroke().equals(lineStroke)) return;
		crm.setLineStroke(lineStroke);
		this.modelChange();
	}

	public Color getLineColor(CursorModel c)
	{
		return this.getModel(c).getLineColor();
	}

	public void setLineColor(CursorModel c, Color lineColor)
	{
		CursorRenderingModel crm = this.getModel(c);
		if (crm.getLineColor().equals(lineColor)) return;
		crm.setLineColor(lineColor);
		this.modelChange();
	}
	
}
