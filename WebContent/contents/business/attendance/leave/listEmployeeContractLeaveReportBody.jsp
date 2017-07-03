<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeContractLeaveReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.employee.contract.leave.detail.report" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchEmployeeContractLeaveReport_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeContractLeaveReportAction.do?proc=list_object" method="post" styleClass="searchEmployeeContractLeaveReport_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeContractLeaveReportHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeContractLeaveReportHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeContractLeaveReportHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeContractLeaveReportHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchLeave_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchLeave_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchLeave_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="2">
						<logic:equal name="accountId" value="100017">
						<li>
							<label>
								<bean:message bundle="business" key="business.employee.report.nick.name" />
							</label>
							<html:text property="remark1" maxlength="100" styleClass="searchLeave_remark1" /> 
						</li>
						</logic:equal>
						</logic:equal>
						<li>
							<label>Leave Start</label>
							<html:text property="leaveStartDate" maxlength="20" styleClass="searchLeave_leaveStartDate Wdate" styleId="leaveStartDate" /> 
						</li>
						<li>
							<label>Leave End</label>
							<html:text property="leaveEndDate" maxlength="20" styleClass="searchLeave_leaveEndDate Wdate" styleId="leaveEndDate" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.employee.status" /></label>
							<html:select property="employStatus" styleClass="searchLeave_employStatus">
								<html:optionsCollection property="employStatuses" value="mappingId" label="mappingValue" />
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
				<kan:auth right="export" action="<%=EmployeeContractLeaveReportAction.ACCESS_ACTION%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchEmployeeContractLeaveReport_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/leave/table/listEmployeeContractLeaveReportTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Reports').addClass('selected');
		$('#menu_attendance_Leave_Report').addClass('selected');
		
		$('#leaveStartDate').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd',
	    		 maxDate:'#F{$dp.$D(\'leaveEndDate\')}'
	    	 });
		});
		$('#leaveEndDate').focus(function(){
			 WdatePicker({
				 dateFmt:'yyyy-MM-dd',
	    		 minDate:'#F{$dp.$D(\'leaveStartDate\')}'
	    	 });
		});
		
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.searchEmployeeContractLeaveReport_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>
