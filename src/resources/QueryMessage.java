package resources;

import java.io.Serializable;
import java.util.LinkedList;


public class QueryMessage implements Serializable{

	private LinkedList<Object> content;//=new LinkedList<Object>();
	private Queries query;
	private int retCode;
	
	public QueryMessage(Queries query,LinkedList<Object> content,int retCode)
	{
		this.content=content;
		this.query=query;
	    this.retCode=retCode;
	}
    public Queries getQuery()
    {
    	return query;
    }
	public LinkedList<Object> getContent()
	{
		return content;
	}
	public int getErrorCode()
	{
		return retCode;
	}
}
