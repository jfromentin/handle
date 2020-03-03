import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.*;
import javax.swing.*;


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
	setBackground(Color.white);
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
	    //Graphics2D gr2=(Graphics2D) gr;
	    //g.drawRect(0,0,r.width-1,r.height-1); 
	    braidDrawing.calcXPos(gr,getBounds(),animationStep,parameterAnimationStep2,parameterAnimationStep4,parameterAnimationStep5);
	    braidDrawing.draw(gr); 
	    g.drawImage(offScreenBuffer,0,0,this);
	}
    }
    
	
	
    public boolean reduce(){
	if(!braidWordSet){
	    JOptionPane.showMessageDialog(null,"Vous devez d'abord saisir un mot de tresse ","Erreur",JOptionPane.ERROR_MESSAGE);
	    return false;
	}
	else if(animationStep==0){
	
	    braidDrawing.handleNormalDraw=true;
	    braidDrawing.findHandle();
	    if(braidDrawing.handleStrand==0){
		return false;
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
	    //update(getGraphics());	
	    animationStep=0;
	    //updateCurrentBraidWord();
	    braidDrawing.handleBeginIndice=0;
	    braidDrawing.handleEndIndice=0;

	}
	else{
	    return false;
	}
	braidWord=braidDrawing.word();
	return true;
    }
	
}
