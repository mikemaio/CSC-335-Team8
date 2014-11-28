package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class ChangePassPanel extends JPanel {
	
	private GUIRoot gr;
	private JButton changeChP = new JButton("Change Password");
	private JButton backChP = new JButton("Back");
	private TextField accountChP = new TextField("Account Name...");
	private JPasswordField oldPassChP = new JPasswordField();
	private JPasswordField newPassChP= new JPasswordField();
	private JPasswordField confirmNewPassChP = new JPasswordField();
	private JLabel Username = new JLabel("Username: ", SwingConstants.RIGHT);
	private JLabel OldPassword = new JLabel("Old Password: ", SwingConstants.RIGHT);
	private JLabel NewPassword = new JLabel("New Password: ", SwingConstants.RIGHT);
	private JLabel NewPasswordConfirm = new JLabel("New Password: ", SwingConstants.RIGHT);
	private JLabel EndLine1 = new JLabel("", SwingConstants.CENTER);
	private JLabel EndLine2 = new JLabel("", SwingConstants.CENTER);

	public ChangePassPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		JPanel window = new JPanel();
		GridLayout windowLayout = new GridLayout(4,1);
		window.setLayout(windowLayout);

		window.add(Username);
		window.add(accountChP);
		window.add(OldPassword);
		window.add(oldPassChP);
		window.add(NewPassword);
		window.add(newPassChP);
		window.add(NewPasswordConfirm);
		window.add(confirmNewPassChP);
		this.add(window);

		JPanel buttons = new JPanel();
		GridLayout buttonLayout = new GridLayout(4,1);
		buttons.setLayout(buttonLayout);
		buttons.add(changeChP);
		buttons.add(backChP);
		buttons.add(EndLine1);
		buttons.add(EndLine2);
		this.add(buttons);

		this.setBackground(GUIRoot.BACKGROUND);
		Username.setOpaque(true);
		Username.setBackground(GUIRoot.BACKGROUND);
		OldPassword.setOpaque(true);
		OldPassword.setBackground(GUIRoot.BACKGROUND);
		NewPassword.setOpaque(true);
		NewPassword.setBackground(GUIRoot.BACKGROUND);
		NewPasswordConfirm.setOpaque(true);
		NewPasswordConfirm.setBackground(GUIRoot.BACKGROUND);
		EndLine1.setOpaque(true);
		EndLine1.setBackground(GUIRoot.BACKGROUND);
		EndLine2.setOpaque(true);
		EndLine2.setBackground(GUIRoot.BACKGROUND);
		
		changeChP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						boolean correctFormat = true;

						//Username is valid

						//old password is valid

						//password length is correct
						if (newPassChP.getPassword().length >= UserHandling.USERMIN) {
							NewPassword.setText("Password: ");
						} else {
							NewPassword.setText("<html><font color='red'>Password is too short.</font></html>");
							correctFormat = false;
						}

						//password and password confirm are the same
						if (Arrays.equals(newPassChP.getPassword(),confirmNewPassChP.getPassword())) {
							NewPasswordConfirm.setText("Confirm Password: ");
						} else {
							NewPasswordConfirm.setText("<html><font color='red'>Passwords don't match.</font></html>");
							correctFormat = false;
						}

						if (correctFormat == true) {
							//Change password

							/*UserHandling.sendEmail(email, 
									"Your password has been changed. \n\n
									If this is not correct, please change your password." 
									+ accountChP.getText());*/
							EndLine1.setText("<html><font color='blue'>Password changed!  </font></html>");
							EndLine2.setText("<html><font color='blue'>Please log in.  </font></html>");

						} else {
							//-1 tries
							System.out.println("BAD");
							System.out.println("");
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

}
