<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	PagedListHolder accountHolder = (PagedListHolder) request.getAttribute("accountHolder");
%>

<div id="content">
	<div class="box searchForm toggableForm" id="account-information">
		<div class="head">
			<label>系统账户</label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<html:form action="accountAction.do?proc=list_object" styleClass="listaccount_form">
				<fieldset>
					<ol>
						<li>
							<label>账户名 （中文）</label> 
							<html:text property="nameCN" maxlength="25" styleClass="searchaccount_namecn" /> 
						</li>
						<li>
							<label>账户名 （英文）</label> 
							<html:text property="nameEN" maxlength="25" styleClass="searchaccount_nameen" /> 
						</li>
						<li>
							<label>公司名称</label> 
							<html:text property="entityName" maxlength="100" styleClass="searchaccount_entityName" /> 
						</li>
						<li>
							<label>地址</label> 
							<html:text property="address" maxlength="100" styleClass="searchaccount_address" /> 
						</li>
						<li>
							<label>联系人</label> 
							<html:text property="linkman" maxlength="50" styleClass="searchaccount_linkman" /> 
						</li>
						<li>
							<label>性别</label> 
							<html:select property="salutation" styleClass="searchaccount_salutation">
								<html:optionsCollection property="salutations" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
						<li>
							<label>职务</label> 
							<html:text property="title" maxlength="100" styleClass="searchaccount_title" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchaccount_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
					<input type="hidden" name="accountId" id="accountId" value="<bean:write name="accountForm" property="encodedId"/>"/>
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="accountHolder" property="sortColumn" />" />
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="accountHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="accountHolder" property="page" />" /> 
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="accountHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
					<p>
						<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search"/>" onclick="submitForm('listaccount_form', 'searchObject', null, null, null, null);" />
						<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset"/>" onclick="resetForm();" />
					</p>
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
				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add"/>" onclick="link('accountAction.do?proc=to_objectNew');" /> 
				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete"/>" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listaccount_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				<input type="button" class="function" name="btnActive" id="btnActive" value="激活" onclick="if (kanList_activeConfirm()) submitForm('listaccount_form', 'activeObjects', null, null, null, 'tableWrapper');" />
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="helpText" class="helpText"></div>
			<div id="scrollWrapper">
				<div id="scrollContainer"></div>
			</div>
			<div id="tableWrapper">
				<!-- Include table jsp 包含tabel对应的jsp文件 -->  
				<jsp:include page="/contents/system/account/table/listAccountTable.jsp" flush="true"/> 
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
		$('#menu_system_Account').addClass('selected');
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);

	function resetForm(){
		$('.searchaccount_namecn').val('');
		$('.searchaccount_nameen').val('');
		$('.searchaccount_entityName').val('');
		$('.searchaccount_linkman').val('');
		$('.searchaccount_salutation').val('0');
		$('.searchaccount_title').val('');
		$('.searchaccount_status').val('0');
	};
</script>