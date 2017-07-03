<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.IndustryTypeAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	final PagedListHolder industryTypeHolder = (PagedListHolder) request.getAttribute("industryTypeHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="industryType-information">
		<div class="head">
			<label>行业类型</label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div class="inner" id="searchDiv">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listIndustryType_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="industryTypeAction.do?proc=list_object" styleClass="listIndustryType_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="industryTypeHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="industryTypeHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="industryTypeHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="industryTypeHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>行业类型名称（中文） </label> 
							<html:text property="nameZH" maxlength="20" styleClass="searchIndustryType_nameZH" /> 
							<input type="hidden" name="industryTypeId" id="industryTypeId" value="" />
						</li>
						<li>
							<label>行业类型名称（英文） </label> 
							<html:text property="nameEN" maxlength="20" styleClass="searchIndustryType_nameEN" /> 
							<input type="hidden" name="industryTypeId" id="industryTypeId" value="" />
						</li>
						<li>
							<label>状态</label>
							<html:select property="status" styleClass="searchIndustryType_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- IndustryType-information -->
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
				<kan:auth right="new" action="<%=IndustryTypeAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('industryTypeAction.do?proc=to_objectNew');" />
				</kan:auth>

				<kan:auth right="delete" action="<%=IndustryTypeAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listIndustryType_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/management/industryType/table/listIndustryTypeTable.jsp" flush="true"/> 
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
		$('#menu_client_IndustryType').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm() {
		$('.searchIndustryType_nameZH').val('');
		$('.searchIndustryType_nameEN').val('');
		$('.searchIndustryType_status').val('0');
	};
</script>
