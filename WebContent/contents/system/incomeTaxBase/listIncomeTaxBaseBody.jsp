<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="incomeTaxBase-information">
		<div class="head">
			<label>个税起征信息</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listIncomeTaxBase_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="incomeTaxBaseAction.do?proc=list_object" styleClass="listIncomeTaxBase_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="incomeTaxBaseHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="incomeTaxBaseHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="incomeTaxBaseHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="incomeTaxBaseHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>个税起征名称（中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchIncomeTaxBase_nameZH" /> 
						</li>
						<li>
							<label>个税起征名称（英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchIncomeTaxBase_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchIncomeTaxBase_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- IncomeTaxBase-information -->
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
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('incomeTaxBaseAction.do?proc=to_objectNew');" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listIncomeTaxBase_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include IncomeTaxBase JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/system/incomeTaxBase/table/listIncomeTaxBaseTable.jsp" flush="true"/> 
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
		$('#menu_system_IncomeTax').addClass('selected');
		$('#menu_system_IncomeTaxBase').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
		
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchIncomeTaxBase_nameZH').val('');
		$('.searchIncomeTaxBase_nameEN').val('');
		$('.searchIncomeTaxBase_status').val('0');
	};
</script>
