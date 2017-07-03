<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractCBModalId">
    <div class="modal-header" id="employeeContractCBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractCBModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="cb" key="cb.batch.solution" /></label>
    </div>
    <div class="modal-body">
    	<div class="top">
    		<!-- 已退购可以编辑6 -->
	   		<input  type="button" class="save hide" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />"/>
	   		<kan:auth right="submit" action="HRO_CB_PURCHASE">
	   			<input title="<bean:message bundle="public" key="button.submit" />" type="button" class="function hide" name="btnPopupSubmit" id="btnPopupSubmit" onclick="submitPopup();" value="<bean:message bundle="public" key="button.submit" />"/>
	   		</kan:auth>
	   		<input title="<bean:message bundle="public" key="button.delete" />" type="button" class="delete hide" name="btnPopupDelete" id="btnPopupDelete" onclick="deletePopup();" value="<bean:message bundle="public" key="button.delete" />"/>
	    	<input title="重置输入框信息" type="button" class="reset hide" name="btnPopupReset" id="btnPopupReset" onclick="resetPopup();" value="<bean:message bundle="public" key="button.reset" />"/>
	    </div>
<!-- 		    <span class="highlight">*** 保存：保存记录；申购提交：保存并提交申购；退购提交：保存并提交退购 ***</span> -->
        <html:form action="employeeContractCBAction.do?proc=modify_objects_popup" styleClass="employeeContractCB_form">
        	<%= BaseAction.addToken( request ) %>
