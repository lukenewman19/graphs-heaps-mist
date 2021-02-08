import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner; 
/*
 * The main Prog340LN class constructs the UI which gives the user options 
 * on how to handle the file.
 * Implements ActionListener for components to trigger events 
 * caught by the actionPerformed method.	
 */
class Prog340LN implements ActionListener{
		// instance variables of types File and Graph
		private File f;
		private Graph g;
		/*
		 * constructor of main Prog340LN class
		 * accepts a File argument to initialize the private instance variable
		 * creates UI for application
		 */
		public Prog340LN(File f, Graph g){
			this.f = f;
			this.g = g;
			// create container for swing components
			JFrame jfrm = new JFrame("A simple");
			jfrm.setSize(275,100);
			jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// JMenuBar is a container for JMenu
			JMenuBar actionMenu = new JMenuBar();
			// create JMenu which will contain the list of possible actions
			JMenu actionList = new JMenu("Action Menu");
			
			// create JMenuItems which are the actions
			JMenuItem io = new JMenuItem("io");
			JMenuItem heap = new JMenuItem("heap");
			JMenuItem mist = new JMenuItem("mist");
			JMenuItem sale1 = new JMenuItem("sale1");
			JMenuItem sale2 = new JMenuItem("sale2");
			JMenuItem consat = new JMenuItem("consat");
			//JMenuItem newFile = new JMenuItem("Select a new file");
			JMenuItem exit = new JMenuItem("exit");
			// a button to select a new file
			JButton newFile = new JButton("Select a new file");
			
			// add JMenuItems to JMenu
			actionList.add(io);
			actionList.add(heap);
			actionList.add(mist);
			actionList.add(sale1);
			actionList.add(sale2);
			actionList.add(consat);
			//actionList.add(newFile);
			actionList.add(exit);
			// add JMenu to JMenuBar
			actionMenu.add(actionList);
			// add button to JMenuBar
			actionMenu.add(newFile);
			
			// add actionListeners to all JMenuItems and button
			// object refered to by this is the instance of the Main class, 
			// it must implement actionListener interface
			io.addActionListener(this);
			heap.addActionListener(this);
			mist.addActionListener(this);
			sale1.addActionListener(this);
			sale2.addActionListener(this);
			consat.addActionListener(this);
			newFile.addActionListener(this);
			exit.addActionListener(this);
			
			// add JMenuBar to main swing container and set to visible
			jfrm.setJMenuBar(actionMenu);
			jfrm.setVisible(true);
		}
		/*
		 * method to catch events triggered by clicking the JMenuItems
		 */
		public void actionPerformed(ActionEvent ae){
			// obtain the name of the component clicked
			String command = ae.getActionCommand();
			// switch statement to respond to the different actions
			// a user could potentially choose
			switch (command){
				case "io": IO io = new IO(f, g);
					break;
				case "heap": Heap heap = new Heap(f, g);
					break;
				case "mist": Mist mist = new Mist(f, g);
					break;
				case "sale1": Sale1 sale1 = new Sale1(f, g);
					break;
				case "sale2": Sale2 sale2 = new Sale2(f, g);
					break;
				case "consat": Consat consat = new Consat(f, g);
					break;
				case "Select a new file": 
					JFileChooser chooser = new JFileChooser();
					chooser.setCurrentDirectory(new File("."));
					int returnVal = chooser.showOpenDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION){
						f = chooser.getSelectedFile();
					}
					// create the initial graph which will just have a list of Nodes
					g = Prog340LN.createGraph(f);
					// finish creating the graph with all its edges
					if (g != null){
						g.processText(f);
					}
					break;
				case "exit": System.out.println("Exit");
					System.exit(0);
				default: System.out.println("Invalid option!");
					System.exit(0);
			}
		}
		
		/*
		 * static method createGraph() will create the graph initially so it has a 
		 * 	list of nodes
		 * It is static because we need to access it before instantiating the Main class
		 */
		public static Graph createGraph(File inputFile){
			try{
				Scanner input = new Scanner(inputFile);
				// The top line of the file will tell us how many Nodes there will be 
				// 	plus the mnemonic values
				String firstLine = input.nextLine();
				// regular expressions = 1 or more whitespaces 
				String delims = "[ ]+";
				String[] mnemonics = firstLine.split(delims);
				// length of array will be the number of elements in the mnemonics array - 2 
				//	because the first two ( ~, val ) are not actual mnemonics
				Node[] nodes = new Node[mnemonics.length -2];
				// create the list of nodes with the mnemonic value
				for (int i = 2; i < mnemonics.length; i++){
					nodes[i-2] = new Node(mnemonics[i]);
				}
				// instantiate the graph with nodes array
				Graph g = new Graph(nodes);
				input.close();
				return g;
			} catch (IOException ex){
				System.out.println(ex.getMessage());
				return null;
			}
		}
		/*
		 * Program starts here 
		 * User will first choose a file to send to the "Main" class along with its graph,
		 * which will create the user interface for the program.
		 */
		public static void main(String args[]) throws Exception {
			SwingUtilities.invokeLater( new Runnable() {
				public void run() {
					// instantiate a JFileChooser to allow the user to select a file
					JFileChooser chooser = new JFileChooser();
					// set the current directory to the current directory of this program
					chooser.setCurrentDirectory(new File("."));
					// obtain the JFileChooser's exit status
					int returnVal = chooser.showOpenDialog(null);
					// if exit status is successful
					if (returnVal == JFileChooser.APPROVE_OPTION){
						File f = chooser.getSelectedFile();
						
						// create the initial graph which will just have a list of Nodes
						Graph g = Prog340LN.createGraph(f);
						// finish creating the graph with all its edges
						if (g != null){
							g.processText(f);
						}
						// instantiate an instance of the main class which will create the UI 
						new Prog340LN( f, g);
					}
					else { // the JFileChooser was unsuccessful if the else block gets executed
						System.out.println("There was an error in obtaining your file.");
						System.exit(0);
					}
					
				}
			});
		}
		
}
