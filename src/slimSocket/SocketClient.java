package slimSocket;

import java.net.*;
import java.io.*;

/**
 *
 * @author merkus
 */
public class SocketClient {
    
    protected String ip = "127.0.0.1";
    protected int port = 8000;
    
    /**
     * Установить ip адрес сервера
     * @param ip 
     */
    protected void setIp (String ip) {
        this.ip = ip;
    }
    
    /**
     * Установить порт сервера
     * @param port 
     */
    protected void setPort (int port) {
        this.port = port;
    }
    
    /**
     * Возвращаем ip адрес сервера
     * @return 
     */
    public String getIp () {
        return this.ip;
    }
    
    /**
     * Возвращаем порт сервера
     * @return 
     */
    public int getPort () {
        return this.port;
    }
    
    /**
     * Создать сервер
     */
    public void createServer () throws IOException {
        ServerSocket serversocket = new ServerSocket ();
    }
    
}
