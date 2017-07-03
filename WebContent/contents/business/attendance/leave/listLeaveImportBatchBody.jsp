<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.LeaveImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information TimesheetBatch Form -->
	<div class="box searchForm toggableForm" id="TimesheetBatch-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.leave.import" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetBatch_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="leaveImportBatchAction.do?proc=list_object" method="post" styleClass="searchTimesheetBatch_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="timesheetBatchHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="timesheetBatchHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="timesheetBatchHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="timesheetBatchHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchTimesheetBatch_employeeId" /> 
						</li>
		   				<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="10" styleClass="searchTimesheetBatch_contractId" /> 
						</li> 
				
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchTimesheetBatch_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information TimesheetBatch Result -->
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
				<kan:auth right="submit" action="<%=LeaveImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="back" action="<%=LeaveImportBatchAction.accessAction%>">
					<input type="button" class="delete" name="btnBack" id="btnBack" value="<bean:message bundle="public" key="button.return" />" />				
				</kan:auth>
	            <a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
	            <kan:auth right="import" action="<%=LeaveImportBatchAction.accessAction%>">
	            	<img style="float:right" src="images/import.png" onclick="popupExcelImport();" title="<bean:message bundle="public" key="img.title.tips.import.excel" />">
	            </kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/leave/table/listLeaveImportBatchTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>
<div id="popupWrapper">
	<jsp:include page="/popup/importExcel.jsp" >
		<jsp:param value="<%=LeaveHeaderAction.accessAction%>" name="accessAction"/>
		<jsp:param value="true" name="closeRefesh"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		// JS of the List 
		$('#menu_attendance_Import').addClass('selected');
		$('#menu_attendance_Leave_Import').addClass('selected');
		$('#menu_attendance_Modules').addClass('current');
		kanList_init();
		kanCheckbox_init();
		
		// 批次更新事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.selected.batch" />')){
					$('.searchTimesheetBatch_form').attr('action', 'leaveImportBatchAction.do?proc=submit_batch');
					submitForm('searchTimesheetBatch_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
		// 批次退回事件
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.selected.batch" />')){
					$('.searchTimesheetBatch_form').attr('action', 'leaveImportBatchAction.do?proc=back_batch');
					submitForm('searchTimesheetBatch_form', "rollbackObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
	})(jQuery);
	// Reset JS of the Search
	function resetForm() {
	    $('.searchTimesheetBatch_employeeId').val('');
	    $('.searchTimesheetBatch_contractId').val('');
	    $('.searchTimesheetBatch_orderId').val('');
	    $('.searchTimesheetBatch_clientId').val('');
	    $('.searchTimesheetBatch_monthly').val('0');
	    $('.searchTimesheetBatch_status').val('0');
	};
</script>
