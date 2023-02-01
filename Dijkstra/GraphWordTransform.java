package uppgift_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

// Hugo Söderholm & Malte Enlund

public class GraphWordTransform {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner nodeInput = new Scanner(System.in);
		System.out.println("Enter the first word:");
		String start = nodeInput.nextLine();
		System.out.println("Enter the last word:");
		String end = nodeInput.nextLine();
		
		GraphBuilder graphB = new GraphBuilder();
		HashMap<String, Node> nmap = graphB.readFile();
		HashMap<Node, LinkedList<Node>> gmap = graphB.buildEdges();
		
		GraphComponent graphC = new GraphComponent();
		System.out.println("jj");
		System.out.println(gmap);
		Node n = nmap.get("vint");
		System.out.println("ha");
		System.out.println(n);
		System.out.println(gmap.get(nmap.get("vint")));
		/*LinkedList<Node> x = new LinkedList<Node>();
		x = gmap.get(nmap.get(start));
		System.out.println("hey");
		System.out.println(nmap.get(start));
		System.out.println(x);*/
		graphC.dijkstra(start, nmap, gmap);
	}
}

class GraphComponent {
	HashMap<Node, LinkedList<Node>> graph = new HashMap<Node, LinkedList<Node>>();
	LinkedList<Node> relatives;
	HashMap<String, Node> nodeMap = new HashMap<String, Node>();
	LinkedList<Node> unknownNodes = new LinkedList<Node>();
	
	GraphComponent(){}
	
	HashMap<String, Node> getNodeMap(){
		return nodeMap;
	}
	HashMap<Node, LinkedList<Node>> getGraph(){
		return graph;
	}
	void setNodeMap(HashMap<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	void addNode(String label) {
		Node n = new Node(label);
		relatives = new LinkedList<Node>();
		graph.put(n, relatives);
	}
	
	void addEdge(Node start, Node end) {
		graph.get(start).add(end);
	}
	
	void dijkstra(String start, HashMap<String, Node> nmap, HashMap<Node, LinkedList<Node>> gmap) {
		/*System.out.println("start");
		System.out.println(start.getClass().getName());
		System.out.println(nmap.toString());*/
		Node n = nmap.get(start);
		//System.out.println(nodeMap.get(start));
		n.setDist(0);
		for (Node nod : nmap.values()) {
			if (!nod.isKnown()) {
				unknownNodes.add(nod);
			}
		}
		
		for ( ; ; ) {
			Node v = chooseShortestDistNode();
			System.out.println("v");
			System.out.println(v);
			System.out.println(gmap.toString());
			System.out.println(v.getClass().getName());
			System.out.println(gmap.getClass().getName());
			System.out.println(gmap.get(v));
			//v = noden med minsta dist bland de noder som inte är known;
			/*if (v == null) {
				break;
			}*/
			v.setKnown(true);
			unknownNodes.remove(v);
			for (Node neighbor : gmap.get(v)){ // Kolla närliggande
				if (!neighbor.isKnown()) {
					int n_dist = neighbor.getDist();
					int v_dist = v.getDist();
					if (v_dist + 1 < n_dist){ // Bättre stig?
						neighbor.setDist(v_dist + 1);
						neighbor.setPath(v);
					}
				}
			}
		}
	}
	
	Node chooseShortestDistNode() {
		int shortestDist = Integer.MAX_VALUE;
		for (Node no : unknownNodes) {
			if (no.getDist() < shortestDist) {
				shortestDist = no.getDist();
				System.out.println(no.toString());
				return no;
			}
		}
		return null;
	}

	boolean known(ArrayList<Object> check) {
		if (check.get(1) == "T") {
			return true;
		}
		else return false;
	}
	
	/*Integer dist(ArrayList<Object> check) {
		return check.get(2);
	}
	
	String path(ArrayList<Object> check) {
		return check.get(3);
	}*/

}
	
class GraphBuilder {
	
	GraphComponent graph = new GraphComponent();
	
	protected HashMap<String, Node> readFile() throws IOException {
		// OBS! Ersätt pathen till filen med egna pathen!
		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\hugos\\eclipse-workspace\\Algoritmer\\src\\uppgift_3\\Words.txt"));
		
		HashMap<String, Node> nodeMap = graph.getNodeMap();
		String line = null;
		while ((line = reader.readLine()) != null) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(line);
			String word = stringBuilder.toString();
			Node n = new Node(word);
			nodeMap.put(word, n);
			graph.addNode(word);
		}
		System.out.println("nodeMap");
		System.out.println(nodeMap.toString());
		reader.close();
		return nodeMap;
	}
	
	protected HashMap<Node, LinkedList<Node>> buildEdges() {
		HashMap<Node, LinkedList<Node>> words = graph.getGraph();
		for (Node s : words.keySet()) {
			char[] charArray = s.toString().toCharArray();
			for (Node check : words.keySet()) {
				int indexCounter = 0;
				int matchCounter = 0;
				for (char c : check.toString().toCharArray()) {
					if (charArray[indexCounter] == c) {
						matchCounter++;
					}
					indexCounter++;
				}
				if (matchCounter == 3) {
					graph.addEdge(s, check);
				}
			}
			/*System.out.println("ah");
			System.out.println(words.get(s));*/
		}
		System.out.println("graph");
		System.out.println(words.toString());
		return words;
	}
}
