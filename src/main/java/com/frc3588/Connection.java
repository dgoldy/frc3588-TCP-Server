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
 * At the current time this class is written to echo back to the client
 * everything the client sends. In the future, this needs to change so the
 * server can process the information and send some type of results back to the
 * client. Also currently, this class will continue to listen on a socket until
 * it receives a "quit" message. At this point, the code will close the socket
 * and start listening for the next request.<br>
 * <br>
 * Each socket is processed on its own thread which is separate from the thread
 * listening for new requests.
 *
 * @author Dave Goldy
 */
public class Connection extends Thread
{

    private static final Logger logger = Logger.getLogger(Connection.class);
    private Socket fClientSocket;

    /**
     * Initialize the Connection class so it can read information from the
     * socket.
     *
     * @param aClientSocket
     */
    public Connection(Socket aClientSocket)
    {
        if (aClientSocket != null)
        {
            fClientSocket = aClientSocket;
        }
        else
        {
            throw new IllegalArgumentException("ClientSocket must be defined.");
        }
    }

    @Override
    public void run()
    {
        try
        {
            InputStream anInputStream = fClientSocket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(anInputStream));
            PrintStream out = new PrintStream(fClientSocket.getOutputStream());
            String data = "";
            logger.debug("Server ready to receive data.");
            data = in.readLine();
            logger.debug("Server received data: " + data);
            out.println(data);
            logger.debug("Server sent data back.");
        }
        catch (EOFException e)
        {
            logger.error("EOF: ", e);
        }
        catch (IOException e)
        {
            logger.error("IO: ", e);
        }
        finally
        {
            try
            {
                fClientSocket.close();
            }
            catch (IOException e)
            {
                logger.error("close failed");
            }
        }
    }
}
