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
		<sup><a href="#" class="dangqian"><bean:write name="monthly"/> <bean:message bundle="wx" key="wx.payment.salary.list" /></a></sup>
	</div>
	
	<table style="table-layout:fixed;width: 100%;border: 0px;">
	<logic:notEmpty name="payslipHolder" property="source">
		<logic:iterate id="payslipDTO" name="payslipHolder" property="source">
			<bean:define id="definePayslipDetailViews" name="payslipDTO" property="payslipDetailViews" />
			<bean:define id="definePayslipHeaderView" name="payslipDTO" property="payslipHeaderView" ></bean:define>
			<logic:iterate id="definePayslipDetailView" name="definePayslipDetailViews">
			<tr>
				<bean:define id="defineValue" name="definePayslipDetailView" property="calValue" />
					<td class="xiangqing" style="width: 70%;text-align:left;"> 
						<%
							if("zh".equalsIgnoreCase(request.getLocale().getLanguage())){
						%>
								<span><bean:write name="definePayslipDetailView" property="nameZH"/>：</span>
						<%
							}else{
						%>
								<span><bean:write name="definePayslipDetailView" property="nameEN"/>：</span>
						<%
							}
						%>
					</td>
					<!-- 如果是年终奖 -->
					<logic:equal name="definePayslipDetailView" property="itemId" value="18">
						<logic:greaterEqual value="0" name="defineValue">
							<td class="xiangqing nianzhongjiang" style="width: 30%;text-align:right;"> 
									<bean:write name="defineValue"/>
							</td>
						</logic:greaterEqual>
						<logic:lessThan value="0" name="defineValue">
							<td class="xiangqing nianzhongjiang" style="width: 30%;text-align:right;"> 
									<span style="color:#ff0000"><bean:write name="defineValue"/></span>
							</td>
						</logic:lessThan>
					</logic:equal>
					<!-- 如果是个税调整 -->
					<logic:equal name="definePayslipDetailView" property="itemId" value="10284">
						<logic:greaterEqual value="0" name="defineValue">
							<td class="xiangqing geshuitiaozheng" style="width: 30%;text-align:right;"> 
									<bean:write name="defineValue"/>
							</td>
						</logic:greaterEqual>
						<logic:lessThan value="0" name="defineValue">
							<td class="xiangqing geshuitiaozheng" style="width: 30%;text-align:right;"> 
									<span style="color:#ff0000"><bean:write name="defineValue"/></span>
							</td>
						</logic:lessThan>
					</logic:equal>
					<!-- 如果是税后工资调整 -->
					<logic:equal name="definePayslipDetailView" property="itemId" value="10051">
						<logic:greaterEqual value="0" name="defineValue">
							<td class="xiangqing shuihougongzitiaozheng" style="width: 30%;text-align:right;"> 
									<bean:write name="defineValue"/>
							</td>
						</logic:greaterEqual>
						<logic:lessThan value="0" name="defineValue">
							<td class="xiangqing shuihougongzitiaozheng" style="width: 30%;text-align:right;"> 
									<span style="color:#ff0000"><bean:write name="defineValue"/></span>
							</td>
						</logic:lessThan>
					</logic:equal>
					<!-- 正常  -->
					<logic:notEqual name="definePayslipDetailView" property="itemId" value="10284">
					<logic:notEqual name="definePayslipDetailView" property="itemId" value="10051">
					<logic:notEqual name="definePayslipDetailView" property="itemId" value="18">
						<logic:greaterEqual value="0" name="defineValue">
							<td class="xiangqing" style="width: 30%;text-align:right;"> 
									<bean:write name="defineValue"/>
							</td>
						</logic:greaterEqual>
						<logic:lessThan value="0" name="defineValue">
							<td class="xiangqing" style="width: 30%;text-align:right;"> 
									<span style="color:#ff0000"><bean:write name="defineValue"/></span>
							</td>
						</logic:lessThan>
					</logic:notEqual>	
					</logic:notEqual>
					</logic:notEqual>
					
			</tr>
			</logic:iterate>
			<logic:notEqual name="definePayslipHeaderView" property="taxAgentAmountPersonal" value="0">
			<tr>
				<td class="xiangqing" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.tax.salary" />：</span></td>
				<td class="xiangqing" style="width: 30%;text-align:right;"><span><bean:write name="definePayslipHeaderView" property="taxAgentAmountPersonal"/></span></td>
			</tr>
			</logic:notEqual>
			<tr>
				<td class="xiangqing" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.before.tax.salary" />：</span></td>
				<td class="xiangqing" style="width: 30%;text-align:right;"><span><bean:write name="definePayslipHeaderView" property="beforeTaxSalary"/></span></td>
			</tr>
			<tr>
				<td class="xiangqing" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.tax" />：</span></td>
				<td class="xiangqing" style="width: 30%;text-align:right;"><span style="color:#ff0000">-<bean:write name="definePayslipHeaderView" property="taxAmountPersonal"/></span></td>
			</tr>
			<tr id="taxAdjustment" style="display: none;"></tr>
			<tr id="afterTaxAdjustment" style="display: none;"></tr>
			<tr>
				<td class="zongji" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.after.tax.salary" />：</span></td>
				<td class="zongji" style="width: 30%;text-align:right;"><span><bean:write name="definePayslipHeaderView" property="afterTaxSalary"/></span></td>
			</tr>
			<tr class="annualBocus_info" id="annualBocus" style="display: none;"></tr>
			<tr class="annualBocus_info" style="display: none;">
				<td class="xiangqing" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.tax.annual.bocus" />：</span></td>
				<td class="xiangqing" style="width: 30%;text-align:right;"><span style="color:#ff0000">-<bean:write name="definePayslipHeaderView" property="annualBonusTax"/></span></td>
			</tr>
			<tr class="annualBocus_info" style="display: none;">
				<td class="zongji" style="width: 30%;text-align:left;"><span><bean:message bundle="wx" key="wx.payment.after.tax.annual.bocus" />：</span></td>
				<td class="zongji" style="width: 30%;text-align:right;"><span><bean:write name="definePayslipHeaderView" property="afterTaxAnnualBonus"/></span></td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
	</table>	
	
</div>
</body>

<script type="text/javascript">
		(function($) {	
			$('table tr').each( function(){
				if($(this).children('td').hasClass('geshuitiaozheng')){
					$('#taxAdjustment').append($(this).html());
					$('#taxAdjustment td').removeClass('geshuitiaozheng');
					$(this).remove();
					$('#taxAdjustment').show();
				}
				if($(this).children('td').hasClass('shuihougongzitiaozheng')){
					$('#afterTaxAdjustment').append($(this).html());
					$('#afterTaxAdjustment td').removeClass('shuihougongzitiaozheng');
					$(this).remove();
					$('#afterTaxAdjustment').show();
				}
				if($(this).children('td').hasClass('nianzhongjiang')){
					$('#annualBocus').append($(this).html());
					$('#annualBocus td').removeClass('nianzhongjiang');
					$(this).remove();
					$('.annualBocus_info').show();
				}
			})
		})(jQuery);
</script>

