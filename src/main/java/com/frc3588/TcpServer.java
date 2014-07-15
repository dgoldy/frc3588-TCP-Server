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

/**
 * The TCP/IP Server class will listen on a specific port for incoming
 * connection requests. The default listening port is 7896 but this can be
 * changed either by using the constructor or by calling the setListenPort
 * method. In both of these cases, the port number must be greater than port
 * 1023. If the port is less than or equal to port 1023 and
 * IllegalArgumentException will be thrown.
 *
 *
 * @author Dave Goldy
 */
public class TcpServer
{
    private static final Logger logger = Logger.getLogger(TcpServer.class);

    public static void main(String args[])
    {
        TcpServer aTcpServer = new TcpServer();
        aTcpServer.start();
    }

    private int fListenPort; //Port to listen for TCP/IP request.
    private boolean fRun;    //True if the server is to continue to listen.

    /**
     * Create a TCP Server that will listen on the default port (7896).
     */
    public TcpServer()
    {
        this.fListenPort = 7896;
        this.fRun = true;
    }

    /**
     * Create a TCP Server that will listen on the argument port. The port
     * number must be greater than 1023.
     *
     * @param listenPort
     */
    public TcpServer(int listenPort)
    {
        this.fListenPort = 7896;
        this.fRun = true;
        if (listenPort > 1023)
        {
            this.fListenPort = listenPort;
        }
        else
        {
            throw new IllegalArgumentException("The listen port must be greater than 1023");
        }
    }

    /**
     * Run is set to true while the server is running and false when you want it
     * to stop.
     *
     * @return Run value
     */
    public boolean isRun()
    {
        return fRun;
    }

    /**
     * Set the run argument to false to stop the listener.
     *
     * @param run
     */
    public void setRun(boolean run)
    {
        this.fRun = run;
    }

    /**
     * Get the current listening port.
     *
     * @return
     */
    public int getListenPort()
    {
        return fListenPort;
    }

    /**
     * Change the listening port. The port number must be greater than 1023.
     *
     * @param listenPort
     */
    public void setListenPort(int listenPort)
    {
        if (listenPort > 1023)
        {
            this.fListenPort = listenPort;
        }
        else
        {
            throw new IllegalArgumentException("The listen port must be greater than 1023");
        }
    }

    /**
     * Start listening on the selected port. The server will run until the stop
     * method is called.
     */
    public void start()
    {
        fRun = true;
        try
        {
            ServerSocket listenSocket = new ServerSocket(fListenPort, 25);
            while (fRun)
            {
                Socket clientSocket = listenSocket.accept();
                logger.info("Server received a request.");
                Connection c = new Connection(clientSocket);
                c.start();
            }
            logger.info("Server stopped");
        } catch (IOException e)
        {
            logger.error("Listen : ", e);
        }
    }
}
