<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.SearchHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="SearchHeader-information">
		<div class="head">
			<label><bean:message bundle="define" key="define.search.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner" >
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listSearchHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="searchHeaderAction.do?proc=list_object" styleClass="listSearchHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="searchHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="searchHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="searchHeaderHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="searchHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.search.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchSearchHeader_nameZH" /> 
							<input type="hidden" name="seaderHeaderId" id="seaderHeaderId" value="" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.search.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchSearchHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="define" key="define.table" /></label>
							<html:select property="tableId" styleClass="searchSearchHeader_tableId">
								<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
							</html:select>
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSearchHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- SearchHeader - information -->

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
				<kan:auth right="new" action="<%=SearchHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('searchHeaderAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				<kan:auth right="delete" action="<%=SearchHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listSearchHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/define/search/header/table/listSearchHeaderTable.jsp" flush="true"/> 
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
		$('#menu_define_Modules').addClass('current');
		$('#menu_define_Search').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮事件
	function resetForm(){	
		$('.searchSearchHeader_tableId').val('0');
		$('.searchSearchHeader_nameZH').val('');
		$('.searchSearchHeader_nameEN').val('');
		$('.searchSearchHeader_status').val('0');
	};
</script>