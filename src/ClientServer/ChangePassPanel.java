package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ChangePassPanel extends JPanel {
	private GUIRoot gr;
	private JButton changeChP = new JButton("Change Password");
	private JButton backChP = new JButton("Back");
	private TextField accountChP = new TextField("Enter Account Name...");
	private TextField oldPassChP = new TextField("Enter Old Password...");
	private TextField newPassChP= new TextField("Enter New Password...");
	private TextField confirmNewPassChP = new TextField("Confirm Password...");

	public ChangePassPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);	
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(6,1);
    	window.setLayout(windowLayout);


		window.add(accountChP);
		window.add(oldPassChP);
		window.add(newPassChP);
		window.add(confirmNewPassChP);
		window.add(changeChP);
		window.add(backChP);
		this.add(window);
		
		changeChP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				}
			);
		
		
		backChP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(2);
					}
				}
			);
		

	}

}
