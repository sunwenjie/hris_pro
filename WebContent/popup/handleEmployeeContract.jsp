<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<!-- Module Box HTML: Begins -->
<div class="popup modal large content hide" id="employeeContractModalId">
    <div class="modal-header" id="employeeContractHeader" style="cursor:move;">
        <a class="close" data-dismiss="modal" onclick="$('#employeeContractModalId').addClass('hide');$('#shield').hide();">×</a>
        <label><bean:message bundle="business" key="business.employee.resign.handle" /></label>
    </div>
    
    <div class="modal-body">
    	<div class="top">
    		<kan:auth action="<%=EmployeeContractAction.getAccessAction(request,response)%>" right="dimission">
	   			<input type="button" class="function" name="btnPopupSave" id="btnPopupSave" value="<bean:message bundle="public" key="button.dimission.submit" />" onclick="handleEmployeeContract();"/>
	   		</kan:auth>
	    </div>
	    <html:form action="employeeContractAction.do?proc=modify_object_popup" styleClass="handleEmployeeContract_form">
	    	<%= BaseAction.addToken( request ) %>
	    	<input type="hidden" name="contractId" value="<bean:write name="employeeContractForm" property="contractId"/>"/>
        	<div class="box toggableForm">
        		<ol class="auto">
					<li class="required"><label><em>* </em><bean:message bundle="public" key="required.field" /></label></li>  
				</ol>
	        	<ol class="auto">
		        	<li>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>：
						<span><bean:write name="employeeContractForm" property="contractId"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>：
						<span><bean:write name="employeeContractForm" property="employeeNameZH"/></span>
					</li>
					<li>
						<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
						<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>：
						<span><bean:write name="employeeContractForm" property="employeeNameEN"/></span>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label><bean:message bundle="business" key="business.employee.calculation.salary" /></label>
						<html:select property="payment" styleClass="payment small" styleId="payment" style="width:112px;">
							<html:option value="1"><bean:message bundle="business" key="business.employee.calculation.salary.option1" /></html:option>
							<html:option value="2"><bean:message bundle="business" key="business.employee.calculation.salary.option2" /></html:option>
						</html:select>
					</li>
					<li id="isRemoveContractLI">
						<label><bean:message bundle="business" key="business.employee.also.remove.contract" /></label>
						<input type="checkbox" id="delete" name="delete" value="2"  />
					</li>
				</ol>
				<ol class="auto">
					<li style="width: 50%;">
						<label><bean:message bundle="business" key="business.employee.rehire" /></label>
						<html:select property="hireAgain" styleClass="hireAgain" style="width:112px;">
							<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
						</html:select>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label style="width:100px;"><bean:message bundle="business" key="business.employee.resign.reasons" /></label>
						<select id="leaveType" style="width:80px; float: left; margin-right: 2px; ">
							<option value="1"><bean:message bundle="business" key="business.employee.resign.reasons.radio1" /></option>
							<option value="2"><bean:message bundle="business" key="business.employee.resign.reasons.radio2" /></option>
						</select>
						<select id="leaveReasons_select" style="width:112px;float: left;">
							<logic:iterate id="employeeLeaveReason" name="employeeContractForm" property="employeeLeaveReasons">
								<option value="<bean:write name="employeeLeaveReason" property="mappingValue" />">
									<bean:write name="employeeLeaveReason" property="mappingValue" />
								</option>
							</logic:iterate>
						</select>
						<html:textarea property="leaveReasons" styleId="leaveReasons_input" styleClass="leaveReasons_input hide" style="width:144px;"/>
					</li>
				</ol>
				<ol class="auto">
					<li>
						<label style="width:100px;">HR Comments</label>
						<html:textarea property="remark5" styleId="remark5" styleClass="remark5" style="width:144px;"/>
					</li>
				</ol>
				
				<logic:notEmpty name="employeeContractForm">
					<ol class="auto">
						<li style="width: 25% !important;">
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.start.date" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.start.date" /></logic:equal>
							</label>
							<br/><br/><input name="startDate" style="width: 150px !important;"  class="startDate Wdate " disabled="disabled" id="startDate_contract" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate_contract\')}'})" type="text" maxlength="10" value="<bean:write name="employeeContractForm" property="startDate"/>">
						</li>
						<li style="width: 25% !important;">
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.end.date" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.end.date" /></logic:equal>
							</label>
							<br/><br/><input name="endDate" style="width: 150px !important;"  class="endDate Wdate " disabled="disabled" id="endDate_contract" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate_contract\')}'})" type="text" maxlength="10" value="<bean:write name="employeeContractForm" property="endDate"/>">
						</li>
						<li style="width: 25% !important;">
							<label><bean:message bundle="business" key="business.employee.resign.date" /><em> *</em></label>
							<br/><br/><input name="resignDate" style="width: 150px !important;" class="endDate Wdate resignDate" id="resignDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate_contract\')<logic:present name='defineMinEndDate'><logic:notEmpty name='defineMinEndDate'>||\'<bean:write name="defineMinEndDate"/>\'</logic:notEmpty></logic:present>}',maxDate:'<bean:write name="employeeContractForm" property="endDate"/>'})" type="text" maxlength="10" value="<bean:write name="employeeContractForm" property="resignDate"/>">
						</li>
						<li style="width: 25% !important;">
							<label><bean:message bundle="business" key="business.employee.last.work.date" /></label>
							<br/><br/><input name="lastWorkDate" style="width: 150px !important;" class="lastWorkDate Wdate " id="lastWorkDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate_contract\')<logic:present name='defineMinEndDate'><logic:notEmpty name='defineMinEndDate'>||\'<bean:write name="defineMinEndDate"/>\'</logic:notEmpty></logic:present>}',maxDate:'<bean:write name="employeeContractForm" property="endDate"/>'})" type="text" maxlength="10" value="<bean:write name="employeeContractForm" property="lastWorkDate"/>">
						</li>
						<li class="hide" style="width: 25% !important;" id="handleEmployeeContractEmployStatusLi" >
							<label><bean:message bundle="business" key="business.employee.employment.status" /></label><br/><br/>
							<html:select property="employStatus" style="width: 150px !important;" styleClass="handleEmployeeContract_employStatus" disabled="disabled">
								<html:optionsCollection property="employStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</logic:notEmpty>
			</div>
			
			<logic:notEmpty name="employeeContractSBVOs">
			<logic:notEmpty name="employeeContractCBVOs">
			<div id="tab"> 
				<div class="tabMenu"> 
					<logic:notEmpty name="employeeContractSBVOs">
						<li id="tabMenu1" onClick="changeTab(1,2)" class="first hover"><bean:message bundle="public" key="menu.table.title.sb" /></li> 
					</logic:notEmpty>
					<logic:notEmpty name="employeeContractCBVOs">
						<li id="tabMenu2" onClick="changeTab(2,2)" class=""><bean:message bundle="public" key="menu.table.title.cb" /></li> 
					</logic:notEmpty>
				</div>
				
				<div class="tabContent"> 
					<logic:notEmpty name="employeeContractSBVOs">
						<div id="tabContent1" class="kantab">
							<table class="table hover" id="resultTable">
								<thead>
									<tr>
										<th style="width: 30%" class="header-nosort">
											<bean:message bundle="sb" key="sb.solution.name" />
										</th>
										<th style="width: 35%" class="header-nosort">
											<bean:message bundle="sb" key="sb.start.date" />
										</th>
										<th style="width: 35%" class="header-nosort">
											<bean:message bundle="sb" key="sb.end.date" />
										</th>
									</tr>
								</thead>
								<tbody>
									<logic:iterate id="employeeContractSBVO" name="employeeContractSBVOs" indexId="number">
										<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
											<td class="left"><input type="hidden" name="solutionId_sb" value="<bean:write name="employeeContractSBVO" property="employeeSBId"/>"/><bean:write name="employeeContractSBVO" property="sbName"/></td>
											<td class="left"><input style="width: 220px !important" name="startDate_sb" disabled="disabled" class="startDate  Wdate " id="startDate_sb_<bean:write name="employeeContractSBVO" property="employeeSBId"/>" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" type="text" maxlength="10" value="<bean:write name="employeeContractSBVO" property="startDate"/>"></td>
											<td class="left"><input style="width: 220px !important" name="endDate_sb" class="endDate Wdate " id="endDate_sb_<bean:write name="employeeContractSBVO" property="employeeSBId"/>" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate_sb_<bean:write name="employeeContractSBVO" property="employeeSBId"/>\')<logic:present name='defineMinEndDate'><logic:notEmpty name='defineMinEndDate'>||\'<bean:write name="defineMinEndDate"/>\'</logic:notEmpty></logic:present>}',maxDate:'<logic:present name='defineMaxEndDate'><logic:notEmpty name='defineMaxEndDate'><bean:write name="defineMaxEndDate"/></logic:notEmpty></logic:present>'})" type="text" maxlength="10" value="<bean:write name="employeeContractSBVO" property="endDate"/>"></td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
						</div>
					</logic:notEmpty>
					<logic:notEmpty name="employeeContractCBVOs">
						<div id="tabContent2" class="kantab" style="display:none" >
							<table class="table hover" id="resultTable">
								<thead>
									<tr>
										<th style="width: 30%" class="header-nosort">
											<bean:message bundle="cb" key="cb.header.solution.name" />
										</th>
										<th style="width: 35%" class="header-nosort">
											<bean:message bundle="cb" key="cb.header.start.date" />
										</th>
										<th style="width: 35%" class="header-nosort">
											<bean:message bundle="cb" key="cb.header.end.date" />
										</th>
									</tr>
								</thead>
								<tbody>
									<logic:iterate id="employeeContractCBVO" name="employeeContractCBVOs" indexId="number">
										<tr class='<%=number % 2 == 1 ? "odd" : "even"%>'>
											<td class="left"><input type="hidden" name="solutionId_cb" value="<bean:write name="employeeContractCBVO" property="employeeCBId"/>"/><bean:write name="employeeContractCBVO" property="cbName"/></td>
											<td class="left"><input style="width: 220px !important" name="startDate_cb" disabled="disabled" class="startDate Wdate " id="startDate_cb_<bean:write name="employeeContractCBVO" property="employeeCBId"/>" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" type="text" maxlength="10" value="<bean:write name="employeeContractCBVO" property="startDate"/>"></td>
											<td class="left"><input style="width: 220px !important" name="endDate_cb" class="endDate Wdate " id="endDate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate_cb_<bean:write name="employeeContractCBVO" property="employeeCBId"/>\')<logic:present name='defineMinEndDate'><logic:notEmpty name='defineMinEndDate'>||\'<bean:write name="defineMinEndDate"/>\'</logic:notEmpty></logic:present>}',maxDate:'<logic:present name='defineMaxEndDate'><logic:notEmpty name='defineMaxEndDate'><bean:write name="defineMaxEndDate"/></logic:notEmpty></logic:present>'})" type="text" maxlength="10" value="<bean:write name="employeeContractCBVO" property="endDate"/>"></td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
						</div>
					</logic:notEmpty>
				</div>
			</div>
			</logic:notEmpty>
			</logic:notEmpty>
		</html:form>
    </div>
