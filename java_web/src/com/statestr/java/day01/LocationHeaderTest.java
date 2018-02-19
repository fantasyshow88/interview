package com.statestr.java.day01;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocationHeaderTest extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		test5(response);
	}

	/**
	 * ����
	 * @param response
	 * @throws IOException
	 */
	private void test5(HttpServletResponse response) throws IOException {
		response.setHeader("Content-disposition", "attachment;filename=http����.bmp");
		InputStream in = this.getServletContext().getResourceAsStream("/http����.bmp");
		int len = 0;
		byte[] buffer = new byte[1024];
		ServletOutputStream out = response.getOutputStream();
		while((len=in.read(buffer))!= -1){
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
	}

	/**
	 * ��ʱˢ��
	 * @param response
	 * @throws IOException
	 */
	private void test4(HttpServletResponse response) throws IOException {
		String a = "aaaaaa";
		response.setHeader("Refresh", "3");//��3sˢ������
//		response.setHeader("Refresh", "3;url=http://www.baidu.com");
		response.getOutputStream().write(a.getBytes());
	}

	/**
	 * ͨ��content-type ��������������ָ�ʽ��������
	 * @param response
	 * @throws IOException
	 */
	private void test3(HttpServletResponse response) throws IOException {
		response.setHeader("Content-Type", "image/bmp");
		
		InputStream in = this.getServletContext().getResourceAsStream("/http����.bmp");
		int len = 0;
		byte[] buffer = new byte[1024];
		ServletOutputStream out = response.getOutputStream();
		while((len=in.read(buffer))!= -1){
			out.write(buffer,0,len);
		}
		in.close();
		out.close();
	}

	/**
	 * ѹ���������
	 * @param response
	 * @throws IOException
	 */
	private void test2(HttpServletResponse response) throws IOException {
		String a = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		
		System.out.println("ԭʼ���ݴ�С��" + a.getBytes().length);
		ByteArrayOutputStream bout = new ByteArrayOutputStream();//�ײ���
		GZIPOutputStream gout = new GZIPOutputStream(bout);//��װ��һ���л��壬Ҫ�������˲Ż�д
		gout.write(a.getBytes());
		gout.close();//һ��Ҫ����������� ˢ�»���
		
		byte[] bzip = bout.toByteArray();
		
		System.out.println("ѹ�������ݴ�С��" + bzip.length);
		
		response.setHeader("Content-Encoding", "gzip");//֪ͨ��������ݲ��õ�ѹ����ʽ
		response.setHeader("Content-Length", bzip.length+"");//��content-Encoding��Ӧ��ͷ ��Ҫдlength
		response.getOutputStream().write(bzip);
	}

	/**
	 * �����ض�����״̬�� 302�� location ͷ��
	 * @param response
	 */
	private void test1(HttpServletResponse response) {
		response.setStatus(302);
		response.setHeader("location", "/java_web/1.html");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
