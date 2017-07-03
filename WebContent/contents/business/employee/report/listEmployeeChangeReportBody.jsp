<%@page import="com.kan.hro.web.actions.biz.employee.EmployeeChangeReportAction"%>
<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>Employee Change Report</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchEmployeeChangeForm', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeChangeReportAction.do?proc=list_object" method="post" styleClass="searchEmployeeChangeForm">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="pagedListHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="pagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="pagedListHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="pagedListHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
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
							<html:text property="employeeNameZH" maxlength="100" styleClass="search_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="search_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="business" key="employee.change.report.changeType" /></label>
							<html:select property="changeType" styleClass="search_changeType">
								<html:optionsCollection property="changeTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="business" key="employee.position.change.description" /></label>
							<html:select property="changeReason" styleClass="search_changeReason">
								<html:optionsCollection property="changeReasons" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="business" key="employee.change.report.operateTime.start" /></label>
							<html:text styleId="operateTimeBegin" property="operateTimeBegin" styleClass="Wdate search_operateTimeBegin" />
						</li>
						<li>
							<label><bean:message bundle="business" key="employee.change.report.operateTime.end" /></label>
							<html:text styleId="operateTimeEnd" property="operateTimeEnd" styleClass="Wdate search_operateTimeEnd" />
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
				<kan:auth right="export" action="<%=EmployeeChangeReportAction.ACCESS_ACTION%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchEmployeeChangeForm', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/employee/report/table/listEmployeeChangeReportBodyTable.jsp" flush="true"/> 
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
		$('#menu_biz_Employee_Change_Report').addClass('selected');
		
		$('#operateTimeBegin').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd HH:mm:ss',
	    		 maxDate:'#F{$dp.$D(\'operateTimeEnd\')}'
	    	 });
		});
		$('#operateTimeEnd').focus(function(){
			 WdatePicker({
				 dateFmt:'yyyy-MM-dd HH:mm:ss',
	    		 minDate:'#F{$dp.$D(\'operateTimeBegin\')}'
	    	 });
		});
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery); 
	
	function resetForm() {
	   var oForm = $('.searchEmployeeChangeForm');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>
