<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.OTImportHeaderAction"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.OTImportBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
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
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="business" key="business.ot.import" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTimesheetHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="otImportHeaderAction.do?proc=list_object" method="post" styleClass="searchTimesheetHeader_form" styleId="searchTimesheetHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="otImportHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="otImportHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="otImportHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="otImportHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="otImportHeaderForm" property="encodedBatchId" />' />
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
				<kan:auth right="list" action="<%=OTImportBatchAction.accessAction%>">
					<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('otImportBatchAction.do?proc=list_object');" />
				</kan:auth>
				<kan:auth right="back" action="<%=OTImportBatchAction.accessAction%>">
					<logic:equal name="timesheetBatchForm" property="status" value="1"><input type="button" class="function hover" name="btnBack" id="btnBack" value="<bean:message bundle="public" key="button.return" />"></logic:equal>
				</kan:auth>
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
				            	<li><label><bean:message bundle="business" key="business.ot.number" /></label><span><bean:write name="timesheetBatchForm" property="countHeaderId"/></span></li>
				            	<li><label><bean:message bundle="business" key="business.ot.hours.number" /></label><span><bean:write name="timesheetBatchForm" property="totalOTHours"/></span></li>
				            </ol>
		                </fieldset>
	               </form> 
				</div>
			</div>
			<!-- Timesheet Batch Info End-->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/ot/table/listOTImportHeaderTable.jsp" flush="true"/> 
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
		$('#menu_attendance_OT_Import').addClass('selected');
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
		
		
		$('#btnBack').click( function(){
			if($("input[name='ids']:checked").length > 0){
				if(confirm('<bean:message bundle="public" key="popup.confirm.return.records" />')){
					$.ajax({
						url: 'otImportHeaderAction.do?proc=backUpRecord&batchId='+$("#batchId").val(), 
						type: 'POST', 
						traditional: true,
						data:$('.otBackForm').serialize(),
						dataType : 'json',
						async:false,
						success: function(result){
							if (result == "0") {
								window.location.href = "otImportBatchAction.do?proc=list_object";
							}else{
								window.location.href = "otImportHeaderAction.do?proc=list_object&batchId="+$("#batchId").val()+"&batchStatus="+$("#batchStatus").val();
								$('#messageWrapper').html('<div class="message success fadable">退回成功！<a onclick="removeMessageWrapperFada();" class="messageCloseButton">&nbsp;</a></div>');
							}
							
						} 
					});
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
