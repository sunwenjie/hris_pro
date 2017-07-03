<%@page import="java.util.List"%>
<%@page import="com.kan.base.util.KANUtil"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="js/jquery-1.8.2.min.js"></script>
<link rel="stylesheet" href="mobile/css/mobile.css" />
<script src="js/kan.js"></script>
<title><bean:message bundle="wx" key="wx.logon.title" /></title>
</head>
<body>
<div class="gongzichaxu">
	<div class="sousuo">
		<form action="paymentHeaderAction.do?proc=list_object_mobile" method="post" class="form_searchPaymentByMonthly">
		<input name="monthly" type="text" class="sousuokuang" onfocus="if(value=='<bean:message bundle="wx" key="wx.payment.input.monthly" />') {value=''}" onblur="if (value=='') {value='<bean:message bundle="wx" key="wx.payment.input.monthly" />'}" value="<bean:message bundle="wx" key="wx.payment.input.monthly" />"/>
		<a href="#" class="buttonSearch monthliesSearch" onclick="btnSearch();"><bean:message bundle="wx" key="wx.payment.search" /></a>
	</form>
	</div>
	<logic:iterate id="paymentHeaderVO" name="paymentHeaderVOs">
		<input name="" type="button" value="<bean:write name="paymentHeaderVO" property="monthly"/>£¨<bean:message bundle="wx" key="wx.payment.actual.payment" />£º<bean:write name="paymentHeaderVO" property="actualSalary" />£©" class="gongzi" onclick="link('paymentHeaderAction.do?proc=to_batchDetail_mobile&monthly=<bean:write name="paymentHeaderVO" property="monthly"/>');"/>	
	</logic:iterate>
	<%
		if(((List<Object>)request.getAttribute("paymentHeaderVOs")).size()==0){
		   %>
		   <div class="xiangqing" align="center"><span>NO DATA!</span></div>
		   <div id="layout">
		   	<a href="#" class="button orange" onclick="btnBack();"><bean:message bundle="wx" key="wx.btn.main.page" /></a>
		   </div>
		   <% 
		}
	%>
</div>
</body>
<script type="text/javascript">
	
	(function($) {	
		
	})(jQuery);
	
	function btnBack(){
		link('securityAction.do?proc=index_mobile');
	}
	function btnSearch(){
		$(".form_searchPaymentByMonthly").submit();
	}
</script>

