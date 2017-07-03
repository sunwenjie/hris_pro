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
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_PAYSLIP_REPORT_ICLICK", PayslipViewAction.JAVA_OBJECT_NAME_REPORT_ICLICK, "payslipDetailViewForm" ) %>
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, PayslipViewAction.JAVA_OBJECT_NAME_REPORT_ICLICK , "JAVA_OBJECT_PAYSLIP_REPORT_ICLICK" ) %>
</div>

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_salary_Modules').addClass('current');
		$('#menu_salary_Reports').addClass('selected');
		$('#menu_salary_Payslip_Report_IClick').addClass('selected');
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
	    parameters = parameters + '&flag=report';
		loadHtml('#special_select', 'payslipViewAction.do?proc=load_special_html' + parameters, false );
	})(jQuery);
	
	// Reset Form
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
		var oYearBegin = parseInt($('#monthlyBegin').val().split('/')[0]);
		var oYearEnd = parseInt($('#monthlyEnd').val().split('/')[0]);
		var oMonthBegin = parseInt($('#monthlyBegin').val().split('/')[1]);
		var oMonthEnd =  parseInt($('#monthlyEnd').val().split('/')[1]);
		if( oYearBegin > oYearEnd ){
			alert('账单月份截止不能早于账单月份起始！');
			$('#monthlyEnd').val($('#monthlyBegin').val());
		}else if( oYearBegin == oYearEnd ){
			if( oMonthBegin > oMonthEnd ) {
				alert('账单月份截止不能早于账单月份起始！');
				$('#monthlyEnd').val($('#monthlyBegin').val());
			}
		}
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
