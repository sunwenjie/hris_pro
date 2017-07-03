<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_INCOME_TAX", "com.kan.hro.domain.biz.payment.PayslipTaxDTO", "payslipHeaderViewForm" ) %>
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, "com.kan.hro.domain.biz.payment.PayslipTaxDTO","JAVA_OBJECT_INCOME_TAX") %>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/selectTaxTemplate.jsp"></jsp:include>
</div>		

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_IncomeTax').addClass('selected');
		$('#searchDiv').hide();
		
		// 加载特殊下拉框
		loadHtml('#special_select', 'incomeTaxAction.do?proc=load_special_html&status=<bean:write name="payslipHeaderViewForm" property="status" />&monthly=<bean:write name="payslipHeaderViewForm" property="monthly" />&itemGroupId=<bean:write name="payslipHeaderViewForm" property="itemGroupId" />', false, null );
	})(jQuery);
	
	<%--generate reset --%> 
	<%= SearchRender.generateSpecialSearchReset( request, "com.kan.hro.domain.biz.payment.PayslipTaxDTO" )%>
	
	function resetForm(){
		$('#employeeId').val("");
		$('#employeeNameZH').val("");
		$('#employeeNameEN').val('');
		$('#itemGroupId').val("0");
		$('#monthly').val("0");
		$('#status').val("0");
	};
</script>
