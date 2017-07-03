<%@ page import="com.kan.hro.web.actions.biz.employee.EmployeeReportAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">	
	<!-- Information Search Form -->
	<div class="box searchForm toggableForm" id="Search-Information">
		<div class="head">
       		<label><bean:message bundle="public" key="public.contacts" /></label>
   	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('searchContact_form', 'searchObject', null, null, null, null);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="employeeReportAction.do?proc=getContacts" method="post" styleClass="searchContact_form">
       			<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="employeeReportHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="employeeReportHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="employeeReportHolder" property="page" />" />
				<input type="hidden" name="subAction" id="subAction" class="subAction" value="" />
				<input type="hidden" name="downloadType" id="downloadType" class="downloadType" value="" />
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<bean:message bundle="public" key="public.employee2.id" />
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="search_employeeId" /> 
						</li>
						<li>
							<label>
								<bean:message bundle="public" key="public.employee2.name.cn" />
							</label>
							<html:text property="nameZH" maxlength="100" styleClass="search_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<bean:message bundle="public" key="public.employee2.name.en" />
							</label>
							<html:text property="nameEN" maxlength="100" styleClass="search_employeeNameEN" /> 
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<!-- Information Search Result -->
	<div class="box noHeader" id="search-results">
		<div class="inner">
			<div class="top">
				<a id="filterRecords" name="filterRecords" class="inquiry"><bean:message bundle="public" key="set.filerts" /></a>
				<a id="exportExcel" style="margin-right: 10px;" name="exportExcel" class="commonTools" title="<bean:message bundle="public" key="img.title.tips.export.excel" />" onclick="submitFormForDownload('searchContact_form');"><img src="images/appicons/excel_16.png" /></a> 
				<a id="exportExcel_wx" style="margin-right: 10px;display:none" name="exportExcel_wx" class="commonTools" title="export weChat contacts" onclick="submitFormForWXDownload('searchContact_form');"><img src="images/appicons/excel_16.png" /></a> 
			</div>
			<!-- top -->
			<div id="tableWrapper" style="overflow: hidden;">
				<!-- Include JSP 包含table对应的JSP文件 -->  
				<jsp:include page="/contents/business/employee/contacts/listContactsBodyTable.jsp" flush="true"/> 
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
		// JS of the List 系统自动生成js代码 
		$('#menu_employee_Modules').addClass('current');
		$('#menu_public_contacts').addClass('selected');
		
		kanList_init();
		kanCheckbox_init();
	    $('#titleNameList').val(getTabTitle());
		$('#titleIdList').val(getTabDB()); 
	})(jQuery); 
	
	function resetForm() {
	   var oForm = $('.searchContact_form');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
	
	function getTabTitle(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).html();
		});
		return oArray;
	};
	
	function getTabDB(){
		var oArray = new Array();
		var oTTFirstTr = $('#_fixTableMain table thead tr th span');
		oTTFirstTr.each( function (i){
			oArray[i] = $(this).attr('id');
		});
		return oArray;
	};
	
	//微信通讯录下载
	function submitFormForWXDownload(searchContact_form){
		$("#downloadType").val("wx");
		submitFormForDownload(searchContact_form);
		$("#downloadType").val("");
	};
</script>
