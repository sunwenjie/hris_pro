<%@page import="com.kan.base.web.renders.util.ListRender"%>
<%@page import="com.kan.base.web.renders.util.SearchRender"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- Information Search Form -->	
	<%= SearchRender.generateSpecialSearch( request, "JAVA_OBJECT_SETTLEMENT", "com.kan.hro.domain.biz.settlement.SettlementDTO", "orderBillHeaderViewForm" ) %>
	 
	 
	<!-- Information Search Result -->
	<%= ListRender.generateSpecialList( request, "com.kan.hro.domain.biz.settlement.SettlementDTO", "JAVA_OBJECT_SETTLEMENT" ) %>
</div>

<!-- popup box -->

<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_settlement_Modules').addClass('current');
		$('#menu_settlement_Payment').addClass('selected');
		$('#searchDiv').hide();
		// 修改FORM
		$(".list_form").attr("action","orderBillHeaderViewAction.do?proc=to_object_detail&clientId=<bean:write name='pagedListHolder' property='object.encodedClientId'/>&monthly=<bean:write name='pagedListHolder' property='object.encodedMonthly'/>&status=<bean:write name='pagedListHolder' property='object.status'/>&orderId=<bean:write name='pagedListHolder' property='object.encodedOrderId'/>");
		
		// 解除导出事件
		$("#exportExcel").unbind("click");
		$("#exportExcel").click(function(){
			linkForm('list_form', 'downloadObjects', "orderBillHeaderViewAction.do?proc=export_object&exportPage=detail&orderId=<bean:write name='pagedListHolder' property='object.encodedOrderId'/>&clientId=<bean:write name='pagedListHolder' property='object.encodedClientId'/>&monthly=<bean:write name='pagedListHolder' property='object.encodedMonthly'/>&status=<bean:write name='pagedListHolder' property='object.status'/>&orderId=<bean:write name='pagedListHolder' property='object.encodedOrderId'/>", 'fileType=excel&javaObjectName=com.kan.hro.domain.biz.settlement.SettlementDTO');
		});
		
		// 添加表头
		<logic:notEqual name="role" value="4" >
			loadOrderBillHeader("<bean:write name='pagedListHolder' property='object.encodedClientId'/>","<bean:write name='pagedListHolder' property='object.encodedOrderId'/>","<bean:write name='pagedListHolder' property='object.encodedMonthly'/>");
		</logic:notEqual>
	})(jQuery);
	
	function resetForm(){
		$("#contractId").val("");
		$("#employeeNameZH").val("");
		$("#employeeNameEN").val("");
	};
	
	function loadOrderBillHeader(clientId,orderId,monthly){
		$.post("clientAction.do?proc=getClientVOAndOrderVOForOrderBillDetailHeader",{"clientId":clientId,"orderId":orderId,"monthly":monthly},function(html){
			$("#tableWrapper").before(html);
		},"text");
	}
</script>
