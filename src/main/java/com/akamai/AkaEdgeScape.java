
package com.akamai;

import java.io.*;
import java.net.*;


public final class AkaEdgeScape { 
    public boolean set_aka_server(String ip) throws UnknownHostException { 
	AkaData.set_aka_server(ip); 
	return(true); 
    } 

    public boolean set_aka_server(String ip, int timeout) throws UnknownHostException { 
	AkaData.set_aka_server(ip,timeout); 
	return(true); 
    } 

    public boolean set_aka_port(int port) throws UnknownHostException { 
	AkaData.set_aka_port(port); 
	return(true); 
    } 

    public boolean set_aka_port(int port, int timeout) throws UnknownHostException { 
	AkaData.set_aka_port(port,timeout); 
	return(true); 
    } 
    
    public boolean set_aka_server_and_port(String ip, int port) throws UnknownHostException { 
	AkaData.set_aka_server_and_port(ip, port); 
	return(true); 
    } 

    public boolean set_aka_server_and_port(String ip, int port, int timeout) throws UnknownHostException { 
	AkaData.set_aka_server_and_port(ip, port,timeout); 
	return(true); 
    } 
    

    public String get_aka_server() 
    { 
	return(AkaData.get_aka_server()); 
    } 
    public int get_aka_port() { 
	return(AkaData.get_aka_port()); 
    } 
    public String getAttribute(String IP, int timeout, String attribute) { 
	return((new AkaData(IP, timeout)).getAttribute(attribute)); 
    } 
} 
