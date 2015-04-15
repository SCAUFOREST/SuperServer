package com.superserver.command;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.InputEvent;

import com.superserver.util.NativeParams;

public class MouseHelper {
	private KeyExecutor mKeyExecutor;
	private PointerInfo mPointerInfo;
	public MouseHelper() {
		mKeyExecutor = KeyExecutor.getInstance();
		mPointerInfo = MouseInfo.getPointerInfo();
	}

	public void executeCommand(String command) {
		
		switch (command) {
		
		case NativeParams.MOUSE_LEFT:
			System.out.println("execute command MOUSE_LEFT ");
			mKeyExecutor.mousePress(InputEvent.BUTTON1_MASK);
			mKeyExecutor.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
			
		case NativeParams.MOUSE_RIGHT:
			System.out.println("execute command MOUSE_RIGHT ");

			mKeyExecutor.mousePress(InputEvent.BUTTON3_MASK);
			mKeyExecutor.mouseRelease(InputEvent.BUTTON3_MASK);
			break;
		
		case NativeParams.MOUSE_DOUBLE_CLICK:
			System.out.println("execute command MOUSE_DOUBLE_CLICK");

			mKeyExecutor.mousePress(InputEvent.BUTTON1_MASK);
			mKeyExecutor.mouseRelease(InputEvent.BUTTON1_MASK);
			mKeyExecutor.mousePress(InputEvent.BUTTON1_MASK);
			mKeyExecutor.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		
		default:
			if(command.startsWith(NativeParams.MOUSE_MOVE)){
				System.out.println("execute command MOUSE_MOVE");

				moveMouseIndicator(command);
			}else if(command.startsWith(NativeParams.MOUSE_WHEEL)){
				System.out.println("execute command MOUSE_WHEEL");

				moveMouseWheel(command);
			}
			break;
		}
	}

	private void moveMouseIndicator(String command) {
		// TODO Auto-generated method stub
		try {
			Point point = mPointerInfo.getLocation();
			String[] commands = command.split(NativeParams.COMMAND_SUB_DIVIDER);
			int x = Integer.valueOf(commands[1]) + (int)point.getX();
			int y = Integer.valueOf(commands[2]) + (int)point.getY();
			mKeyExecutor.mouseMove(x, y);
			System.out.println("dX = " + Integer.valueOf(commands[1])
					+ "  dY = " + Integer.valueOf(commands[2])
					+ "  pointX = " + point.getX() + " pointY = " + point.getY());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void moveMouseWheel(String command) {
		// TODO Auto-generated method stub
		try {
			String[] commands = command.split(NativeParams.COMMAND_SUB_DIVIDER);
			int wheelAmount = Integer.valueOf(commands[1]);
			mKeyExecutor.mouseWheel(wheelAmount);
			System.out.println("mouse wheel amount = " + wheelAmount);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
