<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ page import="com.kan.hro.web.actions.biz.importExcel.EmployeeInsuranceNoImportBatchAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label>保险账号导入</label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchEmployeeInsuranceNoImportHeader_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeInsuranceNoImportHeaderAction.do?proc=list_object" method="post" styleClass="searchEmployeeInsuranceNoImportHeader_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeInsuranceNoHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeInsuranceNoHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeInsuranceNoHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="<bean:write name="employeeInsuranceNoHeaderHolder" property="selectedIds" />" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value='<bean:write name="employeeInsuranceNoImportHeaderForm" property="batchId" />' />
				<fieldset>
					<ol class="auto">
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>ID</label>
							<html:text property="employeeId" maxlength="11" styleClass="searchEmployeeInsuranceNoImportHeader_employeeId" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（中文）</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_employeeNameZH" /> 
						</li>
						<li>
							<label><logic:equal name="role" value="1">雇员</logic:equal><logic:equal name="role" value="2">员工</logic:equal>姓名（英文）</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_employeeNameEN" /> 
						</li>
		   				<logic:equal name="role" value="1">
							<li>
								<label>派送协议ID</label>
								<html:text property="contractId" maxlength="11" styleClass="searchEmployeeInsuranceNoImportHeader_contractId" /> 
							</li> 
						</logic:equal>
						<li>
							<label>医保卡账号</label>
							<html:text property="medicalNumber" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_medicalNumber" /> 
						</li>
						<li>
							<label>社保公积金卡账号</label>
							<html:text property="sbNumber" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_sbNumber" /> 
						</li>
						<li>
							<label>公积金卡账号</label>
							<html:text property="fundNumber" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_fundNumber" /> 
						</li>
						<li>
							<label>商保保单号</label>
							<html:text property="cbNumber" maxlength="100" styleClass="searchEmployeeInsuranceNoImportHeader_cbNumber" /> 
						</li>
						<li>
							<label>状态</label>
							<html:select property="status" styleClass="searchEmployeeInsuranceNoImportHeader_status">
								<html:optionsCollection property="statuses" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
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
				<kan:auth right="back" action="<%=EmployeeInsuranceNoImportBatchAction.accessAction%>">
					<input type="button" class="function" name="btnBack" id="btnBack" value="退回">
				</kan:auth>
				<kan:auth right="list" action="<%=EmployeeInsuranceNoImportBatchAction.accessAction%>">
					<input type="button" class="reset" id="btnList" name="btnList" value="<bean:message bundle="public" key="button.list" />" onclick="link('employeeInsuranceNoImportBatchAction.do?proc=list_object');" />
				</kan:auth>
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
			
			</div>
			<!-- top -->
			<div id="tableWrapper">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="table/listEmployeeInsuranceNoImportHeaderTable.jsp" flush="true"/> 
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
		$('#menu_employee_Modules').addClass('current');
		$('#menu_employee_Import').addClass('selected');
		$('#menu_employee_import_insuranceNo').addClass('selected');
		kanList_init();
		kanCheckbox_init();
		//列表双击
		$("tbody tr").dblclick(function(){ 
			 kanlist_dbclick($(this),"searchEmployeeInsuranceNoImportHeader_form");
		});
		// 批次退回事件
		$('#btnBack').click( function(){
			if($('#selectedIds').val() != null && $('#selectedIds').val() != ''){
				if(confirm("确定退回选中的批次？")){
					$('.searchEmployeeInsuranceNoImportHeader_form').attr('action', 'employeeInsuranceNoImportHeaderAction.do?proc=rollback_batch');
					submitForm('searchEmployeeInsuranceNoImportHeader_form');
					$('#selectedIds').val('');
				}
			}else{
				alert("请选择要退回的批次。");
			}
		});
	})(jQuery);
	
	function resetForm() {
	    $('.searchEmployeeInsuranceNoImportHeader_employeeId').val('');
	    $('.searchEmployeeInsuranceNoImportHeader_employeeNameZH').val('');
	    $('.searchEmployeeInsuranceNoImportHeader_employeeNameEN').val('');
	    $('.searchEmployeeInsuranceNoImportHeader_contractId').val('');
	    $('.searchEmployeeInsuranceNoImportHeader_status').val('0');
	}; 
</script>
