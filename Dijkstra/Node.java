package uppgift_3;

public class Node {
	private String word;
	private boolean known = false;
	private int dist = Integer.MAX_VALUE;
	private Node path;
	
	public Node(String word){
		this.word = word;
	}
	
	public void setKnown(boolean known){
		this.known = known;
	}
	public void setDist(int dist){
		this.dist = dist;
	}
	public void setPath(Node path){
		this.path = path;
	}
	public boolean isKnown(){
		return known;
	}
	public int getDist(){
		return dist;
	}
	public Node getPath(){
		return path;
	}
	@Override
	public String toString() {
		return this.word;
	}
}
