<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_SB_RULE", "clientOrderDetailSBRuleForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		<%
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			
			submitAdditionalCallback.append("if($('#ruleType').val() == '1'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'numeric', 0, 0, 0, 0);");
			submitAdditionalCallback.append("}else if($('#ruleType').val() == '2' || $('#ruleType').val() == '3'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'numeric', 0, 0, 31, 1);");
			submitAdditionalCallback.append("}else if($('#ruleType').val() == '4' || $('#ruleType').val() == '5'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'numeric', 0, 0, 31, 0);");
			submitAdditionalCallback.append("}else if($('#ruleType').val() == '6'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'currency', 0, 0, 0, 0);");
			submitAdditionalCallback.append("}");
			submitAdditionalCallback.append("if($('#chargeType').val() == '1'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleResult', true, 'currency', 0, 0, 0, 0);");
			submitAdditionalCallback.append("}else if($('#chargeType').val() == '2'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleResult', true, 'numeric', 0, 0, 100, 0);");
			submitAdditionalCallback.append("}");
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_SB_RULE", null, null, null, submitAdditionalCallback.toString() ) %>
		
		
		$("#btnList").val('返回');
		
		// 返回按钮点击事件
		$("#btnList").unbind("click");
		$("#btnList").click(function(){
			if(agreest()){
				 link('clientOrderDetailAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailSBRuleForm" property="encodedOrderDetailId"/>');
			}
		});	
	})(jQuery);
</script>

