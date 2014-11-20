//package ClientServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

//import Test.GraphicsFrame;

public class ConnectPanel extends JPanel implements ActionListener{
	private GUIRoot gr;
	//buttons
	private JButton connectButton; 
	private TextField connectIP;
	
	public ConnectPanel (GUIRoot _gr)
	{
		super();
		
		gr = _gr;
		connectButton = new JButton("Connect");
		connectIP = new TextField(20);
		this.setBackground(Color.LIGHT_GRAY);		
		//add Parts
		JPanel window = new JPanel();
		
    	window.setLayout(new GridBagLayout());
    	window.setBorder(BorderFactory.createEmptyBorder(2,1, 0, 0));
		window.add(connectIP);
		window.add(connectButton);
		this.add(window);
		
		
		//listener for gui flow
		connectButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String address = connectIP.getText();
						
						if(address.equals("127.0.0.1")) {
							gr.refreshGUI(2);
							Client clientOne = new Client();
						}
						else {
							connectIP.setText("Wrong IP: " + address);
						}
					}
				}
			);
}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
