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
	<form action="sbHeaderAction.do?proc=list_object_mobile" method="post" class="form_searchSBByMonthly">
		<input name="monthly" type="text" class="sousuokuang" onfocus="if(value=='请输入年份') {value=''}" onblur="if (value=='') {value='请输入年份'}" value="请输入年份"/>
		<a href="#" class="buttonSearch monthliesSearch" onclick="btnSearch();">搜索</a>
	</form>
	</div>
	<logic:iterate id="sbHeaderVO" name="sbHeaderVOs">
		<input name="" type="button" value="<bean:write name="sbHeaderVO" property="monthly"/>（个：<bean:write name="sbHeaderVO" property="formatAmountPersonal" />，企：<bean:write name="sbHeaderVO" property="formatAmountCompany"/>）" class="gongzi" onclick="link('sbHeaderAction.do?proc=sbDetail_mobile&monthly=<bean:write name="sbHeaderVO" property="monthly"/>');"/>	
	</logic:iterate>
	<%
		if(((List<Object>)request.getAttribute("sbHeaderVOs")).size()==0){
		   %>
		   <div class="xiangqing" align="center"><span>NO DATA!</span></div>
		   <div id="layout">
		   	<a href="#" class="button orange" onclick="btnBack();">返回首页</a>
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
		$(".form_searchSBByMonthly").submit();
	}
</script>

