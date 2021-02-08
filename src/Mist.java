/***** Last Modified 3/27/2018 MST due date 3/29/2018 *****/
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/*
 * Mist class used to create a Minimum Spanning Tree of a graph
 * 	The graph should be an Undirected Graph
 */
public class Mist {
	
	File inputFile;
	Graph g;
	Node[] listOfNodes;
	IntNode[] arrayOfIntNodes;
	Heap priorityQueue;
	IntNode[] MSTset;
	IntEdgeList MSTedges;
	
	public Mist(File inputFile, Graph g){
		// initialize some of the private instance variables
		this.inputFile = inputFile;
		this.g = g;
		this.listOfNodes = g.getListOfNodes();
		this.arrayOfIntNodes = new IntNode[listOfNodes.length];
		this.MSTedges = new IntEdgeList();
		// create equivalent integer nodes for every node of the graph
		// the IntNodes will have an int value of Integer.MAX_VALUE to start with - this is the key for the priority queue
		for (int i = 0; i < listOfNodes.length; i++){
			IntNode temp = new IntNode(listOfNodes[i].getMnemonic(),listOfNodes[i].getName(),listOfNodes[i].getValue(),
					listOfNodes[i].getEdgeTo(),listOfNodes[i].getEdgeFrom(), Integer.MAX_VALUE);
			arrayOfIntNodes[i] = temp;
		}
		/*
		 * Currently my IntNodes in arrayOfIntNodes contain Edgelists (effectively equivalent to adjacency lists)
		 * 	that point to other Nodes not IntNodes.  I change this by creating new Edges, instances of a subclass IntEdge,
		 * 	that point to other IntNodes.  They reference to the other IntNodes in the actual arrayOfIntNodes.
		 */
		IntEdge newEdge = null;
		for (int i = 0; i < arrayOfIntNodes.length; i++){
			// a new list of edges (adjacency list) that will be each node's adjacency list as i increments
			IntEdgeList newList = new IntEdgeList();
			// get the current list of edges for the i'th node
			EdgeList edges = arrayOfIntNodes[i].getEdgeTo();
			// iterate through this edgelist
			for (int k = 0; k < edges.getNumOfItems(); k++){
				// get the end node of each edge and create an equivalent IntNode for it
				Node end = edges.retrieveCurrent().getEndNode();
				IntNode endNode = new IntNode(end.getMnemonic(), end.getName(), end.getValue(), end.getEdgeTo(), end.getEdgeFrom(), Integer.MAX_VALUE);
				// Find the actual IntNode in the graph that is equivalent to this end Node and use it to create the new IntEdge
				for (int j = 0; j < arrayOfIntNodes.length; j++){
					if (arrayOfIntNodes[j].getName().equals(endNode.getName())){
						newEdge = new IntEdge(arrayOfIntNodes[i].getEdgeTo().retrieveCurrentAdvanceNext().getValue(),
								arrayOfIntNodes[i], arrayOfIntNodes[j] );
					}
				}
				// add the IntEdge to the new adjacency list for the i'th IntNode in the graph
				newList.add(newEdge);
				// we are done iterating through the edgelist if the condition is true
				// 	so reset the edgeList for possible further use and assign the creation of this new IntEdgeList to be the 
				//	IntNode's adjacency list that we will use
				if (k == edges.getNumOfItems() - 1){
					arrayOfIntNodes[i].getEdgeTo().setCurrentPos(0);
					arrayOfIntNodes[i].setEdgesTo(newList);
				}
			}
		}
		/*
		 * Our graph has now been modified to contain IntNodes with IntEdges pointing to other IntNodes within the graph
		 */
		// Set the int value of the first node to 0 since this is the key that the heap uses
		arrayOfIntNodes[0].setIntValue("0");
		//arrayOfIntNodes[0].setKey(0);
		this.priorityQueue = new Heap(arrayOfIntNodes);
		
		createMST();
		String description = describeMST();
		writeOutput(description);
	}
	
