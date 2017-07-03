<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder employeeStatusHolder = (PagedListHolder)request.getAttribute("employeeStatusHolder");
%>

<div id="content">
	<!-- EmployeeStatus-information -->
	<div class="box searchForm toggableForm" id="contract-information">
		<div class="head">
			<label>
				<logic:equal value="2" name="role">员工状态信息</logic:equal>
				<logic:equal value="1" name="role">雇佣状态信息</logic:equal>
			</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listemployeeStatus_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="employeeStatusAction.do?proc=list_object" styleClass="listemployeeStatus_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeStatusHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeStatusHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="employeeStatusHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeStatusHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<fieldset>					
						<ol class="auto">
						<li>
							<label>
								<logic:equal value="2" name="role">员工状态名（中文）</logic:equal>
								<logic:equal value="1" name="role">雇员状态名（中文）</logic:equal>
							</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchemployeeStatus_nameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal value="2" name="role">员工状态名（英文）</logic:equal>
								<logic:equal value="1" name="role">雇员状态名（英文）</logic:equal>
							</label>
							<html:text property="nameEN" maxlength="100" styleClass="searchemployeeStatus_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchemployeeStatus_status">
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
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">修改成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>		
			<div class="top">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('employeeStatusAction.do?proc=to_objectNew');" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listemployeeStatus_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include EmployeeStatus jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/management/employeeStatus/table/listEmployeeStatusTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_EmploymentStatus').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchemployeeStatus_nameZH').val('');
		$('.searchemployeeStatus_nameEN').val('');
		$('.searchemployeeStatus_status').val('0');
	};
</script>
