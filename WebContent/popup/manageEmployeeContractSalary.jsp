<%@ page pageEncoding="GBK" %>
<%@	page import="com.kan.base.web.action.BaseAction" %>
<%@ page import="com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO" %>
<%@ page import="com.kan.base.util.KANUtil" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!-- Module Box HTML: Begins -->
<div class="popup modal midsize content hide" id="employeeContractSalaryModalId">
    <div class="modal-header" id="employeeContractSalaryHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractSalaryModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="payment" key="payment.employee.salary" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
	   		<input type="button" class="save" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.save" />"/>
	    	<input type="button" class="reset" name="btnCancel" id="btnCancel" onclick="resetPopup()" value="<bean:message bundle="public" key="button.reset" />" />
	    </div>
        <html:form action="employeeContractSalaryAction.do?proc=add_object_popup" styleClass="employeeContractSalary_form">
        	<%= BaseAction.addToken( request ) %>
			<input type="hidden" name="selectedIds" id="selectedIds" value="" />
			<input type="hidden" name="subAction" id="subAction" value="" />
			<fieldset>
				<ol class="auto">
					<li style="display:none">
						<label>派送协议ID</label><span><html:text property="contractId" styleClass="manageEmployeeContractSalary_contractId"></html:text></span>
					</li>
					<li style="display:none">
						<label>薪酬方案ID</label><span><html:text property="employeeSalaryId" styleClass="manageEmployeeContractSalary_employeeSalaryId"></html:text></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.item" /><em> *</em></label>
						<html:select property="itemId" styleClass="manageEmployeeContractSalary_itemId">
							<html:optionsCollection property="items" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.base" /></label>
						<html:text property="base" maxlength="15" styleClass="manageEmployeeContractSalary_base"></html:text>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.cycle" /><em> *</em></label> 
						<html:select property="cycle" styleClass="manageEmployeeContractSalary_cycle">
							<html:optionsCollection property="cycles" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.start.date" /></label>
						<input type="hidden" id="tempStartDate" value="<bean:write name="employeeContractSalaryForm" property="startDate"/>"/>
						<input name="startDate" class="startDate  Wdate " id="startDate" onfocus="WdatePicker({minDate:'#F{ $dp.$D(\'tempStartDate\')}',maxDate:'#F{ $dp.$D(\'endDate\')}'})" type="text" maxlength="10" value="<logic:present name="employeeContractSalaryForm" property="startDate"><bean:write  name="employeeContractSalaryForm" property="startDate"/></logic:present>">
					</li>
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.end.date" /></label> 
						<input type="hidden" id="tempEndDate" value="<bean:write name="employeeContractSalaryForm" property="endDate"/>"/>
						<input name="endDate" class="endDate  Wdate " id="endDate" onfocus="WdatePicker({maxDate:'#F{ $dp.$D(\'tempEndDate\')}',minDate:'#F{ $dp.$D(\'startDate\')}' })" type="text" maxlength="10" value="<logic:present name="employeeContractSalaryForm" property="endDate"><bean:write  name="employeeContractSalaryForm" property="endDate"/></logic:present>">
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.formular" /></label> 
						<html:textarea property="formular" styleClass="manageEmployeeContractSalary_formular"></html:textarea>
					</li>
					<li>
						<label><bean:message bundle="payment" key="payment.employee.salary.show.to.ts" /></label> 
						<html:select property="showToTS" styleClass="manageEmployeeContractSalary_showToTS">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto" style="display:none">
					<li>
						<label><bean:message bundle="public" key="public.description" /></label> 
						<html:textarea property="description" styleClass="manageEmployeeContractSalary_description"></html:textarea>
					</li>
					<li>
						<label><bean:message bundle="public" key="public.status" /></label> 
						<html:select property="status" styleClass="manageEmployeeContractSalary_status">
							<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto"><li><a id="moreInfo-popup"><bean:message bundle="public" key="link.more.info" /></a></li></ol>
				<div id="moreInfo-div-popup" style="border: 1px solid #aaa; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; padding: 10px 10px 0px 10px; display: none;" >
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.employee.salary.type" /><em> *</em></label> 
							<html:select property="salaryType" styleClass="manageEmployeeContractSalary_salaryType">
								<html:optionsCollection property="salaryTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li id="divideTypeLI" style="display: none">
							<label><bean:message bundle="payment" key="payment.employee.salary.divide.type" /></label> 
							<html:select property="divideType" styleClass="manageEmployeeContractSalary_divideType">
								<html:optionsCollection property="divideTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li id="excludeDivideItemIdsLI" style="width: 50%;">
							<label><bean:message bundle="payment" key="payment.employee.salary.paid.item" /></label>
							<div style="width: 215px;"></div>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.employee.salary.base.from" /></label> 
							<html:select property="baseFrom" styleClass="manageEmployeeContractSalary_baseFrom">
								<html:optionsCollection property="itemGroups" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<ol class="auto">
						<li id="percentageLI" style="display: none">
							<label><bean:message bundle="payment" key="payment.employee.salary.percentage" />  <img title="<bean:message bundle="payment" key="payment.employee.salary.percentage.tips" />" src="images/tips.png"></label>
							<html:text property="percentage" maxlength="15" styleClass="manageEmployeeContractSalary_percentage"></html:text>
						</li>
						<li id="fixLI" style="display: none">
							<label><bean:message bundle="payment" key="payment.employee.salary.fix" /></label>
							<html:text property="fix" maxlength="15" styleClass="manageEmployeeContractSalary_fix"></html:text>
						</li>
					</ol>
					<ol class="auto">
						<li>
							<label><bean:message bundle="payment" key="payment.employee.salary.result.cap" /></label>
							<html:text property="resultCap" styleClass="manageEmployeeContractSalary_resultCap"></html:text>
						</li>
						<li>
							<label><bean:message bundle="payment" key="payment.employee.salary.result.floor" /></label>
							<html:text property="resultFloor" styleClass="manageEmployeeContractSalary_resultFloor"></html:text>
						</li>
					</ol>
				</div>	
			</fieldset>
		</html:form >
    </div>
     
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
(function($) {
	// 保存按钮点击事件
	$('#btnPopupSave').click( function () {
		var popupSubAction = $('.employeeContractSalary_form input[id="subAction"]').val();
		
		var selectedIds =  $('.list_form input[id="selectedIds"]').val();
		var contractId = $('.manageEmployeeContractSalary_contractId').val();
		
		// 如果是修改
		if(popupSubAction == 'modifyObject'){
			var detailAction = $('.employeeContractSalary_form').attr('action');
			
			$('.employeeContractSalary_form').attr('action', 'employeeContractSalaryAction.do?proc=modify_object_popup');
			// 提交模态框
			submitForm('employeeContractSalary_form', null, null, null, null, 'search-results', null, hidePopup());
		}else{
			// 如果是单行修改
			if(contractId){
				
				if(!checkPopupValue()){
			    	enableForm('employeeContractSalary_form');
					// 提交模态框
					submitForm('employeeContractSalary_form', null, null, null, null, 'search-results', null, hidePopup());
				}
				
			}
			// 如果是批量修改
			else if(selectedIds == null || selectedIds.trim() == ''){
				alert("请选择<logic:equal name='role' value='1'>派送信息</logic:equal><logic:equal name='role' value='2'>劳动合同</logic:equal>记录！");
			}else{
				
				if(!checkPopupValue()){
					// 填入Popup SelectedIds值
					$('.employeeContractSalary_form input[id="selectedIds"]').val(selectedIds);
			    	enableForm('employeeContractSalary_form');
					// 提交模态框
					submitForm('employeeContractSalary_form', null, null, null, null, 'search-results', null, hidePopup());
				}
				
			}
		}
	});
	
	// “更多信息”点击事件
	$('#moreInfo-popup').click( function () { 
			if($('#moreInfo-div-popup').is(':visible')) { 
				$('#moreInfo-div-popup').hide();
			} else { 
				$('#moreInfo-div-popup').show();
			}
	});
	
	// “金额来源”Change事件
	$('.manageEmployeeContractSalary_baseFrom').change( function () {
		if($(this).val() != 0){
			$('#percentageLI').show();
			$('#fixLI').show();
		}
		else{
			$('#percentageLI').hide();
			$('#fixLI').hide();
		}
	});
	
	$('.manageEmployeeContractSalary_baseFrom').change();
	 
	// “计薪方式”Change事件
 	$('.manageEmployeeContractSalary_salaryType').change( function () {
	 	if($(this).val() == 1){
		 	$("#divideTypeLI").show();
		 	$("#excludeDivideItemIdsLI").show();
	 	}
	 	else
	 	{
		 	$("#divideTypeLI").hide();
		 	$("#excludeDivideItemIdsLI").hide();
	 	}
 	});
 	 
	// “计薪方式”添加Change事件
 	$('.manageEmployeeContractSalary_divideType').change(function(){
	 	if(($(this).val() == 2 || $(this).val() == 3 )&& $('.manageEmployeeContractSalary_salaryType').val()==1){
		 	$("#excludeDivideItemIdsLI").show();
	 	}else{
		 	$("#excludeDivideItemIdsLI").hide();
	 	}
 	});
	
	$('#salaryType').change();
	$('#divideType').change();
	
	$('.manageEmployeeContractSalary_salaryType').change();

	// 不折算科目定义选项
	<%
		final EmployeeContractSalaryVO employeeContractSalaryVO = (EmployeeContractSalaryVO)request.getAttribute("employeeContractSalaryForm");
	%>
	// 移除请选择
	$("#excludeDivideItemIdsLI").find("span").remove();
	
	$("#excludeDivideItemIdsLI").find("label").after("<div style='width: 215px;'><%=KANUtil.getCheckBoxHTML(employeeContractSalaryVO.getExcludeDivideItems(),"excludeDivideItemIds",employeeContractSalaryVO.getExcludeDivideItemIds(),((EmployeeContractSalaryVO)request.getAttribute("employeeContractSalaryForm")).getSubAction() ) %></div>");
})(jQuery);

	// 检测模态框输入值是否有效
	function checkPopupValue(){
		var flag = 0;
		flag = flag + validate("manageEmployeeContractSalary_itemId", true, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_salaryType", true, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_cycle", true, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_showToTS", null, "select", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_base", false, "currency", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_percentage", false, "currency", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_fix", false, "currency", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_resultCap", false, "currency", 0, 0);
		flag = flag + validate("manageEmployeeContractSalary_resultFloor", false, "currency", 0, 0);
		return flag;
	};

	// 搜索区域重置
	function resetPopup(){
		$('.manageEmployeeContractSalary_employeeSalaryId').val('');
		$('.manageEmployeeContractSalary_salaryType').val('0');
		$('.manageEmployeeContractSalary_base').val('');
		$('.manageEmployeeContractSalary_salaryType').val('0');
		$('.manageEmployeeContractSalary_percentage').val('0');
		$('.manageEmployeeContractSalary_fix').val('0');
		$('.manageEmployeeContractSalary_resultCap').val('0');
		$('.manageEmployeeContractSalary_resultFloor').val('0');
		$('.manageEmployeeContractSalary_cycle').val('0');
		$('.startDate').val('');
		$('.endDate').val('');
		$('.manageEmployeeContractSalary_formular').val('');
		$('.manageEmployeeContractSalary_showToTS').val('0');
	};
</script>