<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.util.KANUtil"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeePositionChangeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box toggableForm">
		<div class="head">
			<label id="pageTitle">
				<bean:message bundle="business" key="employee.position.change.quick" />
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
								<input type="button" class="save" id="btnEdit" name="btnEdit" value="<bean:message bundle="public" key="button.edit" />" style="display: none;"/>
							</logic:empty>
						</logic:notEqual>
					</logic:notEqual>
				</kan:auth>
				<kan:auth right="new" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="save" name="btnSave" id="btnSave" style="display: none;" value="<bean:message bundle="public" key="button.save" />" /> 
				</kan:auth>
				<kan:auth right="submit" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" style="display: none;" value="<bean:message bundle="public" key="button.submit" />" /> 
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeePositionChangeAction.accessAction%>">
					<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
				</kan:auth>
			</div>
			
			<html:form action="employeePositionChangeAction.do?proc=add_objectQuick" styleClass="positionChange_form">
				<%= BaseAction.addToken( request ) %>
				<input type="hidden" id="positionChangeId" name="id" value="<bean:write name='employeePositionChangeForm' property='encodedId' />">
				<html:hidden property="subAction" styleClass="subAction" styleId="subAction" />
				<html:hidden property="submitFlag" styleClass="submitFlag" styleId="submitFlag" />
				<html:hidden property="status" styleClass="status" styleId="status" />
				<fieldset>
					<ol class="auto">
						<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="public" key="public.employee2.id" /><em> *</em></label>
							<html:text property="employeeId" styleClass="employeeId important" styleId="employeeId" />
							<a id="employeeSearch" onclick="popupEmployeeSearch();" class="kanhandle">
								<img src="images/search.png" title="<bean:message bundle="public" key="button.search" />">
							</a>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position" /><em> *</em></label>
							<html:select property="oldPositionId" styleClass="positionSelect" styleId="positionSelect">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.no" /></label>
							<html:text property="employeeNo" styleClass="employeeNo" styleId="employeeNo" disabled="true" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.branch" /></label>
							<html:text property="oldBranchName" styleClass="branchName" styleId="branchName" disabled="true" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.name" /></label>
							<html:text property="employeeName" styleClass="employeeName" styleId="employeeName" disabled="true" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.certificate.number" /></label>
							<html:text property="employeeCertificateNumber" styleClass="employeeCertificateNumber" styleId="employeeCertificateNumber" disabled="true" />
						</li>
					</ol>
					
					<%-- 职位信息调整 --%>
					<ol class="auto">
						<li>
							<label>EX- Working Title/Position (Chinese)</label>
							<html:text property="oldPositionNameZH" styleClass="oldPositionNameZH" styleId="oldPositionNameZH" disabled="true" />
						</li>
						<li>
							<label>New Working Title/Position (Chinese)<em> *</em></label>
							<html:text property="newPositionNameZH" styleClass="newPositionNameZH" styleId="newPositionNameZH" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>EX- Working Title/Position (English)</label>
							<html:text property="oldPositionNameEN" styleClass="oldPositionNameEN" styleId="oldPositionNameEN" disabled="true" />
						</li>
						<li>
							<label>New Working Title/Position (English)</label>
							<html:text property="newPositionNameEN" styleClass="newPositionNameEN" styleId="newPositionNameEN" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>EX- BU/Function</label>
							<html:select property="oldParentBranchId" styleClass="oldParentBranchId" styleId="oldParentBranchId" disabled="true">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>	
						<li>	
							<label>New BU/Function</label>
							<html:select property="newParentBranchId" styleClass="newParentBranchId" styleId="newParentBranchId" disabled="true">
								<html:option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></html:option>
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>EX- Department</label>
							<html:select property="oldBranchId" styleClass="oldBranchId" styleId="oldBranchId" disabled="true">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>	
						<li>	
							<label>New Department<em> *</em></label>
							<html:select property="newBranchId" onchange="newBranchChange(this);" styleClass="newBranchId" styleId="newBranchId" >
								<html:option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></html:option>
								<html:optionsCollection property="branchs" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>EX- Job Grade</label>
							<html:select property="oldPositionGradeId" styleClass="oldPositionGradeId" styleId="oldPositionGradeId" disabled="true">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
								<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
							</html:select>
						</li>	
						<li>	
							<label>New Job Grade<em> *</em></label>
							<html:select property="newPositionGradeId" onchange="newPositionGradeChange(this);" styleClass="newPositionGradeId" styleId="newPositionGradeId" >
								<html:option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></html:option>
								<html:optionsCollection property="positionGrades" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label>EX- Working Title / Direct Report Manager</label>
							<html:text property="oldParentPositionOwners" styleClass="oldParentPositionOwners" styleId="oldParentPositionOwners" disabled="true" />
						</li>
						<li>
							<label>New Working Title / Direct Report Manager</label>
							<html:text property="newParentPositionOwnersName" styleClass="newParentPositionOwnersName" styleId="newParentPositionOwnersName" />
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="business" key="employee.position.change.isChildChange" /></label>
							<html:select property="isChildChange" styleClass="isChildChange">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label>New Working Title / Direct Report Manager (Biz Leader)</label>
							<html:text property="remark2" styleClass="remark2" styleId="remark2" />
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
						<li>
							<label><bean:message bundle="business" key="employee.position.change.effectiveDate" /><em> *</em></label>
							<html:text property="effectiveDate" styleClass="effectiveDate Wdate" styleId="effectiveDate" onfocus="WdatePicker();" />
						</li>
						<li>
							<label><bean:message bundle="business" key="employee.position.change.description" /><em> *</em></label>
							<html:select property="remark3" styleClass="remark3" styleId="remark3">
								<html:optionsCollection property="changeReasons" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>	
					<ol class="auto">	
						<li>
							<label><bean:message bundle="public" key="public.note" /></label>
							<html:textarea property="description" styleClass="description" styleId="description" />
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
					<html:hidden property="employeeNameZH" styleClass="employeeNameZH" styleId="employeeNameZH" />
					<html:hidden property="employeeNameEN" styleClass="employeeNameEN" styleId="employeeNameEN" />
					<html:hidden property="newPositionId" styleClass="newPositionId" styleId="newPositionId" />
					<html:hidden property="staffId" styleClass="staffId" styleId="staffId" />
					<html:hidden property="oldStaffPositionRelationId" styleClass="oldStaffPositionRelationId" styleId="oldStaffPositionRelationId" />
					<html:hidden property="oldParentPositionId" styleClass="oldParentPositionId" styleId="oldParentPositionId" />
					<html:hidden property="newParentPositionId" styleClass="newParentPositionId" styleId="newParentPositionId" />
					<%--old Names --%>
					<html:hidden property="oldParentBranchNameZH" styleClass="oldParentBranchNameZH small" styleId="oldParentBranchNameZH" />
					<html:hidden property="oldParentBranchNameEN" styleClass="oldParentBranchNameEN small" styleId="oldParentBranchNameEN" />
					<html:hidden property="oldBranchNameZH" styleClass="oldBranchNameZH small" styleId="oldBranchNameZH" />
					<html:hidden property="oldBranchNameEN" styleClass="oldBranchNameEN small" styleId="oldBranchNameEN" />
					<html:hidden property="oldPositionGradeNameZH" styleClass="oldPositionGradeNameZH small" styleId="oldPositionGradeNameZH" />
					<html:hidden property="oldPositionGradeNameEN" styleClass="oldPositionGradeNameEN small" styleId="oldPositionGradeNameEN" />
					<html:hidden property="oldParentPositionNameZH" styleClass="oldParentPositionNameZH small" styleId="oldParentPositionNameZH" />
					<html:hidden property="oldParentPositionNameEN" styleClass="oldParentPositionNameEN small" styleId="oldParentPositionNameEN" />
					<html:hidden property="oldParentPositionOwnersZH" styleClass="oldParentPositionOwnersZH small" styleId="oldParentPositionOwnersZH" />
					<html:hidden property="oldParentPositionOwnersEN" styleClass="oldParentPositionOwnersEN small" styleId="oldParentPositionOwnersEN" />
					<%--new Names --%>
					<html:hidden property="newParentBranchNameZH" styleClass="newParentBranchNameZH small" styleId="newParentBranchNameZH" />
					<html:hidden property="newParentBranchNameEN" styleClass="newParentBranchNameEN small" styleId="newParentBranchNameEN" />
					<html:hidden property="newBranchNameZH" styleClass="newBranchNameZH small" styleId="newBranchNameZH" />
					<html:hidden property="newBranchNameEN" styleClass="newBranchNameEN small" styleId="newBranchNameEN" />
					<html:hidden property="newPositionGradeNameZH" styleClass="newPositionGradeNameZH small" styleId="newPositionGradeNameZH" />
					<html:hidden property="newPositionGradeNameEN" styleClass="newPositionGradeNameEN small" styleId="newPositionGradeNameEN" />
					<html:hidden property="newParentPositionNameZH" styleClass="newParentPositionNameZH small" styleId="newParentPositionNameZH" />
					<html:hidden property="newParentPositionNameEN" styleClass="newParentPositionNameEN small" styleId="newParentPositionNameEN" />
					<html:hidden property="newParentPositionOwnersZH" styleClass="newParentPositionOwnersZH small" styleId="newParentPositionOwnersZH" />
					<html:hidden property="newParentPositionOwnersEN" styleClass="newParentPositionOwnersEN small" styleId="newParentPositionOwnersEN" />
				</fieldset>
			</html:form>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');			
		$('#menu_employee_Position_Changes').addClass('selected');
		
		// 启用联想
		bindThinkingToParentPositionName();
		bindParentPositionNameBlur();
		// 异动原因选项提示
		changeReason_option_tips();
		
		$('#oldBranchId').find('option').each( function(i){
			$(this).html($(this).text());
		});
		$('#newBranchId').find('option').each( function(i){
			$(this).html($(this).text());
		});
		
		// 取消按钮点击事件
		$('#btnList').click( function () {
			if (agreest())
			link('employeePositionChangeAction.do?proc=list_object');
		});
		
		// 员工Id keyup事件
		$("#employeeId").keyup(function(){
			var oldPositionId = null;
			if( $('form .subAction').val() == 'viewObject'){
				oldPositionId = '<bean:write name="employeePositionChangeForm" property="oldPositionId" />';
			}
			loadEmployeeBaseInfo($(this).val());
			loadEmployeePositionOpitons($(this).val(),oldPositionId);
		});
		
		// 职位change事件
		$('#positionSelect').change( function(){
			if(checkPositionStaffNum($(this).val())){
				loadPositionInfo($('#employeeId').val(), $(this).val());
			}else{
				alert('<bean:message bundle="public" key="popup.position.many.staff" />');
			}
		});
		
		// 保存按钮点击事件
		$('#btnSave').click( function(){
			var flag = validate_form();
			if(flag == 0){
				enableForm('positionChange_form');
				submit('positionChange_form');
			}
		});
		
		// 提交按钮点击事件
		$('#btnSubmit').click( function(){
			var flag = validate_form();
			if(flag == 0){
				$("#submitFlag").val("4");
				enableForm('positionChange_form');
				submit('positionChange_form');
			}
		});
		
		// 编辑按钮点击事件
		$('#btnEdit').click( function(){
			$(this).hide();	
			$('#btnSave').show();
			$('#btnSubmit').show();
			$('#employeeId').addClass('important');
			
			var classNames = new Array();
			classNames[0] = 'employeeId';
			classNames[1] = 'positionSelect';
			classNames[2] = 'newPositionNameZH';
			classNames[3] = 'newPositionNameEN';
			classNames[4] = 'newBranchId';
			classNames[5] = 'newPositionGradeId';
			classNames[6] = 'newParentPositionOwnersName';
			classNames[7] = 'isChildChange';
			classNames[8] = 'effectiveDate';
			classNames[9] = 'remark3';
			classNames[10] = 'description';
			classNames[11] = 'remark1';
			removeDisabled(classNames);
			enableLink('positionChange_form');
			$('.positionChange_form').attr('action','employeePositionChangeAction.do?proc=modify_objectQuick')
		});
		
		if( $('.positionChange_form .subAction').val() == 'createObject'){
			$('#btnSave').show();
			$('#btnSubmit').show();
			$('.decodeModifyBy').attr('disabled', 'disabled');
			$('.decodeModifyDate').attr('disabled', 'disabled');
		}else{
			$('#employeeId').trigger('keyup');
			$('#employeeId').removeClass('important');
			disableForm('positionChange_form');
			if($('#status').val() == '1'){
				$('#btnEdit').show();
			}
		}
	})(jQuery);
	
	// 加载员工基本信息
	function loadEmployeeBaseInfo( employeeId ){
		if(employeeId != null && employeeId != ''){
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=getEmployeeInfoByEmployeeId', 
				type: 'POST', 
				data : {employeeId:employeeId},
				dataType : 'json',
				async    : true,
				success: function(result){
					//页面赋值
					$("#employeeNo").val(result.employeeNo);
					$("#employeeName").val(result.employeeName);
					$("#employeeNameZH").val(result.employeeNameZH);
					$("#employeeNameEN").val(result.employeeNameEN);
					$("#employeeCertificateNumber").val(result.employeeCertificateNumber);
				}
			});
		}
	};
	
	// 加载员工职位列表
	function loadEmployeePositionOpitons( employeeId, oldPositionId ){
		if(employeeId != null && employeeId != ''){
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=getPositionsByEmployeeId', 
				type: 'POST', 
				data : {employeeId:employeeId,oldPositionId:oldPositionId},
				dataType : 'json',
				async:true,
				success: function(result){
					$("#positionSelect").empty();
					for(var i = 0 ; i < result.length ; i ++){
						if($('form .subAction').val() == 'viewObject' && result[i].id ==$('#newPositionId').val() ){
							$("#positionSelect")[0].options.add(new Option(result[i].name,result[i].id, 0,true));
						}else{
							$("#positionSelect")[0].options.add(new Option(result[i].name,result[i].id));
						}
					}
				}
			});
		}
	};
	
	// 检查职位上是否存在多名员工
	function checkPositionStaffNum( positionId ){
		var flag = true;
		if (positionId != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=checkPositionStaffNum_ajax', 
				type: 'POST', 
				data : {positionId:positionId},
				async    : false,
				success: function(result){
					flag = result == '1' ? true : false;
				}
			});
		}
		return flag;
	};
	
	// 加载员工职位明细
	function loadPositionInfo( employeeId, positionId ){
		if (positionId != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=loadPositionInfo_ajax', 
				type: 'POST', 
				data : {employeeId:employeeId,positionId:positionId},
				dataType : 'json',
				async    : false,
				success: function(result){
					$('#branchName').val(result.oldBranchName);
					$('#oldPositionNameZH').val(result.oldPositionNameZH);
					$('#newPositionNameZH').val(result.oldPositionNameZH);
					$('#oldPositionNameEN').val(result.oldPositionNameEN);
					$('#newPositionNameEN').val(result.oldPositionNameEN);
					$('#oldParentBranchId').val(result.oldParentBranchId);
					$('#newParentBranchId').val(result.oldParentBranchId);
					$('#oldBranchId').val(result.oldBranchId);
					$('#newBranchId').val(result.oldBranchId);
					$('#oldPositionGradeId').val(result.oldPositionGradeId);
					$('#newPositionGradeId').val(result.oldPositionGradeId);
					
					$('#newPositionId').val(positionId);
					$('#staffId').val(result.staffId)
					$('#oldStaffPositionRelationId').val(result.oldStaffPositionRelationId);
					$('#newParentPositionId').val(result.oldParentPositionId);
					$('#oldParentPositionId').val(result.oldParentPositionId);
					$('#newParentPositionId').val(result.oldParentPositionId);
					bindThinkingToParentPositionName();
					var oldParentPositionOwners = result.oldParentPositionNameZH + ' / ' + result.oldParentPositionNameEN + ' - ' + result.oldParentPositionOwners;
					$('#oldParentPositionOwners').val(oldParentPositionOwners);
					// oldNames
					$('#oldParentBranchNameZH').val(result.oldParentBranchNameZH);
					$('#oldParentBranchNameEN').val(result.oldParentBranchNameEN);
					$('#oldBranchNameZH').val(result.oldBranchNameZH);
					$('#oldBranchNameEN').val(result.oldBranchNameEN);
					$('#oldPositionGradeNameZH').val(result.oldPositionGradeNameZH);
					$('#oldPositionGradeNameEN').val(result.oldPositionGradeNameEN);
					$('#oldParentPositionNameZH').val(result.oldParentPositionNameZH);
					$('#oldParentPositionNameEN').val(result.oldParentPositionNameEN);
					$('#oldParentPositionOwnersZH').val(result.oldParentPositionOwnersZH);
					$('#oldParentPositionOwnersEN').val(result.oldParentPositionOwnersEN);
					// newNames
					$('#newParentBranchNameZH').val(result.oldParentBranchNameZH);
					$('#newParentBranchNameEN').val(result.oldParentBranchNameEN);
					$('#newBranchNameZH').val(result.oldBranchNameZH);
					$('#newBranchNameEN').val(result.oldBranchNameEN);
					$('#newPositionGradeNameZH').val(result.oldPositionGradeNameZH);
					$('#newPositionGradeNameEN').val(result.oldPositionGradeNameEN);
					$('#newParentPositionNameZH').val(result.oldParentPositionNameZH);
					$('#newParentPositionNameEN').val(result.oldParentPositionNameEN);
					$('#newParentPositionOwnersZH').val(result.oldParentPositionOwnersZH);
					$('#newParentPositionOwnersEN').val(result.oldParentPositionOwnersEN);
				}
			});
		}
	};
	
	function bindParentPositionNameBlur(){
		$('#newParentPositionOwnersName').blur( function(){
			if($('#newParentPositionId').val() == ''){
				$('#newParentPositionId').val('0');
				$('#newParentPositionNameZH').val('');
				$('#newParentPositionNameEN').val('');
				$('#newParentPositionOwnersZH').val('');
				$('#newParentPositionOwnersEN').val('');	
			}
		});
	};
	
	// Use the common thinking
	function bindThinkingToParentPositionName(){
		kanThinking_column('newParentPositionOwnersName', 'newParentPositionId', 'positionAction.do?proc=list_object_json', null, 'parentPositionChangeCallback()' );
	};
	
	// 上级职位改变回调函数 
	function parentPositionChangeCallback(){
		var newParentPositionId = $('#newParentPositionId').val();
		if (newParentPositionId != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=parentPositionChange_ajax', 
				type: 'POST', 
				data : {newParentPositionId:newParentPositionId},
				dataType : 'json',
				async    : false,
				success: function(result){
					$('#newParentPositionNameZH').val(result.newParentPositionNameZH);
					$('#newParentPositionNameEN').val(result.newParentPositionNameEN);
					$('#newParentPositionOwnersZH').val(result.newParentPositionOwnersZH);
					$('#newParentPositionOwnersEN').val(result.newParentPositionOwnersEN);	
				}
			});
		}
	};
	
	// 部门改变
	function newBranchChange( newBranchId ){
		if (newBranchId.value != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=branchChange_ajax', 
				type: 'POST', 
				data : {newBranchId:newBranchId.value},
				dataType : 'json',
				async    : false,
				success: function(result){
					$('#newBranchNameZH').val(result.newBranchNameZH);
					$('#newBranchNameEN').val(result.newBranchNameEN);
					$('#newParentBranchId').val(result.newParentBranchId);
					$('#newParentBranchNameZH').val(result.newParentBranchNameZH);
					$('#newParentBranchNameEN').val(result.newParentBranchNameEN);	
				}
			});
		}else{
			$('#newBranchNameZH').val('');
			$('#newBranchNameEN').val('');
			$('#newParentBranchId').val('');
			$('#newParentBranchNameZH').val('');
			$('#newParentBranchNameEN').val('');
		}
	};
	
	// 职级改变
	function newPositionGradeChange( newPositionGradeId ){
		if (newPositionGradeId.value != "0") {
			$.ajax({
				url: 'employeePositionChangeAction.do?proc=positionGradeChange_ajax', 
				type: 'POST', 
				data : {newPositionGradeId:newPositionGradeId.value},
				dataType : 'json',
				async    : false,
				success: function(result){
					$('#newPositionGradeNameZH').val(result.newPositionGradeNameZH);
					$('#newPositionGradeNameEN').val(result.newPositionGradeNameEN);
				}
			});
		}else{
			$('#newPositionGradeNameZH').val('');
			$('#newPositionGradeNameEN').val('');
		}
	};
	
	function validate_form(){
		var flag = 0;
		flag = flag + validate('employeeId', true, 'common', 10, 0, 0, 0);
		flag = flag + validateEmployeeId();
		flag = flag + validate('positionSelect', true, 'select', 0, 0, 0, 0);
		flag = flag + validate('newPositionNameZH', true, 'common', 50, 0, 0, 0);
		flag = flag + validate('newPositionNameEN', false, 'common', 50, 0, 0, 0);
		flag = flag + validate('newBranchId', true, 'select', 0, 0, 0, 0);
		flag = flag + validate('newPositionGradeId', true, 'select', 0, 0, 0, 0);
		flag = flag + validate('effectiveDate', true, 'commom', 10, 0, 0, 0);
		flag = flag + validate('remark3', true, 'select', 0, 0, 0, 0);
		
		return flag;
	};
	
	function removeDisabled( classNames ){
		for(var i in classNames ){
			$('.' + classNames[i] ).attr('disabled',false);
		}
	};
	
	// 验证employeeId是否合法
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