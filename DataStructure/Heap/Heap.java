
public class Heap extends CBTree {
	public void insert(int key, Object data){
		Node newNode = super.add(key, data);
		upBubbling(newNode);
	}
	private void upBubbling(Node newNode){
		Node tempParent = newNode.getParent();
		Node tempChild = newNode;
		
		while(tempParent != null){
			int parentKey = ((Entry) tempParent.getElement()).getKey();
			int childKey = ((Entry) tempChild.getElement()).getKey();
			
			if(parentKey > childKey){
				swap(tempParent, tempChild);
			}
			tempChild = tempParent;
			tempParent = tempParent.getParent();
		}
	}
	public Entry removeMin(){
		Node temp;
		swap(findLastNode(), root);
		temp = remove();
		downBubbling(root);
		return (Entry) temp.getElement();
		
	}
	private void downBubbling(Node newNode){
		int parentKey;
		int childKey;
		Node tempParent = newNode;
		Node tempChild = newNode.getLeftChild();
		
		while(!tempParent.isLeaf()){
			if(tempParent.getLeftChild() != null
					&& tempParent.getRightChild() != null){
				if(((Entry) tempParent.getRightChild().getElement()).getKey() >
					((Entry) tempParent.getLeftChild().getElement()).getKey()){
					tempChild = tempParent.getLeftChild();
				}
				else{
					tempChild = tempParent.getRightChild();
				}
			}
			else if(tempParent.getRightChild() == null){
				tempChild = tempParent.getLeftChild();
			}
			else if(tempParent.getLeftChild() == null){
				tempChild = tempParent.getRightChild();
			}
			parentKey = ((Entry) tempParent.getElement()).getKey();
			childKey = ((Entry) tempChild.getElement()).getKey();
			if(parentKey > childKey){
				swap(tempParent, tempChild);
			}
			tempParent = tempChild;
		}
	}
	private void swap(Node up, Node down){
		Object tempEntry = up.getElement();
		up.setElement(down.getElement());
		down.setElement(tempEntry);
	}
	
}
