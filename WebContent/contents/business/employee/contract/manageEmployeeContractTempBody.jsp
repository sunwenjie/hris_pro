<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractVO"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<% 
	final EmployeeContractVO employeeContractVO = (EmployeeContractVO)request.getAttribute("employeeContractTempForm"); 
%>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, EmployeeContractAction.getAccessAction( request, response ), "employeeContractTempForm", true ) %>
	<input id="comeFrom" name="comeFrom" type="hidden" value="<%=(request.getParameter("comeFrom")==null||request.getParameter("comeFrom").isEmpty())?"":request.getParameter("comeFrom")%>"/>
	<input id="encodedEmployeeId" name="encodedEmployeeId" type="hidden" value="" />
	<input id="encodedOrderId" name="encodedOrderId" type="hidden" value="" />
	<input id="encodedClientId" name="encodedClientId" type="hidden" value="" />
</div>

<%-- 是否因输入错误重置页面 --%>
<input id="rePageFlag_orderId" name="rePageFlag_orderId" type="hidden" value="false"/>
<input id="rePageFlag_employeeId" name="rePageFlag_employeeId" type="hidden" value="false"/>

<script type="text/javascript">
	(function($) {	
		// 设置顶部菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_ImportLaborContract').addClass('selected');
		var overlap = false;
		// JS of the List
		<%
			final StringBuffer initCallBack = new StringBuffer();
			initCallBack.append("init();");
			initCallBack.append("$('#entityId').attr('disabled', 'disabled');");
			initCallBack.append("$('#businessTypeId').attr('disabled', 'disabled');");

			if(request.getAttribute("orderIdError") != null)
			{
				final String orderIdError = (String)request.getAttribute("orderIdError");
				initCallBack.append("addError('orderId', '" + orderIdError + "');");
				initCallBack.append("$('#rePageFlag_orderId').val('true');");
			}
			
			if(request.getAttribute("employeeIdError") != null)
			{
				final String employeeIdError = (String)request.getAttribute("employeeIdError");
				initCallBack.append("addError('employeeId', '" + employeeIdError + "');");
				initCallBack.append("$('#rePageFlag_employeeId').val('true');");
			}
		
		%>
		
		<%= ManageRender.generateManageJS( request, EmployeeContractAction.getAccessAction( request, response ), initCallBack.toString(), null, null,null) %>

		$('#menu_employee_ServiceAgreement').removeClass('selected');
		// 根据条件添加“查看雇员信息”、“查看订单信息”
		if(getSubAction() != 'createObject'){
			if( $('#rePageFlag_orderId').val() == 'false')
			{
				$('#orderIdLI label:eq(0)').append(' <a onclick="link(\'clientOrderHeaderAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedOrderId" />\');" ><img src="images/find.png" title="查看订单记录" /></a>');
			}
			if( $('#rePageFlag_employeeId').val() == 'false')
			{
				$('#employeeIdLI label:eq(0)').append(' <a onclick="link(\'employeeAction.do?proc=to_objectModify&id=<bean:write name="employeeContractTempForm" property="encodedEmployeeId" />\');" ><img src="images/find.png" title="查看雇员记录" /></a>');
			}
		}
		
		loadServiceContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
		
		// 隐藏按钮
		$('#reAdd,#btnAdd,#btnEdit,#btnNext').hide();
		$('#btnList').val("返回").click(function(){
			link('employeeContractTempAction.do?proc=list_object&batchId=<bean:write name="employeeContractTempForm" property="batchId" />');
		});
		
		// Load Special Info
		loadHtml('#special_info', 'employeeContractTempAction.do?proc=list_special_info_html&contractId=' + $('#id').val() + '&flag=' + $('#flag').val(), getDisable(),function(){
			// 禁用超链接
			$('#tab a').attr('onClick','');
		});
		
		// 添加“搜索雇员信息”
		$('#employeeId').after('<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle"><img src="images/search.png" title="搜索雇员记录" /></a>');
		
		// 添加“搜索订单信息”
		$('#orderId').after('<a id="orderSearch" onclick="popupOrderSearch();" class="kanhandle"><img src="images/search.png" title="搜索订单记录" /></a>');
		
		// 初始化部门控件
		branchChange('branch', null, $('#temp_owner').val(), 'owner');
		
		// 绑定部门Change事件
		$('.branch').change( function () { 
			branchChange('branch', null, 0, 'owner');
		});	
		
		// 绑定法务实体change事件
		$('#entityId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
			loadContractOptions('<bean:write name="employeeContractTempForm" property="masterContractId"/>');
		});
		
		//	绑定业务类型change事件
		$('#businessTypeId').change(function (){
			loadLaborContractTemplateOptions('<bean:write name="employeeContractTempForm" property="templateId"/>');
		});
		
		function hasConflictContractInOneClient(){
			// 验证是否存在服务协议
			if($('#employeeId').val()!='' && $('#clientId').val() !='' && $('#entityId').val()!='' && $('#entityId').val()!=0 && $('#startDate').val() != '' && $('#endDate').val() != '' && $('.subAction').val()!='viewObject' ){
				$.post('employeeContractAction.do?proc=hasConflictContractInOneClient',
						{'clientId':$('#clientId').val(),
						 'employeeId':$('#employeeId').val(),
						 'startDate':$('#startDate').val(),
						 'contractId':$('#contractId').val(),
						 'endDate':$('#endDate').val(),
						 'entityId':$('#entityId').val(),
						 'flag':'2'},
				function(data){
						if(data=='1'){
							$('.startDate_error2').remove();
							$('#startDate').after("<label class='error startDate_error2 '>&#8226; 该时间段内已存在劳动合同</label>");
							overlap = true;
						}else{
							$('.startDate_error2').remove();
							overlap = false;
						}
				},'text');
			};
			return false;
		}
		

	})(jQuery);
	
	// 当前是否需要Disable
	function getDisable(){
		if(getSubAction() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// 加载主合同下拉框事件
	function loadContractOptions(masterContractId){
		loadHtml('#masterContractId', 'employeeContractAction.do?proc=list_object_options_ajax&contractId=' + masterContractId + '&employeeId=' + $('#employeeId').val()+ '&entityId=' + $('#entityId').val(), getDisable() );
	};
	
	// 加载合同模板下拉框事件
	function loadServiceContractTemplateOptions(templateId){
		loadHtml('#templateId', 'laborContractTemplateAction.do?proc=list_object_options_ajax&templateId=' + templateId + '&entityId=' + $('#entityId').val() + '&businessTypeId=' + $('#businessTypeId').val() + '&clientIdPage='+$('#clientId').val()+'&contractTypeId=2', getDisable() );
	};	
	
	// 加载直线经理下拉框事件
	function loadLineManagerOptions(lineManagerId){
		loadHtml('#lineManagerId', 'clientContactAction.do?proc=list_object_options_ajax&contactId=' + lineManagerId + '&clientId=' + $('#encodedClientId').val(), getDisable() );
	};

	function generateContract(){
		if(validate_manage_primary_form() == 0){
			// 验证合同模板或者附件不能为空
			if($('#templateId').val()==0 && $('#attachmentsOL #attachmentArray').size()==0){
				alert("缺少合同模板或者附件！");
				return false;
			}	
			
			// 页面修改数据保存提醒
			if(getSubAction() == 'modifyObject' && !confirm("页面修改数据将被保存！")){
				return false;
			}
			
			// Update the form action
			$('.manage_primary_form').attr('action', 'employeeContractAction.do?proc=generate_contract');
			
			// Enable the form
			enableForm('manage_primary_form');
			submit('manage_primary_form');
		};
	};
	
	function init(){
		if($(".subAction").val()=='viewObject'){
			// 添加  再次添加 按钮
			$("#btnEdit").before("<input type='button' name='reAdd' id='reAdd' value='新增' onclick='reAdd();'>");
			$("#pageTitle").html("雇员 - 派遣协议查询");
		}else if($(".subAction").val()=='createObject'){
			$("#pageTitle").html("雇员 - 派遣协议新增");
		}
	};
	function reAdd(){
		link("employeeContractAction.do?proc=to_objectNew&flag=2&comeFrom=2 />");
	}
	function doEdit(){
		$("#pageTitle").html("雇员 - 派遣协议编辑");
	}
</script>
				
