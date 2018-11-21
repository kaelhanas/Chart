package jmp.ui.mvc;

import java.awt.Dimension;
import java.awt.Graphics2D;

public  class DefaultRenderer implements Renderer
{
	private View view;
	
	public DefaultRenderer(View view)
	{
		this.view = view;
	}
	
	public View getView()
	{
		return this.view;
	}
	
	public void setView(View view)
	{
		this.view = view;
	}

	public void renderView(Graphics2D g, Model modelChangedSource)
	{		
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}

	public Dimension getPreferredSize()
	{
		return this.view.getSize();
	}

	public Dimension getMaximumSize()
	{
		return this.getPreferredSize();
	}
}
