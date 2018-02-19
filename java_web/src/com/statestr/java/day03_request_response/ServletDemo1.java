package com.statestr.java.day03_request_response;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletDemo1 extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("").forward(request, response);
		
		response.sendRedirect("");
		
		this.getServletContext().getRealPath("");
		
		this.getServletContext().getResourceAsStream("");
		
		HttpSession session = request.getSession();
		session.invalidate();
	
		
		
		
	}
	
	/**
	 * �������������
	 * @param response
	 * @throws IOException
	 */
	private void test5(HttpServletResponse response) throws IOException{
		response.setDateHeader("expires", System.currentTimeMillis() + 1000*3600);
		String a = "aaa";
		response.getWriter().write(a);
	}

	/**
	 * ����������������
	 * @param response
	 * @throws IOException
	 */
	private void test4(HttpServletResponse response) throws IOException{
		String filename="http����.bmp";
		//��������ļ�������������Ҫ���ļ�������url����
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename,"UTF-8"));
		
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
	 * printwriter��responseд����ʱ�ı�������
	 * @param response
	 * @throws IOException
	 */
	private void test3(HttpServletResponse response) throws IOException {
		String data = "�й�";
		//����reponseʹ�õ����,����printwriterд��response��ʱ��utf-8���룬�Կ���response��ʲô����������д����
		response.setCharacterEncoding("UTF-8");
		//�����������ʱ������
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter wr = response.getWriter();
		//д��response�У����Ҫ�ѡ��й�����Ϊ���֣�Ĭ�ϵ������iso-8859-1���������Ҫ����response.setCharacterEncoding����
		wr.write(data);
	}
	
	/**
	 * ��html������meta ��ǩ���� http ����������ʽ��ģ��http��Ӧͷ
	 * @param response
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void test2(HttpServletResponse response) throws IOException,
	UnsupportedEncodingException {
	String data = "�й�";
	ServletOutputStream out = response.getOutputStream();
	//��html������meta ��ǩ���� http ����������ʽ��ģ��http��Ӧͷ
	out.write("<meta http-equiv='content-type' content='text/html;charset=UTF-8'>".getBytes());
	out.write(data.getBytes("UTF-8"));
}

	/**
	 * ����������Ժ��ֱ����
	 * @param response
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	private void test1(HttpServletResponse response) throws IOException,
			UnsupportedEncodingException {
		//����������Ժ�������
		response.setContentType("text/html;charset=UTF-8");
//		response.setHeader("Content-type", "text/html;charset=UTF-8");
		
		String data = "�й�";
		response.getOutputStream().write(data.getBytes("UTF-8"));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

}
