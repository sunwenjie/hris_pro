<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%
		if(request.getAttribute("role").equals("1")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_LEAVE", "clientOrderLeaveForm", true ));
		}else if(request.getAttribute("role").equals("2")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_LEAVE_IN_HOUSE", "clientOrderLeaveForm", true ));
		}
   	%>
</div>
							
<script type="text/javascript">
	(function($) {
		// 显示订单名称和订单ID
		$('.required').parent().after(
			'<ol class="auto"><li><label><logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal><logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal></label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderLeaveForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderLeaveForm" property="orderHeaderId"/></a></span></li></ol>'
		);

		$('#annualLeaveRuleIdLI').hide();
		// “科目”为年假时，“法定数量才会显示”
		$('#itemId').change(function(){
			if($(this).val() == 41){
				
				if($('#delayUsing').val() == 1){
					$('#legalQuantityDelayMonthLI').show();
				}
				$('#yearLI').show();
				$('#legalQuantityLI').hide();
				$('#benefitQuantityLI').hide();
				$('#annualLeaveRuleIdLI').show();
				$('#cycle').val('5');
				$('#cycleLI').hide();
				/* $('#benefitQuantityLI label').html('<bean:message bundle="business" key="business.client.order.leave.benefit.quantity" /><em> *</em>');
				$('#legalQuantityLI').after($('#benefitQuantityLI')); */
			}else{
				$('#yearLI').hide();
				$('#legalQuantityLI').hide();
				$('#benefitQuantityLI').show();
				$('#annualLeaveRuleIdLI').hide();
				//$('#cycle').val('0');
				$('#cycleLI').show();
				$('#benefitQuantityLI label').html('<bean:message bundle="business" key="business.client.order.leave.legal.quantity.hours" /><em> *</em>');
			}
		});
		
		$('#itemId').change();
		
		//	当“延迟可用”选择是，则显示“休假可延期（月）”，年假情况显示“法定休假可延期（月）”
		$('#delayUsing').change(function(){
			if($(this).val() == 1){
				$('#benefitQuantityDelayMonthLI').show();
				if($('#itemId').val() == 41){
					$('#legalQuantityDelayMonthLI').show();
				}
			}else{
				$('#benefitQuantityDelayMonthLI').hide();
				$('#legalQuantityDelayMonthLI').hide();
			}
		});
		
		$('#delayUsing').change();
		
		// JS of the List
		<%
			final String orderTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.page.title" );
			final String leaveTitle = KANUtil.getProperty( request.getLocale(), "menu.table.title.leave" );
			final String newP = KANUtil.getProperty( request.getLocale(), "oper.new" );
			final String editP = KANUtil.getProperty( request.getLocale(), "oper.edit" );
			final String viewP = KANUtil.getProperty( request.getLocale(), "oper.view" );
			final boolean en_ = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
			
			final StringBuffer initCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
		       initCallBack.append( "if(getSubAction()=='createObject'){$('#pageTitle').html('" + orderTitle + " - " + newP + ( en_ ? " " : "" ) + leaveTitle + "');}else{" );
		       initCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + leaveTitle + ( en_ ? " " : "" ) + viewP + "');}"  );
			}
			
			final StringBuffer editCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   editCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + leaveTitle + ( en_ ? " " : "" ) + editP + "');"  );
			}
			
			final StringBuffer submitCallBack = new StringBuffer();
			submitCallBack.append( "if(checkClientOrderLeave()==true){flag ++;addError('itemId','已经存在该科目类型的休假设置；')}" );
		%>
		<%
			if(request.getAttribute("role").equals("1")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_LEAVE", initCallBack.toString(), editCallBack.toString(), null, null ));
			}else if(request.getAttribute("role").equals("2")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_LEAVE_IN_HOUSE", initCallBack.toString(), editCallBack.toString(), submitCallBack.toString(), null ));
			}
	   	%>
		
	   	$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		$("#btnList").click(function(){
			if(agreest())
			 link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderLeaveForm" property="encodedOrderHeaderId"/>');
		});
	})(jQuery);
	
	function checkClientOrderLeave(){
		var returnValue = false;
		$.ajax({
			url : "clientOrderLeaveAction.do?proc=checkClientOrderLeave_ajax&orderLeaveId=<bean:write name="clientOrderLeaveForm" property="orderLeaveId" />&orderHeaderId=<bean:write name="clientOrderLeaveForm" property="orderHeaderId" />&itemId=" + $('#itemId').val() + "&year=" + $('#year').val(), 
			type: 'POST',
			async:false,
			success : function(data){
				if(data=='2'){
					returnValue = true;
				}
			}
		});
		return returnValue;
	};
</script>

