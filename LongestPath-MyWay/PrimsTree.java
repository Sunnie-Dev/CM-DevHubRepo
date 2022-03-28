
// Author: Sunnie _Dev  - March 25th 2022
// Lets use my variations of longest path of a graph  based on the premise of  Prim's shortest algorithm to solve this..

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class PrimsTree {
	
static  int  nodesGraph[][];

public int processGraph() {
		//read all user and bird data from file 
	   
	String fileName=null;    
	Scanner console = new Scanner(System.in);  // read keyboard
	System.out.print("Enter path of data file"); // get path data file

	fileName=console.nextLine();
	if (fileName.isBlank()||fileName.isEmpty()) 
		fileName="C:/Temp/assignment6_input.txt";
     console.close();
     
		boolean fileFound = true;
		int vertex=0; int row=0; int col = 0; int weight=0;
		File myObj = new File (fileName);
		Scanner fileIn = null;
		try {
			fileIn = new Scanner(myObj);
			if (fileIn.hasNextInt()){
				vertex = fileIn.nextInt();
				 int edge = fileIn.nextInt();
				 nodesGraph = new int [vertex][vertex];			
				 for(int i=0; i< vertex; i++)
					 Arrays.fill(nodesGraph[i], 0); // fill graph with 0
				}
			 while(fileIn.hasNextInt()) {
					 row = fileIn.nextInt();
					 col= fileIn.nextInt();
					 weight = fileIn.nextInt();
					 nodesGraph[row-1][col-1]= weight;
					 nodesGraph[col-1][row-1]= weight;
					}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(" The file does not exist - Create please and try again!");
		}

	   if (fileIn != null) {
		fileIn.close(); // close file
		}
	  return vertex;
	} 
	
public void getLongestPath(int i) {
	
	int tempGraph[][]=null;
	int maxWeight= 0; int pathLength=0; int count=0;
	int j=i; ArrayList<Integer> pathNodes = new ArrayList<Integer>();
	
	tempGraph = nodesGraph;
	for(int row=0; row < i; row++) {
		System.out.print("Vertex ["+(row+1)+"] = [");
		for (int col=0; col < j; col++) {
			 if (tempGraph[row][col] != 0) // something in the row, set its symmetric partner col/row to visited
			 { 
				 if (maxWeight < tempGraph[row][col]) {
					 maxWeight = tempGraph[row][col];	// get the maxWeight in the row 				
					 count++;    // counts the 
				 }
			 System.out.print((col)+1+" "+tempGraph[col][row]+"  ");	
			 tempGraph[col][row]=0; // since we visited the row /col that mean row/col is visited also so set this to 0 signifying already visited
			
			 }
		}System.out.println("]"); 
		 if (pathNodes.contains((row+1))== false && count !=0)
						pathNodes.add((row+1));
		pathLength=pathLength +maxWeight; // add the maximum weight in the row to the path
		maxWeight=0; // reset the weight cause you will read from the next row
		count=0;
	}
	System.out.println("The Longest path is : " + pathNodes);
	System.out.println("PathLength ="+pathLength);
}

  public static void main(String[] args) {
	  
	  
    PrimsTree useMunga = new PrimsTree();
    int i = useMunga.processGraph();
   
  
     //nodesGraph=graph;
    useMunga.getLongestPath(i);
  }
}