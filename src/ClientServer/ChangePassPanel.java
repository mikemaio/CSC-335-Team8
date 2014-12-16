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

public class ChangePassPanel extends JPanel {
	private GUIRoot gr;
	//button
	private JButton changeChP = new JButton("Change Password");
	private JButton backChP = new JButton("Back");
	//TextFields
	private TextField accountChP = new TextField("");
	private TextField oldPassChP = new TextField("");
	private TextField newPassChP= new TextField("");
	private TextField confirmNewPassChP = new TextField("");
	//Labels
	private JLabel accountEntryChP = new JLabel("Enter Account Name : ");
	private JLabel oldPassEntryChP = new JLabel("Enter Old Password : ");
	private JLabel newPassEntryChP = new JLabel("Enter New Password : ");
	private JLabel confirmPassChP  = new JLabel("Confirm New Password : ");
	//string for special characters
	private String specialChar =	"!@#$%^&*()_+[].*";

	public ChangePassPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(10,1);
    	window.setLayout(windowLayout);
    	//adds components of panel to frame
    	window.add(accountEntryChP);
		window.add(accountChP);
		window.add(oldPassEntryChP);
		window.add(oldPassChP);
		window.add(newPassEntryChP);
		window.add(newPassChP);
		window.add(confirmPassChP);
		window.add(confirmNewPassChP);
		window.add(changeChP);
		window.add(backChP);
		this.add(window);
		
		changeChP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
						{
						//check if fields are emptp					
						if (!accountChP.getText().equals("") && 
								!oldPassChP.getText().equals("") &&
								!newPassChP.getText().equals("") &&
								!confirmNewPassChP.getText().equals("")) 
						{

							//chekcs for a valid password format from function below
							if (validPassword()) {
								//check if password fields match
								if(checkPassword())
								{
									
									gr.clientOne.sendString("changepassword",accountChP.getText(),oldPassChP.getText(),newPassChP.getText());
									String delims = "[,]";
									String recievedData[] = gr.clientOne.getflowValues().split(delims);
									if(recievedData[0].equals("success"))
									{
										clearFields();
										JOptionPane.showMessageDialog(changeChP, "Success!!!");

										gr.refreshGUI(2);
									}
									else if(recievedData[0].equals("oldpasswordfail"))
									{
										JOptionPane.showMessageDialog(changeChP, "Your old password did not match the one on file try again.");
										clearFields();
									}
									else if(recievedData[0].equals("missing"))
									{
										JOptionPane.showMessageDialog(changeChP, "The Account you entered does not exist...Try Again");
										clearFields();
									}
									else
									{
										System.out.println("Error with password change field");
									}
								}
								else
								{
									JOptionPane.showMessageDialog(changeChP, "New passwords do not match.");
									clearFields();
								}
							}
						}
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
	public boolean checkPassword()
	{
		if(newPassChP.getText().equals(confirmNewPassChP.getText()))
		{
			return true;
		}
		return false;
	}
	//function to clear fields
	public void clearFields()
	{
		accountChP.setText("");
		oldPassChP.setText("");
		newPassChP.setText("");
		confirmNewPassChP.setText("");	
	}
	//checks for format specification
	public boolean validPassword() 
	{ 
		boolean correctFormat = true;
		boolean passNum = false;
		boolean passLower = false;
		boolean passUpper = false;
		boolean hasSpecChar = false;
		for(int i = 0; i < newPassChP.getText().length(); i++) {
			if (Character.isDigit(newPassChP.getText().charAt(i))) 
			{
				passNum = true;
			}
			if (Character.isLowerCase(newPassChP.getText().charAt(i))) 
			{
				passLower = true;
			}
			if (Character.isUpperCase(newPassChP.getText().charAt(i))) 
			{
				passUpper = true;
			}
		}
    	for(int i = 0; i < newPassChP.getText().length(); i++)
    	{
        	for(int t = 0; t < specialChar.length(); t++)
        	{
        		if(newPassChP.getText().charAt(i) == specialChar.charAt(t))
        		{
    				hasSpecChar = true;
        		}
        	}
    	}
		if(newPassChP.getText().length() < 8)
		{
			JOptionPane.showMessageDialog(changeChP, "Password must be atleast 8 characters long.");
			correctFormat = false;
		}
		if (!(passNum && passLower && passUpper && hasSpecChar)) {
			JOptionPane.showMessageDialog(changeChP, "Password must have at least one lowercase letter, one uppercase letter, and one number in it.");
			newPassChP.setText("");
			confirmNewPassChP.setText("");
			correctFormat = false;
		}
		return correctFormat;
	}
}