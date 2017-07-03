<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder userHolder = (PagedListHolder) request.getAttribute("userHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="user-information">
		<div class="head">
			<label><bean:message key="user.usersystem" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listuser_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="userAction.do?proc=list_object" styleClass="listuser_form">
				<input type="hidden" name="staffId" id="staffId" class="searchuser_staffId" value="<bean:write name="userForm" property="staffId" />"  />
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="userHolder" property="sortColumn" />" />
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="userHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="userHolder" property="page" />" /> 
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="userHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message key="user.username" /></label> 
							<html:text property="username" maxlength="20" styleClass="searchuser_username" /> 
							<input type="hidden" name="userId" id="userId" value="" />
						</li>
						<li>
							<label><bean:message key="user.empname" /></label> 
							<html:text property="staffName" maxlength="20" styleClass="inputFormatHint searchuser_staffName" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchuser_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- User-information -->

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
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/security/user/table/listUserTable.jsp" flush="true"/> 
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
		$('#menu_security_User').addClass('selected');
		$('#searchDiv').hide();

		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.searchuser_username').val('');
		$('.searchuser_staffName').val('');
		$('.searchuser_status').val('0');
		$('.searchuser_staffId').val('');
	};
	
	kanThinking_column('searchuser_staffName', 'searchuser_staffId', 'staffAction.do?proc=list_object_json');
</script>