import javax.swing.*;
import java.io.*; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.awt.event.MouseMotionListener; 
import java.awt.*;
public class Canvas extends JPanel implements Serializable {
	   private LineList linelist;
	   private Point previousPoint;
	   private Color gotColor;
	   private int gotWidth;
	   private static final long serialVersionUID = 574835789;
	   private Host myHost = null;
	   private Client myClient = null;
	
	   
	   Canvas ( Host myHost )
	   {
		   this.myHost = myHost;
		   linelist = new LineList();
		   addMouseListener(ml);
		   addMouseMotionListener(mml);
		   
	   }
	   Canvas ( Client myClient)
	   {
		   this.myClient = myClient;
		   linelist = new LineList();
		   addMouseListener(ml);
		   addMouseMotionListener(mml);
		  
	   }
	   
	   
	  
	   private MouseListener ml = new MouseListener()
	   {
			public void mouseClicked(MouseEvent event) {}
	    
	    	public void mousePressed(MouseEvent event)
	    	{ 
	    		setPreviousPoint ( event.getPoint());
	    		//previousPoint = event.getPoint();
	    		Line theLine = new Line(event.getPoint(), gotColor,gotWidth );
	    		if ( getHost() != null)
	    		{
	    			
	    			
	    	
	    			getList().addLine ( theLine);
	    	
	    			repaint();
	    			getHost().sendToAll( (Object) theLine );
	    		}
	    		else if ( getClient() != null )
	    		{
	    			

	    			getClient().sendToHost( (Object) theLine );
	    			
	    		}
	    	}
	    	public void mouseReleased(MouseEvent event) {}
	    	public void mouseEntered(MouseEvent event) {}
	    	public void mouseExited(MouseEvent event) {} 
		};
		
		private MouseMotionListener mml = new MouseMotionListener()
		{
			
			public void mouseDragged(MouseEvent event) 
			{ 
				Line theLine = new Line( previousPoint,event.getPoint(), gotColor,gotWidth );
				if ( getHost() != null)
				{
				
					 getList().addLine ( theLine);
			
					 repaint();
					getHost().sendToAll( (Object) theLine );
				}
				else if ( getClient() != null)
				{
					
					getClient().sendToHost( (Object) theLine );
				
				}
				setPreviousPoint ( event.getPoint());
				//previousPoint = event.getPoint();
		    }
			public void mouseMoved(MouseEvent event){} 
		};
	   
		public void setPreviousPoint( Point p)
		{
			previousPoint = p;
		}
		
	   public Host getHost()
	   {
		   return myHost;
	   }
	   public Client getClient()
	   {
		   return myClient;
	   }
	   public LineList getList()
	   {
		   return linelist;
	   }
	   public void newList( LineList ll )
	   {
		   linelist= ll;
		   repaint();
	   }
	   public synchronized void addLine ( Line theLine)
	   {
		   linelist.addLine(theLine);
		   repaint();
	   }
	   public synchronized void Clear()
	   {
		   getList().clear();
		   repaint();
		   if ( getHost() != null)
			   getHost().sendToAll ( (Object) new String("clear") );
	   }
	   
	   public void GetColor (Color c)
	   {
		   gotColor = c;
	   }
	   
	   public void GetWidth ( int w)
	   {
		   gotWidth = w;
	   }
	   
	   public synchronized void paintComponent(Graphics g)
	   { 
	        g.setColor(Color.WHITE); 
	        g.fillRect(0, 0, this.getWidth(), this.getHeight()); 
	        for(Line element:getList().getAllLines())
	        { 
	        	element.draw(g); 
	        } 
	    } 
}