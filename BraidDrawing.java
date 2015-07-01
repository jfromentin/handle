/* File : BraidDrawing.java
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;


public class BraidDrawing extends Braid{
    private float strandStep;               //The distance between two strand
    private float strandWidth;              //The width of a strand
    private float strandLeft;               //The x position of the leftmost strand
    private float[][] xPos;
    private float yUp;
    private float yPos;
    public boolean handleNormalDraw;        //This variable is true if we draw the handle with the classic metod
    public boolean onHandle;                //This variable is true if we are in between the handleBegin Node and the handleEnd node
    public float trivialHeight;   
    private Color[] strandColor;             //The color of different strand
    private Color handleColor=Color.black;   //The color of the handle
    private Color originalHandleStrandColor; //The original color of the handle strand
    static public boolean isDraw;                  //For not repeat drawing
    public boolean firstDraw;
	
    public BraidDrawing(String braidWord){
	super(braidWord);
	trivialNumber=0;
	trivialHeight=0;
	handleNormalDraw=true;
	isDraw=false;
    }
    public void initStrandColor(){
	strandColor=new Color[strandNumber];
	for(int strand=1;strand<strandNumber+1;strand++){
	    strandColor[strand-1]=Color.getHSBColor((float)1-(float)strand/(float)strandNumber,(float)1,1);     
	}
    }
	
    public void drawTrivial(Graphics g,int indice, int strandNumber,float trivialHeight){
	float nextYPos;
	nextYPos=yPos+strandStep*trivialHeight;
	for(int strand=1;strand<strandNumber+1;strand++){
	    if(handleNormalDraw || (!handleNormalDraw && !isOnHandle(indice,strand))){
		drawLine(g,xPos[indice][strand-1],yPos,xPos[indice+1][strand-1],nextYPos,strandColor[strand-1]);
	    }
	}
	yPos=nextYPos;
    }
	
    public boolean isOnHandle(int indice,int strand){
        return (indice>=handleBeginIndice && indice<=handleEndIndice && strand==handleStrand);
    }
    
    public void drawLine(Graphics g, float xA, float yA, float xB,float yB,Color color){
	Graphics2D g2=(Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	g2.setStroke(new BasicStroke(strandWidth*2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
	GeneralPath path=new GeneralPath();
	float yBezier=(yB-yA)/3;
	path.moveTo(xA,yA);
	path.curveTo(xA,yA+yBezier,xB,yA+2*yBezier,xB,yB);
	g2.setColor(Color.white);                                       
	g2.draw(path);
	g2.setStroke(new BasicStroke(strandWidth,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
	path=new GeneralPath();
	path.moveTo(xA,yA);
	path.curveTo(xA,yA+yBezier,xB,yA+2*yBezier,xB,yB);
	g2.setColor(color);                                       
	g2.draw(path);
    }
    
    public void drawCrossing(Graphics g,int indice, int strandNumber,int generator, int exp){
	int begin;
	int step;
	GeneralPath gpl,gpm,gpr;	
	if(exp==1){
	    begin=1;
	}
	else{
	    begin=strandNumber;
	}
	for(int strand=begin;0<strand && strand<strandNumber+1;strand+=exp){
	    if(handleNormalDraw || (!handleNormalDraw && !isOnHandle(indice,strand))){
		if (strand==generator){
		    step=1;
		}
		else if(strand==generator+1){
		    step=-1;
		}
		else{
		    step=0;
		}
		drawLine(g,xPos[indice][strand-1],yPos,xPos[indice+1][strand+step-1],yPos+strandStep,strandColor[strand-1]);
	    }
	}
	yPos+=strandStep;
	Color temp=strandColor[generator-1];
	strandColor[generator-1]=strandColor[generator];
	strandColor[generator]=temp;
	if(generator==handleStrand && isOnHandle(indice,handleStrand)){
	    handleStrand++;
	}
	else if(generator==handleStrand-1 && isOnHandle(indice,handleStrand)){
	    handleStrand--;
	}
    }
	
    public void drawHandle(Graphics g){
	float yPos;
	int nextHandleStrand;
	int generator;
	float height;
	Node current;
	Node backupCurrent;
	backupCurrent=getCurrent();
	current=handleBegin;
	setCurrent(current);
	yPos=handleBeginIndice*strandStep+yUp;
	nextHandleStrand=handleStrand;
	for(int indice=handleBeginIndice;indice<handleEndIndice+1;indice++){
	    generator=value();
	    if(generator<0){
		generator=-generator;
	    }
	    if(generator==handleStrand){
		nextHandleStrand=handleStrand+1;
	    }
	    else if(generator==handleStrand-1 && generator!=0){
		nextHandleStrand=handleStrand-1;
	    }
	    else{
		nextHandleStrand=handleStrand;
	    }
	    if(generator==0){
		height=trivialHeight*strandStep;
	    }
	    else{
		height=strandStep;
	    }
	    drawLine(g,xPos[indice][handleStrand-1],yPos,xPos[indice+1][nextHandleStrand-1],yPos+height,handleColor);	
	    handleStrand=nextHandleStrand;
	    shift();
	    yPos+=height;
	}
	setCurrent(backupCurrent);
	yPos=yUp;
    }
	
    public void draw(Graphics g){
	int generator;          
	int exponent;    
	initCurrent();                                                       
	initStrandColor();        
	yPos=yUp;                                                           
	int stop=0;                                                            
	int indice=0;
	if(!handleNormalDraw && handleType==1){
	    drawHandle(g);
	}
	while(stop==0){
	    if(getCurrent()==handleBegin){
		onHandle=true;
		originalHandleStrandColor=strandColor[handleStrand-1];
		strandColor[handleStrand-1]=handleColor;
	    }
			
	    generator=value();
	    exponent=1;
	    if(generator<0){  
		exponent=-1;
		generator=-generator;              
	    }
	    if(generator!=0){
		drawCrossing(g,indice,strandNumber,generator,exponent);
	    }
	    else{
		if(indice!=0 && indice!=length()){
		    drawTrivial(g,indice,strandNumber,trivialHeight);
		}
		else{
		    drawTrivial(g,indice,strandNumber,1);
		}
	    }
	    if(getCurrent()==handleEnd){
		onHandle=false;
		strandColor[handleStrand-1]=originalHandleStrandColor;
	    }
	    if(isEnd()){    
		stop=1;
	    }
	    else{
		shift();
	    }
	    indice++;
	}
	drawTrivial(g,indice,strandNumber,1);
	if(!handleNormalDraw && handleType==-1){
	    drawHandle(g);
	} 
    }
	
    public void calcXPos(Graphics g,Rectangle r,int animationStep,float parameterAnimationStep2,float parameterAnimationStep4,float parameterAnimationStep5){  
	g.setColor(Color.white);   
	g.fillRect(0,0,r.width,r.height); 
	strandStep=(float)Math.min((double)r.width/(double)(strandNumber+1),(double)r.height/((double)(length())-((double)1-(double)trivialHeight)*(double)trivialNumber));
	strandWidth=strandStep/(float)5;
	strandLeft=((float)r.width-((float)(strandNumber+1)*(float)strandStep+strandWidth))/(float)2;
	yUp=(float)Math.max((double)0,(double)(r.height-(double)strandStep*((double)(length())-((double)1-(double)trivialHeight)*(double)trivialNumber))/(double)2);
	int length=length();
	xPos=new float[length+2][];
	for(int i=0;i<length+2;i++){
	    xPos[i]=new float[strandNumber];
	    for(int j=0;j<strandNumber;j++){
		xPos[i][j]=strandLeft+(j+1)*strandStep;
	    }
	}
	int generator;
	Node current;
	if(handleStrand>0){
	    if(animationStep>=2 && animationStep<=4){
		for(int indice=handleBeginIndice+1;indice<=handleEndIndice;indice++){
		    xPos[indice][handleStrand]+=(parameterAnimationStep2-1)*(float)strandStep;
		}
	    }
	    if(animationStep==4){
		current=handleBegin;
		for(int indice=handleBeginIndice+1;indice<=handleEndIndice;indice++){
		    xPos[indice][handleStrand-1]+=parameterAnimationStep4*(float)strandStep;
		}
		setCurrent(current);
		shift();
		for(int indice=handleBeginIndice+2;indice<handleEndIndice;indice++){
		    generator=value();
		    if(generator<0){
			generator=-generator;
		    }
		    if(handleStrand>1 && generator==handleStrand-1){
			xPos[indice-1][handleStrand-2]+=parameterAnimationStep4*(float)strandStep;
			xPos[indice][handleStrand-2]+=parameterAnimationStep4*(float)strandStep;
		    }
		    shift();
		}
		
	    }
	    if(animationStep==5){
		current=handleBegin;
		setCurrent(current);
		int currentStrand=handleStrand;
		for(int indice=handleBeginIndice;indice<=handleEndIndice;indice++){
		    generator=value();
		    if(generator<0){
			generator=-generator;
		    }
		    if(generator==currentStrand){
			currentStrand++;
		    }
		    else if(generator==currentStrand-1){
			currentStrand--;
		    }
		    if(handleStrand>1 && currentStrand==handleStrand-1){
			xPos[indice+1][handleStrand-2]+=parameterAnimationStep5*(float)strandStep;
		    }
		    shift();
		}
	    }
	}	
    }
	
	
    public String word(){
	boolean stop;
	int value;
	String res="";
	initCurrent();
	stop=false;
	while(!stop){
	    value=value();
	    if(value>0){
		res+=(char)((value-1)+'a');
	    }
	    else if(value<0){
		res+=(char)((-value-1)+'A');
	    }
	    if(isEnd()){
		stop=true;
	    }
	    if(!stop){
		shift();
	    }
	}
	if(res==""){
	    res="empty";
	}
	return res;
    }
	
}
