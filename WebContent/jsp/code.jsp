<%@ page pageEncoding="GBK"%>
<%@ page import="java.util.*"%>

<%
	/** 设置页面不缓存 */
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);

	/** 生成随机类 */
	Random random = new Random();

	/** 取随机产生的认证码(4位数字) */
	String sRand="";
	for (int i = 0;i < 4;i++){
    	String rand = String.valueOf(random.nextInt(10));
    	sRand = sRand + rand;
	}

	/** 将认证码存入SESSION */
	session.setAttribute("rand",sRand);
%>