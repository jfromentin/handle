
class List {
    private Node first;
    private Node last;
    public Node current;  
	public int length;
    
    public List(){
		length=0;
    }
    
    public Node getFirst(){
		return first;
    }	
    
    public Node getLast(){
		return last;
    }
	
    public void initCurrent(){
		current=first;
    }
    
    public void setCurrent(Node _current){
		current=_current;
    }
    
    public Node getCurrent(){
		return current;
    }
    
    public boolean isEnd(){
		return current==last;
    }
	
    public void shift(){
		current=current.getNext();
    }
	
    public void addFirst(int value){
		Node node=new Node(value);
		if(length==0){
			first=node;
			last=node;
		}
		else{
			first.setPrevious(node);
			node.setNext(first);
			first=node;
		}
		length++;
    }
	
    public void addLast(int value){
		Node node=new Node(value);
		if(length==0){
			first=node;
			last=node;
		}
		else{
			last.setNext(node);
			node.setPrevious(last);
			last=node;
		}
		length++;
    }
    
    public Node addBefore(int value){
		Node node=new Node(value);
		if(current==first){
			first=node;
			node.setNext(current);
			current.setPrevious(node);
		}
		else {
			Node temp=current.getPrevious();
			temp.setNext(node);
			node.setPrevious(temp);
			current.setPrevious(node);
			node.setNext(current);
		}
		length++;
		return node;
    }
    
    public Node addAfter(int value){
		Node node=new Node(value);
		if(current==last){
			last=node;
			node.setPrevious(current);
			current.setNext(node);
		}
		else{
			Node temp=current.getNext();
			temp.setPrevious(node);
			node.setNext(temp);
			current.setNext(node);
			node.setPrevious(current);
		}
		length++;
		return node;
    }
	
    public void remove(Node node){
		if(length!=0){
			if(length==1){
				length=0;
			}
			else{
				if(node==first){
					first=node.getNext();
					node=first;
				}
				else if(node==last){
					last=node.getPrevious();
					current=last;
				}
				else{
					(node.getPrevious()).setNext(node.getNext());
					(node.getNext()).setPrevious(node.getPrevious());
				}
				length--;
			}
		}
    }
    
    public int value(){
		return current.getValue();
    }
	
    public int length(){
		return length;
    }
}
