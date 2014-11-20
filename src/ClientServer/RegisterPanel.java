//package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RegisterPanel extends JPanel{
	
	private GUIRoot gr;
	private JButton createRegP = new JButton("Create Account");
	private JButton backRegP = new JButton("Back");
	private TextField accountRegP = new TextField("Enter Desired Account Name...");
	private TextField emailRegP = new TextField("Enter Desired Email...");
	private TextField emailConfirmRegP= new TextField("Confirm Email...");
	private TextField passwordRegP = new TextField("Enter Desired Password...");
	private TextField confirmpassRegP = new TextField("Confirm Password...");

	public RegisterPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);
		
		
		this.setBackground(Color.red);		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(7,1);
    	window.setLayout(windowLayout);


		window.add(accountRegP);
		window.add(emailRegP);
		window.add(emailConfirmRegP);
		window.add(passwordRegP);
		window.add(confirmpassRegP);
		window.add(createRegP);
		window.add(backRegP);
		this.add(window);
		
		createRegP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
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
