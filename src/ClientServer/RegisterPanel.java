package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RegisterPanel extends JPanel{
	
	private GUIRoot gr;
	private JButton createRegP = new JButton("Create Account");
	private JButton backRegP = new JButton("Back");
	private TextField accountRegP = new TextField("");
	private TextField emailRegP = new TextField("");
	private TextField emailConfirmRegP= new TextField("");
	private TextField passwordRegP = new TextField("");
	private TextField confirmPassRegP = new TextField("");
	private JLabel accountEntryRegP = new JLabel("Enter Desired Account Name : ");
	private JLabel emailEntryRegP = new JLabel("Enter Desired Email : ");
	private JLabel emailReEntryRegP = new JLabel("Re-Enter Desired Email : ");
	private JLabel passwordEntryRegP = new JLabel("Enter Desired Password : ");
	private JLabel passwordReEntryRegP = new JLabel("Re-Enter Desired Password : ");


	


	public RegisterPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;		
		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(12,1);
    	window.setLayout(windowLayout);

    	window.add(accountEntryRegP);
		window.add(accountRegP);
    	window.add(emailEntryRegP);
		window.add(emailRegP);
    	window.add(emailReEntryRegP);
		window.add(emailConfirmRegP);
    	window.add(passwordEntryRegP);
		window.add(passwordRegP);
    	window.add(passwordReEntryRegP);
		window.add(confirmPassRegP);
		window.add(createRegP);
		window.add(backRegP);
		this.add(window);
		
		createRegP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						String account = accountRegP.getText();
						String password = passwordRegP.getText();
						String email = emailRegP.getText(); 
						//add ifs for account in function
						if(emailRegP.getText().equals(emailConfirmRegP.getText()))
						{
							gr.clientOne.sendString("registerprocedure",account,password,email);
							String delims = "[,]";
							String recievedData[] = gr.clientOne.getflowValues().split(delims);
							if(recievedData[0].equals("created"))
							{
								clearFields();
								JOptionPane.showMessageDialog(createRegP, "Account Created!!!!");
							}
							else if(recievedData[0].equals("exists"))
							{
								clearFields();
								JOptionPane.showMessageDialog(createRegP, "The account desired already exists please try again.");
								confirmPassRegP.setText("");

							}
							else
							{
								System.out.println("Error with Register panel");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(createRegP, "The two entered emails do not match..Try Again.");
						}
						clearFields();

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
	public void clearFields()
	{
		accountRegP.setText("");
		passwordRegP.setText("");
		emailRegP.setText("");
		emailConfirmRegP.setText("");
		confirmPassRegP.setText("");
		
	}
	public boolean checkFields()
	{
		//add here
		return false;
	}
}
