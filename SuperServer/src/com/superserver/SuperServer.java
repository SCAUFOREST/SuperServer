package com.superserver;

import com.superserver.util.NativeParams;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SuperServer {

    public static int count = 0;
    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        final ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(20156);
            Thread serverThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("server start");
                    try {
                        while (true){
                            boolean isWaitingCommand = true;
                            Socket socket = serverSocket.accept();
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("Connection Success !");
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                            while (isWaitingCommand) {
                                String cmd = dis.readUTF();
                                if (cmd != null){
                                    String[] commands = cmd.split(NativeParams.COMMAND_DIVIDER);
                                    if(NativeParams.COMMAND_PREFIX.equals(commands[0])){
                                       System.out.println(cmd);
                                       switch (commands[1]){
                                           case NativeParams.MANAGER_MOUSE:
                                               MouseServer.getInstance().startService(socket);
                                               break;
                                           case NativeParams.MANAGER_POWER:
                                               PowerServer.getInstance().startService(socket);
                                               break;
                                           case NativeParams.MANAGER_PPT:
                                               PptServer.getInstance().startService(socket);
                                               break;
                                           default:
                                               System.out.println(commands[1] + " not found in : " +
                                                        "\n" + NativeParams.MANAGER_MOUSE +
                                                       "\n" + NativeParams.MANAGER_POWER +
                                                       "\n" + NativeParams.MANAGER_PPT );
                                               break;
                                       } // switch
                                    } // inner if
                                    isWaitingCommand = false;  // break inner while
                                }// outer if
                            } // inner while
                        } // outer while

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            serverThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