<!-- 			<input type="hidden" name="selectedIds" id="selectedIds" value="" /> -->
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<input type="hidden" name="encodedContractId" id="encodedContractId" class="manageEmployeeContractCB_encodedContractId" value="" />
			<fieldset>
				<ol class="auto hide" id="applyToAllHeaderOL">
					<li>
						<label id="solutionIdLabel">是否修改<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的商保信息：</label>
						<input type="checkbox" id="applyToAllHeader" name="applyToAllHeader" value="1" checked="checked"/>
					</li>
				</ol>
				<ol class="hide" id="noticeOL">
					<li>
					    <span class="highlight">注意： <br/>  1、如果勾选则将修改选择<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>的对应商保方案类型的其他数据。<br/>  2、如<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>不存在对应商保方案类型数据则创建该类型商保方案（复选框是否勾选不影响创建）。<br/> 3、如<logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>存在对应商保方案类型数据：①按“保存”只修改数值数据;②按“提交”则根据已选择商保类型状态进行“申购”或“退购”操作。</span>
					</li>
				</ol>
				<ol class="auto hide">
					<li>
						<label>派送信息ID</label><span><html:text property="contractId" styleClass="manageEmployeeContractCB_contractId"></html:text></span>
					</li>
					<li>
						<label>方案ID</label><span><html:text property="employeeCBId" styleClass="manageEmployeeContractCB_employeeCBId"></html:text></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label id="solutionIdLabel"><bean:message bundle="cb" key="cb.batch.solution" /><em> *</em>&nbsp;&nbsp;&nbsp;</label>
						<html:select property="solutionId" styleId="solutionId" styleClass="manageEmployeeContractCB_solutionId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="cb" key="cb.header.start.date" /></label> 
						<input type="hidden" id="tempStartDate" value=""/>
						<input name="startDate" class="startDate  Wdate " id="startDate" <logic:equal name="employeeContractCBForm" property="status" value="2">disabled="disabled"</logic:equal><logic:equal name="employeeContractCBForm" property="status" value="3">disabled="disabled"</logic:equal> onfocus="WdatePicker({minDate:'tempStartDate',maxDate:'#F{ $dp.$D(\'endDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="cb" key="cb.header.end.date" /></label>
						<input type="hidden" id="tempEndDate" value=""/>
						<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'startDate\')}'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="public" key="public.status" /></label> 
						<html:select property="status" disabled="disabled" styleClass="manageEmployeeContractCB_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto"><li><a id="moreInfo-popup"><bean:message bundle="public" key="link.more.info" /></a></li></ol>
				<div id="moreInfo-div-popup" style="border: 1px solid #aaa; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; padding: 10px 10px 0px 10px; display: none;" >
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.cb.solution.header.free.short.of.month" /></label> 
							<html:select property="freeShortOfMonth" styleClass="manageEmployeeContractCB_freeShortOfMonth">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.cb.solution.header.charge.full.month" /></label> 
							<html:select property="chargeFullMonth" styleClass="manageEmployeeContractCB_chargeFullMonth">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</div>
				<ol class="auto" style="display:none">
					<li>
						<label><bean:message bundle="public" key="public.description" /></label> 
						<html:textarea property="description" styleClass="manageEmployeeContractCB_description"></html:textarea>
					</li>
				</ol>
			</fieldset>
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
(function($) {
	// 状态（0:无商保，1:申购提交，2:待申购，3:正常购买，4:退购提交，5:待退购，6:已退购）
	
	// 添加商保方案情况 - 需要显示提交按钮
	if( getSubAction() == 'createObject' ){
		$('#btnPopupSubmit').val("<bean:message bundle="public" key="button.purchase.submit" />");
	}
	// 初始化隐藏Button、Disable Form
	hideButton();
	disableForm('employeeContractCB_form');

	// 保存按钮点击事件
	$('#btnPopupSave').click( function () {
		// 隐藏Btn
// 		hideButton();
		
    	// 清空错误
    	cleanAllError();
    	
		var popupSubAction = $('.employeeContractCB_form input[id="subAction"]').val();
		var status = $('.manageEmployeeContractCB_status').val();
		
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		var contractId = $('.manageEmployeeContractCB_contractId').val();
		
		// 如果是查看
		if(popupSubAction == 'viewObject'){
			enableForm('employeeContractCB_form');
			// 状态不可以修改
			$('.manageEmployeeContractCB_status').attr('disabled', true);
			// “商保方案”不可修改
			$('.manageEmployeeContractCB_solutionId').attr('disabled', true);
			// 修改subAction为修改
			$('.employeeContractCB_form input[id="subAction"]').val('modifyObject');
			// 显示相关Btn
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			
			// 如果是“无商保”状态显示“申购提交”
			if(status == 0){
				$('#btnPopupSubmit').removeClass('hide');
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.purchase.submit" />');
				$('#endDate').attr('disabled', true);
			}
			// “待申购”、“正常购买” 显示“退购提交”
			else if(status == 2 || status == 3){
				$('#btnPopupSubmit').removeClass('hide');
				$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.return.submit" />');
				$('#startDate').attr('disabled', true);
			}
			// “已退购”不能修改日期，隐藏“提交”Button,可以修改数据
			else if(status == 6){
				$('#btnPopupSubmit').addClass('hide');
				$('#startDate').attr('disabled', true);
				$('#endDate').attr('disabled', true);
			}

		}
		// 如果是单条新增
		else if(popupSubAction == 'createObject'){
				
			if(!checkPopupValue()){
		    	enableForm('employeeContractCB_form');
				// 提交模态框
				submitForm('employeeContractCB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
			}
				
		}
		// 如果是单条或批量修改
		else if(popupSubAction == 'modifyObject'){
				
			if(!checkPopupValue()){
		    	enableForm('employeeContractCB_form');
				// 提交模态框
				submitForm('employeeContractCB_form', 'modifyObject', null, null, null, 'search-results', null, hidePopup());
			}
			
		}
	});
	
	 // 加载商保下拉框
	 loadHtml('#solutionId','clientOrderCBAction.do?proc=list_object_options_ajax&contractId=<bean:write name="employeeContractCBForm" property="encodedContractId"/>&solutionId=<bean:write name="employeeContractCBForm" property="solutionId"/>');

	 // 商保方案Change事件
	 $('.manageEmployeeContractCB_solutionId').change(function(){
		 $.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json_manage&contractId=' + $('.manageEmployeeContractCB_contractId').val() + '&solutionId=' + $('.manageEmployeeContractCB_solutionId').val() + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('#startDate').val(data.startDate);
		
				// 加载商保下拉框
				loadSolutionHeaderOptions( $('.manageEmployeeContractCB_encodedContractId').val(), data.solutionId, false);
			}
		});
	 });
	 
	 // 绑定 不全月免费 和 按月计费 事件
	 $('.manageEmployeeContractCB_freeShortOfMonth').change(function(){
		 var chargeFullMonthVal = $('.manageEmployeeContractCB_chargeFullMonth').val();
		 if(this.value==chargeFullMonthVal && this.value!=0 && this.value!=2){
			 this.value=0;
			 alert('\'不全月免费\'  和  \'按月计费\'  不能同时选择  \'是\'');
		 }
	 });
	 
	 $('.manageEmployeeContractCB_chargeFullMonth').change(function(){
		 var freeShortOfMonthVal = $('.manageEmployeeContractCB_freeShortOfMonth').val();
		 if(this.value==freeShortOfMonthVal && this.value!=0 && this.value!=2){
			 this.value=0;
			 alert('\'不全月免费\'  和  \'按月计费\'  不能同时选择  \'是\'');
		 }
	 });
})(jQuery);

	// 弹出模态窗口_添加(单条)
	function addEmployeeContractCB(contractId, encodedContractId){
		// 隐藏Button
		hideButton();
		enableForm('employeeContractCB_form');
		
		// 清空EmployeeCB ID
		$('.manageEmployeeContractCB_encodedContractId').val(encodedContractId);
		$('.manageEmployeeContractCB_employeeCBId').val('');
		
		// 修改Action		
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		// 状态不可以修改
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// 显示“保存”和“重置”button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// 显示“申购提交”button
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.purchase.submit" />');
		// endDate不可编辑
		$('#endDate').attr('disabled', true);
		// subAction为新增
		$('.employeeContractCB_form input[id="subAction"]').val('createObject');
		
		// “加保时间”默认值
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json_manage&contractId=' + contractId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				// 重置ContractId
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('#startDate').val(data.startDate);
		
				// 加载商保下拉框
				loadSolutionHeaderOptions(encodedContractId, null, false);
			}
		});
		
	};
	
	// 弹出模态窗口_操作(多条)
	function addEmployeeContractCBs(){
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		// 移除CheckBox属性
		$('#applyToAllHeader').removeAttr('checked');
		
		// 如果未选择记录
		if(selectedIds == null || selectedIds.trim() == ''){
			alert("请选择<logic:equal name='role' value='1'>雇员</logic:equal><logic:equal name='role' value='2'>员工</logic:equal>记录！");
		}else{
			// 填入Popup SelectedIds值
			$('.employeeContractCB_form input[id="selectedIds"]').val(selectedIds);
			// 隐藏Button
			hideButton();
			enableForm('employeeContractCB_form');
			
			// 修改Button
			$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
			$('#btnPopupSubmit').val('提交');
			// 清空Contract ID 及EmployeeCB ID
			$('.manageEmployeeContractCB_contractId').val('');
			$('.manageEmployeeContractCB_employeeCBId').val('');
			
			// 修改Action		
			$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_objects_popup');
			// 状态不可以修改
			$('.manageEmployeeContractCB_status').attr('disabled', true);
			// 显示“保存”、“提交”和“重置”button
			$('#btnPopupSave').removeClass('hide');
			$('#btnPopupSubmit').removeClass('hide');
			$('#btnPopupReset').removeClass('hide');
			// 显示提示栏和复选框
			$('#applyToAllHeaderOL').removeClass('hide');
			$('#noticeOL').removeClass('hide');
			// subAction为新增
			$('.employeeContractCB_form input[id="subAction"]').val("modifyObject");
			// 加载商保下拉框
			loadSolutionHeaderOptions('', '', true);
		}
	}
	
	// 弹出模态窗口_退购
	function rollbackEmployeeContractCB(employeeCBId){
		// 隐藏Button
		hideButton();
		enableForm('employeeContractCB_form');
		// 状态不可以修改
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// 方案不能修改
		$('.manageEmployeeContractCB_solutionId').attr('disabled', true);
		
		// 修改Action
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json&employeeCBId=' + employeeCBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('.manageEmployeeContractCB_employeeCBId').val(data.employeeCBId);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_description').val(data.description);
				$('.manageEmployeeContractCB_status').val(data.status);
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);

				// 加载商保下拉框
				loadSolutionHeaderOptions(data.encodedContractId, data.solutionId, true);
			}
		});
		
		// 修改Button
		$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
		// 修改subAction为修改
		$('.employeeContractCB_form input[id="subAction"]').val('modifyObject');
		// 显示“保存”和“重置”button
		$('#btnPopupSave').removeClass('hide');
		$('#btnPopupReset').removeClass('hide');
		// 显示“退购提交”
		$('#btnPopupSubmit').removeClass('hide');
		$('#btnPopupSubmit').val('<bean:message bundle="public" key="button.return.submit" />');
		// startDate不可编辑
		$('#startDate').attr('disabled', true);
		// 显示模态框
		showPopup();
	}
	
	// 弹出模态窗口_修改
	function modifyEmployeeContractCB(employeeCBId){
		disableForm('employeeContractCB_form');
		// 隐藏Button
		hideButton();
		// 状态不可以修改
		$('.manageEmployeeContractCB_status').attr('disabled', true);
		// 修改SubAction
		$('.employeeContractCB_form input[id="subAction"]').val('viewObject');
		// 修改Action
		$('.employeeContractCB_form').attr('action', 'employeeContractCBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractCBAction.do?proc=get_object_json&employeeCBId=' + employeeCBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractCB_contractId').val(data.contractId);
				$('.manageEmployeeContractCB_employeeCBId').val(data.employeeCBId);
				$('.manageEmployeeContractCB_solutionId').val(data.solutionId);
				$('.manageEmployeeContractCB_freeShortOfMonth').val(data.freeShortOfMonth);
				$('.manageEmployeeContractCB_chargeFullMonth').val(data.chargeFullMonth);
				$('.manageEmployeeContractCB_description').val(data.description);
				$('.manageEmployeeContractCB_status').val(data.status);
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);
				
				// “申购提交”、“退购提交”、“待退购” 只能看，不能修改
				if(data.status == 1 || data.status == 4 || data.status == 5){
					hideButton();
				}else{
					$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
					$('#btnPopupSave').removeClass('hide');
				}

				// 加载商保下拉框
				loadSolutionHeaderOptions(data.encodedContractId, data.solutionId, true);
			}
		});

		// 显示模态框
		showPopup();
	}
	
	// 删除商保方案
	function deleteEmployeeContractCB(employeeCBId){
		if(confirm("确定删除该条记录？")){
			// 注入SelectedIds值
			$('.employeeContractCB_form input[id="selectedIds"]').val(employeeCBId);
			// 提交
			submitForm('employeeContractCB_form', 'deleteObjects', null, null, null, 'search-results', "employeeContractCBAction.do?proc=list_object", hidePopup());
		}
	}
	
	// “提交”按钮
	function submitPopup(){
		
		// 需同时验证必填项及日期
		if(!checkPopupValue() && !checkDateValue()){
			// 提交模态框
			submitForm('employeeContractCB_form', 'submitObject', null, null, null, 'search-results', null, hidePopup());
		}
		
	}

	// 检查模态框输入值是否有效
	function checkPopupValue(){
		
		var flag = 0;
		flag = flag + validate("manageEmployeeContractCB_solutionId", true, "select", 0, 0);
		return flag;
	}
	
	// 检查日期是否有效
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractCB_status').val();
		var contractId = $('.manageEmployeeContractCB_contractId').val();
		var flag = 0;
		
		// 如果是批量设置需验证“加保日期”、“退保日期”
		if(!contractId){
			flag = flag + validate("startDate", true, null, 0, 0);
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		// 如果是“无商保”(申购提交) - 加保日期不能为空
		else if(status == 0){
			flag = flag + validate("startDate", true, null, 0, 0);
		}
		// 如果是“待申购”、“正常购买”(退购提交) - 退保日期不能为空
		else if(status == 2 || status == 3 ){
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// 搜索区域重置
	function resetPopup(){
		$('.manageEmployeeContractCB_solutionId').val('0');
		$('.manageEmployeeContractCB_freeShortOfMonth').val('0');
		$('.manageEmployeeContractCB_chargeFullMonth').val('0');
		if(!$('#startDate').attr('disabled')){
			$('#startDate').val('');
		}
		if(!$('#endDate').attr('disabled')){
			$('#endDate').val('');
		}
	}
	
	// 隐藏弹出框
	function hidePopup(){
		$('#employeeContractCBModalId').addClass('hide');
    	$('#shield').hide();
	}
	
	// 显示弹出框
	function showPopup(){
		$('#employeeContractCBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// 清空错误
    	cleanAllError();
	}
	
	// 清空错误
	function cleanAllError(){
		cleanError('manageEmployeeContractCB_solutionId');
		cleanError('startDate');
		cleanError('endDate');
	}
	
	// 隐藏Button、提示信息
	function hideButton(){
		$('#btnPopupSave').addClass('hide');
		$('#btnPopupSubmit').addClass('hide');
		$('#btnPopupDelete').addClass('hide');
		$('#btnPopupReset').addClass('hide');
		
		$('#applyToAllHeaderOL').addClass('hide');
		$('#noticeOL').addClass('hide');
	}
	
	// 获取当前SubAction
	function getSubAction(){
		return $('.employeeContractCB_form input#subAction').val();
	}

	// 加载商保下拉框
	function loadSolutionHeaderOptions(contractId, solutionId, integrityFlag){
		// 如加载完整下拉框
		if(integrityFlag){
			loadHtml('.manageEmployeeContractCB_solutionId', 'clientOrderCBAction.do?proc=list_object_options_ajax&contractId=' + contractId + '&solutionId=' + solutionId, null, showPopup());
		}
		// 只加载未添加的
		else{
			loadHtml('.manageEmployeeContractCB_solutionId', 'employeeContractCBAction.do?proc=list_object_options_manage_ajax&contractId=' + contractId + '&solutionId=' + solutionId, null, showPopup());
		}
	}
	
	// “更多信息”点击事件
	$('#moreInfo-popup').click( function () { 
			if($('#moreInfo-div-popup').is(':visible')) { 
				$('#moreInfo-div-popup').hide();
			} else { 
				$('#moreInfo-div-popup').show();
			}
	});
	
	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>