<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.define.TaxTemplateHeaderAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="taxTemplateHeader-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.tax.template.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listTaxTemplateHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="taxTemplateHeaderAction.do?proc=list_object" styleClass="listTaxTemplateHeader_form">
				<input type="hidden" name="cityIdTemp" id="cityIdTemp" class="searchTaxTemplateHeader_cityIdTemp" value="<bean:write name='taxTemplateHeaderForm' property='cityId' />">
				<input type="hidden" name="templateHeaderId" id="templateHeaderId" value="" />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="taxTemplateHeaderHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="taxTemplateHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="taxTemplateHeaderHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="taxTemplateHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.tax.template.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchTaxTemplateHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.tax.template.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchTaxTemplateHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /></label> 
							<html:select property="provinceId" styleClass="searchTaxTemplateHeade_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>		
						</li>						
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchTaxTemplateHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	
	<!-- List TaxTemplate Header - information -->
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
				<kan:auth right="new" action="<%=TaxTemplateHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('taxTemplateHeaderAction.do?proc=to_objectNew');" /> 
				</kan:auth>
				<kan:auth right="delete" action="<%=TaxTemplateHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listTaxTemplateHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include taxTemplateHeader jsp 包含table对应的jsp文件 -->  
				<jsp:include page="/contents/define/taxTemplate/header/table/listTaxTemplateHeaderTable.jsp" flush="true"/> 
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
		$('#menu_salary_IncomeTaxTemplate').addClass('selected');
		$('#searchDiv').hide();
		
		// 初始化省份控件
		provinceChange('searchTaxTemplateHeade_provinceId', 'modifyObject', $('.searchTaxTemplateHeader_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.searchTaxTemplateHeade_provinceId').change( function () { 
			provinceChange('searchTaxTemplateHeade_provinceId', 'modifyObject', 0, 'cityId');
		});
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 重置按钮事件
	function resetForm(){
		$('.searchTaxTemplateHeader_taxId').val(0);
		$('.searchTaxTemplateHeader_searchId').val(0);
		$('.searchTaxTemplateHeader_nameZH').val('');
		$('.searchTaxTemplateHeader_nameEN').val('');
		$('.searchTaxTemplateHeader_usePagination').val(0);
		$('.searchTaxTemplateHeader_status').val('0');
	};
</script>