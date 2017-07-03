<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.actions.management.SocialBenefitSolutionHeaderAction"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="socialBenefitSolutionHeader-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.sb.solution.header.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listSocialBenefitSolutionHeader_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="socialBenefitSolutionHeaderAction.do?proc=list_object" styleClass="listSocialBenefitSolutionHeader_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="socialBenefitSolutionHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="socialBenefitSolutionHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="socialBenefitSolutionHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="socialBenefitSolutionHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.sb.solution.header.id" /></label> 
							<html:text property="headerId" maxlength="100" styleClass="searchSocialBenefitSolutionHeader_headerId" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sb.solution.header.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchSocialBenefitSolutionHeader_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sb.solution.header.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchSocialBenefitSolutionHeader_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.province.city" /></label> 
							<html:hidden property="cityIdTemp" styleClass="location_cityIdTemp" />
							<html:select property="provinceId" styleClass="location_provinceId" >
								<html:optionsCollection property="provinces" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sb.solution.header.start.date.limit" /></label> 
							<html:select property="startDateLimit" styleClass="searchSocialBenefitSolutionHeader_startDateLimit">
								<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="management" key="management.sb.solution.header.end.date.limit" /></label> 
							<html:select property="endDateLimit" styleClass="searchSocialBenefitSolutionHeader_endDateLimit">
								<html:optionsCollection property="dates" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchSocialBenefitSolutionHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- SocialBenefitSolutionHeader-information -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div id="messageWrapper">
				<logic:present name="SUCCESS_MESSAGE">
					<div class="message success fadable">
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="ADD">保存成功！</logic:equal>
						<logic:equal name="SUCCESS_MESSAGE_TYPE" value="UPDATE">编辑成功！</logic:equal>
		    			<a onclick="$('div.fadable').remove();" class="messageCloseButton">&nbsp;</a>
					</div>
				</logic:present>
			</div>
			<div class="top">
				<kan:auth right="new" action="<%=SocialBenefitSolutionHeaderAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('socialBenefitSolutionHeaderAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=SocialBenefitSolutionHeaderAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listSocialBenefitSolutionHeader_form', 'deleteObjects', null, null, null, 'tableWrapper');" />				
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include SocialBenefitSolutionHeader JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/management/socialBenefitSolution/header/table/listSocialBenefitSolutionHeaderTable.jsp" flush="true"/> 
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
		$('#menu_sb_Configuration').addClass('selected');
		$('#menu_sb_Solution').addClass('selected');
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
		$('.searchSocialBenefitSolutionHeader_headerId').val('');
		$('.searchSocialBenefitSolutionHeader_nameZH').val('');
		$('.searchSocialBenefitSolutionHeader_nameEN').val('');
		$('.searchSocialBenefitSolutionHeader_startDateLimit').val('0');
		$('.searchSocialBenefitSolutionHeader_endDateLimit').val('0');
		$('.searchSocialBenefitSolutionHeader_status').val('0');
	};
</script>
