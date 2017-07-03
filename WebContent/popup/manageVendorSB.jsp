<%@ page pageEncoding="GBK"%>
<%@	page import="com.kan.base.web.action.BaseAction"%>
<%@	page import="com.kan.base.util.CachedUtil"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="modal midsize content hide" id="employeeContractSBModalId">
    <div class="modal-header" id="employeeContractSBHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractSBModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="sb" key="sb.vendor.setting" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.edit" />"/>
	    </div>
        <html:form action="employeeContractSBAction.do?proc=modify_object_popup" styleClass="employeeContractSB_form">
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
						<label><bean:message bundle="sb" key="sb.vendor.service" /></label> 
						<html:select property="vendorServiceId" styleClass="manageEmployeeContractSB_vendorServiceId">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="sb" key="sb.start.date" /></label> 
						<input name="startDate" class="startDate  Wdate " id="startDate" onfocus="WdatePicker({minDate:'2013-11-27',maxDate:'#F{ $dp.$D(\'endDate\') || $dp.$DV(\'2014-11-28\') }'})" type="text" maxlength="10">
					</li>
					<li>
						<label><bean:message bundle="sb" key="sb.end.date" /></label> 
						<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker({maxDate:'2014-11-28',minDate:'#F{ $dp.$D(\'startDate\') || $dp.$DV(\'2013-11-27\') }' })" type="text" maxlength="10">
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
					<li style="display:none">
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
			<div id="special_info" class="hide">
				<!-- 社保公积金Detail信息 -->
				<jsp:include page="/popup/extend/listVendorSBDetail.jsp"></jsp:include>
			</div>			
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {

		// 加载社保公积金下拉框
		loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=<bean:write name="employeeContractSBForm" property="encodedContractId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>');
		// 加载供应商下拉框
		loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>');
		// 加载供应商服务下拉框
		loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>&serviceId=<bean:write name="employeeContractSBForm" property="vendorServiceId"/>');
		// 加载社保公积金明细Tab页
		loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=<bean:write name="employeeContractSBForm" property="sbSolutionId"/>', true, null);
		
		// 社保公积金方案Change事件
		$(".manageEmployeeContractSB_sbSolutionId").change(function(){
			if($('.employeeContractSB_form input#subAction').val() != 'viewObject') {
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&contractId=<bean:write name="employeeContractSBForm" property="encodedId"/>&employeeSBId=<bean:write name="employeeContractSBForm" property="employeeSBId"/>&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val(), false, null);
			}
			
			// 加载供应商下拉框
			 loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + $(".manageEmployeeContractSB_sbSolutionId").val() + '&vendorId=<bean:write name="employeeContractSBForm" property="vendorId"/>', null, null);
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
		
		// 初始化Disable Form
		disableForm('employeeContractSB_form');

		// 保存按钮点击事件
		$('#btnPopupSave').click( function () {
	    	// 清空错误
	    	cleanAllError();
	    	
			var popupSubAction = $('.employeeContractSB_form input[id="subAction"]').val();
			
			// 如果是查看
			if(popupSubAction == 'viewObject'){
				disableForm('employeeContractSB_form');
				// 供应商、供应商服务 可编辑
				$('.manageEmployeeContractSB_vendorId').removeAttr('disabled');
				$('.manageEmployeeContractSB_vendorServiceId').removeAttr('disabled');
				// 修改subAction为修改
				$('.employeeContractSB_form input[id="subAction"]').val('submitObject');
				// 显示相关Btn
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.save" />');
				
			}
			// 如果是单条或批量修改
			else if(popupSubAction == 'submitObject'){
					
				if(!checkPopupValue()){
			    	enableForm('employeeContractSB_form');
					// 提交模态框
					submitForm('employeeContractSB_form', 'submitObject', null, null, null, 'search-results', 'employeeContractSBAction.do?proc=modify_object_popup&pageFlag=vendor', hidePopup());
				}
				
			}
		});
	})(jQuery);
	
	// 弹出模态窗口_修改
	function modifyEmployeeContractSB(employeeSBId){
		disableForm('employeeContractSB_form');
		// 修改SubAction
		$('.employeeContractSB_form input[id="subAction"]').val('viewObject');
		// 修改Action
		$('.employeeContractSB_form').attr('action', 'employeeContractSBAction.do?proc=modify_object_popup');
		
		$.ajax({
			url:'employeeContractSBAction.do?proc=get_object_json&employeeSBId=' + employeeSBId + '&date=' + new Date(),
			dataType : 'json',
			success: function(data){
				$('.manageEmployeeContractSB_contractId').val(data.contractId);
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
				$('#startDate').val(data.startDate);
				$('#endDate').val(data.endDate);
				
				$('#btnPopupSave').val('<bean:message bundle="public" key="button.edit" />');
				// 加载社保公积金下拉框
				loadHtml('.manageEmployeeContractSB_sbSolutionId', 'clientOrderSBAction.do?proc=list_object_options_ajax&contractId=' + data.encodedContractId + '&sbSolutionId=' + data.sbSolutionId);
				// 加载供应商下拉框
				loadHtml('.manageEmployeeContractSB_vendorId', 'vendorAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId);
				// 加载供应商服务下拉框
				loadHtml('.manageEmployeeContractSB_vendorServiceId', 'vendorServiceAction.do?proc=list_object_options_ajax&sbSolutionId=' + data.sbSolutionId + '&vendorId=' + data.vendorId + '&serviceId=' + data.vendorServiceId);
				// 加载社保公积金明细Tab页
				loadHtml('#special_info', 'employeeContractSBAction.do?proc=list_special_info_html&employeeSBId=' + data.employeeSBId + '&sbSolutionId=' + data.sbSolutionId, true, null);
			}
		});

		// 显示模态框
		showPopup();
	}

	// 检查日期是否有效
	function checkDateValue(){
		
		var status = $('.manageEmployeeContractSB_status').val();
		var contractId = $('.manageEmployeeContractSB_contractId').val();
		var flag = 0;
		
		// 如果是批量设置需验证“加保日期”、“退保日期”
		if(!contractId){
			flag = flag + validate("startDate", true, null, 0, 0);
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		// 如果是“无社保公积金”(加保提交) - 加保日期不能为空
		else if(status == 0){
			flag = flag + validate("startDate", true, null, 0, 0);
		}
		// 如果是“待加保”、“正常购买”(退保提交) - 退保日期不能为空
		else if(status == 2 || status == 3 ){
			flag = flag + validate("endDate", true, null, 0, 0);
		}
		
		return flag;
	}

	// 检测模态框输入值(包含Detail信息)是否有效
	function checkPopupValue(){
		var flag = 0;
		flag = flag + validate("manageEmployeeContractSB_sbSolutionId", true, "select", 0, 0);
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

	// 显示弹出框
	function showPopup(){
		$('#employeeContractSBModalId').removeClass('hide');
    	$('#shield').show();
    	
    	// 清空错误
    	cleanAllError();
	}

	// 隐藏弹出框
	function hidePopup(){
		$('#employeeContractSBModalId').addClass('hide');
    	$('#shield').hide();
	}
	
	// 清空错误
	function cleanAllError(){
		cleanError('manageEmployeeContractSB_sbSolutionId');
		cleanError('startDate');
		cleanError('endDate');
		cleanError('manageEmployeeContractSB_vendorServiceId');
	}

	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	hidePopup();
	    } 
	});
</script>