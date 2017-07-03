<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.page.PagedListHolder"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<div id="content">
	<div class="box searchForm toggableForm" id="Search-Information">
		 <div class="head">
	        <label id="pageTitle">导入考勤汇总</label>
	        <label class="recordId">&nbsp; (ID: <bean:write name="attendanceImportBatchForm" property="batchId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('search_attendanceImportHeaderForm', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="attendanceImportHeaderAction.do?proc=list_object" method="post" styleClass="search_attendanceImportHeaderForm">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="attendanceImportHeaderHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="attendanceImportHeaderHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="attendanceImportHeaderHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="batchId" id="batchId" value="<bean:write name="attendanceImportBatchForm" property="encodedId" />" />	
				<fieldset>
					<ol class="auto">
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.id" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.id" /></logic:equal>
							</label>
							<html:text property="employeeId" maxlength="10" styleClass="searchSBHeader_employeeId" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.cn" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.cn" /></logic:equal>		
							</label>
							<html:text property="employeeNameZH" maxlength="100" styleClass="searchSBHeader_employeeNameZH" /> 
						</li>
						<li>
							<label>
								<logic:equal name="role" value="1"><bean:message bundle="public" key="public.employee1.name.en" /></logic:equal>
								<logic:equal name="role" value="2"><bean:message bundle="public" key="public.employee2.name.en" /></logic:equal>	
							</label>
							<html:text property="employeeNameEN" maxlength="100" styleClass="searchSBHeader_employeeNameEN" /> 
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<div class="box noHeader">
	    <div class="inner">
	        <div class="top">
	        	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />">
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<div id="tableWrapper">
				<jsp:include page="/contents/business/attendance/import/table/listAttendanceImportHeaderTable.jsp" flush="true"/> 
			</div>
			<div class="bottom"><p/></div>
         </div>
  	</div>
</div>
							
<script type="text/javascript">
	(function($) {
		// 设置顶部菜单选择样式
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Import').addClass('selected');
		$('#menu_attendance_Import_Attendance').addClass('selected');
		$('#searchDiv').hide();
		
		$('#btnList').click(function() {
			if (agreest())
				link('attendanceImportBatchAction.do?proc=list_object');
		});
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.search_attendanceImportHeaderForm');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>

