<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_SETTLEMENT", "com.kan.hro.domain.biz.settlement.OrderDTO", "orderBillHeaderViewForm" ) %>
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, "com.kan.hro.domain.biz.settlement.OrderDTO", "JAVA_OBJECT_SETTLEMENT" ) %>
</div>

<!-- popup box -->

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_Payment').addClass('selected');
		$('#searchDiv').hide();
		// 加载特殊下拉框
		loadHtml('#special_select', 'orderBillHeaderViewAction.do?proc=load_special_html&monthly=<bean:write name="orderBillHeaderViewForm" property="monthly" />&status=<bean:write name="orderBillHeaderViewForm" property="status" />&entityId=<bean:write name="orderBillHeaderViewForm" property="entityId" />&businessTypeId=<bean:write name="orderBillHeaderViewForm" property="businessTypeId" />', false, null );

		// 解除导出事件
		$("#exportExcel").unbind("click");
		$("#exportExcel").click(function(){
			linkForm('list_form', 'downloadObjects', 'orderBillHeaderViewAction.do?proc=export_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.settlement.OrderDTO');
		});
	})(jQuery);
	
	function resetForm(){
		$("#clientId").val("");
		$("#clientNO").val("");
		$("#clientNameZH").val("");
		$("#clientNameEN").val("");
		$("#orderId").val("");
		$("#entityId").val("0");
		$("#businessTypeId").val("0");
		$("#monthly").val("0");
		$("#status").val("0");
	};

	function exportMulit(clientId,orderId,status,monthly){
		linkForm('list_form', 'downloadObjects', 'orderBillHeaderViewAction.do?proc=export_complicated_object', 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.settlement.SettlementDTO&_clientId='+clientId+'&_monthly='+monthly+'&_status='+status+'&_orderId='+orderId);
	}
</script>
