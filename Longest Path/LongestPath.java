
import java.io.*;
import java.util.*;

public class LongestPath {

public static int V = 5;

//Function to find index of max-weight
//vertex from set of unvisited vertices
static int findMaxVertex(boolean visited[],
						int weights[])
{

	// Stores the index of max-weight vertex
	// from set of unvisited vertices
	int index = -1;

	// Stores the maximum weight from
	// the set of unvisited vertices
	int maxW = Integer.MIN_VALUE;

	// Iterate over all possible
	// nodes of a graph
	for (int i = 0; i < V; i++)
	{

	// If the current node is unvisited
	// and weight of current vertex is
	// greater than maxW
		if (visited[i] == false && weights[i] > maxW)
		{

			// Update maxW
			maxW = weights[i];

			// Update index
			index = i;
		}
		}
		return index;
	}

//Utility function to find the maximum
//spanning tree of graph
static void printMaximumSpanningTree(int graph[][],
									int parent[])
{

	// Stores total weight of
	// maximum spanning tree
	// of a graph
	int MST = 0;

	// Iterate over all possible nodes
	// of a graph
	for (int i = 1; i < V; i++)
	{

	// Update MST
	MST += graph[i][parent[i]];
	}

	System.out.println("Weight of the maximum Spanning-tree "
					+ MST);
	System.out.println();
	System.out.println("Edges \tWeight");

	// Print the Edges and weight of
	// maximum spanning tree of a graph
	for (int i = 1; i < V; i++)
	{
	System.out.println(parent[i] + " - " + i + " \t"
						+ graph[i][parent[i]]);
	}
}

//Function to find the maximum spanning tree
static void maximumSpanningTree(int[][] graph)
{

	// visited[i]:Check if vertex i
	// is visited or not
	boolean[] visited = new boolean[V];

	// weights[i]: Stores maximum weight of
	// graph to connect an edge with i
	int[] weights = new int[V];

	// parent[i]: Stores the parent node
	// of vertex i
	int[] parent = new int[V];

	// Initialize weights as -INFINITE,
	// and visited of a node as false
	for (int i = 0; i < V; i++) {
	visited[i] = false;
	weights[i] = Integer.MIN_VALUE;
	}

	// Include 1st vertex in
	// maximum spanning tree
	weights[0] = Integer.MAX_VALUE;
	parent[0] = -1;

	// Search for other (V-1) vertices
	// and build a tree
	for (int i = 0; i < V - 1; i++) {

	// Stores index of max-weight vertex
	// from a set of unvisited vertex
	int maxVertexIndex
		= findMaxVertex(visited, weights);

	// Mark that vertex as visited
	visited[maxVertexIndex] = true;

	// Update adjacent vertices of
	// the current visited vertex
	for (int j = 0; j < V; j++) {

		// If there is an edge between j
		// and current visited vertex and
		// also j is unvisited vertex
		if (graph[j][maxVertexIndex] != 0
			&& visited[j] == false) {

		// If graph[v][x] is
		// greater than weight[v]
		if (graph[j][maxVertexIndex]
			> weights[j]) {

			// Update weights[j]
			weights[j]
			= graph[j][maxVertexIndex];

			// Update parent[j]
			parent[j] = maxVertexIndex;
		}
		}
	}
	}

	// Print maximum spanning tree
	printMaximumSpanningTree(graph, parent);
}

//Driver Code
public static void main(String[] args)
{

	// Given graph
	int[][] graph = { 
					{ 0, 2, 0, 6, 0 },
					{ 2, 0, 3, 8, 5 },
					{ 0, 3, 0, 0, 7 },
					{ 6, 8, 0, 0, 9 },
					{ 0, 5, 7, 9, 0 } };

	// Function call
	maximumSpanningTree(graph);
}
}

//This code is contributed by Dharanendra L V
