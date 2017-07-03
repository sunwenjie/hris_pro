<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.hro.domain.biz.vendor.VendorContactVO"%>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_VENDOR_CONTACT", "vendorContactForm", true ) %>
</div>

<!-- popup box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchVendor.jsp"></jsp:include>
</div>		

<script type="text/javascript">

		// 非新建状态，添加快速操作
		if(getSubAction() != 'createObject')
		{
			$('#vendorIdLI label').append('&nbsp;<a onclick="link(\'vendorAction.do?proc=to_objectModify&id=<bean:write name="vendorContactForm" property="encodedVendorId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}

		//添加“搜索服务协议信息”
		$('#vendorIdLI').append('<a onclick="popupVendorSearch();" class="kanhandle"><img src="images/search.png" title="<bean:message bundle="public" key="button.search" />" /></a>');

		//JS of the List 系统自动生成js代码
		<% 
			final StringBuffer initCallBack = new StringBuffer();
			// 获取错误消息
			final String vendorIdErrorMsg = ( String )request.getAttribute( "vendorIdErrorMsg" );
			
			if( KANUtil.filterEmpty( vendorIdErrorMsg ) != null )
			{
			   initCallBack.append( "addError('vendorId', '" + vendorIdErrorMsg + "');" );
			}
			
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append( "if($('.vendor_user_username').val() != ''){" );
			editCallBack.append( "$('.vendor_user_username').attr('disabled', 'disabled');" );
			editCallBack.append( "$('#resetPassword').show();}" );
			editCallBack.append( "$('#vendorId').addClass('important');" );
			editCallBack.append( "$('#vendorNameZH').attr('disabled', 'disabled');" );
			editCallBack.append( "$('#vendorNameEN').attr('disabled', 'disabled');" );
			
			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("if($('.vendor_user_username').val() != ''){");
			submitAdditionalCallback.append("flag = flag + validate('bizEmail',true, 'email', 0 ,0, 0,0);");
			submitAdditionalCallback.append("}");
		%>
		
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_VENDOR_CONTACT", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallback.toString() ) %>
		
		/**
	     * define  定义变量 
	    **/
		
		var vendorId = '<bean:write name="vendorContactForm" property="encodedVendorId"/>';
		var disable = true; 
		if(getSubAction() == 'createObject'){
			$('#vendorId').addClass('important');
			$('#vendorNameZH').attr('disabled', 'disabled');
			$('#vendorNameEN').attr('disabled', 'disabled');
			disable = false;
		}
		
	    /**
	     * must excue code first 必须先执行的JS代码
	    **/
	    
	 	//初始化省份控件
		provinceChange('provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');
	
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		/**
	     * loadHtml 加载页面
	    **/
	
		loadHtml('#special_info', 'vendorContactAction.do?proc=list_special_info_html&vendorContactId=<bean:write name="vendorContactForm" property="encodedId"/>', disable);
		
		/**
	     * bind 绑定事件
	    **/
	    
		// 绑定省份Change事件
		$('.provinceId').change( function () { 
			provinceChange('provinceId', 'modifyObject', 0, 'cityId');
		});
		
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 供应商ID keyup事件
		$("#vendorId").bind('keyup', function(){
			if($("#vendorId").val()!=''){
				$.ajax({url: 'vendorAction.do?proc=get_object_json&vendorId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						cleanError('vendorId');
						
						if(data.success == 'true'){
							$('#vendorNameZH').val(data.nameZH);
							$('#vendorNameEN').val(data.nameEN);
						}else{
							//$('#vendorId').val('');
							$('#vendorNameZH').val('');
							$('#vendorNameEN').val('');
							addError('vendorId', '<bean:message bundle="public" key="error.input.id.invalid" />');  
						}
					}
				});
			}
		});
	
		<%
			final String type = request.getParameter("type");
		
			if( "return".equals(type) )
			{
			   out.println("$('#btnList').unbind( \"click\" );");
			   out.println("$('#btnList').val(\"" + KANUtil.getProperty( request.getLocale(), "button.back.fh" ) + "\");"); 
			   out.println("$('.manage_primary_form').attr( 'action', 'vendorContactAction.do?proc=add_object&type=return');"); 
			   out.println("$('#btnList').click(function(){link(\"vendorAction.do?proc=to_objectModify&id=\" + vendorId);});"); 
			   out.println("$(\"#vendorId\").keyup();");
			}
			
		%>
		
		<%
			if( BaseAction.getRole(request, response) != null && (BaseAction.getRole(request, response).equals(KANConstants.ROLE_VENDOR)))
			{
			   out.println("$('#btnList').unbind( \"click\" );");
			   out.println( "$('#btnList').val('" + KANUtil.getProperty( request.getLocale(), "button.back.fh" ) + "');" );
			   out.println( "$('#btnList').click(function(){link(\"vendorAction.do?proc=to_objectModify_inVendor\");});");
			}
		%>
</script>
