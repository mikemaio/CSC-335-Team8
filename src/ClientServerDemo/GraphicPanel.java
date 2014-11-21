package ClientServerDemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;


// -- need to extend JPanel so that we can override some of
//    the default methods -- JPanel inherits from AWT Container
//    (can hold Components) which inherits from AWT Component
//    (can be displayed on a screen)
public class GraphicPanel extends JPanel {

	private GraphicsFrame gf;
	
	private Timer animationTimer = null;	
	private boolean running = false;
	
	public Timer getAnimationTimer()
	{
		return animationTimer;
	}

	public GraphicPanel (GraphicsFrame _gf)
	{
		super();
		
		gf = _gf;

		this.setBackground(Color.gray);

		// -- The JPanel can have a mouse listener if desired
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		}
		);
	
		// -- Timer will generate an event every 1000mSec once it is started
		//    First parameter is the delay in mSec, second is the ActionListener
		animationTimer = new Timer(1000, 
				// -- ActionListener for the timer event
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("tic");
				}
			}
					
			);

	}
	
	
	// -- this override sets the desired size of the JPanel which is
	//    used by some layout managers -- default desired size is 0,0
	//    which is, in general, not good -- will pull from layout manager
	public Dimension getPreferredSize() 
	{
		return new Dimension(50, 50);
	}
	
	// -- this override is where all the painting should be done. 
	//    DO NOT call it directly. Rather, call repaint() and let the
	//    event handling system decide when to call it
	//    DO NOT put graphics function call elsewhere in the code, although
	//    legal, it's bad practice and could destroy the integrity of the
	//    display
	public void paint(Graphics g)
	{
		// -- the base class paintComponent(g) method ensures 
		//    the drawing area will be cleared properly. Do not
		//    modify any attributes of g prior to sending it to
		//    the base class
		super.paintComponent(g);
		
		// -- for legacy reasons the parameter comes in as type Graphics
		//    but it is really a Graphics2D object. Cast it up since the
		//    Graphics2D class is more capable
		Graphics2D g2d = (Graphics2D)g;

		// -- get the height and width of the JPanel drawing area
		int width = this.getWidth();
		int height = this.getHeight();
/*	
		// -- set the drawing color and draw some lines on the diagonals
		g2d.setColor(Color.blue);
		g2d.drawLine(0, 0, width - 1, height - 1);
		g2d.drawLine(width - 1, 0, 0, height - 1);
		
		// -- set the drawing color and draw a rectangle
		g2d.setColor(new Color(255, 255, 0));
		int centerW = width / 2;
		int centerH = height / 2;
		g2d.drawRect(centerW - 25, centerH - 25, 50, 50);
		
		// -- set the drawing color and draw a circle (oval)
		g2d.setColor(Color.green);
		g2d.drawOval(centerW - 50, centerH - 50, 100, 100);
		
		// -- set the drawing color and draw a rectangle with rounded edges
		//    the colors are specified as R, G, B 8 bits each
		g2d.setColor(new Color(255, 0, 255));
		g2d.drawRoundRect(centerW - 75, centerH - 75, 150, 150, 50, 50);
		
		// -- set the drawing color and draw a filled circle (oval)
		g2d.setColor(Color.cyan);
		g2d.fillOval(ballx - ballradius, bally - ballradius, 2 * ballradius, 2 * ballradius);
*/		
		
	}
	

}
