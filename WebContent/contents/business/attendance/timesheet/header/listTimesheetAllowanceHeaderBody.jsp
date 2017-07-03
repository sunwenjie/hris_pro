<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetAllowanceHeaderAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetAllowanceBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.allowance.import" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="timesheetAllowanceHeaderAction.do?proc=list_object" method="post" styleClass="searchTimesheetHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="timesheetHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="timesheetHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="timesheetHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="timesheetHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="timesheetHeaderForm" property="encodedBatchId" />' />
				<fieldset>
					<ol class="auto">
						<!-- Hr Function Search Condition Start -->
						<logic:equal name="isHRFunction" value="1">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchTimesheetHeader_employeeId" /> 
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
							<logic:equal name="role" value="1">
								<li>
									<label><bean:message bundle="public" key="public.order1.id" /></label>
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
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.contract1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.contract2.name.cn" /></logic:equal>
								</label>
								<html:text property="contractId" maxlength="10" styleClass="searchTimesheetHeader_contractId" /> 
							</li> 
						</logic:equal>
						<!-- Hr Function Search Condition End -->
						<li>
							<label><bean:message bundle="business" key="business.allowance.import.monthly" /></label> 
							<html:select property="monthly" styleClass="searchTimesheetHeader_status">
								<html:optionsCollection property="last12Months" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<%-- <li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchTimesheetHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li> --%>
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
				<kan:auth right="list" action="<%=TimesheetAllowanceBatchAction.accessAction%>">
					<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('timesheetAllowanceBatchAction.do?proc=list_object');" />
				</kan:auth>
				<logic:notEqual name="timesheetBatchForm" property="status" value="2">
					<kan:auth right="back" action="<%=TimesheetAllowanceBatchAction.accessAction%>">
						<input type="button" class="function hover" id="btnBack" name="btnBack" value="<bean:message bundle="public" key="button.return" />"  />
					</kan:auth>	
				</logic:notEqual>
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
						   	<ol class="auto" >
				            	<li><label><bean:message bundle="business" key="business.allowance.import.monthly" /></label><span><bean:write name="timesheetBatchForm" property="monthly"/></span></li>
				            	<li><label><bean:message bundle="business" key="business.ts.order.number" /></label><span><bean:write name="timesheetBatchForm" property="countOrderId"/></span></li>
				            </ol>
				            <ol class="auto">
				            	<li><label><bean:message bundle="business" key="business.ts.contract.number" /></label><span><bean:write name="timesheetBatchForm" property="countContractId"/></span></li>
				            	<li><label><bean:message bundle="business" key="business.ts.ts.number" /></label><span><bean:write name="timesheetBatchForm" property="countHeaderId"/></span></li>
				            </ol>
		                </fieldset>
	               </form> 
				</div>
			</div>
			<!-- Timesheet Batch Info End-->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/timesheet/header/table/listTimesheetAllowanceHeaderTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Timesheet_Allowance').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		
	
		//退回
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$('.searchTimesheetHeader_form').attr('action', 'timesheetAllowanceHeaderAction.do?proc=back_header');
					submitForm('searchTimesheetHeader_form');
					$('#selectedIds').val('');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.not.selected.records" />');
			}
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
	    $('.searchTimesheetHeader_status').val('0');
	};
	
	function submit_object( id ){
		link('timesheetHeaderAction.do?proc=submit_object&id=' + id);  
	};
</script>
