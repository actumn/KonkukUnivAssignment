
public class ListNode {
	private TreeNode value;
	private ListNode prev;
	private ListNode next;
	
	public ListNode(){
		prev = null;
		next = null;
		value = null;
	}
	
	public ListNode(ListNode prev, ListNode next, TreeNode value){
		this.prev = prev;
		this.next = next;
		this.value = value;
	}
	
	public void setValue(TreeNode str){
		value = str;
	}
	
	public void setPrev(ListNode p){
		prev = p;
	}
	public void setNext(ListNode n){
		next = n;
	}
	public ListNode getPrev(){
		return prev;
	}
	public ListNode getNext(){
		return next;
	}
	public Object getEmplement(){
		return value;
	}
	
	public boolean equal(ListNode p){
		return value == p.value;
	}
}
