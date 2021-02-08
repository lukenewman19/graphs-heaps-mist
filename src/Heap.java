/***** Last Modified 2/22/2018 *****/
/***** 
 * Added public void heapDecreaseKey(IntNode node, int newValue)
 * 	couldn't use previous heapDecreaseKey method because for the MST I don't
 * 	know the index of the node whose key value I am decreasing
 * 
 *****/
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/*
 * The Heap class represents a min binary heap of IntNodes.
 */
public class Heap {
	/*
	 * private instance variables for heap class
	 * arrayOfNodes is initialized with the graphs nodes in the order they were read from the file
	 */
	File inputFile;
	IntNode[] arrayOfHeapNodes;
	Node[] arrayOfNodes;
	IntNode[] heapArray;
	int heapSize;
	/*
	 * constructor for heap initializes private instance variables
	 * 	inserts the nodes from the graph one at a time, prints the heap after each addition
	 * 	sorts the heap in decreasing order, printing the partially ordered array after each step
	 * 	will also write the output to a file
	 */
	public Heap(File inputFile, Graph g){
		this.inputFile = inputFile;
		this.arrayOfNodes = g.getListOfNodes();
		this.arrayOfHeapNodes = new IntNode[arrayOfNodes.length];
		this.heapArray = new IntNode[arrayOfNodes.length + 1];
		
		for (int i = 0; i < arrayOfNodes.length; i++){
			IntNode temp = new IntNode(arrayOfNodes[i].getMnemonic(),arrayOfNodes[i].getName(),arrayOfNodes[i].getValue(),
					arrayOfNodes[i].getEdgeTo(),arrayOfNodes[i].getEdgeFrom());
			arrayOfHeapNodes[i] = temp;
		}
		heapSize = 0;
		String output = "Heaps:\n";
		for (int i = 0; i < arrayOfHeapNodes.length; i++){
			heapInsert(arrayOfHeapNodes[i]);
			for (int j = 1; j <= heapSize; j++){
				output += j + ":" + heapArray[j].getMnemonic();
				if (j < heapSize){
					output += ", ";
				}
			}
			output += "\n";
		}
		output += heapsortWithPrint();
		writeOutput(output);
		//System.out.print(output);
	}
	/*
	 * another Heap constructor that just builds the heap
	 * 	no output
	 */
	public Heap(IntNode[] arr){
		heapArray = new IntNode[arr.length + 1];
		System.arraycopy(arr, 0, heapArray, 1, arr.length);
		buildHeap();
	}
	/*
	 * left(int i) returns the index of the left child of the node indexed at i 
	 */
	public int left(int i){
		return 2*i;
	}
	/*
	 * right(int i) returns the index of the right child of the node indexed at i 
	 */
	public int right(int i){
		return 2*i + 1;
	}
	/*
	 * parent(int i) returns the index of the parent of the node indexed at i
	 */
	public int parent(int i){
		return i/2;
	}
	/*
	 * heapInsert(IntNode node) increases the heap size by one
	 * 	and inserts a new IntNode into the heap 
	 * 	The new node is then placed into its correct postion in the heap 
	 * 		by calling heapDecreaseKey(...)
	 */
	public void heapInsert(IntNode node){
		heapSize = heapSize + 1;
		if (heapSize >= heapArray.length){
			IntNode[] newArray = new IntNode[heapSize*2+1];
			System.arraycopy( heapArray, 0, newArray, 0, heapSize - 1);
			heapArray = newArray;
			heapArray[heapSize] = node;
			try{
				heapDecreaseKey(heapSize, node);
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}
		}
		else {
			heapArray[heapSize] = node;
			try{
				heapDecreaseKey(heapSize, node);
			} catch (Exception ex){
				System.out.println(ex.getMessage());
			}
		}
	}
	/*
	 * heapDecreaseKey(int index, IntNode key) will float the new node key to be positioned at index
	 * 	up to its correct position in the heap
	 * 	if the key is greater than the node currently position at index an exception is thrown
	 * 		this is because if the key is greater than the current node it is possible that 
	 * 		it should be moved down not up
	 */
	public void heapDecreaseKey(int index, IntNode key) throws Exception{
		if ( key.compareTo(heapArray[index]) > 0){
			throw new Exception("New key is greater than current key!");
		}
		heapArray[index] = key;
		int i = index;
		while (i > 1 && heapArray[parent(i)].compareTo(heapArray[i]) > 0){
			IntNode temp = heapArray[i];
			heapArray[i] = heapArray[parent(i)];
			heapArray[parent(i)] = temp;
			i = parent(i);
		}
	}
	/*
	 * Method added for MST
	 * find the index first of the node whose key value was changed
	 */
	public void heapDecreaseKey(IntNode node, int newValue){
		int index = 0;
		// find the index of the node
		for (int i = 1; i <= heapSize; i++){
			if (heapArray[i].getName().equals(node.getName())){
				heapArray[i] = node;
				index = i;
			}
		}
		if (index !=0){
			while (index > 1 && heapArray[parent(index)].compareTo(heapArray[index]) > 0){
				IntNode temp = heapArray[index];
				heapArray[index] = heapArray[parent(index)];
				heapArray[parent(index)] = temp;
				index = parent(index);
			}
		}
	}
	/*
	 * heapExtractMin() extracts the root node from the heap and 
	 * 	then is heapified to keep the min heap properties
	 * 	throws an exception when there aren't any nodes to extract
	 * 	decreases heapsize after extracting the root
	 */
	public IntNode heapExtractMin() throws Exception{
		if (heapSize < 1){
			throw new Exception("Heap Underflow");
		}
		else{
			IntNode min = heapArray[1];
			heapArray[1] = heapArray[heapSize];
			heapSize = heapSize - 1;
			heapify(1);
			return min;
		}
	}
	/*
	 * buildHeap() 
	 * 	if the heapArray is not a valid min heap, buildHeap() 
	 * 		will reconstruct the array so it satisfies the min heap properties
	 */
	public void buildHeap(){
		heapSize = heapArray.length - 1;
		for (int i = heapSize/2; i >= 1; i--){
			heapify(i);
		}
	}
	/*
	 * heapify(int i) 
	 * 	will swap nodes from index i and down the subtree in order to maintain the min heap properties 
	 * 	assumes that the subtrees rooted at the left and right child are already min heaps
	 * 	the smaller of node i's two children will be swapped with node i
	 * 	if a swap occurs, heapify(int i) is called again with i being the position that the smallest node was in
	 * 	if no swap occurs, then i and its immediate children satisfy the heap property no change occurs
	 */
	public void heapify(int i){
		int leftChild = left(i);
		int rightChild = right(i);
		int smallest = i;
		if ( leftChild <= heapSize && heapArray[leftChild].compareTo(heapArray[i]) < 0){
			smallest = leftChild;
		}
		if ( rightChild <= heapSize && heapArray[rightChild].compareTo(heapArray[smallest]) < 0){
			smallest = rightChild;
		}
		if (smallest != i){
			IntNode temp = heapArray[smallest];
			heapArray[smallest] = heapArray[i];
			heapArray[i] = temp;
			heapify(smallest);
		}
	}
	/*
	 * heapsortWithPrint()
	 * 	makes sure that heapArray satisfies the min heap properties by calling buildheap()
	 * 	sorts heapArray in decreasing order
	 * 	extracts the root node by swapping it with the node in the back of the array and decreasing heapsize
	 * 		heapify(1) will then maintain the min heap properties of the remaining heap
	 * 	prints the array after each call to heapify
	 */
	public String heapsortWithPrint(){
		buildHeap();
		String output = "Heapsort:\n";
		for (int i = heapArray.length - 1; i >= 2; i--){
			IntNode temp = heapArray[heapSize];
			heapArray[heapSize] = heapArray[1];
			heapArray[1] = temp;
			heapSize = heapSize - 1;
			heapify(1);
			for (int j = 1; j <= heapArray.length - 1; j++){
				output += j + ":" + heapArray[j].getMnemonic();
				if (j < heapArray.length - 1){
					output += ", ";
				}
			}
			output += "\n";
		}
		return output;
	}
	/*
	 * heapsort()
	 * 	same as heapsortWithPrint() except will not print heapArray after each call to heapify
	 */
	public void heapsort(){
		buildHeap();
		for (int i = heapArray.length - 1; i >= 2; i--){
			IntNode temp = heapArray[heapSize];
			heapArray[heapSize] = heapArray[1];
			heapArray[1] = temp;
			heapSize = heapSize - 1;
			heapify(1);
		}
		
	}
	/*
	 * getter method that returns heapArray
	 */
	public IntNode[] getHeapArray(){
		return heapArray;
	}
	/*
	 * getter method that returns heapsize 
	 */
	public int getHeapSize(){
		return heapSize;
	}
	/*
	 * writeOutput(String output) will write its String argument to a file
	 * 	the file's name will be the same as the input filename + "_out"
	 */
	public void writeOutput(String output){
		// get the name of the file
		String Filename = inputFile.getName();
		// get position of the beginning of the extension of the file name
		int i = Filename.lastIndexOf(".txt");
		// create file name of the output file that will be written to
		String outputFileName = Filename.substring(0,i) + "_out.txt";
		// instantiate a DataOutputStream object to write output to content
		//  or catch IOException and print error message
		try{
			//FileOutputStream output = new FileOutputStream(outputFileName);
			DataOutputStream dataOutput = new DataOutputStream(new FileOutputStream(outputFileName));
			dataOutput.writeBytes(output);
			dataOutput.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	
}
