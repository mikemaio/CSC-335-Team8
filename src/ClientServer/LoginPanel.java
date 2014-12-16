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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class LoginPanel extends JPanel {

private GUIRoot gr;
private ConnectionThread ct;
//name undercase followed by initials of Class 
private JButton loginLP = new JButton("Login");; 
private JButton recoverLP = new JButton("Recover Password");
private JButton registerLP = new JButton("Register");
private JButton changepassLP = new JButton("Change Password");
private JButton disconnectLP = new JButton("Disconnect");
private TextField accountLP = new TextField();
//private  TextField passwordLP = new TextField();
private JPasswordField passwordLP = new JPasswordField();
private  JLabel accountLabelLP = new JLabel("Account : ");
private  JLabel passwordLabelLP = new JLabel("Password : ");


	
	


	public LoginPanel (GUIRoot _gr)
	{
		super();
		accountLP.setEditable(true);
		passwordLP.setEditable(true);

		gr = _gr;

		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(9,1);
    	window.setLayout(windowLayout);

    	window.add(accountLabelLP);
		window.add(accountLP);
    	window.add(passwordLabelLP);
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
						//gr.refreshGUI(6);
						String account = accountLP.getText();
						char[] passwordarray = passwordLP.getPassword();
						String password = new String(passwordarray);
						gr.clientOne.sendString("loginprocedure",account,password,null);
						String delims = "[,]";
						String recievedData[] = gr.clientOne.getflowValues().split(delims);
						if(accountLP.getText().equals(""))
						{
							JOptionPane.showMessageDialog(loginLP, "Please enter an account name.");
						}
						if(recievedData[0].equals("success"))
						{
							clearFields();
							gr.refreshGUI(6);
						}
						else if(recievedData[0].equals("expired"))
						{
							accountExpired();
						}
						else if(recievedData[0].equals("locked"))
						{
							accountLocked();
						}
						else if(recievedData[0].equals("invalid"))
						{
							invalidPassword();
						}
						else if(recievedData[0].equals("missing"))
						{
							accountIsNotInDB();
						}
						else
						{
							System.out.println("Error with Login Panel");
						}
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
	public String getAccount()
	{
		return accountLP.getText();
	}
	public String getPassword()
	{
		return passwordLP.getText();
	}
	public void invalidPassword()
	{
		passwordLP.setText("");
		JOptionPane.showMessageDialog(loginLP, "Invalid Password Try Again.");
	}
	public void accountLocked()
	{
		JOptionPane.showMessageDialog(loginLP, "You have attempted to login to many times your account is LOCKED!!!!");
	}
	public void accountIsNotInDB()
	{
		accountLP.setText("");
		passwordLP.setText("");
		passwordLP.getText();
		JOptionPane.showMessageDialog(loginLP, "The account entered is not listed.");
	}
	public void accountExpired()
	{
		JOptionPane.showMessageDialog(loginLP, "Your account has expired!!!!");
	}
	public boolean fieldsFilled()
	{	
		return false;
	}
	public boolean okToLogon()
	{
		return true;
	}
	public void clearFields()
	{
		accountLP.setText("");
		passwordLP.setText("");
	}
}
