<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ColumnGroupAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="columnGroup-information">
		<div class="head">
			<label><bean:message bundle="define" key="define.column.group.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listcolumnGroup_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="columnGroupAction.do?proc=list_object" styleClass="listcolumnGroup_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="columnGroupHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="columnGroupHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="columnGroupHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="columnGroupHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.column.group.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchcolumnGroup_nameZH" /> 
							<input type="hidden" name="defGroupId" id="defGroupId" value="" />
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.group.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchcolumnGroup_nameEN" /> 
						</li>		
						<li>
							<label><bean:message bundle="define" key="define.column.group.is.display" />  <a title="<bean:message bundle="define" key="define.column.group.is.display.tips" />"><img src="images/tips.png" width="14" height="14px" /></a></label>
							<html:select property="isDisplayed" styleClass="searchcolumnGroup_isDisplayed">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchcolumnGroup_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- Column-information -->

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
				<kan:auth right="new" action="<%=ColumnGroupAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('columnGroupAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				<kan:auth right="delete" action="<%=ColumnGroupAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcolumnGroup_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/define/columnGroup/table/listColumnGroupTable.jsp" flush="true"/> 
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
		$('#menu_define_ColumnGroup').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	// 重置按钮事件
	function resetForm(){		
		$('.searchcolumnGroup_nameZH').val('');
		$('.searchcolumnGroup_nameEN').val('');
		$('.searchcolumnGroup_useName').val('0');
		$('.searchcolumnGroup_useBorder').val('0');
		$('.searchcolumnGroup_isDisplayed').val('0');
		$('.searchcolumnGroup_isDisplayed').val('0');
		$('.searchcolumnGroup_description').val('');
		$('.searchcolumnGroup_status').val('0');
	};
</script>