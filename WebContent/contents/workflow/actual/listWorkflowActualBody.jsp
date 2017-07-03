<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="workflowActual-information">
		<div class="head">
			<label><bean:message bundle="workflow" key="workflow.actual.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<html:form action="workflowActualAction.do?proc=list_object" styleClass="listworkflowActual_form">
				<div class="top">
					<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listworkflowActual_form', 'searchObject', null, null, null, null);" />
					<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
				</div>
				<fieldset>
					<ol>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.name.cn" /></label> 
							<html:text property="nameZH" maxlength="25" styleClass="search_workflowActual_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="workflow" key="workflow.actual.name.en" /></label> 
							<html:text property="nameEN" maxlength="25" styleClass="search_workflowActual_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_workflowActual_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<!-- <li>
							<label>我的审批状态</label> 
							<html:select property="actualStepStatus" styleClass="search_workflowActual_actualStepStatus">
								<html:optionsCollection property="actualStepStatuses" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						-->
					</ol>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="workflowActualHolder" property="sortColumn" />" />
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="workflowActualHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="workflowActualHolder" property="page" />" /> 
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="workflowActualHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
				</fieldset>
			</html:form>
		</div>
	</div>

	<div class="box noHeader" id="search-results">
		<div class="inner">
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- jsp:include 动态包含table对应的jsp文件 -->
				<jsp:include page="/contents/workflow/actual/table/listWorkflowActualTable.jsp"></jsp:include>
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
		$('#menu_workflow_Search').addClass('selected');
		$('#searchDiv').hide();
		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.search_workflowActual_nameZH').val('');
		$('.search_workflowActual_nameEN').val('');
		$('.search_workflowActual_status').val('');
	};
	
	function pageForward() {
		var value = Number($('#forwardPage_render').val());
		// 如果页数无效自动跳转到第一页
		if(/[^0-9]+/.test(value)){
			value = 1;
		}
		var forwardPage_render = Number($('#forwardPage_render').val()) - 1;
		submitForm('listworkflowActual_form', null, forwardPage_render, null, null, 'tableWrapper');
	};
</script>