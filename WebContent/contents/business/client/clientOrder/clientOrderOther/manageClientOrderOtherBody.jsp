<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderOtherVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%
		if(request.getAttribute("role").equals("1")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_OTHER", "clientOrderOtherForm", true ));
		}else if(request.getAttribute("role").equals("2")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_OTHER_IN_HOUSE", "clientOrderOtherForm", true ));
		}
   	%>
</div>
							
<script type="text/javascript">
	(function($) {	
		// 显示订单名称和订单ID
		$('.required').parent().after(
			'<ol class="auto"><li><label><logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal><logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal></label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOtherForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderOtherForm" property="orderHeaderId"/></a></span></li></ol>'
		);
		
		// “金额来源”添加Change事件
		$('#baseFrom').change(function(){
			if($(this).val() != 0){
				$('#percentageLI').show();
				$('#fixLI').show();
			}else{
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});
		
		$('#baseFrom').change();
		
		// JS of the List
		<%
			final String orderTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.page.title" );
			final String otherTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.other.page.title" );
			final String newP = KANUtil.getProperty( request.getLocale(), "oper.new" );
			final String editP = KANUtil.getProperty( request.getLocale(), "oper.edit" );
			final String viewP = KANUtil.getProperty( request.getLocale(), "oper.view" );
			final boolean en_ = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
			
			final StringBuffer initCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   initCallBack.append( "if(getSubAction()=='createObject'){$('#pageTitle').html('" + orderTitle + " - " + newP + ( en_ ? " " : "" ) + otherTitle + "');}else{" );
		       initCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + otherTitle + ( en_ ? " " : "" ) + viewP + "');}"  );
			}
			
			final StringBuffer editCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   editCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + otherTitle + ( en_ ? " " : "" ) + editP + "');"  );
			}
		%>
		<%
			if(request.getAttribute("role").equals("1")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_OTHER", initCallBack.toString(), editCallBack.toString(), null, null ));
			}else if(request.getAttribute("role").equals("2")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_OTHER_IN_HOUSE", initCallBack.toString(), editCallBack.toString(), null, null ));
			}
	   	%>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		$("#btnList").click(function(){
			if(agreest())
			 link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOtherForm" property="encodedOrderHeaderId"/>');
		});

		var startDate = '<bean:write name="clientOrderHeaderVO" property="startDate" />';
		var endDate = '<bean:write name="clientOrderHeaderVO" property="endDate" />';
		
		$('#startDate').addClass('Wdate');
		$('#endDate').addClass('Wdate');
		
		$('#startDate').focus(function(){
			 WdatePicker({
				 minDate:startDate,
	    		 maxDate:'#F{$dp.$D(\'endDate\')||\''+ endDate +'\'}'
	    	 });
		});
		
		$('#endDate').focus(function(){
			 WdatePicker({
				 minDate:'#F{$dp.$D(\'startDate\')||\''+ startDate +'\'}',
				 maxDate:endDate
	    	 });
		});

	})(jQuery);
</script>

