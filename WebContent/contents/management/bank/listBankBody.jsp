<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import=" com.kan.base.web.actions.management.BankAction"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>


<div id="content">
	<div class="box searchForm toggableForm" id="bank-information">
		<div class="head">
			<label><bean:message bundle="management" key="management.bank.search.title" /></label>
		</div>
		<!-- inner -->
		<a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
		<div id="searchDiv" class="inner">
			<div class="top">
				<input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('listBank_form', 'searchObject', null, null, null, null);" />
				<input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
			</div>
			<html:form action="bankAction.do?proc=list_object" styleClass="listBank_form">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="bankHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="bankHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="bankHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="bankHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label><bean:message bundle="management" key="management.bank.name.cn" /></label> 
							<html:text property="nameZH" maxlength="100" styleClass="searchBank_nameZH" /> 
						</li>
						<li>
							<label><bean:message bundle="management" key="management.bank.name.en" /></label> 
							<html:text property="nameEN" maxlength="100" styleClass="searchBank_nameEN" /> 
						</li>
						<li>
							<label><bean:message bundle="public" key="public.status" /></label> 
							<html:select property="status" styleClass="searchBank_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue" />
							</html:select>
						</li>
					</ol>
				</fieldset>
			</html:form>
		</div>
	</div>
	<!-- Bank - information -->
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
				<kan:auth right="new" action="<%=BankAction.accessAction%>">
					<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('bankAction.do?proc=to_objectNew');" />
				</kan:auth>
				<kan:auth right="delete" action="<%=BankAction.accessAction%>">
					<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listBank_form', 'deleteObjects', null, null, null, 'tableWrapper');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/management/bank/table/listBankTable.jsp" flush="true"/> 
			</div>
			<!-- tableWrapper -->
			<div class="bottom">
				<p>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	(function($) {
		// 设置菜单选择样式，如果当前用户是Super
		if('<bean:write name="accountId" />' == '1'){
			$('#menu_system_Modules').addClass('current');
			$('#menu_system_Bank').addClass('selected');
		}else{
			$('#menu_finance_Modules').addClass('current');	
			$('#menu_finance_Configuration').addClass('selected');
			$('#menu_finance_Bank').addClass('selected');
		}
		$('#searchDiv').hide();
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
	
	// 搜索重置
	function resetForm(){
		$('.searchBank_nameZH').val('');
		$('.searchBank_nameEN').val('');
		$('.searchBank_status').val('0');
	};
</script>
