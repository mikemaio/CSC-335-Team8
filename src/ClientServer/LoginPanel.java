package ClientServer;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {

private GUIRoot gr;
//name undercase followed by initials of Class 
private JButton loginLP = new JButton("Login");; 
private JButton recoverLP = new JButton("Recover Password");
private JButton registerLP = new JButton("Register");
private JButton changepassLP = new JButton("Change Password");
private JButton disconnectLP = new JButton("Disconnect");
private TextField accountLP = new TextField("Enter Account...");
private TextField passwordLP = new TextField("Enter Password...");
private ConnectionThread db;
 

	
	
	public LoginPanel (GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);
		this.setBackground(Color.red);		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(7,1);
    	window.setLayout(windowLayout);


		window.add(accountLP);
		window.add(passwordLP);
		window.add(loginLP);
		window.add(registerLP);
		window.add(changepassLP);
		window.add(recoverLP);
		window.add(disconnectLP);
		
		this.add(window);
		
		loginLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(6);
						String txt = gr.clientOne.sendString("hello");
						System.out.println(txt);
					}
				}
			);
		recoverLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(3);
					}
				}
			);
		registerLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(4);
						String txt = gr.clientOne.sendString("register");
						System.out.println(txt);
					}
				}
			);
		changepassLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(5);
					}
				}
			);
		disconnectLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(1);
					}
				}
			);
		
		
	}
}
