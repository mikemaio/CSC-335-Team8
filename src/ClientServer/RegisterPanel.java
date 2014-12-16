package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class RegisterPanel extends JPanel{
	
	private GUIRoot gr;
	private JButton createRegP = new JButton("Create Account");
	private JButton backRegP = new JButton("Back");
	private TextField accountRegP = new TextField("");
	private TextField emailRegP = new TextField("");
	private TextField emailConfirmRegP= new TextField("");
	private TextField passwordRegP = new TextField("");
	//private JPasswordField passwordRegP = new JPasswordField("");
	private TextField confirmPassRegP = new TextField("");
	private JLabel accountEntryRegP = new JLabel("Enter Desired Account Name : ");
	private JLabel emailEntryRegP = new JLabel("Enter Desired Email : ");
	private JLabel emailReEntryRegP = new JLabel("Re-Enter Desired Email : ");
	private JLabel passwordEntryRegP = new JLabel("Enter Desired Password : ");
	private JLabel passwordReEntryRegP = new JLabel("Re-Enter Desired Password : ");
	//password check variables
	private int USERMIN = 8;
	private String emailregex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private Pattern pattern = Pattern.compile(emailregex);
	private String specialChar =	"!@#$%^&*()_+[].*";
	
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
						
						if (!accountRegP.getText().equals("") && 
								!emailRegP.getText().equals("") &&
								!emailConfirmRegP.getText().equals("") &&
								!passwordRegP.getText().equals("") &&
								!confirmPassRegP.getText().equals("")) 
						{
							
						if (correctFields()) 
						{
							gr.clientOne.sendString("registerprocedure",account,password,email);
							String delims = "[,]";
							String recievedData[] = gr.clientOne.getflowValues().split(delims);
							JOptionPane.showMessageDialog(createRegP, "Correct fields.");

							if (recievedData[0].equals("exists"))
							{
								JOptionPane.showMessageDialog(createRegP, "Desired username already exists.");
								accountRegP.setText("");

							} else if (recievedData[0].equals("created")) {
							
								clearFields();
								JOptionPane.showMessageDialog(createRegP, "Account Created!!!!");
								gr.refreshGUI(2);
							}
						}
						} else {
							JOptionPane.showMessageDialog(createRegP, "Please fill out all fields.");
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
	public void clearFields()
	{
		accountRegP.setText("");
		passwordRegP.setText("");
		emailRegP.setText("");
		emailConfirmRegP.setText("");
		confirmPassRegP.setText("");
		
	}	
    public boolean validEmailAddress (String emailaddress)
    {
        Matcher matcher = pattern.matcher(emailaddress);
        return matcher.find();
    }
    public boolean correctFields() {
    	boolean correctFormat = true;
    	boolean passNum = false;
		boolean passLower = false;
		boolean passUpper = false;
		boolean hasSpecChar = false;
		   	
    	if (accountRegP.getText().length() < USERMIN) 
		{
			JOptionPane.showMessageDialog(createRegP, "Username must be at least 8 characters long.");
			accountRegP.setText("");
			correctFormat = false;
		}
    	
    	for(int i = 0; i < passwordRegP.getText().length(); i++) 
    	{
			if (Character.isDigit(passwordRegP.getText().charAt(i))) 
			{
				passNum = true;
			}
			if (Character.isLowerCase(passwordRegP.getText().charAt(i))) 
			{
				passLower = true;
			}
			if (Character.isUpperCase(passwordRegP.getText().charAt(i))) 
			{
				passUpper = true;
			}
		}
    	for(int i = 0; i < passwordRegP.getText().length(); i++)
    	{
        	for(int t = 0; t < specialChar.length(); t++)
        	{
        		if(passwordRegP.getText().charAt(i) == specialChar.charAt(t))
        		{
    				hasSpecChar = true;
        		}
        	}
    	}
		
		if (!(passNum && passLower && passUpper && hasSpecChar)) 
		{
			JOptionPane.showMessageDialog(createRegP, "Password must have at least one lowercase letter, one uppercase letter, special character, and one number in it.");
			passwordRegP.setText("");
			confirmPassRegP.setText("");
			correctFormat = false;
		}
		
		if (passwordRegP.getText().length() < USERMIN) 
		{
			JOptionPane.showMessageDialog(createRegP, "Password must be at least 5 characters long.");
			passwordRegP.setText("");
			confirmPassRegP.setText("");
			correctFormat = false;
		}
		
		if (!passwordRegP.getText().equals(confirmPassRegP.getText())) 
		{
			JOptionPane.showMessageDialog(createRegP, "Passwords do not match.");
			confirmPassRegP.setText("");
			correctFormat = false;
		}
		
		if(!emailRegP.getText().equals(emailConfirmRegP.getText()))
		{
			JOptionPane.showMessageDialog(createRegP, "Emails do not match.");
			emailConfirmRegP.setText("");
			correctFormat = false;
		}
		
		if (!validEmailAddress(emailRegP.getText())) 
		{
			JOptionPane.showMessageDialog(createRegP, "Invalid email address.");
			emailRegP.setText("");
			emailConfirmRegP.setText("");
			correctFormat = false;
		}
		
    	return correctFormat;
    }
}