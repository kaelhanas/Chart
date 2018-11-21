package jmp.chart.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.util.List;

import jmp.chart.model.AxisModel;
import jmp.chart.model.AxisRenderingModel;
import jmp.chart.model.ChartModel;
import jmp.chart.model.ChartRenderingModel;
import jmp.chart.model.CursorsModel;
import jmp.chart.model.CursorsRenderingModel;
import jmp.chart.model.Side;
import jmp.chart.view.ChartView;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.Model;
import jmp.ui.mvc.View;

public abstract class ChartRenderer extends DefaultRenderer implements ComponentListener
{
	private static final int CURVES = 0;
	private static final int CURVES_AND_CURSORS = 1;

	private AxisRenderer xaRenderer;
	private AxisRenderer yaRenderer;
	private CursorsRenderer cursorsRenderer;
	
	private Rectangle2D.Double displayRect;
	private Image offscreenImage[];
	RenderingHints renderingHints;

	public ChartRenderer(View view)
	{
		super(view);
		this.xaRenderer = new AxisRenderer(this, ChartView.X_AXIS_MODEL, ChartView.X_AXIS_RENDERING_MODEL);
		this.yaRenderer = new AxisRenderer(this, ChartView.Y_AXIS_MODEL, ChartView.Y_AXIS_RENDERING_MODEL);
		this.cursorsRenderer = new CursorsRenderer(this);
		this.displayRect = new Rectangle2D.Double();
		this.renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		this.offscreenImage = new Image[2];
	}

	protected ChartView view()
	{
		return (ChartView) this.getView();
	}

	public void renderView(Graphics2D g, Model modelChangedSource)
	{		

		if (this.offscreenImage[CURVES]== null)
		{
			final Dimension size = this.getView().getSize();
			this.offscreenImage[CURVES] = this.view().createImage(size.width, size.height);
			this.offscreenImage[CURVES_AND_CURSORS] = this.view().createImage(size.width, size.height);
		}
		
		this.renderChartOffscreen(this.offscreenImage[CURVES], modelChangedSource);
		this.renderCursorsOffscreen(this.offscreenImage[CURVES_AND_CURSORS]);
		
		g.drawImage(this.offscreenImage[CURVES_AND_CURSORS], 0, 0, null);		
	}
    
	protected  void renderChartOffscreen(Image image, Model modelChangedSource)
	{
		if (modelChangedSource == this.view().cursorsModel()) return;
	
		final AxisModel xam = this.view().xAxisModel();
		final AxisRenderingModel xarm = this.view().xAxisRenderingModel();
		final AxisModel yam = this.view().yAxisModel();
		final AxisRenderingModel yarm = this.view().yAxisRenderingModel();
		final ChartModel cm = this.view().chartModel();
		final ChartRenderingModel crm = this.view().chartRenderingModel();
		
		Graphics2D g = (Graphics2D)image.getGraphics();
	
		this.renderBackGround(g, this.view().chartRenderingModel());
		
		g.setRenderingHints(this.renderingHints);
		
		this.xaRenderer.renderView(g, xam, xarm, this.displayRect);
		this.yaRenderer.renderView(g, yam, yarm, this.displayRect);

		this.renderChartBackGround(g, cm, crm, this.displayRect);
		this.renderGrids(g, cm, crm, this.displayRect);
		g.setClip(displayRect);
		this.renderChart(image, g, cm, crm, this.displayRect);
		
		g.dispose();
	}

	protected void renderCursorsOffscreen(Image image)
	{
		final AxisModel yam = this.view().yAxisModel();
		final AxisModel xam = this.view().xAxisModel();
		final CursorsModel cursorsm = this.view().cursorsModel();
		final CursorsRenderingModel cursorsrm = this.view().cursorsRenderingModel();
		
		Graphics2D g = (Graphics2D)image.getGraphics();

		g.drawImage(this.offscreenImage[CURVES], 0, 0,null);
		g.setRenderingHints(this.renderingHints);
		g.setClip(displayRect);
		this.cursorsRenderer.renderCursors(g, cursorsm, cursorsrm, xam, yam, displayRect);
		g.dispose();
	}

	protected void renderBackGround(Graphics2D g, ChartRenderingModel crm)
	{
		final Dimension size = this.getView().getSize();
		g.setColor(crm.getBackGroundColor());
		g.fillRect(0,0, size.width, size.height);
	}

	protected abstract void renderChart(Image image, Graphics2D g, ChartModel cm, ChartRenderingModel crm, Rectangle2D.Double displayRect);

	protected void renderChartBackGround(Graphics2D g, ChartModel cm, ChartRenderingModel crm, Rectangle2D.Double displayRect)
	{
		g.setColor(crm.getChartBackGroundColor());
		g.fillRect((int) displayRect.x, (int) displayRect.y, (int) displayRect.width, (int) displayRect.height);
	}

	protected void renderGrids(Graphics2D g, ChartModel cm, ChartRenderingModel crm, Rectangle2D.Double displayRect)
	{
		if (cm.isVerticalGridLineVisible())
		{
			g.setColor(crm.getVerticalGridLineColor());
			g.setStroke(crm.getVerticalGridLineThickness());

			List<Integer> ticksPos = this.xaRenderer.getTicksPositions();
			for (Integer tickPos : ticksPos)
			{
				g.drawLine(tickPos, this.y(0), tickPos, this.y(displayRect.height));
			}
		}

		if (cm.isHoriziontalGridLineVisible())
		{
			g.setColor(crm.getHoriziontalGridLineColor());
			g.setStroke(crm.getHoriziontalGridLineThickness());

			List<Integer> ticksPos = this.yaRenderer.getTicksPositions();
			for (Integer tickPos : ticksPos)
			{
				g.drawLine(this.x(0), tickPos, this.x(displayRect.width), tickPos);
			}
		}
	}

	protected void updateDisplayRectangle(Rectangle2D.Double rect)
	{
		ChartRenderingModel crm = this.view().chartRenderingModel();
		Dimension size = this.getView().getSize();
		rect.x = crm.getMargin(Side.Left);
		rect.y = crm.getMargin(Side.Top);
		rect.width = size.width - crm.getMargin(Side.Left) - crm.getMargin(Side.Right);
		rect.height = size.height - crm.getMargin(Side.Top) - crm.getMargin(Side.Bottom);

		this.view().xAxisModel().setDisplayLength(rect.width);
		this.view().yAxisModel().setDisplayLength(rect.height);

		this.offscreenImage[CURVES] = this.view().createImage(size.width, size.height);
		this.offscreenImage[CURVES_AND_CURSORS] = this.view().createImage(size.width, size.height);
	}

	public Rectangle2D.Double displayRect()
	{
		return this.displayRect;
	}

	public int x(double value)
	{
		return (int) (this.displayRect.x + value);
	}

	public int y(double value)
	{
		return (int) (this.displayRect.y + this.displayRect.height - value);
	}
	
	public double _x(int value)
	{
		return value - this.displayRect.x;
	}

	public double _y(int value)
	{
		return  this.displayRect.y + this.displayRect.height - value;
	}
	
	@Override
	public void componentHidden(ComponentEvent e)
	{}

	@Override
	public void componentMoved(ComponentEvent e)
	{}

	@Override
	public void componentResized(ComponentEvent e)
	{
		this.updateDisplayRectangle(this.displayRect);
	}

	@Override
	public void componentShown(ComponentEvent e)
	{}

}
