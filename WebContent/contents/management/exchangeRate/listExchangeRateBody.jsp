<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.base.web.actions.management.ExchangeRateAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<!-- ExchangeRate-information -->
	<div class="box searchForm toggableForm" id="exchangeRate-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.exchange.rate.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listexchangeRate_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="exchangeRateAction.do?proc=list_object" styleClass="listexchangeRate_form">
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="exchangeRateHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="exchangeRateHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="exchangeRateHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="exchangeRateHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />	
					<fieldset>					
						<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.name.cn" /></label> 
							<html:text property="currencyNameZH" maxlength="100" styleClass="searchexchangeRate_currencyNameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.name.en" /></label> 
							<html:text property="currencyNameEN" maxlength="100" styleClass="searchexchangeRate_currencyNameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.exchange.rate.currency.code" /></label> 
							<html:text property="currencyCode" maxlength="100" styleClass="searchexchangeRate_currencyCode" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchexchangeRate_status">
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
				<kan:auth right="new" action="<%=ExchangeRateAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('exchangeRateAction.do?proc=to_objectNew');" />
				</kan:auth>
				
				<kan:auth right="delete" action="<%=ExchangeRateAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listexchangeRate_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include ExchangeRate JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/management/exchangeRate/table/listExchangeRateTable.jsp" flush="true"/> 
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
		$('#menu_employee_Modules').addClass('current');	
		$('#menu_employee_Configuration').addClass('selected');
		$('#menu_employee_ExchangeRates').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchexchangeRate_nameZH').val('');
		$('.searchexchangeRate_nameEN').val('');
		$('.searchexchangeRate_status').val('0');
	};
</script>
