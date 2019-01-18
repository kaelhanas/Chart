package jmp.z_unused;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Test extends JPanel{

    private static final long    serialVersionUID    = -1570645570118871214L;

    public Test(){ }
    
    
    public void paintComponent(Graphics g){
        drawImage1(this, g, new Rectangle(0, 0, getWidth(), getHeight()));
        
        drawImage2(this, g, new Rectangle(10, 20, 100, 100), .7f);
        drawImage2(this, g, new Rectangle(50, 50, 100, 100), .2f);
        drawImage2(this, g, new Rectangle(70, 100, 100, 100), .4f);
    }
    
    public void drawImage1(Component c, Graphics g, Rectangle bounds ){
        //tu creer une image transparente
        BufferedImage bi = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        //tu récupere le graphique de l'image
        Graphics2D g2 = bi.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.RED);
        Shape circle1 = new Ellipse2D.Double(0, 0, 200, 200);
        Shape circle2 = new Ellipse2D.Double(50, 50, 200, 200);
        g2.fill(circle1);
        g2.setColor(Color.YELLOW);
        g2.clip(circle1);
        g2.fill(circle2);
         //tu dessine l'image  
        g.drawImage(bi, bounds.x, bounds.y, c);
        //tu relache les ressources du Graphics2D
        g2.dispose();
    }
    
    public void drawImage2(Component c, Graphics g, Rectangle bounds, float alpha ){
        //tu creer une image transparente
        BufferedImage bi = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
        //tu récupere le graphique de l'image
        Graphics2D g2 = bi.createGraphics();

        //juste pour le fun tu ajoute une petite transparence sur le nouveau dessin 0.0f <= alpha <= 1.0f (1 == pas de transparence)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        //ici tu commence à dessiner ce que tu veux
        g2.setColor(Color.BLACK);
        Shape rect = new Rectangle2D.Double(0, 0, bounds.width - 1, bounds.height - 1);
        g2.fill(rect);
        
        //tu dessine l'image  
        g.drawImage(bi, bounds.x, bounds.y, c);
        //tu relache les ressources du Graphics2D
        g2.dispose();
    }
    
    
    public static void main(String [] args){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(3);
        f.setSize(300, 300);
        f.setLocationRelativeTo(null);
        f.setContentPane(new Test());
        f.setVisible(true);
    }

}