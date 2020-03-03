import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.io.*;

public class HandleReduction extends JFrame implements ActionListener{
    protected Action newBraidAction,sample1BraidAction,startReductionAction,pauseReductionAction;
    protected JPanel mainPanel,animationPanel,animationButtonPanel,samplePanel,creationPanel;
    protected JButton sample1,sample2,sample3,sample4,playPause,faster,slower;
    protected JButton sigmaOne,sigmaOneInverse,sigmaTwo,sigmaTwoInverse,sigmaThree,sigmaThreeInverse,sigmaFour,sigmaFourInverse,erase,clear;
    protected HandleReductionPanel handleReductionPanel;
    protected boolean play=false,braidSet=false;
    protected int delay=0;
    private Timer animationTimer;	
    
    public HandleReduction() {
	createFrame();
	createTimer();
	setTitle("Reduction des poignées");
	setSize(1000,900);
	setVisible(true);
	setBackground(Color.white);
	
    }
	
	
    public void createFrame(){
	mainPanel = new JPanel(new BorderLayout());
	animationPanel = new JPanel(new BorderLayout());
	handleReductionPanel = new HandleReductionPanel();
	animationPanel.add(handleReductionPanel,BorderLayout.CENTER);
	animationButtonPanel = new JPanel(new FlowLayout());
	playPause=new JButton("Lecture");
	playPause.addActionListener(this);
	animationButtonPanel.add(playPause);
	faster=new JButton("+");
	faster.addActionListener(this);
	animationButtonPanel.add(faster);
	slower=new JButton("-");
	slower.addActionListener(this);
	animationButtonPanel.add(slower);
	animationPanel.add(animationButtonPanel,BorderLayout.PAGE_END);
	mainPanel.add(animationPanel,BorderLayout.CENTER);
	samplePanel=new JPanel(new GridLayout(0,1));
	sample1 = new JButton();
	sample1.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sample1.png")));
	sample1.addActionListener(this);
	samplePanel.add(sample1);
	sample2 = new JButton();
	sample2.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sample2.png")));
	sample2.addActionListener(this);
	samplePanel.add(sample2);
	sample3 = new JButton();
	sample3.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sample3.png")));
	sample3.addActionListener(this);
	samplePanel.add(sample3);
	sample4 = new JButton();
	sample4.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sample4.png")));
	sample4.addActionListener(this);
	samplePanel.add(sample4);
	mainPanel.add(samplePanel,BorderLayout.LINE_START);
	creationPanel=new JPanel(new GridLayout(0,1));
	sigmaOne=new JButton();
	sigmaOne.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma1inv.png")));
	sigmaOne.addActionListener(this);
	creationPanel.add(sigmaOne);	
	sigmaOneInverse=new JButton();
	sigmaOneInverse.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma1.png")));
	sigmaOneInverse.addActionListener(this);
	creationPanel.add(sigmaOneInverse);	
	sigmaTwo=new JButton();
	sigmaTwo.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma2inv.png")));
	sigmaTwo.addActionListener(this);
	creationPanel.add(sigmaTwo);	
	sigmaTwoInverse=new JButton();
	sigmaTwoInverse.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma2.png")));
	sigmaTwoInverse.addActionListener(this);
	creationPanel.add(sigmaTwoInverse);		
	sigmaThree=new JButton();
	sigmaThree.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma3inv.png")));
	sigmaThree.addActionListener(this);
	creationPanel.add(sigmaThree);
	sigmaThreeInverse=new JButton();
	sigmaThreeInverse.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/sigma3.png")));
	sigmaThreeInverse.addActionListener(this);
	creationPanel.add(sigmaThreeInverse);
	erase=new JButton("Effacer");
	erase.addActionListener(this);
	creationPanel.add(erase);
	clear=new JButton("Tout effacer");
	clear.addActionListener(this);
	creationPanel.add(clear);
	mainPanel.add(creationPanel,BorderLayout.LINE_END);
	add(mainPanel);	
		
    }
	
    public void actionPerformed(ActionEvent event){
	if(event.getSource()==sample1){
	    animationTimer.stop();
	    handleReductionPanel.writeBraidWord("cbabC");
	    braidSet=true;
	}
	else if(event.getSource()==sample2){
	    animationTimer.stop();
	    handleReductionPanel.writeBraidWord("abcbAaBC");
	    braidSet=true;
	}
	else if(event.getSource()==sample3){
	    animationTimer.stop();
	    handleReductionPanel.writeBraidWord("abABabABabAB");
	    braidSet=true;
	}
	else if(event.getSource()==sample4){
	    animationTimer.stop();
	    handleReductionPanel.writeBraidWord("abcABCabcABCabcABC");
	    braidSet=true;
	}

	else if(event.getSource()==playPause){
	    if(play){
		playPause.setText("Lecture");
		playPause.updateUI();
		animationTimer.stop();	
		play=false;
	    }
	    else if(braidSet){
		playPause.setText("Stop");
		playPause.updateUI();
		animationTimer.start();
		play=true;
	    }
	}
	else if(event.getSource()==slower){
	    if(delay<200){
		delay+=10;
		createTimer();
	    }
	}
	else if(event.getSource()==faster){
	    if(delay>0){
		delay-=10;
		createTimer();
	    }
	}
	else if(event.getSource()==sigmaOne){
	    handleReductionPanel.add('a');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==sigmaOneInverse){
	    handleReductionPanel.add('A');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==sigmaTwo){
	    handleReductionPanel.add('b');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==sigmaTwoInverse){
	    handleReductionPanel.add('B');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==sigmaThree){
	    handleReductionPanel.add('c');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==sigmaThreeInverse){
	    handleReductionPanel.add('C');
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==erase){
	    handleReductionPanel.remove();
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
	else if(event.getSource()==clear){
	    handleReductionPanel.clear();
	    playPause.setText("Lecture");
	    playPause.updateUI();
	    animationTimer.stop();	
	    play=false;
	}
    }
    
    public void createTimer(){
	if(animationTimer!=null){
	    animationTimer.stop();
	}
	animationTimer=new Timer(delay,new ActionListener(){public void actionPerformed(ActionEvent evt){
	    handleReductionPanel.refresh();
	    if(!handleReductionPanel.reduce()){
		animationTimer.stop();
		play=false;
		playPause.setText("Lecture");
		playPause.updateUI();
	    }}});
	if(play){
	    animationTimer.start();
	}
    }
    
    public static void main(String args[]) {
	new HandleReduction();
    }
    
}
