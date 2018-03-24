package com.statestr.testjdk6.senv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 * �Զ���senvЭ��������
 * </p>
 */
public class SenvURLConnection extends URLConnection {

	/** senvЭ���Ĭ�϶˿ں� */
	public static final int DEFAULT_PORT = 9527;

	private Socket connection = null;

	public SenvURLConnection(URL url) {
		super(url);
	}

	/**
	 * <p>
	 * ���ڸ���URLConnection�е�getInputStream()�������ṩ�������Ļ�ȡʵ���߼������������Ҫ��д�˷���
	 * </p>
	 */
	@Override
	public synchronized InputStream getInputStream() throws IOException {
		if (!connected)
			this.connect();
		return connection.getInputStream();
	}

	/**
	 * <p>
	 * senvЭ�����Ӳ���
	 * </p>
	 * 
	 */
	@Override
	public synchronized void connect() throws IOException {
		if (!connected) {
			int port = url.getPort();
			if (port < 1 || port > 65535)
				port = DEFAULT_PORT;
			this.connection = new Socket(url.getHost(), port);
			connected = true;
			// ���Ӻ�������������
			sendRequest(url);
		}
	}

	/**
	 * <p>
	 * ����senvЭ������
	 * </p>
	 * 
	 */
	protected void sendRequest(URL u) throws IOException {
		OutputStream outputStream = this.connection.getOutputStream();
		String queryString = u.getQuery();
		/*
		 * ��URL�Ĳ�ѯ�������ַ��͸����������ɷ��������������ѯ�󷵻ؽ���� ��������������Ϊ��ʱ������һ��"?"����ʾ��ѯ������ϵͳ������������Ϣ��
		 */
		outputStream.write(StringUtils.isNotNullOrBlank(queryString) ? queryString.getBytes() : "?".getBytes());
		outputStream.flush();
	}

}