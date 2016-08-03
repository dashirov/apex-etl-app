/* $Id$ */

/* Implementation of queries using jdk 1.2 */
/*  Interface to be documented soon....    */

package com.akamai;

import java.io.*;
import java.net.*;


public final class AkaData {
    public static final int AKAMAI_OK                 = 0;
    public static final int AKAMAI_DEFAULT            = 1;
    public static final int AKAMAI_GENERIC_ERROR      = 2;
    public static final int AKAMAI_SMALL_BUFFER       = 3;
    public static final int AKAMAI_TRY_AGAIN          = 4;
    public static final int AKAMAI_PERMISSION_DENIED  = 5;
    public static final int AKAMAI_NOT_FOUND          = 6;
    public static final int AKAMAI_GO_AWAY            = 7;
    public static final int AKAMAI_LATEST             = 8;

    public static final int HFIXEDSZ                  = 8;
    public static final int BUFFERSZ                  = 1024;
    public static int akadata_port              = 2001;
    public static int GLOBALTIMEOUT             = 1000;
    public static final String[][] default_answer     = {
	{"default_answer", "T"},
	{"country_code",   "US"},
	{"default_source", "client"}
    }; 
    public static String akadata_addr_str       = "127.0.0.1";    

    public static boolean set_aka_server(String ip) {
	return set_aka_server(ip, GLOBALTIMEOUT);
    }    

    public static boolean set_aka_server(String ip, int timeout) {
	String current_ip = akadata_addr_str;
	AkaData data;
	String str;
	akadata_addr_str = ip;
	data = new AkaData("127.0.0.1", timeout);
	str = data.getAttribute("default_answer");
	if (str != null) {
	    akadata_addr_str = current_ip;
	    return false;
	}
	else {
	    return true;
	}
    }

    public static boolean set_aka_port(int port) {
	return set_aka_port(port, GLOBALTIMEOUT);
    }

    public static boolean set_aka_port(int port, int timeout) {
	int current_port = akadata_port;
	AkaData data;
	String str;
	akadata_port = port;
	data = new AkaData("127.0.0.1", timeout);
	str = data.getAttribute("default_answer");
	if (str != null) {
	    akadata_port = current_port;
	    return false;
	}
	else {
	    return true;
	}
    }
    
    public static boolean set_aka_server_and_port(String ip, int port) {
	return set_aka_server_and_port(ip, port, GLOBALTIMEOUT);
    }
 
   public static boolean set_aka_server_and_port(String ip, int port, int timeout) {
	String current_ip = akadata_addr_str;
	int current_port = akadata_port;
	AkaData data;
	String str;
	akadata_addr_str = ip;
	akadata_port = port;
	data = new AkaData("127.0.0.1", timeout);
	str = data.getAttribute("default_answer");
	if (str != null) {
	    akadata_addr_str = current_ip;
	    akadata_port = current_port;
	    return false;
	}
	else {
	    return true;
	}
    }


    public static String get_aka_server() {
	return akadata_addr_str;
    }

    public static int get_aka_port() {
	return akadata_port;
    }
    
    public String[][]  results;
    
    public AkaData (String ip, int timeout) {
        DatagramSocket   socket;
        DatagramPacket   p;
        AkaDataPacked    query;
        int              result_len;
	
        byte[]           qbuf;
        byte[]           result;
	
        try {
            qbuf = new byte[AkaData.HFIXEDSZ+ip.length()];
            query = new AkaDataPacked(qbuf);
            query.setVersion(3);
            query.setFlags((byte)0);
            query.setQuery((int)(Math.random() *(float)(2<<16)));
            query.setLength(BUFFERSZ);
            query.setIPAddr(ip);
            socket = new DatagramSocket();
            socket.setSoTimeout(timeout);
            socket.send(new DatagramPacket(qbuf, AkaData.HFIXEDSZ+ip.length(),
                                           InetAddress.getByName(akadata_addr_str), akadata_port));
            p = new DatagramPacket(new byte[AkaData.BUFFERSZ], AkaData.BUFFERSZ);
	    
            socket.receive(p);
            result = p.getData();
            result_len = p.getLength();
            socket.close();
            results = parse_data(result, result_len, query);
            
        } catch (InterruptedIOException e) {
            results = default_answer;
        } catch (SocketException e) {
            results = default_answer;
        } catch (UnknownHostException e) {
            results = default_answer;
        } catch (IOException e) {
            results = default_answer;
        } catch (SecurityException e) {
            results = default_answer;
        }
    }
    
