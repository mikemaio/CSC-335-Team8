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
	private JButton changeChP = new JButton("Change Password");
	private JButton backChP = new JButton("Back");
	private TextField accountChP = new TextField("");
	private TextField oldPassChP = new TextField("");
	private TextField newPassChP= new TextField("");
	private TextField confirmNewPassChP = new TextField("");
	private JLabel accountEntryChP = new JLabel("Enter Account Name : ");
	private JLabel oldPassEntryChP = new JLabel("Enter Old Password : ");
	private JLabel newPassEntryChP = new JLabel("Enter New Password : ");
	private JLabel confirmPassChP  = new JLabel("Confirm New Password : ");

	public ChangePassPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(10,1);
    	window.setLayout(windowLayout);

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
						if(checkPassword())
						{
							
							gr.clientOne.sendString("changepassword",accountChP.getText(),oldPassChP.getText(),newPassChP.getText());
							String delims = "[,]";
							String recievedData[] = gr.clientOne.getflowValues().split(delims);
							if(recievedData[0].equals("success"))
							{
								JOptionPane.showMessageDialog(changeChP, "Success");
								clearFields();
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
							JOptionPane.showMessageDialog(changeChP, "Password Format requires One Number and One Special Character....Try Again.");
							clearFields();
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
	public void clearFields()
	{
		accountChP.setText("");
		oldPassChP.setText("");
		newPassChP.setText("");
		confirmNewPassChP.setText("");	
	}
}
