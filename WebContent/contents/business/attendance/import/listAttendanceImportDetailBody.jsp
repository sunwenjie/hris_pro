<%@page import="com.kan.base.util.KANUtil"%>
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
	        <label class="recordId">&nbsp; (ID: <bean:write name="attendanceImportHeaderForm" property="headerId" />)</label>
	    </div>
   	    <a class="toggle tiptip" title="<bean:message key="public.hideoptions"/>">&gt;</a>
   	    <div id="searchDiv" class="inner" style="display: none;">
   	    	<div class="top">
	            <input type="button" id="searchBtn" name="searchBtn" value="<bean:message bundle="public" key="button.search" />" onclick="submitForm('search_attendanceImportDetailForm', 'searchObject', null, null, null, null, null, null, true);" />
	            <input type="button" class="reset" id="resetBtn" name="resetBtn" value="<bean:message bundle="public" key="button.reset" />" onclick="resetForm();" />
       		</div>
       		<html:form action="attendanceImportDetailAction.do?proc=list_object" method="post" styleClass="search_attendanceImportDetailForm">
				<input type="hidden" name="sortColumn" id="sortColumn" value="<bean:write name="attendanceImportDetailHolder" property="sortColumn" />" /> 
				<input type="hidden" name="sortOrder" id="sortOrder" value="<bean:write name="attendanceImportDetailHolder" property="sortOrder" />" />
				<input type="hidden" name="page" id="page" value="<bean:write name="attendanceImportDetailHolder" property="page" />" />
				<input type="hidden" name="selectedIds" id="selectedIds" value="" />
				<input type="hidden" name="subAction" id="subAction" value="" />
				<input type="hidden" name="headerId" id="headerId" value="<bean:write name="attendanceImportHeaderForm" property="encodedId" />" />	
				<fieldset>
					<ol class="auto">
						<li>
							<label>科目</label>
							<html:select property="itemId" styleClass="search_itemId">
								<option value="0"><%=KANUtil.getEmptyMappingVO( request.getLocale() ).getMappingValue() %></option>
								<html:optionsCollection property="leaveItems" value="mappingId" label="mappingValue"  />
								<html:optionsCollection property="otItems" value="mappingId" label="mappingValue"  />
							</html:select>
						</li>
					</ol>
				</fieldset> 
       		</html:form>
   	    </div>
	</div>
	
	<div class="box noHeader">
	    <div class="inner">
	        <div class="top">
	        	<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.back.fh" />">
				<a id="filterRecords" name="filterRecords"><bean:message bundle="public" key="set.filerts" /></a>
			</div>
			<div id="tableWrapper">
				<jsp:include page="/contents/business/attendance/import/table/listAttendanceImportDetailTable.jsp" flush="true"/> 
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
				link('attendanceImportHeaderAction.do?proc=list_object&batchId=<bean:write name="attendanceImportHeaderForm" property="encodedBatchId" />');
		});
	})(jQuery);
	
	function resetForm() {
	   var oForm = $('.search_attendanceImportDetailForm');
	   oForm.find('input:visible').val('');
	   oForm.find('select:visible').val('0');
	};
</script>