    private String[][] parse_data (byte[] data, int data_len, AkaDataPacked query) {
	String[][] result;
	int count, x, y;
	AkaDataPacked response;
	int iplength = query.ip_addr().length();
	if (data_len < AkaData.HFIXEDSZ+iplength) {
	    return default_answer;
	}
	
	response = new AkaDataPacked(data);
	
	if ((response.errcode() != AKAMAI_OK) &&
	    (response.errcode() != AKAMAI_DEFAULT)) {
	    return default_answer;
	}
	if ((response.version()  != query.version())  ||
	    (response.query_no() != query.query_no()) ||
	    (response.length()   != data_len)         ||
	    (response.ip_addr().matches(query.ip_addr()+".*") == false )) {
	    return default_answer;
	}
	
	/* Now we have enough to actually create the strings */
	/* First we need a count of how many entries there are */
	x = AkaData.HFIXEDSZ + iplength;
	count = 0;
	while (x < data_len-1) {
	    if (data[x] == '\0') {
		count++;
	    }
	    x++;
	}
	if (count == 0) {
	    /* hmmm.. */
	    return default_answer;
	}
	result = new String[count][];
	
	/* now we go through again and repeat to extract strings */
	x = AkaData.HFIXEDSZ + iplength;
	count = 0;
	
	try {
	    while (x < data_len-1) {
		y = 0;
		while (data[x+y] != '=') {
		    if ((x+y >= data_len) ||
			(data[x+y] == '\0')) {
			/* hmm problem */
			return default_answer;
		    }
		    y++;
		}
		/* y is the length from X until we find the = */
		result[count] = new String[2];
		result[count][0] = new String(data, x, y);
		/* Now find the value */
		x += y+1;
		y = 0;
		while (data[x+y] != '\0') {
		    if (x+y >= data_len) {
			/* hmm problem */
			return default_answer;
		    }
		    y++;
		}
		int tmp = count;
		result[count++][1] = new String(data, x, y);
		x += y + 1;
	    }
	} catch (NullPointerException e) {
	    return default_answer;
	}
	return result;
    }
    
    
    
    public String getAttribute(String attribute) {
	int x = 0;

	while (x < results.length) {
	    if (results[x][0].equals(attribute)) {
		return results[x][1];
	    }
	    x++;
	}
	return null;
    }

}


class AkaDataPacked {
    protected byte[] d;

    protected AkaDataPacked(byte[] data) {
	d = data;
    }

    protected final int get16(int p) {
	return ((d[p] & 0xff) << 8) | (d[p + 1] & 0xff);
    }

    protected final int get32(int p) {
	return ((d[p] & 0xff) << 24) |
	    ((d[p + 1] & 0xff) << 16) |
	    ((d[p + 2] & 0xff) << 8) |
	    (d[p + 3] & 0xff);
    }

    protected final void set16(int p, int v) {
	d[p] = (byte)((v >>> 8) & 0xff);
	d[p + 1] = (byte)(v & 0xff);
    }

    protected final void set32(int p, int v) {
	d[p] = (byte)((v >>> 24) & 0xff);
	d[p + 1] = (byte)((v >>> 16) & 0xff);
	d[p + 2] = (byte)((v >>> 8) & 0xff);
	d[p + 3] = (byte)(v & 0xff);
    }

    public int version()   { return d[0];}
    public int flags()     { return d[1];}
    public int query_no()  { return get16(2);}
    public int length()    { return get16(4);}
    public int errcode()   { return d[6];}
    public String ip_addr()  { String result = new String(d,8,d.length-8) ; return result;}
    
    public void setVersion(int i) { d[0] = (byte)i; }
    public void setFlags(byte f)  { d[1] = f; }
    public void setQuery(int q)   { set16(2, q); }
    public void setLength(int l)  { set16(4, l); }
    public void setErrcode(int i) { d[6] = (byte) i; }

    public void setIPAddr(String ipaddr) {
        byte[] bytes;
        bytes = ipaddr.getBytes();
        for (int i=0;i<bytes.length;i++) {
            d[8+i]  = bytes[i];
        }
    }

}

