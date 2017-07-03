<%@page import="com.kan.base.web.action.BaseAction"%>
<%@page import="com.kan.hro.web.actions.biz.travel.TravelAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<script type="text/javascript" src="js/kan.popup.workflow.js"></script>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>差旅信息</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchTravel_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="travelAction.do?proc=list_object" method="post" styleClass="searchTravel_form">
       			<input type="hidden" name="page" id="page" value="<bean:write name="travelHolder" property="page" />" />
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="travelHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="travelHolder" property="sortOrder" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="travelHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<!-- Hr Function Search Condition Start -->
						
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
								</label>
								<html:text property="employeeId" maxlength="10" styleClass="searchTravel_employeeId" /> 
							</li>
							
							<li>
								<label>
									<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
									<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>
								</label>
								<html:text property="nameZH" maxlength="100" styleClass="searchTravel_employeeNameZH" /> 
							</li>
						
			
						<li>
							<label><bean:message bundle="public" key="public.start.time" /></label> 
							<html:text property="startDate" maxlength="20" readonly="true" styleClass="searchTravel_startDate" styleId="startDate" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.end.time" /></label> 
							<html:text property="endDate" maxlength="20" readonly="true" styleClass="searchTravel_endDate" styleId="endDate" /> 
						</li>
						
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchTravel_status">
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
	
				<kan:auth right="new" action="<%=TravelAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('travelAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="submit" action="<%=TravelAction.accessAction%>">
					<input type="button" class="function" id="btnSubmit" name="btnSubmit" value="<bean:message bundle="public" key="button.submit" />" />				
				</kan:auth>
				
				<kan:auth right="delete" action="<%=TravelAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('searchTravel_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<kan:auth right="export" action="<%=TravelAction.accessAction%>">
					<a id="exportExcel" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="linkForm( 'searchTravel_form', 'downloadObjects', null, null );"><img src="images/appicons/excel_16.png" /></a> 
				</kan:auth>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/travel/table/listTravelTable.jsp" flush="true"/> 
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

		
		$('#startDate').addClass('Wdate');
		$('#startDate').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd HH:mm',
	    		 maxDate:'#F{$dp.$D(\'endDate\')}'
	    	 });
		});
		$('#endDate').addClass('Wdate');
		$('#endDate').focus(function(){
			 WdatePicker({
	    		 dateFmt:'yyyy-MM-dd HH:mm',
	    		 minDate:'#F{$dp.$D(\'startDate\')}'
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
					$('.searchTravel_form').attr('action', 'travelAction.do?proc=submit_objects');
					submitForm('searchTravel_form', "submitObjects", null, null, null, 'tableWrapper');
					$('#selectedIds').val('');
					$('.searchTravel_form').attr('action', 'travelAction.do?proc=list_object');
				}
			}else{
				alert('<bean:message bundle="public" key="popup.select.submit.records" />');
			}
		});
		
	})(jQuery);
	
	function resetForm() {
	    $('.searchTravel_employeeId').val('');
	    $('.searchTravel_nameZH').val('');
	    $('.searchTravel_nameEN').val('');
	    $('.searchTravel_itemId').val('0');
	    $('.searchTravel_startDate').val('');
	    $('.searchTravel_endDate').val('');
	    $('.searchTravel_status').val('0');
	};
	
	//modify by siuvan 2014-08-28
	function submit_object( id, employeeId, contractId, startDate ){
		
		if( getFlag( "timesheetHeaderAction.do?proc=existAvailableTimesheet&submitType=2&employeeId=" + employeeId + "&contractId=" + contractId + "&startDate=" + startDate ) == false ){
			if(!confirm('<bean:message bundle="public" key="popup.leave.not.entry.ts" />')){
				return;
			}
		}
		
		$('.searchTravel_form').attr('action', 'travelAction.do?proc=submit_object&id=' + id);
		submitForm('searchTravel_form', 'submitObject', null, null, null, 'tableWrapper');
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
		submitForm('searchTravel_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>
