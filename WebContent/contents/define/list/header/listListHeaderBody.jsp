<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ListHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="listHeader-information">
		<div class="head">
			<label><bean:message bundle="define" key="define.list.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listListHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="listHeaderAction.do?proc=list_object" styleClass="listListHeader_form">
				<input type="hidden" name="listHeaderId" id="listHeaderId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="listHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="listHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="listHeaderHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="listHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.table" /></label>
							<html:select property="tableId" styleClass="searchListHeader_tableId">
								<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
							</html:select>		
						</li>
						<li>
							<label><bean:message bundle="define" key="define.list.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchListHeader_nameZH" /> 
							
						</li>
						<li>
							<label><bean:message bundle="define" key="define.list.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchListHeader_nameEN" /> 
						</li>						
						<li>
							<label><bean:message bundle="define" key="define.list.header.use.pagination" /></label> 
							<html:select property="usePagination" styleClass="searchListHeader_usePagination">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 	
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchListHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- ListHeader - information -->
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
				<kan:auth right="new" action="<%=ListHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('listHeaderAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				<kan:auth right="delete" action="<%=ListHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listListHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include listHeader jsp 包含table对应的jsp文件 -->  
				<jsp:include page="/contents/define/list/header/table/listListHeaderTable.jsp" flush="true"/> 
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

<!-- 快速设置列表顺序popup -->
<div id="popupWrapper">
</div>
<script type="text/javascript">
	(function($) {
		$('#menu_define_Modules').addClass('current');
		$('#menu_define_List').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function showQuickColumnIndexPopup( listHeaderId ){
		loadHtml('#popupWrapper', 'listHeaderAction.do?proc=load_popup_html&listHeaderId=' + listHeaderId, false, "$('#indexQuickModuleId').removeClass('hide');$('#shield').show();" );
	};
	
	// 重置按钮事件
	function resetForm(){
		$('.searchListHeader_tableId').val(0);
		$('.searchListHeader_searchId').val(0);
		$('.searchListHeader_nameZH').val('');
		$('.searchListHeader_nameEN').val('');
		$('.searchListHeader_usePagination').val(0);
		$('.searchListHeader_status').val('0');
	};
</script>