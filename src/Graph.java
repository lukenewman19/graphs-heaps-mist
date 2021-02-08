import java.io.*;
import java.util.Scanner;
/*
 * class Graph contains a list of nodes which have the information 
 * 	about their edges contained in the instance of the node itself
 */
public class Graph {
	// private instance variable listOfNodes is an array of nodes for the graph
	private Node[] listOfNodes;
	/*
	 * constructor for the graph class initializes instance variable
	 */
	public Graph(Node[] nodes){
		this.listOfNodes = nodes;
	}
	/*
	 * processText fills out all the information about the graph 
	 * 	obtained from from the second line and down from the inputfile
	 * The static method createGraph from the main class must be called first
	 * 	or the program will not work properly
	 */
	public void processText(File inputFile){
		try {
			Scanner input = new Scanner(inputFile);
			// skip to second line
			input.nextLine();
			int count = 0;
			while (input.hasNextLine()){
				String nextLine = input.nextLine();
				String delims = "[ ]+";
				String[] edgeValues = nextLine.split(delims);
				/*
				 * The first item in edgeValues array will be name of node
				 * The second item in edgeValues array will be value of node
				 * The third and so on if they exist (not equal to ~) will be value of edge
				 */
				for (int i = 0; i < edgeValues.length; i++){
					if (i == 0){
						listOfNodes[count].setName(edgeValues[i]);
					}
					else if (i == 1){
						listOfNodes[count].setValue(edgeValues[i]); 
					}
					else{
						if (!edgeValues[i].equals("~")){
							Edge newEdge = new Edge(edgeValues[i], listOfNodes[count], listOfNodes[i-2]);
							// listOfNodes[count] is the row
							listOfNodes[count].addEdgeTo(newEdge);
							// listOfNodes[i-2] is the column
							listOfNodes[i-2].addEdgeFrom(newEdge);
						}
					}
				}
				count++;
			}
			input.close();
		} catch (IOException ex){
			System.out.println(ex.getMessage());
		}
	}
	/*
	 * describeGraph() will return a string which is the full description of the graph
	 */
	public String describeGraph(){
		String output ="";
		for (int i = 0; i < listOfNodes.length; i++){
			// First line describing the node we are analyzing
			//output += "Node " + listOfNodes[i].getName() + ", mnemonic " + listOfNodes[i].getMnemonic() + ", value " + listOfNodes[i].getValue() + "\n";
			output += listOfNodes[i].toString();
			// Describe all edges in the forward direction
			for (int j = 0; j < listOfNodes[i].getEdgeTo().getNumOfItems(); j++){
				output += listOfNodes[i].getName() + " has edge to " + listOfNodes[i].getEdgeTo().retrieveCurrent().getEndNode().getName() + " labeled " + listOfNodes[i].getEdgeTo().retrieveCurrentAdvanceNext().getValue() + "\n";
				// This conditional is needed in case the user accidentally selects "io" from the gui with the same file 
				// 	current position of edgeList will need to be set to zero or else a null pointer exception will occur
				if ( j == listOfNodes[i].getEdgeTo().getNumOfItems() - 1){
					listOfNodes[i].getEdgeTo().setCurrentPos(0);
				}
			}
			// Describe all edges in the backward direction
			for (int k = 0; k < listOfNodes[i].getEdgeFrom().getNumOfItems(); k++){
				output += listOfNodes[i].getName() + " has edge from " + listOfNodes[i].getEdgeFrom().retrieveCurrent().getStartNode().getName() + " labeled " + listOfNodes[i].getEdgeFrom().retrieveCurrentAdvanceNext().getValue() + "\n";
				// This conditional is needed in case the user accidentally selects "io" again from the gui with the same file 
				// 	current position of edgeList will need to be set to zero or else a null pointer exception will occur
				if ( k == listOfNodes[i].getEdgeFrom().getNumOfItems() - 1){
					listOfNodes[i].getEdgeFrom().setCurrentPos(0);
				}
			}
			output += "\n";
		}
		return output;
	}
	/*
	 * getListOfNodes() is an getter method for the private instance variable
	 */
	public Node[] getListOfNodes(){
		return listOfNodes;
	}
}
