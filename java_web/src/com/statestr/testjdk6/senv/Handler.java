package com.statestr.testjdk6.senv;
import java.io.IOException;  
import java.net.URL;  
import java.net.URLConnection;  
import java.net.URLStreamHandler;  
  
/** 
 * <p> 
 *      �Զ����senvЭ�鴦������ 
 *      ����senvЭ��ĸ�ʽ���ϱ�׼��URL��ʽ�� 
 *          protocol://username@hostname:port/path/filename?query#fragment 
 *      ��ˣ����ʵ����ֻ��ʵ�ָ����е�openConnection()�������ɡ���������д���෽�� 
 *      protected void parseURL(URL u, String spec, int start, int limit)�� 
 *      ��������ȷ������URL�ĸ�������ֵ������:host,port,query�ȡ� 
 * </p>  
 */  
public class Handler extends URLStreamHandler {  
  
    /** 
     * <p>��URL����Э���ҵ��ô�����������openConnection()�����󣬷��ظ������Э�����ӵ�������</p>  
     * @return 
     */  
    @Override  
    protected URLConnection openConnection(URL u) throws IOException {  
        return new SenvURLConnection(u);  
    }  
      
}  