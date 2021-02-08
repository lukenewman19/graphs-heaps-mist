/*
 * EdgeList class represents an array of edges
 * 	allows iteration through edge array
 * 	array capacity will be expanded if necessary
 */
public class EdgeList {
	// private instance variables needed for the representation of the array
	private int numOfItems;
	private Edge[] data;
	private int currentPos;
	/*
	 * constructor EdgeList() initializes array and variables
	 */
	public EdgeList(){
		numOfItems = 0;
		currentPos = 0;
		data = new Edge[10];
	}
	/*
	 * add() method to add edges to the array
	 */
	public void add(Edge edge){
		// if maximum capacity is reached create bigger array
		if (numOfItems == data.length){
			Edge[] newArray = new Edge[numOfItems*2 +1];
			System.arraycopy(data, 0, newArray, 0, numOfItems);
			data = newArray;
		}
		
		data[numOfItems] = edge;
		numOfItems++;
	}
	/*
	 * retrieveCurrent() retrieves the current Edge in the array
	 * 	does not move current position forward
	 */
	public Edge retrieveCurrent(){
		if (currentPos != numOfItems){
			return data[currentPos];
		}
		else{
			return null;
		}
	}
	/*
	 * retrieveNextAdvanceNext() retrieves the current Edge in the array
	 * 	moves current position forward
	 */
	public Edge retrieveCurrentAdvanceNext(){
		if (currentPos != numOfItems){
			currentPos++;
			return data[currentPos-1];
		}
		else {
			currentPos = 0;
			return null;
		}
	}
	/*
	 * setCurrentPos() set current position = 0 for a reset
	 */
	public void setCurrentPos(int i){
		currentPos = i;
	}
	/*
	 * getNumOfItems() retrieves the number of items in the array
	 */
	public int getNumOfItems(){
		return numOfItems;
	}
}
