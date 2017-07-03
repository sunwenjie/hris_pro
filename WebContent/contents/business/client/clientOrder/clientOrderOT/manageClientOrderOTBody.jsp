<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%
		if(request.getAttribute("role").equals("1")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_OT", "clientOrderOTForm", true ));
		}else if(request.getAttribute("role").equals("2")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_OT_IN_HOUSE", "clientOrderOTForm", true ));
		}
   	%>
</div>
							
<script type="text/javascript">
	(function($) {
		// 显示订单名称和订单ID
		$('.required').parent().after(
			'<ol class="auto"><li><label><logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal><logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal></label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOTForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderOTForm" property="orderHeaderId"/></a></span></li></ol>'
		);
		
		// “薪资来源”添加Change事件
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
			if(request.getAttribute("role").equals("1")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_OT", null, null, null, null ));
			}else if(request.getAttribute("role").equals("2")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_OT_IN_HOUSE", null, null, null, null ));
			}
	   	%>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		$("#btnList").click(function(){
			if(agreest())
			 link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderOTForm" property="encodedOrderHeaderId"/>');
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

