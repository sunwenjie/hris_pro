<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.security.EntityAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="entity-information">
		<div class="head">
			<label><bean:message bundle="security" key="security.entity.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value='<bean:message bundle="public" key="button.search"  />' onclick="submitForm('listentity_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value='<bean:message bundle="public" key="button.reset" />' onclick="resetForm();" />
			</div>
			<html:form action="entityAction.do?proc=list_object" styleClass="listentity_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="entityHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="entityHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="entityHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="entityHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.entity.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchEntity_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchEntity_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.entity.business.type" /></label> 
							<html:select property="bizType" styleClass="searchEntity_bizType">
								<html:optionsCollection property="bizTypes" value="mappingId" label="mappingValue" />
							</html:select>	
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchEntity_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- Entity-information -->
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
				<kan:auth right="new" action="<%=EntityAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value='<bean:message bundle="public" key="button.add"  />' onclick="link('entityAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=EntityAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value='<bean:message bundle="public" key="button.delete"  />' onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listentity_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include Entity JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/security/entity/table/listEntityTable.jsp" flush="true"/> 
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
		$('#menu_security_Modules').addClass('current');	
		$('#menu_seciruty_LegalEntity').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchEntity_nameZH').val('');
		$('.searchEntity_nameEN').val('');
		$('.searchEntity_bizType').val('0');
		$('.searchEntity_status').val('0');
	};
</script>
