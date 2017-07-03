<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_HEADER_RULE", "clientOrderHeaderRuleForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// 显示订单名称和订单ID
		$('.required').parent().after(
			'<ol class="auto"><li><label>订单ID</label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderHeaderRuleForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderHeaderRuleForm" property="orderHeaderId"/></a></span></li></ol>'
		);
		
		<%
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			
			submitAdditionalCallback.append("if($('#ruleType').val() == '1'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'numeric', 10, 0, 0, 0);");
			submitAdditionalCallback.append("}else if($('#ruleType').val() == '2'){");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'currency', 20, 0, 0, 0);");
			submitAdditionalCallback.append("}else{");
			submitAdditionalCallback.append("flag = flag + validate('ruleValue', true, 'common', 0, 0, 0, 0);");
			submitAdditionalCallback.append("}");
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_HEADER_RULE", null, null, null, submitAdditionalCallback.toString() ) %>
		
		$("#btnList").val('返回');
		
		$("#btnList").click(function(){
			if(agreest())
	        	link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderHeaderRuleForm" property="encodedOrderHeaderId"/>');
		});
		
		$('#ruleType').change();
	})(jQuery);
</script>

