package ClientServerDemo;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class ControlPanel extends JPanel {

	private GraphicsFrame gf;
	
	public ControlPanel (GraphicsFrame _gf)
	{
		gf = _gf;
		
		setLayout(new GridLayout(20, 1, 2, 2));

		JButton startButton = new JButton("Start");
		startButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gf.getGraphicPanel().getAnimationTimer().start();
				
					}
				}
			);
		
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gf.getGraphicPanel().getAnimationTimer().stop();
					}
				}
			);
		
		add(startButton);
		add(stopButton);
		
	}
	
	public Dimension getPreferredSize() 
	{
		return new Dimension(100, 500);
	}

}
