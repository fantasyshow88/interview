package com.statestr.testjdk6.senv;
import java.net.URLStreamHandler;  
import java.net.URLStreamHandlerFactory;  
  
/** 
 * <p> 
 *       �Զ���Э��Ĵ������������������ÿ���Զ����Э����������Ǹ��Զ�Ӧ��Э�鴦���� 
 *       ���Ҫ�������Ĳ��ҹ���1����װЭ�鴦����ʱ������Ҫ�õ������ 
 *</p>  
 */  
public class CustomProtocolFactory implements URLStreamHandlerFactory {

	
	@Override
    public URLStreamHandler createURLStreamHandler(String protocol) {  
        if ("senv".equalsIgnoreCase(protocol))  
            return new Handler();  
        return null;  
    } 
  
 
  
}  