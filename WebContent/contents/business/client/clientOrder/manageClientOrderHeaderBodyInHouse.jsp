<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage(request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE", "clientOrderHeaderForm", true) %>
</div>

<%-- 是否因输入错误重置页面 --%>
<input id="rePageFlag" name="rePageFlag" type="hidden" value="false"/>

<html:form action="employeeContractAction.do?proc=list_object_order" styleClass="listServiceContract_form">
	<input type="hidden" name="sortColumn" id="sortColumn" value="" /> 
	<input type="hidden" name="sortOrder" id="sortOrder" value="" />
	<input type="hidden" name="page" id="page" value="" />
	<input type="hidden" name="subAction" id="subAction" value="" />
	<input type="hidden" name="selectedIds" id="selectedIds" value="" />
	<input type="hidden" name="bool" id="bool" value="" />
	<input type="hidden" name="orderId" id="orderId" value="<bean:write name="clientOrderHeaderForm" property="encodedId"/>" />
</html:form>

<script type="text/javascript">

	// 初始化工作流查看按钮
	loadWorkflowSeach('status','<bean:write name="clientOrderHeaderForm" property="workflowId"/>');
	
	//标记 - 订单是否必须绑定合同
	var orderBindContract = false;
	
	function checkContract(){
		if(orderBindContract){
			return validate("contractId", true, "select", "0", "0", "0", "0");
		}else{
			return 0;
		}
	};

	(function($) {
		// JS of the List
		<%
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append("$('#clientId').addClass('important');");
			editCallBack.append("$('#clientId').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			
			final StringBuffer initCallBack = new StringBuffer();
			
			if(request.getAttribute("clientIdError") != null)
			{
				final String clientIdError = (String) request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
			}

			final StringBuffer submitAdditionalCallback = new StringBuffer();
			submitAdditionalCallback.append("flag = flag + validate('otLimitByDay', false, 'currency', 2, 0, 10, 0);");
			submitAdditionalCallback.append("flag = flag + validate('otLimitByMonth', false, 'currency', 3, 0, 200, 0);");
			submitAdditionalCallback.append("flag = flag + checkContract();");
		%>
		<%= ManageRender.generateManageJS(request, "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE", null, editCallBack.toString(), null, submitAdditionalCallback.toString()) %>
		
		// 合同到期赋值
		if(getSubAction() == 'viewObject'){
			var noticeExpire = "<bean:write name='clientOrderHeaderForm' property='noticeExpire'/>";
 			if(noticeExpire){
				$("input[name=noticeExpire]").each(function(i){
					for(var i = 0;i<noticeExpire.length;i++){
						if($(this).val()==noticeExpire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}		
			var noticeProbationExpire = "<bean:write name='clientOrderHeaderForm' property='noticeProbationExpire'/>";
 			if(noticeProbationExpire){
				$("input[name=noticeProbationExpire]").each(function(i){
					for(var i = 0;i<noticeProbationExpire.length;i++){
						if($(this).val()==noticeProbationExpire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}
 			var noticeRetire = "<bean:write name='clientOrderHeaderForm' property='noticeRetire'/>";
 			if(noticeRetire){
				$("input[name=noticeRetire]").each(function(i){
					for(var i = 0;i<noticeRetire.length;i++){
						if($(this).val()==noticeRetire[i]){
							$(this).attr("checked","checked");						
						}
					}
				});
 			}
		}
		
		if(($('#status').val() == '1' && getSubAction() != 'createObject') || $('#status').val() == '4'){
			$('#btnSubmit').show();
			$('#btnCancel').show();
		}
		
		if($('#status').val() == '2' ||$('#status').val() == '6' || $('#status').val() == '7'){
			$('#btnEdit').hide();
		}
		
		if($('#status').val() == '3' || $('#status').val() == '5'){
			$('#btnStop').hide();
		}
		
		if($('#status').val() == '8'){
			$('#btnSubmit').show();
		}

		// 提交按钮事件
		$('#btnSubmit').click( function () {  
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=modify_object');
				}
				
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		$('#btnEdit').click(function(){
			if($('#bool').val()==""){
				$('#bool').val("false");
			}else{
				$('#bool').val("");
				alert("您所修改的结算规则只能影响到在修改结算规则后新增的劳动合同，如果需要修改历史劳动合同的结算规则，需要到员工-劳动合同中进行手工修改，或联系我们的客服");
			}
		});
		
		// 取消按钮事件
		$('#btnCancel').click( function () { 
			$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=cancel_object');
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		});
		
		// 终止按钮事件
		$('#btnStop').click( function () { 
			$('.manage_primary_form').attr('action', 'clientOrderHeaderAction.do?proc=stop_object');
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		});
	
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
			
		// 绑定部门Change事件
		$('.branch').change(function (){ 
			branchChange('branch', null, 0, 'owner');
		});	 
	
		// 添加Tab
		loadHtml('#special_info', 'clientOrderHeaderAction.do?proc=list_special_info_html&subAction=<bean:write name="clientOrderHeaderForm" property="subAction"/>&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>', getSubAction() == 'viewObject');
		
		// 非新增模式添加“查看客户信息”
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify_internal&id=<bean:write name="clientOrderHeaderForm" property="encodedClientId" />\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
		}
		
		// 客户ID输入事件
		$("#clientId").bind('keyup', function(){
			if($("#clientId").val().length >= 9){
				$.ajax({url: 'clientAction.do?proc=get_object_json&clientId=' + $(this).val() + '&date=' + new Date(),
					dataType : 'json',
					success: function(data){
						cleanError('clientId');
						$('#clientIdLI label em').next('a').remove();
						
						if(data.success == 'true'){
							$('#clientNameZH').val(data.nameZH);
							$('#clientNameEN').val(data.nameEN);
							$('#clientIdLI label').append('&nbsp;<a onclick="link(\'clientAction.do?proc=to_objectModify&id=' + data.encodedId + '\');" ><img src="images/find.png" title="<bean:message bundle="public" key="img.title.tips.view.detials" />" /></a>');
						}else if(data.success == 'false'){
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							addError('clientId', '客户ID无效');
						}
						
						$('#entityId').val(data.legalEntity);
						$('#entityId').change();
						loadContractOptions('<bean:write name="clientOrderHeaderForm" property="contractId"/>');
						loadInvoiceAddressOptions();

						$('#contractIdLI label').html($('#contractIdLI label').html().replace(' <em>*</em>', ''));
						orderBindContract = false;
						
						if(data.orderBindContract){
							$('#contractIdLI label').html($('#contractIdLI label').html() + ' <em>*</em>');
							orderBindContract = true;
						}
					}
				});
			}
		});
		
		// 绑定合同Change事件
		$('#contractId').change(function(){
			$.ajax({url: 'clientContractAction.do?proc=get_object_ajax&contractId=' + $(this).val() + '&date=' + new Date(),
				dataType: "json",
				success: function( data ){
					$('#entityId').val(data.entityId);
					$('#businessTypeId').val(data.businessTypeId);
					$('#invoiceAddressId').val(data.invoiceAddressId);
					$('#startDate').val(data.startDate);
					$('#endDate').val(data.endDate);
					$('#entityId').change();
				}
			});
		});
		
		// 法务实体Change事件
		$('#entityId').change(function(){
			$.ajax({url: 'entityAction.do?proc=get_object_json&entityId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if($('#contractId').val() == '0' || $('#businessTypeId').val() == '0'){
						$('#businessTypeId').val(data.bizType);
					}
					$('#businessTypeId').change();
				}
			});
		});
		
		// 业务类型Change事件
		$('#businessTypeId').change(function(){
			$.ajax({url: 'taxAction.do?proc=get_object_json&entityId=' + $('#entityId').val() + '&businessTypeId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					$('#taxId').val(data.taxId);
				}
			});
		});
		
		// 两个“计薪周期”开始大于结束一天（开始1号，结束31日例外）
		$('#circleStartDay').change(function(){
			if($('#circleStartDay').val() == 1){
				$('#circleEndDay').val(31);
			}else{
				$('#circleEndDay').val($('#circleStartDay').val() - 1);
			}
		});
		
		<logic:present name="orderBindContract">
			<logic:equal name="orderBindContract" value="true">
				$('#contractIdLI label').html($('#contractIdLI label').html() + ' <em>*</em>');
				orderBindContract = true;
			</logic:equal>
		</logic:present>
		
		// 添加“复制信息”按钮
		if($('#orderHeaderId').val() != ''){
			$('#btnList').after('<a name="copyInfo" title="<bean:message bundle="business" key="business.client.order.copy.tips" />" class="commonTools" id="copyInfo" onclick="if(confirm(\'<bean:message bundle="business" key="business.client.order.copy.popup" />\')){link(\'clientOrderHeaderAction.do?proc=copy_object&orderHeaderId=<bean:write name="clientOrderHeaderForm" property="encodedId"/>&com.kan.token=' + $("#com.kan.token").val() + '\');}"><u><bean:message bundle="business" key="business.client.order.copy" /></u></a>');
		}
	})(jQuery);
	
	// 加载发票地址下拉框
	function loadInvoiceAddressOptions(invoiceAddressId){
		loadHtml('#invoiceAddressId', 'clientInvoiceAction.do?proc=list_object_options_ajax&invoiceAddressId=' + invoiceAddressId + '&clientId=' + $('#clientId').val(), getSubAction() == 'viewObject', 'if($("#invoiceAddressId").children().length == 2 && getSubAction() != "viewObject"){$("#invoiceAddressId option:nth-child(2)").attr("selected" , "selected");}' );
	};
	
	// 加载合同下拉框
	function loadContractOptions(contractId){
		loadHtml('#contractId', 'clientContractAction.do?proc=list_object_options_ajax&flag=order&contractId=' + contractId + '&clientId=' + $('#clientId').val(), getSubAction() == 'viewObject', '' );
	};
	
</script>

