<%@page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.LeaveImportHeaderAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.LeaveImportBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<%
	final boolean isHRFunction = BaseAction.isHRFunction( request, null );
	request.setAttribute( "isHRFunction", isHRFunction ? "1" : "2" );
%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.leave.import" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="leaveImportHeaderAction.do?proc=list_object" method="post" styleClass="searchTimesheetHeader_form" styleId="searchTimesheetHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="leaveImportHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="leaveImportHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="leaveImportHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="leaveImportHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="pageFlag" id="pageFlag" value="header" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="leaveImportHeaderForm" property="encodedBatchId" />' />
				<fieldset>
					<ol class="auto">
						<!-- Hr Function Search Condition Start -->
						<logic:equal name="isHRFunction" value="1">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchTimesheetHeader_employeeId" /> 
							</li>
							<li style="display: none;">
								<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>编号</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchTimesheetHeader_employeeNo" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchTimesheetHeader_employeeNameZH" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
								</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchTimesheetHeader_employeeNameEN" /> 
							</li>
							<li>
								<label><bean:message bundle="public" key="public.certificate.number" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchTimesheetHeader_certificateNumber" /> 
							</li>
			   				<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
								</label>
								<html:text property="contractId" maxlength="10" styleClass="searchTimesheetHeader_contractId" /> 
							</li> 
						</logic:equal>
					
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
				<!-- 如果是HR职能 -->
				<logic:equal name="isHRFunction" value="1" ></logic:equal>
					<kan:auth right="list" action="<%=LeaveImportBatchAction.accessAction%>">
						<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('leaveImportBatchAction.do?proc=list_object');" />
					</kan:auth>
			    <logic:equal name="timesheetBatchForm" property="status" value="1">
			    	<kan:auth right="back" action="<%=LeaveImportBatchAction.accessAction%>">
            			<input type="button" class="delete" name="btnRollback" id="btnRollback" value="<bean:message bundle="public" key="button.return" />"  />
            		</kan:auth>
            	</logic:equal>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<!-- Timesheet Batch Info -->
			<div class="tabMenu"> 
				<ul> 
					<li id="tabMenu1" onClick="changeTab(1,1)" class="first hover"><bean:message bundle="business" key="business.batch" /> (ID: <bean:write name="timesheetBatchForm" property="batchId" />)</li> 
				</ul> 
			</div>
			<div class="tabContent"> 
				<div id="tabContent1" class="kantab" >
					<form action="">
						<fieldset>
				            <ol class="auto">
				            	<li><label><bean:message bundle="business" key="business.leave.number" /></label><span><bean:write name="timesheetBatchForm" property="countHeaderId"/></span></li>
				            	<li><label><bean:message bundle="business" key="business.leave.ot.hours.number" /></label><span><bean:write name="timesheetBatchForm" property="totalLeaveHours"/></span></li>
				            </ol>
		                </fieldset>
	               </form> 
				</div>
			</div>
			<!-- Timesheet Batch Info End-->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/leave/table/listLeaveImportHeaderTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Import').addClass('selected');
		$('#menu_attendance_Leave_Import').addClass('selected');

		kanList_init();
		kanCheckbox_init();
		
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.searchTimesheetHeader_form').attr('action', 'timesheetHeaderAction.do?proc=submit_header');
					submitForm('searchTimesheetHeader_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
		});
		
	})(jQuery);
	

	
	
	$('#btnRollback').click(function(){
		
		if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
			if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
				$('.searchTimesheetHeader_form').attr('action', 'leaveImportHeaderAction.do?proc=back_heard');
				$('#subAction').val('rollbackObjects');
				submitForm('searchTimesheetHeader_form');
			}
		}else{
			alert('<bean:message bundle="public" key="popup.not.selected.records" />');
		}
	});
	
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
	    $('.searchTimesheetHeader_status').val('0');
	};
	
	function submit_object( id ){
		link('timesheetHeaderAction.do?proc=submit_object&id=' + id);  
	};
</script>
