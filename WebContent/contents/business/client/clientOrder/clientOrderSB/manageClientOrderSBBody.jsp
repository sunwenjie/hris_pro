<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>

<div id="content">
	<!-- Information Manage Form -->
	<%
		if(request.getAttribute("role").equals("1")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_SB", "clientOrderSBForm", true ));
		}else if(request.getAttribute("role").equals("2")){
		   out.print(ManageRender.generateManage( request, "HRO_BIZ_CLIENT_ORDER_SB_IN_HOUSE", "clientOrderSBForm", true ));
		}
  	%>
</div>
							
<script type="text/javascript">
	(function($) {
		// ��ʾ�������ƺͶ���ID
		$('.required').parent().after(
			'<ol class="auto"><li><label><logic:equal name="__COOKIE_USER_JSON" property="role" value="1"><bean:message bundle="public" key="public.order1.id" /></logic:equal><logic:equal name="__COOKIE_USER_JSON" property="role" value="2"><bean:message bundle="public" key="public.order2.id" /></logic:equal></label><span><a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderSBForm" property="encodedOrderHeaderId"/>\');"><bean:write name="clientOrderSBForm" property="orderHeaderId"/></a></span></li></ol>'
		);

		// JS of the List
		<%
			final String orderTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.page.title" );
			final String sbTitle = KANUtil.getProperty( request.getLocale(), "business.client.order.sb.page.title" );
			final String newP = KANUtil.getProperty( request.getLocale(), "oper.new" );
			final String editP = KANUtil.getProperty( request.getLocale(), "oper.edit" );
			final String viewP = KANUtil.getProperty( request.getLocale(), "oper.view" );
			final boolean en_ = request.getLocale().getLanguage().equalsIgnoreCase( "EN" );
			
			final StringBuffer initCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   initCallBack.append( "if(getSubAction()=='createObject'){$('#pageTitle').html('" + orderTitle + " - " + newP + ( en_ ? " " : "" ) + sbTitle + "');}else{" );
		       initCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + sbTitle + ( en_ ? " " : "" ) + viewP + "');}"  );
			}
			
			final StringBuffer editCallBack = new StringBuffer();
			if( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, null ) ) )
			{
			   editCallBack.append( "$('#pageTitle').html('" + orderTitle + " - " + sbTitle + ( en_ ? " " : "" ) + editP + "');"  );
			}
		%>
		<%
			if(request.getAttribute("role").equals("1")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_SB", initCallBack.toString(), editCallBack.toString(), null ));
			}else if(request.getAttribute("role").equals("2")){
			   out.print(ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_ORDER_SB_IN_HOUSE", initCallBack.toString(), editCallBack.toString(), null ));
			}
	  	%>
		
		$("#btnList").val('<bean:message bundle="public" key="button.back.fh" />');
		
		if($('#subAction').val() != 'createAction'){
			// ���ع�Ӧ��������
			loadHtml('#vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="clientOrderSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="clientOrderSBForm" property="vendorId"/>', false, '$("#sbSolutionId").change();');
		}
		
		// �����ViewObject,�����籣�����������򣨼�������������
		if(getSubAction() == 'viewObject') {
			loadHtml('.sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="clientOrderSBForm" property="sbSolutionId"/>', null, null);
		}
		// �����籣������������ֻ����δ��ӵģ�
		else{
			loadHtml('.sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_manage_ajax&orderHeaderId=<bean:write name="clientOrderSBForm" property="encodedOrderHeaderId"/>', null, null);
		}
		
		// �籣�����𷽰�Change�¼�
		$('#sbSolutionId').change(function(){
			// ���ع�Ӧ��������
			loadHtml('#vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $("#sbSolutionId").val() + '&vendorId=' + $('#vendorId').val(), false, '$("#vendorId").change();');
		});
		
		// ��Ӧ��Change�¼�
		$('#vendorId').change(function(){
			// ���ع�Ӧ�̷���������
			loadHtml('#vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('#sbSolutionId').val() + '&vendorId=' + $('#vendorId').val() + '&serviceId=<bean:write name="clientOrderSBForm" property="vendorServiceId" />',false,function(){
				if(getSubAction() != 'viewObject'){
					if($('#vendorServiceId option').size()==2){
						$('#vendorServiceId option').eq(1).attr('selected','seleccted');
					}else{
						$("#vendorServiceId").val("0");
					}
				}
			});
		});	
		
		$("#btnList").click(function(){
			if(agreest())
			 link('clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="clientOrderSBForm" property="encodedOrderHeaderId"/>');
		});
	})(jQuery);
	
	// ���ع�Ӧ���������¼�
	function loadVendorsOptions(){
		loadHtml('#vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('#sbSolutionId').val() + '&vendorId=<bean:write name="clientOrderSBForm" property="vendorId" />', getDisable(), '$(\'#vendorId\').change();' );
	};
</script>

