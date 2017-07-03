<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.ShiftHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="shiftHeader-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.shift.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listShiftHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="shiftHeaderAction.do?proc=list_object" styleClass="listShiftHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="shiftHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="shiftHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="shiftHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="shiftHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.shift.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchShiftHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.shift.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchShiftHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.shift.header.type" /></label> 
							<html:select property="shiftType" styleClass="searchShiftHeader_shiftType">
								<html:optionsCollection property="shiftTypies" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchShiftHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- ShiftHeader-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
					<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">����ɹ���</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">�༭�ɹ���</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=ShiftHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('shiftHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=ShiftHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listShiftHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include ShiftHeader JSP ����table��Ӧ��JSP�ļ� -->  
				<jsp:include page="/contents/management/shift/header/table/listShiftHeaderTable.jsp" flush="true"/> 
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
		if('<bean:write name="accountId"/>' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_WorkShift').addClass('selected');
		}else{
			$('#menu_attendance_Modules').addClass('current');
			$('#menu_attendance_Configuration').addClass('selected');
			$('#menu_attendance_Shift').addClass('selected');	
		}
		
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// ��������
	function resetForm(){
		$('.searchShiftHeader_nameZH').val('');
		$('.searchShiftHeader_nameEN').val('');
		$('.searchShiftHeader_shiftType').val('0');
		$('.searchShiftHeader_status').val('0');
	};
</script>
