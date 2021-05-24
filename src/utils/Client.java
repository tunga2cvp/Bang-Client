package utils;
// Java implementation for a client
// Save file as Client1.java

import entity.message.Listener;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// Client class
public class Client
{
    final static int ServerPort = 8081;
    static DataInputStream dis ;
    static DataOutputStream dos ;


    public static void sendMessage(String message) throws IOException {
        dos.writeUTF(message);
    }

    public static void activate() throws UnknownHostException, IOException
    {
        Scanner scn = new Scanner(System.in);

        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        if (msg != null && !("".equals(msg)) && !("Your json command: ".equals(msg))) {
                            System.out.println(msg);
                            Listener.readReceiveMessage(msg);
                        };
                    } catch (IOException | InterruptedException e) {

                        e.printStackTrace();
                    }
                }
            }
        });
        readMessage.start();
    }
}