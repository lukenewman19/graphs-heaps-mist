/*
 * class Edge represents an edge in the graph
 */
public class Edge {
	
	private String value;
	private Node startNode;
	private Node endNode;
	/*
	 * constructor initializes all instance variables
	 */
	public Edge(String value, Node startNode, Node endNode){
		this.value = value;
		this.startNode = startNode;
		this.endNode = endNode;
	}
	/*
	 * getValue() retrieves the value of the node
	 */
	public String getValue(){
		return value;
	}
	/*
	 * getStartNode() retrieves the starting Node of this edge
	 */
	public Node getStartNode(){
		return startNode;
	}
	/*
	 * getEndNode() retrieves the ending Node of this edge
	 */
	public Node getEndNode(){
		return endNode;
	}
}
