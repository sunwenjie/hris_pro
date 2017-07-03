<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.workflow.WorkflowDefineAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="workflowDefine-information">
		<div class="head">
			<label><bean:message bundle="workflow" key="workflow.define.title.setting" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listworkflowDefine_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="workflowDefineAction.do?proc=list_object" styleClass="listworkflowDefine_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowDefineHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowDefineHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="workflowDefineHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="workflowDefineHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.name.cn" /></label> 
							<html:text property="nameZH" maxlength="25" styleClass="search_workflowDefine_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.name.en" /></label> 
							<html:text property="nameEN" maxlength="25" styleClass="search_workflowDefine_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.define.scope" /></label> 
							<html:select property="scope" styleClass="workflowDefine_scope">
								<html:optionsCollection property="scopes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_workflowDefine_status">
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
				<logic:present name="MESSAGE">
					<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
						<bean:write name="MESSAGE" />
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=WorkflowDefineAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('workflowDefineAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=WorkflowDefineAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listworkflowDefine_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- jsp:include ��̬����table��Ӧ��jsp�ļ� -->
				<jsp:include page="/contents/workflow/define/table/listWorkflowDefineTable.jsp"></jsp:include>
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
		$('#menu_workflow_Modules').addClass('current');
		$('#menu_workflow_Configuration').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.search_workflowDefine_nameZH').val('');
		$('.search_workflowDefine_nameEN').val('');
		$('.search_workflowDefine_status').val('');
	};
</script>