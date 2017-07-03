<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.ReportHeaderAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="reportHeader-information">
		<div class="head">
			<label><bean:message bundle="define" key="define.report.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listreportHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="reportHeaderAction.do?proc=list_object" styleClass="listreportHeader_form">
				<input type="hidden" name="reportHeaderId" id="reportHeaderId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="reportHeaderPagedListHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="reportHeaderPagedListHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="reportHeaderPagedListHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="reportHeaderPagedListHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="define" key="define.table" /></label>
							<html:select property="tableId" styleClass="searhreportHeader_tableId">
								<html:optionsCollection property="tables" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="define" key="define.report.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchreportHeader_nameZH" /> 							
						</li>
						<li>
							<label><bean:message bundle="define" key="define.report.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchreportHeader_nameEN" /> 
						</li>	
						<li>
							<label><bean:message bundle="define" key="define.list.header.use.pagination" /></label> 
							<html:select property="usePagination" styleClass="searchreportHeader_usePagination">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>					
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchreportHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- ReportHeader - information -->

	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:present name="addSuccess">保存成功！</logic:present>
						<logic:present name="updateSuccess">编辑成功！</logic:present>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>	
			<div class="top">
				<kan:auth right="new" action="<%=ReportHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('reportHeaderAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				
				<kan:auth right="delete" action="<%=ReportHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listreportHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />					
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<jsp:include page="/contents/define/report/table/listReportHeaderTable.jsp" flush="true"></jsp:include>
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
		$('#menu_define_Report').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
		
	})(jQuery);
	// 重置按钮事件
	function resetForm(){		
		$('.searhreportHeader_tableId').val('0');
		$('.searchreportHeader_nameZH').val('');
		$('.searchreportHeader_nameEN').val('');
		$('.searchreportHeader_usePagination').val('0');
		$('.searchreportHeader_status').val('0');
	};
</script>