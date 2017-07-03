<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetReportExportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>
<%-- <%
	String isHRFunction = BaseAction.getRole( request, null ).equals( "1" ) ? "1" : "2";
	if( isHRFunction.equals( "2" ) )
	{
	   isHRFunction = BaseAction.isHRFunction( request, null ) ? "1" : "2";
	}
	request.setAttribute( "isHRFunction", isHRFunction );
%> --%>
<div id="content">
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.ts.report.search.title" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="timesheetReportExportAction.do?proc=list_object" method="post" styleClass="searchTimesheetHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="timesheetReportExportHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="timesheetReportExportHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="timesheetReportExportHolder" property="page" />" />
				<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
				<input type="hidden" name="titleNameList" id="titleNameList" value="" />
				<input type="hidden" name="titleIdList" id="titleIdList" value="" />
				<fieldset>
					<ol class="auto">
						<!-- Hr Function Search Condition Start -->
						<%-- <logic:equal name="isHRFunction" value="1"> --%>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchTimesheetHeader_employeeId" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
									<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.no" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.no" /></logic:equal>
								</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchTimesheetHeader_employeeNo" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchTimesheetHeader_employeeNameZH" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
								</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchTimesheetHeader_employeeNameEN" /> 
							</li>
							<li>
								<label><bean:message bundle="public" key="public.certificate.type" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchTimesheetHeader_certificateNumber" /> 
							</li>
							<logic:equal name="role" value="1">	
								<li>
									<label>客户ID</label>
									<html:text property="clientId" maxlength="10" styleClass="searchTimesheetHeader_clientId" /> 
								</li> 
								<li>
									<label>客户名称（中文）</label>
									<html:text property="clientNameZH" maxlength="100" styleClass="searchTimesheetHeader_clientNameZH" /> 
								</li>
								<li>
									<label>客户名称（英文）</label>
									<html:text property="clientNameEN" maxlength="100" styleClass="searchTimesheetHeader_clientNameEN" /> 
								</li>
							</logic:equal>
							<logic:equal name="role" value="1">
								<li>
									<label>订单ID</label>
									<html:text property="orderId" maxlength="10" styleClass="searchTimesheetHeader_orderId" /> 
								</li>
							</logic:equal>
							<logic:equal name="role" value="2">
								<li>
									<label><bean:message bundle="public" key="public.order2.id" /></label>
				   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
										<html:select property="orderId" styleClass="searchTimesheetHeader_orderId">
											<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
										</html:select>
				   					</logic:notEmpty>
				   					<logic:empty name="clientOrderHeaderMappingVOs">
				   						<html:text property="orderId" maxlength="10" styleClass="searchTimesheetHeader_orderId" /> 
				   					</logic:empty>
			   					</li>
		   					</logic:equal>
			   				<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="4"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>	
								</label>
								<html:text property="contractId" maxlength="10" styleClass="searchTimesheetHeader_contractId" /> 
							</li> 
						<%-- </logic:equal> --%>
						<!-- Hr Function Search Condition End -->
						<li>
							<label><bean:message bundle="business" key="business.ts.month" /></label> 
							<html:select property="monthly" styleClass="searchTimesheetHeader_monthly">
								<html:optionsCollection property="last12Months" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchTimesheetHeader_status">
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
				<kan:auth right="export" action="<%=TimesheetReportExportAction.accessAction%>">
	            	<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />"><img src="images/appicons/excel_16.png" /></a> 
	            </kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/timesheet/export/table/listTimesheetReportExportTable.jsp" flush="true"/>
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
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Reports').addClass('selected');
		$('#menu_attendance_Timesheet_Report_Export').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		
		$('#titleNameList').val(getTabTitle());
		$('#titleIdList').val(getTabDB()); 
		
		$('#exportExcel').click( function(){
			$('#selectedIds').val('');
			submitFormForDownload('searchTimesheetHeader_form');
		});
	})(jQuery);
	// Reset JS of the Search
	function resetForm() {
	    $('.searchTimesheetHeader_employeeId').val('');
	    $('.searchTimesheetHeader_employeeNo').val('');
	    $('.searchTimesheetHeader_certificateNumber').val('');
	    $('.searchTimesheetHeader_employeeNameZH').val('');
	    $('.searchTimesheetHeader_employeeNameEN').val('');
	    $('.searchTimesheetHeader_contractId').val('');
	    $('.searchTimesheetHeader_orderId').val('');
	    $('.searchTimesheetHeader_clientId').val('');
	    $('.searchTimesheetHeader_clientNameZH').val('');
	    $('.searchTimesheetHeader_clientNameEN').val('');
	    $('.searchTimesheetHeader_monthly').val('0');
	};
	
	function getTabTitle(){
		var oArray = new Array();
		var oTTFirstTrTh = $('#_fixTableMain table thead tr').first().find('th').not('.noTitle');
		oTTFirstTrTh.each( function (i){
			oArray[i] = $(this).find('span').html();
		});
		return oArray;
	};
	
	function getTabDB(){
		var oArray = new Array();
		var oTTFirstTrTh = $('#_fixTableMain table thead tr').first().find('th').not('.noTitle');
		oTTFirstTrTh.each( function (i){
			oArray[i] = $(this).find('span').attr('id');
		});
		return oArray;
	};
</script>
