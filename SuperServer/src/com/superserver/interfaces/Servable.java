package com.superserver.interfaces;

import java.net.Socket;

/**
 * Created by forest on 2015/4/6.
 */
public interface Servable {
    public void startService(Socket socket);
}
