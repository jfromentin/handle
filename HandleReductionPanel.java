/* File : HandleReductionPanel.java
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class HandleReductionPanel extends JPanel {
    private JLabel currentBraidWord;
    private String braidWord="";   
    private boolean braidWordSet=false;
    public BraidDrawing braidDrawing; 
    public String currentWord="";
    public boolean working=false;
    private int animationStep;
    private float parameterAnimationStep2=1;
    private float parameterAnimationStep4;
    private float parameterAnimationStep5;
    private boolean continuousAnimation=true;
    private Image offScreenBuffer; 

	
    public HandleReductionPanel(){
	setBackground(new Color(220,220,220));
	setVisible(true);
    }
	
    public void add(char c){
	braidWord+=c;
	writeBraidWord(braidWord);
    }
	
    public void remove(){
	if(braidWord.length()!=0){
	    braidWord=braidWord.substring(0,braidWord.length()-1);
	    writeBraidWord(braidWord);
	}
    }
	
    public void clear(){
	braidWord="";
	writeBraidWord(braidWord);
    }
	
    public void writeBraidWord(String buffer){
	braidWord=buffer;
	braidDrawing=new BraidDrawing(braidWord);
	animationStep=0;
	updateCurrentBraidWord();
	braidWordSet=true;
	update(getGraphics());
    }

    public void updateCurrentBraidWord(){
	currentWord=braidDrawing.word();
    }

    public String getCurrentWord(){
	return braidWord;
    }
	
    public void paint(Graphics g){
	if(!braidDrawing.isDraw){
	    update(g);
	    braidDrawing.isDraw=true;
	}
	else{
	    g.drawImage(offScreenBuffer,0,0,this);
	}
    }
	
    public void refresh(){
	update(getGraphics());
    }
	
    public void update(Graphics g) {
	if(braidWordSet){
	    Graphics gr;
	    if (offScreenBuffer==null||(!(offScreenBuffer.getWidth(this)==getBounds().width && offScreenBuffer.getHeight(this)==getBounds().height))){
		offScreenBuffer=createImage(getBounds().width,getBounds().height);
	    }
	    gr=offScreenBuffer.getGraphics();
	    braidDrawing.calcXPos(gr,getBounds(),animationStep,parameterAnimationStep2,parameterAnimationStep4,parameterAnimationStep5);
	    braidDrawing.draw(gr); 
	    g.drawImage(offScreenBuffer,0,0,this);
	}
    }
	
    public int reduce(){
	if(!braidWordSet){
	    JOptionPane.showMessageDialog(null,"Vous devez d'abord saisir un mot de tresse ","Erreur",JOptionPane.ERROR_MESSAGE);
	    return 0;
	}
	else if(animationStep==0){	
	    braidDrawing.handleNormalDraw=true;
	    braidDrawing.findHandle();
	    if(braidDrawing.handleStrand==0){
		return 0;
	    }
	    braidDrawing.firstDraw=true;
	    braidDrawing.insertTrivials();
	    braidDrawing.trivialHeight=0;
	    animationStep=1;
	}
	else if(animationStep==1){
	    if(braidDrawing.trivialHeight>=1){
		braidDrawing.trivialHeight=1;
		parameterAnimationStep2=1;	    
		animationStep=2;
	    }
	    else{
		braidDrawing.trivialHeight+=0.1;
	    }
	}
	else if(animationStep==2){
	    braidDrawing.handleNormalDraw=false;
	    if(parameterAnimationStep2<0){
		parameterAnimationStep2=0;
		animationStep=3;
	    }
	    else{
		parameterAnimationStep2-=(float)(0.05);
	    }
	}
	else if(animationStep==3){
	    parameterAnimationStep4=0;
	    animationStep=4;
	}
	else if(animationStep==4){
	    if(parameterAnimationStep4>=1){
		braidDrawing.removeHandle();
		parameterAnimationStep2=1;
		parameterAnimationStep5=1;
		animationStep=5;
	    }
	    else{
		parameterAnimationStep4+=(float)0.05;
	    }
	}
	else if(animationStep==5){
	    if(parameterAnimationStep5<=0){
		parameterAnimationStep5=0;
		animationStep=6;
	    }
	    else{
		parameterAnimationStep5-=(float)0.05;
	    }
	}
	else if(animationStep==6){
	    if(braidDrawing.trivialHeight<=0){
		braidDrawing.trivialHeight=0;
		animationStep=7;
	    }
	    else{
		braidDrawing.trivialHeight-=0.1;
	    }
	}
	else if(animationStep==7){
	    braidDrawing.removeTrivials();
	    braidDrawing.handleNormalDraw=true;
	    braidDrawing.handleStrand=0;
	    animationStep=0;
	    braidDrawing.handleBeginIndice=0;
	    braidDrawing.handleEndIndice=0;
	    return 2;
	}
	else{
	    return 0;
	}
	braidWord=braidDrawing.word();
	return 1;
    }
}