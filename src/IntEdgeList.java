/****** Last Modified 2/22/2018 Added Subclass to implement MST *******/
/*
 * IntEdgeList extends EdgeList
 * is a list of IntEdges rather than Edges
 */
public class IntEdgeList extends EdgeList{
	
	private IntEdge[] list;
	private int numOfIntItems;
	private int position;
	/*
	 * constructor for IntEdgeList
	 * 	invoking super() not necessary for MST deliverable but may later decide to use parent class methods and variables
	 */
	public IntEdgeList(){
		super();
		list = new IntEdge[10];
		this.numOfIntItems = 0;
		position = 0;
	}
	
	/*
	 * add() method to add IntEdges to the array
	 */
	public void add(IntEdge edge){
		// if maximum capacity is reached create bigger array
		if (numOfIntItems == list.length){
			IntEdge[] newArray = new IntEdge[numOfIntItems*2 +1];
			System.arraycopy(list, 0, newArray, 0, numOfIntItems);
			list = newArray;
		}
		
		list[numOfIntItems] = edge;
		numOfIntItems++;
	}
	/*
	 * retrieveCurrent() retrieves the current IntEdge in the array
	 * 	does not move current position forward
	 */
	public IntEdge retrieveCurrent(){
		if (position != numOfIntItems){
			return list[position];
		}
		else{
			return null;
		}
	}
	/*
	 * retrieveNextAdvanceNext() retrieves the current IntEdge in the array
	 * 	moves current position forward
	 */
	public IntEdge retrieveCurrentAdvanceNext(){
		if (position != numOfIntItems){
			position++;
			return list[position-1];
		}
		else {
			position = 0;
			return null;
		}
	}
	/*
	 * setCurrentPos() set current position = 0 for a reset
	 */
	public void setCurrentPos(int i){
		position = i;
	}
	/*
	 * getter method for the number of IntEdges in the array
	 */
	public int getNumOfIntItems(){
		return numOfIntItems;
	}
	/*
	 * Retrieve an IntEdge in the array whose end IntNode has the same name as the IntNode passed into the method
	 * 	used to retrieve the edge added to the MST
	 */
	public IntEdge retrieveEdge(IntNode node){
		for (int i = 0; i < numOfIntItems; i++){
			if (list[i].getEndIntNode().getName().equals(node.getName())){
				return list[i];
			}
		}
		return null;
	}
	
}