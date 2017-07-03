<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<div class="box searchForm toggableForm" id="socialBenefitHeader-information">
		<div class="head">
			<label><bean:message bundle="system" key="system.sb.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listSocialBenefitHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="socialBenefitHeaderAction.do?proc=list_object" styleClass="listSocialBenefitHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="socialBenefitHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="socialBenefitHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="socialBenefitHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="socialBenefitHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="system" key="system.sb.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchSocialBenefitHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="system" key="system.sb.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchSocialBenefitHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /></label> 
							<html:hidden property="cityIdTemp" styleClass="location_cityIdTemp" />
							<html:select property="provinceId" styleClass="location_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.sb.header.residency.type" /></label> 
							<html:select property="residency" styleClass="searchSocialBenefitHeader_residency">
								<html:optionsCollection property="residencys" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.sb.header.adjustment.month" /></label> 
							<html:select property="adjustMonth" styleClass="searchSocialBenefitHeader_adjustMonth">
								<html:optionsCollection property="adjustMonths" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="system" key="system.sb.header.makeup" /></label> 
							<html:select property="makeup" styleClass="searchSocialBenefitHeader_makeup">
								<html:optionsCollection property="flags" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSocialBenefitHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- SocialBenefitHeader-information -->
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
				<logic:equal value="1" name="PAGE_ACCOUNT_ID">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('socialBenefitHeaderAction.do?proc=to_objectNew');" />
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listSocialBenefitHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</logic:equal>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include SocialBenefitHeader JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/system/socialBenefit/header/table/listSocialBenefitHeaderTable.jsp" flush="true"/> 
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
		$('#menu_sb_Modules').addClass('current');
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_SB').addClass('selected');
		$('#menu_sb_sbPolicy').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
		
		// 绑定省Change事件
		$('.location_provinceId').change( function () { 
			provinceChange('location_provinceId', 'modifyObject', 0, '');
		});
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchSocialBenefitHeader_nameZH').val('');
		$('.searchSocialBenefitHeader_nameEN').val('');
		$('.searchSocialBenefitHeader_residency').val('0');
		$('.searchSocialBenefitHeader_adjustMonth').val('0');
		$('.searchSocialBenefitHeader_makeup').val('0');
		$('.searchSocialBenefitHeader_status').val('0');
	};
</script>
