/*
 * Node class represents a Node in the graph
 */
public class Node {
	
	private String name;
	private String value;
	private String mnemonic;
	// Each node contains a list of edges going out (toDirection) and coming in (fromDirection)
	private EdgeList toDirection;
	private EdgeList fromDirection;
	/*
	 * constructor initializes the mnemonic value and empty edge lists
	 */
	public Node(String mnemonic){
		this.mnemonic = mnemonic;
		
		toDirection = new EdgeList();
		fromDirection = new EdgeList();
	}
	/*
	 * addEdgeTo() will add an edge going out
	 */
	public void addEdgeTo(Edge edge){
		toDirection.add(edge);
	}
	/*
	 * addEdgeFrom() will add an edge coming in
	 */
	public void addEdgeFrom(Edge edge){
		fromDirection.add(edge);
	}
	/*
	 * getName() retrieves the name of the node
	 */
	public String getName(){
		return name;
	}
	/*
	 * getMnemonic() retrieves the name of the mnemonic
	 */
	public String getMnemonic(){
		return mnemonic;
	}
	/*
	 * getValue() retrieves the value of the node
	 */
	public String getValue(){
		if (value.equals("~")){
			return "no value";
		} 
		else {
			return value;
		}
	}
	/*
	 * getEdgeTo() retrieves the list of edges going out from this node
	 */
	public EdgeList getEdgeTo(){
		return toDirection;
	}
	/*
	 * getEdgeFrom() retrieves the list of edges coming into this node
	 */
	public EdgeList getEdgeFrom(){
		return fromDirection;
	}
	/*
	 * setName(String name) will set the name of this node to its argument
	 */
	public void setName(String name){
		this.name = name;
	}
	/*
	 * setValue(String value) will set the value of this node to its argument
	 */
	public void setValue(String value){
		this.value = value;
	}
	/*
	 * setEdgeTo(EdgeList edges) will set the edges going out of this node
	 */
	public void setEdgeTo(EdgeList edges){
		this.toDirection = edges;
	}
	/*
	 * setEdgeFrom(EdgeList edges) will set the edges coming into this node
	 */
	public void setEdgeFrom(EdgeList edges){
		this.fromDirection = edges;
	}
	/*
	 * toString() returns a string description of the node
	 */
	public String toString(){
		String output = "";
		if (!value.equals("~")){
			output = "Node " + name + ", mnemonic " + mnemonic + ", value " + value + "\n";
		}
		else{
			output = "Node " + name + ", mnemonic " + mnemonic + ", no value \n";
		}
		return output;
	}
	
}
