<%@page import="com.kan.base.web.actions.management.YERRRuleAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.LanguageAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="YERRRule-information">
		<div class="head">
			<label>工资涨幅规则</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listYERRRole_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="yerrRuleAction.do?proc=list_object" styleClass="listYERRRole_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="yerrRuleHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="yerrRuleHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="yerrRuleHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="yerrRuleHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />	
					<fieldset>					
						<ol class="auto">
							<li>
								<label>绩效评分</label>
								<html:select property="rating" styleClass="searchYERRRule_rating">
									<html:optionsCollection property="ratings" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
							<li>
								<label><bean:message bundle="public" key="public.status" /></label> 
								<html:select property="status" styleClass="searchlanguage_status">
									<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
								</html:select>
							</li>
						</ol>				
				</fieldset>
			</html:form>
		</div>
	</div>
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
				<kan:auth right="new" action="<%=YERRRuleAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('yerrRuleAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=YERRRuleAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listYERRRole_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<jsp:include page="/contents/management/yerrRule/table/listYERRRuleTable.jsp" flush="true"/> 
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
		$('#menu_performance_Modules').addClass('current');	
		$('#menu_performance_Configuration').addClass('selected');
		$('#menu_performance_TTC').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('form ol li input[type="text"]:visible').val('');
		$('form ol li select:visible').val('0');
	};
</script>
