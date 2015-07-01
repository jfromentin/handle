/* File : Braid.java
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

class Braid extends List{
    public int handleType;
    public int strandNumber=4;
    public Node handleBegin;
    public Node handleEnd;
    public int handleBeginIndice;
    public int handleEndIndice;
    public int handleStrand;
    public int trivialNumber; 

    public Braid(){}

    public Braid(String braidWord){
	int length;
	int value;
	char letter;
	strandNumber=4;
	value=0;
	length=braidWord.length();
	addLast(0);
	for(int i=0;i<length;i++){
	    letter=braidWord.charAt(i);
	    if(letter>='a' && letter<='z'){
		value=(int)(letter-'a')+1;
		if(value+1>strandNumber){       
		    strandNumber=value+1;     
		}
	    }
	    else if(letter>='A' && letter<='Z'){
		value=-(int)(letter-'A')-1;
		if(-value+1>strandNumber){
		    strandNumber=-value+1;
		}
	    }
	    else{
		value=0;
	    }
	    addLast(value);
	}
	addLast(0);
    }
	
	
    public void findHandle(){
	Node handles[]=new Node[strandNumber];
	int indices[]=new int[strandNumber];
	int signs[]=new int[strandNumber];
	int sign,value;
	boolean stop;
	int indice=0;
	for(int i=0;i<strandNumber;i++){
	    signs[i]=0;
	}
	stop=false;
	value=0;
	sign=0;
	initCurrent();
	while(!stop){
	    value=value();
	    sign=1;
	    if(value<0){
		value=-value;
		sign=-1;
	    }
	    else if(value==0){                
		sign=0;
	    }
	    if(signs[value]*sign<0){
		stop=true;
	    }
	    else{
		signs[value]=sign;
		indices[value]=indice;
		handles[value]=getCurrent();
		for(int j=1;j<value;j++){
		    signs[j]=0;
		}
	    }
	    if(isEnd()){
		stop=true;
	    }
	    if(!stop){
		indice++;
		shift(); 
	    }
	}
	if(signs[value]*sign<0){
	    handleBegin=handles[value];
	    handleEnd=getCurrent();
	    handleBeginIndice=indices[value];
	    handleEndIndice=indice;
	    handleStrand=value;
	    handleType=-sign;
	}
	else{
	    handleStrand=0;
	}
    }
	
    public void insertTrivials(){
	int value;
	boolean stop;
	stop=false;
	setCurrent(handleBegin);
	trivialNumber=0;
	while(!stop){
	    value=value();
	    if(Math.abs(value)==handleStrand-1){
		addBefore(0);
		setCurrent(addAfter(0));
		trivialNumber+=2;
	    }
	    shift();
	    if(getCurrent()==handleEnd){
		stop=true;
	    }
	}
	handleEndIndice+=trivialNumber;
    }
	
    public void removeHandle(){
	int sign;
	int value;
	boolean stop;
	sign=-handleType;
	stop=false;
	setCurrent(handleBegin);
	while(!stop){
	    value=value();
	    if(Math.abs(value)==handleStrand){
		(getCurrent()).setValue(0); 
	    }
	    if(value==0){
		(getCurrent()).setValue((handleStrand-1)*sign);
		sign=-sign;
	    }
	    if(value==handleStrand-1){
		(getCurrent()).setValue(value+1);
	    }
	    if(value==-(handleStrand-1)){
		(getCurrent()).setValue(value-1);
	    }
	    if(getCurrent()==handleEnd){
		stop=true;
	    }
	    else{
		shift();
	    }
	}
	trivialNumber=2;
    }
	
    public void removeTrivials(){
	remove(handleBegin);
	remove(handleEnd);
	trivialNumber=0;
    }
	
    public int indice(){
	int value;
	int indice=0;
	boolean stop=false;
	initCurrent();
	while(!stop){
	    value=value();
	    if(Math.abs(value)>Math.abs(indice)){
		indice=value;
	    }
	    if(isEnd()){
		stop=true;  
	    }
	    else{        
		shift(); 
	    }
	}
	return indice;
    }
	
}
