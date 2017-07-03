<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ page import="com.kan.base.web.action.BaseAction"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://kangroup/authTag" prefix="kan"%>

<div id="content">
	<div id="timesheetBatch" class="box toggableForm">
		<div class="head">
			<label id="pageTitle"><bean:message bundle="public" key="button.create" />&nbsp;<bean:message bundle="business" key="business.ts" /></label>
		</div>
		<div class="inner">
			<div class="top">
				<kan:auth right="new" action="<%=TimesheetBatchAction.accessActionInHouse%>">
					<input type="button" class="addbutton" name="btnSave" id="btnSave" value="<bean:message bundle="public" key="button.create" />" /> 
				</kan:auth>
				<input type="button" class="reset" name="btnList" id="btnList" value="<bean:message bundle="public" key="button.list" />" /> 
			</div>
			<!-- Include JSP 包含form对应的JSP文件 -->  
			<jsp:include page="/contents/business/attendance/timesheet/batch/form/generateTimesheetBatchForm.jsp" flush="true"/>
		</div>
	</div>
</div>

<!-- Employee Popup Box -->
<div id="popupWrapper">
	<jsp:include page="/popup/searchClient.jsp"></jsp:include>
	<jsp:include page="/popup/searchOrder.jsp"></jsp:include>
	<jsp:include page="/popup/searchEmployee.jsp"></jsp:include>
	<jsp:include page="/popup/searchContract.jsp"></jsp:include>
</div>
							
<script type="text/javascript">
	(function($) {
		// 初始化菜单
		$('#menu_attendance_Modules').addClass('current');
		$('#menu_attendance_Timesheet').addClass('selected');
	
     	// 保存按钮点击事件
		$('#btnSave').click( function () { 
			var flag = 0;
			
		    flag = flag + validate('timesheetBatch_monthly', true, 'select', 0, 0);
	 	    flag = flag + validate('timesheetBatch_description', false, 'common', 500, 0, 0, 0);
	 	    
			if( flag == 0 ){
				submit('timesheetBatch_form');
			}
		});
		
		// 列表按钮点击事件
		$('#btnList').click( function () {
			link('timesheetBatchAction.do?proc=list_object');
		});
	})(jQuery);	
</script>
