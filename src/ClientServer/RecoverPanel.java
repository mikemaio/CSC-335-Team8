package ClientServer;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class RecoverPanel extends JPanel
{
	private GUIRoot gr;
	private JButton recoverRP = new JButton("Recover Password");
	private JButton backRP =  new JButton("Back");
	private TextField accountRP = new TextField("Enter Account Name...");
	private TextField emailRP = new TextField("Enter Email...");
	private TextField emailConfirmRP= new TextField("Confirm Email...");

	public RecoverPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;

		this.setBackground(Color.red);

		
		this.setBackground(Color.red);		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(5,1);
    	window.setLayout(windowLayout);


		window.add(accountRP);
		window.add(emailRP);
		window.add(emailConfirmRP);
		window.add(recoverRP);
		window.add(backRP);
		this.add(window);
		
		recoverRP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				}
			);
		backRP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(2);
					}
				}
			);

	}

}
