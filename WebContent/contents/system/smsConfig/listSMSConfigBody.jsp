<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder smsConfigHolder = (PagedListHolder) request.getAttribute("smsConfigHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="smsConfig-information">
		<div class="head">
			<label>系统短信配置</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<html:form action="smsConfigAction.do?proc=list_object" styleClass="listsmsConfig_form">
				<fieldset>
					<ol>
						<li>
							<label>配置名 （中文）</label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchsmsConfig_namezh" /> 
							<input type="hidden" name="ruleId" id="ruleId" value="" />
						</li>
						<li>
							<label>配置名 （英文）</label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchsmsConfig_nameen" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchsmsConfig_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="smsConfigHolder" property="sortColumn" />" />
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="smsConfigHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="smsConfigHolder" property="page" />" /> 
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="smsConfigHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<p>
						<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listsmsConfig_form', 'searchObject', null, null, null, null);" />
						<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
					</p>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- User-information -->

	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('smsConfigAction.do?proc=to_objectNew');" /> 
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listrule_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/smsConfig/table/listSMSConfigTable.jsp" flush="true"/>
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
		$('#menu_system_Modules').addClass('current');
		$('#menu_system_SMSConfig').addClass('selected');
		$('#searchDiv').hide();
		
		// 继续筛选记录
		$('#filterRecords').click( function () {
			if(!$('#searchDiv').is(':hidden')){
				$('#filterRecords').html("<bean:message bundle="public" key="set.filerts" />");
			}else{
				$('#filterRecords').html("关闭筛选功能？");
			}
			$('.tiptip').trigger("click");
		});

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.searchsmsConfig_namezh').val('');
		$('.searchsmsConfig_nameen').val('');
		$('.searchsmsConfig_status').val('0');
	};
</script>