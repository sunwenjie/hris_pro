<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>

<%
	/** ����ҳ�治���� */
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);

	/** ��������� */
	Random random = new Random();

	/** ȡ�����������֤��(4λ����) */
	String sRand="";
	for (int i = 0;i < 4;i++){
    	String rand = String.valueOf(random.nextInt(10));
    	sRand = sRand + rand;
	}

	/** ����֤�����SESSION */
	session.setAttribute("rand",sRand);
%>