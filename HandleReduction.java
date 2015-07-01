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
    
    public boolean isEnd(){
	return current==last;
    }

    public void shift(){
	// ---------------------------------------
	//| Change the current node with the next |
	// ---------------------------------------
	current=current.getNext();
    }

    public void addFirst(int value){
	// ----------------------------------------------------
	//| Add a Node of value 'value' at the end of the list |
	// ----------------------------------------------------
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
 
public class HandleReduction extends JApplet implements ActionListener{
    //*******************************************************
    //*                                                     *
    //* This class represent the window of the application. *
    //*                                                     *
    //*******************************************************


    public static final long serialVersionUID = 0;
    private Panel panel;           //Will draw graphics on the panel
    private JTextField textFieldDraw;
    private JTextField textFieldReduce;
    private JTextField textFieldCompare1;
    private JTextField textFieldCompare2; 
    private JButton draw; 
    private JButton reduce;
    private JButton compare;       
    private JButton option;
    private JButton startDraw;
    private JButton startReduce;    
    private JButton startCompare;
    private JButton applyOption;
    private JButton cancelDraw;
    private JButton cancelReduce;
    private JButton cancelCompare;
    private JButton cancelOption;
    private JFrame drawFrame;
    private JFrame reduceFrame;
    private JFrame compareFrame;
    private JFrame optionFrame;
    private JScrollBar delay;
    private JLabel speed;
    private JRadioButton continuous;
    private JRadioButton stepbystep;
    public JLabel currentWord;
    public int speedValue=100;
    public boolean continuousAnimation=true;
    public int speedValue2;
    public boolean continuousAnimation2;

	    
    public void init(){
	panel=new Panel();
	panel.setBackground(Color.darkGray);
	panel.setBounds(0,20,480,600);
	add(panel);
	currentWord=new JLabel("No braid word");
	currentWord.setBounds(0,0,480,20);
	add(currentWord);
	draw=new JButton("Draw");
	draw.addActionListener(this);
	draw.setBounds(0,620,120,20);
	add(draw);
	reduce=new JButton("Reduce");       
	reduce.addActionListener(this);     
	reduce.setBounds(120,620,120,20);
	add(reduce);            
	compare=new JButton("Compare");
	compare.addActionListener(this);
	compare.setBounds(240,620,120,20);
	add(compare);
	option=new JButton("Controls");
	option.addActionListener(this);
	option.setBounds(360,620,120,20);
	add(option);
	add(new JLabel(""));
    }

    public void start(){
	update(getGraphics());
	validate();
    }
	
    public void lock(){
	draw.setEnabled(false);
	reduce.setEnabled(false);
	compare.setEnabled(false);
	option.setEnabled(false);
    }

    public void unlock(){
	draw.setEnabled(true);
	reduce.setEnabled(true);
	compare.setEnabled(true);
	option.setEnabled(true);
    }


    public void actionPerformed(ActionEvent event){
	// ----------------------------------------------------------
	//| Specify what the action on the element of the windows do |
	// ----------------------------------------------------------

	//===> Draw
	If(event.getSource()==draw){
	    drawFrame=new JFrame("Draw");
	    drawFrame.setSize(300,105);
	    drawFrame.setLocationRelativeTo(null);
	    drawFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	    
	    drawFrame.setResizable(false);
	    lock();	
	    JLabel message=new JLabel("Enter a braid word : ");
	    message.setBounds(5,5,290,20);
	    textFieldDraw=new JTextField(10);
	    textFieldDraw.setBounds(5,25,290,20);
	    textFieldDraw.addActionListener(this);
	    startDraw=new JButton("Draw");
	    startDraw.addActionListener(this);
	    startDraw.setBounds(20,55,120,20);
	    cancelDraw=new JButton("Cancel");
	    cancelDraw.addActionListener(this);
	    cancelDraw.setBounds(160,55,120,20);
	    drawFrame.add(message);
	    drawFrame.add(textFieldDraw);
	    drawFrame.add(startDraw);
	    drawFrame.add(cancelDraw);
	    drawFrame.add(new JLabel(""));
	    drawFrame.setVisible(true);
        }
	else if(event.getSource()==startDraw){
	    panel.init(this,speedValue,continuousAnimation);
	    panel.writeBraidWord(textFieldDraw.getText());
	    unlock();
	    drawFrame.dispose();
	}
	else if(event.getSource()==cancelDraw){
	    unlock();
	    drawFrame.dispose();	    
	}

	//===> Reduce
	else if(event.getSource()==reduce){
	   	reduceFrame=new JFrame("Reduce");
		reduceFrame.setSize(300,105);
		reduceFrame.setLocationRelativeTo(null);
		reduceFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		reduceFrame.setResizable(false);	    
		lock();
		JLabel message=new JLabel("Enter a braid word : ");
		message.setBounds(5,5,290,20);
		textFieldReduce=new JTextField(10);
		textFieldReduce.setBounds(5,25,290,20);
		textFieldReduce.addActionListener(this);
		startReduce=new JButton("Reduce");
		startReduce.addActionListener(this);
		startReduce.setBounds(20,55,120,20);
		cancelReduce=new JButton("Cancel");
		cancelReduce.addActionListener(this);
		cancelReduce.setBounds(160,55,120,20);
		reduceFrame.add(message);
		reduceFrame.add(textFieldReduce);
		reduceFrame.add(startReduce);
		reduceFrame.add(cancelReduce);
		reduceFrame.add(new JLabel(""));
		reduceFrame.setVisible(true);
	}
	else if(event.getSource()==startReduce){
	    textFieldReduce.setEnabled(false);
	    if(!panel.working){
		panel.init(this,speedValue,continuousAnimation);
		panel.writeBraidWord(textFieldReduce.getText());
		panel.working=true;
		startReduce.setText("Continue");
		startReduce.updateUI();
	    }
	    startReduce.setEnabled(false);
	    boolean red=panel.reduce();
	    String message;
	    int ind=panel.indice();
	    if(red){
		if(ind==0){
		    message="The final word is empty, so your initial braid word \n"+textFieldReduce.getText()+" is trivial.";
		}
		else{
		    message="There is no more handle and the final word is nonempty, so your initial braid word\n "+textFieldReduce.getText()+" is not trivial.";
		}
		JOptionPane.showMessageDialog(null,message);
		unlock();
		panel.working=false;
		reduceFrame.dispose();
	    }
	    else{
		startReduce.setEnabled(true);
	    }
	}
	else if(event.getSource()==cancelReduce){
	    reduceFrame.dispose();
	    unlock();
	    panel.working=false;
	}
	
	//===> Compare
	else if(event.getSource()==compare){
	    compareFrame=new JFrame("Compare");
	    compareFrame.setSize(300,145);
	    compareFrame.setLocationRelativeTo(null);
	    compareFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    compareFrame.setResizable(false);
	    lock();
	    JLabel message1=new JLabel("Enter a first braid word : ");
	    message1.setBounds(5,5,290,20);
	    textFieldCompare1=new JTextField(10);
	    textFieldCompare1.setBounds(5,25,290,20);
	    JLabel message2=new JLabel("Enter a second braid word : ");
	    message2.setBounds(5,45,290,20);
	    textFieldCompare2=new JTextField(10);
	    textFieldCompare2.setBounds(5,65,290,20);
	    startCompare=new JButton("Compare");
	    startCompare.addActionListener(this);
	    startCompare.setBounds(20,95,120,20);
	    cancelCompare=new JButton("Cancel");
	    cancelCompare.addActionListener(this);
	    cancelCompare.setBounds(160,95,120,20);
	    compareFrame.add(message1);
	    compareFrame.add(textFieldCompare1);
	    compareFrame.add(message2);
	    compareFrame.add(textFieldCompare2);
	    compareFrame.add(startCompare);	    
	    compareFrame.add(cancelCompare);
	    compareFrame.add(new JLabel(""));
	    compareFrame.setVisible(true);
	}
	else if(event.getSource()==startCompare){
	    if(!panel.working){
		String word1,word2;	    
		word1=textFieldCompare1.getText();
		word2=textFieldCompare2.getText();
		textFieldCompare1.setEnabled(false);
		textFieldCompare2.setEnabled(false);
		panel.init(this,speedValue,continuousAnimation);
		panel.compare(word1,word2);
		startCompare.setText("Continue");
		startCompare.updateUI();
	    }
	    boolean red;
	    startCompare.setEnabled(false);
	    red=panel.reduce();
	    if(red){
		String message;
		int ind=panel.indice();
		if(ind==0){
		    message="The final word is empty,\nso your initial braid words "+textFieldCompare1.getText()+" and "+textFieldCompare2.getText()+" are equivalent.";
		}
		else if(ind>0){
		    //smaller
		    message="There is no more handle and the final word is nonempty,\nso your initial braid words "+textFieldCompare1.getText()+" and "+textFieldCompare2.getText()+" are not equivalent.";
		}
		else{
		    //larger
		    message="There is no more handle and the final word is nonempty,\nso your initial braid words "+textFieldCompare1.getText()+" and "+textFieldCompare2.getText()+" are not equivalent.";
		}
		JOptionPane.showMessageDialog(null,message);
		compareFrame.dispose();
		panel.working=false;
		unlock();
	    }
	    else{
		startCompare.setEnabled(true);
	    }	
	}
	else if(event.getSource()==cancelCompare){
	    compareFrame.dispose();
	    unlock();
	    panel.working=false;
	}

	//===> Option
	else if(event.getSource()==option){
	    optionFrame=new JFrame("Controls");
	    optionFrame.setSize(300,110);
	    optionFrame.setLocationRelativeTo(null);
	    optionFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    optionFrame.setResizable(false);
	    lock();
	    continuous=new JRadioButton();
	    stepbystep=new JRadioButton();
	    ButtonGroup group = new ButtonGroup();
	    continuous.setText("Continuous");
	    stepbystep.setText("Step-by-step");
	    continuous.addActionListener(this);
	    stepbystep.addActionListener(this);
	    continuousAnimation2=continuousAnimation;
	    speedValue2=speedValue;
	    if(continuousAnimation){
		continuous.setSelected(true);
	    }
	    else{
		stepbystep.setSelected(true);
	    }
	    group.add(continuous);
	    group.add(stepbystep);
	    JLabel label1=new JLabel("Mode :");
	    delay=new JScrollBar(JScrollBar.HORIZONTAL,speedValue,0,0,100);
	    delay.addAdjustmentListener(new java.awt.event.AdjustmentListener(){
		    public void adjustmentValueChanged(AdjustmentEvent event){
			scrollBarValueChanged(event);
		    }
		});
	    speed=new JLabel("Speed : "+speedValue);
  	    label1.setBounds(5,5,60,20);
	    continuous.setBounds(65,5,100,20);
	    stepbystep.setBounds(165,5,120,20);
	    speed.setBounds(5,30,80,20);
	    delay.setBounds(85,30,200,20);
	    applyOption=new JButton("Apply");
	    applyOption.addActionListener(this);
	    applyOption.setBounds(20,60,120,20);
	    cancelOption=new JButton("Cancel");
	    cancelOption.addActionListener(this);
	    cancelOption.setBounds(160,60,120,20);
	    optionFrame.add(label1);
	    optionFrame.add(continuous);
	    optionFrame.add(stepbystep);
	    optionFrame.add(speed);	  
	    optionFrame.add(delay);
	    optionFrame.add(applyOption);
	    optionFrame.add(cancelOption);
	    optionFrame.add(new JLabel(""));
	    optionFrame.setVisible(true);
	}
	else if(event.getSource()==continuous){
	    if(continuous.isSelected()){
		continuousAnimation2=true;
	    }
	}
	else if(event.getSource()==stepbystep){
	    if(stepbystep.isSelected()){
		continuousAnimation2=false;
	    }
	}
	else if(event.getSource()==applyOption){
	    continuousAnimation=continuousAnimation2;
	    speedValue=speedValue2;
	    unlock();
	    optionFrame.dispose();
	}
	else if(event.getSource()==cancelOption){
	    optionFrame.dispose();
	    unlock();
	}
    }

    public void scrollBarValueChanged(AdjustmentEvent event){
	if(event.getSource()==delay){
	    speedValue2=delay.getValue();
	    speed.setText("Speed : "+speedValue2);
	}
    }
    
    public String getAppletInfo() {
        return "Handle reduction algorithm.";
    }
}
    

class Pause extends Thread{
    //*********************************
    //*                               *
    //* This class pause the program. *
    //*                               *
    //*********************************

    public void pause(int delayValue){
	try {
	    sleep(delayValue);
	}
	catch (InterruptedException ex) {}
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

    //Word variable
    private HandleReduction myWindow;
    private JLabel currentBraidWord;

    //=====> Braid variable
    private String braidWord;         //The initial braid word
    private boolean braidWordSet=false;
    private List braid;                     //The braid word as a list
    public String currentWord="";
    public boolean working=false;
    private String compareWord1;
    private String compareWord2;
    

    //=====> Strand variable
    private int strandNumber=5;             //The number of strand
    private float strandStep;               //The distance between two strand
    private float strandWidth;              //The width of a strand
    private float strandLeft;               //The x position of the leftmost strand
    private float[] xPos;                   //The x postion of strands
    private float[] xPosNext;               //The next x position of strands 
    private float yUp;                      //The y position of the first element of the braid
    
    //=====> Handle variable
    private Node handleBegin;                //The first node of the handle
    private Node handleEnd;                  //The last node of the handle
    private int handleStrand;                //The current strand of the handle
    private int handleType;                  //The type of the handle, is 1 if the handle start with a positive element
    private boolean handleNormalDraw;        //This variable is true if we draw the handle with the classic metod
    private boolean onHandle;                //This variable is true if we are in between the handleBegin Node and the handleEnd node
    private float handleBeginYPos;           //The y position of the begin of the handle
    private float handleEndYPos;             //The y position of the end of the handle
    
    //======> Trivial variable
    private int trivialNumber;               //The number of trivial element without the extremity
    private float trivialHeight;             //The height of a trivial element
    
    //======> Animation variable
    private int animationStep;               //The current animation step
    private float parameterAnimationStep2;   // \
    private float parameterAnimationStep4;   //  | Parameters for the animation 
    private float parameterAnimationStep5;   // /
    private boolean continuousAnimation;
    private int delayValue; 
    private Pause pause=new Pause();         //For make pause during animation   

    //======> Color variable
    private Color[] strandColor;             //The color of different strand
    private Color handleColor=Color.black;   //The color of the handle
    private Color originalHandleStrandColor; //The original color of the handle strand
      
    //======> Way variable 
    private GeneralPath[] continuousWay;     //The currant continuous way of strands
    
    //======> Double Buffering variable   
    private Image offScreenBuffer;           //A buffer in wich we draw and after drawing we will show this buffer
    private boolean isDraw;                  //For not repeat drawing
 

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
    
    public void init(HandleReduction _myWindow,int _delayValue,boolean _continuousAnimation){
	myWindow=_myWindow;
	delayValue=100-_delayValue;
	continuousAnimation=_continuousAnimation;
    }

    public void writeBraidWord(String buffer){
	// -----------------------------------------------------
	//| Initialise the braid word and some other parameters |
	//------------------------------------------------------
	braidWord=buffer;
	initBraid();                              //initialise the braid list
	animationStep=0;                          //initialise the animation step
	trivialNumber=0;                          // \
	trivialHeight=0;                          //  Initilise the trivial parameters
	handleNormalDraw=true;                    //we draw the handle with the classic method for the moment
	updateCurrentBraidWord();
	braidWordSet=true;
	isDraw=false;                             //we draw a new image
	update(super.getGraphics());              //update draw
    }

    public void compare(String word1, String word2){
	int length;
	int value;
	char letter;
	compareWord1=word1;
	compareWord2=word2;
	strandNumber=2;
	value=0;
	braid=new List();
	length=word2.length();                 //get the length of the braid word          
	for(int i=0;i<length;i++){
	    letter=word2.charAt(i);
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
		value=24;
	    }
	    braid.addLast(value);                  //add the value at the list of the braid
	}
	braid.addLast(0);
	length=word1.length();                  
	for(int i=0;i<length;i++){
	    letter=word1.charAt(i);
	    if(letter>='a' && letter<='z'){       
		value=-(int)(letter-'a')-1;        
		if(-value+1>strandNumber){        
		    strandNumber=-value+1;     
		}
	    }
	    else if(letter>='A' && letter<='Z'){ 
		value=(int)(letter-'A')+1;         
		if(value+1>strandNumber){       
		    strandNumber=value+1;
		}
	    }
	    else{
		value=24;
	    }
	    braid.addFirst(value);                  
	}
	braid.addFirst(0);
	animationStep=0;                          
	trivialNumber=0;                          
	trivialHeight=0;                         
	handleNormalDraw=true;                      
	updateCurrentBraidWord();
	braidWordSet=true;
	isDraw=false;                             //we draw a new image
	working=true;
	update(super.getGraphics());              //update draw
    }                          

    public void updateCurrentBraidWord(){
	boolean stop;
	int value;
	String res="";
	braid.initCurrent();
	stop=false;
	while(!stop){
	    value=braid.value();
	    if(value>0){
		res+=(char)((value-1)+'a');
	    }
	    else if(value<0){
		res+=(char)((-value-1)+'A');
	    }
	    if(braid.isEnd()){
		stop=true;
	    }
	    if(!stop){
		braid.shift();
	    }
	}
	if(res==""){
	    res="1";
	}
	currentWord=res;
	(myWindow.currentWord).setText("Current braid word: "+res);
	myWindow.update(myWindow.getGraphics());
	myWindow.validate();
    }


    // ===============================
    //I                               I
    //I  Handle reduction algorithms  I
    //I                               I
    // ===============================

    public void findHandle(){
	// -----------------------------------
	//| Search a handle in the braid word | 
	//| with no intricated handle         |
	// -----------------------------------
	Node handles[]=new Node[strandNumber];     //We stock information of potential handle
	int signs[]=new int[strandNumber];         //We stock the sign of the last n generato in signs[n];
	int sign,value;
	boolean stop;                                  //A flag for stop the main while
	for(int i=0;i<strandNumber;i++){
	    signs[i]=0;
	}
	stop=false;
	value=0;
	sign=0;
	braid.initCurrent();                       //Initiliastion of braid current
	while(!stop){
	    value=braid.value();
	    sign=1;
	    if(value<0){                           //We compte the sign
		value=-value;
		sign=-1;
	    }
	    else if(value==0){                
		sign=0;
	    }
	    if(signs[value]*sign<0){               //We have found the first handle with no imbricated handle
		stop=true;
	    }
	    else{
		signs[value]=sign;                 //Update signs
		handles[value]=braid.getCurrent(); //Update value
		for(int j=1;j<value;j++){          //Clear the potentialy imbricated handle
		    signs[j]=0;
		}
	    }
	    if(braid.isEnd()){                  //End of the braid ?
		stop=true;
	    }
	    if(!stop){                           //Restart with the ext generator 
		braid.shift(); 
	    }
	}
	if(signs[value]*sign<0){                   //If we have found a handle
	    handleBegin=handles[value];
	    handleEnd=braid.getCurrent();
	    handleStrand=value;
	    handleType=-sign;
	}
	else{
	    handleStrand=0;                        //If we haven't found handle
	}
    }

    private void insertTrivials(){
	// -----------------------------------------------------
	//| This fonction insert trivials generato in the braid |
        //| in order to make some space for the animation.      |
	// -----------------------------------------------------
	int value;
	boolean stop;
	stop=false;
	braid.setCurrent(handleBegin);               //We start at the begin of the handle
	trivialNumber=0;
	while(!stop){
	    value=braid.value();
	    if(Math.abs(value)==handleStrand-1){     //If the generator is handleStrand-1 or -(handleStrand-1)
		braid.addBefore(0);                  //We add a trivial before 
		braid.setCurrent(braid.addAfter(0)); //We add a trivial after
		trivialNumber+=2;                    //Update the number of trivials
	    }
	    braid.shift();
	    if(braid.getCurrent()==handleEnd){       //If we are at the end of the handlee
		stop=true;                           //we stop the main while
	    }
	}
    }

    public void removeHandle(){
	// -------------------------------------------
	//| This function remove the handle was found |
	// -------------------------------------------
	int sign;
	int value;
	boolean stop;
	sign=-handleType;                                              //sign is a variable for alternating sign of new handleStrand generator
	stop=false;
	braid.setCurrent(handleBegin);                                 //We strat at the begin of the handle
	while(!stop){
	    value=braid.value();
	    if(Math.abs(value)==handleStrand){                         //If the generator is handleStrand or -handleStrand
		(braid.getCurrent()).setValue(0);                      //we replace it with a trivial generator
	    }
	    if(value==0){
		(braid.getCurrent()).setValue((handleStrand-1)*sign);  //If the generator is a trivial we replace it
		sign=-sign;                                            //with (handleStrand-1)*sign
	    }
	    if(value==handleStrand-1){                                 //If the genertaor is handleStrand-1
		(braid.getCurrent()).setValue(value+1);                //we replace it with handleStrand
	    }
	    if(value==-(handleStrand-1)){                             //If the generator is -(handleStand-1)
		(braid.getCurrent()).setValue(value-1);               //we replace it with -handleStrand
	    }
	    if(braid.getCurrent()==handleEnd){                        //If we are at he end of te endle
		stop=true;                                               //we stop the main while
	    }
	    else{
		braid.shift();                                        //else we restart with the next generator
	    }
	}
	trivialNumber=2;                                              //At the end trivial numbers must be 2
    }

    private void removeTrivials(){
	// -------------------------------------------
	//| Remove the not extreme trivials generator | 
	// -------------------------------------------
	braid.remove(handleBegin);
	braid.remove(handleEnd);
	trivialNumber=0;
    }

    public int indice(){
	int value;
	int indice=0;
	boolean stop=false;
	braid.initCurrent();
	while(!stop){
	    value=braid.value();
	    if(Math.abs(value)>Math.abs(indice)){
		indice=value;
	    }
	    if(braid.isEnd()){
		stop=true;  
	    }
	    else{        
		braid.shift(); 
	    }
	}
	return indice;
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

    // ==================
    //I                  I
    //I Handle functions I
    //I                  I
    // ==================

    public void colorizeHandle(Graphics2D g,float yPos){
	// --------------------------------------
	//| Colorize the handle if we are on her | 
	// --------------------------------------
	if(handleStrand!=0){                                                     //We verify we have found a handle
	    if(braid.getCurrent()==handleBegin){                                 //If we are at the begin of the handle
		handleBeginYPos=yPos;                                            //Initialize the y position of the begin of the handle
		onHandle=true;                                                      //We are on the handle
		closeContinuousWay(g,handleStrand);                              //Close the conitnuous way of handleStrand
		originalHandleStrandColor=strandColor[handleStrand-1];           //Save the original color of the strand handleStrand
		strandColor[handleStrand-1]=handleColor;                         //Change color of the strand handleStrand
		continuousWay[handleStrand-1].moveTo(xPos[handleStrand-1],yPos); //Start position of the new continuous way of handleStrand
	    }
	    else if(braid.getCurrent()==handleEnd.getNext()){                    //If we are at the end of the handle
		handleEndYPos=yPos;                                              //Initailaize the y position of the end of the handle
		closeContinuousWay(g,handleStrand);                              //Close the continuous way of handleStrand
		strandColor[handleStrand-1]=originalHandleStrandColor;           //The original color of strand handleStrand
		continuousWay[handleStrand-1].moveTo(xPos[handleStrand-1],yPos); //Start position of the new continuous way
		onHandle=false;                                                  //We are no longer in the handle
	    }
	}
    }

    public void changeHandleStrand(int generator){
	// ------------------------------------------------------
	//| Update the value of the strand on wich the handle is |
	// ------------------------------------------------------
	if(handleStrand!=0 && onHandle){
	    if(generator==handleStrand){
		handleStrand++;
	    }
	    else if(generator+1==handleStrand){
		handleStrand--;
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
	if(strand!=handleStrand || handleNormalDraw || strandColor[handleStrand-1]!=handleColor){ 
	    //We verify we must draw the continuous way or not
	    //We don't draw this way if we are on the handle and the value of handleNormalDraw is 0
	    g.draw(gp);
	}
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
	if(braid.getCurrent()==handleBegin){                                                               //If we have at the begin of the handle
	    if(animationStep==2){                                                                          //If the step of animation is 2 
		xPosNext[handleStrand]=(strandLeft+(handleStrand+parameterAnimationStep2)*strandStep);
	    }
	    else if(animationStep==4){                                                                     //If the step ofanimation is 4
		xPosNext[handleStrand-1]+=parameterAnimationStep4*(float)strandStep;
	    }
	}
	else if(braid.getCurrent()==handleEnd){                                                            //If we are at the end of the handle
	}
	else if(onHandle){                                                                              //If we are on the interior of the handle
	    if(animationStep==2){                                                                          //If the step of animation is 2
		xPosNext[handleStrand-1]=(strandLeft+(handleStrand+parameterAnimationStep2-1)*strandStep);
	    }
	    else if(animationStep==4){                                                                     //If the step of animation is 4
		xPosNext[handleStrand-2]+=parameterAnimationStep4*(float)strandStep;
		if(Math.abs(((braid.getCurrent()).getNext()).getValue())==handleStrand-2 ||
		   Math.abs((braid.getCurrent()).getValue())==handleStrand-2){
		    xPosNext[handleStrand-3]+=parameterAnimationStep4*(float)strandStep;
		}
	    }
	    else if(animationStep==5){                                                                    //If the step of animation is 5
		if(Math.abs((braid.getCurrent()).getValue())==handleStrand-1){		  
		    xPosNext[handleStrand-2]+=parameterAnimationStep5*(float)strandStep;
		}
		else if(Math.abs((braid.getCurrent()).getValue())==handleStrand+1){
		    xPosNext[handleStrand-1]+=parameterAnimationStep5*(float)strandStep;
		}   
	    }
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
	if(!handleNormalDraw && braid.getCurrent()==handleBegin){
	    //If we are at the begin of handle and then handleNormal draw is 0 we send special values
	    res[0]=xPosNext[strand-1];
	    res[1]=xPosNext[strand-1];
	    res[2]=yPos+strandStep;
	    res[3]=yPos+strandStep;
	    return res;
	}
	else if(!handleNormalDraw && braid.getCurrent()==handleEnd){
	    //If we are at the end of handle and then handleNormal draw is 0 we send special value
	    res[0]=xPosNext[strand];
	    res[1]=xPosNext[strand];
	    res[2]=yPos+strandStep;
	    res[3]=yPos+strandStep;
	    return res;
	}
	else{
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
    }

    // ===================
    //I                   I
    //I Drawing functions I
    //I                   I
    // ===================

    public void drawHandle(Graphics2D g){
	// -------------------------------------------
	//| Draw the handle if handleNormal draw is 0 |
	// -------------------------------------------
	GeneralPath gp=new GeneralPath();
	gp.moveTo(strandLeft+handleStrand*strandStep,handleBeginYPos); 
	gp.lineTo(strandLeft+handleStrand*strandStep,handleEndYPos);
	g.setColor(handleColor);                                         //Apply the color of the handle
	g.draw(gp);
    }
    
    public void drawTrivial(float yPos){
	// -------------------------------------
	//| Draw a not extrem trivial generator |
	// -------------------------------------
	for(int strand=0;strand<strandNumber;strand++){
	    continuousWay[strand].lineTo(xPosNext[strand],yPos+trivialHeight*strandStep);
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
	float yPos;                                                                              //The current y position
	r=getBounds();                                                                           //The dimension of the graphics window
	g.setColor(Color.darkGray);                                                              //Background color       
        g.clearRect(0,0,r.width,r.height);                                                       //Clear the drawing window 
        g.drawRect(0,0,r.width-1,r.height-1);                                                    //Aply background color
	
	//Defintion of some strand parameter
	strandStep=(float)min((double)r.width/(double)(strandNumber+1),(double)r.height/((double)(braid.length())-((double)1-(double)trivialHeight)*(double)trivialNumber));
	strandWidth=strandStep/(float)5;
	strandLeft=((float)r.width-((float)(strandNumber+1)*(float)strandStep+strandWidth))/(float)2;
	yUp=(float)max((double)0,(double)((double)r.height-(double)strandStep*((double)(braid.length())-((double)1-(double)trivialHeight)*(double)trivialNumber))/(double)2);
	
	braid.initCurrent();                                                                     //Set current as braid.first  
	initStrandColor();                                                                       //Init the strand color
	initContinuousWay();                                                                     //Init then continuous way
	g2.setStroke(new BasicStroke(strandWidth,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL)); //Type of general path
	yPos=yUp;                                                                                //Init current y position
	int stop=0;                                                                              //stop is a flag for break the main while
	if(!handleNormalDraw && handleType==1){
	    //If handleNormalDraw is 0 and hande type is 1 we draw the handle first
	    drawHandle(g2);
	}
	while(stop==0){
	    generator=braid.value();                                                             //Get braid value
	    exponent=1;
	    if(generator<0){                                                                     // \
		exponent=-1;                                                                     //  Computation of generato and exponent
		generator=-generator;                                                            // /
	    }
	    colorizeHandle(g2,yPos);
	    calcXPosNext();
	    if(onHandle && generator==0){                                                     //If the generator is trivial and we are on the handle
		drawTrivial(yPos);
		yPos+=trivialHeight*strandStep;                                                  //Update yPos
	    }
	    else{
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
		changeHandleStrand(generator);                                                   //Update handle strand
		yPos+=strandStep;                                                                //Update yPos
	    }
	    if(braid.isEnd()){                                                                //Are we at the end of the braid ?
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
	if(!handleNormalDraw && handleType==-1){
	    //If handleNormalDraw is 0 and hande type is 1 we draw the handle last
	    drawHandle(g2);
	}
    }

    // ===================
    //I                   I
    //I Graphics function I
    //I                   I
    // ===================

    public void paint(Graphics g){
	if(!isDraw){
	    update(g);
	    isDraw=true;
	}
	else{
	    g.drawImage(offScreenBuffer,0,0,this);
	}
    }
   
    public void update(Graphics g) {
	// -----------------------------------------------
	//| Graphic update function with double-buffering |
	// -----------------------------------------------
	if(braidWordSet){
	    Graphics gr; 
	    if (offScreenBuffer==null||(!(offScreenBuffer.getWidth(this)==getBounds().width && offScreenBuffer.getHeight(this)==getBounds().height))){
		offScreenBuffer=this.createImage(getBounds().width,getBounds().height);
	    }
	    gr=offScreenBuffer.getGraphics();
	    draw(gr); 
	    g.drawImage(offScreenBuffer,0,0,this);
	}
   }

    // ====================
    //I                    I
    //I Animation function I
    //I                    I
    // ====================

    public boolean reduce(){
	if(!braidWordSet){
	    JOptionPane.showMessageDialog(null,"You must enter a braid word first ! ","Error",JOptionPane.ERROR_MESSAGE);
	    return true;
	}
	else if(animationStep==0){
	    findHandle();
	    update(getGraphics());
	    if(handleStrand==0){
		//update(getGraphics());
		return true;
	    }
	    else{
		update(getGraphics());
		insertTrivials();
		trivialHeight=0;
		animationStep=1;
		handleNormalDraw=true;
		if(continuousAnimation){
		    return reduce();
		}
		else{
		    return false;
		}
	    }
	}
	else if(animationStep==1){
	    if(trivialHeight>=1){
		trivialHeight=1;
		parameterAnimationStep2=1;	    
		animationStep=2;
		pause.pause(delayValue);
		return reduce();
	    }
	    else{
		trivialHeight+=0.1;
		update(getGraphics());
		pause.pause(delayValue);
		return reduce();
	    }
	}
	else if(animationStep==2){
	    if(handleStrand==0){
		return true;
	    }
	    else if(parameterAnimationStep2<(float)(1.25)*((float)strandWidth/strandStep)){
		handleNormalDraw=false;
		animationStep=3;
		pause.pause(delayValue);
		return reduce();
	    }
	    else{
		parameterAnimationStep2-=(float)0.05;
		update(getGraphics());	
		pause.pause(delayValue);
		return reduce();
	    }
	}
	else if(animationStep==3){
	    parameterAnimationStep4=0;
	    animationStep=4;
	    update(getGraphics());
	    pause.pause(delayValue);
	    return reduce();
	}
	else if(animationStep==4){
	    if(parameterAnimationStep4>=1){
		removeHandle();
		handleNormalDraw=true;
		parameterAnimationStep5=1-(float)(1.25)*((float)strandWidth/strandStep);
		animationStep=5;
		pause.pause(delayValue);
		return reduce();
	    }
	    else{
		parameterAnimationStep4+=(float)0.05;
		update(getGraphics());	
		pause.pause(delayValue);
		return reduce();
	    }
	}
	else if(animationStep==5){
	    if(parameterAnimationStep5<=(float)0.05){
		parameterAnimationStep5=0;
		animationStep=6;
		pause.pause(delayValue);
		return reduce();
	    }
	    else{
		parameterAnimationStep5-=(float)0.05;
		update(getGraphics());	
		pause.pause(delayValue);
		return reduce();
	    }
	}
	else if(animationStep==6){
	    if(trivialHeight<=0){
		trivialHeight=0;
		animationStep=7;
		pause.pause(delayValue);
		update(getGraphics());
		return reduce();
	    }
	    else{
		trivialHeight-=0.1;
		update(getGraphics());	
		pause.pause(delayValue);
		return reduce();
	    }
	}
	else if(animationStep==7){
	    removeTrivials();
	    handleStrand=0;
	    update(getGraphics());	
	    animationStep=0;
	    updateCurrentBraidWord();
	    update(getGraphics());	
	    pause.pause(delayValue);
	    if(continuousAnimation){
		return reduce();
	    }
	    else{
		return false;
	    }
	}
	return false;
    }
}




