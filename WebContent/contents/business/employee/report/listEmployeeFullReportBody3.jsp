<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.employee.report.full" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchEmployeeReport_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeReportAction.do?proc=list_object" method="post" styleClass="searchEmployeeReport_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeReportHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeReportHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeReportHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeReportHolder" property="selectedIds" />" />
				<input type="hidden" name="rt" id="reportType" value="full" />
				<input type="hidden" name="rl" id="rl" value="3" />
				<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
				<input type="hidden" name="titleNameList" id="titleNameList" value="" />
				<input type="hidden" name="titleIdList" id="titleIdList" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="search_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="nameZH" maxlength="100" styleClass="search_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="nameEN" maxlength="100" styleClass="search_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="2">
						<logic:equal name="accountId" value="100017">
						<li>
							<label>
								<bean:message bundle="business" key="business.employee.report.nick.name" />
							</label>
							<html:text property="remark1" maxlength="100" styleClass="search_remark1" /> 
						</li>
						</logic:equal>
						</logic:equal>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label>
							<html:select property="entityId" styleClass="search_entitys">
								<html:optionsCollection property="entitys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.report.new.contractStartDate.after" /></label>
							<input type="text" name="startWorkDateStart" class="Wdate" onfocus="WdatePicker()" value="<bean:write name="employeeReportForm" property="startWorkDateStart" />" />
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.report.new.contractStartDate.before" /></label>
							<input type="text" name="startWorkDateEnd" class="Wdate" onfocus="WdatePicker()" value="<bean:write name="employeeReportForm" property="startWorkDateEnd" />" />
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.report.dimission.contractStartDate.after" /></label>
							<input type="text" name="resignDateStart" class="Wdate" onfocus="WdatePicker()" value="<bean:write name="employeeReportForm" property="resignDateStart" />" />
						</li>
						<li>
							<label><bean:message bundle="business" key="business.employee.report.dimission.contractStartDate.before" /></label>
							<input type="text" name="resignDateEnd" class="Wdate" onfocus="WdatePicker()" value="<bean:write name="employeeReportForm" property="resignDateEnd" />" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee2.status" /></label>
							<html:select property="status" styleClass="search_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
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
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="export" action="<%=EmployeeReportAction.ACCESS_ACTION_FULL_3%>"> 
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="submitFormForDownload('searchEmployeeReport_form');"><img src="images/appicons/excel_16.png" /></a> 
			    </kan:auth> 
			</div>
			<!-- top -->
			<div id="tableWrapper" style="overflow: hidden;">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/employee/report/table/listEmployeeFullReportBodyTable3.jsp" flush="true"/> 
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
		// JS of the List 系统自动生成js代码 
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Reports').addClass('selected');
		$('#menu_biz_Employee_Full_Report_3').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
	    $('#titleNameList').val(getTabTitle());
		$('#titleIdList').val(getTabDB()); 
	})(jQuery); 
	
	function resetForm() {
	   var oForm = $('.searchEmployeeReport_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
	
	function getTabTitle(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).html();
		});
		return oArray;
	};
	
	function getTabDB(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).attr('id');
		});
		return oArray;
	};
</script>
