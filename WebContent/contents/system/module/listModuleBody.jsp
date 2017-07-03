<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder moduleHolder = (PagedListHolder) request.getAttribute("moduleHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="module-information">
		<div class="head">
			<label>系统模块</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmodule_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="moduleAction.do?proc=list_object" styleClass="listmodule_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="moduleHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="moduleHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="moduleHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="moduleHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>模块名称 （中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchmodule_namezh" /> 
							<input type="hidden" name="moduleId" id="moduleId" value="" />
						</li>
						<li>
							<label>模块名称 （英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchmodule_nameen" /> 
						</li>
						<li>
							<label>模块ID</label> 
							<html:text property="moduleName" maxlength="50" styleClass="searchmodule_moduleName" /> 
						</li>
						<li>
							<label>访问链接</label> 
							<html:text property="accessAction" maxlength="100" styleClass="searchmodule_accessAction" /> 
						</li>
						<li>
							<label>模块类型</label> 
							<html:select property="moduleType" styleClass="searchmodule_moduleType">
								<html:optionsCollection property="moduleTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchmodule_status">
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
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('moduleAction.do?proc=to_objectNew');" /> 
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmodule_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/module/table/listModuleTable.jsp" flush="true"/>
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
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_Module').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.searchmodule_namezh').val('');
		$('.searchmodule_nameen').val('');
		$('.searchmodule_moduleName').val('');
		$('.searchmodule_accessAction').val('');
		$('.searchmodule_moduleType').val('0');
		$('.searchmodule_status').val('0');
	};
</script>