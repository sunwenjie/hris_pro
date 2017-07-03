<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.web.actions.biz.attendance.LeaveHeaderAction"%>
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
       		<label><bean:message bundle="business" key="business.leave.search.title" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchLeave_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="leaveHeaderAction.do?proc=list_object" method="post" styleClass="searchLeave_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="leaveHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="leaveHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="leaveHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="leaveHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<!-- Hr Function Search Condition Start -->
						
						<logic:equal name="isHRFunction" value="1">
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchLeave_employeeId" /> 
							</li>
							<li style="display: none;">
								<label>档案编号</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchLeave_employeeNo" /> 
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
							<li>
								<label><bean:message bundle="public" key="public.certificate.number" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchLeave_certificateNumber" /> 
							</li>
						</logic:equal>
						<!-- Hr Function Search Condition End -->
						<li>
							<label><bean:message bundle="business" key="business.leave.type" /></label> 
							<html:select property="itemId" styleClass="searchLeave_itemId">
								<html:optionsCollection property="leaveItems" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.start.time" /></label> 
							<html:text property="estimateStartDate" maxlength="20" readonly="true" styleClass="searchLeave_estimateStartDate" styleId="estimateStartDate" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.end.time" /></label> 
							<html:text property="estimateEndDate" maxlength="20" readonly="true" styleClass="searchLeave_estimateEndDate" styleId="estimateEndDate" /> 
						</li>
						<logic:equal name="role" value="1">
							<li>
								<label>客户ID</label>
								<html:text property="clientId" maxlength="10" styleClass="searchLeave_clientId" /> 
							</li> 
							<li>
								<label>客户名称（中文）</label>
								<html:text property="clientNameZH" maxlength="100" styleClass="searchLeave_clientNameZH" /> 
							</li>
							<li>
								<label>客户名称（英文）</label>
								<html:text property="clientNameEN" maxlength="100" styleClass="searchLeave_clientNameEN" /> 
							</li>
						</logic:equal>
						<logic:equal name="role" value="2">
							<logic:equal name="isHRFunction" value="1">
								<li>
									<label><bean:message bundle="public" key="public.contract2.id" /></label>
									<html:text property="contractId" maxlength="10" styleClass="searchLeave_contractId" /> 
								</li> 
							</logic:equal>
						</logic:equal>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchLeave_status">
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
				<kan:auth right="new" action="<%=LeaveHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('leaveHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="submit" action="<%=LeaveHeaderAction.accessAction%>">
					<input type="button" class="function" id="btnSubmit" name="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />				
				</kan:auth>
				
				<kan:auth right="delete" action="<%=LeaveHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('searchLeave_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="export" action="<%=LeaveHeaderAction.accessAction%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchLeave_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/leave/table/listLeaveTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Leave').addClass('selected');
		<logic:equal name="role" value="5">
			$('#menu_attendance_Leave').addClass('current');
        </logic:equal>
		kanList_init();
		kanCheckbox_init();

		
		$('#estimateStartDate').addClass('Wdate');
		$('#estimateStartDate').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd HH:mm',
	    		 maxDate:'#F{$dp.$D(\'estimateEndDate\')}'
	    	 });
		});
		$('#estimateEndDate').addClass('Wdate');
		$('#estimateEndDate').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd HH:mm',
	    		 minDate:'#F{$dp.$D(\'estimateStartDate\')}'
	    	 });
		});
		
		// 批量提交
		$('#btnSubmit').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if( getFlag( "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=3&objectName=leave&selectedIds=" + $('#selectedIds').val() ) == false ){
					if(!confirm('<bean:message bundle="public" key="popup.leave.not.entry.ts" />')){
						return;
					}
				}
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.searchLeave_form').attr('action', 'leaveHeaderAction.do?proc=submit_objects');
					submitForm('searchLeave_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.searchLeave_form').attr('action', 'leaveHeaderAction.do?proc=list_object');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.select.submit.records" />');
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	    $('.searchLeave_employeeId').val('');
	    $('.searchLeave_employeeNo').val('');
	    $('.searchLeave_employeeNameZH').val('');
	    $('.searchLeave_employeeNameEN').val('');
	    $('.searchLeave_certificateNumber').val('');
	    $('.searchLeave_itemId').val('0');
	    $('.searchLeave_estimateStartDate').val('');
	    $('.searchLeave_estimateEndDate').val('');
	    $('.searchLeave_clientId').val('');
	    $('.searchLeave_clientNameZH').val('');
	    $('.searchLeave_clientNameEN').val('');
	    $('.searchLeave_contractId').val('');
	    $('.searchLeave_status').val('0');
	};
	
	//modify by siuvan 2014-08-28
	function submit_object( id, employeeId, contractId, startDate ){
		
		if( getFlag( "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=2&employeeId=" + employeeId + "&contractId=" + contractId + "&startDate=" + startDate ) == false ){
			if(!confirm('<bean:message bundle="public" key="popup.leave.not.entry.ts" />')){
				return;
			}
		}
		
		$('.searchLeave_form').attr('action', 'leaveHeaderAction.do?proc=submit_object&id=' + id);
		submitForm('searchLeave_form', 'submitObject', null, null, null, 'tableWrapper');
	};
	
	// 提交时，若改月考勤表非新建、退回状态则给出提示。
	function getFlag( url ){
		var flag = true;
		$.ajax({
			url : url, 
			type: 'POST',
			async:false,
			success : function(data){
				if(data == "1"){
					flag = false;
				}
			}
		});
		return flag;
	};
	
	function pageForward() {
		var value = Number($('#forwardPage_render').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
		submitForm('searchLeave_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>
