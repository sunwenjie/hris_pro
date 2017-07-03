<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeSalaryAdjustmentAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemLocation" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="business" key="employee.salary.adjustment" /> <bean:message bundle="public" key="oper.view" />
			</label>
		</div>
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
			    		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			
			<div class="top">
				<kan:auth right="new" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="editbutton" name="btnSave" id="btnSave" style="display: none;" value="<bean:message bundle="public" key="button.save" />" onclick="saveSalaryAdjustmentForm()"/> 
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" style="display: none;" value="<bean:message bundle="public" key="button.submit" />" onclick="submitSalaryAdjustmentForm()"/> 
				</kan:auth>
				<kan:auth right="modify" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<logic:notEqual name="employeeSalaryAdjustmentForm" property="status" value="3">
						<logic:notEqual name="employeeSalaryAdjustmentForm" property="status" value="5">
							<logic:empty name="employeeSalaryAdjustmentForm" property="workflowId">
								<input type="button" class="save" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.edit" />"/>
							</logic:empty>
						</logic:notEqual>
					</logic:notEqual>
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeeSalaryAdjustmentAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			
			<html:form action="employeeSalaryAdjustmentAction.do?proc=add_object" styleClass="salaryAdjustment_form">
				<input type="hidden" id="salaryAdjustmentId" name="id" value="<bean:write name='employeeSalaryAdjustmentForm' property='encodedId' />">
				<input type="hidden" id="subAction" name="subAction" class="subAction" value="<bean:write name="employeeSalaryAdjustmentForm" property="subAction" />" /> 
				<input type="hidden" id="submitFlag" name="submitFlag">
				<html:hidden property="status"  />
				<html:hidden property="employeeSalaryId" styleClass="employeeSalaryId" styleId="employeeSalaryId"  />
				<html:hidden property="itemNameZH" styleClass="itemNameZH" styleId="itemNameZH"  />
				<html:hidden property="itemNameEN" styleClass="itemNameEN" styleId="itemNameEN"  />
				<%= BaseAction.addToken( request ) %>
				<fieldset>
					<ol class="auto">
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.contract2.id" /><em> *</em></label>
							<html:text property="contractId" maxlength="100" styleClass="contractId" styleId="contractId" readonly="readonly"/>
							<a id="employeeContractSearch" onclick="popupContractSearch();" class="kanhandle" >
								<img src="images/search.png" title="<bean:message bundle="public" key="button.search" />">
							</a>
						</li>
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.contract2.name" /></label>
							<html:text property="employeeContractName" maxlength="100" styleClass="employeeContractName" styleId="employeeContractName"/>
						</li>
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.contract2.start.date" /></label>
							<html:text property="employeeContractStartDate" maxlength="100" styleClass="employeeContractStartDate" styleId="employeeContractStartDate"/>
						</li>
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.contract2.end.date" /></label>
							<html:text property="employeeContractEndDate" maxlength="100" styleClass="employeeContractEndDate" styleId="employeeContractEndDate"/>
						</li>
					</ol>	
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.employee2.id" /></label>
							<html:text property="employeeId" maxlength="100" styleClass="employeeId" styleId="employeeId"/>
						</li>
					</ol>	
					<ol class="auto">	
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.employee2.name" /></label>
							<html:text property="employeeName" maxlength="100" styleClass="employeeName" styleId="employeeName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="employeeCertificateNumber" maxlength="100" styleClass="employeeCertificateNumber" styleId="employeeCertificateNumber"/>
						</li>
					</ol>	
					<ol class="auto">		
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.item" /><em> *</em></label>
							<html:select property="itemId" styleClass="itemId" styleId="itemId">
								<html:optionsCollection property="salaryItems" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
					<ol class="auto">		
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.base" /></label>
							<html:text property="oldBase" maxlength="100" styleClass="oldBase" styleId="oldBase"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.base" /><em> *</em></label>
							<html:text property="newBase" maxlength="100" styleClass="newBase" styleId="newBase"/>
						</li>
					</ol>
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.start.date" /></label>
							<html:text property="oldStartDate" maxlength="100" styleClass="oldStartDate" styleId="oldStartDate"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.start.date" /><em> *</em></label>
							<html:text property="newStartDate" maxlength="100" styleClass="newStartDate" styleId="newStartDate"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.before.end.date" /></label>
							<html:text property="oldEndDate" maxlength="100" styleClass="oldEndDate" styleId="oldEndDate"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.salary.adjustment.adjustment.after.end.date" /></label>
							<html:text property="newEndDate" maxlength="100" styleClass="newEndDate" styleId="newEndDate"/>
						</li>
					</ol>
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.description" /><em> *</em></label>
							<html:select property="remark3" styleClass="remark3" styleId="remark3">
								<html:optionsCollection property="changeReasons" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.effectiveDate" /><em> *</em></label>
							<html:text property="effectiveDate" onfocus="WdatePicker();" maxlength="100" styleClass="effectiveDate" styleId="effectiveDate"/>
						</li>
					</ol>
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.note" /></label>
							<html:textarea property="description" styleClass="description" styleId="description"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.status" /></label>
							<html:text property="decodeStatus" maxlength="100" styleClass="status" styleId="status"/>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.modify.by" /></label> 
							<html:text property="decodeModifyBy" maxlength="100" disabled="disabled" styleClass="decodeModifyBy" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.modify.date" /></label> 
							<html:text property="decodeModifyDate" maxlength="100" disabled="disabled" styleClass="decodeModifyDate" /> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_salary_adjustment').addClass('selected');

		// 列表按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('employeeSalaryAdjustmentAction.do?proc=list_object');
		});
		
		// 雇员ID输入事件
		$("#contractId").bind('keyup',function(){
			cleanSearchForm();
			//加载员工合同信息
			getEmployeeAndContractInfo();
		});
		
		// 薪酬科目改变时间
		$("#itemId").change( function(){
			loadEmployeeSalaryInfo_ajax();
		});
		
		if($('.salaryAdjustment_form input.subAction').val() == 'createObject'){
			$('#btnEdit').hide();
			$("#btnSave").show();
			$("#btnSubmit").show();
			$('.decodeModifyBy').attr('disabled', 'disabled');
			$('.decodeModifyDate').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
			handleForm();
		};
		
		if($('.salaryAdjustment_form input.subAction').val() == 'viewObject'){
			// 将Form设为Disable
			disableForm('salaryAdjustment_form');
			$('#btnEdit').show();
			$("#btnSave").hide();
			$("#btnSubmit").hide();
		};
		
		if($('.salaryAdjustment_form input.subAction').val() == 'createObject'){
    		// 更换Page Title
			$('#pageTitle').html('<bean:message bundle="business" key="employee.salary.adjustment" />' + ' ' + '<bean:message bundle="public" key="oper.new" />');
    	}else{
    		$('#pageTitle').html('<bean:message bundle="business" key="employee.salary.adjustment" />' + ' ' + '<bean:message bundle="public" key="oper.view" />');
    	}
		
		$('#newStartDate').focus(
				function() {
					WdatePicker({
						maxDate : $("#newEndDate").val(),
						oncleared : function() {
							$('#newStartDate').blur();
						}
				});
		});

		$('#newEndDate').focus(
			function() {
				WdatePicker({
					minDate : $('#newStartDate').val() ,
					oncleared : function() {
						$('#newEndDate').blur();
					}
			});
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function () {
			enableForm('salaryAdjustment_form');
			$('#btnEdit').hide();
    		$("#btnSave").show();
    		$("#btnSubmit").show();
    		handleForm();
    		// 修改人、修改时间不可编辑
			$('.decodeModifyBy').attr('disabled', 'disabled');
			$('.decodeModifyDate').attr('disabled', 'disabled');
			$('#status').attr('disabled', 'disabled');
			if($('.salaryAdjustment_form input.subAction').val() == 'viewObject'){
				// 更改Form Action
        		$('.salaryAdjustment_form').attr('action', 'employeeSalaryAdjustmentAction.do?proc=modify_object');
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="business" key="employee.salary.adjustment" />' + ' ' + '<bean:message bundle="public" key="oper.edit" />');
        	}
			if($('.salaryAdjustment_form input.subAction').val() == 'createObject'){
        		// 更换Page Title
    			$('#pageTitle').html('<bean:message bundle="business" key="employee.salary.adjustment" />' + ' ' + '<bean:message bundle="public" key="oper.new" />');
        	}
		});
		
		// 异动原因选项提示
		changeReason_option_tips();
	})(jQuery);
	
	// 加载员工基本信息
	function getEmployeeAndContractInfo(){
		$.ajax({
			url: 'employeeSalaryAdjustmentAction.do?proc=getEmployeeAndContractInfo', 
			type: 'POST', 
			data : {contractId:$("#contractId").val()},
			dataType : 'json',
			async:true,
			success: function(result){
				$("#employeeContractStartDate").val(result.contractStartDate);
				$("#employeeContractEndDate").val(result.contractEndDate);
				$("#employeeContractName").val(result.contractName);
				$("#employeeId").val(result.employeeId);
				$("#employeeName").val(result.employeeName);
				$("#employeeCertificateNumber").val(result.employeeCertificateNumber);
			}
		});
	};
	
	// 加载员工薪酬信息
	function loadEmployeeSalaryInfo_ajax(){
		$.ajax({
			url: 'employeeSalaryAdjustmentAction.do?proc=loadEmployeeSalaryInfo_ajax',
			type: 'POST',
			data : {contractId:$("#contractId").val(),itemId:$('#itemId').val()},
			dataType: 'json',
			async: true,
			success: function(data){
				$('#employeeSalaryId').val(data.employeeSalaryId);
				$('#oldBase').val(data.oldBase);
				$('#oldStartDate').val(data.oldStartDate);
				$('#oldEndDate').val(data.oldEndDate);
				$('#itemNameZH').val(data.itemNameZH);
				$('#itemNameEN').val(data.itemNameEN);
			}
		})
	};
	
	function saveSalaryAdjustmentForm(){
		var flag = 0;
		flag = flag + validate('itemId', true, 'select', 0, 0, 0, 0);
		flag = flag + validate('newBase', true, 'currency', 0, 0, 0, 0);
		flag = flag + validate('newStartDate', true, 'common', 10, 0, 0, 0);
		flag = flag + validate('remark3', true, 'select', 0, 0, 0, 0);
		if(flag == 0){
			enableForm('salaryAdjustment_form');
			submit('salaryAdjustment_form');
		}
	};
	
	function submitSalaryAdjustmentForm(){
		var flag = 0;
		flag = flag + validate('itemId', true, 'select', 0, 0, 0, 0);
		flag = flag + validate('newBase', true, 'currency', 0, 0, 0, 0);
		flag = flag + validate('newStartDate', true, 'common', 10, 0, 0, 0);
		flag = flag + validate('remark3', true, 'select', 0, 0, 0, 0);
		if(flag == 0 ){
			enableForm('salaryAdjustment_form');
			$("#submitFlag").val("1");
			submit('salaryAdjustment_form');
		}
	};
	
	function checkHasContainsSalaryAdjustmentData(){
		var overlap = 1;
		var salaryAdjustmentId = $('#salaryAdjustmentId').val();
		var employeeSalaryId = $('#employeeSalaryId').val();
		var contractId = $('#contractId').val();
		var employeeName = $('#employeeName').val();
		var employeeContractName = $('#employeeContractName').val();
		$.ajax({
			url: 'employeeSalaryAdjustmentAction.do?proc=checkHasContainsSalaryAdjustmentData', 
			type: 'POST', 
			data : {contractId:contractId,employeeSalaryId:employeeSalaryId,salaryAdjustmentId:salaryAdjustmentId},
			dataType : 'text',
			async:false,
			success: function(data){
				if (data == '1') {
					alert('<bean:message bundle="public" key="popup.exist.adjustment.salary.data" />');
				} else {
					overlap = 0;
				}
			}
		});
		return overlap;
	};
	
	function handleForm(){
		$("#employeeContractName").attr("disabled","disabled");
		$("#employeeContractStartDate").attr("disabled","disabled");
		$("#employeeContractEndDate").attr("disabled","disabled");
		$("#employeeId").attr("disabled","disabled");
		$("#employeeName").attr("disabled","disabled");
		$("#employeeCertificateNumber").attr("disabled","disabled");
		$("#oldBase").attr("disabled","disabled");
		$("#oldStartDate").attr("disabled","disabled");
		$("#oldEndDate").attr("disabled","disabled");
	};
	
	//清除页面信息
	function cleanSearchForm(){
		
		cleanError('contractId');
		$("#contractName").val("");
		
		$("#employeeId").val("");
		$("#employeeName").val("");
		$("#employeeCertificateNumber").val("");
		$("#employeeSalaryId").val("0");
		$("#oldBase").val("");
		
		$("#oldStartDate").val("");
		$("#oldEndDate").val("");
		
		$("#newBase").val("");
		$("#newStartDate").val("");
		$("#newEndDate").val("");
		$("#effectiveDate").val("");
	};
	
	// 异动原因选项提示
	function changeReason_option_tips(){
		$('#remark3 option').each( function(){
			if( $(this).val() == 1){
				$(this).attr('title','New to iClick; or Company Code Change');
			}else if( $(this).val() == 2){
				$(this).attr('title','Employment Category Change');
			}else if( $(this).val() == 3){
				$(this).attr('title','Org. Structure Change');
			}else if( $(this).val() == 4){
				$(this).attr('title','BU/Function; Department; Job Role');
			}else if( $(this).val() == 5){
				$(this).attr('title','New Manager on board; People Manager/Team Lead Change');
			}else if( $(this).val() == 6){
				$(this).attr('title','Job Grade Change');
			}else if( $(this).val() == 7){
				$(this).attr('title','IC/M Shift; or Working Title Change');
			}else if( $(this).val() == 8){
				$(this).attr('title','Location Change');
			}else if( $(this).val() == 9){
				$(this).attr('title','Pay Structure Change; Pay Change');
			}else if( $(this).val() == 10){
				$(this).attr('title','Leave iClick; or Company Code Change');
			}else if( $(this).val() == 11){
				$(this).attr('title','Data Correction only');
			}
		})
	};
</script>