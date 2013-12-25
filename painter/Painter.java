import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.*; 


public class Painter implements WindowListener  {

	Host myHost;
	Client myClient;
	JFrame painter = new JFrame("Collaborative Painter");
	JSlider redSlider = new JSlider(0,255,0);
	JSlider greenSlider = new JSlider(0,255,0);
	JSlider blueSlider = new JSlider(0,255,0);
	JSlider sizeSlider = new JSlider(JSlider.VERTICAL,1,30,15);
	Canvas myCanvas;
	ShowColor sc = new ShowColor();
	ShowSize ss= new ShowSize();
	JFileChooser fileChooser = new JFileChooser();
	
	public void windowActivated (WindowEvent e){}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e){
	
		
			if ( myClient != null)
			{
				myClient.sendToHost( (Object) new String ("logout") );
			}
			System.exit(0);
		
	}
	public  void	windowDeactivated(WindowEvent e) {}
	public  void	windowDeiconified(WindowEvent e){}
	public 	void 	windowIconified(WindowEvent e) {}
	public  void	windowOpened(WindowEvent e) {}
	
	
	
	
	
	Painter ( Host myHost)
	{
		this.myHost = myHost;
		myCanvas = new Canvas ( myHost);
	}
	
	Painter ( Client myClient)
	{
		this.myClient = myClient;
		myCanvas = new Canvas ( myClient);
	}
	
 

	ActionListener clearListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			myCanvas.Clear();
		}
	};
	
	ActionListener saveListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			try{
				fileChooser.setDialogTitle("Save File");
				if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
				{
					FileOutputStream filestream = new FileOutputStream(fileChooser.getSelectedFile());
					ObjectOutputStream os = new ObjectOutputStream(filestream);
					//os.writeObject(myCanvas.getAllLines());
					os.writeObject(myCanvas.getList());
					os.close();
					
				}
			
			}
			catch(Exception ee)
			{
				ee.printStackTrace();}
		}
	};
	ActionListener loadListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			
			try{
				fileChooser.setDialogTitle("Load File");
				if ( fileChooser.showOpenDialog(fileChooser) == JFileChooser.APPROVE_OPTION)
				{
					FileInputStream filestream = new FileInputStream(fileChooser.getSelectedFile());
					ObjectInputStream os = new ObjectInputStream(filestream);
					Object one = os.readObject();
					myCanvas.newList( (LineList) one);
					os.close();
				}
				
				 
			myCanvas.repaint();
			myHost.sendToAll( (Object) myCanvas.getList() );
			
			}
			catch ( FileNotFoundException eee)
			{
				JOptionPane.showMessageDialog(null, "File not Found!", "Fail to load", JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			
		}
	};
	
	
	
	ActionListener exitListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if ( myClient != null)
			{
				myClient.sendToHost( (Object) new String("logout") );
			}
			System.exit(0);
		}
	};
	ChangeListener sliderListener = new ChangeListener()
	{
		public void stateChanged(ChangeEvent changeEvent)
		{
			sc.repaint();
			ss.repaint();
		}
	};

	
	public void go(){
 
		//building a painter GUI
		fileChooser.setDialogTitle("Choose a file");
		JMenuBar actionBar = new JMenuBar();
		JMenu action = new JMenu("Action");
		
		JMenuItem clear = new JMenuItem("Clear");
		clear.addActionListener(clearListener);
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(saveListener);
		
		JMenuItem load = new JMenuItem("Load");
		load.addActionListener(loadListener);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.addActionListener(exitListener);
		
		if ( myHost != null )
		{
			action.add(clear);
			action.add( save);
			action.add (load);
			action.add( exit);
		}
		else if ( myClient != null)
		{
			action.add(save);
			action.add(exit);
		}

		actionBar.add(action);
		
		painter.setJMenuBar(actionBar);
		
		JPanel tool = new JPanel();
		tool.setBackground(Color.white);
		
	
		JPanel colorPanel = new JPanel();
		
		redSlider.setBackground(Color.red);
		redSlider.setMajorTickSpacing(50);
		redSlider.setPaintTicks(true);
		redSlider.addChangeListener( sliderListener);
		
		greenSlider.setBackground(Color.green);
		greenSlider.setMajorTickSpacing(50);
		greenSlider.setPaintTicks(true);
		greenSlider.addChangeListener( sliderListener);
		
		blueSlider.setBackground(Color.blue);
		blueSlider.setMajorTickSpacing(50);
		blueSlider.setPaintTicks(true);
		blueSlider.addChangeListener( sliderListener);
		
		colorPanel.setLayout(new BoxLayout(colorPanel,BoxLayout.Y_AXIS));
		colorPanel.add(redSlider);
		colorPanel.add(greenSlider);
		colorPanel.add(blueSlider);
		
		
		
		JPanel sizePanel = new JPanel();
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setPreferredSize(new Dimension(100,100));
		sizeSlider.addChangeListener( sliderListener);
		
		ss.setPreferredSize(new Dimension(100,100));
		sizePanel.add(ss);
		sizePanel.add(sizeSlider);
		sizePanel.setBackground(Color.white);
		
		sc.setPreferredSize(new Dimension(100,100));
		tool.add(sc);
		tool.add(colorPanel);
		tool.add(sizePanel);
		tool.setBackground(Color.lightGray);
		
	
		painter.getContentPane().add(BorderLayout.CENTER,myCanvas);
		painter.getContentPane().add(BorderLayout.SOUTH,tool);
		painter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		painter.setSize(650,550);
		painter.setVisible(true);
		painter.addWindowListener( this );

		
	
	}
	
	
	class ShowColor extends JPanel 
	{
		private static final long serialVersionUID = 266546767;
		public void paintComponent(Graphics g)
		{
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor (new Color(redSlider.getValue(),greenSlider.getValue(),blueSlider.getValue() ) );
			myCanvas.GetColor (new Color(redSlider.getValue(),greenSlider.getValue(),blueSlider.getValue() ) );
			g2d.fillRect(0,0,this.getWidth(),this.getHeight());
		}
	}
	
	class ShowSize extends JPanel
	{
		private static final long serialVersionUID = 267321267;
		public void paintComponent(Graphics g)
		{
			int x;
			int y; 
			x = 50-sizeSlider.getValue()/2 ;
			y = 50-sizeSlider.getValue()/2 ;
	
			Graphics2D g2d = (Graphics2D) g;
			myCanvas.GetWidth (sizeSlider.getValue() );
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0,0,this.getWidth(),this.getHeight());
			g2d.setColor(Color.BLACK);
			g2d.fillOval(x,y,sizeSlider.getValue(),sizeSlider.getValue());
		}
	}
	
	
	
}

