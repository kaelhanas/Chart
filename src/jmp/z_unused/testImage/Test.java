package jmp.z_unused.testImage;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Test extends JFrame
{
	private ImageView imageView;
	
	public void setup()
	{
		setTitle("Test Image");
		this.setupComponentsPane();

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

	private void setupComponentsPane()
	{		
		this.imageView = new ImageView();
		this.imageView.setSize(500,500);
		
		this.getContentPane().add(this.imageView, BorderLayout.CENTER);
	}
	
	public static void main(String[] args)
	{
		final Test app = new Test();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
