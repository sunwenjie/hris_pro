<%@ page pageEncoding="GBK" %>
<%@ page import="com.kan.base.util.KANUtil" %>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.base.web.renders.util.ManageRender"%>
<%@ page import="com.kan.hro.domain.biz.client.ClientOrderHeaderVO"%>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO" %>
<%@ page import="com.kan.base.web.renders.util.EmployeeUserManageRender"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">
	<!-- Information Manage Form -->
	<%= ManageRender.generateManage( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY", "employeeContractSalaryForm", true ) %>
</div>
						
<script type="text/javascript">
	var status = null;
	
	(function($) {	
		// 初始化状态
		status = $('#status').val();
		<% 
			final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO )request.getAttribute( "employeeContractSalaryForm" );
		%>
		// JS of the List
		<%= ManageRender.generateManageJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY", "init();",  "editCallBack();", null, "submitAdditionalCallBack();" ) %>
		// 雇员登陆限制隐藏按钮
		<%= EmployeeUserManageRender.generateDisplayBtnJS( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY")%>
		
		<%
			if(request.getParameter("comefrom") != null && !request.getParameter("comefrom").trim().isEmpty() && request.getParameter("comefrom").trim().equals("setting")){
	  	%>
			   $('#btnList').click(function() {
					link('employeeContractSalaryAction.do?proc=list_object');
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
	  	
		// 提交按钮点击事件
		$('#btnSubmit').click( function () { 
			if( validate_manage_primary_form() == 0){
				// 更改当前Form的SubAction
				if( $('.manage_primary_form input#subAction').val() != 'createObject'){
					$('.manage_primary_form').attr('action', 'employeeContractSalaryAction.do?proc=modify_object');
				}
				$('.manage_primary_form input#subAction').val('submitObject');
				
	    		enableForm('manage_primary_form');
	    		submitForm('manage_primary_form');
			}
		});
		
		// 更多信息点击事件
		$('#HRO_BIZ_EMPLOYEE_CONTRACT_SALARY_CG13_Link').click(function(){
			if ('<bean:write name="ItemType"/>' == "13") {
				$('#isDeductLI').show();
				$('#divideTypeLI').hide();
			}else{
				$('#isDeductLI').hide();
			}
		});
		
		if ('<bean:write name="ItemType"/>' == "13") {
			$('#isDeductLI').show();
			$('#divideTypeLI').hide();
			$('#excludeDivideItemIdsLI').show();
		}else{
			$('#isDeductLI').hide();
		}

		// “科目”Change事件，影响假期例外是否显示
		$('#itemId').change(function() {
			$.ajax({url : 'itemAction.do?proc=get_object_json&itemId='+ $(this).val()+ '&date='+ new Date(),
					dataType : 'json',
					success : function(data) {
						if (data.itemType == '1') {
							$('#excludeDivideItemIdsLI').hide();
						} else {
							$('#excludeDivideItemIdsLI').show();
						}
						if (data.itemType == '13') {
							$('#isDeductLI').show();
							$('#divideTypeLI').hide();
							//非全勤计算规则选择为“不折算”
							$('#divideType').val("1");
						} else {
							$('#isDeductLI').hide();
						}
					}
			});
			if ($('#itemId').find("option:selected").text() == '第十三薪') {
				$('.formular').val('基本工资/365*((年度工作末天-年度工作首天+1)-(全年全勤小时数-全年工作小时数)/8)');
			} else {
				$('.formular').val('');
			}
		});

		// “金额来源”添加Change事件
		$('#baseFrom').change(function() {
			if ($(this).val() != 0) {
				$('#percentageLI').show();
				$('#fixLI').show();
			} else {
				$('#percentageLI').hide();
				$('#fixLI').hide();
			}
		});

		$('#baseFrom').change();

		// “计薪方式”添加Change事件
		$('#salaryType').change(function() {
			if ($(this).val() == 1) {
				if ('<bean:write name="ItemType"/>' != "13") {
					$("#divideTypeLI").show();
				}
				$("#excludeDivideItemIdsLI").show();
			} else {
				$("#divideTypeLI").hide();
				$("#excludeDivideItemIdsLI").hide();
			}
		});

		// “支付频率”为一次性时，生效日期和结束日期必填
		$('#cycle').change(function() {
			if ($(this).val() == '13') {
				$('li#startDateLI,#endDateLI').find('label').append(
						'<em class="em_temp"> *</em>');
			} else {
				$('.em_temp').remove();
			}
		});

		// “计算规则”添加Change事件
		$('#divideType').change(function() {
			if (($(this).val() == 2 || $(this).val() == 3|| $(this).val() == 5||$(this).val() == 4)
					&& $('#salaryType').val() == 1) {
				$.ajax({
					url : 'itemAction.do?proc=get_object_json&itemId='
							+ $('#itemId').val() + '&date='
							+ new Date(),
					dataType : 'json',
					success : function(data) {
						/* if (data.itemType == '1') {
								$('#excludeDivideItemIdsLI').hide();
						} else {
							$('#excludeDivideItemIdsLI').show();
						} */
						$('#excludeDivideItemIdsLI').show();
					}
				});
			} else {
				$("#excludeDivideItemIdsLI").hide();
			}
		});

		if ($('#itemId').val() != '0') {
			$('#salaryType').change();
			$('#divideType').change();
		}

		// Review by siuvan 2014-10-27
		// 页面初始化 设置开始时间 结束时间
		$('#startDate').removeAttr('onfocus');
		$('#endDate').removeAttr('onfocus');
		//$('#startDate').attr('onFocus',"WdatePicker({minDate:'<bean:write name="employeeContractVO" property="startDate"/>',maxDate:'#F{ $dp.$D(\\\'endDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="endDate"/>\\\') }'})" );
		//$('#endDate').attr('onFocus',"WdatePicker({maxDate:'<bean:write name="employeeContractVO" property="endDate"/>',minDate:'#F{ $dp.$D(\\\'startDate\\\') || $dp.$DV(\\\'<bean:write name="employeeContractVO" property="startDate"/>\\\') }' })" );
		$('#startDate').focus(function() {
			WdatePicker({
				minDate : '<bean:write name="employeeContractVO" property="startDate"/>',
				maxDate : $('#endDate').val() == '' ? '<bean:write name="employeeContractVO" property="endDate"/>'
						: $('#endDate').val(),
				oncleared : function() {
					$('#startDate').blur();
				},
				onpicked : function(dp) {
					checkHasConflictContractSalaryInOneItem();
				}
			});
		});

		$('#endDate').focus(function() {
			WdatePicker({
				minDate : $('#startDate').val() == '' ? '<bean:write name="employeeContractVO" property="startDate"/>'
						: $('#startDate').val(),
				maxDate : '<bean:write name="employeeContractVO" property="endDate"/>',
				oncleared : function() {
					$('#endDate').blur();
				},
				onpicked : function(dp) {
					checkHasConflictContractSalaryInOneItem();
				}
			});
		});
	})(jQuery);

	//  薪酬方案开始时间和薪酬方案结束时间，选中时间事件    验证该时间段内是否已存在薪酬科目
	function checkHasConflictContractSalaryInOneItem() {
		var overlap = false;
		cleanError('startDate');
		if ($('#employeeId').val() != '' && $('#clientId').val() != ''
				&& $("#itemId").val() != "0" && $('#startDate').val() != ''
				&& $('#endDate').val() != ''
				&& $('.subAction').val() != 'viewObject') {
			$.ajax({
				url : 'employeeContractSalaryAction.do?proc=checkHasConflictContractSalaryInOneItem&employeeId=<bean:write name="employeeContractVO" property="employeeId"/>&startDate='
						+ $('#startDate').val()
						+ '&itemId='
						+ $('#itemId option:selected').val()
						+ '&contractId='
						+ $('#contractId').val()
						+ '&id='
						+ $('.manage_primary_form #id').val()
						+ '&endDate=' + $('#endDate').val() + '&flag=2',
				type : 'POST',
				async : false,
				success : function(data) {
					if (data == '1') {
						addError('startDate',
								'<bean:message bundle="public" key="error.time.period.exist.salary" />');
						overlap = true;
					} else {
						overlap = false;
					}
				}
			});
		}
		return overlap;
	};

	// 页面加载，初始化JS事件
	function init() {
		// 状态不可编辑
		$('#status').attr('disabled','disabled');
		// 状态为启用||存在工作流不能编辑
		if( '<bean:write name="employeeContractVO" property="status" />' != 1 && ( $('#status').val() == '1' || '<bean:write name="employeeContractSalaryForm" property="workflowId" />' != '')){
			$('#btnEdit').hide();
		}
		// 列表按钮Text置换成返回
		$('#btnList').val('<bean:message bundle="public" key="button.back.fh" />');
		// 初始化工作流查看按钮
		loadWorkflowSeach('status','<bean:write name="employeeContractSalaryForm" property="workflowId"/>');
		
		// 如果是Inhouse
		if ('<bean:write name="role"/>' == 2) {
			var html = '<ol class="auto"><li>';
			html = html
					+ '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal></label>';
			html = html
					+ '<span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span>';
			html = html + '</li><li>';
			html = html
					+ '<label><logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name" /></logic:equal><logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name" /></logic:equal></label>';
			html = html
					+ '<span><bean:write name="employeeContractVO" property="name" /></span></li></ol>';
			$('.required').parent().after(html);
			//$('#pageTitle').html('劳动合同 - 薪酬方案新增 ');
		} else {
			$('.required').parent().after('<ol class="auto"><li><label><bean:write name="employeeContractVO" property="decodeFlag" />ID</label><span><a onclick="link(\'employeeContractAction.do?proc=to_objectModify&id=<bean:write name="employeeContractVO" property="encodedId"/>\');"><bean:write name="employeeContractVO" property="contractId" /></a></span></li><li><label><bean:write name="employeeContractVO" property="decodeFlag" />名称</label><span><bean:write name="employeeContractVO" property="name" /></span></li></ol>');
			$('#pageTitle').html('<bean:write name="employeeContractVO" property="decodeFlag" /> - 薪酬方案新增');
		}

		$("#menu_employee_ServiceAgreement").addClass('selected');

		// 不折算科目定义选项
		<%
			final ClientOrderHeaderVO clientOrderHeaderVO = (ClientOrderHeaderVO)request.getAttribute("clientOrderHeaderVO");
			final String subAction = ((EmployeeContractSalaryVO)request.getAttribute("employeeContractSalaryForm")).getSubAction();
			String excludeDivideItemIds = "";
			if( BaseAction.VIEW_OBJECT.equalsIgnoreCase( subAction ) )
			{
			   // 如果是查看，从salary里面获取
			   excludeDivideItemIds = employeeContractSalaryVO.getExcludeDivideItemIds();
			}else
			{
			   // 如果是新增则从订单/账套里面带过来
			   excludeDivideItemIds = clientOrderHeaderVO.getExcludeDivideItemIds();
			}
		%>
		// 移除请选择
		$("#excludeDivideItemIdsLI").find("span").remove();
		
		$("#excludeDivideItemIdsLI").find("label").after("<div style='width: 215px;'><%=KANUtil.getCheckBoxHTML(employeeContractSalaryVO.getExcludeDivideItems(), "excludeDivideItemIds", excludeDivideItemIds, subAction, "<br/>") %></div>");
	};
	
	// 编辑按钮点击回调函数
	function editCallBack(){
		// 状态不可编辑
		$('#status').attr('disabled','disabled');
		// 状态为停用显示提交按钮
		if($('#status').val() == 2){
			$('#btnSubmit').show();
		}
		// 状态为启用隐藏编辑按钮，如果要修改，需要到【员工 > 调薪】处具体操作
		else if($('#status').val() == 1){
			$('#btnEdit').hide();
		}
	};
	
	// 提交按钮点击验证回调函数
	function submitAdditionalCallBack(){
		// “支付频率”为一次性时，生效日期和结束日期必填
		if($('#cycle').val()=='13'){
			flag = flag + validate('startDate', true, 'common', 10, 0, 0, 0);
			flag = flag + validate('endDate', true, 'common', 10, 0, 0, 0);
		}
		// 验证科目唯一性
		if(checkHasConflictContractSalaryInOneItem() == true){
			flag = flag + 1;
		}
	}; 
</script>

