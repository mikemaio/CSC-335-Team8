package ClientServer;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class RegisterPanel extends JPanel{
	
	private GUIRoot gr;
	private JButton createRegP = new JButton("Create Account");
	private JButton backRegP = new JButton("Back");
	private TextField accountRegP = new TextField("Account Name...");
	private TextField emailRegP = new TextField("Email...");
	private TextField emailConfirmRegP= new TextField("Confirm Email...");
	private JPasswordField passwordRegP = new JPasswordField("");
	private JPasswordField confirmpassRegP = new JPasswordField("");
	private JLabel Username = new JLabel("Username: ", SwingConstants.RIGHT);
	private JLabel Email = new JLabel("Email: ", SwingConstants.RIGHT);
	private JLabel EmailConfirm = new JLabel("Confirm Email: ", SwingConstants.RIGHT);
	private JLabel Password = new JLabel("Password: ", SwingConstants.RIGHT);
	private JLabel PasswordConfirm = new JLabel("Confirm Password: ", SwingConstants.RIGHT);
	private JLabel End = new JLabel("");
	private JLabel EndLine1 = new JLabel("", SwingConstants.CENTER);
	private JLabel EndLine2 = new JLabel("", SwingConstants.CENTER);
	
	public RegisterPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(GUIRoot.BACKGROUND);
		//Username.setBackground(Color.red);
		
		//add Parts
		final JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(5,1);
    	window.setLayout(windowLayout);

    	window.add(Username);
		window.add(accountRegP);
    	window.add(Email);
		window.add(emailRegP);
		window.add(EmailConfirm);
		window.add(emailConfirmRegP);
		window.add(Password);
		window.add(passwordRegP);
		window.add(PasswordConfirm);
		window.add(confirmpassRegP);
//		window.add(createRegP);
//		window.add(backRegP);
//		window.add(End);
		this.add(window);
		
		JPanel buttons = new JPanel();
		GridLayout buttonLayout = new GridLayout(4,1);
		buttons.setLayout(buttonLayout);
		buttons.add(createRegP);
		buttons.add(backRegP);
		buttons.add(EndLine1);
		buttons.add(EndLine2);
		this.add(buttons);
		
		Username.setOpaque(true);
		Username.setBackground(GUIRoot.BACKGROUND);
		Email.setOpaque(true);
		Email.setBackground(GUIRoot.BACKGROUND);
		EmailConfirm.setOpaque(true);
		EmailConfirm.setBackground(GUIRoot.BACKGROUND);
		Password.setOpaque(true);
		Password.setBackground(GUIRoot.BACKGROUND);
		PasswordConfirm.setOpaque(true);
		PasswordConfirm.setBackground(GUIRoot.BACKGROUND);
		EndLine1.setOpaque(true);
		EndLine1.setBackground(GUIRoot.BACKGROUND);
		EndLine2.setOpaque(true);
		EndLine2.setBackground(GUIRoot.BACKGROUND);
		
		createRegP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						boolean correctFormat = true;
						String email = emailRegP.getText();
						
						//Username length is correct
						if (accountRegP.getText().length() >= UserHandling.USERMIN) {
							Username.setText("Username: ");
						} else {
							Username.setText("<html><font color='red'>Username is too short.</font></html>");
							correctFormat = false;
						}
						//password length is correct
						if (passwordRegP.getPassword().length >= UserHandling.USERMIN) {
							Password.setText("Password: ");
						} else {
							Password.setText("<html><font color='red'>Password is too short.</font></html>");
							correctFormat = false;
						}
						
						//password contains one letter and one number 
						//DOES NOT WORK
						/*if (///////)) {
							System.out.println("Valid characters");
						} else {
							System.out.println("Password does not contain all characters");
							End.setText("Password must have one letter and one number.");
							correctFormat = false;
						}*/
						
						//password and password confirm are the same
						if (Arrays.equals(passwordRegP.getPassword(),confirmpassRegP.getPassword())) {
							PasswordConfirm.setText("Confirm Password: ");
						} else {
							PasswordConfirm.setText("<html><font color='red'>Passwords don't match.</font></html>");
							correctFormat = false;
						}
						
						//email and email confirm are the same
						if (emailRegP.getText().equals(emailConfirmRegP.getText())) {
							EmailConfirm.setText("Confirm Email: ");
						} else {
							EmailConfirm.setText("<html><font color='red'>Emails don't match.</font></html>");
							correctFormat = false;
						}
						
						//email address is valid
						if (UserHandling.validEmailAddress(email)) {
							Email.setText("Email: ");
						} else {
							Email.setText("<html><font color='red'>Invalid Email.</font></html>");
							correctFormat = false;
						}
						
						//everything is correct
						if (correctFormat == true) {
							//register in server
							
							UserHandling.sendEmail(email, 
									"You have registered!\n\nYour username is: " 
									+ accountRegP.getText());
							EndLine1.setText("<html><font color='blue'>Email sent!</font></html>");
							EndLine2.setText("<html><font color='blue'>Please log in.</font></html>");
							
						} else {
							System.out.println("BAD");
							System.out.println("");
						}
					}
				}
			);

		backRegP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(2);
					}
				}
			);

	}
}
