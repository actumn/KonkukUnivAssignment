
public class NodeStack {
	private Node[] nodes;
	private int index;
	public NodeStack(int n){
		nodes = new Node[n];
		index = -1;
		//System.out.println("static Init");
	}
	
	public void push_back(Node v){
		this.nodes[++index] = v;
		//System.out.println("(" + index + ", " + number + ")");
	}
	
	public Node[] getNodes(){
		return nodes;
	}
}
