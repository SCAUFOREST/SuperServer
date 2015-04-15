package com.superserver;

import com.superserver.command.CommandHelper;
import com.superserver.command.KeyExecutor;
import com.superserver.command.MouseHelper;
import com.superserver.interfaces.Servable;
import com.superserver.util.NativeParams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by forest on 2015/4/6.
 */
public class MouseServer implements Servable {
	private DataInputStream mDataInputStream;
	private DataOutputStream mDataOutputStream;
	private Socket mSocket;
	private static MouseServer instance;
	private KeyExecutor mKeyExecutor;
	private MouseHelper mMouseHelper;

	private MouseServer() {
		mKeyExecutor = KeyExecutor.getInstance();
	}

	public static MouseServer getInstance() {
		if (instance == null) {
			synchronized (MouseServer.class) {
				instance = new MouseServer();

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
					mDataOutputStream.writeUTF("Hello MouseManager");
					mMouseHelper = new MouseHelper();
					while (true) {
						String cmd = mDataInputStream.readUTF();
						if (cmd != null) {
							System.out.println(cmd);
							String[] commands = cmd.split(NativeParams.COMMAND_DIVIDER);
							if (NativeParams.COMMAND_PREFIX.equals(commands[0])
									&& commands.length >= 1) {
								mMouseHelper.executeCommand(commands[1]);
								System.out.println("command from mouse manager : "+ commands[1]);
							}
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(getClass().getName()+ " >> error in start service ");
				}
			}
		}).start();
	}

}
