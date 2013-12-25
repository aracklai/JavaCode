import java.awt.*;
import java.io.*;
public class Line implements Serializable {

	Point a;
	Point b;
	Color c;
	int lineWidth;
	private static final long serialVersionUID = 26767;
	public Line ( Point p1, Point p2, Color c, int lineWidth)
	{
		a = p1;
		b = p2;
		this.c = c;
		this.lineWidth = lineWidth;
	}
	 public Line(Point point, Color c , int lineWidth) 
	 { 
	        a = point;
	        b = point;
	        this.c = c;
	        this.lineWidth = lineWidth;
	 } 

	public void  draw ( Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(c);
		g2d.setStroke( new BasicStroke(lineWidth , BasicStroke.CAP_ROUND ,BasicStroke.JOIN_ROUND));
		g2d.drawLine( a.x, a.y, b.x, b.y );
	}
	
}
