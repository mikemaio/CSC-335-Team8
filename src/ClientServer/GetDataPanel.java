//package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GetDataPanel extends JPanel {

	private GUIRoot gr;
	private JButton queryDataBaseGDP = new JButton("Retrieve Data");
	private JButton backGDP = new JButton("Back");
	private TextField queryDataEntryGDP = new TextField("Enter Desired...");
	private TextField displayDataGDP = new TextField();
	public GetDataPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);
		
		
		this.setBackground(Color.red);		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(4,1);
    	window.setLayout(windowLayout);

    	window.add(queryDataEntryGDP);
    	window.add(queryDataBaseGDP);
    	window.add(displayDataGDP);
    	window.add(backGDP);
		this.add(window);
		
		queryDataBaseGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				}
			);
		displayDataGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				}
			);
		
		backGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(6);
					}
				}
			);

	}

}
