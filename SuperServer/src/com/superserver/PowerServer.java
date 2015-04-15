package com.superserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.superserver.interfaces.Servable;
import com.superserver.util.CommandUtil;
import com.superserver.util.NativeParams;

/**
 * Created by forest on 2015/4/6.
 */
public class PowerServer implements Servable {
	private DataInputStream mDataInputStream;
	private DataOutputStream mDataOutputStream;
	private Socket mSocket;
	private static PowerServer instance;

	private PowerServer() {
	}

	public static PowerServer getInstance() {
		if (instance == null) {
			synchronized (PowerServer.class) {
				instance = new PowerServer();
			}
		}
		return instance;
	}

	@Override
	public void startService(final Socket socket) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mSocket = socket;
					mDataInputStream = new DataInputStream(socket
							.getInputStream());
					mDataOutputStream = new DataOutputStream(socket
							.getOutputStream());
					mDataOutputStream.writeUTF("Hello PowerManager");

					while (true) {
						String cmd = mDataInputStream.readUTF();
						if (cmd != null) {
							System.out.println(cmd);
							String[] commands = cmd
									.split(NativeParams.COMMAND_DIVIDER);
							if (NativeParams.COMMAND_PREFIX.equals(commands[0])) {
								executeCommand(commands[1]);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(getClass().getName()
							+ " >> error in start service ");
				}
			}
		}).start();
	}

	public void executeCommand(String cmd) {
		System.out.println("command from mouse manager : " + cmd);
		switch (cmd) {
		case NativeParams.CMD_SHUTDOWN:
			CommandUtil.executeCommand("notepad poweroff.txt");
			// CommandUtil.executeCommand("shudown -s -t 0");
			break;

		case NativeParams.CMD_REBOOT:
			CommandUtil.executeCommand("notepad reboot.txt");
			//CommandUtil.executeCommand("shutdown -r -t 0");
			break;
		default:
			break;
		}
	}
}
