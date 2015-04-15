package com.superserver.command;

import java.awt.event.KeyEvent;

import com.superserver.util.NativeParams;

/**
 * Created by forest on 2015/4/15.
 */

public class CommandHelper {
	private KeyExecutor mKeyExecutor;

	public CommandHelper() {
		mKeyExecutor = KeyExecutor.getInstance();
	}

	public void executeCommand(String command) {
		System.out.println("execute command " + command);
		switch (command) {
		case NativeParams.PPT_PRIOR:
			mKeyExecutor.press(KeyEvent.VK_LEFT);
			mKeyExecutor.release(KeyEvent.VK_LEFT);
			break;
		case NativeParams.PPT_NEXT:
			mKeyExecutor.press(KeyEvent.VK_RIGHT);
			mKeyExecutor.release(KeyEvent.VK_RIGHT);
			break;
		case NativeParams.PPT_CURRENT:
			mKeyExecutor.press(KeyEvent.VK_SHIFT);
			mKeyExecutor.press(KeyEvent.VK_F5);
			mKeyExecutor.release(KeyEvent.VK_SHIFT);
			mKeyExecutor.release(KeyEvent.VK_F5);
			break;
		case NativeParams.PPT_FIRST_PAGE:
			// to sure the ppt is palying
			mKeyExecutor.press(KeyEvent.VK_F5);
			mKeyExecutor.release(KeyEvent.VK_F5);
			mKeyExecutor.press(KeyEvent.VK_1);
			mKeyExecutor.press(KeyEvent.VK_ENTER);
			mKeyExecutor.release(KeyEvent.VK_1);
			mKeyExecutor.release(KeyEvent.VK_ENTER);
			break;
		case NativeParams.PPT_EXIT:
			mKeyExecutor.press(KeyEvent.VK_ESCAPE);
			mKeyExecutor.release(KeyEvent.VK_ESCAPE);
			break;
		default:
			if (command.startsWith(NativeParams.PPT_DIRECT_TO)) {  
				// 跳转到指定页面
				String[] params = command
						.split(NativeParams.COMMAND_SUB_DIVIDER);
				if (params.length >= 1) {
					// to sure the ppt is palying
					mKeyExecutor.press(KeyEvent.VK_F5);
					mKeyExecutor.release(KeyEvent.VK_F5);
					for (int i = 0; i < params[1].length(); i++) {
						mKeyExecutor.press(params[1].charAt(i));
						mKeyExecutor.release(params[1].charAt(i));
					}
					mKeyExecutor.press(KeyEvent.VK_ENTER);
					mKeyExecutor.release(KeyEvent.VK_ENTER);
				}
			}
			break;
		}
	}


}
