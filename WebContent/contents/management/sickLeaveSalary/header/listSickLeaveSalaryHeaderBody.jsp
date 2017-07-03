<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.SickLeaveSalaryHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="SickLeaveSalaryHeader-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.sick.leave.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listSickLeaveSalaryHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="sickLeaveSalaryHeaderAction.do?proc=list_object" styleClass="listSickLeaveSalaryHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="sickLeaveSalaryHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="sickLeaveSalaryHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="sickLeaveSalaryHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="sickLeaveSalaryHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.sick.leave.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchSickLeaveSalaryHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sick.leave.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchSickLeaveSalaryHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sick.leave.header.item" /></label> 
							<html:select property="itemId" styleClass="searchSickLeaveSalaryHeader_itemId">
								<html:optionsCollection property="itemIds" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sick.leave.header.base.on" /></label> 
							<html:select property="baseOn" styleClass="searchSickLeaveSalaryHeader_baseOn">
								<html:optionsCollection property="baseOns" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSickLeaveSalaryHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- SickLeaveSalaryHeader-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
					<logic:present name="SUCCESS_MESSAGE_HEADER">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=SickLeaveSalaryHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('sickLeaveSalaryHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=SickLeaveSalaryHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listSickLeaveSalaryHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			 <div id="tableWrapper">
				<!-- Include SickLeaveSalaryHeader JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/management/sickLeaveSalary/header/table/listSickLeaveSalaryHeaderTable.jsp" flush="true"/> 
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
		$('#menu_salary_Modules').addClass('current');			
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_Sickleave').addClass('selected');
		
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchSickLeaveSalaryHeader_nameZH').val('');
		$('.searchSickLeaveSalaryHeader_nameEN').val('');
		$('.searchSickLeaveSalaryHeader_status').val('0');
		$('.searchSickLeaveSalaryHeader_itemId').val('0');
		$('.searchSickLeaveSalaryHeader_baseOn').val('0');
	};
</script>
