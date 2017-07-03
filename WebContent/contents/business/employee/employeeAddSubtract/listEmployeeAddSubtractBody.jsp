<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeAddSubtractAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<div id="content">
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>增减员报表</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />"  />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeAddSubtractAction.do?proc=list_object" method="post" styleClass="employeeAddSubtract_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeAddSubtractHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeAddSubtractHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeAddSubtractHolder" property="page" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li class="required">
							<label>月份<span><img src="images/tips.png" title="操作类型是增员，取选择月份及其以前的增员数据；操作类型是减员，取选择月份及其以后的减员数据" /></span></label>
							<html:select property="month" styleClass="employeeAddSubtract_month">
							<html:option value="" >请选择</html:option>
								<html:optionsCollection property="last12Months" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>操作类型</label> 
							<html:select property="opType" styleClass="employeeAddSubtract_opType">
								<html:optionsCollection property="opTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>报表类型</label> 
							<html:select property="type" styleId="type" styleClass="employeeAddSubtract_type">
								<html:optionsCollection property="types" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
								<html:text property="employeeId" maxlength="100" styleClass="employeeAddSubtract_employeeId" /> 
							</li>
							</ol>
				<ol class="auto">
							<li>
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名</label>
								<html:text property="employeeName" maxlength="100" styleClass="employeeAddSubtract_employeeName" /> 
							</li>
							<li>
								<label>证件号码</label>
								<html:text property="certificateNumber" maxlength="100" styleClass="employeeAddSubtract_certificateNumber" /> 
							</li>
							<logic:equal name="role" value="1">	
								<li>
									<label>客户ID</label>
									<html:text property="clientId" maxlength="10" styleClass="employeeAddSubtract_clientId" /> 
								</li> 
								<li>
									<label>客户名称</label>
									<html:text property="clientName" maxlength="100" styleClass="employeeAddSubtract_clientName" /> 
								</li>
						
								<li>
									<label>财务编码</label>
									<html:text property="number" maxlength="100" styleClass="employeeAddSubtract_number" /> 
								</li>
							</logic:equal>
			   				<li>
								<label><logic:equal name="role" value="1">派送信息</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>ID</label>
								<html:text property="contractId" maxlength="10" styleClass="employeeAddSubtract_contractId" /> 
							</li> 
						<li>
							<label><logic:equal name="role" value="1">派送协议</logic:equal><logic:equal name="role" value="2">劳动合同</logic:equal>状态</label> 
							<html:select property="contractStatus" styleId="contractStatus" styleClass="searchSBAdjustmentImportBatch_contractStatus">
								<html:optionsCollection property="contractStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
						<li>
							<label>社保公积金状态</label> 
							<html:select property="sbStatus" styleId="sbStatus" styleClass="searchSBAdjustmentImportBatch_sbStatus">
								<html:optionsCollection property="sbStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
						<li>
							<label>商保状态</label> 
							<html:select property="cbStatus" styleId="cbStatus" styleClass="searchSBAdjustmentImportBatch_cbStatus">
								<html:optionsCollection property="cbStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="export" action="<%=EmployeeAddSubtractAction.accessAction%>">
	            	<a id="exportExcel" name="exportExcel" class="commonTools" title="导出Excel文件"><img src="images/appicons/excel_16.png" /></a> 
	            </kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/employee/employeeAddSubtract/table/listEmployeeAddSubtractTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	(function($) {
		// JS of the List 
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Reports').addClass('selected');
		$('#menu_employee_AddSubtract_Reports').addClass('selected');
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	// Reset JS of the Search
	function resetForm() {
	    $('.employeeAddSubtract_employeeId').val('');
	    $('.employeeAddSubtract_employeeName').val('');
	    $('.employeeAddSubtract_certificateNumber').val('');
	    $('.employeeAddSubtract_contractId').val('');
	    $('.employeeAddSubtract_number').val('');
	    $('.employeeAddSubtract_clientId').val('');
	    $('.employeeAddSubtract_clientName').val('');
	    $('.employeeAddSubtract_opType').val('1');
	    $('.searchSBAdjustmentImportBatch_contractStatus').val('0');
	    $('.searchSBAdjustmentImportBatch_sbStatus').val('0');
	    $('.searchSBAdjustmentImportBatch_cbStatus').val('0');
	    $('.employeeAddSubtract_type').val('5');
	 	$('.employeeAddSubtract_month').val('<%=request.getAttribute("nowDate")%>');
	    
	    
	};
	
		
		$('#exportExcel').click( function(){

		$('#selectedIds').val('');
		
		var type=$('#type option:selected').val();
		if(type=='5'&&$('#contractStatus').val()=='0'){
			alert("请选择派送协议状态!");
		}else if (type=='6'&&$('#cbStatus').val()=='0'){
			alert("请选择商保状态!");
		}else if(type!='5'&&type!='6'&&$('#sbStatus').val()=='0'){
			alert("请选择社保公积金状态!");
		}else{
			linkForm('employeeAddSubtract_form', null, 'employeeAddSubtractAction.do?proc=exportReport', null)
		}
		
		
		});
		
		
		$('#searchBtn').click( function(){
			var type=$('#type option:selected').val();
			if(type=='5'&&$('#contractStatus').val()=='0'){
				alert("请选择派送协议状态!");
			}else if (type=='6'&&$('#cbStatus').val()=='0'){
				alert("请选择商保状态!");
			}else if(type!='5'&&type!='6'&&$('#sbStatus').val()=='0'){
				alert("请选择社保公积金状态!");
			}else{
				submitForm('employeeAddSubtract_form', 'searchObject', null, null, null, null);
			}
			
		}); 
		
	
	
</script>
