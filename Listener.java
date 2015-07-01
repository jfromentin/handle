/* File : Listener.java
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

import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;


public class Listener implements ActionListener, AdjustmentListener{
    private Components components;
    
    public Listener(Components components){
	this.components=components;
    }
    
    public void actionPerformed(ActionEvent event){
	String action=event.getActionCommand();
	if(action.equals("draw")){
	    components.draw();
	}
	else if(action.equals("drawApply")){
	    components.drawBraid();
	    components.closeDraw();
	}
	else if(action.equals("drawCancel")){
	    components.closeDraw();
	}
	else if(action.equals("reduce")){
	    components.reduce();
	}
	else if(action.equals("controls")){
	    components.controls();
	}
	else if(action.equals("compare")){
	    components.compare();
	}
	else if(action.equals("compareApply")){
	    components.compareBraids();
	    components.closeCompare();
	}
	else if(action.equals("compareClose")){
	    components.closeCompare();
	}
	else if(action.equals("controlsApply")){
	    components.saveParameters();
	    components.closeControls();
	}
	else if(action.equals("controlsCancel")){
	    components.closeControls();
	}
	else if(action.equals("tic")){
	    components.tic();
	}
	else{
	    System.out.println(action);
	}
    }

    public void adjustmentValueChanged(AdjustmentEvent event){
	components.delayAdjust();
    }

}