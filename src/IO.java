import java.io.*;
import java.util.Scanner;
/*
 * Class IO creates the output file which is a description
 * of the adjacency matrix representation of a graph 
 * written on the input file 
 */
public class IO {
	// private instance variables of type File and Scanner
	File inputFile;
	Scanner input;
	Graph g;
	/*
	 * IO constructor will take a File argument and call the necessary 
	 * methods to get the description of the graph and write it to output file
	 */
	public IO(File inputFile, Graph g) {
		this.inputFile = inputFile;
		this.g = g;
		// get the description of the graph
		String content = g.describeGraph();
		// write description to output file
		writeOutput(content);
	}
	/*
	 * writeOutput() method to write content to output file
	 */
	private void writeOutput(String content){
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
			dataOutput.writeBytes(content);
			dataOutput.close();
		} catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
}
