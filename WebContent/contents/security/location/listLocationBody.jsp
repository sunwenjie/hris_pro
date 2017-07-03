<%@ page pageEncoding="GBK"%>
<%@page import="com.kan.base.web.actions.security.LocationAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="location-information">
		<div class="head">
			<label><bean:message bundle="security" key="security.office.location.search.title" /></label>
		</div>
		<!-- inner -->
		<a href="#" class="toggle tiptip" title="<bean:message bundle="security" key="security.business.type.search.title" />">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listlocation_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="secLocationAction.do?proc=list_object" styleClass="listlocation_form">
				<input type="hidden" name="cityIdTemp" id="cityIdTemp" class="searchlocation_cityIdTemp" value="<bean:write name='locationForm' property='cityId' />">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="locationHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="locationHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="locationHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="locationHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.cn" /></label>
							<html:text property="nameZH" maxlength="50" styleClass="searchlocation_nameZH" />
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location.name.en" /></label> 
							<html:text property="nameEN" maxlength="50" styleClass="searchlocation_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location.address.cn" /></label> 
							<html:text property="addressZH" maxlength="50" styleClass="searchlocation_addressZH" /> 
						</li>
						<li>
							<label><bean:message bundle="security" key="security.office.location.address.en" /></label> 
							<html:text property="addressEN" maxlength="50" styleClass="searchlocation_addressEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.city" /></label> 
							<html:select property="provinceId" styleClass="searchlocation_provinceId">
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchlocation_status">
								<html:optionsCollection property="locationStatuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- Location-information -->
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
				<kan:auth right="new" action="<%=LocationAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('secLocationAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=LocationAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listlocation_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/security/location/table/listLocationTable.jsp" flush="true"/> 
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
		$('#menu_security_OrgManagement').addClass('selected');
		$('#menu_security_Location').addClass('selected');
		$('#searchDiv').hide();
		
		// 初始化省份控件
		provinceChange('searchlocation_provinceId', 'searchObject', $('.searchlocation_cityIdTemp').val(), 'cityId');
		
		// 绑定省Change事件
		$('.searchlocation_provinceId').change( function () { 
			provinceChange('searchlocation_provinceId', 'searchObject', 0, 'cityId');
		});
			
		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.searchlocation_addressZH').val('');
		$('.searchlocation_addressEN').val('');
		$('.searchlocation_nameZH').val('');
		$('.searchlocation_nameEN').val('');
		$('.searchlocation_status').val('0');
		$('.searchlocation_provinceId').val('0');
		$('.cityId').val('0');
		//$('.cityId').hide();
	};
</script>