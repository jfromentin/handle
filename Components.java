/* File : Components.java
 * Program : Handle Reduction Animation - Applet 
 * By Jean Fromentin <jean.froment@gmail.com>
 * Copyright 2000 Jean Fromentin
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
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import javax.swing.Action;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.ButtonGroup;
import javax.swing.Timer;


public class Components extends JPanel{
    private HandleReductionPanel handleReductionPanel;
    private JButton draw,compare,reduce,controls,controlsApply,controlsCancel;
    private JRadioButton continuous,stepByStep;
    private JTextField wordField,firstWordField,secondWordField;
    private JScrollBar delayScrollBar;
    private int pauseDelay,newPauseDelay;
    private JLabel wordLabel,delayLabel;
    private JDialog controlsFrame,compareFrame,drawFrame;
    private Listener listener;
    private Timer animationTimer;
    private Boolean reducing,continuousAnimation,comparing;
    private String word,word1,word2;

    public Components(){
	handleReductionPanel=new HandleReductionPanel();
	draw=new JButton("Draw");
	compare=new JButton("Compare");
	reduce=new JButton("Reduce");
	controls=new JButton("Controls");
	wordLabel=new JLabel("Empty word");
	    
	//Layout
	setLayout(new BorderLayout());
	add(wordLabel,BorderLayout.NORTH);
	add(handleReductionPanel,BorderLayout.CENTER);
	JPanel buttonPanel=new JPanel();
       	add(buttonPanel,BorderLayout.SOUTH);
	buttonPanel.setLayout(new FlowLayout());
	buttonPanel.add(draw);
	buttonPanel.add(compare);
	buttonPanel.add(reduce);
	buttonPanel.add(controls);
	
	//Action listenner
	listener=new Listener(this);
	draw.addActionListener(listener);
	compare.addActionListener(listener);
	reduce.addActionListener(listener);
	controls.addActionListener(listener);

	//Action command
	draw.setActionCommand("draw");
	compare.setActionCommand("compare");
	reduce.setActionCommand("reduce");
	controls.setActionCommand("controls");

	//States
	reducing=false;
	comparing=false;
	continuousAnimation=true;
	reduce.setEnabled(false);
	pauseDelay=10;
	createTimer();

    }
    //Buttons state
    public void setButtonsState(Boolean drawState,Boolean compareState,Boolean reduceState,Boolean controlsState){
	draw.setEnabled(drawState);
	compare.setEnabled(compareState);
	reduce.setEnabled(reduceState);
	controls.setEnabled(controlsState);
    }

    //Actions

    public void updateWord(){
	wordLabel.setText("Current word : "+handleReductionPanel.getCurrentWord());
	wordLabel.updateUI();
    }

    public void draw(){
	drawFrame=new JDialog();
	drawFrame.setResizable(false);
	drawFrame.setTitle("Draw braid");
	
	//First word
	JLabel wordLabel=new JLabel(" Braid word : ");
	wordField=new JTextField();
	wordField.setPreferredSize(new Dimension(200, 28));

	//Buttons 
	JButton drawCancel,drawApply;
	drawCancel=new JButton("Cancel");
	drawApply=new JButton("Draw");
	
	//Action listenner
	drawCancel.addActionListener(listener);
	drawApply.addActionListener(listener);
	
	//Action command
	drawCancel.setActionCommand("drawCancel");
	drawApply.setActionCommand("drawApply");
	
	//Layout
	GridBagLayout gb=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	drawFrame.setLayout(gb);
	gbc.anchor = GridBagConstraints.EAST;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridx=0;
	gbc.gridy=0;
	gbc.gridwidth=1;
	gb.setConstraints(wordLabel,gbc);
	drawFrame.add(wordLabel);
	gbc.gridx=1;
	gbc.gridy=0;
	gbc.gridwidth=2;
	gb.setConstraints(wordField,gbc);
	drawFrame.add(wordField);
	gbc.gridx=1;
	gbc.gridy=1;
	gbc.gridwidth=1;
	gb.setConstraints(drawCancel,gbc);
	drawFrame.add(drawCancel);
	gbc.gridx=2;
	gbc.gridy=1;
	gbc.gridwidth=1;
	gb.setConstraints(drawApply,gbc);
	drawFrame.add(drawApply);
	
	drawFrame.pack();
	drawFrame.setVisible(true);

	/*JOptionPane dialog = new JOptionPane();
	String word = dialog.showInputDialog(this,"Enter the braid word to draw :");
	handleReductionPanel.writeBraidWord(word);
	updateWord();
	setButtonsState(true,true,true,true);
	reduce.setText("Reduce");
	reduce.updateUI();*/	
    }

    public void drawBraid(){
	word=wordField.getText();
	comparing=false;
	handleReductionPanel.writeBraidWord(word);
	updateWord();
	setButtonsState(true,true,true,true);
	reduce.setText("Reduce");
	reduce.updateUI();
    }

    public void closeDraw(){
	drawFrame.dispose();
    }
    public void reduce(){
	if(reducing){
	    animationTimer.stop();
	    reducing=false;
	    setButtonsState(true,true,true,true);
	    reduce.setText("Continue");
	    reduce.updateUI();
	}
	else{
	    reducing=true;
	    setButtonsState(false,false,true,false);
	    reduce.setText("Pause");
	    reduce.updateUI();
	    animationTimer.start();
	}
    }

    public void controls(){
       	controlsFrame=new JDialog();
	controlsFrame.setResizable(false);
	controlsFrame.setTitle("Controls");

	//Animation mode
	JLabel modeLabel=new JLabel("  Animation : ");
	continuous=new JRadioButton("Continuous",continuousAnimation);
	stepByStep=new JRadioButton("Step-by-step",!continuousAnimation);
	ButtonGroup group = new ButtonGroup();
	group.add(continuous);
	group.add(stepByStep);
	
	//Pause delay
	newPauseDelay=pauseDelay;
	JLabel pauseLabel=new JLabel("  Pause delay : ");
	delayLabel=new JLabel("  "+newPauseDelay+" ms ");
	delayScrollBar=new JScrollBar(JScrollBar.HORIZONTAL,newPauseDelay,1,0,100);
	delayScrollBar.setPreferredSize(new Dimension(150,20));
	//Buttons
	controlsApply=new JButton("Apply");
	controlsCancel=new JButton("Cancel");
	

	//Layout
	GridBagLayout gb=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	controlsFrame.setLayout(gb);
	gbc.anchor = GridBagConstraints.WEST;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridx=0;
	gbc.gridy=0;
	gbc.gridwidth=1;
	gb.setConstraints(pauseLabel,gbc);
	controlsFrame.add(pauseLabel);
	gbc.gridx=1;
	gbc.gridy=0;
	gbc.gridwidth=2;
	gb.setConstraints(delayScrollBar,gbc);
	controlsFrame.add(delayScrollBar);
	gbc.gridx=3;
	gbc.gridy=0;
	gbc.gridwidth=1;
	gb.setConstraints(delayLabel,gbc);
	controlsFrame.add(delayLabel);
	gbc.gridx=0;
	gbc.gridy=1;
	gbc.gridwidth=1;
	gb.setConstraints(modeLabel,gbc);
	controlsFrame.add(modeLabel);
	gbc.gridx=1;
	gbc.gridy=1;
	gbc.gridwidth=2;
	gb.setConstraints(continuous,gbc);
	controlsFrame.add(continuous);
	gbc.gridx=1;
	gbc.gridy=2;
	gbc.gridwidth=2;
	gb.setConstraints(stepByStep,gbc);
	controlsFrame.add(stepByStep);
	gbc.anchor = GridBagConstraints.EAST;

	gbc.gridx=2;
	gbc.gridy=3;
	gbc.gridwidth=1;
	gb.setConstraints(controlsCancel,gbc);
	controlsFrame.add(controlsCancel);
	gbc.gridx=3;
	gbc.gridy=3;
	gbc.gridwidth=1;
	gb.setConstraints(controlsApply,gbc);
	controlsFrame.add(controlsApply);

	//Action listenner
	controlsApply.addActionListener(listener);
	controlsCancel.addActionListener(listener);
	delayScrollBar.addAdjustmentListener(listener);
	
	//Action command
	controlsApply.setActionCommand("controlsApply");
	controlsCancel.setActionCommand("controlsCancel");
	
	//Active frame
	controlsFrame.pack();
	controlsFrame.setVisible(true);
    }
    
    public void delayAdjust(){
	newPauseDelay=delayScrollBar.getValue();
	delayLabel.setText("  "+newPauseDelay+" ms ");
	delayLabel.updateUI();
    }

    public void saveParameters(){
	continuousAnimation=continuous.isSelected();
	pauseDelay=newPauseDelay;
	animationTimer.setDelay(pauseDelay);
    }
    
    public void closeControls(){
	controlsFrame.dispose();
    }

    public void compare(){
	compareFrame=new JDialog();
	compareFrame.setResizable(false);
	compareFrame.setTitle("Compare braids");
	
	//First word
	JLabel firstWordLabel=new JLabel(" First braid word : ");
	firstWordField=new JTextField();
	firstWordField.setPreferredSize(new Dimension(200, 28));

	//Second word
	JLabel secondWordLabel=new JLabel(" Second braid word : ");
	secondWordField=new JTextField();
	secondWordField.setPreferredSize(new Dimension(200, 28));

	//Buttons 
	JButton compareCancel,compareApply;
	compareCancel=new JButton("Cancel");
	compareApply=new JButton("Compare");
	
	//Action listenner
	compareCancel.addActionListener(listener);
	compareApply.addActionListener(listener);
	
	//Action command
	compareCancel.setActionCommand("compareCancel");
	compareApply.setActionCommand("compareApply");
	
	//Layout
	GridBagLayout gb=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	compareFrame.setLayout(gb);
	gbc.anchor = GridBagConstraints.EAST;
	gbc.insets = new Insets(5, 5, 5, 5);
	gbc.gridx=0;
	gbc.gridy=0;
	gbc.gridwidth=1;
	gb.setConstraints(firstWordLabel,gbc);
	compareFrame.add(firstWordLabel);
	gbc.gridx=1;
	gbc.gridy=0;
	gbc.gridwidth=2;
	gb.setConstraints(firstWordField,gbc);
	compareFrame.add(firstWordField);
	gbc.gridx=0;
	gbc.gridy=1;
	gbc.gridwidth=1;
	gb.setConstraints(secondWordLabel,gbc);
	compareFrame.add(secondWordLabel);
	gbc.gridx=1;
	gbc.gridy=1;
	gbc.gridwidth=2;
	gb.setConstraints(secondWordField,gbc);
	compareFrame.add(secondWordField);
	gbc.gridx=1;
	gbc.gridy=2;
	gbc.gridwidth=1;
	gb.setConstraints(compareCancel,gbc);
	compareFrame.add(compareCancel);
	gbc.gridx=2;
	gbc.gridy=2;
	gbc.gridwidth=1;
	gb.setConstraints(compareApply,gbc);
	compareFrame.add(compareApply);
	
	compareFrame.pack();
	compareFrame.setVisible(true);	
    }

    public void compareBraids(){
	word1=firstWordField.getText();
	word2=secondWordField.getText();
	System.out.println(word1+"/"+word2);
	word=word1;
	char l;
	for(int i=word2.length()-1;i>=0;i--){
	    l=word2.charAt(i);
	    if('a'<=word2.charAt(i) && word2.charAt(i)<='z'){
		l=(char)((int)l-(int)'a'+(int)'A');
	    }
	    else if('A'<=word2.charAt(i) && word2.charAt(i)<='Z'){
		l=(char)((int)l-(int)'A'+(int)'a');	    }
	    word+=l;
	}
	handleReductionPanel.writeBraidWord(word);
	comparing=true;
	updateWord();
	setButtonsState(true,true,true,true);
	reduce.setText("Reduce");
	reduce.updateUI();
    }

    public void closeCompare(){
	compareFrame.dispose();
    }

    //Timer
    public void createTimer(){
	if(animationTimer!=null){
	    animationTimer.stop();
	}
	animationTimer=new Timer(pauseDelay,listener);
	animationTimer.setActionCommand("tic");
    }

    public void tic(){
	handleReductionPanel.refresh();
	int state=handleReductionPanel.reduce();
	if(state==2){
	    updateWord();
	    if(!continuousAnimation){
		reduce();
	    }
	}
	else if(state==0){
	    animationTimer.stop();
	    reducing=false;
	    setButtonsState(true,true,false,true);
	    String finalWord=handleReductionPanel.getCurrentWord();
	    String message;
	    int ind=handleReductionPanel.braidDrawing.indice();
	    if(!comparing){
		if(finalWord.equals("Empty")){
		     message="The final word is empty, so your initial braid word \n"+word+" is trivial.";
		}
		else{
		     message="There is no more handle and the final word is nonempty,\nso your initial braid word "+word+" is not trivial.";
		}
	    }
	    else{
		if(ind==0){
		    message="The final word is empty,\nso your initial braid words "+word1+" and "+word2+" are equivalent.";
		}
		else{
		    message="There is no more handle and the final word is nonempty,\nso your initial braid words "+word1+" and "+word2+" are not equivalent.";
		}

	    }
	    JOptionPane.showMessageDialog(null,message);
	    reduce.setText("Reduce");
	    reduce.updateUI();
	}
    }
}


