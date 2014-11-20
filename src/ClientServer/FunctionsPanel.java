//package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FunctionsPanel extends JPanel {
	private GUIRoot gr;
	private JButton userQueryFP = new JButton("UserQuery");
	private JButton dataPullFP = new JButton("Pull Data");
	private JButton logoutFP = new JButton("Logout");

	public FunctionsPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);	
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(3,1);
    	window.setLayout(windowLayout);


    	window.add(userQueryFP);
    	window.add(dataPullFP);
    	window.add(logoutFP);
    	
		this.add(window);
		
		dataPullFP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(8);
					}
				}
			);
		userQueryFP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(7);
					}
				}
			);
		
		logoutFP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(2);
					}
				}
			);
	}
}
