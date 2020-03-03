
class Node {
    private int value;     
	private Node next;
	private Node previous;
    
    public Node(){
		value=0;
    }
    
    public Node(int _value){
		value=_value;
    }
    
    public void setValue(int _value){
		value=_value;
    }
	
    public void setPrevious(Node _previous){
		previous=_previous;
    }
    
    public void setNext(Node _next){
		next=_next;
    }
	
    public int getValue(){
		return value;
    }
    
    public Node getPrevious(){
		return previous;
    }
    
    public Node getNext(){
		return next;
    }
}