	/*
	 * Additional constructor to use if we wish to create an MST without any output
	 * The code is identical to the previous constructor so comments were deleted to shorten it
	 */
	public Mist(Graph g){
		this.g = g;
		this.listOfNodes = g.getListOfNodes();
		this.arrayOfIntNodes = new IntNode[listOfNodes.length];
		this.MSTedges = new IntEdgeList();
		for (int i = 0; i < listOfNodes.length; i++){
			IntNode temp = new IntNode(listOfNodes[i].getMnemonic(),listOfNodes[i].getName(),listOfNodes[i].getValue(),
					listOfNodes[i].getEdgeTo(),listOfNodes[i].getEdgeFrom(), Integer.MAX_VALUE);
			arrayOfIntNodes[i] = temp;
		}
		IntEdge newEdge = null;
		for (int i = 0; i < arrayOfIntNodes.length; i++){
			IntEdgeList newList = new IntEdgeList();
			EdgeList edges = arrayOfIntNodes[i].getEdgeTo();
			for (int k = 0; k < edges.getNumOfItems(); k++){
				Node end = edges.retrieveCurrent().getEndNode();
				IntNode endNode = new IntNode(end.getMnemonic(), end.getName(), end.getValue(), end.getEdgeTo(), end.getEdgeFrom(), Integer.MAX_VALUE);
				for (int j = 0; j < arrayOfIntNodes.length; j++){
					if (arrayOfIntNodes[j].getName().equals(endNode.getName())){
						newEdge = new IntEdge(arrayOfIntNodes[i].getEdgeTo().retrieveCurrentAdvanceNext().getValue(),
								arrayOfIntNodes[i], arrayOfIntNodes[j] );
					}
				}
				newList.add(newEdge);
				if (k == edges.getNumOfItems() - 1){
					arrayOfIntNodes[i].getEdgeTo().setCurrentPos(0);
					arrayOfIntNodes[i].setEdgesTo(newList);
				}
			}
		}
		arrayOfIntNodes[0].setIntValue("0");
		this.priorityQueue = new Heap(arrayOfIntNodes);
		createMST();
	}
	/*
	 * createMST() does the work of creating the MST
	 * a precondition is that the priority queue has been initialized 
	 */
	public void createMST(){
		/*
		 * We need a try block because of the method heapExtractMin() but for any other Exceptions 
		 * 	that may be thrown their message and stack trace are printed out in the catch block
		 */
		try{
			// declaring variables that will be used in the for loop
			int edgeValue;
			IntEdge currentEdge;
			IntEdgeList integerEdges;
			// in each iteration of the for loop an IntNode is extracted from the heap 
			// 	the for loop is equivalent to saying while the priority queue is not empty
			for (int k = 0; k < arrayOfIntNodes.length; k++){
				IntNode selectedNode = priorityQueue.heapExtractMin();
				// mark the node as already part of the growing spanning tree
				selectedNode.setColor("black");
				// retrieve the adjacency list of the selected node
				integerEdges = selectedNode.getEdgesTo();
				// every node but the initial one will have a predecessor which corresponds to an edge added to the MST
				if (selectedNode.getPredecessor() != null){
					//I retrieve the edge that should be added to the MST from the selected node's predecessor's list of edges					
					IntEdge addedEdge = selectedNode.getPredecessor().getEdgesTo().retrieveEdge(selectedNode);
					// alternative way the reverse edge - IntEdge addedEdge = integerEdges.retrieveEdge(selectedNode.getPredecessor());
					MSTedges.add(addedEdge);
				}
				/*
				 * Iterate through the adjacency list and decrease the key of adjacent IntNodes whose color is white
				 * 	and whose current key value is greater than the current edge value connecting it (both must be true)
				 * If changes are made, the priority queue is updated
				 */
				for (int i = 0; i < integerEdges.getNumOfIntItems(); i++){
					currentEdge = integerEdges.retrieveCurrentAdvanceNext();
					edgeValue = currentEdge.getIntValue();
					if (edgeValue < currentEdge.getEndIntNode().getIntValue() && currentEdge.getEndIntNode().getColor().equals("white")){
						currentEdge.getEndIntNode().setIntValue("" + edgeValue);
						currentEdge.getEndIntNode().setPredecessor(selectedNode);
						priorityQueue.heapDecreaseKey(currentEdge.getEndIntNode(), edgeValue);
						/*for (int j = priorityQueue.getHeapSize()/2; j >= 1; j--){
							priorityQueue.heapify(j);
						}
						IntNode[] data = priorityQueue.getHeapArray();
						for (int l = 1; l < data.length; l++){
							System.out.print(data[l].getName() + data[l].getIntValue());
						}*/
					}
					// reset the edgelist for possible future use if we are done iterating through it
					if (i == integerEdges.getNumOfIntItems() - 1){
						integerEdges.setCurrentPos(0);
					}	
				}
			}
		} catch (Exception ex){
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	/*
	 * describeMST() returns a String that describes the minimum spanning tree of the graph
	 */
	public String describeMST(){
		int sum = 0;
		String output = "MST edges in order of addition: ";
		if (MSTedges.getNumOfIntItems() > 0){
			IntEdge mstEdge = MSTedges.retrieveCurrentAdvanceNext();
			sum += mstEdge.getIntValue();
			output += mstEdge.getStartIntNode().getMnemonic() + mstEdge.getEndIntNode().getMnemonic();
		}
		for (int n = 1; n < MSTedges.getNumOfIntItems(); n++){
			IntEdge mstEdge = MSTedges.retrieveCurrentAdvanceNext();
			sum += mstEdge.getIntValue();
			output += ", " + mstEdge.getStartIntNode().getMnemonic() + mstEdge.getEndIntNode().getMnemonic();
		}
		output += "\nMST length: " + sum; 
		System.out.println(output);
		return output;
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
	/*
	 * accessor method that returns the modified arrayOfIntNodes
	 * 	it is modified to be structured as a minimum spanning tree after the method create MST is called
	 */
	public IntNode[] getIntNodes(){
		return arrayOfIntNodes;
	}
	/*
	 * accessor method that returns the edges of the minimum spanning tree
	 * 	both this method and previous are different perspectives of the MST
	 */
	public IntEdgeList getMSTEdges(){
		return MSTedges;
	}
}
