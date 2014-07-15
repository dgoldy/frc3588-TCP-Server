/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frc3588;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import org.apache.log4j.Logger;

/**
 *
 * @author davegoldy
 */
public class Connection extends Thread {

    private static final Logger logger = Logger.getLogger(Connection.class);
    private BufferedReader in;
    private PrintStream out;
    private Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            InputStream anInputStream = clientSocket.getInputStream();
            in = new BufferedReader(new InputStreamReader(anInputStream));
            out = new PrintStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            logger.error("Connection: ", e);
        }
    }

    public void run() {
        try {
            String data = "";
            while (!data.equals("quit")) {
                logger.debug("Server ready to receive data.");
                data = in.readLine();
                logger.debug("Server received data: " + data);
                out.println(data);
                logger.debug("Server sent data back.");
            }
        } catch (EOFException e) {
            logger.error("EOF: ", e);
        } catch (IOException e) {
            logger.error("IO: ", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                logger.error("close failed");
            }
        }
    }
}
