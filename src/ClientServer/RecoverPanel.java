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

public class RecoverPanel extends JPanel
{
	private GUIRoot gr;
	private JButton recoverRP = new JButton("Recover Password");
	private JButton backRP =  new JButton("Back");
	private JLabel accountEntry = new JLabel("Ener Account Name : ");
	private TextField accountRP = new TextField("");

	public RecoverPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;
		
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(5,1);
    	window.setLayout(windowLayout);
		window.add(accountRP);
		window.add(accountEntry);
		window.add(recoverRP);
		window.add(backRP);
		this.add(window);
		
		recoverRP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(!accountRP.equals(""))
						{
							gr.clientOne.sendString("recover",accountRP.getText(),null,null);
							String delims = "[,]";
							String recievedData[] = gr.clientOne.getflowValues().split(delims);
							if(recievedData[0].equals("success"))
							{
								System.out.println("success");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(recoverRP, "Please enter an account name.");
						}
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
