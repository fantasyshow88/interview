package com.statestr.testjdk6.senv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/** 
 * <p> 
 *    SenvЭ������Ŀͻ��ˣ���Ҫ���ܷ�Ϊ�� 
 *    1.�ڴ�����һ�δ���URL����֮ǰ����Ӷ��Զ���Э���֧�� 
 *    2.�������� 
 *    3.չʾ��Ӧ���� 
 * </p>  
 */  
public class SenvProtocolClient {  
      
    public static void main(String[] args) {  
        BufferedReader reader = null;  
        try {  
            // ����Э�鴦�������ҹ���һ  
            if (StringUtils.isNullOrBlank(System.getProperty("java.protocol.handler.pkgs"))) {  
                // ���ø���Э������ڵĸ���·��  
                System.setProperty("java.protocol.handler.pkgs", "com.statestr.testjdk6");  
            }  
            /* 
                         * ����Э�鴦�������ҹ���� 
                         * ���ַ�ʽ������Ӧ�÷�Χ֮��ֻ�ܱ�ִ��һ�Ρ� 
                         * �������һ��������"java.lang.Error: factory already defined"�����Ĵ��󡣵������ܹ���һ������. 
                         */   
//          URL.setURLStreamHandlerFactory(new CustomProtocolFactory());  
            URL url = new URL("senv://localhost:9527/?pro=os.name");  
//            URL url = new URL("senv://localhost:9527/");  
            reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));  
            String result = "";  
            while ((result = reader.readLine()) != null)  
                System.out.println(result);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (reader != null)  
                    reader.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
}