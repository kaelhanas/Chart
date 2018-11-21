package jmp.testImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

public class ImageView extends JPanel implements ComponentListener
{
	private BufferedImage image;

	public ImageView()
	{
		this.addComponentListener(this);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (this.image == null) this.updateImage();
		System.out.println("I paint");
		this.directDraw(this.image);
		this.bufferdImageSetRGB(this.image);
		this.rasterSetDataElt(this.image);
		g.drawImage(this.image, 0, 0, null);

	}

	
	protected void directDraw(BufferedImage img)
	{
		long time = System.currentTimeMillis();
		final int width = img.getWidth();
		final int height = img.getHeight();

		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setColor(Color.BLACK);
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				g.fillRect(x,y, 1, 1);

		g.fillRect(0, 0, width, height);
		
		System.out.println("test directDraw->"+(System.currentTimeMillis()-time));

	}
	protected void bufferdImageSetRGB(BufferedImage img)
	{
		long time = System.currentTimeMillis();

		final int width = img.getWidth();
		final int height = img.getHeight();
		final int color = Color.red.getRGB();
		
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				this.drawBufferedRect(img, x, y,1,1, color);
				//img.setRGB(x, y, color);

		
		System.out.println("test BufferedImage.setRgb->"+(System.currentTimeMillis()-time));
	}

	protected void drawBufferedRect(BufferedImage img, int x,int y,int w, int h,int color)
	{
		for (int rx = 0; rx < w; rx++)
			for (int ry = 0; ry < h; ry++)
				img.setRGB(rx+x, ry+y, color);
	}
	
	protected void rasterSetDataElt(BufferedImage img)
	{
		long time = System.currentTimeMillis();

		final int width = img.getWidth();
		final int height = img.getHeight();

	    final WritableRaster raster = img.getRaster();
	    int [] color = {0,0,255,0};

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				//raster.setPixel(x, y, color);
				this.drawRasterRect(raster, x, y, 1, 1, color);
		
		System.out.println("test Raster.setDataElement->"+(System.currentTimeMillis()-time));
	}

	protected void drawRasterRect(WritableRaster r, int x,int y,int w, int h,int[] color)
	{
		for (int rx = 0; rx < w; rx++)
			for (int ry = 0; ry < h; ry++)
				r.setPixel(rx+x, ry+y, color);
	}
	
	protected void updateImage()
	{
		final Dimension size = this.getSize();
		this.image = (BufferedImage) this.createImage(size.width, size.height);
		this.repaint();
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
		this.updateImage();
	}

	@Override
	public void componentShown(ComponentEvent e)
	{}

	@Override
	public Dimension getPreferredSize()
	{
		return this.getSize();
	}
}