</div>
<!-- Module Box HTML: Ends -->

<script type="text/javascript">
	(function($) {
		// 离职时间失去焦点事件
		$('#resignDate').blur(function(){
			if( $(this).val() ){
				$('#handleEmployeeContractEmployStatusLi').removeClass('hide');
				$('.handleEmployeeContract_employStatus option[value="1"]').attr('disabled', 'disabled');

				if( !$('.handleEmployeeContract_employStatus').val() || $('.handleEmployeeContract_employStatus').val() == 1 ){
					$('.handleEmployeeContract_employStatus').val($(".handleEmployeeContract_employStatus option:last-child").val());
				}
			}else{
				$('#handleEmployeeContractEmployStatusLi').addClass('hide');
				$('.handleEmployeeContract_employStatus option[value="1"]').removeAttr('disabled');
				$('.handleEmployeeContract_employStatus').val('<bean:write name="employeeContractForm" property="employStatus"/>');
			}
			$('#lastWorkDate').val($('#resignDate').val()); 
		});
		
		$('#resignDate').focus(function(){
			WdatePicker({
				// WdatePicker插件内部事件，确认时
				onpicking :function(dp){
					if( dp.cal.getNewDateStr() !='' ){
						$('#handleEmployeeContractEmployStatusLi').removeClass('hide');
						$('.handleEmployeeContract_employStatus option[value="1"]').attr('disabled', 'disabled');
						
						if( !$('.handleEmployeeContract_employStatus').val() || $('.handleEmployeeContract_employStatus').val() == 1 ){
							$('.handleEmployeeContract_employStatus').val($(".handleEmployeeContract_employStatus option:last-child").val());
						}
					}
				}
			});
			if( $(this).val() ){
				$('#handleEmployeeContractEmployStatusLi').removeClass('hide');
				$('.handleEmployeeContract_employStatus option[value="1"]').attr('disabled', 'disabled');

				if( !$('.handleEmployeeContract_employStatus').val() || $('.handleEmployeeContract_employStatus').val() == 1 ){
					$('.handleEmployeeContract_employStatus').val($(".handleEmployeeContract_employStatus option:last-child").val());
				}
			}else{
				$('#handleEmployeeContractEmployStatusLi').addClass('hide');
				$('.handleEmployeeContract_employStatus option[value="1"]').removeAttr('disabled');
				$('.handleEmployeeContract_employStatus').val('<bean:write name="employeeContractForm" property="employStatus"/>');
			}
		});
		
		// 计算工资Change事件
		$("#payment").change(function(){
			if( $(this).val() ==2){
				 $("#delete").attr("checked", false);  
				 $("#isRemoveContractLI").show();
			}else{
				 $("#delete").attr("checked", false);  
				 $("#isRemoveContractLI").hide();
			}
		});
		
		// 离职原因Change事件
		$('#leaveType').change(function(){
			$('#leaveReasons_select').val('1');
			$ ('#leaveReasons_input').val('');
			if( $(this).val() ==2){
				$('#leaveReasons_select').hide();
				$('#leaveReasons_input').show();
			}else{
				$('#leaveReasons_select').show();
				$('#leaveReasons_input').hide();
			}
		});
		
		$('#leaveReasons_select').change( function(){
			$('#leaveReasons_input').val($(this).val());
		});
		
		$('#payment').trigger('change');
		$('#leaveType').trigger('change');
		$('#leaveReasons_select').trigger('change');
	})(jQuery);

	// Esc按键事件 - 隐藏弹出框
	$(document).keyup(function(e){
	    if (e.keyCode == 27) 
	    {
	    	$('#employeeContractModalId').addClass('hide');
	    	$('#shield').hide();
	    }
	});
	
	// 显示雇员离职模态框
	function handleContract(contractId){
		// 设置派送信息ID
		$('.manageEmployeeContract_contractId').val(contractId);
		$('#contractIdTitle').html(contractId);
		
		loadHtmlWithRecall('#handlePopupWrapper', 'employeeContractAction.do?proc=get_object_ajax_popup&contractId=' + contractId, null, 'showPopup();');
	};
	
	// 显示弹出框
	function showPopup(){
		$('#employeeContractModalId').removeClass('hide');
    	$('#shield').show();
    	$('.handleEmployeeContract_employStatus').attr('disabled', 'disabled');
	};
	
	// 页面提交
	function handleEmployeeContract(){
		if($('#resignDate').val() && ($('.handleEmployeeContract_employStatus').val() == 1)){
			$('.handleEmployeeContract_employStatus').val($(".handleEmployeeContract_employStatus option:last-child").val());
			 
		}
		
		if(validata_subForm() == 0){
			$('#employeeContractModalId').css('z-index','9997');
			// Enable form，否则后台换取不到disabled的property
			enableForm('handleEmployeeContract_form');
			submitForm('handleEmployeeContract_form');
		}
	};
	
	// 验证提交表单
	function validata_subForm(){
		var flag = 0;
		flag = flag + validate("resignDate", true, "common", 100, 0);
		return flag;
	};
</script>