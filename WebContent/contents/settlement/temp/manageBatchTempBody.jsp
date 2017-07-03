<%@ page pageEncoding="GBK" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_SETTLE_ORDER_BATCH_TEMP", "batchTempForm", true ) %>
</div>

<div id="popupWrapper">
	<logic:equal name="role" value="1">
		<jsp:include page="/popup/searchClient.jsp"></jsp:include>
		<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	</logic:equal>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>
						
<script type="text/javascript">
	(function($) {
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_SETTLE_ORDER_BATCH_TEMP", null, null, null ) %>
		
		$('#pageTitle').html('创建结算批次');

		// 添加“搜索客户信息”
		$('#clientIdLI').append('<a onclick="popupClientSearch();" class="kanhandle"><img src="images/search.png" title="搜索客户记录" /></a>');
		// 添加“搜索订单信息”
		$('#orderIdLI').append('<a onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="搜索订单记录" /></a>');
		// 添加“搜索派送信息记录”
		$('#contractIdLI').append('<a onclick="popupContractSearch();" class="kanhandle"><img src="images/search.png" title="搜索派送信息记录" /></a>');

		// 添加包含选项
		$('#accountPeriodLI').parent().after( 
			'<ol class="auto"><li><label>账单内容</label><div style="width: 215px;"><span><input type="checkbox" name="containSalary" value="1" checked> 工资 &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containSB" value="1" checked> 社保公积金 &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containCB" value="1" checked> 商保<br/><input type="checkbox" name="containOther" id="containOther" value="1" checked> 其他 &nbsp;&nbsp;&nbsp; <input type="checkbox" name="containServiceFee" id="containServiceFee" value="1" checked> 服务费</span></div></li></ol>'
		);
		
		// 其他和服务费需要同时选中
		$('#containServiceFee').click(function(){
			if($(this).attr('checked') == 'checked'){
				$('#containOther').attr('checked', 'checked');
			}else{
				$('#containOther').removeAttr('checked');
			}
		});
		
		// 其他和服务费需要同时选中
		$('#containOther').click(function(){
			if($(this).attr('checked') == 'checked'){
				$('#containServiceFee').attr('checked', 'checked');
			}else{
				$('#containServiceFee').removeAttr('checked');
			}
		});
		
		// 设置会计期间的最小时间
		$('#accountPeriod').attr('onFocus', "WdatePicker({minDate:'" + $('#accountPeriod').val() + "'})");
	})(jQuery);
</script>

