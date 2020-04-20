package com.teeqee.netty.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerDemo {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(7518);
            //获取一个客户端连接
            Socket socketClient = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            PrintWriter pro = new PrintWriter(socketClient.getOutputStream(), true);
            String request ,response = null;
            while ((request=in.readLine())!=null){
                if ("Done".equals(request)){
                    break;
                }
                System.out.println(request);
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
