<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.management.LaborContractTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="laborContractTemplate-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.employee.contract.template.search.title" /></label>
		</div>
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div class="inner" id="searchDiv">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listLaborContractTemplate_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="laborContractTemplateAction.do?proc=list_object" styleClass="listLaborContractTemplate_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="laborContractTemplateHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="laborContractTemplateHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="laborContractTemplateHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="laborContractTemplateHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.employee.contract.template.name.cn" /></label> 
							<html:text property="nameZH" maxlength="20" styleClass="searchLaborContractTemplate_nameZH" /> 
							<input type="hidden" name="laborContractTemplateId" id="laborContractTemplateId" value="" />
						</li>
						<li>
							<label><bean:message bundle="management" key="management.employee.contract.template.name.en" /></label> 
							<html:text property="nameEN" maxlength="20" styleClass="searchLaborContractTemplate_nameEN" /> 
							<input type="hidden" name="laborContractTemplateId" id="laborContractTemplateId" value="" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity" /></label>
							<html:select property="entityId" styleClass="searchLaborContractTemplate_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="security" key="security.business.type" /></label>
							<html:select property="businessTypeId" styleClass="searchLaborContractTemplate_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label>
							<html:select property="status" styleClass="searchLaborContractTemplate_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- LaborContractTemplate-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=LaborContractTemplateAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('laborContractTemplateAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=LaborContractTemplateAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listLaborContractTemplate_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->
				<jsp:include page="/contents/management/laborContractTemplate/table/listLaborContractTemplateTableInHouse.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p></p>
			</div>
			<!-- List Component -->
		</div>
		<!-- inner -->
	</div>
	<!-- search-results -->
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_LaborContractTemplate').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm() {
		$('.searchLaborContractTemplate_nameZH').val('');
		$('.searchLaborContractTemplate_nameEN').val('');
		$('.searchLaborContractTemplate_contractTypeId').val('0');
		$('.searchLaborContractTemplate_entityId').val('0');
		$('.searchLaborContractTemplate_businessTypeId').val('0');
		$('.searchLaborContractTemplate_status').val('0');
		$('.searchLaborContractTemplate_description').val('');
	};
</script>
