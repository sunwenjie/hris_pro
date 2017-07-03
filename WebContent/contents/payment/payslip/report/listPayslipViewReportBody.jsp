<%@page import="com.kan.hro.web.actions.biz.payment.PayslipViewAction"%>
<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_PAYSLIP_REPORT", PayslipViewAction.JAVA_OBJECT_NAME_REPORT, "payslipDetailViewForm" ) %>
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, PayslipViewAction.JAVA_OBJECT_NAME_REPORT , "JAVA_OBJECT_PAYSLIP_REPORT" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Reports').addClass('selected');
		$('#menu_salary_Payslip_Report').addClass('selected');
		$('#searchDiv').hide();
		
		// 重构导出按钮事件
		$('#exportExcel').attr('onclick','exportPayslipReport();');
		
		useFixColumn();
		
		// 加载特殊下拉框
		var parameters = '&monthlyBegin=<bean:write name="payslipDetailViewForm" property="monthlyBegin" />';
	    parameters = parameters + '&monthlyEnd=<bean:write name="payslipDetailViewForm" property="monthlyEnd" />';
	    parameters = parameters + '&sbStatus=<bean:write name="payslipDetailViewForm" property="status" />';
	    parameters = parameters + '&tempBranchIds=<bean:write name="payslipDetailViewForm" property="tempBranchIds" />';
	    parameters = parameters + '&tempPositionIds=<bean:write name="payslipDetailViewForm" property="tempPositionIds" />';
	    parameters = parameters + '&tempParentBranchIds=<bean:write name="payslipDetailViewForm" property="tempParentBranchIds" />';
	    parameters = parameters + '&entityId=<bean:write name="payslipDetailViewForm" property="entityId" />';
	    parameters = parameters + '&orderId=<bean:write name="payslipDetailViewForm" property="orderId" />';
	    parameters = parameters + '&status=<bean:write name="payslipDetailViewForm" property="status" />';
	    parameters = parameters + '&flag=report';
		loadHtml('#special_select', 'payslipViewAction.do?proc=load_special_html' + parameters, false );
	})(jQuery);
	
	// 导出Apc文件
	function exportApc(){
		if( $('#monthlyBegin').val() != $('#monthlyEnd').val()  ){
			alert('只能导出一个月份的数据，请检查账单月份起始和截止！');
		}else if( $('#entityId').val() != '91' && $('#entityId').val() != '93' && $('#entityId').val() != '95' && $('#entityId').val() != '99'  ){
			alert('所選法務實體不能導出APC！');
		}else{
			linkForm( 'list_form', 'downloadObjects', null, 'apc=1' );
		}
	};
	
	function resetForm(){
		var currDate = new Date();
		var monthly = currDate.getFullYear() + '/';
		if( parseInt(currDate.getMonth()) + 1 >= 10 ){
			monthly = monthly + (parseInt(currDate.getMonth()) + 1);
		}else{
			monthly = monthly + "0" + (parseInt(currDate.getMonth()) + 1);
		}
		
		$('#searchDiv input[type="text"]').val('');
		$('#searchDiv select').not('#monthlyBegin,#monthlyEnd').val('0');
		$('#monthlyBegin,#monthlyEnd').val(monthly)
	};
	
	function exportPayslipReport(){
		 linkForm('list_form', 'downloadObjects', null, 'fileType=excel&javaObjectName=' + '<%=PayslipViewAction.JAVA_OBJECT_NAME_REPORT%>' + '&templateType=1&templateId=' + 0);
	};
	
	function checkMonthly(){
		if( compareMonthly($('#monthlyBegin').val(),$('#monthlyEnd').val()) ){
			alert('账单月份截止不能早于账单月份起始！');
		}
	};
	
	// 比较两个“monthly”大小
	function compareMonthly( startMontly, endMonthly ){
		var oYearBegin = parseInt(startMontly.split('/')[0]);
		var oYearEnd = parseInt(endMonthly.split('/')[0]);
		var oMonthBegin = parseInt(startMontly.split('/')[1]);
		var oMonthEnd =  parseInt(endMonthly.split('/')[1]);
		return oYearBegin*12 + oMonthBegin > oYearEnd*12 + oMonthEnd;
	};
	
	// 使用固定列
	function useFixColumn(){
		<logic:notEqual name="pagedListHolder" property="holderSize" value="0">
			$("#resultTable").fixTable({
				fixColumn: 2,//固定列数
				width:0,//显示宽度
				height:450//显示高度
			});
		</logic:notEqual>
	};
</script>
