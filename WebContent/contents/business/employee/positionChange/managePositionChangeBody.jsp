<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeePositionChangeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div id="systemLocation" class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="business" key="employee.position.change" />
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
				<kan:auth right="modify" action="<%=EmployeePositionChangeAction.accessAction%>">
					<logic:notEqual name="employeePositionChangeForm" property="status" value="3">
						<logic:notEqual name="employeePositionChangeForm" property="status" value="5">
							<logic:empty name="employeePositionChangeForm" property="workflowId">
								<!-- <input type="button" class="function" name="btnAdd" id="btnAdd" value="新任" onclick="addPosition()"/>  -->
								<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.fire" />" onclick="deletePosition()"/>
								<input type="button" class="function" name="btnEdit" id="btnEdit" value="<bean:message bundle="public" key="button.change" />" onclick="updatePosition()"/>
								<input type="button" class="editbutton" id="btnEditPage" name="btnEditPage" value="<bean:message bundle="public" key="button.edit" />" onclick="editedPage()" style="display: none;"/>
							</logic:empty>
						</logic:notEqual>
					</logic:notEqual>
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			
			<html:form action="employeePositionChangeAction.do?proc=add_object" styleClass="positionChange_form">
				<fieldset>
					<ol class="auto">
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.employee2.id" /></label>
							<html:text property="employeeId" styleClass="employeeId" styleId="employeeId"/>
							<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle">
								<img src="images/search.png" title="<bean:message bundle="public" key="button.search" />">
							</a>
						</li>
						<li style="width: 50%;" id="positionPageDiv">
							<label><bean:message bundle="security" key="security.position" /></label>
							<select id="positionSelect" class="positionSelect" onchange="getBranchInfoByPositionId()">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
							</select>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.employee2.no" /></label>
							<html:text property="employeeNo" styleClass="employeeNo" styleId="employeeNo"/>
						</li>
						<li id="branchPageDiv" style="width: 50%;">
							<label><bean:message bundle="security" key="security.branch" /></label>
							<html:text property="oldBranchName" styleClass="branchName" styleId="branchName"/>
							<html:hidden property="oldBranchId" styleClass="branchId" styleId="branchId"/>
							<html:hidden property="oldStaffPositionRelationId" styleClass="staffPositionRelationId" styleId="staffPositionRelationId"/>
						</li>
						<li id="employeeIdLI" style="width: 50%;">
							<label><bean:message bundle="public" key="public.employee2.name" /></label>
							<html:text property="employeeName" styleClass="employeeName" styleId="employeeName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="employeeCertificateNumber" styleClass="employeeCertificateNumber" styleId="employeeCertificateNumber"/>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<div id="positionChangeDiv" class="box" style="display: none;">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="business" key="employee.position.change.detail" />
			</label>
		</div>
		<div class="inner">
			<div class="top">
				<kan:auth right="new" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="editbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.save" />" onclick="saveForm()"/> 
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" onclick="submitPositionChangeForm()"/> 
				</kan:auth>
			</div>
			<html:form action="employeePositionChangeAction.do?proc=add_object" styleClass="positionChangeSubmit_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="positionChangeId" name="id" value="<bean:write name='employeePositionChangeForm' property='encodedId' />">
				<html:hidden property="subAction" styleClass="subAction" styleId="subAction"/>
				<html:hidden property="employeeId" styleClass="oldEmployeeId" styleId="oldEmployeeId"/>
				<html:hidden property="submitFlag" styleClass="submitFlag" styleId="submitFlag"/>
				<html:hidden property="oldBranchId" styleClass="oldBranchId" styleId="oldBranchId"/>
				<html:hidden property="oldPositionId" styleClass="oldPositionId" styleId="oldPositionId"/>
				<html:hidden property="oldStaffPositionRelationId" styleClass="oldStaffPositionRelationId" styleId="oldStaffPositionRelationId"/>
				
				<fieldset>
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldParentBranchName" /></label>
							<html:text property="oldParentBranchName" styleClass="oldParentBranchName" styleId="oldParentBranchName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldParentPositionName" /></label>
							<html:text property="oldParentPositionName" styleClass="oldParentPositionName" styleId="oldParentPositionName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldPositionGradeName" /></label>
							<html:text property="oldPositionGradeName" styleClass="oldPositionGradeName" styleId="oldPositionGradeName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldParentPositionOwnersName" /></label>
							<html:text property="oldParentPositionOwnersName" styleClass="oldParentPositionOwnersName" styleId="oldParentPositionOwnersName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldBranchName" /></label>
							<html:text property="oldBranchName" styleClass="oldBranchName" styleId="oldBranchName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.oldPositionName" /></label>
							<html:text property="oldPositionName" styleClass="oldPositionName" styleId="oldPositionName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newParentBranchName" /></label>
							<html:text property="newParentBranchName" styleClass="newParentBranchName" styleId="newParentBranchName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newParentPositionName" /></label>
							<html:text property="newParentPositionName" styleClass="newParentPositionName" styleId="newParentPositionName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newPositionGradeName" /></label>
							<html:text property="newPositionGradeName" styleClass="newPositionGradeName" styleId="newPositionGradeName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newParentPositionOwnersName" /></label>
							<html:text property="newParentPositionOwnersName" styleClass="newParentPositionOwnersName" styleId="newParentPositionOwnersName"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newBranchId" /></label>
							<select id="newBranchId" name="newBranchId" onchange="getPositionIdByBranchId()">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
							</select>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.newPositionId" /></label>
							<select id="newPositionId" name="newPositionId" class="newPositionId" onchange="getBranchInfoByPositionId('new')">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
							</select>
						</li>
						<li style="width: 50%; display: none;" >
							<label><bean:message bundle="business" key="employee.position.change.positionStatus" /></label>
							<html:select property="positionStatus" styleClass="searchPositionChange_positionStatus">
								<html:optionsCollection property="positionStatues" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li style="width: 50%;display: none;">
							<label><bean:message bundle="business" key="employee.position.change.isImmediatelyEffective" /></label>
							<input type="checkbox" id="isImmediatelyEffective" name="isImmediatelyEffective" checked="checked" value="0">
						</li>
					</ol>	
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.isChildChange" /></label>
							<html:select property="isChildChange" styleClass="isChildChange">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li style="width: 50%;">
							<label>New Direct Report Manager (Biz Leader) </label>
							<html:text property="remark2" styleClass="remark2" styleId="remark2"/>
						</li>
					</ol>
					<ol class="auto">
						<li style="width: 50%;">
							<label>Job Role</label>
							<html:select property="remark1" styleClass="remark1">
								<html:optionsCollection property="jobRoles" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
					</ol>	
					<ol class="auto">
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.effectiveDate" /><em> *</em></label>
							<html:text property="effectiveDate" styleClass="effectiveDate" styleId="effectiveDate"/>
						</li>
						<li style="width: 50%;">
							<label><bean:message bundle="business" key="employee.position.change.description" /><em> *</em></label>
							<html:select property="remark3" styleClass="remark3" styleId="remark3">
								<html:optionsCollection property="changeReasons" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">	
						<li style="width: 50%;">
							<label><bean:message bundle="public" key="public.note" /></label>
							<html:textarea property="description" styleClass="description" styleId="description"/>
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
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/calendar.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_Position_Changes').addClass('selected');

		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('employeePositionChangeAction.do?proc=list_object');
		});
		
		// 雇员ID输入事件
		$("#employeeId").bind('keyup',function(){
			//根据员工ID获取职位下拉框
			getPositionsByEmployeeId();
			//获取员工信息
			getEmployeeInfoByEmployeeId();
		});
		
		if($('.positionChangeSubmit_form input.subAction').val() == 'createObject'){
			
			$('#btnAdd').show();
			$("#btnEdit").show();
			$("#btnDelete").show();
			$('.employeeNo').attr('disabled', 'disabled');
			$('.branchName').attr('disabled', 'disabled');
			$('.employeeName').attr('disabled', 'disabled');
			$('.employeeCertificateNumber').attr('disabled', 'disabled');
			$("#positionChangeDiv").hide();
			$('.decodeModifyBy').attr('disabled', 'disabled');
			$('.decodeModifyDate').attr('disabled', 'disabled');
			$("#positionPageDiv").show();
			$("#branchPageDiv").show();
			
			//加载部门下拉框（明细部分）
			getBranchForPage();
		}
		
		if($('.positionChangeSubmit_form input.subAction').val() == 'viewObject'){
			
			$('#btnAdd').hide();
			$("#btnEdit").hide();
			$("#btnDelete").hide();
			$('#btnSave').hide();
			$('#btnSubmit').hide();
			$('#btnEditPage').show();
			$("#positionChangeDiv").show();
			//根据员工ID获取职位下拉框
			getPositionsByEmployeeId();
			//获取员工信息
			getEmployeeInfoByEmployeeId();
			//加载部门下拉框（明细部分）
			getBranchForPage();
			//加载职位下拉框（明细部分）
			getPositionIdByBranchId();
			disableForm('positionChange_form');
			disableForm('positionChangeSubmit_form');
			
			$("#positionPageDiv").hide();
			$("#branchPageDiv").hide();
		}
		
		$('#effectiveDate').focus(function() {WdatePicker();});

		// 异动原因选项提示
		changeReason_option_tips();
	})(jQuery);
	
	function getPositionsByEmployeeId(){
		
		cleanSearchForm();
		
		var employeeId = $("#employeeId").val();
		$.ajax({
			url: 'employeePositionChangeAction.do?proc=getPositionsByEmployeeId', 
			type: 'POST', 
			data : {employeeId:employeeId},
			dataType : 'json',
			async:true,
			success: function(result){
				
				if (result.length == 1) {
					
					$("#positionSelect").empty();
					for(var i = 0 ; i < result.length ; i ++){
						$("#positionSelect")[0].options.add(new Option(result[i].name,result[i].id));
					}
					$("#positionSelect").val(result[0].id);
					$("#positionSelect").attr("disabled",true);
					
					//根据position获取部门信息
					getBranchInfoByPositionId();
				}else if (result.length > 1) {
					
					$("#positionSelect").empty();
					for(var i = 0 ; i < result.length ; i ++){
						$("#positionSelect")[0].options.add(new Option(result[i].name,result[i].id));
					}
				}
			}
		});
	}
	
	//根结职位获得页面信息
	function getBranchInfoByPositionId(type){
		
		var positionId = type == 'new' ? $("#newPositionId").val() : $("#positionSelect").val();
		var employeeId = type == 'new' ? "" : $("#employeeId").val();
		if (positionId != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=getBranchInfoByPositionId', 
				type: 'POST', 
				data : {employeeId:employeeId,positionId:positionId},
				dataType : 'json',
				async    : false,
				success: function(result){
					if (type == 'new') {
						
						$("#newParentBranchName").val(result.parentBranchName);
						$("#newParentPositionName").val(result.parentPositionName);
						$("#newPositionGradeName").val(result.positionGradeName);
						$("#newParentPositionOwnersName").val(result.parentPositionOwnersName);
					}else{
						
						//页面赋值
						$("#branchId").val(result.branchId);
						$("#branchName").val(result.branchName);
						$("#oldBranchId").val($("#branchId").val());
						$("#oldBranchName").val($("#branchName").val());
						$("#staffPositionRelationId").val(result.staffPositionRelationId);
						$("#oldStaffPositionRelationId").val(result.staffPositionRelationId);
						$("#oldPositionId").val($("#positionSelect").val());
						$("#oldPositionName").val($("#positionSelect").find("option:selected").text());
						
						$("#oldParentBranchName").val(result.parentBranchName);
						$("#oldParentPositionName").val(result.parentPositionName);
						$("#oldPositionGradeName").val(result.positionGradeName);
						$("#oldParentPositionOwnersName").val(result.parentPositionOwnersName);
					}
				}
			});
		}else{
			if($('.positionChangeSubmit_form input.subAction').val() != 'viewObject')
				cleanSearchForm();
		}
	}
	
	//根结员工ID获得员工信息
	function getEmployeeInfoByEmployeeId(){
		
		var employeeId = $("#employeeId").val();
		if (employeeId != "") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=getEmployeeInfoByEmployeeId', 
				type: 'POST', 
				data : {employeeId:employeeId},
				dataType : 'json',
				async    : false,
				success: function(result){
					//页面赋值
					$("#employeeNo").val(result.employeeNo);
					$("#employeeName").val(result.employeeName);
					$("#employeeCertificateNumber").val(result.employeeCertificateNumber);
				}
			});
		}else{
			cleanSearchForm();
		}
	};
	
	function getBranchForPage(){
		$.ajax({
			url: 'employeePositionChangeAction.do?proc=getBranchForPage', 
			type: 'POST', 
			data : '',
			dataType : 'json',
			async:false,
			success: function(result){
				$("#newBranchId").empty();
				for(var i = 0 ; i < result.length ; i ++){
					$("#newBranchId")[0].options.add(new Option(result[i].name,result[i].id));
				}
				
				if($('.positionChangeSubmit_form input.subAction').val() == 'viewObject'){
					$("#newBranchId").find("option[value='<bean:write name='employeePositionChangeForm' property='newBranchId' />']").attr("selected",true);
				}
			}
		});
	};
	
	function getPositionIdByBranchId(){
		var branchId = $("#newBranchId").val();
		var oldPositionId = $('#oldPositionId').val();
		$.ajax({
			url: 'employeePositionChangeAction.do?proc=getPositionIdByBranchId', 
			type: 'POST', 
			data : {branchId:branchId},
			dataType : 'json',
			async:true,
			success: function(result){
				$("#newPositionId").empty();
				for(var i = 0 ; i < result.length ; i ++){
					if (result[i].full == "0" && result[i].id != oldPositionId) {
						$("#newPositionId")[0].options.add(new Option(result[i].name,result[i].id));
					}else{
						var option = new Option(result[i].name,result[i].id);
						option.style.color= "red";
						option.disabled = "disabled";
						$("#newPositionId")[0].options.add(option);
					}
				}
				if($('.positionChangeSubmit_form input.subAction').val() == 'viewObject'){
					$("#newPositionId").find("option[value='<bean:write name='employeePositionChangeForm' property='newPositionId' />']").attr("selected",true);
				}
			}
		});
	};
	
	function addPosition(){
		
		$('#positionChangeDiv').hide();
		var flag = 0;
		flag = flag + validate('employeeId', true, 'common', 10, 0, 0, 0);
		flag = flag + validateEmployeeId();
		if(flag == 0){
			
			cleanSubmitForm();
			$("#oldBranchId").val("0");
			$("#oldBranchName").val("");
			$("#oldStaffPositionRelationId").val("0");
			$("#oldPositionId").val("0");
			$("#oldPositionName").val("");
			$("#oldEmployeeId").val($("#employeeId").val());
			$("#newBranchId").attr('disabled', false);
			$("#newPositionId").attr('disabled', false);
			$('#positionChangeDiv').show();
			
			$("#oldBranchName").attr('disabled', true);
			$("#oldPositionName").attr('disabled', true);
			
			$("#oldParentBranchName").attr('disabled', true);
			$("#oldParentPositionName").attr('disabled', true);
			$("#oldPositionGradeName").attr('disabled', true);
			$("#oldParentPositionOwnersName").attr('disabled', true);
			
			$("#newParentBranchName").attr('disabled', true);
			$("#newParentPositionName").attr('disabled', true);
			$("#newPositionGradeName").attr('disabled', true);
			$("#newParentPositionOwnersName").attr('disabled', true);
		}
	};
	
	function updatePosition(){
		
		if($('.positionChangeSubmit_form input.subAction').val() == 'createObject'){
			$('#positionChangeDiv').hide();
			var flag = 0;
			flag = flag + validate('employeeId', true, 'common', 10, 0, 0, 0);
			flag = flag + validate('positionSelect', true, 'select', 0, 0, 0, 0);
			flag = flag + validateEmployeeId();
			if(flag == 0){
				
				$('#btnSave').show();
				$('#btnSubmit').show();
				
				$("#oldBranchId").val($("#branchId").val());
				$("#oldBranchName").val($("#branchName").val());
				$("#oldStaffPositionRelationId").val($("#staffPositionRelationId").val());
				$("#oldPositionId").val($("#positionSelect").val());
				$("#oldPositionName").val($("#positionSelect").find("option:selected").text());
				$("#oldEmployeeId").val($("#employeeId").val());
				
				$("#newBranchId").attr('disabled', false);
				$("#newPositionId").attr('disabled', false);
				
				$("#effectiveDate").attr('disabled', false);
				$("#description").attr('disabled', false);
				
				$("#oldBranchName").attr('disabled', true);
				$("#oldPositionName").attr('disabled', true);
				
				$("#oldParentBranchName").attr('disabled', true);
				$("#oldParentPositionName").attr('disabled', true);
				$("#oldPositionGradeName").attr('disabled', true);
				$("#oldParentPositionOwnersName").attr('disabled', true);
				
				$("#newParentBranchName").attr('disabled', true);
				$("#newParentPositionName").attr('disabled', true);
				$("#newPositionGradeName").attr('disabled', true);
				$("#newParentPositionOwnersName").attr('disabled', true);
				
				$('#positionChangeDiv').show();
				
				if($('.positionChangeSubmit_form input.subAction').val() == 'viewObject'){
					$("#newBranchId").find("option[value='<bean:write name='employeePositionChangeForm' property='newBranchId' />']").attr("selected",true);
					$("#newPositionId").find("option[value='<bean:write name='employeePositionChangeForm' property='newPositionId' />']").attr("selected",true);
				}
			}
		}
	};
		
	function deletePosition(){
		
		$('#positionChangeDiv').hide();
		var flag = 0;
		flag = flag + validate('employeeId', true, 'common', 10, 0, 0, 0);
		flag = flag + validate('positionSelect', true, 'select', 0, 0, 0, 0);
		flag = flag + validateEmployeeId();
		if(flag == 0){
			
			$('#btnSave').show();
			$('#btnSubmit').show();
			
			$("#oldBranchId").val($("#branchId").val());
			$("#oldBranchName").val($("#branchName").val());
			$("#oldStaffPositionRelationId").val($("#staffPositionRelationId").val());
			$("#oldPositionId").val($("#positionSelect").val());
			$("#oldPositionName").val($("#positionSelect").find("option:selected").text());
			$("#oldEmployeeId").val($("#employeeId").val());
			
			$("#newBranchId").val("0");
			$("#newPositionId").val("0");
			$("#newBranchId").attr('disabled', true);
			$("#newPositionId").attr('disabled', true);
			$("#oldBranchName").attr('disabled', true);
			$("#oldPositionName").attr('disabled', true);
			
			$("#oldParentBranchName").attr('disabled', true);
			$("#oldParentPositionName").attr('disabled', true);
			$("#oldPositionGradeName").attr('disabled', true);
			$("#oldParentPositionOwnersName").attr('disabled', true);
			
			$("#newParentBranchName").attr('disabled', true);
			$("#newParentPositionName").attr('disabled', true);
			$("#newPositionGradeName").attr('disabled', true);
			$("#newParentPositionOwnersName").attr('disabled', true);
			
			$("#effectiveDate").attr('disabled', false);
			$("#description").attr('disabled', false);
			$('#positionChangeDiv').show();
		}
	};
	
	function saveForm(){
		
		var flag = 0;
		//特殊格式验证
		flag = flag + validatePositionId();
		flag = flag + validate('effectiveDate', true, 'common', 0, 0, 0, 0);
		flag = flag + validate('remark3', true, 'select', 0, 0, 0, 0);
		if(flag == 0){
			
			if ($("#newPositionId").val() == "0") {
				if(confirm('<bean:message bundle="public" key="popup.confirm.fire.position" />')){
					submit('positionChangeSubmit_form');
				}
			}else{
				submit('positionChangeSubmit_form');
			}
		}
	};
	
	function submitPositionChangeForm(){
		
		var flag = 0;
		//特殊格式验证
		flag = flag + validatePositionId();
		flag = flag + validate('effectiveDate', true, 'common', 0, 0, 0, 0);
		flag = flag + validate('remark3', true, 'select', 0, 0, 0, 0);
		if(flag == 0){
			
			if ($("#newPositionId").val() == "0") {
				if(confirm('<bean:message bundle="public" key="popup.confirm.fire.position" />')){
					$("#submitFlag").val("1");
					submit('positionChangeSubmit_form');
				}
			}else{
				$("#submitFlag").val("1");
				submit('positionChangeSubmit_form');
			}
		}
	};
	
	function editedPage(){

		$('.positionChangeSubmit_form').attr('action', 'employeePositionChangeAction.do?proc=modify_object');
		enableForm('positionChangeSubmit_form');
		$("#oldBranchName").attr('disabled', true);
		$("#oldPositionName").attr('disabled', true);
		
		$("#oldParentBranchName").attr('disabled', true);
		$("#oldParentPositionName").attr('disabled', true);
		$("#oldPositionGradeName").attr('disabled', true);
		$("#oldParentPositionOwnersName").attr('disabled', true);
		
		$("#newParentBranchName").attr('disabled', true);
		$("#newParentPositionName").attr('disabled', true);
		$("#newPositionGradeName").attr('disabled', true);
		$("#newParentPositionOwnersName").attr('disabled', true);
		
		$('#btnSave').show();
		$('#btnSubmit').show();
		$('#btnEditPage').hide();
		$('.decodeModifyBy').attr('disabled', 'disabled');
		$('.decodeModifyDate').attr('disabled', 'disabled');
	};
	
	//验证employeeID是否合法
	function validateEmployeeId(){
		
		var returnValue = 1;
		if($("#employeeId").val() != "" && $("#employeeId").val().length >= 9){
			$.ajax({url: 'employeeAction.do?proc=get_object_json&employeeId=' + $("#employeeId").val() + '&date=' + new Date(),
				dataType : 'json',
				async    : false,
				success: function(data){
					cleanError('employeeId');
					if(data.success == 'true'){
						returnValue = 0;
					}else if(data.success == 'false'){
						addError('employeeId', data.errorMsg);
					}
				}
			});
		}
		return returnValue;
	};
	
	function validatePositionId(){
		
		var flag = 0;
		var oldPositionId = $("#oldPositionId").val();
		var newPositionId = $("#newPositionId").val();
		if (oldPositionId == "0" && newPositionId == "0") {
			flag = 1;
			cleanError('newPositionId');
			addError('newPositionId', '<bean:message bundle="public" key="error.choose.position" />');
		}
		return flag;
	};
	
	//清除页面信息
	function cleanSearchForm(){
		cleanError('employeeId');
		cleanError('positionSelect');
		$("#positionSelect").val("0");
		$("#employeeNo").val("");
		$("#branchName").val("");
		$("#employeeName").val("");
		$("#employeeCertificateNumber").val("");
	};
	
	function cleanSubmitForm(){
		
		cleanError('newPositionId');
		$('.positionChangeSubmit_form input,.positionChangeSubmit_form textarea').val('');
		$('.positionChangeSubmit_form select').val('0');
		$('.positionChangeSubmit_form input[type=checkbox]').val('0');
		$('.positionChangeSubmit_form input[type=checkbox]').attr("checked",false);
	};
	
	// 异动原因选项提示
	function changeReason_option_tips(){
		$('.remark3 option').each( function(){
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