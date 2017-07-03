<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="workflowModule-information">
		<div class="head">
			<label>工作流</label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listworkflowModule_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="workflowModuleAction.do?proc=list_object" styleClass="listworkflowModule_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowModuleHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowModuleHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="workflowModuleHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="workflowModuleHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>工作流名称（中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="search_workflowModule_nameZH" /> 
						</li>
						<li>
							<label>工作流名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="search_workflowModule_nameEN" /> 
						</li>
						<li>
							<label>系统模块（标识）</label>
							<html:text property="moduleTitle" styleClass="search_workflowModule_moduleTitle" />
							<html:hidden property="moduleId" styleClass="search_workflowModule_moduleId" />
						</li>
						<li>
							<label>适用范围</label> 
							<html:select property="scopeType" styleClass="search_workflowModule_scopeType">
								<html:optionsCollection property="scopeTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_workflowModule_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- User-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('workflowModuleAction.do?proc=to_objectNew');" /> 
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listworkflowModule_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- jsp:include 动态包含table对应的jsp文件 -->
				<jsp:include page="/contents/system/workflow/table/listWorkflowModuleTable.jsp" flush="true"/>
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
		$('#menu_workflow_modules').addClass('current');
		$('#menu_workflow_module').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
		
		// Use the common thinking
		kanThinking_column('search_workflowModule_moduleTitle', 'search_workflowModule_moduleId', 'moduleAction.do?proc=list_object_json');
	})(jQuery);

	function resetForm(){
		$('.search_workflowModule_nameZH').val('');
		$('.search_workflowModule_nameEN').val('');
		$('.search_workflowModule_moduleName').val('');
		$('.search_workflowModule_moduleId').val('');
		$('.search_workflowModule_status').val('');
	};
</script>