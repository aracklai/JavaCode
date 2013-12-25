

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Host
{

	private int port;
	Painter hostPainter;
	ArrayList<ObjectOutputStream> clients = new ArrayList<ObjectOutputStream>();
	ServerSocket serverSocket ;
	int succeedToCreateAServer = 1;
	
	
	
	
	
	public Host ( int port)
	{
		this.port = port;
		
	}
	public synchronized void sendToAll( Object message )
	{
		for(ObjectOutputStream oos: clients)
		{
		
			try
			{	
				
				oos.writeObject(message);
			}
			catch ( Exception e)
			{e.printStackTrace();}
		}
	}
	public synchronized int findClient ( ObjectOutputStream oos)
	{
		return clients.indexOf(oos);
	}
	public synchronized void  removeClient ( int index)
	{
		clients.remove(index);
	}
	public synchronized void addClient ( ObjectOutputStream oos)
	{
		clients.add(oos);
	}
	
	
	
	public void go()
	{
		try{
			serverSocket = new ServerSocket(port);
			
			}
			catch ( Exception e)
			{
				JOptionPane.showMessageDialog(null, "Unable to listen to port " + port + "!", "Fail to start", JOptionPane.ERROR_MESSAGE);
				succeedToCreateAServer = 0;
			}
		if ( succeedToCreateAServer == 1)
		{
		Thread serverThread = new Thread ( new ServerThread() );
		serverThread.start();
		hostPainter = new Painter( this );
		hostPainter.go();
		}
		

		
	
	}
	
	
	
	

	class ServerThread implements Runnable
	{
		
		public void run()
		{
			try
			{
				
				Socket client;
				while ( true)
				{
					
					client = serverSocket.accept();
					
		
			
	
					ObjectOutputStream oos = new ObjectOutputStream ( client.getOutputStream() );
					addClient (oos);
					LineList ready = hostPainter.myCanvas.getList();
					//for ( Line element : ready)
						oos.writeObject( (Object) ready );
					
					ClientHandler ch = new ClientHandler(client , oos);
					ch.start();

					
				}
			}
			catch ( Exception e)
			{
				System.out.println( "host error");
				e.printStackTrace();
			}
			
		}
		
		
		
		
		
		
	}
	class ClientHandler extends Thread
	{
	
		ObjectInputStream oisFromClient;
		ObjectOutputStream clientoos;
	
		public ClientHandler ( Socket client , ObjectOutputStream oos)
		{
			try
			{
	
				clientoos = oos;
				oisFromClient = new ObjectInputStream( client.getInputStream() );
				
				
			}
			catch( Exception e)
			{
				e.printStackTrace();
			}
			
		}
	
		public void run()
		{
			
		
			Object message;
			

			try
			{
				
				
				while ( ( message = oisFromClient.readObject() ) != null )
				{
					if ( message instanceof String)
					{
						if ( ( (String) message ).equals(new String ("logout")) )
						{
							
							int position = findClient( clientoos);
						
							removeClient ( position);
							break;
						}
					}
					else{
					//hostPainter.myCanvas.addLine((Line) message );
						hostPainter.myCanvas.addLine( (Line) message );
						//hostPainter.myCanvas.repaint();
						sendToAll( message );
					}
				}
			}
			catch ( Exception e)
			{
				e.printStackTrace();}
		}
		
	}

	
}
