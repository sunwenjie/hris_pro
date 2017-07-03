<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.message.MessageTemplateAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>
<%
	PagedListHolder messageTemplateHolder = (PagedListHolder) request.getAttribute("messageTemplateHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="messageTemplate-information">
		<div class="head">
			<label><bean:message bundle="message" key="message.template.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" class="search" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listmessageTemplate_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="messageTemplateAction.do?proc=list_object" styleClass="listmessageTemplate_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="messageTemplateHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="messageTemplateHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="messageTemplateHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="messageTemplateHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="message" key="message.template.name.zh" /></label> 
							<html:text property="nameZH" maxlength="25" styleClass="search_messageTemplate_nameZH" /> 
							<input type="hidden" name="accountId" id="accountId" value="" />
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.name.en" /></label> 
							<html:text property="nameEN" maxlength="25" styleClass="search_messageTemplate_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.type" /></label> 
							<html:select property="templateType" styleClass="search_messageTemplate_templateType">
								<html:optionsCollection property="templateTypes" value="mappingId" label="mappingValue" />
							</html:select> 
						</li>
						<li>
							<label><bean:message bundle="message" key="message.template.content.type" /></label> 
							<html:select property="contentType" styleClass="search_messageTemplate_contentType">
								<html:optionsCollection property="contentTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="search_messageTemplate_status">
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
				<kan:auth right="new" action="<%=MessageTemplateAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('messageTemplateAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=MessageTemplateAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listmessageTemplate_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/message/template/table/listMessageTemplateTable.jsp" flush="true"/> 
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
		$('#menu_message_Modules').addClass('current');
		$('#menu_message_Template').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.search_messageTemplate_nameZH').val('');
		$('.search_messageTemplate_nameEN').val('');
		$('.search_messageTemplate_templateType').val('0');
		$('.search_messageTemplate_contentType').val('0');
		$('.search_messageTemplate_status').val('0');
	};
</script>