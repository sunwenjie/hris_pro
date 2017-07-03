<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ColumnAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="column-information">
		<div class="head">
			<label><bean:message bundle="define" key="define.column.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listcolumn_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="columnAction.do?proc=list_object" styleClass="listcolumn_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="columnHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="columnHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="columnHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="columnHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					 <ol class="auto">
					 	<li>
							<label><bean:message bundle="define" key="define.table" /></label> 
							<html:select property="tableId" styleClass="searchColumn_tableId">
								<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
							</html:select>																
						</li>
					 	<li>
							<label><bean:message bundle="define" key="define.column.name.db" /></label> 
							<html:text property="nameDB" maxlength="100" styleClass="searchcolumn_nameDB" /> 
							<input type="hidden" name="defColumnId" id="defTableId" value="" />
						</li>	
						 <li>
							<label><bean:message bundle="define" key="define.column.name.sys" /></label> 
							<html:text property="nameSys" maxlength="100" styleClass="searchcolumn_nameSys" /> 							
						</li>	
						<li>
							<label><bean:message bundle="define" key="define.column.group" /></label>
							<html:select property="groupId" styleClass="searchcolumn_groupId">
								<html:optionsCollection property="columnGroups" value="mappingId" label="mappingValue" />
							</html:select>		
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.is.required" /></label>
							<html:select property="isRequired" styleClass="searchcolumn_isRequired">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>		
						</li>
						<li>
							<label><bean:message bundle="define" key="define.column.input.type" /></label>
							<html:select property="inputType" styleClass="searchcolumn_inputType">
								<html:optionsCollection property="inputTypies" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>	
						<li>
							<label><bean:message bundle="define" key="define.column.value.type" /></label>
							<html:select property="valueType" styleClass="searchcolumn_valueType">
								<html:optionsCollection property="valueTypies" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>	
						<li>
							<label><bean:message bundle="define" key="define.column.validate.type" /></label>
							<html:select property="validateType" styleClass="searchcolumn_validateType">
								<html:optionsCollection property="validateTypies" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>	
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchcolumn_status">
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
				<kan:auth right="new" action="<%=ColumnAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('columnAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				<kan:auth right="delete" action="<%=ColumnAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcolumn_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/define/column/table/listColumnTable.jsp" flush="true"/> 
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
		$('#menu_define_Column').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮点击函数
	function resetForm(){
		$('.searchColumn_tableId').val('0');
		$('.searchcolumn_nameDB').val('');
		$('.searchcolumn_nameSys').val('');
		$('.searchcolumn_groupId').val('0');
		$('.searchcolumn_isRequired').val('0');
		$('.searchcolumn_inputType').val('0');	
		$('.searchcolumn_valueType').val('0');	
		$('.searchcolumn_validateType').val('0');	
		$('.searchcolumn_status').val('0');
	};
</script>