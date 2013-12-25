
import java.io.*;
import java.net.*;



import javax.swing.JOptionPane;




public class Client {
	private String ip;
	private int port;
	private Painter clientPainter;
	private Socket host;
	private ObjectInputStream oisFromHost;
	private ObjectOutputStream oosToHost;
	int ableToConnectToHost = 1;
	
	public Client ( String ip , int port)
	{
		this.ip = ip;
		this.port = port;
	}
	public synchronized void sendToHost( Object message)
	{
		try{
		oosToHost.writeObject(message);
		}
		catch ( Exception e )
		{e.printStackTrace(); }
	}
		
	public void go()
	{
		setupNetwork();
		if ( ableToConnectToHost == 1)
		{
		
		clientPainter = new Painter( this);
		clientPainter.go();
	
		
		Thread t = new Thread ( new InReader());
		t.start();
		}
		
	}
	
	public void setupNetwork()
	{
		try{
		host = new Socket( ip, port);
		oisFromHost = new ObjectInputStream ( host.getInputStream());
		oosToHost = new ObjectOutputStream (host.getOutputStream());
	
		}
		catch ( Exception e)
		{
			JOptionPane.showMessageDialog(null, "Unable to connect to host!", "Fail to start", JOptionPane.ERROR_MESSAGE);
			ableToConnectToHost = 0;

		}
	}
	

	class InReader implements Runnable
	{
		
		
		
		
		public void run()
		{
			

				Object message;
				while (  true )
				{
					
					try
					{ 
			
						message=oisFromHost.readObject();
						
						if ( message instanceof String)
						{
							String stringMessage = (String) message;
							if ( stringMessage.equals(new String ( "clear" ) ) );
							{
								clientPainter.myCanvas.Clear();
							}
						}
						else if ( message instanceof Line)
						{
							 
							//clientPainter.myCanvas.addLine( (Line) message );
							clientPainter.myCanvas.addLine( (Line) message );
							//clientPainter.myCanvas.repaint();
							
						}
						else if ( message instanceof LineList)
						{
						
								
								//clientPainter.myCanvas.newLines( (ArrayList<Line>) message );
							//clientPainter.myCanvas.Clear();
							clientPainter.myCanvas.newList( (LineList) message );
							//clientPainter.myCanvas.repaint();
							
							
						}
					}
					catch ( Exception e)
					{
						JOptionPane.showMessageDialog(null, "Host is gone!", "Connection dropped", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
		}
	}
}