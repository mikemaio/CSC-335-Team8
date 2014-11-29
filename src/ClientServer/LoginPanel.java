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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {

private GUIRoot gr;
private ConnectionThread ct;
//name undercase followed by initials of Class 
private JButton loginLP = new JButton("Login");; 
private JButton recoverLP = new JButton("Recover Password");
private JButton registerLP = new JButton("Register");
private JButton changepassLP = new JButton("Change Password");
private JButton disconnectLP = new JButton("Disconnect");
public static TextField accountLP = new TextField();
public static TextField passwordLP = new TextField();
public int loginCase = 1;

	
	


	public LoginPanel (GUIRoot _gr)
	{
		super();
		accountLP.setEditable(true);
		passwordLP.setEditable(true);

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
		accountLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						accountLP.validate();
						//DBaseConnection.account = accountLP.getText();
						System.out.print("trigger");
					}
				}
			);
		loginLP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//gr.refreshGUI(6);
						String account = accountLP.getText();
						String password = passwordLP.getText();
						String txt = gr.clientOne.sendString("loginprocedure",account,password,null);
						System.out.println(txt);
						switch(loginCase)
						{

						case 1:
							gr.refreshGUI(6);
							break;
						case 2:
							accountLocked();
							break;
						case 3:
							accountIsNotInDB();
							break;
						default:
							System.out.println("There is an error with the DB selection");
							break;
						}
						System.out.print(accountLP.getText());
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
						String txt = gr.clientOne.sendString("register",null,null,null);
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
		accountLP.setText("");
		passwordLP.setText("");
		JOptionPane.showMessageDialog(loginLP, "Invalid Password Try Again");
	}
	public void accountLocked()
	{
		JOptionPane.showMessageDialog(loginLP, "You have attempted to login to many times your account is LOCKED!!!!");
	}
	public void accountIsNotInDB()
	{
		JOptionPane.showMessageDialog(loginLP, "The account entered is not listed");
	}
	public void setCase(int x)
	{
		loginCase = x;
	}

}
