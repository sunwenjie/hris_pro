<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_RULE", "clientOrderDetailRuleForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// 显示订单名称
		$('.required').parent().after(
			'<logic:present name="itemVO"><ol class="auto"><li><label>科目</label><span><a onclick="link(\'itemAction.do?proc=to_objectModify&id=<bean:write name="itemVO" property="encodedId"/>\');"><bean:write name="itemVO" property="itemId"/> - <bean:write name="itemVO" property="itemNo"/></a></span></li><li><label>科目名称</label><span><bean:write name="itemVO" property="nameZH"/> - <bean:write name="itemVO" property="nameEN"/></span></li></ol></logic:present>'
		);
		
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
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_RULE", null, null, null, submitAdditionalCallback.toString() ) %>
		
		// “计算方式”是百分比，“规则结果”只能0 - 100
		$("#ruleResult").keyup(function(){
			if($("#ruleResult").val() != null && $("#chargeType").val() == "2"){
				if(parseFloat($("#ruleResult").val()) > 100){
					$("#ruleResult").val("100");
				}else if( parseFloat($("#ruleResult").val()) < 0 ){
					$("#ruleResult").val("0");
				}
			}
		});
		
		$("#btnList").val('返回');
		
		// 返回按钮点击事件
		$("#btnList").click(function(){
			if(agreest())
			 link('clientOrderDetailAction.do?proc=to_objectModify&id=<bean:write name="clientOrderDetailRuleForm" property="encodedOrderDetailId"/>');
		});	
	})(jQuery);
</script>

