<%@ page import="com.kan.hro.web.actions.biz.attendance.RecordAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final boolean IN_HOUSE = request.getAttribute( "role" ).equals( "2" ) ? true : false;
%>

<div id="content">
	<div class="box searchForm toggableForm" id="record-information">
		<div class="head">
			<label>打卡记录信息</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listRecord_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="recordAction.do?proc=list_object" styleClass="listRecord_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="recordHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="recordHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="recordHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="recordHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />	
				<fieldset>					
					<ol class="auto">
						<li>
							<label><%=IN_HOUSE ? "员工" : "雇员" %>ID</label> 
							<html:text property="employeeId" maxlength="100" styleClass="searchRecord_employeeId" /> 
						</li>
						<li>
							<label><%=IN_HOUSE ? "员工" : "雇员" %>姓名（中文）</label> 
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchRecord_employeeNameZH" /> 
						</li>
						<li>
							<label><%=IN_HOUSE ? "员工" : "雇员" %>姓名（英文）</label> 
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchRecord_employeeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchrRecord_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>				
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<kan:auth right="delete" action="<%=RecordAction.ACCESS_ACTION %>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listRecord_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<img style="float:right" src="images/import.png" onclick="popupExcelImport();" title="考勤加班导入">
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<jsp:include page="/contents/business/attendance/record/table/listRecordTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<div id="popupWrapper">
	<jsp:include page="/popup/importRecord.jsp" >
		<jsp:param value="true" name="closeRefesh"/>
	</jsp:include>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_attendance_Modules').addClass('current');	
		$('#menu_attendance_Import').addClass('selected');
		$('#menu_attendance_SignRecord').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchRecord_employeeId').val('');
		$('.searchRecord_employeeNameZH').val('');
		$('.searchRecord_employeeNameEN').val('');
		$('.searchRecord_status').val('0');
	};
</script>
