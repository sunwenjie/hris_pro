<%@page import="com.kan.base.page.PagedListHolder"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder countryHolder = (PagedListHolder)request.getAttribute("countryHolder");
%>

<div id="content">
	<!-- Country-information -->
	<div id="search-results" class="box" >
		<div class="head">
			<label>国家</label>
		</div>
		<html:form action="countryAction.do?proc=list_object" styleClass="listcountry_form">
			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="countryHolder" property="sortColumn" />" /> 
			<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="countryHolder" property="sortOrder" />" />
			<input type="hidden" name="page" id="page" value="<bean:write name="countryHolder" property="page" />" />
			<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="countryHolder" property="selectedIds" />" />
			<input type="hidden" name="subAction" id="subAction" value="" />					
		</html:form>
		<div class="inner">
			<div class="top">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('countryAction.do?proc=to_objectNew');" />
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcountry_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/country/table/listCountryTable.jsp" flush="true"/>
			</div>
			<!-- tableWrapper -->
			<!-- frmList_ohrmListComponent -->
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		$('#menu_system_Modules').addClass('current');			
		$('#menu_system_City').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
