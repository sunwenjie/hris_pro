<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.security.PositionGradeAction"%>
<%@ page import="com.kan.base.domain.management.PositionGradeCurrencyVO" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<%
	final PagedListHolder positionGradeHolder = (PagedListHolder) request.getAttribute("positionGradeHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="positionGrade-information">
		<div class="head">
			<label><bean:message bundle="security" key="security.position.grade.search.title" /> </label>
		</div>
		<a href="#" class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listPositionGrade_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="positionGradeAction.do?proc=list_object" styleClass="listPositionGrade_form">
				<input type="hidden" name="positionGradeId" id="positionGradeId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="positionGradeHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="positionGradeHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="positionGradeHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="positionGradeHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.position.grade.name.cn" /></label> 
							<html:text property="gradeNameZH" maxlength="20" styleClass="searchPositionGrade_gradeNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.grade.name.en" /></label> 
							<html:text property="gradeNameEN" maxlength="20" styleClass="searchPositionGrade_gradeNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.position.grade.weight" /></label>
							<html:text property="weight" maxlength="3" styleClass="searchPositionGrade_weight" />
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label>
							<html:select property="status" styleClass="searchPositionGrade_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>

	<!-- PositionGrade-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="MESSAGE">
					<div class="message success fadable">
						<bean:write name="MESSAGE" />
			    		<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('positionGradeAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="new" action="<%=PositionGradeAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listPositionGrade_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
			<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/security/positionGrade/table/listPositionGradeTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper --><!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
			<!-- List Component -->
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_security_Modules').addClass('current');
		$('#menu_security_PositionManagement').addClass('selected');
		$('#menu_security_PositionGrade').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm() {
		$('.searchPositionGrade_gradeNameZH').val('');
		$('.searchPositionGrade_gradeNameEN').val('');
		$('.searchPositionGrade_weight').val('');
		$('.searchPositionGrade_status').val('0');
	};
</script>