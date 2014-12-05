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

public class GetDataPanel extends JPanel {

	private GUIRoot gr;
	private JButton queryDataBaseGDP = new JButton("Retrieve Data");
	private JButton backGDP = new JButton("Back");
	private JLabel enterCity = new JLabel("Enter desired city to get district: ");
	//private JLabel enterDesired = new JLabel("Enter Desired Info : ");
	private JLabel retrievedData = new JLabel("District : ");

	//private TextField queryDataDesiredGDP = new TextField("");
	private TextField queryDataCityGDP = new TextField("");
	private TextField displayDataGDP = new TextField();
	public GetDataPanel(GUIRoot _gr)
	{
		super();
		
		gr = _gr;
	
		//add Parts
		JPanel window = new JPanel();
    	GridLayout windowLayout = new GridLayout(5,1);
    	window.setLayout(windowLayout);
    	window.add(enterCity);
    	window.add(queryDataCityGDP);
    	//window.add(enterDesired);
    	//window.add(queryDataDesiredGDP);
    	window.add(retrievedData);
    	window.add(displayDataGDP);
    	window.add(queryDataBaseGDP);
    	window.add(backGDP);
		this.add(window);
		
		queryDataBaseGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) 
					{
						if(!queryDataCityGDP.getText().equals(""))
						{
							String city = queryDataCityGDP.getText();
							String desired = "District";
							gr.clientOne.sendString("querysubmit",city,desired,null);
							String delims = "[,]";
							String recievedData[] = gr.clientOne.getflowValues().split(delims);
							System.out.println(gr.clientOne.getflowValues());
							if(recievedData[0].equals("success"))
							{
								displayDataGDP.setText(recievedData[2]);
							}
							else if(recievedData[0].equals("failureforcity"))
							{
								clearFields();
								JOptionPane.showMessageDialog(null, "You entered an invalid City for info.");
							}
							else
							{
								JOptionPane.showMessageDialog(null, "You entered an invalid Column for info.");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Enter all fields.");
						}
					}
				}
			);
		displayDataGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					}
				}
			);
		
		backGDP.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						gr.refreshGUI(6);
					}
				}
			);

	}
	public void clearFields()
	{
		displayDataGDP.setText("");
		queryDataCityGDP.setText("");
	}

}
