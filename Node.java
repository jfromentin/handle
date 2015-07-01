/* File : Node.java
 * Program : Handle Reduction Aimation - Applet 
 * By Jean Fromentin <jfroment@info.unicaen.fr>
 * Copyright 2008 Jean Fromentin
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

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
