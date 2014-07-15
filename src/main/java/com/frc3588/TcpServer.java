/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frc3588;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author davegoldy
 */

        
public class TcpServer {
    
private static final Logger logger = Logger.getLogger(TcpServer.class);

    public static void main(String args[]) {

        //PropertyConfigurator.configure("log4j.properties");
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort, 25);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                logger.info("Server received a request.");
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            logger.error("Listen : ", e);
        }
    }
}

