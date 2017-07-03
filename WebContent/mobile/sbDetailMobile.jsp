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
    <div class="mingzi"><bean:write name="employeeVO" property="nameZH"/>
    	<logic:notEmpty name="employeeVO" property="nameEN">
    	（<bean:write name="employeeVO" property="nameEN"/>）
    	</logic:notEmpty>
    	- <bean:write name="monthly"/> </div>
	<div class="zongji">
		<sup><a href="#" class="dangqian ap" onclick="pClick();">个人</a></sup>
		<sup><a href="#" class="dangqian ac" style="color: #000000" onclick="cClick();">公司</a></sup>
	</div>
	<div class="personal">
		<table style="table-layout:fixed;width: 100%;border: 0px;">
			<logic:iterate id="sbDetailVO" name="sbDetailVOs">
			<tr>
				<td class="xiangqing" style="width: 50%;text-align:left;"> 
					<span><bean:write name="sbDetailVO"  property="nameZH"/>：</span>
				</td>
				<td class="xiangqing" style="width: 50%;text-align:right;">
					<bean:write name="sbDetailVO" property="formatAmountPersonal"/>
				</td>
			</tr>
			</logic:iterate>
			<tr>
				<td class="zongji" style="width: 50%;text-align:left;"> 
					<span>合计：</span>
				</td>
				<td class="zongji" style="width: 50%;text-align:right;">
					<bean:write name='mountPersonal'  />
				</td>
			</tr>
		</table>
	</div>
	<div class="company" style="display: none">
		<table style="table-layout:fixed;width: 100%;border: 0px;">
			<logic:iterate id="sbDetailVO" name="sbDetailVOs">
			<tr>
				<td class="xiangqing" style="width: 50%;text-align:left;"> 
					<span><bean:write name="sbDetailVO"  property="nameZH"/>：</span>
				</td>
				<td class="xiangqing" style="width: 50%;text-align:right;">
					<bean:write name="sbDetailVO" property="formatAmountCompany"/>
				</td>
			</tr>
			</logic:iterate>
			<tr>
				<td class="zongji" style="width: 50%;text-align:left;"> 
					<span>合计：</span>
				</td>
				<td class="zongji" style="width: 50%;text-align:right;">
					<bean:write name='mountCompany'  />
				</td>
			</tr>
		</table>
	</div>
</div>
</body>

<script type="text/javascript">
		(function($) {	
		})(jQuery);
		function pClick(){
			$(".personal").show();
			$(".company").hide();
			$(".ap").css("color","#FF3300");
			$(".ac").css("color","#000000");
		};
		function cClick(){
			$(".personal").hide();
			$(".company").show();
			$(".ap").css("color","#000000");
			$(".ac").css("color","#FF3300");
		};
</script>

