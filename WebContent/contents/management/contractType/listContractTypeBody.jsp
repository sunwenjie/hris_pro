<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<div id="content">
	<!-- ContractType-information -->
	<div class="box searchForm" id="contract-information">
		<div class="head">
			<label>合同类型信息</label>
		</div>
	</div>
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
				<html:form action="contractTypeAction.do?proc=list_object" styleClass="listcontractType_form">	
					<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="contractTypeHolder" property="sortColumn" />" /> 
					<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="contractTypeHolder" property="sortOrder" />" />
					<input type="hidden" name="page" id="page" value="<bean:write name="contractTypeHolder" property="page" />" />
					<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="contractTypeHolder" property="selectedIds" />" />
					<input type="hidden" name="subAction" id="subAction" value="" />
				</html:form>
<!-- 				<input type="button" class="" id="btnAdd" name="btnAdd" value="<bean:message bundle="public" key="button.add" />" onclick="link('contractTypeAction.do?proc=to_objectNew');" /> -->
<!-- 				<input type="button" class="delete" id="btnDelete" name="btnDelete" value="<bean:message bundle="public" key="button.delete" />" onclick="if (kanList_deleteConfirm( null, '<bean:message bundle="public" key="popup.select.delete.records" />','<bean:message bundle="public" key="popup.confirm.delete.records" />')) submitForm('listcontractType_form', 'deleteObjects', null, null, null, 'tableWrapper');" /> -->
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include ContractType jsp 包含table对应的jsp文件 -->  
				<jsp:include page="/contents/management/contractType/table/listContractTypeTable.jsp" flush="true"/> 
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
		$('#menu_employee_ContractType').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
	})(jQuery);
</script>
