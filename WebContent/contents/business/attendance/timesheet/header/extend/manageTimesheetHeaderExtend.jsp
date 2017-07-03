<%@page import="com.kan.hro.domain.biz.vendor.VendorContactVO"%>
<%@page import="com.kan.hro.web.renders.biz.vendor.VendorRender"%>
<%@page import="com.kan.hro.domain.biz.vendor.VendorVO"%>
<%@ page import="com.kan.hro.web.actions.biz.attendance.TimesheetBatchAction"%>
<%@ page pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="kan" uri="http://kangroup/authTag"%>

<logic:notEqual name="timesheetDetailHolder" property="holderSize" value="0">
<div id="tab"> 
	<div class="tabMenu"> 
		<ul> 
			<li id="tabMenu1" onClick="changeTab(1,2)" class="first hover">
				<bean:message bundle="public" key="menu.table.title.ts.detail" />&nbsp;( <bean:write name="timesheetDetailHolder" property="holderSize" /> )
			</li> 
			<li id="tabMenu2" onClick="changeTab(2,2)" class="">
				<bean:message bundle="public" key="menu.table.title.ts.salary" />&nbsp;( <bean:write name="timesheetAllowanceHolder" property="holderSize" /> ) 
			</li> 
		</ul> 
	</div> 
	<div class="tabContent"> 
		<div id="tabContent1" class="kantab kanThinkingCombo" >
			<div id="tableWrapper">
				<!--TimesheetDetail Information Table--> 
				<jsp:include page="/contents/business/attendance/timesheet/detail/table/listTimesheetDetailTable.jsp" flush="true"/>
			</div>
		</div> 
		<div id="tabContent2" class="kantab kanMultipleChoice1" style="display:none">
			<div class="top">
				<kan:auth right="modify" action="<%=TimesheetBatchAction.accessActionInHouse%>">
					<logic:equal name="timesheetHeaderForm" property="status" value="1">
						<input type="button" id="btnAddTimesheetAllowance" class="save" value="<bean:message bundle="public" key="button.add" />" />
					</logic:equal>
					<logic:equal name="timesheetHeaderForm" property="status" value="4">
						<input type="button" id="btnAddTimesheetAllowance" class="save" value="<bean:message bundle="public" key="button.add" />" />
					</logic:equal>
				</kan:auth>
			</div>
			<div id="tableWrapper1">
				<!--TimesheetAllowance Information Table--> 
				<jsp:include page="/contents/business/attendance/timesheet/allowance/table/listTimesheetAllowanceTable.jsp" flush="true"/>
			</div>				
		</div>
	</div>
</div>
</logic:notEqual>