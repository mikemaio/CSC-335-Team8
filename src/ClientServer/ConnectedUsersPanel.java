package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ConnectedUsersPanel extends JPanel {
	private GUIRoot gr;
	private JButton usersCheckCuP = new JButton("CheckUsers");
	private JButton backCuP = new JButton("Back");
	private TextField userDisplayCuP = new TextField();
	public ConnectedUsersPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(3,1);
    	window.setLayout(windowLayout);


    	window.add(usersCheckCuP);
    	window.add(userDisplayCuP);
    	window.add(backCuP);
		this.add(window);
		
		usersCheckCuP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.clientOne.sendString("queryusers", null, null, null);
						String users = gr.clientOne.getUserQueryValue() + "";
						userDisplayCuP.setText("Users: " + users);
					}
				}
			);

		backCuP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(6);
					}
				}
			);

	}

}
