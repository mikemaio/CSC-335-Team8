package ClientServer;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIRoot extends JFrame {
		
		public ConnectPanel cp;
		public LoginPanel lp;
		public RecoverPanel rp;
		public RegisterPanel regp;
		public ChangePassPanel chp;
		public ConnectedUsersPanel cup;
		public FunctionsPanel fp;
		public GetDataPanel gdp;
		public Client clientOne;
		public boolean serverRunning = false;
		public boolean clientCreated = false;
		//Panel for master control and used to refresh to new page selection 
		JPanel controlPanel = new JPanel();
		//Cases for Page Choice
		public int pageSelection = 1;
		public String _accountLP = "";
		public String _passwordLP = "";
		
		public GUIRoot (int height, int width)
		{
			//tester
			//testing mikey to master Test 123
			
			setTitle("RJ's SUPER SECRET PROGRAM?");

			setSize(width, height);
			
			setLocationRelativeTo(null);

			addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	if(clientOne != null)
	            	{
	            		clientOne.disconnect();
	            		System.out.println("Client Closed");
	            	}
	            	else
	            	{	
	            		System.out.println("Closed");
	            		e.getWindow().dispose();
	            	}
	            }
	        });

			// -- set the layout manager and add items
			//    5, 5 is the border around the edges of the areas		
			//Default case for Login
        	controlPanel = cp = new ConnectPanel(this);
        	setLayout(new BorderLayout(5,5));
        	this.add(controlPanel, BorderLayout.CENTER);


			// -- show the frame on the screen
			setVisible(true);
			
		}
		
		//public void getPanel
		

		//need to add case for each screen
		//focus listener to remove text
		public void refreshGUI(int caseID)
		{
			pageSelection = caseID;
			this.remove(this.controlPanel);
			switch (pageSelection) {
            case 1: 			
            	controlPanel = cp = new ConnectPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
                     break;
            case 2:  
            	if(!clientCreated)
            	{
            		clientOne = new Client(this);
            		clientCreated = true;
            	}
            	controlPanel = lp = new LoginPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 3:
            	controlPanel = rp = new RecoverPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 4:	
            	controlPanel = regp = new RegisterPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 5: 
            	controlPanel = chp = new ChangePassPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 6:
            	controlPanel = fp = new FunctionsPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 7:
            	controlPanel = cup = new ConnectedUsersPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            case 8:
            	controlPanel = gdp = new GetDataPanel(this);
            	setLayout(new BorderLayout(5,5));
            	this.add(controlPanel, BorderLayout.CENTER);
            	break;
            default: 
            	System.out.println("You Done Messed Up RJ");
                     break;
			}
			this.invalidate();
			this.validate();
			this.repaint();
		}

		public static void main (String[] args)
		{
			
			GUIRoot gr = new GUIRoot(400, 400);
		}
	}


