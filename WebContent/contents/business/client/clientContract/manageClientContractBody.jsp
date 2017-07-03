<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientContractVO"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<% 
	final ClientContractVO clientContractVO = (ClientContractVO) request.getAttribute("clientContractForm"); 
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_CLIENT_CONTRACT", "clientContractForm", true ) %>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
</div>

<%-- 是否因输入错误重置页面 --%>
<input id="rePageFlag" name="rePageFlag" type="hidden" value="false"/>
<script type="text/javascript">
	(function($) {	
		// JS of the List
		<%
			final StringBuffer submitAdditionalCallBack = new StringBuffer();
			submitAdditionalCallBack.append( "if(flag==0){$('#status').attr('disabled', false);}" );
		
			final StringBuffer editCallBack = new StringBuffer();
			editCallBack.append("$('#clientId').addClass('important');");
			editCallBack.append("$('#clientNameZH').attr('disabled', 'disabled');");
			editCallBack.append("$('#clientNameEN').attr('disabled', 'disabled');");
			editCallBack.append("$('#status').attr('disabled', 'disabled');");
			
			final StringBuffer initCallBack = new StringBuffer();
			if(request.getAttribute("clientIdError") != null){
				final String clientIdError = (String)request.getAttribute("clientIdError");
				initCallBack.append("addError('clientId', '" + clientIdError + "');");
				initCallBack.append("$('#rePageFlag').val('true');");
				initCallBack.append("if(getSubAction() == 'viewObject'){");
				initCallBack.append("$('#btnEdit').trigger('click');");
				initCallBack.append("}" );
			}

			if(clientContractVO != null && clientContractVO.getStatus() != null && !clientContractVO.getStatus().trim().equals("3") && !clientContractVO.getStatus().trim().equals("5") && !clientContractVO.getStatus().trim().equals("6")){
			   editCallBack.append("disableLinkById('#addOrder');");
			}
		%>
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_CLIENT_CONTRACT", initCallBack.toString(), editCallBack.toString(), null, submitAdditionalCallBack.toString() ) %>
		
		// 初始化工作流查看按钮
		loadWorkflowSeach('status','<bean:write name="clientContractForm" property="workflowId"/>');
		
		// 如果不是新建和退回状态，隐藏编辑按钮
		if($('#status').val() != '1' && $('#status').val() != '4'){
			$('#btnEdit').hide();
		}

		// 盖章完成的合同可以输入合同编号（即归档号）
		if($('#status').val() == '5' ){
			$('#contractNo').attr('disabled', false);
		}
		
		// 页面初始化 - 显示“下一步”按钮
		$('#btnNext').show();
		$('#btnNext').click( function () { generateContract(); } );
		
		// 添加“搜索客户信息”
		$('#clientIdLI').append('<a onclick="popupClientSearch()" class="kanhandle"><img src="images/search.png" title="搜索客户记录" /></a>');
		
		$('#clientIdLI label').append('&nbsp;');
		
		// 非新增模式添加“查看客户信息”
		if(getSubAction() != 'createObject' && $('#rePageFlag').val() == 'false'){
			$('#clientIdLI label:eq(0)').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=<bean:write name="clientContractForm" property="encodedClientId" />\');" ><img src="images/find.png" title="查看客户记录" /></a>');
		}
	
		// 加载Tab
		loadHtml('#special_info', 'clientContractAction.do?proc=list_special_info_html&clientContractId=' + $('#id').val(), getDisable());
		
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
							$('#clientIdLI label').append('<a onclick="link(\'clientAction.do?proc=to_objectModify&id=' + data.encodedId + '\');" ><img src="images/find.png" title="查看客户记录" /></a>');
						}else if(data.success == 'false'){
							$('#clientId').val('');
							$('#clientNameZH').val('');
							$('#clientNameEN').val('');
							addError('clientId', '客户ID无效');
						}

						$('#entityId').val(data.legalEntity);
						$('#entityId').change();
						loadContractOptions();
						loadInvoiceAddressOptions();
					}
				});
			}
		});
		
		// 绑定法务实体Change事件
		$('#entityId').change(function (){
			$.ajax({url: 'entityAction.do?proc=get_object_json&entityId=' + $(this).val() + '&date=' + new Date(),
				dataType : 'json',
				success: function(data){
					if(data.success == 'true'){
						if($('#businessTypeId').val() == '0'){
							$('#businessTypeId').val(data.bizType);
						}
						$('#businessTypeId').change();
					}
				}
			});
		});
		
		// 绑定业务类型Change事件
		$('#businessTypeId').change(function (){
			var date = new Date();
			var contractName = $('#businessTypeId option:selected').text() + '合同 - ' + date.getFullYear();
			if(date.getMonth() + 1 < 10){
				contractName = contractName + "0";
			}
			contractName = contractName + eval(date.getMonth() + 1);
			
			if($('#nameZH').val() == ''){
				$('#nameZH').val(contractName);
			}
			//jzy add 2014/04/11 联动时候清空合同下拉框
			$('#templateId')[0].length=0;
			loadBusinessContractTemplateOptions();
		});
		
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
	})(jQuery);
	
	// 当前是否需要Disable
	function getDisable(){
		if($('.manage_primary_form input#subAction').val() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// To Next Page - Generate Contract
	function generateContract(){
		var flag = 0;

		//	验证合同模板
		if(checkContractTemplate()){
			flag = flag + 1;
			addError('templateId', '请选择合同模板或上传客户版合同；');
			$('#templateId').removeAttr('disabled');
		}
		
		if(flag == 0 && validate_manage_primary_form() == 0){
			$('.manage_primary_form').attr('action', 'clientContractAction.do?proc=generate_contract');
			if($('.manage_primary_form #subAction').val() == 'viewObject'){
				$('.manage_primary_form #subAction').val('modifyObject');
			}
			enableForm('manage_primary_form');
			submitForm('manage_primary_form');
		}
	};

	//	验证合同模板事件
	function checkContractTemplate(){
		cleanError('templateId');
		var templateFlag = false;
		//	获得模板ID
		var templateID = $('#templateId').val();
		//	获得用户上传附件
		var attachmentLength = $('#attachmentsOL > li').length;
		
		if( templateID == 0 && attachmentLength == 0 )
		{
			templateFlag = true;
		}
		
		return templateFlag;		
	};
	
	// 加载主合同下拉框事件
	function loadContractOptions(contractId){
		loadHtml('#masterContractId', 'clientContractAction.do?proc=list_object_options_ajax&flag=contract&contractId=' + contractId + '&clientId=' + $('#clientId').val(), getDisable() );
	};
	
	// 加载发票地址下拉框事件
	function loadInvoiceAddressOptions(invoiceAddressId){
		loadHtml('#invoiceAddressId', 'clientInvoiceAction.do?proc=list_object_options_ajax&invoiceAddressId=' + invoiceAddressId + '&clientId=' + $('#clientId').val(), getDisable(), 'if($("#invoiceAddressId").children().length == 2 && getSubAction == "createObject"){$("#invoiceAddressId option:nth-child(2)").attr("selected" , "selected");}' );
	};
	
	// 加载合同模板下拉框事件
	function loadBusinessContractTemplateOptions(templateId){
		loadHtml('#templateId', 'businessContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val(), getDisable(), 'if($("#templateId").children().length == 2 && getSubAction == "createObject"){$("#templateId option:nth-child(2)").attr("selected" , "selected");}' );
	};
</script>
