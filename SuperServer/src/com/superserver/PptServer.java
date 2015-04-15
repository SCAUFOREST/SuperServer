package com.superserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.superserver.command.CommandHelper;
import com.superserver.interfaces.Servable;
import com.superserver.util.NativeParams;

/**
 * Created by forest on 2015/4/6.
 */
public class PptServer implements Servable {


    private DataInputStream mDataInputStream ;
    private DataOutputStream mDataOutputStream;
    private Socket mSocket;
    private static PptServer instance;

    private  PptServer() {
    }

    public static PptServer getInstance(){
        if(instance == null){
            synchronized(PptServer.class){
                instance = new PptServer();
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
                    mDataInputStream = new DataInputStream(socket.getInputStream());
                    mDataOutputStream = new DataOutputStream(socket.getOutputStream());
                    mDataOutputStream.writeUTF("Hello PptManager");
                    CommandHelper commandHelper = new CommandHelper();
                    while (true) {
                        String cmd = mDataInputStream.readUTF();
                        if (cmd != null){
                        	System.out.println(cmd);
                            String[] commands = cmd.split(NativeParams.COMMAND_DIVIDER);
                            if(NativeParams.COMMAND_PREFIX.equals(commands[0]) && commands.length >= 1){
                            	commandHelper.executeCommand(commands[1]);
                                System.out.println("command from ppt manager : " + commands[1]);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println( getClass().getName() + " >> error in start service ");
                }
            }
        }).start();
    }
}
