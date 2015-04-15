package com.superserver.command;

import java.awt.AWTException;
import java.awt.Robot;


/**
 * Created by forest on 2015/4/15.
 */


public class KeyExecutor extends Robot
{
	
	private static KeyExecutor instance;
	
    private KeyExecutor() throws AWTException {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public static KeyExecutor getInstance(){
    	if(instance == null){
    		synchronized (KeyExecutor.class) {
				try {
					instance = new KeyExecutor();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    	}
    	return instance;
    }
    
    public void press(int keyCode ){
    	instance.keyPress(keyCode);
    }
    
    public void release(int keyCode) {
		instance.keyRelease(keyCode);
	}


}
