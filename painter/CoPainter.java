import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class CoPainter {
	JFrame starting = new JFrame("Collaborative Painter");
	JPanel hostPanel = new JPanel();
	JPanel portPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	
	JLabel host = new JLabel("Host: ");
	JLabel port = new JLabel("Port: ");
	JTextField hostText = new JTextField(17);
	

	JTextField portText = new JTextField(17);	
	

 
	ActionListener hostListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			try{
			Host host = new Host( Integer.valueOf( portText.getText() ) );
			host.go();
			if ( host.succeedToCreateAServer == 1 )
				starting.dispose();
			}
			catch ( NumberFormatException ee)
			{ JOptionPane.showMessageDialog(null, "Inappropriate Port Number !", "Fail to start", JOptionPane.ERROR_MESSAGE); }
			
		}
	};
	ActionListener clientListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			try{
			Client client = new Client( hostText.getText(),Integer.valueOf( portText.getText() ) );
			client.go();
			if ( client.ableToConnectToHost == 1)
				starting.dispose();
			}
			catch (  NumberFormatException ee)
			{
				JOptionPane.showMessageDialog(null, "Inappropriate Port Number !", "Fail to start", JOptionPane.ERROR_MESSAGE);
			}
		}
	};

	public void go()
	{
		
	
		hostText.setText("localhost");
		
		
		portText.setText("54321");
		portText.requestFocus();
		
		JButton hostButton = new JButton("Start as a host");
		JButton clientButton = new JButton("Connect to a host");
		
		hostButton.addActionListener(hostListener);
		clientButton.addActionListener(clientListener);
		
		hostPanel.add(host);
		hostPanel.add(hostText);
		
		portPanel.add(port);
		portPanel.add(portText);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.add(hostButton);
		buttonPanel.add(clientButton);
		
	
		
		starting.getContentPane().add(BorderLayout.NORTH,hostPanel);
		starting.getContentPane().add(BorderLayout.CENTER,portPanel);
		starting.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
		
		starting.setSize(265,130);
		
		starting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		starting.setVisible(true);
		portText.selectAll();
		portText.requestFocus();
		
	}
	
	
	public static void main(String[] args)
	{
		CoPainter cp = new CoPainter();
		cp.go();
	}
	
	
}

