<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.BusinessContractTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	PagedListHolder businessContractTemplateHolder = (PagedListHolder) request.getAttribute("businessContractTemplateHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="businessContractTemplate-information">
		<div class="head">
			<label>商务合同模板</label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div class="inner" id="searchDiv">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listBusinessContractTemplate_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="businessContractTemplateAction.do?proc=list_object" styleClass="listBusinessContractTemplate_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="businessContractTemplateHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="businessContractTemplateHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="businessContractTemplateHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="businessContractTemplateHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>模板名称（中文）</label> 
							<html:text property="nameZH" maxlength="20" styleClass="searchBusinessContractTemplate_nameZH" /> 
							<input type="hidden" name="businessContractTemplateId" id="businessContractTemplateId" value="" />
						</li>
						<li>
							<label>模板名称（英文名）</label> 
							<html:text property="nameEN" maxlength="20" styleClass="searchBusinessContractTemplate_nameEN" /> 
							<input type="hidden" name="businessContractTemplateId" id="businessContractTemplateId" value="" />
						</li>
						<li>
							<label>法务实体</label>
							<html:select property="entityId" styleClass="searchBusinessContractTemplate_entityId">
								<html:optionsCollection property="entities" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>业务类型</label>
							<html:select property="businessTypeId" styleClass="searchBusinessContractTemplate_businessTypeId">
								<html:optionsCollection property="businessTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>状态</label>
							<html:select property="status" styleClass="searchBusinessContractTemplate_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- BusinessContractTemplate-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<logic:present name="MESSAGE">
						<div class="message <bean:write name="MESSAGE_CLASS" /> fadable">
							<bean:write name="MESSAGE" />
			    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
						</div>
					</logic:present>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=BusinessContractTemplateAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('businessContractTemplateAction.do?proc=to_objectNew');" />
				</kan:auth>

				<kan:auth right="delete" action="<%=BusinessContractTemplateAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listBusinessContractTemplate_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含table对应的jsp文件 -->  
				<jsp:include page="/contents/management/businessContractTemplate/table/listBusinessContractTemplateTable.jsp" flush="true"/> 
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
		$('#menu_client_Modules').addClass('current');	
		$('#menu_client_Configuration').addClass('selected');
		$('#menu_client_BusinessContractTemplate').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm() {
		$('.searchBusinessContractTemplate_nameZH').val('');
		$('.searchBusinessContractTemplate_nameEN').val('');
		$('.searchBusinessContractTemplate_status').val('0');
		$('.searchBusinessContractTemplate_entityId').val('0');
		$('.searchBusinessContractTemplate_businessTypeId').val('0');
	};
</script>
