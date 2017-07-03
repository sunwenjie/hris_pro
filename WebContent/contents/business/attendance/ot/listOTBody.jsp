<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.web.actions.biz.attendance.OTHeaderAction"%>
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
       		<label><bean:message bundle="business" key="business.ot.search.title" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchOT_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="otHeaderAction.do?proc=list_object" method="post" styleClass="searchOT_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="otHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="otHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="otHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="otHeaderHolder" property="selectedIds" />" />
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
								<html:text property="employeeId" maxlength="10" styleClass="searchOT_employeeId" /> 
							</li>
							<li style="display: none;">
								<label>档案编号</label>
								<html:text property="employeeNo" maxlength="50" styleClass="searchOT_employeeNo" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="employeeNameZH" maxlength="100" styleClass="searchOT_employeeNameZH" /> 
							</li>
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>
								</label>
								<html:text property="employeeNameEN" maxlength="100" styleClass="searchOT_employeeNameEN" /> 
							</li>
							<li>
								<label><bean:message bundle="public" key="public.certificate.number" /></label>
								<html:text property="certificateNumber" maxlength="100" styleClass="searchOT_certificateNumber" /> 
							</li>
						</logic:equal>
						<!-- Hr Function Search Condition End -->
						<li>
							<label><bean:message bundle="business" key="business.ot.type" /></label> 
							<html:select property="itemId" styleClass="searchOT_itemId">
								<html:optionsCollection property="otItems" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.start.time" /></label> 
							<html:text property="estimateStartDate" maxlength="20" readonly="true" styleClass="searchOT_estimateStartDate" styleId="estimateStartDate" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.end.time" /> </label> 
							<html:text property="estimateEndDate" maxlength="20" readonly="true" styleClass="searchOT_estimateEndDate" styleId="estimateEndDate" /> 
						</li>
						<logic:notEqual name="role" value="2">
							<logic:notEqual name="role" value="5	">
								<li>
									<label>客户ID</label>
									<html:text property="clientId" maxlength="10" styleClass="searchOT_clientId" /> 
								</li> 
								<li>
									<label>客户名称（中文）</label>
									<html:text property="clientNameZH" maxlength="100" styleClass="searchOT_clientNameZH" /> 
								</li>
								<li>
									<label>客户名称（英文）</label>
									<html:text property="clientNameEN" maxlength="100" styleClass="searchOT_clientNameEN" /> 
								</li>
								</logic:notEqual>
						</logic:notEqual>
						<logic:equal name="role" value="2">
							<logic:equal name="isHRFunction" value="1">
								<li>
									<label><bean:message bundle="public" key="public.contract2.id" /></label>
									<html:text property="contractId" maxlength="10" styleClass="searchOT_contractId" /> 
								</li> 
							</logic:equal>
						</logic:equal>
						
						<logic:equal name="accountId" value="100056">
							<li>
							<label><bean:message bundle="business" key="business.ot.special.overtime" /></label> 
								<html:select property="specialOT" styleClass="searchOT_specialOT">
								<html:optionsCollection property="specialOTs" value="mappingId" label="mappingValue" />
							</html:select>
							</li> 
						</logic:equal>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchOT_status">
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
				<kan:auth right="new" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('otHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="submit" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="function" id="btnSubmit" name="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />
				</kan:auth>
				<kan:auth right="confirm" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="function" id="btnConfirm" name="btnConfirm" value="<bean:message bundle="public" key="button.confirm" />" />
				</kan:auth>
				<kan:auth right="delete" action="<%=OTHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('searchOT_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="export" action="<%=OTHeaderAction.accessAction%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchOT_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/attendance/ot/table/listOTTable.jsp" flush="true"/> 
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
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Overtime').addClass('selected');
		<logic:equal name="role" value="5">
			$('#menu_attendance_Overtime').addClass('current');
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
				if(confirm('<bean:message bundle="public" key="popup.confirm.submit.records" />')){
					$('.searchOT_form').attr('action', 'otHeaderAction.do?proc=submit_objects');
					submitForm('searchOT_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.searchOT_form').attr('action', 'otHeaderAction.do?proc=list_object');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.select.submit.records" />');
			}
		});
		
		// 批量确认
		$('#btnConfirm').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if( getFlag( "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=3&objectName=ot&selectedIds=" + $('#selectedIds').val() ) == false ){
					if(!confirm('<bean:message bundle="public" key="popup.ot.not.entry.ts" />')){
						return;
					}
				}
				if(confirm('<bean:message bundle="public" key="popup.confirm.confirm.records" />')){
					$('.searchOT_form').attr('action', 'otHeaderAction.do?proc=submit_objects');
					submitForm('searchOT_form', "confirmObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.searchOT_form').attr('action', 'otHeaderAction.do?proc=list_object');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.select.confirm.records" />');
			}
		});
	})(jQuery);
	
	function resetForm() {
	    $('.searchOT_employeeId').val('');
	    $('.searchOT_employeeNo').val('');
	    $('.searchOT_employeeNameZH').val('');
	    $('.searchOT_employeeNameEN').val('');
	    $('.searchOT_certificateNumber').val('');
	    $('.searchOT_itemId').val('0');
	    $('.searchOT_estimateStartDate').val('');
	    $('.searchOT_estimateEndDate').val('');
	    $('.searchOT_clientId').val('');
	    $('.searchOT_clientNameZH').val('');
	    $('.searchOT_clientNameEN').val('');
	    $('.searchOT_status').val('0');
	};
	
	//modify by siuvan 2014-08-28
	function submit_object( id, subAction, employeeId, contractId, startDate ){
		
		if( subAction == 'confirmObject' && getFlag( "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=2&employeeId=" + employeeId + "&contractId=" + contractId + "&startDate=" + startDate ) == false ){
			if(!confirm('<bean:message bundle="public" key="popup.ot.not.entry.ts" />')){
				return;
			}
		}
		
		$('.searchOT_form').attr('action', 'otHeaderAction.do?proc=submit_object&id=' + id);
		submitForm('searchOT_form', subAction, null, null, null, 'tableWrapper');
	};
	
	// 确认时，若改月考勤表非新建、退回状态则给出提示。
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
		submitForm('searchOT_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>
