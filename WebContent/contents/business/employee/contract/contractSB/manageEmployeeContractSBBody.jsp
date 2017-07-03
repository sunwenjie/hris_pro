<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANConstants"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSBVO"%>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB", "employeeContractSBForm", true ) %>
</div>
							
<script type="text/javascript">
	var status = null;

	(function($) {	
		// 如果 是Inhouse则隐藏sbbase
		<%
			if(KANConstants.ROLE_IN_HOUSE.equals(BaseAction.getRole(request,null))){
			   %>
				$("#sbBaseLI").hide();
			   <%
			}
		%>
		// 初始化状态
		status = $('#status').val();
		
		// 状态和退保日期默认不可编辑
		$('#status').attr('disabled', 'disabled');
		$('#endDate').attr('disabled','disabled');
		
		// 添加社保公积金方案情况 - 需要显示提交按钮
		if( getSubAction() == 'createObject' ){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
		}

		// 加保提交、退保提交和已退保状态不能编辑||存在工作流不能编辑
		if( '<bean:write name="employeeContractSBForm" property="workflowId" />' != '' || status == '1' ||status == '4'){
			$('#btnEdit').hide();
		}
		
		// JS of the List
		<%
			// 初始化
			final String initCallBack = "$('#sbSolutionId').change();init();";
			// 点击编辑验证
			final String editCallBack = "editCallBackInit();";
			// 表单提交的额外验证
			final String submitAdditionalCallBack = "flag = flag + validateBase(); flag = flag + validateVendorService();if($('.error')[0]){$('.error')[0].focus();}";
		%>
		
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB", initCallBack, editCallBack, null, submitAdditionalCallBack ) %>
		 //雇员登陆限制隐藏按钮
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SB")%>
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		
		// 初始化工作流查看按钮
		loadWorkflowSeach('status','<bean:write name="employeeContractSBForm" property="workflowId"/>');
		
		<%
			if(request.getParameter("comefrom") != null && !request.getParameter("comefrom").trim().isEmpty() && request.getParameter("comefrom").trim().equals("setting")){
	  	%>
			   $('#btnList').click(function() {
					link('employeeContractSBAction.do?proc=list_object');
				});
		<%
			}else{
	  	%>
		$('#btnList').click(function() {
			link('employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>');
		});
		<%
			}
	  	%>
	  	
		// 提交按钮事件
		$('#btnSubmit').click( function () { 
			$("#startDate").removeClass('error');
			$("#endDate").removeClass('error');
			$(".startDate_error").remove();
			$(".endDate_error").remove();
			
			// 如果是无社保，则加保日期不能为空
			if(status == '0' && $('#startDate').val() == ''){
				$("#startDate").addClass('error');
				$(".startDate_error").remove();
				$("#startDate").after('<label class="error startDate_error">&#8226; 不能为空；</label>');
				return;
			}
			
			// 待申报加保、正常缴纳退保日期不能为空
			if( status == '2' || status == '3'){
				var errorCount = 0;
				
				if( $('#startDate').val() == '' ){
					$("#startDate").addClass('error');
					$(".startDate_error").remove();
					$("#startDate").after('<label class="error startDate_error">&#8226; 不能为空；</label>');
					errorCount = errorCount + 1;
				}
				
				if( $('#endDate').val() == '' ){
					$("#endDate").addClass('error');
					$(".endDate_error").remove();
					$("#endDate").after('<label class="error endDate_error">&#8226; 不能为空；</label>');
					errorCount = errorCount + 1;
				}
				
				if( errorCount != 0 ){
					return;
				}
			}
			
			// 已退保情况  Added by siuvan at 2014-08-06
			if( status == '6'){
				
			}
			
			if(validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if(getSubAction() != 'createObject'){
					$('.manage_primary_form').attr('action', 'employeeContractSBAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// 加载供应商下拉框
		loadHtml('.vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// 加载供应商服务下拉框
		loadHtml('.vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// 加载社保公积金明细Tab页
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		
		// 社保公积金方案Change事件
		$("#sbSolutionId").change(function(){
			if(getSubAction() != 'viewObject') {
				$.ajax({
					url:"employeeContractSBAction.do?proc=sbSolution_change_ajax&orderHeaderId=<bean:write name="employeeContractVO" property="orderId"/>&contractId=<bean:write name="employeeContractVO" property="encodedId"/>&sbSolutionId=" + $(this).val(),
					success:function(html){
						$('#personalSBBurden').val(html.trim());
					}
				});

				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=<bean:write name="employeeContractVO" property="encodedId"/>&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $("#sbSolutionId").val(), false, null);
			}
			
			// 加载供应商下拉框
			loadHtml('.vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $("#sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, '$("#vendorId").change();');
		});
		
		// 如果是ViewObject,加载社保公积金下拉框（加载完整下拉框）
		if(getSubAction() == 'viewObject') {
			var callback = "$(\"#sbSolutionId\").trigger('change');";
			loadHtml('.sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=<bean:write name="employeeContractSBForm" property="encodedContractId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', null, callback);
		}
		// 加载社保公积金下拉框（只加载未添加的）
		else{
			var callback = "$(\"#sbSolutionId\").trigger('change');";
			loadHtml('.sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=<bean:write name="employeeContractVO" property="encodedId"/>', null, callback);
		}
		
		$("#vendorId").change(function(){
			// 加载供应商服务下拉框
			loadHtml('.vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('#sbSolutionId').val() + '&vendorId=' + $('#vendorId').val() + '&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>',false,function(){
				if($('.vendorServiceId option').size()==2){
					$('.vendorServiceId option').eq(1).attr('selected','seleccted');
				}
			});
		});
	})(jQuery);
	
	// 当前是否需要Disable
	function getDisable(){
		if(getSubAction() == 'viewObject'){
			return true;
		}else{
			return false;
		}
	};
	
	// 验证基数
	function validateBase(){
		var error = 0;
		$(':input[maxvalue][minvalue]').each(function(){
			if( !(/^[0-9]+(.[0-9]+)?$/.test($.trim($(this).val()))) ){
				//格式验证错误
				$(this).addClass("error");
				error++;
			}else {
				if( parseFloat($(this).attr('minvalue')) <= parseFloat($(this).val()) && parseFloat($(this).val()) <= parseFloat($(this).attr('maxvalue'))){
					//验证通过
					$(this).removeClass("error");
				}else{
					//验证失败
					$(this).addClass("error");
					error++;
				}
			}
		});
		
		return error;
	};

	// 验证供应商服务下拉框
	function validateVendorService(){
		$('.vendorServiceId').removeClass("error");
		$('.vendorServiceId_error').remove();
		if($('.vendorId').val()!=0 && $('.vendorServiceId').val()==0 ){
			$('.vendorServiceId').addClass("error");
			$('.vendorServiceId').after('<label class="error vendorServiceId_error">&#8226;请选择</label>');
			return 1;
		}
		return 0;
	};	
	
	// 点击编辑验证
	function editCallBackInit(){
		// 状态不可编辑
		$('#status').attr('disabled', 'disabled');	
		enableLinkById('#convenientSettingA');
		
		// 如果是无社保公积金，退保日期不能填写
		if(status == '0'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
			$('#endDate').attr('disabled', 'disabled');	
		}
		
		// 待申报加保 - 可以申请退保（当月买、当月退的情况）
		if(status == '2'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
			$('#sbSolutionId').attr('disabled', 'disabled');
		}
		
		// 正常缴纳
		if(status == '3'){
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
			$('#startDate,#sbSolutionId').attr('disabled', 'disabled');
		}
		
		// 待申报退保
		if(status == '5'){
			$('#startDate,#sbSolutionId').attr('disabled', 'disabled');
		}
	
		// Add by siuvan @2014-08-06
		// 已退保情况编辑按钮点击事件时：1、隐藏保存按钮；2、显示加保提交按钮；3、清空日期（退保日期disabled）；4、重新绑定日期控件（加保日期不得小于之前的退保日期）
		<logic:notEmpty name="employeeContractSBForm" property="encodedId">
		if(status == '6'){
			$('#btnEdit').hide();
			$('#btnSubmit').show();
			$('#btnSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
			$('#startDate').val("");
			$('#endDate').val("");
			$('#endDate,#sbSolutionId').attr('disabled', 'disabled');
			
			var endDate = getAppointDate('<bean:write name="employeeContractSBForm" property="endDate" />', 1);
			// 取消加保日期onfocus事件
			$('#startDate').attr('onfocus',"WdatePicker({minDate:'"+ endDate +"'})");
		}
		</logic:notEmpty>
	};
	
	function init(){
		// 如果是Inhouse
		if('<bean:write name="role"/>' == 2){
			var html = '<ol class="auto"><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html + '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html + '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html + '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after( html );
			//$('#pageTitle').html('劳动合同 - 社保公积金方案新增 ');
		}
		else{
			$('.required').parent().after(
					'<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />名称</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>'
			);
			//$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - 社保公积金方案');
		}
		
		$("#menu_employee_ServiceAgreement").addClass('selected');
	};

	function getAppointDate( date, appoint ){
		var newDate = new Date(date);
		var hs = newDate.getTime() + parseInt(appoint) * 1000*60*60*24;
		var returnDate = new Date();
		returnDate.setTime(hs);
		
		var year = returnDate.getFullYear();
		var month = returnDate.getMonth() + 1;
		var day = returnDate.getDate();
		
		if( month < 10){
			month = "0" + month;
		}
		
		if( day < 10){
			day = "0" + day;
		}
		
		return year + "-" + month + "-" + day;
	};
</script>

