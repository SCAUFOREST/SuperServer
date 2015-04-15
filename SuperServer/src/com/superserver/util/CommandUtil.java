package com.superserver.util;

import java.io.IOException;
/**
 * Created by forest on 2015/4/4.
 */
public class CommandUtil {
    public  static  void  executeCommand(String cmd){
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
