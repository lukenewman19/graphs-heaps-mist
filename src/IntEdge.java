/***** Last Modified 2/22/2018 Added Subclass to implement MST *****/
/*
 * IntEdge extends Edge 
 * 	contains an integer value and has IntNodes as start and end nodes
 */
public class IntEdge extends Edge{
	
	private int value;
	private IntNode endIntNode;
	private IntNode startIntNode;
	/*
	 * constructor for IntEdge 
	 */
	public IntEdge(String value, IntNode startNode, IntNode endNode){
		super(value, endNode, startNode);
		this.value = Integer.parseInt(value);
		this.endIntNode = endNode;
		this.startIntNode = startNode;
	}
	/*
	 * getter method for integer value
	 */
	public int getIntValue(){
		return value;
	}
	/*
	 * setter method for integer value
	 */
	public void setIntValue(int intValue){
		this.value = intValue;
	}
	/*
	 * getter method for end IntNode
	 */
	public IntNode getEndIntNode(){
		return endIntNode;
	}
	/*
	 * getter method for start IntNode
	 */
	public IntNode getStartIntNode(){
		return startIntNode;
	}
}
