<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@	page import="com.kan.base.util.CachedUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractSBModalId">
    <div class="modal-header" id="employeeContractSBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractSBModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="sb" key="sb.solution" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save hide" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />"/>
	   		<kan:auth right="submit" action="HRO_SB_PURCHASE">
	   			<input title="<bean:message bundle="public" key="button.submit" />" type="button" class="function hide" name="btnPopupSubmit" id="btnPopupSubmit" onclick="submitPopup();" value="<bean:message bundle="public" key="button.submit" />"/>
	   		</kan:auth>
	   		<input title="<bean:message bundle="public" key="button.delete" />" type="button" class="delete hide" name="btnPopupDelete" id="btnPopupDelete" onclick="deletePopup();" value="<bean:message bundle="public" key="button.delete" />"/>
	    	<input type="button" class="reset hide" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
        <html:form action="employeeContractSBAction.do?proc=modify_objects_popup" styleClass="employeeContractSB_form">
        	<%= BaseAction.addToken( request ) %>
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<fieldset>
				<ol class="auto hide" id="applyToAllHeaderOL">
					<li>
						<label id="sbSolutionIdLabel">是否修改<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的社保公积金信息：</label>
						<input type="checkbox" id="applyToAllHeader" name="applyToAllHeader" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeHeaderOL">
					<li>
					    <span class="highlight">注意： <br/>  1、如果勾选则将修改选择<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的对应社保公积金方案类型的其他数据。<br/>  2、如<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>不存在对应社保公积金方案类型数据则创建该类型社保公积金方案（复选框是否勾选不影响创建）。<br/> 3、如<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>存在对应社保公积金方案类型数据：①按“保存”只修改数值数据;②按“提交”则根据已选择社保公积金类型状态进行“加保”或“退保”操作。</span>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label>派送信息ID</label><span><html:text property="contractId" styleClass="manageEmployeeContractSB_contractId"></html:text></span>
						<input type="hidden" class="manageEmployeeContractSB_encodedContractId" name="encodedContractId" id="manageEmployeeContractSB_encodedContractId" value="" />
					</li>
					<li>
						<label>方案ID</label><span><html:text property="employeeSBId" styleClass="manageEmployeeContractSB_employeeSBId"></html:text></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.solution" /><em> *</em></label>
						<html:select property="sbSolutionId" styleClass="manageEmployeeContractSB_sbSolutionId">
							<html:optionsCollection property="solutions" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.vendor" /></label> 
						<html:select property="vendorId" styleClass="manageEmployeeContractSB_vendorId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="business" key="business.vendor.service" /></label> 
						<html:select property="vendorServiceId" styleClass="manageEmployeeContractSB_vendorServiceId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.start.date" /></label> 
						<input type="hidden" id="tempStartDate" value=""/>
						<input name="startDate" class="manageEmployeeContractSB_startDate  Wdate " id="manageEmployeeContractSB_startDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'tempStartDate\')}', maxDate:'#F{ $dp.$D(\'manageEmployeeContractSB_endDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.end.date" /></label> 
						<input type="hidden" id="tempEndDate" value=""/>
						<input name="endDate" class="manageEmployeeContractSB_endDate  Wdate " id="manageEmployeeContractSB_endDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'manageEmployeeContractSB_startDate\')}', maxDate:'#F{ $dp.$D(\'tempEndDate\')}'})" type="text" maxlength="10">
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.need.medical.card" /></label>
						<html:select property="needMedicalCard" styleClass="manageEmployeeContractSB_needMedicalCard">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.employee.medical.number" /></label>
						<html:text property="medicalNumber" maxlength="15" styleClass="manageEmployeeContractSB_medicalNumber"></html:text>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.need.sb.card" /></label> 
						<html:select property="needSBCard" styleClass="manageEmployeeContractSB_needSBCard">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.employee.sb.number" /></label>
						<html:text property="sbNumber" maxlength="15" styleClass="manageEmployeeContractSB_sbNumber"></html:text>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.employee.fund.number" /></label>
						<html:text property="fundNumber" maxlength="15" styleClass="manageEmployeeContractSB_fundNumber	"></html:text>
					</li>
					<li>
						<label><bean:message bundle="public" key="public.status" /></label> 
						<html:select property="status" styleClass="manageEmployeeContractSB_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label><bean:message bundle="public" key="public.description" /></label> 
						<html:textarea property="description" styleClass="manageEmployeeContractSB_description"></html:textarea>
					</li>
				</ol>
				<ol class="auto hide" id="applyToAllDetailOL">
					<li>
						<label id="solutionDetailIdLabel">是否修改<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的社保公积金明细信息：</label>
						<input type="checkbox" id="applyToAllDetail" name="applyToAllDetail" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeDetailOL">
					<li>
					    <span class="highlight">注意： <br/>  1、如果勾选则将修改选择<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的对应社保公积金方案类型的明细信息数据，否则只修改方案信息，不修改明细信息。<br/>  2、如<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>不存在对应社保公积金方案类型数据则创建该类型社保公积金方案同时会生成方案的明细信息（复选框是否勾选不影响生成）。<br/></span>
					</li>
				</ol>
			</fieldset>
			<div id="special_info">
				<!-- 社保公积金Detail信息 -->
				<jsp:include page="/popup/extend/listEmployeeContractSBDetail.jsp"></jsp:include>
			</div>			
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 加载供应商下拉框
		loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// 加载供应商服务下拉框
		loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// 加载社保公积金明细Tab页
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		// 社保公积金方案Change事件
		$(".manageEmployeeContractSB_sbSolutionId").change(function(){
			$('#special_info').removeClass('hide');
			if(getPopSubAction() != 'viewObject') {
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=' + $('.manageEmployeeContractSB_contractId').val() + '&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val(), false, null);
			}
			// 加载供应商下拉框
			 loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, '$(".manageEmployeeContractSB_sbSolutionIdvendorId").change();');
		});
		
		// 供应商change事件加载供应商服务
		$(".manageEmployeeContractSB_vendorId").change(function(){
			// 加载供应商服务下拉框
			loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + $('.manageEmployeeContractSB_sbSolutionId').val() + '&vendorId=' + $('.manageEmployeeContractSB_vendorId').val() + '&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>',false,function(){
				if($('.manageEmployeeContractSB_vendorServiceId option').size()==2){
					$('.manageEmployeeContractSB_vendorServiceId option').eq(1).attr('selected','seleccted');
				}
			});
		});
		
		// 添加社保公积金方案情况 - 需要显示提交按钮
		if( getPopSubAction() == 'createObject' ){
			$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
		}
		
		// 初始化隐藏Button、Disable Form
		hideButton();

		// 加保提交、退保提交和已退保状态不能编辑||存在工作流不能编辑
		if( '<bean:write name="employeeContractSBForm" property="workflowId" />' == '' && '<bean:write name="employeeContractSBForm" property="status" />' != '1' && '<bean:write name="employeeContractSBForm" property="status" />' != '4' && '<bean:write name="employeeContractSBForm" property="status" />' != '6'){
			$('#btnPopupEdit').show();
		}
		
		disableForm('employeeContractSB_form');

		// 保存按钮点击事件
		$('#btnPopupSave').click( function () {		
	    	// 清空错误
	    	cleanAllError();
	    	
			var status = $('.manageEmployeeContractSB_status').val();
			var selectedIds =  $('.list_form input[id="selectedIds"]').val();
			var contractId = $('.manageEmployeeContractSB_contractId').val();
			var encodedContractId = $('.manageEmployeeContractSB_encodedContractId').val();
			
			// 如果是查看
			if(getPopSubAction() == 'viewObject'){
				enableForm('employeeContractSB_form');
				// “社保公积金方案”不可修改
				$('.manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
				// 状态不可以修改
				$('.manageEmployeeContractSB_status').attr('disabled', true);
				// 修改subAction为修改
				$('.employeeContractSB_form input[id="subAction"]').val('modifyObject');
				// 显示相关Btn
				$('#btnPopupSave').removeClass('hide');
				$('#btnPopupReset').removeClass('hide');
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
				
				// 如果是“无社保公积金”状态显示“加保提交”
				if(status == 0){
					$('#btnPopupSubmit').removeClass('hide');
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
					$('.manageEmployeeContractSB_sbSolutionId').removeAttr('disabled');
					$('#manageEmployeeContractSB_endDate').attr('disabled', true);
				}
				// “待加保”、“正常购买” 显示“退保提交”
				else if(status == 2 || status == 3){
					$('#btnPopupSubmit').removeClass('hide');
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
					if(status == 2){
						$('#manageEmployeeContractSB_startDate').attr('disabled', false);
					}else{
						$('#manageEmployeeContractSB_startDate').attr('disabled', true);
					}
				}
				// “带待申报退保”
				else if(status == 5){
					$('#manageEmployeeContractSB_startDate,#manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
				}
				// 已退保情况编辑按钮点击事件时：1、隐藏保存按钮；2、显示加保提交按钮；3、清空日期（退保日期disabled）；4、重新绑定日期控件（加保日期不得小于之前的退保日期）
				else if(status == 6){
// 					$('#btnPopupSubmit').addClass('hide');
// 					$('#manageEmployeeContractSB_startDate').attr('disabled', true);
// 					$('#manageEmployeeContractSB_endDate').attr('disabled', true);
					
					$('#btnPopupSave').hide();
					$('#btnPopupSubmit').show();
					$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
					
					$('#manageEmployeeContractSB_endDate, #sbSolutionId').attr('disabled', 'disabled');
					
					var endDate = getAppointDate($('#manageEmployeeContractSB_endDate').val(), 1);
					
					// 取消加保日期onfocus事件
					$('#manageEmployeeContractSB_startDate').val(endDate);
					$('#manageEmployeeContractSB_startDate').attr('onfocus',"WdatePicker({minDate:'"+ endDate +"'})");
					$('#manageEmployeeContractSB_endDate').val("");
				}

			}
			// 如果是单条新增
			else if(getPopSubAction() == 'createObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// 提交模态框
					submitForm('employeeContractSB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
				}
					
			}
			// 如果是单条或批量修改
			else if(getPopSubAction() == 'modifyObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// 提交模态框
					submitForm('employeeContractSB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
				}
				
			}
		});
	})(jQuery);

	// 弹出模态窗口_添加(单条)
	function addEmployeeContractSB(contractId, encodedContractId){
		$('#special_info').addClass('hide');
		$.ajax({
			url:'employeeContractAction.do?proc=get_object_json&contractId=' + contractId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('#tempStartDate').val(data.startDate);

				// 隐藏Button
				hideButton();
				enableForm('employeeContractSB_form');
				// 状态不可以修改
				$('.manageEmployeeContractSB_status').attr('disabled', true);
				
				// 重置ContractId及清空EmployeeSB ID
				$('.manageEmployeeContractSB_contractId').val(contractId);
				$('.manageEmployeeContractSB_employeeSBId').val('');
				
				// 修改Action		
				$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
				// 显示“保存”和“重置”button
				$('#btnPopupSave').removeClass('hide');
				$('#btnPopupReset').removeClass('hide');
				// 显示“加保提交”button
				$("#btnPopupSubmit").show();
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.claim" />');
				// manageEmployeeContractSB_endDate不可编辑
				$('.modal-body #manageEmployeeContractSB_endDate').attr('disabled', true);
				// subAction为新增
				$('.employeeContractSB_form input[id="subAction"]').val('createObject');
				// 加载社保公积金下拉框
				loadSolutionHeaderOptions(encodedContractId, null, false);
			}
		});
	};
	
	// 弹出模态窗口_操作(多条)
	function addEmployeeContractSBs(){
		$('#special_info').removeClass('hide');
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		
		// 移除CheckBox属性
		$('#applyToAllHeader').removeAttr('checked');
		$('#applyToAllDetail').removeAttr('checked');
		
		// 如果未选择记录
		if(selectedIds == null || selectedIds.trim() == ''){
			alert("请选择<logic:equal name='role' value='1'>雇员</logic:equal><logic:equal name='role' value='2'>员工</logic:equal>记录！");
		}else{
			// 填入Popup SelectedIds值
			$('.employeeContractSB_form input[id="selectedIds"]').val(selectedIds);
			// 隐藏Button
			hideButton();
			enableForm('employeeContractSB_form');
			// 状态不可以修改
			$('.manageEmployeeContractSB_status').attr('disabled', true);
			
			// 修改Button
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit" />');
			// 清空Contract ID 及EmployeeSB ID
			$('.manageEmployeeContractSB_contractId').val('');
			$('.manageEmployeeContractSB_employeeSBId').val('');
			
			// 修改Action		
			$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_objects_popup');
			// 显示“保存”、“提交”和“重置”button
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupSubmit').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			// 显示提示栏和复选框
			$('#applyToAllHeaderOL').removeClass('hide');
			$('#applyToAllDetailOL').removeClass('hide');
			$('#noticeHeaderOL').removeClass('hide');
			$('#noticeDetailOL').removeClass('hide');
			// subAction为新增
			$('.employeeContractSB_form input[id="subAction"]').val("modifyObject");

			// 加载社保公积金下拉框
			loadSolutionHeaderOptions('', '', true);
		}
	}
	
	// 弹出模态窗口_退保
	function rollbackEmployeeContractSB(employeeSBId){
		$('#special_info').removeClass('hide');
		// 隐藏Button
		hideButton();
		enableForm('employeeContractSB_form');
		// 状态不可以修改
		$('.manageEmployeeContractSB_status').attr('disabled', true);
		// 方案不能修改
		$('.manageEmployeeContractSB_sbSolutionId').attr('disabled', true);
		
		// 修改Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
				$('.manageEmployeeContractSB_encodedContractId').val(data.encodedContractId);
				$('.manageEmployeeContractSB_employeeSBId').val(data.employeeSBId);
				$('.manageEmployeeContractSB_sbSolutionId').val(data.sbSolutionId);
				$('.manageEmployeeContractSB_vendorId').val(data.vendorId);
				$('.manageEmployeeContractSB_vendorServiceId').val(data.vendorServiceId);
				$('.manageEmployeeContractSB_needMedicalCard').val(data.needMedicalCard);
				$('.manageEmployeeContractSB_medicalNumber').val(data.medicalNumber);
				$('.manageEmployeeContractSB_needSBCard').val(data.needSBCard);
				$('.manageEmployeeContractSB_sbNumber').val(data.sbNumber);
				$('.manageEmployeeContractSB_fundNumber').val(data.fundNumber);
				$('.manageEmployeeContractSB_description').val(data.description);
				$('.manageEmployeeContractSB_status').val(data.status);
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('.modal-body #manageEmployeeContractSB_endDate').val(data.endDate);

				// 加载社保公积金下拉框
				loadSolutionHeaderOptions(data.encodedContractId, data.sbSolutionId, true);
				// 加载供应商下拉框
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// 加载供应商服务下拉框
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// 加载社保公积金明细Tab页
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});
		
		// 修改Button
		$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
		// 修改subAction为修改
		$('.employeeContractSB_form input[id="subAction"]').val('modifyObject');
		// 显示“保存”和“重置”button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// 显示“退保提交”
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.submit.to.stop.claim" />');
		// manageEmployeeContractSB_startDate不可编辑
		$('#manageEmployeeContractSB_startDate').attr('disabled', true);
		// 显示模态框
		showPopup();
	}
	
	// 弹出模态窗口_修改
	function modifyEmployeeContractSB(employeeSBId){
		$('#special_info').removeClass('hide');
		disableForm('employeeContractSB_form');
		// 隐藏Button
		hideButton();
		
		// 修改SubAction
		$('.employeeContractSB_form input[id="subAction"]').val('viewObject');
		// 修改Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');

		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
				$('.manageEmployeeContractSB_encodedContractId').val(data.encodedContractId);
				$('.manageEmployeeContractSB_employeeSBId').val(data.employeeSBId);
				$('.manageEmployeeContractSB_sbSolutionId').val(data.sbSolutionId);
				$('.manageEmployeeContractSB_vendorId').val(data.vendorId);
				$('.manageEmployeeContractSB_vendorServiceId').val(data.vendorServiceId);
				$('.manageEmployeeContractSB_needMedicalCard').val(data.needMedicalCard);
				$('.manageEmployeeContractSB_medicalNumber').val(data.medicalNumber);
				$('.manageEmployeeContractSB_needSBCard').val(data.needSBCard);
				$('.manageEmployeeContractSB_sbNumber').val(data.sbNumber);
				$('.manageEmployeeContractSB_fundNumber').val(data.fundNumber);
				$('.manageEmployeeContractSB_description').val(data.description);
				$('.manageEmployeeContractSB_status').val(data.status);
				$('#manageEmployeeContractSB_startDate').val(data.startDate);
				$('.modal-body #manageEmployeeContractSB_endDate').val(data.endDate);
				
				// “加保提交”、“退保提交” 只能看，不能修改
				if(data.status == 1 || data.status == 4){
					hideButton();
				}
				else{
					$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
					$('#btnPopupSave').show();
					
					$('#btnPopupSubmit').hide();
				}

				// 加载社保公积金下拉框
				loadSolutionHeaderOptions(data.encodedContractId, data.sbSolutionId, false);
				// 加载供应商下拉框
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// 加载供应商服务下拉框
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// 加载社保公积金明细Tab页
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});
		
	}
	
	// 删除社保公积金方案
	function deleteEmployeeContractSB(employeeSBId){
		if(confirm("确定删除该条记录？")){
			// 注入SelectedIds值
			$('.employeeContractSB_form input[id="selectedIds"]').val(employeeSBId);
			// 提交
			submitForm('employeeContractSB_form', 'deleteObjects', null, null, null, 'search-results', "employeeContractSBAction.do?proc=list_object", hidePopup());
		}
	}
	
	// “提交”按钮
	function submitPopup(){
		// 需同时验证必填项及日期
		if(!checkPopupValue() && !checkDateValue()){
			// 提交模态框
			submitForm('employeeContractSB_form', 'submitObject', null, null, null, 'search-results', null, hidePopup());
		}
	}

	// 检查日期是否有效
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractSB_status').val();
		var contractId = $('.manageEmployeeContractSB_contractId').val();
		var flag = 0;
		
		// 如果是批量设置需验证“加保日期”、“退保日期”
		if(!contractId){
			flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
			flag = flag + validate("manageEmployeeContractSB_endDate", true, null, 0, 0);
		}
		// 如果是“无社保公积金”(加保提交) - 加保日期不能为空
		else if(status == 0){
			flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
		}
		// 如果是“待加保”、“正常购买”(退保提交) - 退保日期不能为空
		else if(status == 2 || status == 3 ){
			flag = flag + validate("manageEmployeeContractSB_endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// 检测模态框输入值(包含Detail信息)是否有效
	function checkPopupValue(){
		var flag = 0;
		flag = flag + validate("manageEmployeeContractSB_sbSolutionId", true, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSB_startDate", true, null, 0, 0);
		// 检测Header信息
		flag = flag + validateVendorService();
		// 检测Detail信息
		flag = flag + validateBase();
		return flag;
	}

	// 验证供应商服务下拉框
	function validateVendorService(){
		cleanError('manageEmployeeContractSB_vendorServiceId');
		if($('.manageEmployeeContractSB_vendorId').val()!=0 && $('.manageEmployeeContractSB_vendorServiceId').val()==0 ){
			addError('manageEmployeeContractSB_vendorServiceId', '请选择');
			return 1;
		}
		return 0;
	}
	
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
	
	// 搜索区域重置
	function resetPopup(){
		$('.manageEmployeeContractSB_vendorId').val('0');
		$('.manageEmployeeContractSB_vendorServiceId').val('0');
		$('.manageEmployeeContractSB_needMedicalCard').val('0');
		$('.manageEmployeeContractSB_medicalNumber').val('');
		$('.manageEmployeeContractSB_needSBCard').val('0');
		$('.manageEmployeeContractSB_sbNumber').val('');
		$('.manageEmployeeContractSB_fundNumber').val('');
		if(!$('#manageEmployeeContractSB_startDate').attr('disabled')){
			$('#manageEmployeeContractSB_startDate').val('');
		}
		if(!$('#manageEmployeeContractSB_endDate').attr('disabled')){
			$('#manageEmployeeContractSB_endDate').val('');
		}
	};
	
	// 显示弹出框
	function showPopup( ){
		$('#employeeContractSBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// 清空错误
    	cleanAllError();
	};

	// 隐藏弹出框
	function hidePopup(){
		$('#employeeContractSBModalId').addClass('hide');
    	$('#shield').hide();
    	link('employeeContractSBAction.do?proc=list_object');
	};
	
	// 清空错误
	function cleanAllError(){
		cleanError('manageEmployeeContractSB_sbSolutionId');
		cleanError('manageEmployeeContractSB_startDate');
		cleanError('manageEmployeeContractSB_endDate');
		cleanError('manageEmployeeContractSB_vendorServiceId');
	};
	
	// 隐藏Button、提示信息
	function hideButton(){
		// 隐藏按钮
		$('#btnPopupSave,#btnPopupSubmit,#btnPopupDelete,#btnPopupReset').addClass('hide');
		// 隐藏选项
		$('#applyToAllHeaderOL,#applyToAllDetailOL,#noticeHeaderOL,#noticeDetailOL').addClass('hide');
	};
	
	// 获取当前SubAction
	function getPopSubAction(){
		return $('.employeeContractSB_form input[id="subAction"]').val();
	};
	
	// 加载社保公积金下拉框
	function loadSolutionHeaderOptions(contractId, sbSolutionId, integrityFlag){
		// 如加载完整下拉框
		if(integrityFlag){
			loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=' + contractId + '&sbSolutionId=' + sbSolutionId, null, showPopup());
		}
		// 只加载未添加的
		else{
			loadHtml('.manageEmployeeContractSB_sbSolutionId', 'employeeContractSBAction.do?proc=list_object_options_manage_ajax&contractId=' + contractId + '&sbSolutionId=' + sbSolutionId, null, showPopup());
		}
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
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>