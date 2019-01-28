package com.gao.server;

import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模擬http:一種應用層的協議
 */
public class HttpSever {
    public static void main(String[] args) {
//        創建一個socket通信 啓動服務器，監聽8888端口
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket (8888);
            System.out.println ("服务器启动" + 8888 + "端口");
            while (!Thread.interrupted ()) {
//                如果當前綫程沒有被終端，不停的去接受客戶端的請求
                Socket client = serverSocket.accept ();
//                返回的socket代表了一個客戶端
//                獲取輸入流
                InputStream inputStream = client.getInputStream ();
//                獲取輸出流
                OutputStream outputStream = client.getOutputStream ();
//讀取輸入流
                byte[] bytes = new byte[inputStream.available ()];
                int read = inputStream.read (bytes);
                String s = new String (bytes, 0, read);
                System.out.println ("流中的内容：" + s);
                PrintWriter pw = new PrintWriter (outputStream);
                pw.println ("HTTP/1.1 200 OK");
                pw.flush ();
                pw.println ();
                pw.flush ();
                pw.println (s + "你好");
                pw.flush ();
                outputStream.close ();
                inputStream.close ();
                client.close ();
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
        try {
            serverSocket.close ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
