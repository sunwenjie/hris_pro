<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<%
	String isHRFunction = BaseAction.getRole( request, null ).equals( "1" ) ? "1" : "2";
	if( isHRFunction.equals( "2" ) )
	{
	   isHRFunction = BaseAction.isHRFunction( request, null ) ? "1" : "2";
	}
	request.setAttribute( "isHRFunction", isHRFunction );
%>

<div id="content">	
	<!-- Information TimesheetBatch Form -->
	<div class="box searchForm toggableForm" id="TimesheetBatch-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.ts.search.title" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetBatch_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="timesheetBatchAction.do?proc=list_object" method="post" styleClass="searchTimesheetBatch_form">
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
								<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchTimesheetBatch_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
							</label>
							<html:text property="employeeNameZH" maxlength="10" styleClass="searchTimesheetBatch_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="4"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
							</label>
							<html:text property="employeeNameEN" maxlength="10" styleClass="searchTimesheetBatch_employeeNameEN" /> 
						</li>
						<logic:equal name="role" value="1">	
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="10" styleClass="searchTimesheetBatch_clientId" /> 
							</li> 
						</logic:equal>
						<logic:equal name="role" value="1">
							<li>
								<label>订单ID</label>
								<html:text property="orderId" maxlength="10" styleClass="searchTimesheetBatch_orderId" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<li>
								<label><bean:message bundle="public" key="public.order2.id" /></label>
			   					<logic:notEmpty name="clientOrderHeaderMappingVOs">
									<html:select property="orderId" styleClass="searchTimesheetBatch_orderId">
										<html:optionsCollection name="clientOrderHeaderMappingVOs" value="mappingId" label="mappingValue" />
									</html:select>
			   					</logic:notEmpty>
			   					<logic:empty name="clientOrderHeaderMappingVOs">
			   						<html:text property="orderId" maxlength="10" styleClass="searchTimesheetBatch_orderId" /> 
			   					</logic:empty>
		   					</li>
	   					</logic:equal>
		   				<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="4"><bean:message bundle="public" key="public.contract1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.id" /></logic:equal>
							</label>
							<html:text property="contractId" maxlength="10" styleClass="searchTimesheetBatch_contractId" /> 
						</li> 
						<li>
							<label><bean:message bundle="business" key="business.ts.month" /></label> 
							<html:select property="monthly" styleClass="searchTimesheetBatch_status">
								<html:optionsCollection property="last12Months" value="mappingId" label="mappingValue" />
							</html:select>
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
				<!-- 如果是HR职能 -->
				<logic:equal name="isHRFunction" value="1" >
					<kan:auth right="new" action="<%=TimesheetBatchAction.accessActionInHouse%>">
						<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.create" />" onclick="link('timesheetBatchAction.do?proc=to_objectNew');" />
					</kan:auth>
					
					<kan:auth right="submit" action="<%=TimesheetBatchAction.accessActionInHouse%>">
						<input type="button" class="function" name="btnSubmit" id="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />				
					</kan:auth>
				</logic:equal>
	            <a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/timesheet/batch/table/listTimesheetBatchTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Timesheet').addClass('selected');
		
		// 批次提交事件
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.searchTimesheetBatch_form').attr('action', 'timesheetBatchAction.do?proc=submit_batch');
					submitForm('searchTimesheetBatch_form', "submitObjects", null, null, null, 'tableWrapper');
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
	    $('.searchTimesheetBatch_employeeNameZH').val('');
	    $('.searchTimesheetBatch_employeeNameEN').val('');
	    $('.searchTimesheetBatch_contractId').val('');
	    $('.searchTimesheetBatch_orderId').val('');
	    $('.searchTimesheetBatch_clientId').val('');
	    $('.searchTimesheetBatch_monthly').val('0');
	    $('.searchTimesheetBatch_status').val('0');
	};
</script>
