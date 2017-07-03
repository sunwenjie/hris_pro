<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderCBVO"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%
		if(request.getAttribute("role").equals("1")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_CB", "clientOrderCBForm", true ));
		}else if(request.getAttribute("role").equals("2")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_CB_IN_HOUSE", "clientOrderCBForm", true ));
		}
   	%>
</div>
							
<script type="text/javascript">
	(function($) {	
		// 显示订单名称和订单ID
		$('.required').parent().after(
			'<ol class="auto"><li><label><logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal><logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal></label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderCBForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderCBForm" property="orderHeaderId"/></a></span></li></ol>'
		);

		// JS of the List
		<%
			final String orderTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.page.title" );
			final String cbTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.cb.page.title" );
			final String newP = KANUtil.getProperty( request.getLocale(), "oper.new" );
			final String editP = KANUtil.getProperty( request.getLocale(), "oper.edit" );
			final String viewP = KANUtil.getProperty( request.getLocale(), "oper.view" );
			final boolean en_ = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
			
			final StringBuffer initCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   initCallBack.append( "if(getSubAction()=='createObject'){$('#pageTitle').html('" + orderTitle + " - " + newP + ( en_ ? " " : "" ) + cbTitle + "');}else{" );
		       initCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + cbTitle + ( en_ ? " " : "" ) + viewP + "');}"  );
			}
			
			final StringBuffer editCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   editCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + cbTitle + ( en_ ? " " : "" ) + editP + "');"  );
			}
		%>
		<%
			if(request.getAttribute("role").equals("1")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_CB", initCallBack.toString(), editCallBack.toString(), null, null ));
			}else if(request.getAttribute("role").equals("2")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_CB_IN_HOUSE", initCallBack.toString(), editCallBack.toString(), null, null ));
			}
		%>
		
		//绑定 “不全月免费”和“按全月计费”不能同时选“是”
		$('#freeShortOfMonth').change(function(){
			if( $(this).val() == 2){
				$('#chargeFullMonthLI').show();
			}else{
				$('#chargeFullMonth').val('0');
				$('#chargeFullMonthLI').hide();
			}
		});
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		$("#btnList").click(function(){
	        link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderCBForm" property="encodedOrderHeaderId"/>');
		});	
	})(jQuery);
</script>

