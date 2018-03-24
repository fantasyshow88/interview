package com.statestr.testjdk6.senv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.logging.Logger;


/** 
 * <p>����SenvЭ��ķ����� 
 *    1.���տͻ������� 
 *    2.������Ӧ��� 
 *  </p>  
 */  
  
public class SenvProtocolServer {  
      
//    private static final Logger logger = Logger.getLogger(SenvProtocolServer.class);  
      
    /** SenvЭ������������ʶ */  
    public static final String REQUEST_PARAM_MARK = "pro=";  
      
    /** SenvЭ������Ĭ�϶˿ں� */  
    private static final int DEFAULT_PORT = 9527;  
  
    /** ��������IP�������� */  
    private String host;  
      
    /** ����SenvЭ�����Ķ˿ں� */  
    private int port = 9527;  
      
    /** ��ǰ�����ķ����ͨ�� */  
    private ServerSocketChannel serverChannel;  
      
    /** ��ǰ�����Ŀͻ���ͨ�� */  
    private SocketChannel clientChannel;  
      
    /** ����˵��¼�ע���� */  
    private Selector selector;  
      
    /** 
     * <p>����SenvЭ�������</p>  
     */  
    public void start() throws IOException {  
          
        serverChannel = ServerSocketChannel.open();  
          
        if (port < 1 || port > 65535)  
            port = DEFAULT_PORT;  
          
        if (StringUtils.isNotNullOrBlank(host)) {  
            serverChannel.socket().bind(new InetSocketAddress(InetAddress.getByName(host), port));  
            //logger.info("Start server " + host + ":" + port);  
        } else {  
            serverChannel.socket().bind(new InetSocketAddress(port));  
            //logger.info("Start server on port " + port);  
        }  
        serverChannel.configureBlocking(false);  
        selector = Selector.open();  
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);   
        handle();  
    }  
      
    /** 
     * <p>����SenvЭ������</p>  
     */  
    protected void handle() throws IOException {  
        while (true) {  
            selector.select();  
            Iterator<SelectionKey> keySetIterator = selector.selectedKeys().iterator();  
            SelectionKey cuurentKey = null;  
            while (keySetIterator.hasNext()) {  
                // ��ȡ��ǰ����ͨ���ļ�����  
                cuurentKey = keySetIterator.next();  
                // ����ͬһ������ͨ�����ظ�����  
                keySetIterator.remove();  
                try {  
                    if (cuurentKey.isAcceptable()) {  
                        serverChannel = (ServerSocketChannel) cuurentKey.channel();  
                        clientChannel = serverChannel.accept();  
                        if (clientChannel != null) {  
                        	System.out.println("Receive request from "   
                                    + clientChannel.socket().getInetAddress().getHostAddress() + ":"  
                                    + clientChannel.socket().getLocalPort());
                            clientChannel.configureBlocking(false);  
                            clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
                        }   
                    } else {  
                        clientChannel = (SocketChannel) cuurentKey.channel();  
                        if (cuurentKey.isReadable())   
                            writeResponse();  
                    }  
                } catch (IOException e) {  
                    if (clientChannel != null && clientChannel.isOpen())  
                        try {  
                            /* 
                             *  Ϊ��ֹ������ڶ�д�ͻ�����Ϣʱ���ͻ�������ĳ��ԭ������ر���������Ҳ��ǿ�ƹرյ���������� 
                             *  ����catch����Ҳ��Ҫ�Կͻ��˵�ͨ�����رմ��� �Ӷ���ֹ�����Ҳ��ǿ�ƹرյ��������⡣ 
                             *  ���⣬�Ծ���ͨ���Ķ�д�����赥������һ��try...catch���С� 
                             */  
                            clientChannel.close();  
                        } catch (IOException ioe) {  
                            ioe.printStackTrace();  
                        }  
                }   
            }  
        }  
    }  
      
    /** 
     * <p>��ȡ�ͻ�������</p>  
     */  
    protected String readRequest() throws IOException {  
        StringBuffer request = new StringBuffer();  
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();  
        ByteBuffer buffer = ByteBuffer.allocate(1024);  
        while (clientChannel.read(buffer) > 0) {  
            buffer.flip();  
            request.append(decoder.decode(buffer).toString());  
            buffer.clear();  
        }  
        return request.toString();  
    }  
      
    /** 
     * <p>��ͻ��˷�����Ӧ���</p>  
     */  
    protected void writeResponse() throws IOException {  
        String request = readRequest();  
        int start = -1;  
        // ������͵�����Ϊ"?"����������ָ���Ĳ���ʱ�����ѯ���е�ϵͳ��������  
        if ("?".equals(request) ||   
                (start = request.toLowerCase().indexOf(REQUEST_PARAM_MARK)) < 0) {  
            clientChannel.write(ByteBuffer.wrap(SystemUtils.formatSystemProperties().getBytes()));  
        } else {  
            // ��ȡ�������ֵ  
            String queryValueString = request.substring(start + REQUEST_PARAM_MARK.length());  
            if (StringUtils.isNullOrBlank(queryValueString))  
                clientChannel.write(ByteBuffer.wrap(SystemUtils.formatSystemProperties().getBytes()));  
            else {  
                int index = queryValueString.indexOf("&");  
                if (index > -1)  
                    /* 
                     *  ����������ֵ�������"&"�ַ��� 
                     *  ��˵������ַ��������������Ϊ������һЩ������������ݣ� 
                     *  ��˲����ⲿ������������ 
                     */  
                    queryValueString = queryValueString.substring(0, index);  
                clientChannel.write(ByteBuffer.wrap(SystemUtils.formatSystemProperties(queryValueString.split(",")).getBytes()));  
            }  
        }  
        /* 
         *  ��Ӧ���ݱ����ͳ�ȥ֮����ӻ��б�ʶ�� 
         *  Ŀ�����ÿͻ��˵�BufferedReader�������readLine()�������ܽ���ǰ�е����ݶ�ȡ���� 
         */  
        clientChannel.write(ByteBuffer.wrap("\n".getBytes()));  
          
        /* 
         *  ��������Ӧ��Ϣ�����Ϲر���ͻ���֮���ͨ���� 
         *  Ŀ�������ÿͻ��˶�ȡ����Щ��Ӧ֮�󣬾������ͷŵ���Դ���Ӷ��ö���������һֱ��������״̬ 
         */  
        clientChannel.close();  
    }  
    public String getHost() {  
        return host;  
    }  
  
    public void setHost(String host) {  
        this.host = host;  
    }  
  
    public int getPort() {  
        return port;  
    }  
  
    public void setPort(int port) {  
        this.port = port;  
    }  
  
    public static void main(String[] args) {  
        SenvProtocolServer server = new SenvProtocolServer();  
        server.setHost("localhost");  
        try {  
            server.start();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
}  
 


  
