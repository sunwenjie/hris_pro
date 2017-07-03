<%@page import="com.kan.base.util.KANUtil"%>
<%@page import="com.kan.hro.domain.biz.vendor.VendorVO"%>
<%@page import="com.kan.base.web.renders.util.ManageRender"%>
<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.base.util.KANConstants"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<% 
	final VendorVO vendorVO = (VendorVO)request.getAttribute("vendorForm");
	request.setAttribute("vendorForm" , vendorVO);
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_VENDOR", "vendorForm", true ) %>
</div>
							
<script type="text/javascript">
	(function($) {
		// JS of the List
		$('.manage_primary_form ').append('<input type=\"hidden\" id=\"forwardURL\" name=\"forwardURL\" value=\"\" />');
			
		loadWorkflowSeach('status','<bean:write name='vendorForm' property='workflowId' />');
		
		<%
			final VendorVO vendorForm = ( VendorVO )request.getAttribute( "vendorForm" );
			final StringBuffer initCallBack = new StringBuffer(); 
			if( vendorForm != null && KANUtil.filterEmpty( vendorForm.getWorkflowId() ) != null )
			{
			   initCallBack.append( "$('#btnEdit').hide();" );
			}
			if( BaseAction.getRole(request, response) != null && (BaseAction.getRole(request, response).equals(KANConstants.ROLE_VENDOR)))
			{
			   initCallBack.append( "$('#btnList').hide();" );
			}
	
			final StringBuffer submitAdditionalCallBack = new StringBuffer();
			submitAdditionalCallBack.append( "if(flag==0){" );
			submitAdditionalCallBack.append( "var html = $('#attachmentsOL').html();" );
			submitAdditionalCallBack.append( "$('#special_info').html(html);}" );
			
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append( "$('#uploadAttachment').show();" );
			editCallBack.append( "$('#addVendorContact').attr('disabled', false);" );
			editCallBack.append( "$('#addVendorContact').attr('onclick', $('#addVendorContact').attr('onclick').substring(1));" );
			editCallBack.append( "$('#addVendorContact').removeClass('disabled');" );
			editCallBack.append( "$('#status').attr( 'disabled', true );" );
			editCallBack.append( "if( status == '3' ){" );
			editCallBack.append( "$('#btnSubmit').show();" );
			editCallBack.append( "$('#btnEdit').hide();" );
			editCallBack.append( "}" );
		%>
		
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_VENDOR", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallBack.toString() ) %>
		
		/**
		* define  定义变量
		**/
		var status =  '<bean:write name="vendorForm" property="status"/>';
		
		var subAction = getSubAction();
		
		var vendorId = '<bean:write name="vendorForm" property="vendorId"/>';
	
		var disable = true;
		if( subAction == 'createObject' ){
			disable = false;
		}
		
		/**
		* must excue code first 必须先执行的JS代码
		**/
		$('#status').attr( 'disabled', true );
		
		/**
		* loadHtml 加载页面
		**/

		// 加载联系人下拉框
		loadHtml('#mainContact', 'vendorContactAction.do?proc=list_object_options_ajax&vendorId=<bean:write name="vendorForm" property="encodedId"/>', true );

		//初始化省份控件
		provinceChange('provinceId', 'viewObject', $('#temp_cityId').val(), 'cityId');
		
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		<logic:notEqual name="vendorForm" property="subAction" value="createObject">
			// 加载Tab页面
			loadHtml('#append_info', 'vendorAction.do?proc=list_special_info_html&vendorId=' + vendorId, false );  
		</logic:notEqual>
		
		// 根据状态显示按钮
		if( status == '1' || status == '4' ){
		 	$('#btnSubmit').show(); 	// 新建、退回显示提交按钮
		 	$('#btnEdit').show(); 
		}else if( status == '2' ){
			$('#btnEdit').hide(); 	    // 待审核隐藏编辑按钮 
		}
		
		/**
		* bind 绑定事件
		**/
		
		// 提交按钮事件
		$('#btnSubmit').click( function () { 
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'vendorAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// 绑定省份Change事件
		$('.provinceId').change( function () { 
			provinceChange('provinceId', 'modifyObject', 0, 'cityId');
		});
	
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 绑定编辑按钮点击事件
		$('#btnEdit').click( function () { 
			if(getSubAction() == 'modifyObject'){
				// 上传链接生效
				$('#addVendorContact').removeAttr("readonly");
				$('#addVendorContact').removeClass("disabled");
				// Enable删除小图标
	   			$('img[id^=warning_img]').each(function(i){
	   				$(this).show();
	   			});
	   			$('img[id^=disable_img]').each(function(i){
	   				$(this).hide();
	   			});
			}
		});

		// 主联系人更改自动修改部分信息
		$('#mainContact').bind('change', function(){
			$.ajax({url: 'vendorContactAction.do?proc=get_object_json&vendorContactId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'Json',
				success : function(data){
					cleanError('mainContact');
					if(data.success = 'true'){
						$('#phone').val(data.bizPhone);
						$('#fax').val(data.fax);
						$('#email').val(data.bizEmail);
						$('#cityId').val(data.cityId);
						$('#address').val(data.address);
						$('#postcode').val(data.postcode);
						$('#branch').val(data.branch);
						$('#owner').val(data.owner);
					}
				}
			});
		});
		
		/***
		* init 初始化相关JS
		**/
		
		/***
		* other 其他代码
		**/
		
	})(jQuery);
</script>
