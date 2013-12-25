import java.util.*;
import java.io.*;
public class LineList  implements Serializable{
	private static final long serialVersionUID = 267674732;
	private ArrayList<Line> theList;
	public LineList()
	{
		theList = new ArrayList<Line>();
	}
	public void addLine( Line theLine)
	{
		theList.add(theLine);
	}

	public ArrayList<Line> getAllLines( )
	{
		return theList;
	}
	public void clear()
	{
		theList.clear();
	}

}
