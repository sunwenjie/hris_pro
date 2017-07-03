<%@page import="com.kan.base.web.actions.management.AnnualLeaveRuleHeaderAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="AnnualLeaveRuleHeader-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.annual.leave.rule.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listAnnualLeaveRuleHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="annualLeaveRuleHeaderAction.do?proc=list_object" styleClass="listAnnualLeaveRuleHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="annualLeaveRuleHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="annualLeaveRuleHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="annualLeaveRuleHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="annualLeaveRuleHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.annual.leave.rule.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchAnnualLeaveRuleHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.annual.leave.rule.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchAnnualLeaveRuleHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.annual.leave.rule.header.base.on" /></label> 
							<html:select property="baseOn" styleClass="searchAnnualLeaveRuleHeader_baseOn">
								<html:optionsCollection property="baseOns" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.annual.leave.rule.header.divide.type" /></label> 
							<html:select property="divideType" styleClass="searchAnnualLeaveRuleHeader_divideType">
								<html:optionsCollection property="divideTypes" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchAnnualLeaveRuleHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<!-- AnnualLeaveRuleHeader-information -->
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
				<kan:auth right="new" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('annualLeaveRuleHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=AnnualLeaveRuleHeaderAction.ACCESS_ACTION%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listAnnualLeaveRuleHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			 <div id="tableWrapper">
				<!-- Include AnnualLeaveRuleHeader JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/management/annualLeaveRule/header/table/listAnnualLeaveRuleHeaderTable.jsp" flush="true"/> 
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
		$('#menu_salary_Modules').addClass('current');			
		$('#menu_salary_Configuration').addClass('selected');
		$('#menu_salary_AnnualLeave_Rule').addClass('selected');
		
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchAnnualLeaveRuleHeader_nameZH').val('');
		$('.searchAnnualLeaveRuleHeader_nameEN').val('');
		$('.searchAnnualLeaveRuleHeader_status').val('0');
		$('.searchAnnualLeaveRuleHeader_baseOn').val('0');
		$('.searchAnnualLeaveRuleHeader_divideType').val('0');
	};
</script>