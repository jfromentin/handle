import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.net.*;
import java.io.*;
import java.applet.*;


class Node {
    //*********************************************
    //*                                           *
    //* This class is needed for the class list.  *
    //* A Node looks like an element of the list. *
    //*                                           *
    //*********************************************
    
    private int value;      //Value of the Node 
    private Node next;      //The next Node in the list
    private Node previous;  //The previous Node in the list
    
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

class List {
    //******************************************
    //*                                        *
    //* This class decribe a bichained list.   *
    //* I don't use the linkedList of java     *
    //* because it need use of complex Object. *
    //*                                        *
    //******************************************

    private Node first;   //The first Node of the list
    private Node last;    //The last Node of the list
    public Node current;  //A Node we play the game of an iterator
    public int length;    //The length of the list
    
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
    
    public int isEnd(){
	if(current==last){
	    return 1;
	}
	else{
	    return 0;
	}
    }

    public void shift(){
	// ---------------------------------------
	//| Change the current node with the next |
	// ---------------------------------------
	current=current.getNext();
    }

    public void addLast(int value){
	// ----------------------------------------------------
	//| Add a Node of value 'value' at the end of the list |
	// ----------------------------------------------------
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
	// -----------------------------------------------------------------
	//| Add a Node of value 'value' before the current Node of the list | 
	//| and return the new Node                                         |
	// -----------------------------------------------------------------
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
	// ----------------------------------------------------------------
	//| Add a Node of value 'value' after the current Node of the list | 
	//| and return the new Node                                        |
	// ----------------------------------------------------------------
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
	// ----------------------------------
	//| Remove the Node node of the list |
	// ----------------------------------
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
 
public class Diagram extends JApplet implements ActionListener{
    //*******************************************************
    //*                                                     *
    //* This class represent the window of the application. *
    //*                                                     *
    //*******************************************************


    public static final long serialVersionUID = 0;
    private Panel panel;           //Will draw graphics on the panel
    private JTextField textField;  //An area in which will enter a braid word
	    
    public void init(){
	JLabel label=new JLabel("Enter a word :");
	label.setBounds(0,0,100,20);
	add(label);
	textField=new JTextField(10);
	textField.addActionListener(this);  //Active the action listener of
	textField.setBounds(100,0,380,20);
	add(textField);
	panel=new Panel();
	panel.setBackground(Color.darkGray);
	panel.setBounds(0,20,480,600);
	add(panel);
	add(new JLabel(""));
    }

    public void actionPerformed (ActionEvent event){
	// ----------------------------------------------------------
	//| Specify what the action on the element of the windows do |
	// ----------------------------------------------------------
	if(event.getSource()==textField){
	    StringBuffer word=new StringBuffer(event.getActionCommand());
	    panel.writeBraidWord(word);
	}
    }
	
    public String getAppletInfo() {
        return "Handle reduction algorithm.";
    }
}
    

class Panel extends JPanel{
    //************************************************
    //*                                              *
    //* This class is the key class of this program. *
    //* It contains algoritms and drawing tools.     *
    //*                                              *
    //************************************************
    
    public static final long serialVersionUID = 0;

    //=====> Braid variable
    private StringBuffer braidWord;         //The initial braid word
    private int braidWordSet=0;
    private List braid;                     //The braid word as a list
    
    //=====> Strand variable
    private int strandNumber=5;             //The number of strand
    private float strandStep;               //The distance between two strand
    private float strandWidth;              //The width of a strand
    private float strandLeft;               //The x position of the leftmost strand
    private float[] xPos;                   //The x postion of strands
    private float[] xPosNext;               //The next x position of strands 
    private float yUp;                      //The y position of the first element of the braid
    
    //======> Color variable
    private Color[] strandColor;             //The color of different strand
    private Color handleColor=Color.black;   //The color of the handle
    private Color originalHandleStrandColor; //The original color of the handle strand
      
    //======> Way variable 
    private GeneralPath[] continuousWay;     //The currant continuous way of strands
    
    //======> Double Buffering variable   
    private Image offScreenBuffer;           //A buffer in wich we draw and after drawing we will show this buffer
    private int isDraw;                      //For not repeat drawing

    // =================
    //I                 I
    //I Braid functions I
    //I                 I
    // =================

    private void initBraid(){
	// -----------------------------------------------------
	//| Iniliasition of braid as a list and of strandNumber |
	// -----------------------------------------------------
	int length;
	int value;
	char letter;
	strandNumber=2;
	value=0;
	braid=new List();
	length=braidWord.length();                 //get the length of the braid word 
	braid.addLast(0);                          //add a trivial at the begin
	for(int i=0;i<length;i++){
	    letter=braidWord.charAt(i);
	    if(letter>='a' && letter<='z'){        //if we have a lower case letter
		value=(int)(letter-'a')+1;         //value is 1 for 'a', 2 for 'b', ...
		if(value+1>strandNumber){          //update the strand number        
		    strandNumber=value+1;     
		}
	    }
	    else if(letter>='A' && letter<='Z'){   //if we have an upper case letter
		value=-(int)(letter-'A')-1;        //value is -1 for 'A', -2 for 'B', ... 
		if(-value+1>strandNumber){         //update the strand number
		    strandNumber=-value+1;
		}
	    }
	    else{
		value=0;
	    }
	    braid.addLast(value);                  //add the value at the list of the braid
	}
	braid.addLast(0);                          //add a trivial at th end
    }


    public void writeBraidWord(StringBuffer buffer){
	// -----------------------------------------------------
	//| Initialise the braid word and some other parameters |
	//------------------------------------------------------
	braidWord=buffer;
	initBraid();                              //initialise the braid list
	isDraw=0;                                 //we draw a new image
	braidWordSet=1;
	update(super.getGraphics());              //update draw
    }

    
    // =================
    //I                 I
    //I Color functions I
    //I                 I
    // =================
    
    public void initStrandColor(){
	// ---------------------------
	//| Init the color of strands |
	// ---------------------------
	strandColor=new Color[strandNumber];                         //Make the table of strand color
	for(int strand=1;strand<strandNumber+1;strand++){
	    if(strandNumber<10){
		strandColor[strand-1]=Color.getHSBColor((float)1-(float)strand/(float)10,(float)0.8,1);     
	    }
	    else {
		strandColor[strand-1]=Color.getHSBColor((float)1-(float)strand/(float)strandNumber,(float)0.8,1);     
	    }	
	}
    }

 
    // ==========================
    //I                          I
    //I Continuous way functions I
    //I                          I
    // ==========================

    public void initContinuousWay(){
	// -----------------------------------------
	//| Start the continuous way for all strand |
	// -----------------------------------------
	continuousWay=new GeneralPath[strandNumber];             //Make the table of continuous way
	xPos=new float[strandNumber];                            //Make the table of x position of strands
	xPosNext=new float[strandNumber];                        //Make the table of next x position of strands
	for(int strand=1;strand<strandNumber+1;strand++){
	    continuousWay[strand-1]=new GeneralPath();           
	    xPos[strand-1]=strandLeft+strand*strandStep;         //The xPos of n is strandLeft+n*strandStep
	    continuousWay[strand-1].moveTo(xPos[strand-1],yUp);  //Start the continuous way at the y position yUp
	}
    }

    public void closeContinuousWay(Graphics2D g,int strand){
	// -------------------------------------------
	//| Stop and draw the continous way of strand |
	// -------------------------------------------
	GeneralPath gp;
	gp=continuousWay[strand-1];
	g.setColor(strandColor[strand-1]);                                                            //Apply the color of strand
	g.draw(gp);
	gp=new GeneralPath();                                                                         //Make a new general path                                   
	continuousWay[strand-1]=gp;                                                                   //Start the new continuous way
    }
   
    public void permuteContinuousWay(int strand){
	// ----------------------------------------------------------------- 
	//| Echange the continuous way and the color of strand and strand+1 |
	// -----------------------------------------------------------------
	GeneralPath gp;
	Color color;
	//Echange continuous way 
	gp=continuousWay[strand-1];
	continuousWay[strand-1]=continuousWay[strand];
	continuousWay[strand]=gp;
	//Echange color
	color=strandColor[strand-1];
	strandColor[strand-1]=strandColor[strand];
	strandColor[strand]=color;
    }

    // ================
    //I                I
    //I Math functions I
    //I                I
    // ================
   
    public double min(double a,double b){
	if(a<b){
	    return a;
	}
	else{
	    return b;
	}
    }

    public double max(double a,double b){
	if(a>b){
	    return a;
	}
	else{
	    return b;
	}
    }

    public double square(double a){
	return a*a;
    }

    // ===================
    //I                   I
    //I Position function I
    //I                   I
    // ===================

    public void calcXPosNext(){
	// ----------------------------------------
	//| Compute the nex x position of strands. |
	//| It's a key function for animation.     |
	// ----------------------------------------
	for(int strand=0;strand<strandNumber;strand++){
	    xPosNext[strand]=strandLeft+(strand+1)*strandStep;                                             //The general case
	}
    }

    public void updateXPos(){
	// ------------------------------------------------------------------
	//| Update the x postion of strand with the nex x position of strand | 
	// ------------------------------------------------------------------
	for(int strand=0;strand<strandNumber;strand++){
	    xPos[strand]=xPosNext[strand];
	}	
    }

    public float[] calculIntersection(int strand,float yPos,int type){
	// -----------------------------------------------------------------------
	//| Compute the two key point for draw the generator strand |
	// -----------------------------------------------------------------------
	float []res=new float[4]; 
	//res[0]<=res[1] is the x position of this points
	//res[2]<=res[3] is the y position of this points
	double decale=1.5*strandWidth;                                                      //Is distance of a strand with the other in the crossing
	double distance;                         
	if(type==1){                                                                        //If the crossing is positive 
	    //We compute the distance between the left-up point and then right-down  
	    distance=Math.sqrt(square(xPosNext[strand]-xPos[strand-1])+square(strandStep));
	}
	else {                                                                              //If the crossing is negative
	    //We compute the distance between the right-up and the left-down 
	    distance=Math.sqrt(square(xPos[strand]-xPosNext[strand-1])+square(strandStep));
	}
	double alpha=Math.acos(strandStep/distance);                                        //The angle of the crossing
	double decalX=Math.sin(alpha)*decale;                                               //The x coords of decale
	double decalY=Math.cos(alpha)*decale;                                               //The y coords of decale
	
	//The straight line D1 of equation x=m1*y+p1 contains the left-up and the right-down point
	double m1=((double)strandStep)/((double)(xPosNext[strand]-xPos[strand-1]));
	double p1=yPos-m1*((double)xPos[strand-1]);
	//The straight line D2 of equation x=m2*y+p2 contains the right-up and the left-down point
	double m2=((double)strandStep)/((double)(xPosNext[strand-1]-xPos[strand]));
	double p2=yPos-m2*((double)xPos[strand]);
	
	double xInter=(p2-p1)/(m1-m2);                                                      //x coord of D1 inter D2               
	double yInter=m1*xInter+p1;                                                         //y coord of D1 inter D2
	
	//Computaion of values of res, the min and max fonction are usefull for a good drawing
	res[0]=(float)max(xInter-decalX,xPosNext[strand-1]);
	res[1]=(float)min(xInter+decalX,xPosNext[strand]);
	res[2]=(float)max(yInter-decalY,yPos);
	res[3]=(float)min(yInter+decalY,yPos+strandStep);
	return res;
    }

    // ===================
    //I                   I
    //I Drawing functions I
    //I                   I
    // ===================

   
    public void drawTrivial(float yPos){
	// -------------------------------------
	//| Draw a not extrem trivial generator |
	// -------------------------------------
	for(int strand=0;strand<strandNumber;strand++){
	    continuousWay[strand].lineTo(xPosNext[strand],yPos+strandStep);
	}
    }
    
    public void drawNoCrossing(Graphics2D g,int strand,float yPos){
	// ----------------------
	//| Draw straight strand |
	// ----------------------
	continuousWay[strand-1].lineTo(xPosNext[strand-1],yPos+strandStep);
    }

    public void drawPositiveCrossing(Graphics2D g,int strand,float yPos){
	// --------------------------
	//| Draw a positive crossing |
	// --------------------------
	float inter[]=calculIntersection(strand,yPos,1);
	continuousWay[strand-1].lineTo(inter[0],inter[2]);
	closeContinuousWay(g,strand);	
	continuousWay[strand-1].moveTo(inter[1],inter[3]);
	continuousWay[strand-1].lineTo(xPosNext[strand],yPos+strandStep);
	continuousWay[strand].lineTo(xPosNext[strand-1],yPos+strandStep);
	permuteContinuousWay(strand);
    }
   
    public void drawNegativeCrossing(Graphics2D g,int strand,float yPos){
	// --------------------------
	//| Draw a negative crossing |
	// --------------------------
	float inter[]=calculIntersection(strand,yPos,-1);
	continuousWay[strand].lineTo(inter[1],inter[2]);
	closeContinuousWay(g,strand+1);	
	continuousWay[strand].moveTo(inter[0],inter[3]);
	continuousWay[strand].lineTo(xPosNext[strand-1],yPos+strandStep);
	continuousWay[strand-1].lineTo(xPosNext[strand],yPos+strandStep);
	permuteContinuousWay(strand);
    }

    public void draw(Graphics g){
	// --------------------------------------------
	//| The main and first called drawing function |
	// --------------------------------------------
	Graphics2D g2=(Graphics2D) g;                                                            //A Graphics2D context is needed
       	Rectangle r;                         
	int generator;                                                                           //The absolute value of generator
	int exponent;                                                                            //The sign of generator 1 or -1
	float yPos;
	//The current y position
	r=getBounds();                                                                           //The dimension of the graphics window
	g.setColor(Color.darkGray);                                                              //Background color       
        g.clearRect(0,0,r.width,r.height);                                                       //Clear the drawing window 
        g.drawRect(0,0,r.width-1,r.height-1);                                                    //Aply background color
	
	//Defintion of some strand parameter
	strandStep=(float)min((double)r.width/(double)(strandNumber+1),(double)r.height/((double)(braid.length())));
	strandWidth=strandStep/(float)5;
	strandLeft=((float)r.width-((float)(strandNumber+1)*(float)strandStep+strandWidth))/(float)2;
	yUp=(float)max((double)0,(double)((double)r.height-(double)strandStep*((double)(braid.length())))/(double)2);
	
	braid.initCurrent();                                                                     //Set current as braid.first  
	initStrandColor();                                                                       //Init the strand color
	initContinuousWay();                                                                     //Init then continuous way
	g2.setStroke(new BasicStroke(strandWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL)); //Type of general path
	yPos=yUp;                                                                                //Init current y position
	int stop=0;                                                                              //stop is a flag for break the main while
	while(stop==0){
	    generator=braid.value();                                                             //Get braid value
	    exponent=1;
	    if(generator<0){                                                                     // \
		exponent=-1;                                                                     //  Computation of generator and exponent
		generator=-generator;                                                            // /
	    }
	    calcXPosNext();
	    for(int strand=1;strand<strandNumber+1;strand++){
		if(strand==generator && exponent==1){                                        //If the generator is positive        
		    drawPositiveCrossing(g2,strand,yPos);
		    strand++;
		}
		else if(strand==generator && exponent==-1){                                  //If the generato is negative 
		    drawNegativeCrossing(g2,strand,yPos);
		    strand++;
		}
		else{
		    drawNoCrossing(g2,strand,yPos);
		}
	    }
	    yPos+=strandStep;                                                                //Update yPos
	    if(braid.isEnd()==1){                                                                //Are we at the end of the braid ?
		stop=1;
	    }
	    else{
		braid.shift();
	    }
	    updateXPos();                                                                        //Update xPos
	}                                                                                        //Restart with the new generator
	for(int strand=1;strand<strandNumber+1;strand++){                                         
	    closeContinuousWay(g2,strand);                                                       //Close the continuous way
	}
    }

    // ===================
    //I                   I
    //I Graphics function I
    //I                   I
    // ===================

    public void paint(Graphics g){
	if(isDraw==0){
	    update(g);
	    isDraw=1;
	}
	else{
	    g.drawImage(offScreenBuffer,0,0,this);
	}
    }
   
    public void update(Graphics g) {
	// -----------------------------------------------
	//| Graphic update function with double-buffering |
	// -----------------------------------------------
	if(braidWordSet==1){
	    Graphics gr; 
	    if (offScreenBuffer==null||(!(offScreenBuffer.getWidth(this)==getBounds().width && offScreenBuffer.getHeight(this)==getBounds().height))){
		offScreenBuffer=this.createImage(getBounds().width,getBounds().height);
	    }
	    gr=offScreenBuffer.getGraphics();
	    draw(gr); 
	    g.drawImage(offScreenBuffer,0,0,this);
	}
   }
}




