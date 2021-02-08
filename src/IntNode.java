/***** Last Modified 2/22/2018 *****/
/***
 * Added private instance variables int color, IntNode predecessor and IntEdgeList edgesTo and edgesFrom
 * 	These allow me to create the Minimum Spanning Tree
 * Added new constructor that does not set the intValue from the String value of the node
 * 	the int value is regarded as separate from the node value, it is the key for the priority queue
 * Added getters and setters for new instance variables
 */
/*
 * IntNode class extends Node 
 * 		the IntNode will be used in the heap as it will contain an integer value
 */
public class IntNode extends Node{
	// private instance variable intValue is the integer value of the IntNode
	int intValue;
	String color;
	IntNode predecessor;
	IntEdgeList edgesTo;
	IntEdgeList edgesFrom;
	/*
	 * IntNode(String mnemonic) constructor calls the super constructor
	 */
	public IntNode(String mnemonic){
		super(mnemonic);
	}
	/*
	 * IntNode(String mnemonic, String name, String value, EdgeList toDirection, EdgeList fromDirection)
	 * 		constructor for IntNode that takes all the information for a node as arguments and 
	 * 		creates a new IntNode by calling methods in the parent class and setting intValue
	 */
	public IntNode(String mnemonic, String name, String value, EdgeList toDirection, EdgeList fromDirection){
		super(mnemonic);
		this.setName(name);
		this.setValue(value);
		this.intValue = Integer.parseInt(value);
		this.setEdgeTo(toDirection);
		this.setEdgeFrom(fromDirection);
	}
	/*
	 * Additional constructor that will set the intValue separate from the String value of the node
	 * 	Also sets color to "white", predecessor to null, and instantiates IntEdgeLists
	 */
	public IntNode(String mnemonic, String name, String value, EdgeList toDirection, EdgeList fromDirection, int intValue){
		super(mnemonic);
		this.setName(name);
		this.setValue(value);
		this.setEdgeTo(toDirection);
		this.setEdgeFrom(fromDirection);
		this.intValue = intValue;
		this.color = "white";
		this.predecessor = null;
		this.edgesTo = new IntEdgeList();
		this.edgesFrom = new IntEdgeList();
	}
	/*
	 * getter method for instance variable intValue
	 */
	public int getIntValue(){
		return intValue;
	}
	/*
	 * setter method for instance variable intValue
	 */
	public void setIntValue(String intString){
		intValue = Integer.parseInt(intString);
	}
	/*
	 * getter method for instance variable color
	 */
	public String getColor(){
		return this.color;
	}
	/*
	 * setter method for instance variable color
	 */
	public void setColor(String color){
		this.color = color;
	}
	/*
	 * getter method for instance variable predecessor
	 */
	public IntNode getPredecessor(){
		return this.predecessor;
	}
	/*
	 * setter method for instance variable predecessor
	 */
	public void setPredecessor(IntNode predecessor){
		this.predecessor = predecessor;
	}
	/*
	 * getter method for instance variable edgesTo
	 */
	public IntEdgeList getEdgesTo(){
		return edgesTo;
	}
	/*
	 * setter method for instance variable edgesTo
	 */
	public void setEdgesTo(IntEdgeList edges){
		this.edgesTo = edges;
	}
	/*
	 * getter method for instance variable edgesFrom
	 */
	public IntEdgeList getEdgesFrom(){
		return edgesFrom;
	}
	/*
	 * setter method for instance variable edgesFrom
	 */
	public void setEdgesFrom(IntEdgeList edges){
		this.edgesFrom = edges;
	}
	/*
	 * compareTo(IntNode A) 
	 * 	compares current Node to the IntNode passed in to the method
	 * 	if the values are the same, they will be compared lexicographically
	 */
	public int compareTo(IntNode A){
		if (this.intValue < A.getIntValue()){
			return -1;
		}
		else if (this.intValue > A.getIntValue()){
			return 1;
		}
		else{
			int comparison = this.getName().compareTo(A.getName());
			if (comparison < 0){
				return -1;
			}
			else if (comparison > 0){
				return 1;
			}
			else{
				return 0;
			}
		}		
	}
}
